package com.example.lenovo.weather.data.entity.gson;

import com.google.gson.annotations.SerializedName;

public class Basic {
    public String city;

    @SerializedName(value = "id")
    public String weatherId;

    public Update update;

    public static class Update{
        @SerializedName(value = "loc")
        public String time;
    }
}
