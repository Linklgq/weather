package com.example.lenovo.weather.data.entity;

import android.arch.persistence.room.Entity;

@Entity(tableName = "county")
public class County extends District{
    private String weatherId;
    private int cityCode;

    public County(String name, int code, String weatherId, int cityCode) {

        super(name, code);
        this.weatherId = weatherId;
        this.cityCode = cityCode;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }
}
