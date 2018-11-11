package com.example.lenovo.weather.data.source.local;

import com.example.lenovo.weather.data.entity.City;
import com.example.lenovo.weather.data.entity.County;
import com.example.lenovo.weather.data.entity.Province;
import com.example.lenovo.weather.data.source.DistrictRepository;
import com.example.lenovo.weather.district.DistrictContract;
import com.example.lenovo.weather.util.AppExecutors;

import java.util.List;

public class DistrictLocalSource implements DistrictRepository.LocalModel{
    private DistrictDao mDistrictDao;

    private AppExecutors mAppExecutors;

    private DistrictLocalSource(){
        mDistrictDao=DistrictDatabase.getInstance().districtDao();
        mAppExecutors=new AppExecutors();
    }

//    private static volatile DistrictLocalSource sInstance;
//
//    public static DistrictLocalSource getInstance(){
//        if(sInstance==null){
//            synchronized (DistrictLocalSource.class){
//                if(sInstance==null){
//                    sInstance=new DistrictLocalSource();
//                }
//            }
//        }
//        return sInstance;
//    }

    public static DistrictLocalSource getInstance(){
        return new DistrictLocalSource();
    }

    @Override
    public void queryProvinces(DistrictContract.QueryListener queryListener) {
        Runnable runnable=()->{
            List<Province> list=mDistrictDao.queryProvinces();
            if(list.isEmpty())
                mAppExecutors.UIThread().execute(()->queryListener.onFailure(""));
            else
                mAppExecutors.UIThread().execute(()->queryListener.onSuccess(list));
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void queryCities(int provinceCode, DistrictContract.QueryListener queryListener) {
        Runnable runnable=()->{
            List<City> list=mDistrictDao.queryCities(provinceCode);
            if(list.isEmpty())
                mAppExecutors.UIThread().execute(()->queryListener.onFailure(""));
            else
                mAppExecutors.UIThread().execute(()->queryListener.onSuccess(list));
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void queryCounties(int provinceCode, int cityCode, DistrictContract.QueryListener queryListener) {
        Runnable runnable=()->{
            List<County> list=mDistrictDao.queryCounties(cityCode);
            if(list.isEmpty())
                mAppExecutors.UIThread().execute(()->queryListener.onFailure(""));
            else
                mAppExecutors.UIThread().execute(()->queryListener.onSuccess(list));
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void syncProvinces(List<Province> provinceList) {
        mAppExecutors.diskIO().execute(()-> mDistrictDao.insertProvinces(provinceList));

    }

    @Override
    public void syncCities(List<City> cityList) {
        mAppExecutors.diskIO().execute(()-> mDistrictDao.insertCities(cityList));
    }

    @Override
    public void syncCounties(List<County> countyList) {
        mAppExecutors.diskIO().execute(()->mDistrictDao.insertCounties(countyList));
    }
}
