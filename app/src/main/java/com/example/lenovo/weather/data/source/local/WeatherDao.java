package com.example.lenovo.weather.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lenovo.weather.data.entity.Weather;

@Dao
public interface WeatherDao {
    @Query("SELECT * FROM weather WHERE id = :weatherId")
    Weather queryWeather(String weatherId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeather(Weather weather);

    @Update
    void updateWeather(Weather weather);
}
