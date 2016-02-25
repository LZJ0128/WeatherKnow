package com.lzj.weatherknow.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2/24 0024.
 * SharePreference帮助类
 */
public class SharePreferenceHelper {

    /**
     * 在本地保存int类型的SP
     * @param context
     * @param spName 文件名
     * @param key
     * @param value
     */
    public static void setIntSP(Context context, String spName, String key, int value){
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 得到intSP的value
     * @param context
     * @param spName 文件名
     * @param key
     * @param value
     * @return value
     */
    public static int getIntSP(Context context, String spName, String key, int value){
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getInt(key, value);
    }

    /**
     * 在本地保存String类型的SP
     * @param context
     * @param spName
     * @param key
     * @param value
     */
    public static void setStringSP(Context context, String spName, String key, String value){
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 得到StringSP的value
     * @param context
     * @param spName
     * @param key
     * @param value
     * @return
     */
    public static String getStringSP(Context context, String spName, String key, String value){
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    /**
     * 在本地保存boolean类型的SP
     * @param context
     * @param spName
     * @param key
     * @param value
     */
    public static void setBooleanSP(Context context, String spName, String key, boolean value){
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 得到booleanSP的value
     * @param context
     * @param spName
     * @param key
     * @param value
     * @return
     */
    public static boolean getBooleanSP(Context context, String spName, String key, boolean value){
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, value);
    }
}
