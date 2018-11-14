package com.example.lenovo.weather.manage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lenovo.weather.R;
import com.example.lenovo.weather.data.entity.ManageItem;
import com.example.lenovo.weather.district.DistrictActivity;
import com.example.lenovo.weather.util.L;
import com.example.lenovo.weather.weather.WeatherActivity;

import java.util.ArrayList;
import java.util.List;

public class ManageFragment extends Fragment implements ManageContract.View {
    private static final int DISTRICT_REQUEST=3;

    private ManageContract.Presenter mPresenter;

    private ProgressBar mLoadingBar;
    private RecyclerView mRecyclerView;
    private DistrictsWeatherAdapter mAdapter;
    private List<ManageItem> mList=new ArrayList<>();

    private FloatingActionButton mFab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_manage,container,false);

        mLoadingBar=root.findViewById(R.id.prg_loading);
        mRecyclerView=root.findViewById(R.id.rcv_district);
        mAdapter=new DistrictsWeatherAdapter(mList, new ItemListener() {
            @Override
            public void select(int position) {
                mPresenter.showWeather(mList.get(position).weatherId);
            }

            @Override
            public void remove(int position) {
                mPresenter.removeWeather(mList.get(position).weatherId);
            }
        },getActivity().getMenuInflater());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mFab=root.findViewById(R.id.fab_add_district);
        mFab.setOnClickListener(v->{
            DistrictActivity.startForResult(getActivity(),true,DISTRICT_REQUEST);
        });

        return root;
    }

    @Override
    public void showDistrictsWeather(List<ManageItem> list) {
        mList.clear();
        mList.addAll(list);
        L.d("showDistrictsWeather: "+list.size());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void destroyView() {
        getActivity().finish();
    }

    @Override
    public void showWeather(String weatherId) {
        WeatherActivity.startWithWeatherId(getActivity(),weatherId);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setWaiting(boolean waiting) {
        if(waiting) {
            mLoadingBar.setVisibility(View.VISIBLE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else {
            mLoadingBar.setVisibility(View.GONE);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void setPresenter(@NonNull ManageContract.Presenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        L.d("onActivityResult: "+"result out "+requestCode+" "+resultCode+
        " "+data.getStringExtra(DistrictActivity.WEATHER_ID_KEY));
        switch(requestCode){
            case DISTRICT_REQUEST:
                if(resultCode== Activity.RESULT_OK){
                    mPresenter.addWeather(data.getStringExtra(DistrictActivity.WEATHER_ID_KEY));
                    L.d("onActivityResult: "+"result");
                }
                break;
        }
    }

    public interface ItemListener{
        void select(int position);

        void remove(int position);
    }
}
