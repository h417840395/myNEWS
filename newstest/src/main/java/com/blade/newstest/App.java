package com.blade.newstest;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by blade on 2016/4/2.
 */
public class App extends Application {
    public static RequestQueue mQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mQueue = Volley.newRequestQueue(this);
    }

    public static RequestQueue getQueue(){
        return mQueue;
    }

}
