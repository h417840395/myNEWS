package com.example.blade.myweatherapp.util;

/**
 * Created by blade on 2016/3/9.
 */
public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}
