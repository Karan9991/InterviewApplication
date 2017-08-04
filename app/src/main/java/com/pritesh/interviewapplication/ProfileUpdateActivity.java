package com.pritesh.interviewapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    User mUser;

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
                    nameText.setText(mUser.getUserName());
                    emailText.setText("Email : " + mUser.getUserEmail());
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
        if(valid)
        {
            mUserRealm.executeTransaction(new Realm.Transaction()
            {
                @Override
                public void execute(Realm realm)
                {
                    mUser.setUserName(name);
                    mUser.setUserMobile(mobile);
                    if(!passwordCnew.isEmpty())
                    {
                        mUser.setUserPassword(passwordCnew);
                    }
                    mUserRealm.copyToRealmOrUpdate(mUser);
                    Toast.makeText(ProfileUpdateActivity.this, "User info updated successfully", Toast.LENGTH_SHORT).show();
                }
            });
        } else
        {
            Toast.makeText(ProfileUpdateActivity.this, "Error!!! while updating User info", Toast.LENGTH_SHORT).show();
        }
    }
}
