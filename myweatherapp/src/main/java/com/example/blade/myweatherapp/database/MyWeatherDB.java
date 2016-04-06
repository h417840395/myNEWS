package com.example.blade.myweatherapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.blade.myweatherapp.model.City;
import com.example.blade.myweatherapp.model.Country;
import com.example.blade.myweatherapp.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blade on 2016/3/9.
 */
public class MyWeatherDB {

    public static final String DB_NAME = "MyWeatherDB";
    public static final int VERSION = 1;
    private SQLiteDatabase db;
    private static MyWeatherDB myWeatherDB = null;


    private MyWeatherDB(Context context) {
        MyDBHelper myDBHelper = new MyDBHelper(context, DB_NAME, null, VERSION);
        db = myDBHelper.getWritableDatabase();
    }

    public static synchronized MyWeatherDB getMyWeatherDB(Context context) {
        if (myWeatherDB == null) {
            myWeatherDB = new MyWeatherDB(context);
        }
        return myWeatherDB;
    }

    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("province_name", province.getProvinceName());
            contentValues.put("province_code", province.getProvinceCode());
            db.insert("province", null, contentValues);
        }
    }

    public void savePCity(City city) {
        if (city != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("city_name", city.getCityName());
            contentValues.put("city_code", city.getCityCode());
            contentValues.put("province_id", city.getProvinceId());
            db.insert("city", null, contentValues);
        }
    }

    public void savePCountry(Country country) {
        if (country != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("country_name", country.getCountryName());
            contentValues.put("country_code", country.getCountryCode());
            contentValues.put("city_id", country.getCityId());
            db.insert("country", null, contentValues);
        }
    }

    public List<Province> loadProvince() {
        List<Province> list = new ArrayList<>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;

    }


    public List<City> loadCity(int provinceId) {
        List<City> list = new ArrayList<>();
        Cursor cursor = db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
                list.add(city);

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;

    }

    public List<Country> loadCountry(int cityId) {
        List<Country> list = new ArrayList<>();
        Cursor cursor = db.query("Country", null, "city_id=?",new  String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Country country = new Country();
                country.setId(cursor.getInt(cursor.getColumnIndex("id")));
                country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
                country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
                country.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(country);

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;

    }


}
