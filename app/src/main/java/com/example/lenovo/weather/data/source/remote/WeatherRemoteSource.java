package com.example.lenovo.weather.data.source.remote;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.weather.MyApplication;
import com.example.lenovo.weather.R;
import com.example.lenovo.weather.data.entity.gson.HeWeather;
import com.example.lenovo.weather.weather.WeatherContract;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherRemoteSource implements WeatherContract.RemoteModel {
    private static final String WEATHER_URL="http://guolin.tech/api/weather?cityid=";
    private static final String WEATHER_KEY= MyApplication.getContext().getString(R.string.weather_api_key);

    private RequestQueue mRequestQueue;

    private WeatherRemoteSource(){
        mRequestQueue= Volley.newRequestQueue(MyApplication.getContext());
    }

    public static WeatherRemoteSource getInstance(){
        return new WeatherRemoteSource();
    }

    @Override
    public void queryWeather(String weatherId, WeatherContract.LoadWeatherListener loadWeatherListener) {
        StringRequest request=new StringRequest(weatherURL(weatherId), response -> {
            try {
                JSONObject jsonObject=new JSONObject(response);
                JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
                String data=jsonArray.getJSONObject(0).toString();
                Gson gson=new Gson();
                HeWeather heWeather=gson.fromJson(data,HeWeather.class);
                loadWeatherListener.onSuccess(heWeather,System.currentTimeMillis());
            } catch (Exception e) {
                loadWeatherListener.onFailure();
            }
        }, error -> loadWeatherListener.onFailure());

        mRequestQueue.add(request);
    }

    private String weatherURL(String weatherId){
        return WEATHER_URL+weatherId+"&key="+WEATHER_KEY;
    }
}
