package com.example.lenovo.weather.weather;

import com.example.lenovo.weather.BasePresenter;
import com.example.lenovo.weather.BaseView;
import com.example.lenovo.weather.data.entity.gson.HeWeather;

public interface WeatherContract {
    interface Presenter extends BasePresenter{
        void saveStatus();
    }

    interface View extends BaseView<Presenter>{
        void showWeather(HeWeather heWeather);

        void showWaiting(boolean waiting);

        void showUpdating(boolean updating);

        void showError(String error);
    }

    interface LoadWeatherListener{
        void onSuccess(HeWeather heWeather,long updateTime);

        void onFailure();
    }

    interface RemoteModel{
        void queryWeather(String weatherId,LoadWeatherListener loadWeatherListener);
    }

    interface LocalModel extends RemoteModel{
        void syncWeather(String weatherId,HeWeather heWeather,long time);
    }
}
