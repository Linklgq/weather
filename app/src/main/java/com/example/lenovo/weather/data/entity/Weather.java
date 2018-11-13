package com.example.lenovo.weather.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "weather")
public class Weather {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public String weatherId;

    public String province;

    public String city;

    public String county;

    public String data;

    /*
    * 最后一次查询数据的时间
    * */
    public long updateTime;

    @Ignore
    public Weather(String weatherId, String province, String city, String county, String data, long updateTime) {
        this.weatherId = weatherId;
        this.province = province;
        this.city = city;
        this.county = county;
        this.data = data;
        this.updateTime = updateTime;
    }

    public Weather() {
    }
}
