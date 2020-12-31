package com.application.detailschart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.application.detailschart.model.Stat;
import com.application.detailschart.model.StatData;
import com.application.detailschart.model.StatLocal;
import com.application.detailschart.viewmodel.StatViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String URL = "https://demo5636362.mockable.io/stats";
    private boolean dataLocallyAvailable = false;
    boolean doubleBackToExitPressedOnce = false;
    private StatViewModel statViewModel;
    private List<StatLocal> listData = new ArrayList<>();
    StatAdapter adapter;
    RecyclerView recyclerView;
    ImageView logout;
    public SharedPreferences.Editor loginPrefsEditor;
    public SharedPreferences loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        logout = findViewById(R.id.logout);
        statViewModel = new ViewModelProvider(this).get(StatViewModel.class);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        adapter = new StatAdapter(displayMetrics.widthPixels, displayMetrics.heightPixels);
        adapter.setContext(this);
        adapter.setList(listData);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);


        statViewModel.getAll().observe(this, new Observer<List<StatLocal>>() {
            @Override
            public void onChanged(List<StatLocal> statLocals) {

                if (statLocals != null) {
                    if (statLocals.size() > 0) {

                        if (statLocals.size() == 12) {
                            listData = statLocals;
                            Collections.sort(listData);
                            dataLocallyAvailable = true;
                            adapter.setList(listData);
                            adapter.notifyDataSetChanged();
                        } else
                            statViewModel.setDataUpdated(false);
                    }
                }


                if (isInternetAvailable()) {
                    if (!statViewModel.isDataUpdated())
                        requestData();
                } else {
                    if (!dataLocallyAvailable)
                        Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPreferences.edit().remove("mobileno").apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        new Handler().post(new Runnable() {
            @Override
            public void run() {

            }
        });

        Thread thread = new Thread() {
            @Override
            public void run() {
                statViewModel.initialise(getApplication());
                statViewModel.getData();
            }
        };

        thread.start();


    }

    private static final String TAG = "MainActivity";

    private void requestData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response == null) {
                    Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                } else {
                    Type type = TypeToken.get(StatData.class).getType();
                    StatData statData = new Gson().fromJson(response, type);
                    List<Stat> listStat = statData.getData();
                    updateCurrentList(listStat);


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null)
                    Log.d(TAG, error.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

    private void updateCurrentList(List<Stat> listStat) {
        List<StatLocal> listPrevious = new ArrayList<>(listData);
        if (dataLocallyAvailable) {
            statViewModel.deleteAll();
            listData = new ArrayList<>();
        }

        boolean valueChanged = false;
        for (int i = 0; i < listStat.size(); i++) {
            Stat stat = listStat.get(i);
            int priority = getPriority(stat.getMonth());

            StatLocal statLocal = new StatLocal();
            statLocal.setMonth(stat.getMonth());
            statLocal.setPriority(priority);
            statLocal.setStat(stat.getStat());

            statViewModel.insert(statLocal);
            listData.add(statLocal);


        }


        Collections.sort(listData);
        Collections.sort(listPrevious);

        if (listPrevious.size() > 0) {
            for (int i = 0; i < listPrevious.size(); i++) {
                StatLocal statPrev = listPrevious.get(i);
                StatLocal statNow = listData.get(i);
                if (statNow.getPriority() == statPrev.getPriority()) {
                    int p = Integer.parseInt(statPrev.getStat());
                    int q = Integer.parseInt(statNow.getStat());
                    if (p != q) {
                        valueChanged = true;
                        break;
                    }

                }
            }

        } else
            valueChanged = true;

        Collections.sort(listData);

        if (valueChanged)
            statViewModel.setList(listData);


        statViewModel.setDataUpdated(true);

    }

    private int getPriority(String month) {
        int value = -1;
        switch (month) {
            case "January":
                value = 1;
                break;

            case "February":
                value = 2;
                break;

            case "March":
                value = 3;
                break;

            case "April":
                value = 4;
                break;

            case "May":
                value = 5;
                break;

            case "June":
                value = 6;
                break;

            case "July":
                value = 7;
                break;

            case "August":
                value = 8;
                break;

            case "September":
                value = 9;
                break;

            case "October":
                value = 10;
                break;

            case "November":
                value = 11;
                break;

            case "December":
                value = 12;
                break;

            default:
                value = 0;
        }

        return value;
    }


    protected boolean isInternetAvailable() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();

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

}