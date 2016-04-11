package com.example.sweetiean.stlfieldinstallation1;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class DataViewActyivity extends ActionBarActivity {
    private ArrayList<String> EquipmentListArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view_actyivity);

        final TextView dvTsysAid = (TextView) findViewById(R.id.dvSysaidId);
        final TextView dvTcustomer = (TextView) findViewById(R.id.dvCustId);
        final TextView dvTEngineer = (TextView) findViewById(R.id.dvEngineer);
        final TextView dvInstType = (TextView) findViewById(R.id.dvInstType);
        ListView equipmentList = (ListView) findViewById(R.id.dvEquipmentListView);


        // Defined Array values to show in ListView
        String[] Equipmetvalues = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String sisaidId = extras.getString("DATA_SYSAID_ID");
            FieldInstallationDB site_info = new FieldInstallationDB(DataViewActyivity.this);
            //Equipmetvalues = site_info.getTaskDataArray(sisaidId);
        }

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < Equipmetvalues.length; ++i) {
            list.add(Equipmetvalues[i]);
        }
        final dataviewadapterClass adapter = new dataviewadapterClass(this,
                android.R.layout.simple_list_item_1, list);
        equipmentList.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_view_actyivity, menu);
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
