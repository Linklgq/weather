package com.example.lenovo.weather.data.entity.gson;

import com.google.gson.annotations.SerializedName;

public class Suggestion {
    @SerializedName(value = "comf")
    public Comfort comfort;

    @SerializedName(value = "cw")
    public CarWash carWash;

    public Sport sport;


    public static class Comfort{
        @SerializedName(value = "txt")
        public String info;
    }

    public static class CarWash{
        @SerializedName(value = "txt")
        public String info;
    }

    public static class Sport{
        @SerializedName(value = "txt")
        public String info;
    }
}
