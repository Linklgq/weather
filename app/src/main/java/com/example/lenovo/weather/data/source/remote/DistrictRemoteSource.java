package com.example.lenovo.weather.data.source.remote;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.weather.MyApplication;
import com.example.lenovo.weather.R;
import com.example.lenovo.weather.data.entity.City;
import com.example.lenovo.weather.data.entity.County;
import com.example.lenovo.weather.data.entity.Province;
import com.example.lenovo.weather.district.DistrictContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DistrictRemoteSource implements DistrictContract.Model {
    private static final String URL_PROVINCE="http://guolin.tech/api/china";
    private static final String ERROR_GET_PROVINCES=
            MyApplication.getContext().getString(R.string.error_getProvinces);
    private static final String ERROR_GET_CITIES=
            MyApplication.getContext().getString(R.string.error_getCities);
    private static final String ERROR_GET_COUNTIES=
            MyApplication.getContext().getString(R.string.error_getCounties);

    private RequestQueue mRequestQueue;

    private DistrictRemoteSource(){
        mRequestQueue= Volley.newRequestQueue(MyApplication.getContext());
    }

    public static DistrictRemoteSource getInstance(){
        return new DistrictRemoteSource();
    }

    @Override
    public void queryProvinces(final DistrictContract.QueryListener queryListener) {
        StringRequest request=new StringRequest(provinceURL(), response -> {
            try {
                JSONArray array=new JSONArray(response);
                List<Province> list=new ArrayList<>();
                for(int i=0;i<array.length();i++){
                    JSONObject jsonObject=array.getJSONObject(i);
                    Province province=new Province(jsonObject.getString("name"),
                            jsonObject.getInt("id"));
                    list.add(province);
                }
                queryListener.onSuccess(list);
            } catch (JSONException e) {
                queryListener.onFailure(ERROR_GET_PROVINCES);
            }
        }, error -> queryListener.onFailure(ERROR_GET_PROVINCES));

        mRequestQueue.add(request);
    }

    @Override
    public void queryCities(final int provinceCode, final DistrictContract.QueryListener queryListener) {
        StringRequest request=new StringRequest(cityURL(provinceCode), response -> {
            try {
                JSONArray array=new JSONArray(response);
                List<City> list=new ArrayList<>();
                for(int i=0;i<array.length();i++){
                    JSONObject jsonObject=array.getJSONObject(i);
                    City city=new City(jsonObject.getString("name"),
                            jsonObject.getInt("id"), provinceCode);
                    list.add(city);
                }
                queryListener.onSuccess(list);
            } catch (JSONException e) {
                queryListener.onFailure(ERROR_GET_CITIES);
            }
        }, error -> queryListener.onFailure(ERROR_GET_CITIES));

        mRequestQueue.add(request);
    }

    @Override
    public void queryCounties(final int provinceCode, final int cityCode, final DistrictContract.QueryListener queryListener) {
        StringRequest request=new StringRequest(countyURL(provinceCode,cityCode), response -> {
            try {
                JSONArray array=new JSONArray(response);
                List<County> list=new ArrayList<>();
                for(int i=0;i<array.length();i++){
                    JSONObject jsonObject=array.getJSONObject(i);
                    County county=new County(jsonObject.getString("name"),
                            jsonObject.getInt("id"),
                            jsonObject.getString("weather_id"),cityCode);
                    list.add(county);
                }
                queryListener.onSuccess(list);
            } catch (JSONException e) {
                queryListener.onFailure(ERROR_GET_COUNTIES);
            }
        }, error -> queryListener.onFailure(ERROR_GET_COUNTIES));

        mRequestQueue.add(request);
    }

    private String provinceURL(){
        return URL_PROVINCE;
    }

    private String cityURL(int provinceCode){
        return URL_PROVINCE+"/"+provinceCode;
    }

    private String countyURL(int provinceCode,int cityCode){
        return URL_PROVINCE+"/"+provinceCode+"/"+cityCode;
    }
}
