package com.example.lenovo.weather.data.entity;

import android.arch.persistence.room.Entity;

@Entity(tableName = "province")
public class Province extends District{
    public Province(String name, int code) {
        super(name, code);
    }
}
