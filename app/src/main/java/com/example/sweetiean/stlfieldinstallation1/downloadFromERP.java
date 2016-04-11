package com.example.sweetiean.stlfieldinstallation1;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by sweetiean on 2/29/2016.
 */
public class downloadFromERP extends AsyncTask<String, Integer, Void> {

    Context context;
    String result;


    public downloadFromERP(Context c){
        context = c;
    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            if(MainActivity.itemClicked.equals("customers")) {
                result = MainActivity.axcall.customers();
                MainActivity.jsonString = result;
            }
            if(MainActivity.itemClicked.equals("items")){
                result = MainActivity.axcall.equipment();
                MainActivity.jsonString = result;
            }
        }
        catch (Exception e){

            e.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if(MainActivity.itemClicked.equals("customers")) {
            MainActivity.jsonString = result;
            MainActivity.InsertCustIntoDB();
        }
        if(MainActivity.itemClicked.equals("items")){
            MainActivity.jsonString = result;
            MainActivity.InsertItemsIntoDB();
        }


    }


}
