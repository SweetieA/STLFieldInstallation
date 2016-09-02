package com.example.sweetiean.stlfieldinstallation1;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by perjakobsen on 5/29/15.
 */
public class myGetTasktypeListner implements AdapterView.OnItemSelectedListener {



        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            parent.getContext();

            if (!(parent.getSelectedItem().toString().equals("Survey"))){

                MainActivity.task.setText(parent.getItemAtPosition(pos).toString());

            } else{
                MainActivity.task.setText("");
            }


            /*if (parent.getSelectedItem().toString().equals("Replacement")) {
                MainActivity.oldSerialNumber.setEnabled(true);
            } else {
                MainActivity.oldSerialNumber.setEnabled(false);
            }*/
        }


        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }



}
