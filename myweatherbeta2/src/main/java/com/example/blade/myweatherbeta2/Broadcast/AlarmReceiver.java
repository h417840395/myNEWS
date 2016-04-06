package com.example.blade.myweatherbeta2.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.blade.myweatherbeta2.Service.RefreshService;

/**
 * Created by blade on 2016/3/24.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentToRefresh=new Intent(context, RefreshService.class);
        context.startService(intentToRefresh);
    }
}
