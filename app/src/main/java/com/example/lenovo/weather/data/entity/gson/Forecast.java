package com.example.lenovo.weather.data.entity.gson;

import com.google.gson.annotations.SerializedName;

public class Forecast {
    public String date;

    @SerializedName(value = "cond")
    public WeatherType type;

    @SerializedName(value = "tmp")
    public Temperature temperature;

    public static class WeatherType{
        @SerializedName(value = "txt_d")
        public String info;
    }

    public static class Temperature{
        public String max;
        public String min;
    }
}
