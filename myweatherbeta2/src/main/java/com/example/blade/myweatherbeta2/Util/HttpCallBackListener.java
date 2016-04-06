package com.example.blade.myweatherbeta2.Util;

/**
 * Created by blade on 2016/3/21.
 */
public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}
