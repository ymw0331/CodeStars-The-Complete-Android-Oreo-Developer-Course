package com.wayneyong.twittercloneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {


    public void redirectUser() {
        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
            startActivity(intent);

        }
    }


    public void signupLogin(View view) {
        EditText usernameEditText = findViewById(R.id.usenameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEdiText);

        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (e == null) {
                    Log.e("Login", "Login Success!");
                    Toast.makeText(MainActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                    redirectUser();

                } else {
                    //first time user
                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(usernameEditText.getText().toString());
                    newUser.setPassword(passwordEditText.getText().toString());

                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.e("SignUp", "Signup success!");
                                Toast.makeText(MainActivity.this, "Signup Success!", Toast.LENGTH_SHORT).show();
                                redirectUser();

                            } else {

                                Log.e("SignUp", e.getMessage().substring(e.getMessage().indexOf(" ")));
                                Toast.makeText(MainActivity.this, e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Twitter: Login");

        redirectUser();

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}