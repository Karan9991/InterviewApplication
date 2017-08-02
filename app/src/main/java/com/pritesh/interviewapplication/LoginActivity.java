package com.pritesh.interviewapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pritesh.interviewapplication.data.User;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class LoginActivity extends Activity
{

    private static final String TAG = LoginActivity.class.getName();
    private static final int REQUEST_SIGNUP = 0;

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;

    private Realm realmUser;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);

        _loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login()
    {
        Log.d(TAG, "Login");

        if(!validate())
        {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable()
                {
                    public void run()
                    {
                        // On complete call either onLoginSuccess or onLoginFailed

                        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
                        Realm.deleteRealm(realmConfiguration);
                        realmUser = Realm.getInstance(realmConfiguration);
                        realmUser.executeTransaction(new Realm.Transaction()
                        {
                            @Override
                            public void execute(Realm realm) {
                                //Search for the user
                                User mUser = realm.where(User.class)
                                        .equalTo("userEmail", email)
                                        .equalTo("userPassword",password)
                                        .findFirst();
                                progressDialog.dismiss();
                                if(mUser != null)
                                {
                                    onLoginSuccess();
                                }
                                else
                                {
                                    onLoginFailed();
                                }
                            }
                        });

                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_SIGNUP)
        {
            if(resultCode == RESULT_OK)
            {
                //this.finish();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess()
    {
        _loginButton.setEnabled(true);
        Intent mIntent = new Intent(LoginActivity.this,RecipesListActivity.class);
        startActivity(mIntent);
        finish();
    }

    public void onLoginFailed()
    {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate()
    {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

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
    protected void onDestroy() {
        super.onDestroy();
        realmUser.close();
    }
}
