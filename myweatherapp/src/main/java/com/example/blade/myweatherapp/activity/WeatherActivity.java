package com.example.blade.myweatherapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.blade.myweatherapp.R;
import com.example.blade.myweatherapp.util.HttpCallBackListener;
import com.example.blade.myweatherapp.util.HttpUtil;
import com.example.blade.myweatherapp.util.JsonUtil;

/**
 * Created by blade on 2016/3/9.
 */
public class WeatherActivity extends Activity implements View.OnClickListener {
    private TextView cityTV;
    private TextView dateTV;
    private TextView weatherTV;
    private TextView temp1TV;
    private TextView temp2TV;
    private TextView publish;
    private Button switchCity;
    private Button refreshWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather);
        cityTV = (TextView) findViewById(R.id.city_name);
        dateTV = (TextView) findViewById(R.id.date);
        weatherTV = (TextView) findViewById(R.id.weather);
        temp1TV = (TextView) findViewById(R.id.temp1);
        temp2TV = (TextView) findViewById(R.id.temp2);
        publish = (TextView) findViewById(R.id.publish_txt);
        switchCity=(Button)findViewById(R.id.switch_btn);
        refreshWeather=(Button)findViewById(R.id.refresh);

        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);

        String countryCode = getIntent().getStringExtra("country_code");
        if (!TextUtils.isEmpty(countryCode)) {
            publish.setText("正在刷新");
            cityTV.setVisibility(View.INVISIBLE);
            queryWeather(countryCode);
        } else {
            showWeather();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.switch_btn:
                Intent intent=new Intent(this,ChooseActivity.class);
                intent.putExtra("From_Weather_Activity",true);
                startActivity(intent);
                finish();
                break;
            case  R.id.refresh:
                publish.setText("同步中");
//                SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
//                String countryCode=sharedPreferences.getString("","");
                break;
            default:break;


        }


    }

    private void queryWeather(String countryCode) {
//        String address = "http://wthrcdn.etouch.cn/weather_mini?citykey=" + countryCode;
        String address ="http://wthrcdn.etouch.cn/weather_mini?citykey=101220607";
        queryFromSever(address);

    }

    private void queryFromSever(final String address) {

        HttpUtil.sendHttpRequest(address, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {

                JsonUtil.responseToJson(WeatherActivity.this, response);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWeather();
                    }
                });

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    private void showWeather(){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        cityTV.setText(sharedPreferences.getString("city",""));
        dateTV.setText(sharedPreferences.getString("date",""));
        weatherTV.setText(sharedPreferences.getString("type",""));
        temp1TV.setText(sharedPreferences.getString("high",""));
        temp2TV.setText(sharedPreferences.getString("low",""));
        cityTV.setVisibility(View.VISIBLE);


    }

//    @Override
//    public void onBackPressed() {
//        Intent intent=new Intent(this,ChooseActivity.class);
//        startActivity(intent);
//        super.onBackPressed();
//    }
}
