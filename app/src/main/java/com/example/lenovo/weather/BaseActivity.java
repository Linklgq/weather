package com.example.lenovo.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lenovo.weather.util.L;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d("onCreate: "+getClass().getName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.d("onStart: "+getClass().getName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d("onResume: "+getClass().getName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.d("onPause: "+getClass().getName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.d("onStop: "+getClass().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d("onDestroy: "+getClass().getName());
    }
}
