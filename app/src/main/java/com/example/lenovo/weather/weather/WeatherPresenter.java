package com.example.lenovo.weather.weather;

import com.example.lenovo.weather.data.entity.gson.HeWeather;

public class WeatherPresenter implements WeatherContract.Presenter {
    private WeatherContract.LocalModel mLocalModel;
    private WeatherContract.RemoteModel mRemoteModel;
    private WeatherContract.View mView;

    private String mWeatherId;

    private boolean mLatest=false;
    private boolean mLocalCache=false;

    public WeatherPresenter(String weatherId,WeatherContract.LocalModel localModel,
                            WeatherContract.RemoteModel remoteModel,WeatherContract.View view) {
        mWeatherId=weatherId;
        mLocalModel=localModel;
        mRemoteModel=remoteModel;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showWaiting(true);
        mLocalModel.queryWeather(mWeatherId, new WeatherContract.LoadWeatherListener() {
            @Override
            public void onSuccess(HeWeather heWeather, long updateTime) {
                mView.showWaiting(false);
                mView.showWeather(heWeather);

                mLatest=isLatest(updateTime);
                mLocalCache=true;
            }

            @Override
            public void onFailure() {
                mLocalCache=false;
            }
        });
        if(!mLatest){
            if(mLocalCache) mView.showUpdating(true);
            mRemoteModel.queryWeather(mWeatherId, new WeatherContract.LoadWeatherListener() {
                @Override
                public void onSuccess(HeWeather heWeather, long updateTime) {
                    if(mLocalCache) mView.showUpdating(false);
                    else mView.showWaiting(false);

                    mView.showWeather(heWeather);
                    mLocalModel.syncWeather(mWeatherId,heWeather,updateTime);
                    mLocalCache=true;
                }

                @Override
                public void onFailure() {
                    if(mLocalCache) mView.showUpdating(false);
                    else mView.showWaiting(false);

                    mView.showError("//todo");
                }
            });
        }
    }

    private boolean isLatest(long time){
        // TODO: 2018/11/12
        return false;
    }
}
