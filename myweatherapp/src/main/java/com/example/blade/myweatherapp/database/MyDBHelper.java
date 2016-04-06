package com.example.blade.myweatherapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by blade on 2016/3/9.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    public static final String CREATE_T_PROVINCE="create table Province(" +
            "id integer primary key autoincrement," +
            "province_name text," +
            "province_code text)";
    public static final String CREATE_T_CITY="create table City(" +
            "id integer primary key autoincrement," +
            "province_id integer," +
            "city_name text," +
            "city_code text)";
    public static final String CREATE_T_COUNTRY="create table Country(" +
            "id integer primary key autoincrement," +
            "city_id integer," +
            "country_name text," +
            "country_code text)";
    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_T_PROVINCE);
        db.execSQL(CREATE_T_CITY);
        db.execSQL(CREATE_T_COUNTRY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
