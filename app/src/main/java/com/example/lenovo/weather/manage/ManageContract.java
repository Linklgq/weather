package com.example.lenovo.weather.manage;

import com.example.lenovo.weather.BasePresenter;
import com.example.lenovo.weather.BaseView;
import com.example.lenovo.weather.data.entity.ManageItem;

import java.util.List;

public interface ManageContract {
    interface Presenter extends BasePresenter{
        void removeWeather(String weatherId);

        void addWeather(String weatherId);

        void showWeather(String weatherId);
    }

    interface View extends BaseView<Presenter>{
        void showDistrictsWeather(List<ManageItem> list);

        void destroyView();

        void showWeather(String weatherId);

        void showMessage(String message);

        void setWaiting(boolean waiting);
    }

    interface LoadManageItemListener{
        void onSuccess(List<ManageItem> list);

        void onFailure();
    }

    interface WeatherListener{
        void onSuccess();

        void onFailure(String failMessage);
    }

    interface Model{
        void queryDistrictsWeather(LoadManageItemListener loadManageItemListener);

        void addWeather(String weatherId,WeatherListener addWeatherListener);

        void removeWeather(String weatherId,WeatherListener rmWeatherListener);
    }
}
