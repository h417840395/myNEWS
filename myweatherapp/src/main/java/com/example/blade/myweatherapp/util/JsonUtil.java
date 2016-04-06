package com.example.blade.myweatherapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.security.SecurityPermission;

/**
 * Created by blade on 2016/3/9.
 */
public class JsonUtil {
    public static void responseToJson(Context context, String response) {
        try {

            Log.d("message",response.toString());

            JSONObject jsonObject = new JSONObject(new JSONTokener(response));
            String city = jsonObject.getJSONObject("data").getString("city");
            String ganmao = jsonObject.getJSONObject("data").getString("ganmao");
            String date = jsonObject.getJSONObject("data").getJSONArray("forecast").getJSONObject(0).getString("date");
            String fengxiang = jsonObject.getJSONObject("data").getJSONArray("forecast").getJSONObject(0).getString("fengxiang");
            String fengli = jsonObject.getJSONObject("data").getJSONArray("forecast").getJSONObject(0).getString("fengli");
            String type = jsonObject.getJSONObject("data").getJSONArray("forecast").getJSONObject(0).getString("type");
            String high = jsonObject.getJSONObject("data").getJSONArray("forecast").getJSONObject(0).getString("high");
            String low = jsonObject.getJSONObject("data").getJSONArray("forecast").getJSONObject(0).getString("low");
            Log.d("123456789","00000000000000000000000000");
            saveWeather(context, city, ganmao, date, fengxiang, fengli, type, high, low);
        } catch (Exception e) {
        }


    }


    public static void saveWeather(Context context, String city, String ganmao, String date, String fengxiang, String fengli, String type, String high, String low) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putBoolean("city_selected", true);
        editor.putString("city", city);
        editor.putString("ganmao", ganmao);
        editor.putString("date", date);
        editor.putString("fengxiang", fengxiang);
        editor.putString("fengli", fengli);
        editor.putString("type", type);
        editor.putString("high", high);
        editor.putString("low", low);
        editor.commit();

    }
}
