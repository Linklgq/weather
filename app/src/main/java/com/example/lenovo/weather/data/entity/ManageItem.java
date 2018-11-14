package com.example.lenovo.weather.data.entity;

public class ManageItem {
    public String weatherId;
    public String province;
    public String city;
    public String county;
    public String temperature;
    public String weatherType;

    public ManageItem(String weatherId, String province, String city, String county, String temperature, String weatherType) {
        this.weatherId = weatherId;
        this.province = province;
        this.city = city;
        this.county = county;
        this.temperature = temperature;
        this.weatherType = weatherType;
    }

    public ManageItem() {
    }
}
