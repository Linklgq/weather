package com.example.lenovo.weather.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.lenovo.weather.BaseActivity;
import com.example.lenovo.weather.R;
import com.example.lenovo.weather.data.source.local.WeatherLocalSource;
import com.example.lenovo.weather.data.source.remote.WeatherRemoteSource;

public class WeatherActivity extends BaseActivity {
    private static final String WEATHER_ID="weather_id";

    public static void startWithWeatherId(Context context,String weatherId){
        Intent intent=new Intent(context,WeatherActivity.class);
        intent.putExtra(WEATHER_ID,weatherId);
        context.startActivity(intent);
    }

    WeatherContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        String weatherId=getIntent().getStringExtra(WEATHER_ID);
        WeatherFragment weatherFragment= (WeatherFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_weather);

        mPresenter=new WeatherPresenter(weatherId, WeatherLocalSource.getInstance(),
                WeatherRemoteSource.getInstance(),weatherFragment);

        Toolbar toolbar=findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
