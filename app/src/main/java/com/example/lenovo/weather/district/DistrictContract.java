package com.example.lenovo.weather.district;

import android.support.annotation.NonNull;

import com.example.lenovo.weather.BasePresenter;
import com.example.lenovo.weather.BaseView;
import com.example.lenovo.weather.data.entity.District;

import java.util.List;

public interface DistrictContract {
    interface Presenter extends BasePresenter{
        void select(int position);

        void back();
    }

    interface View extends BaseView<Presenter>{
        void showDistricts(@NonNull String title,@NonNull List<String> list);

        void destroyView();

        void showWeather(String weatherId);

        void showWaiting(boolean wait);

        void showError(String error);
    }

    interface QueryListener{
        <T extends District> void onSuccess(List<T> list);

        void onFailure(String failMessage);
    }

    interface Model{
        void queryProvinces(QueryListener queryListener);

        void queryCities(int provinceCode,QueryListener queryListener);

        void queryCounties(int provinceCode,int cityCode,QueryListener queryListener);
    }
}
