package com.example.lenovo.weather.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.example.lenovo.weather.MyApplication;
import com.example.lenovo.weather.data.entity.City;
import com.example.lenovo.weather.data.entity.County;
import com.example.lenovo.weather.data.entity.Province;

@Database(entities = {Province.class, City.class, County.class},version = 1,exportSchema = false)
public abstract class DistrictDatabase extends RoomDatabase {
    private static volatile DistrictDatabase sInstance;

    public static DistrictDatabase getInstance(){
        if(sInstance==null){
            synchronized (DistrictDatabase.class){
                if(sInstance==null){
                    sInstance= Room.databaseBuilder(MyApplication.getContext(),
                            DistrictDatabase.class,"district.db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return sInstance;
    }

    public abstract DistrictDao districtDao();
}
