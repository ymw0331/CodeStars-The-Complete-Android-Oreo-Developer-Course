package com.wayneyong.instagramcloneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    Boolean signUpModeActive = true;
    TextView logInTextView;
    EditText usernameEditText;
    EditText passwordEditText;

    public void showUserList() {
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

            signUpClicked(v);
        }
        return false;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.logInTextView) {
//            Log.e("Switch", "Was Tapped");

            Button signUpButton = findViewById(R.id.signUpButton);

            if (signUpModeActive) {
                signUpModeActive = false;
                signUpButton.setText("Login");
                logInTextView.setText("Click here to SignUp");
            } else {
                signUpModeActive = true;
                signUpButton.setText("Signup");
                logInTextView.setText("Click here to LogIn");
            }
        } else if (view.getId() == R.id.logoImageView || view.getId() == R.id.backgroundLayout) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void signUpClicked(View view) {

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);


        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {

            Toast.makeText(this, "Username and password is required", Toast.LENGTH_SHORT).show();
            Log.e("Signup", "Username and password is required");
        } else {
            if (signUpModeActive) {
                ParseUser user = new ParseUser();
                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("Signup", "Success");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                //Login
                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Log.i("Login", "ok");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Instagram");

        logInTextView = findViewById(R.id.logInTextView);
        logInTextView.setOnClickListener(this);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        ImageView logoImageView = findViewById(R.id.logoImageView);
        ConstraintLayout backgroundLayout = findViewById(R.id.backgroundLayout);
        logoImageView.setOnClickListener(this);
        backgroundLayout.setOnClickListener(this);

        passwordEditText.setOnKeyListener(this);

        //ParseUser.logOut will not log you out from the application, it keeps some record in cache. SO whenever you are checking
        if (ParseUser.getCurrentUser().getUsername() != null) { //check to see if the user has aldy loggin, showUserList directly
            showUserList();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

}