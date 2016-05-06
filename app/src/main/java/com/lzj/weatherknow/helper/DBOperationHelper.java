package com.lzj.weatherknow.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lzj.weatherknow.entity.CityEntity;
import com.lzj.weatherknow.entity.WeatherEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/17 0017.
 * 数据库相关操作帮助类
 */
public class DBOperationHelper {
    public static final String DB_NAME = "city_list";//数据库名
    public static final String TABLE_CITY = "cities";//表名
    public static final String TABLE_WEATHER = "weathers";
    public static final int VERSION = 1;//版本号
    private SQLiteDatabase mDB;
    private static DBOperationHelper mDBOperation;

    /**
     * 构造方法
     * @param context
     */
    private DBOperationHelper(Context context){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, VERSION);
        mDB = dbOpenHelper.getWritableDatabase();
    }

    /**
     * 一个实例
     * @param context
     * @return
     */
    public synchronized static DBOperationHelper getInstance(Context context){
        if (mDBOperation == null){
            mDBOperation = new DBOperationHelper(context);
        }
        return mDBOperation;
    }

    /**
     * 将所有城市名字和ID保存到本地
     * @param cityEntity
     */
    public void saveCity(CityEntity cityEntity){
        if (cityEntity != null){
            ContentValues cv = new ContentValues();
            cv.put("city_name", cityEntity.getCityName());
            cv.put("city_id", cityEntity.getCityId());
            mDB.insert(TABLE_CITY, null, cv);
        }
    }

    /**
     * 从本地取出数据
     * @return
     */
    public List<CityEntity> getCityList(){
        List<CityEntity> cityNameList = new ArrayList<>();
        String sql = "select distinct * from " + TABLE_CITY;
        Cursor cursor = mDB.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                CityEntity cityEntity = new CityEntity();
                cityEntity.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                cityEntity.setCityId(cursor.getString(cursor.getColumnIndex("city_id")));
                cityNameList.add(cityEntity);
            }
        }
        cursor.close();
        return cityNameList;
    }

    /**
     * 根据城市名称得到该城市ID
     * @param cityName
     * @return
     */
    public String getCityId(String cityName){
        String cityId = "";
        String sql = "select city_id from " + TABLE_CITY + " where city_name = ?";
        Cursor cursor = mDB.rawQuery(sql, new String[]{cityName});
        if (cursor.moveToFirst()){
            cityId = cursor.getString(cursor.getColumnIndex("city_id"));
        }
        cursor.close();
        return cityId;
    }

    /**
     * 保存天气数据（城市，天气，温度）
     * @param weather
     */
    public void addWeather(String cityName, String weather, String temp){
        String sql = "select * from " + TABLE_WEATHER + " where city_name = ?";
        Cursor cursor = mDB.rawQuery(sql, new String[]{cityName});
        if (cursor.getCount()>0){
            Log.e("addWeather", "已保存成功");
            return;         //如果数据库中存在该城市则不保存
        }
        cursor.close();
        ContentValues cv = new ContentValues();
        cv.put("city_name", cityName);
        cv.put("weather", weather);
        cv.put("temp", temp);
        mDB.insert(TABLE_WEATHER, null, cv);
        Log.e("addWeather", "保存成功");
    }

    /**
     * 从数据库获取天气数据列表
     * @return
     */
    public List<WeatherEntity> getWeathers(){
        List<WeatherEntity> list = new ArrayList<>();
        String sql = "select * from " + TABLE_WEATHER;
        Cursor cursor = mDB.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            while (cursor.moveToNext()){
                WeatherEntity entity = new WeatherEntity();
                entity.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                entity.setWeather(cursor.getString(cursor.getColumnIndex("weather")));
                entity.setTemp(cursor.getString(cursor.getColumnIndex("temp")));
                list.add(entity);
            }
        }
        cursor.close();
        return list;
    }

    /**
     * 删除天气列表的一条数据
     */
    public void deleteWeather(String cityName){
        mDB.delete(TABLE_WEATHER, "city_name = ?", new String[]{cityName});
        Log.e("DBOperationHelper", "你删除了" + cityName);
    }
}
