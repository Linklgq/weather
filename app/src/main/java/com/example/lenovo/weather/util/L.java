package com.example.lenovo.weather.util;

import android.util.Log;

import com.example.lenovo.weather.MyApplication;
import com.example.lenovo.weather.R;

public class L {
    private static final String TAG= MyApplication.getContext().getString(R.string.app_name);

    private static final boolean DEBUG=true;

    public static void d(String msg){
        if(DEBUG){
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg){
        if(DEBUG){
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg){
        if(DEBUG){
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg){
        if(DEBUG){
            Log.e(TAG, msg);
        }
    }
}

