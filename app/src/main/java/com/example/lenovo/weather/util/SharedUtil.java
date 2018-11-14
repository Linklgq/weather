package com.example.lenovo.weather.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lenovo.weather.MyApplication;

public class SharedUtil {
    private static final String NAME="weather_data";

    private static final String DEFAULT_STRING="";
    private static final int DEFAULT_INT=-1;
    private static final boolean DEFAULT_BOOLEAN=false;

    public static void putString(String key,String value){
        Context context=MyApplication.getContext();
        SharedPreferences.Editor editor= context.getSharedPreferences(NAME,
                context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static void putInt(String key,int value){
        Context context=MyApplication.getContext();
        SharedPreferences.Editor editor= context.getSharedPreferences(NAME,
                context.MODE_PRIVATE).edit();
        editor.putInt(key,value);
        editor.apply();
    }

    public static void putBoolean(String key,boolean value){
        Context context=MyApplication.getContext();
        SharedPreferences.Editor editor= context.getSharedPreferences(NAME,
                context.MODE_PRIVATE).edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public static String getString(String key){
        Context context=MyApplication.getContext();
        return context.getSharedPreferences(NAME, context.MODE_PRIVATE)
                .getString(key,DEFAULT_STRING);
    }

    public static int getInt(String key){
        Context context=MyApplication.getContext();
        return context.getSharedPreferences(NAME, context.MODE_PRIVATE)
                .getInt(key,DEFAULT_INT);
    }

    public static boolean getBoolean(String key){
        Context context=MyApplication.getContext();
        return context.getSharedPreferences(NAME, context.MODE_PRIVATE)
                .getBoolean(key,DEFAULT_BOOLEAN);
    }

    public static void removeString(String key){
        Context context=MyApplication.getContext();
        SharedPreferences.Editor editor= context.getSharedPreferences(NAME,
                context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    public static void removeInt(String key){
        Context context=MyApplication.getContext();
        SharedPreferences.Editor editor= context.getSharedPreferences(NAME,
                context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    public static void removeBoolean(String key){
        Context context=MyApplication.getContext();
        SharedPreferences.Editor editor= context.getSharedPreferences(NAME,
                context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }
}
