package com.example.lenovo.weather.data.entity.gson;

public class AQI {
    public AQICity city;

    public static class AQICity{
        public String aqi;
        public String pm25;
        public String qlty;
    }
}
