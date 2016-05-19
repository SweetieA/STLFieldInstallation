package com.example.sweetiean.stlfieldinstallation1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;


public class SplashActivity extends Activity {

    protected int _splashTime = 5000;
    private Thread splashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashThread = new Thread(){
            @Override
            public void run() {
                try{
                    synchronized (this){
                        wait(_splashTime);
                    }
                }catch (InterruptedException e){

                }finally {
                    finish();
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                }
            }
        };

        splashThread.start();
    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            synchronized (splashThread){
                splashThread.notifyAll();
            }
        }

        return  true;
    }



}
