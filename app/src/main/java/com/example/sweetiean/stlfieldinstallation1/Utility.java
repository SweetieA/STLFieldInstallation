package com.example.sweetiean.stlfieldinstallation1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

/**
 * Created by sweetiean on 1/21/2015.
 */
public class Utility {
//    Utility is the main role here. Before inserting the image and retrieving from the SQLite Database,
//    we use these methods:
//    getBytes() to convert bitmap image to byte array and return byte[]
//    getPhoto() to convert byte[] to bitmap and return bitmap image

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static String getTodaysDate() {

        final Calendar c = Calendar.getInstance();
        int yr = c.get(Calendar.YEAR);
        int mnth = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String m = String.valueOf(mnth);
        String d = String.valueOf(day);

        if (mnth <= 9) {
            m = "0" + m;
        }
        if (day <= 9) {
            d = "0" + d;
        }

        String date = d + "-" + m + "-" + String.valueOf(yr);

        return date;

    }

    /*public static String encodeTobase64(Bitmap image){
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }
    public static Bitmap decodeBase64(String input){
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }*/


}
