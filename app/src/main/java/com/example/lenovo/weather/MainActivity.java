package com.example.lenovo.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.lenovo.weather.district.DistrictActivity;
import com.example.lenovo.weather.util.SharedUtil;
import com.example.lenovo.weather.weather.WeatherActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String weatherId= SharedUtil.getString(getString(R.string.current_district_key));
        if(TextUtils.isEmpty(weatherId)) {  // 未选中地区
            Intent intent = new Intent(this, DistrictActivity.class);
            startActivity(intent);
        }else{  // 已有选中的地区
            WeatherActivity.startWithWeatherId(this,weatherId);
        }
        finish();
    }
}
