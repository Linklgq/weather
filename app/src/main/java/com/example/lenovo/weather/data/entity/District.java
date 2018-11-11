package com.example.lenovo.weather.data.entity;

import android.arch.persistence.room.PrimaryKey;

public class District {
    protected String name;

    @PrimaryKey
    protected int code;

    public District(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
