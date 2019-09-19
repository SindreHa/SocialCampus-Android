package com.example.socialcampus.ui.home;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import com.example.socialcampus.R;

public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, com.example.socialcampus.MainActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}