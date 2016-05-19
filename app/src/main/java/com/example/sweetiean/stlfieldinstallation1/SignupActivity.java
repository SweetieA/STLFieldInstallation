package com.example.sweetiean.stlfieldinstallation1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignupActivity extends ActionBarActivity {

    EditText username, password, confirmPassword;
    String setUsername, setPass, confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
    }

    private void init() {
        username = (EditText) findViewById(R.id.setUsernameEditText);
        password = (EditText) findViewById(R.id.setPasswordEditText);
        confirmPassword = (EditText) findViewById(R.id.confirmPassEditText);
    }

    public void appSignup(View view) {

        setUsername = username.getText().toString();
        setPass = password.getText().toString();
        confirmPass = confirmPassword.getText().toString();

        if(setUsername.length()==0 || setPass.length()==0 || confirmPass.length()==0){

            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_LONG).show();

        }else if(!setPass.equals(confirmPass)){

            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show();

        }else{

            //call hashing method and call method to save database
            insertUserData();
            Intent i = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }

    public void returnToLogin(View view) {

        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(i);
    }

    public static String md5(String password) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void insertUserData(){

        String sql_username = setUsername;
        String sql_password = md5(setPass);

        FieldInstallationDB user_record = new FieldInstallationDB(this);
        user_record.openForRead();
        user_record.createUserRecord(sql_username, sql_password);
        user_record.close();

    }
}
