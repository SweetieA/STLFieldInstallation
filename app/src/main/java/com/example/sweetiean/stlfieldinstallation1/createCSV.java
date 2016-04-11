package com.example.sweetiean.stlfieldinstallation1;

/**
 *Created by perjakobsen on 9/11/15.
 */


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class createCSV {
    public createCSV(){}

    boolean createCSVFile(String _res)
    {



        try{
            String fpath = "/sdcard/" + MainActivity.DeviceId +"_"+ Utility.getTodaysDate() + ".csv";
            File file = new File(fpath);
            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }


            FileOutputStream fout = new FileOutputStream(file);
            fout.write(_res.getBytes());
            fout.close();

            return true;
            }
        catch (IOException e) {
                e.printStackTrace();
                return false;

            }


    }
}
