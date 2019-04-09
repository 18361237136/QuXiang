package com.example.core.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.core.GifFun;

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/8 下午2:34
 * Describe:SharedPreferences工具类，提供简单的封装接口，简化SharedPreferences的用法。
 */
public class ShareUtil {

    //存储bool类型的键值对到SharedPreference文件中
    public static void save(String key,boolean value){
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(GifFun.getContext()).edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    //存储float类型的键值对到SharedPreference文件中
    public static void save(String key,float value){
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(GifFun.getContext()).edit();
        editor.putFloat(key,value);
        editor.apply();
    }

    //存储int类型的键值对到SharedPreference文件中
    public static void save(String key,int value){
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(GifFun.getContext()).edit();
        editor.putInt(key,value);
        editor.apply();
    }

    //存储long类型的键值对到SharedPreference文件中
    public static void save(String key,long value){
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(GifFun.getContext()).edit();
        editor.putLong(key,value);
        editor.apply();
    }

    //存储String类型的键值对到SharedPreference文件中
    public static void save(String key,String value){
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(GifFun.getContext()).edit();
        editor.putString(key,value);
        editor.apply();
    }

    //从SharedPreference文件中读取boolean类型的值
    public static boolean read(String key,boolean defValue){
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(GifFun.getContext());
        return prefs.getBoolean(key,defValue);
    }

    //从SharedPreference文件中读取float类型的值
    public static float read(String key,float defValue){
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(GifFun.getContext());
        return prefs.getFloat(key,defValue);
    }

    //从SharedPreference文件中读取int类型的值
    public static int read(String key,int defValue){
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(GifFun.getContext());
        return prefs.getInt(key,defValue);
    }

    //从SharedPreference文件中读取long类型的值
    public static long read(String key,long defValue){
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(GifFun.getContext());
        return prefs.getLong(key,defValue);
    }

    //从SharedPreference文件中读取String类型的值
    public static String read(String key,String defValue){
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(GifFun.getContext());
        return prefs.getString(key,defValue);
    }

    //判断SharedPreferences文件当中是否包含指定的键值。
    public static boolean contains(String key){
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(GifFun.getContext());
        return prefs.contains(key);
    }

    // 清理SharedPreferences文件当中传入键所对应的值。
    public static void clear(String key){
        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(GifFun.getContext()).edit();
        editor.remove(key);
        editor.apply();
    }

    //将SharedPreferences文件中存储的所有值清除。
    public static void clearAll(){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                GifFun.getContext()).edit();
        editor.clear();
        editor.apply();
    }
}
