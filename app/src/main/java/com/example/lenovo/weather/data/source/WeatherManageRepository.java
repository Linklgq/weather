package com.example.lenovo.weather.data.source;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.weather.MyApplication;
import com.example.lenovo.weather.R;
import com.example.lenovo.weather.data.entity.City;
import com.example.lenovo.weather.data.entity.County;
import com.example.lenovo.weather.data.entity.ManageItem;
import com.example.lenovo.weather.data.entity.Province;
import com.example.lenovo.weather.data.entity.Weather;
import com.example.lenovo.weather.data.entity.gson.HeWeather;
import com.example.lenovo.weather.data.source.local.DistrictDao;
import com.example.lenovo.weather.data.source.local.MyDatabase;
import com.example.lenovo.weather.data.source.local.WeatherDao;
import com.example.lenovo.weather.manage.ManageContract;
import com.example.lenovo.weather.util.AppExecutors;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherManageRepository implements ManageContract.Model{
    private static final String WEATHER_URL="http://guolin.tech/api/weather?cityid=";
    private static final String WEATHER_KEY= MyApplication.getContext().getString(R.string.weather_api_key);

    private static final String WEATHER_EXIST=MyApplication.getContext().getString(R.string.weather_item_exist);
    private static final String WEATHER_ERROR=MyApplication.getContext().getString(R.string.weather_item_error);

    private final WeatherDao mWeatherDao;
    private final DistrictDao mDistrictDao;

    private final AppExecutors mAppExecutors;

    private final RequestQueue mRequestQueue;

    private WeatherManageRepository(){
        mWeatherDao= MyDatabase.getInstance().weatherDao();
        mDistrictDao=MyDatabase.getInstance().districtDao();
        mAppExecutors=new AppExecutors();

        mRequestQueue= Volley.newRequestQueue(MyApplication.getContext());
    }

    private static volatile WeatherManageRepository sInstance;

    public static WeatherManageRepository getInstance(){
        if(sInstance==null){
            synchronized (WeatherManageRepository.class){
                if(sInstance==null)
                    sInstance=new WeatherManageRepository();
            }
        }
        return sInstance;
    }


    @Override
    public void queryDistrictsWeather(ManageContract.LoadManageItemListener loadManageItemListener) {
        mAppExecutors.diskIO().execute(()->{
            List<Weather> weatherList=mWeatherDao.queryAllWeather();
            if(weatherList==null)
                mAppExecutors.UIThread().execute(()->loadManageItemListener.onFailure());
            else {
                List<ManageItem> list = new ArrayList<>();
                for (Weather w : weatherList)
                    list.add(manageItem(w));
                mAppExecutors.UIThread().execute(()->
                    loadManageItemListener.onSuccess(list));
            }
        });
    }

    @Override
    public void addWeather(String weatherId, ManageContract.WeatherListener addWeatherListener) {
        mAppExecutors.diskIO().execute(()->{
            Weather weather=mWeatherDao.queryWeather(weatherId);
            if(weather!=null)
                mAppExecutors.UIThread().execute(()->addWeatherListener.onFailure(WEATHER_EXIST));
            else
                queryFromRemoteAndSync(weatherId,addWeatherListener);
        });
    }

    private ManageItem manageItem(Weather weather) throws JsonSyntaxException {
        HeWeather heWeather=new Gson().fromJson(weather.data,HeWeather.class);
        ManageItem item=new ManageItem(weather.weatherId,weather.province,weather.city,
                weather.county,heWeather.now.temperature,heWeather.now.type.info);
        return item;
    }

    private void queryFromRemoteAndSync(String weatherId,ManageContract.WeatherListener addWeatherListener){
        StringRequest request=new StringRequest(weatherURL(weatherId), response -> {
            try {
                JSONObject jsonObject=new JSONObject(response);
                JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
                String data=jsonArray.getJSONObject(0).toString();

                County county=mDistrictDao.queryCountyByWeatherId(weatherId);
                City city=mDistrictDao.queryCityByCode(county.getCityCode());
                Province province=mDistrictDao.queryProvinceByCode(city.getProvinceCode());
                Weather weather=new Weather(weatherId,province.getName(),city.getName(),county.getName(),
                        data,System.currentTimeMillis());
//                ManageItem item=manageItem(weather);
                // 插入到本地数据库
                mWeatherDao.insertWeather(weather);
                mAppExecutors.UIThread().execute(()->addWeatherListener.onSuccess());
            } catch (Exception e) {
                mAppExecutors.UIThread().execute(()-> addWeatherListener.onFailure(WEATHER_ERROR));
            }
        }, error -> mAppExecutors.UIThread().execute(()->
            addWeatherListener.onFailure(WEATHER_ERROR)));

        mRequestQueue.add(request);
    }

    @Override
    public void removeWeather(String weatherId, ManageContract.WeatherListener rmWeatherListener) {
        mAppExecutors.diskIO().execute(()->{
            mWeatherDao.deleteWeather(weatherId);
            mAppExecutors.UIThread().execute(()->rmWeatherListener.onSuccess());
        });
    }

    private String weatherURL(String weatherId){
        return WEATHER_URL+weatherId+"&key="+WEATHER_KEY;
    }
}
