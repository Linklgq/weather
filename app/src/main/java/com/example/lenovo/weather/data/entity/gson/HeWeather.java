package com.example.lenovo.weather.data.entity.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeWeather {
    public String status;

    public Basic basic;

    public AQI aqi;

    public Now now;

    public Suggestion suggestion;

    @SerializedName(value = "daily_forecast")
    public List<Forecast> forecastList;
}
