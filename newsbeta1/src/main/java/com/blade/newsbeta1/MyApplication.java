package com.blade.newsbeta1;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**全局变量只有一个RequestQueue 保证生命周期
 * Created by blade on 2016/4/3.
 */
public class MyApplication extends Application {
    public static RequestQueue mQueue;
    @Override

    public void onCreate() {
        super.onCreate();
        mQueue= Volley.newRequestQueue(this);
    }


    public static RequestQueue getRequestQueue(){
        return mQueue;
    }




}
