package com.example.lenovo.weather.manage;

import android.text.TextUtils;

import com.example.lenovo.weather.MyApplication;
import com.example.lenovo.weather.R;
import com.example.lenovo.weather.data.entity.ManageItem;
import com.example.lenovo.weather.util.L;
import com.example.lenovo.weather.util.SharedUtil;

import java.util.List;

public class ManagePresenter implements ManageContract.Presenter {
    private static final String CURRENT_WEATHER_KEY=MyApplication.getContext()
            .getString(R.string.current_district_key);

    private final ManageContract.Model mModel;
    private final ManageContract.View mView;

    public ManagePresenter(ManageContract.Model model, ManageContract.View view) {
        mModel = model;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void removeWeather(String weatherId) {
        mView.setWaiting(true);
        mModel.removeWeather(weatherId, new ManageContract.WeatherListener() {
            @Override
            public void onSuccess() {
                boolean newCache=false;
                if(weatherId.equals(SharedUtil.getString(CURRENT_WEATHER_KEY))){
                    SharedUtil.removeString(CURRENT_WEATHER_KEY);
                    newCache=true;
                }
                showUI(newCache);
            }

            @Override
            public void onFailure(String failMessage) {
                mView.setWaiting(false);
                L.d("onFailure: remove");
            }
        });
    }

    @Override
    public void addWeather(String weatherId) {
        mView.setWaiting(true);
        mModel.addWeather(weatherId, new ManageContract.WeatherListener() {
            @Override
            public void onSuccess() {
                boolean newCache=false;
                if(TextUtils.isEmpty(SharedUtil.getString(CURRENT_WEATHER_KEY)))
                    newCache=true;
                showUI(newCache);
            }

            @Override
            public void onFailure(String failMessage) {
                mView.setWaiting(false);
                mView.showMessage(failMessage);
            }
        });
    }

    @Override
    public void start() {
        mView.setWaiting(true);
        showUI(false);
    }

    @Override
    public void showWeather(String weatherId) {
        mView.showWeather(weatherId);
        mView.destroyView();
    }

    private void showUI(boolean newCache){
        mModel.queryDistrictsWeather(new ManageContract.LoadManageItemListener() {
            @Override
            public void onSuccess(List<ManageItem> list) {
                mView.setWaiting(false);
                mView.showDistrictsWeather(list);
                if(newCache&&!list.isEmpty())
                    SharedUtil.putString(CURRENT_WEATHER_KEY,list.get(0).weatherId);
            }

            @Override
            public void onFailure() {
                mView.setWaiting(false);
                L.d("onFailure: showUI");
            }
        });
    }
}
