package com.example.lenovo.weather.district;

import android.support.annotation.NonNull;

import com.example.lenovo.weather.MyApplication;
import com.example.lenovo.weather.R;
import com.example.lenovo.weather.data.entity.City;
import com.example.lenovo.weather.data.entity.County;
import com.example.lenovo.weather.data.entity.District;
import com.example.lenovo.weather.data.entity.Province;

import java.util.ArrayList;
import java.util.List;

public class DistrictPresenter implements DistrictContract.Presenter{
    private static final int PROVINCE_LEVEL=0;
    private static final int CITY_LEVEL=1;
    private static final int COUNTY_LEVEL=2;

    private static final String COUNTRY= MyApplication.getContext().getString(R.string.country);

    private DistrictContract.Model mModel;
    private DistrictContract.View mView;

    private int mCurrentLevel;

    private Province mProvince;
    private City mCity;

    private List<District> mProvinceList;
    private List<District> mCityList;
    private List<District> mCountyList;

    public DistrictPresenter(@NonNull DistrictContract.Model model,
                             @NonNull DistrictContract.View view) {
        mModel = model;
        mView = view;
    }

    @Override
    public void select(int position) {
        switch(mCurrentLevel){
            case PROVINCE_LEVEL:{
                mCurrentLevel=CITY_LEVEL;
                mProvince=(Province) mProvinceList.get(position);
                mView.showWaiting(true);
                mModel.queryCities(mProvince.getCode(), new DistrictContract.QueryListener() {
                    @Override
                    public <T extends District> void onSuccess(List<T> list) {
                        mCityList=new ArrayList<>();
                        for(T t:list)
                            mCityList.add(t);
                        mView.showWaiting(false);
                        mView.showDistricts(mProvince.getName(),stringList(mCityList));
                    }

                    @Override
                    public void onFailure(String failMessage) {
                        mView.showWaiting(false);
                        mView.showError(failMessage);
                    }
                });
                break;
            }
            case CITY_LEVEL:{
                mCurrentLevel=COUNTY_LEVEL;
                mCity=(City) mCityList.get(position);
                mView.showWaiting(true);
                mModel.queryCounties(mProvince.getCode(),mCity.getCode(), new DistrictContract.QueryListener() {
                    @Override
                    public <T extends District> void onSuccess(List<T> list) {
                        mCountyList=new ArrayList<>();
                        for(T t:list)
                            mCountyList.add(t);
                        mView.showWaiting(false);
                        mView.showDistricts(mCity.getName(),stringList(mCountyList));
                    }

                    @Override
                    public void onFailure(String failMessage) {
                        mView.showWaiting(false);
                        mView.showError(failMessage);
                    }
                });
                break;
            }
            case COUNTY_LEVEL:{
                County county=(County) mCountyList.get(position);
                mView.showWeather(county.getWeatherId());
                mView.destroyView();
                break;
            }
        }
    }

    @Override
    public void back() {
        switch(mCurrentLevel){
            case PROVINCE_LEVEL:
                mView.destroyView();
                break;
            case CITY_LEVEL:
                mCurrentLevel=PROVINCE_LEVEL;
                mView.showDistricts(COUNTRY,stringList(mProvinceList));
                break;
            case COUNTY_LEVEL:
                mCurrentLevel=CITY_LEVEL;
                mView.showDistricts(mProvince.getName(),stringList(mCityList));
                break;
        }
    }

    @Override
    public void start() {
        mCurrentLevel=PROVINCE_LEVEL;
        mView.showWaiting(true);
        mModel.queryProvinces(new DistrictContract.QueryListener() {
            @Override
            public <T extends District> void onSuccess(List<T> list) {
                mProvinceList=new ArrayList<>();
                for(T t:list)
                    mProvinceList.add(t);
                mView.showWaiting(false);
                mView.showDistricts(COUNTRY,stringList(mProvinceList));
            }

            @Override
            public void onFailure(String failMessage) {
                mView.showWaiting(false);
                mView.showError(failMessage);
            }
        });
    }

    private <T extends District> List<String> stringList(List<T> list){
        List<String> result=new ArrayList<>();
        for(T t:list)
            result.add(t.getName());
        return result;
    }
}
