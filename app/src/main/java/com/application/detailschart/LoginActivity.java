package com.application.detailschart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private EditText mobile;
    private Button submit;
    boolean doubleBackToExitPressedOnce = false;
    ProgressBar p2;
    public SharedPreferences.Editor loginPrefsEditor;
    public SharedPreferences loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobile = findViewById(R.id.et_mobile);
        p2 = findViewById(R.id.progressbar);
        submit = findViewById(R.id.btn_submit);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                p2.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
                hideKeyboard(LoginActivity.this);

                final String mobileno = mobile.getText().toString().trim();

                if (mobileno.isEmpty() || mobile.length() < 10) {
                    mobile.setError("Enter a valid mobile number");
                    mobile.requestFocus();
                    p2.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                    return;
                }

                if (!isInternetAvailable()) {
                    Toast.makeText(LoginActivity.this, "No Internet!", Toast.LENGTH_SHORT).show();
                    p2.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                    return;
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(LoginActivity.this, VerifyActivity.class);
                        intent.putExtra("mobile", mobileno);
                        startActivity(intent);

                    }
                }, 2000);

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected boolean isInternetAvailable() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();

    }

    @Override
    protected void onResume() {
        super.onResume();
        p2.setVisibility(View.GONE);
        submit.setVisibility(View.VISIBLE);

    }
}