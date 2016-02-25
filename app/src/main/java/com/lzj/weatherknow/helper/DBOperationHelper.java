package com.lzj.weatherknow.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lzj.weatherknow.entity.CityEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/17 0017.
 * 数据库相关操作帮助类
 */
public class DBOperationHelper {
    public static final String DB_NAME = "city_list";//数据库名
    public static final String TABLE_CITY = "cities";//表名
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
     * 将数据保存到本地
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
}
