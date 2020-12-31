package com.application.detailschart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    public SharedPreferences.Editor loginPrefsEditor;
    public SharedPreferences loginPreferences;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loginPreferences.contains("mobileno")) {
                    Intent t1 = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(t1);
                    finish();
                } else {
                    Intent t1 = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(t1);
                    finish();
                }
            }
        }, 2000);
    }

}
