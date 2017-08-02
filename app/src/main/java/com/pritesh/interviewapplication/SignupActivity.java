package com.pritesh.interviewapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pritesh.interviewapplication.data.User;

import io.realm.Realm;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class SignupActivity extends Activity
{
    private static final String TAG = SignupActivity.class.getName();
    EditText _nameText;
    EditText _emailText;
    EditText _passwordText;
    Button _signupButton;
    TextView _loginLink;

    private Realm realmUser;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        _nameText = (EditText) findViewById(R.id.input_name);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);

        _signupButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });

        //RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        //Realm.deleteRealm(realmConfiguration);
        //realmUser = Realm.getInstance(realmConfiguration);
        realmUser = Realm.getDefaultInstance();
    }

    public void signup()
    {
        Log.d(TAG, "Signup");

        if(!validate())
        {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = _nameText.getText().toString();
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable()
                {
                    public void run()
                    {
                        onSignupSuccess(name, email, password);
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess(final String name, final String email, final String password)
    {
        //Add new user record
        final User mUser = new User();
        mUser.setUserName(name);
        mUser.setUserEmail(email);
        mUser.setUserPassword(password);
        //realmUser.createObject(User.class, mUser);

        realmUser.executeTransaction(new Realm.Transaction()
        {
            @Override
            public void execute(Realm realm)
            {
                try
                {
                    // This will create a new object in Realm or throw an exception if the
                    // object already exists (same primary key)
                    realmUser.copyToRealm(mUser);

                    // This will update an existing object with the same primary key
                    // or create a new object if an object with no primary key
                    //realmUser.copyToRealmOrUpdate(mUser);
                } catch(RealmPrimaryKeyConstraintException | RealmException re)
                {
                    Toast.makeText(SignupActivity.this, re.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed()
    {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate()
    {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if(name.isEmpty() || name.length() < 3)
        {
            _nameText.setError("At least 3 characters");
            valid = false;
        } else
        {
            _nameText.setError(null);
        }

        if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            _emailText.setError("Enter a valid email address");
            valid = false;
        } else
        {
            _emailText.setError(null);
        }

        if(password.isEmpty() || password.length() < 4 || password.length() > 10)
        {
            _passwordText.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else
        {
            _passwordText.setError(null);
        }

        return valid;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        realmUser.close();
    }
}
