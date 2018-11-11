package com.example.lenovo.weather.data.entity;

import android.arch.persistence.room.Entity;

@Entity(tableName = "city")
public class City extends District{
    private int provinceCode;

    public City(String name, int code, int provinceCode) {
        super(name, code);
        this.provinceCode = provinceCode;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
