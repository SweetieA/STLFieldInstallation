package com.example.sweetiean.stlfieldinstallation1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by perjakobsen on 9/16/15.
 */
public class uploadToERP extends AsyncTask<String, Integer, Void> {

    Context context;
    String Info;
    String Survey;
    String SurveyTest;
    String Task;

    uploadToERP(Context context){
        this.context = context;
    }


    @Override
    protected Void doInBackground(String... params) {
            Boolean doNothing = false;

        try{

            if(MainActivity.checkInstallType.equals("testWEBservice")) {
                Info = MainActivity.axcall.hellowWorld();
                doNothing = true;
            }
            if(MainActivity.checkInstallType.equals("testERP")) {
                Info = MainActivity.axcall.TestERP("it");
                doNothing = true;
            }
            if(MainActivity.checkInstallType.equals("uploadfile")) {
                Info = MainActivity.axcall.SendFile(MainActivity.getBytesFromFile(),MainActivity.sysaidIdFileName+".pdf");
                doNothing = true;
            }

            //Info = MainActivity.axcall.pushToAx("fromTablet", "123xx456");

            if(!doNothing) {

                FieldInstallationDB getInfoRecord = new FieldInstallationDB(context);
                Info = getInfoRecord.getInfoRecord(MainActivity.sysaidIdFileName);

                if(MainActivity.checkInstallType.equals("Survey")) {
                    FieldInstallationDB getSurveyRecord = new FieldInstallationDB(context);
                    Survey = getSurveyRecord.getSurveyRecord(MainActivity.sysaidIdFileName);

                FieldInstallationDB getSurvTestRecord = new FieldInstallationDB(context);
                SurveyTest = getSurvTestRecord.getSurvTestRecord(MainActivity.sysaidIdFileName);

                }//end insert survey

                if(!(MainActivity.checkInstallType.equals("Survey"))) {
                    //FieldInstallationDB getTaskRecord = new FieldInstallationDB(context);
                    Task = getInfoRecord.getTaskRecord(MainActivity.sysaidIdFileName);
                }
            }//end insert installation



        }catch (Exception e){


            e.printStackTrace();


        }


        return null;
    }




    @Override
    protected void onPostExecute(Void aVoid) {

        Toast.makeText(context, Info, Toast.LENGTH_SHORT).show();

        if(MainActivity.checkInstallType.equals("uploadfile")){

            Toast.makeText(context, Info, Toast.LENGTH_SHORT).show();

        }else if(MainActivity.checkInstallType.equals("Survey")){

            Toast.makeText(context, Survey, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, SurveyTest, Toast.LENGTH_SHORT).show();

        }else if(MainActivity.checkInstallType.equals("Installation")){

            Toast.makeText(context, Task, Toast.LENGTH_SHORT).show();

        }

        //Toast.makeText(context, "Upload to ERP complete!!!", Toast.LENGTH_LONG).show();

    }


}
