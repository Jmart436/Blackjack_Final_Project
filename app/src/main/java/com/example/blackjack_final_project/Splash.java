package com.example.blackjack_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;



public class Splash extends AppCompatActivity {
    private static int splashTimeOut = 4000;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.blackjacksplash);

        new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent homeIntent = new Intent(Splash.this, MainActivity.class);
                    setContentView(R.layout.activity_main);
                    startActivity(homeIntent);
                    finish();
                }
            }, splashTimeOut);
        }
    }
