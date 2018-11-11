package com.example.lenovo.weather.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.lenovo.weather.R;

public class WeatherActivity extends AppCompatActivity {
    private static final String WEATHER_ID="weather_id";

    public static void startWithWeatherId(Context context,String weatherId){
        Intent intent=new Intent(context,WeatherActivity.class);
        intent.putExtra(WEATHER_ID,weatherId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }
}
