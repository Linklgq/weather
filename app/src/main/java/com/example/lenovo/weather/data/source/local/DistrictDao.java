package com.example.lenovo.weather.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.lenovo.weather.data.entity.City;
import com.example.lenovo.weather.data.entity.County;
import com.example.lenovo.weather.data.entity.Province;

import java.util.List;

@Dao
public interface DistrictDao {
    @Query("SELECT * FROM province")
    List<Province> queryProvinces();

    @Query("SELECT * FROM city WHERE provinceCode = :provinceCode")
    List<City> queryCities(int provinceCode);

    @Query("SELECT * FROM county WHERE cityCode = :cityCode")
    List<County> queryCounties(int cityCode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProvinces(List<Province> provinceList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCities(List<City> cityList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCounties(List<County> countyList);
}
