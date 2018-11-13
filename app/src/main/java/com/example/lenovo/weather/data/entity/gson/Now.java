package com.example.lenovo.weather.data.entity.gson;

import com.google.gson.annotations.SerializedName;

public class Now {
    @SerializedName(value = "tmp")
    public String temperature;

    @SerializedName(value = "cond")
    public WeatherType type;

    public static class WeatherType{
        @SerializedName(value = "txt")
        public String info;
    }
}
