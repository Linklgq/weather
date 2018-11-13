package com.example.lenovo.weather.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.example.lenovo.weather.MyApplication;
import com.example.lenovo.weather.data.entity.City;
import com.example.lenovo.weather.data.entity.County;
import com.example.lenovo.weather.data.entity.Province;
import com.example.lenovo.weather.data.entity.Weather;

@Database(entities = {Province.class, City.class, County.class, Weather.class},version = 1,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    private static volatile MyDatabase sInstance;

    public static MyDatabase getInstance(){
        if(sInstance==null){
            synchronized (MyDatabase.class){
                if(sInstance==null){
                    sInstance= Room.databaseBuilder(MyApplication.getContext(),
                            MyDatabase.class,"weather.db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return sInstance;
    }

    public abstract DistrictDao districtDao();

    public abstract WeatherDao weatherDao();
}
