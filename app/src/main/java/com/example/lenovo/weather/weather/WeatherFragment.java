package com.example.lenovo.weather.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.weather.R;
import com.example.lenovo.weather.customview.MyListView;
import com.example.lenovo.weather.data.entity.gson.Forecast;
import com.example.lenovo.weather.data.entity.gson.HeWeather;
import com.example.lenovo.weather.manage.ManageActivity;
import com.example.lenovo.weather.util.L;

import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment implements WeatherContract.View{
    private WeatherContract.Presenter mPresenter;

    private ProgressBar mLoadingBar;
    private ViewGroup mWeatherContent;
    private SwipeRefreshLayout mUpdateSL;

    private TextView mTemperature;
    private TextView mWeatherType;
    private TextView mDistrict;

    private MyListView mForecastLV;
    private List<Forecast> mForecastList;
    private ForecastAdapter mForecastAdapter;

    private TextView mQlty;
    private TextView mAQI;
    private TextView mPM25;

    private TextView mComfort;
    private TextView mCarWash;
    private TextView mSport;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_weather,container,false);

        mLoadingBar=root.findViewById(R.id.prg_loading);
        mWeatherContent=root.findViewById(R.id.ll_weather_content);
        mUpdateSL=root.findViewById(R.id.sl_update);
        mUpdateSL.setColorSchemeResources(R.color.colorAccent);
        mUpdateSL.setOnRefreshListener(()->{
            mPresenter.updateWeather();
        });

        mTemperature=root.findViewById(R.id.tv_tmp);
        mWeatherType=root.findViewById(R.id.tv_type);
        mDistrict=root.findViewById(R.id.tv_district);

        mForecastLV=root.findViewById(R.id.lv_forecast_list);
        mForecastList=new ArrayList<>();
        mForecastAdapter=new ForecastAdapter(getContext(),R.layout.forecast_item,mForecastList);
        mForecastLV.setAdapter(mForecastAdapter);

        mQlty=root.findViewById(R.id.tv_qlty);
        mAQI=root.findViewById(R.id.tv_aqi);
        mPM25=root.findViewById(R.id.tv_pm25);

        mComfort=root.findViewById(R.id.tv_comfort);
        mCarWash=root.findViewById(R.id.tv_carWash);
        mSport=root.findViewById(R.id.tv_sport);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void showWeather(HeWeather heWeather) {
        mTemperature.setText(heWeather.now.temperature);
        mWeatherType.setText(heWeather.now.type.info);
        mDistrict.setText(heWeather.basic.city);

        mForecastList.clear();
        mForecastList.addAll(heWeather.forecastList);
        mForecastAdapter.notifyDataSetChanged();

        mQlty.setText(heWeather.aqi.city.qlty);
        mAQI.setText(heWeather.aqi.city.aqi);
        mPM25.setText(heWeather.aqi.city.pm25);

        mComfort.setText(heWeather.suggestion.comfort.info);
        mCarWash.setText(heWeather.suggestion.carWash.info);
        mSport.setText(heWeather.suggestion.sport.info);
    }

    @Override
    public void showWaiting(boolean waiting) {
        if(waiting) {
            mWeatherContent.setVisibility(View.INVISIBLE);
            mLoadingBar.setVisibility(View.VISIBLE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else {
            mWeatherContent.setVisibility(View.VISIBLE);
            mLoadingBar.setVisibility(View.GONE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void setPresenter(@NonNull WeatherContract.Presenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUpdating(boolean updating) {
        if(!updating||!mUpdateSL.isRefreshing())
            mUpdateSL.setRefreshing(updating);
        L.d("showUpdating: update "+updating);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.saveStatus();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.weather_fragment_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                Intent intent=new Intent(getActivity(), ManageActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                break;
        }
        return true;
    }

    private static class ForecastAdapter extends ArrayAdapter<Forecast>{
        private int mResourceId;

        public ForecastAdapter(@NonNull Context context, int resource, @NonNull List<Forecast> objects) {
            super(context, resource, objects);
            mResourceId=resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Forecast forecast=getItem(position);
            View root=LayoutInflater.from(getContext()).inflate(mResourceId,parent,false);

            TextView date=root.findViewById(R.id.tv_forecast_date);
            TextView type=root.findViewById(R.id.tv_forecast_type);
            TextView tmpRange=root.findViewById(R.id.tv_forecast_tmpRange);
            date.setText(forecast.date);
            type.setText(forecast.type.info);
            tmpRange.setText(forecast.temperature.min+"℃ ~ "+forecast.temperature.max+"℃");

            return root;
        }
    }
}
