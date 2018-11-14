package com.example.lenovo.weather.manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.lenovo.weather.R;
import com.example.lenovo.weather.data.source.WeatherManageRepository;
import com.example.lenovo.weather.district.DistrictActivity;
import com.example.lenovo.weather.util.SharedUtil;
import com.example.lenovo.weather.weather.WeatherActivity;

public class ManageActivity extends AppCompatActivity {
    private ManageContract.Presenter mPresenter;
    private ManageFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        mFragment= (ManageFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_manage);
        mPresenter=new ManagePresenter(WeatherManageRepository.getInstance(),mFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFragment.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onBackPressed() {
        String weatherId= SharedUtil.getString(getString(R.string.current_district_key));
        if(TextUtils.isEmpty(weatherId)) {  // 未选中地区
            Intent intent = new Intent(this, DistrictActivity.class);
            startActivity(intent);
        }else{  // 已有选中的地区
            WeatherActivity.startWithWeatherId(this,weatherId);
        }
        super.onBackPressed();
    }
}
