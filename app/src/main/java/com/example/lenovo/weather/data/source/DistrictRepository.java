package com.example.lenovo.weather.data.source;

import com.example.lenovo.weather.data.entity.City;
import com.example.lenovo.weather.data.entity.County;
import com.example.lenovo.weather.data.entity.District;
import com.example.lenovo.weather.data.entity.Province;
import com.example.lenovo.weather.data.source.local.DistrictLocalSource;
import com.example.lenovo.weather.data.source.remote.DistrictRemoteSource;
import com.example.lenovo.weather.district.DistrictContract;

import java.util.ArrayList;
import java.util.List;

public class DistrictRepository implements DistrictContract.Model {
    public interface LocalModel extends DistrictContract.Model{
        void syncProvinces(List<Province> provinceList);

        void syncCities(List<City> cityList);

        void syncCounties(List<County> countyList);
    }

    private final LocalModel mLocal;
    private final DistrictContract.Model mRemote;

    private DistrictRepository(){
        mLocal= DistrictLocalSource.getInstance();
        mRemote= DistrictRemoteSource.getInstance();
    }

    private static volatile DistrictRepository sInstance;

    public static DistrictRepository getInstance(){
        if(sInstance==null){
            synchronized (DistrictRepository.class){
                if(sInstance==null){
                    sInstance=new DistrictRepository();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void queryProvinces(final DistrictContract.QueryListener queryListener) {
        if(queryListener!=null){
            mLocal.queryProvinces(new DistrictContract.QueryListener() {
                @Override
                public <T extends District> void onSuccess(List<T> list) {
                    queryListener.onSuccess(list);
                }

                @Override
                public void onFailure(String failMessage) {
                    // 本地获取数据失败，通过网络获取
                    mRemote.queryProvinces(new DistrictContract.QueryListener() {
                        @Override
                        public <T extends District> void onSuccess(List<T> list) {
                            queryListener.onSuccess(list);
                            List<Province> provinceList=new ArrayList<>();
                            for(T t:list)
                                provinceList.add((Province)t);
                            mLocal.syncProvinces(provinceList);
                        }

                        @Override
                        public void onFailure(String failMessage) {
                            queryListener.onFailure(failMessage);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void queryCities(final int provinceCode, final DistrictContract.QueryListener queryListener) {
        if(queryListener!=null){
            mLocal.queryCities(provinceCode,new DistrictContract.QueryListener() {
                @Override
                public <T extends District> void onSuccess(List<T> list) {
                    queryListener.onSuccess(list);
                }

                @Override
                public void onFailure(String failMessage) {
                    // 本地获取数据失败，通过网络获取
                    mRemote.queryCities(provinceCode, new DistrictContract.QueryListener() {
                        @Override
                        public <T extends District> void onSuccess(List<T> list) {
                            queryListener.onSuccess(list);
                            List<City> cityList=new ArrayList<>();
                            for(T t:list)
                                cityList.add((City)t);
                            mLocal.syncCities(cityList);
                        }

                        @Override
                        public void onFailure(String failMessage) {
                            queryListener.onFailure(failMessage);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void queryCounties(final int provinceCode, final int cityCode, final DistrictContract.QueryListener queryListener) {
        if(queryListener!=null){
            mLocal.queryCounties(provinceCode, cityCode, new DistrictContract.QueryListener() {
                @Override
                public <T extends District> void onSuccess(List<T> list) {
                    queryListener.onSuccess(list);
                }

                @Override
                public void onFailure(String failMessage) {
                    // 本地获取数据失败，通过网络获取
                    mRemote.queryCounties(provinceCode, cityCode, new DistrictContract.QueryListener() {
                        @Override
                        public <T extends District> void onSuccess(List<T> list) {
                            queryListener.onSuccess(list);
                            List<County> countyList=new ArrayList<>();
                            for(T t:list)
                                countyList.add((County)t);
                            mLocal.syncCounties(countyList);
                        }

                        @Override
                        public void onFailure(String failMessage) {
                            queryListener.onFailure(failMessage);
                        }
                    });
                }
            });
        }
    }
}
