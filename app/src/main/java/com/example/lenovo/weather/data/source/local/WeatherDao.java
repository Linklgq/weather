package com.example.lenovo.weather.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lenovo.weather.data.entity.Weather;

import java.util.List;

@Dao
public interface WeatherDao {
    @Query("SELECT * FROM weather WHERE id = :weatherId")
    Weather queryWeather(String weatherId);

    @Query("SELECT * FROM weather")
    List<Weather> queryAllWeather();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeather(Weather weather);

    @Update
    void updateWeather(Weather weather);

    @Query("DELETE FROM weather WHERE id = :weatherId")
    void deleteWeather(String weatherId);
}
