package com.example.lenovo.weather.district;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lenovo.weather.BaseActivity;
import com.example.lenovo.weather.R;
import com.example.lenovo.weather.data.source.DistrictRepository;
import com.example.lenovo.weather.util.L;
import com.example.lenovo.weather.weather.WeatherActivity;

import java.util.ArrayList;
import java.util.List;

public class DistrictActivity extends BaseActivity implements DistrictContract.View{
    private static final String FOR_RESULT_KEY="for_result";
    public static final String WEATHER_ID_KEY="weatherId";

    public static void startForResult(Activity context, boolean forResult,int requestCode){
        Intent intent=new Intent(context,DistrictActivity.class);
        intent.putExtra(FOR_RESULT_KEY,forResult);
        context.startActivityForResult(intent,requestCode);
        L.d("startForResult: "+requestCode);
    }

    private DistrictContract.Presenter mPresenter;
    private boolean mForResult;

    private ListView mDistricts;
    private ArrayAdapter<String> mAdapter;
    private List<String> mDataList;

    private ProgressBar mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);

        if(getIntent()!=null)
            mForResult=getIntent().getBooleanExtra(FOR_RESULT_KEY,false);
        mPresenter=new DistrictPresenter(DistrictRepository.getInstance(),this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLoadingBar=findViewById(R.id.prg_loading);

        mDistricts=findViewById(R.id.lv_district);
        mDataList=new ArrayList<>();
        mAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mDataList);
        mDistricts.setAdapter(mAdapter);

        mDistricts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.select(position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.start();
    }

    @Override
    public void showDistricts(@NonNull String title, @NonNull List<String> list) {
        getSupportActionBar().setTitle(title);
        mDataList.clear();
        mDataList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void destroyView() {
        finish();
    }

    @Override
    public void showWeather(String weatherId) {
        if(mForResult){
            Intent intent=new Intent();
            intent.putExtra(WEATHER_ID_KEY,weatherId);
            setResult(RESULT_OK,intent);
            L.d("showWeather: "+"result ok");
        }else
            WeatherActivity.startWithWeatherId(this,weatherId);
    }

    @Override
    public void setPresenter(@NonNull DistrictContract.Presenter presenter) {
        // nothing
    }

    @Override
    public void showWaiting(boolean wait) {
        if(wait) {
            mLoadingBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else {
            mLoadingBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        mPresenter.back();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mPresenter.back();
                break;
        }
        return true;
    }
}
