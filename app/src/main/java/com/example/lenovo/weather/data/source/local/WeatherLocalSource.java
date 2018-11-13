package com.example.lenovo.weather.data.source.local;

import com.example.lenovo.weather.data.entity.City;
import com.example.lenovo.weather.data.entity.County;
import com.example.lenovo.weather.data.entity.Province;
import com.example.lenovo.weather.data.entity.Weather;
import com.example.lenovo.weather.data.entity.gson.HeWeather;
import com.example.lenovo.weather.util.AppExecutors;
import com.example.lenovo.weather.weather.WeatherContract;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class WeatherLocalSource implements WeatherContract.LocalModel {
    private WeatherDao mWeatherDao;
    private DistrictDao mDistrictDao;

    private AppExecutors mAppExecutors;

    private WeatherLocalSource(){
        mWeatherDao=MyDatabase.getInstance().weatherDao();
        mDistrictDao=MyDatabase.getInstance().districtDao();
        mAppExecutors=new AppExecutors();
    }

    private static volatile WeatherLocalSource sInstance;

    public static WeatherLocalSource getInstance(){
        if(sInstance==null){
            synchronized (WeatherLocalSource.class){
                if(sInstance==null)
                    sInstance=new WeatherLocalSource();
            }
        }
        return sInstance;
    }



    @Override
    public void queryWeather(String weatherId, WeatherContract.LoadWeatherListener loadWeatherListener) {
        mAppExecutors.diskIO().execute(()->{
            Weather weather=mWeatherDao.queryWeather(weatherId);
            if(weather!=null){
                Gson gson=new Gson();
                try {
                    HeWeather heWeather= gson.fromJson(weather.data,HeWeather.class);
                    mAppExecutors.UIThread().execute(()->
                            loadWeatherListener.onSuccess(heWeather,weather.updateTime));
                } catch (JsonSyntaxException e) {
                    mAppExecutors.UIThread().execute(()-> loadWeatherListener.onFailure());
                }
            }else
                mAppExecutors.UIThread().execute(()->loadWeatherListener.onFailure());
        });
    }

    @Override
    public void syncWeather(String weatherId, HeWeather heWeather, long time) {
        mAppExecutors.diskIO().execute(()->{
            Weather weather=mWeatherDao.queryWeather(weatherId);
            String data=new Gson().toJson(heWeather);
            if(weather==null){  // 插入新的地区天气数据
                County county=mDistrictDao.queryCountyByWeatherId(weatherId);
                City city=mDistrictDao.queryCityByCode(county.getCityCode());
                Province province=mDistrictDao.queryProvinceByCode(city.getProvinceCode());
                weather=new Weather(weatherId,province.getName(),city.getName(),county.getName(),
                        data,time);
                mWeatherDao.insertWeather(weather);
            }else{  // 更新地区天气数据
                weather.data=data;
                weather.updateTime=time;
                mWeatherDao.updateWeather(weather);
            }
        });
    }
}
