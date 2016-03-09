package com.lzj.weatherknow.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2/17 0017.
 * 数据库帮助类
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    /**
     * 所有城市列表
     */
    public static final String CREATE_CITIES = "create table cities (" +
            "id integer primary key autoincrement, " +
            "city_name text," +
            "city_id text)";

    /**
     * 历史记录
     */
    public static final String CREATE_WEATHERS = "create table weathers (" +
            "id integer primary key autoincrement, " +
            "city_name text, " +
            "weather text, " +
            "temp text)";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_CITIES);
        db.execSQL(CREATE_WEATHERS);
    }

    public void onUpgrade(SQLiteDatabase db, int newVersion, int oldVersion){
    }
}
