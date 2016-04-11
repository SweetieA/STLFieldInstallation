package com.example.sweetiean.stlfieldinstallation1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Setting extends Activity {

    EditText IPAdd;
    Button save;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String IP = "ipKey";
    SharedPreferences sharedpreferences;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        IPAdd = (EditText)findViewById(R.id.IPEditText);
        save = (Button)findViewById(R.id.IPSaveButton);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        IPAdd.setText(getValue());


    }


    public void save(View v){

        MainActivity.webaddress = IPAdd.getText().toString();


        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(IP, MainActivity.webaddress);

        editor.commit();
        Toast.makeText(Setting.this, "Address Saved", Toast.LENGTH_LONG).show();

    }


    public String getValue() {


        settings = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);;
        MainActivity.webaddress = settings.getString(IP, null);
        return MainActivity.webaddress;

    }


















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
