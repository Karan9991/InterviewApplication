package com.pritesh.interviewapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.pritesh.interviewapplication.data.User;

import io.realm.Realm;

public class ProfileUpdateActivity extends Activity
{
    EditText nameText, oldPasswordText, passwordText, confirmPasswordText, mobileText;
    TextView emailText;
    Button updateButton;
    Realm mUserRealm;
    String mUserEmail;
    User mUser, updatedUser;
    Switch mSwitchTouchId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        mUserEmail = ((MyApplication) getApplicationContext()).getLoggedInUserEmail();

        emailText = (TextView) findViewById(R.id.input_email);
        nameText = (EditText) findViewById(R.id.input_name);
        oldPasswordText = (EditText) findViewById(R.id.input_old_password);
        passwordText = (EditText) findViewById(R.id.input_new_password);
        confirmPasswordText = (EditText) findViewById(R.id.input_confirm_password);
        mobileText = (EditText) findViewById(R.id.input_mobile);
        updateButton = (Button) findViewById(R.id.btn_update);
        mSwitchTouchId = (Switch) findViewById(R.id.switchTouchID);
        updateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                validate();
            }
        });

        setData();
    }

    private void setData()
    {
        updatedUser = new User();
        mUserRealm = Realm.getDefaultInstance();
        mUserRealm.executeTransaction(new Realm.Transaction()
        {
            @Override
            public void execute(Realm realm)
            {
                //Search for the user
                mUser = realm.where(User.class)
                        .equalTo("userEmail", mUserEmail)
                        .findFirst();

                if(mUser != null)
                {
                    updatedUser.setUserEmail(mUserEmail);
                    nameText.setText(mUser.getUserName());
                    emailText.setText("Email : " + mUser.getUserEmail());
                    mobileText.setText(mUser.getUserMobile());
                } else
                {
                    Toast.makeText(ProfileUpdateActivity.this, "Error!!! Retrieving User info", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void validate()
    {
        boolean valid = true;

        final String name = nameText.getText().toString();
        final String mobile = mobileText.getText().toString();
        String passwordOld = oldPasswordText.getText().toString();
        String passwordNew = passwordText.getText().toString();
        final String passwordCnew = confirmPasswordText.getText().toString();

        if(name.isEmpty() || name.length() < 3)
        {
            nameText.setError("At least 3 characters");
            valid = false;
        } else
        {
            updatedUser.setUserName(name);
            nameText.setError(null);
        }

        if(!mobile.isEmpty())
        {
            if(mobile.length() < 10)
            {
                mobileText.setError("At least 10 characters");
                valid = false;
            } else
            {
                updatedUser.setUserMobile(mobile);
                mobileText.setError(null);
            }
        }

        if(!passwordOld.isEmpty())
        {
            if(passwordOld.equals(mUser.getUserPassword()))
            {
                oldPasswordText.setError(null);
                if(passwordNew.isEmpty() || passwordNew.length() < 4 || passwordNew.length() > 10)
                {
                    passwordText.setError("Between 4 and 10 alphanumeric characters");
                    valid = false;
                } else
                {
                    passwordText.setError(null);
                    if(passwordCnew.isEmpty() || passwordCnew.length() < 4 || passwordCnew.length() > 10)
                    {
                        confirmPasswordText.setError("Password not matched");
                        valid = false;
                    } else
                    {
                        if(passwordNew.equals(passwordCnew))
                        {
                            updatedUser.setUserPassword(passwordNew);
                            confirmPasswordText.setError(null);
                        } else
                        {
                            valid = false;
                            confirmPasswordText.setError("Password not matched");
                        }
                    }
                }
            } else
            {
                oldPasswordText.setError("Old password incorrect");
                valid = false;
            }
        }
        else
        {
            updatedUser.setUserPassword(mUser.getUserPassword());
        }
        if(valid)
        {
            mUserRealm.executeTransaction(new Realm.Transaction()
            {
                @Override
                public void execute(Realm realm)
                {
                    mUserRealm.copyToRealmOrUpdate(updatedUser);
                    Toast.makeText(ProfileUpdateActivity.this, "User info updated successfully", Toast.LENGTH_SHORT).show();
                }
            });
        } else
        {
            Toast.makeText(ProfileUpdateActivity.this, "Error!!! while updating User info", Toast.LENGTH_SHORT).show();
        }
    }
}
