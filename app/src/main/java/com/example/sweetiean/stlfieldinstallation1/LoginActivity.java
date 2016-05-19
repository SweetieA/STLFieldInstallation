package com.example.sweetiean.stlfieldinstallation1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity {

    EditText username, password;
    TextView signup;
    Button login;
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {

        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        login = (Button) findViewById(R.id.loginButton);
        signup = (TextView) findViewById(R.id.signUpTextView);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                signup.setTextColor(Color.MAGENTA);
            }
        });
    }

    public void appLogin(View v) {

        String getUserName = username.getText().toString();
        String getPassword = SignupActivity.md5(password.getText().toString());
        FieldInstallationDB user_record = new FieldInstallationDB(this);
        user_record.openForRead();

        String storedPassword = user_record.getSinlgeEntry(getUserName);

        if(getPassword.equals(storedPassword))
        {
            Toast.makeText(LoginActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

            password.setText("");

        }
        else
        {
            Toast.makeText(LoginActivity.this, "User Name and Password does not match", Toast.LENGTH_LONG).show();
            counter--;
            Toast.makeText(LoginActivity.this, "You have " + counter + " trials more", Toast.LENGTH_LONG).show();

            if (counter == 0) {
                login.setEnabled(false);
                Toast.makeText(LoginActivity.this, "Login Button disabled, close the app and start again.", Toast.LENGTH_LONG).show();
            }
        }
        user_record.close();
    }

}
