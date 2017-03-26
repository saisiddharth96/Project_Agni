package com.example.saisi.agni;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread welcomeThread = new Thread() {
        @Override
        public void run() {

            try {
                super.run();
                sleep(1500);  //Delay of 10 seconds
            } catch (Exception e) {

            } finally {

                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }
    };
        welcomeThread.start();
    }

}
