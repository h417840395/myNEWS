package com.example.blade.myweatherapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blade.myweatherapp.R;
import com.example.blade.myweatherapp.database.MyWeatherDB;
import com.example.blade.myweatherapp.model.City;
import com.example.blade.myweatherapp.model.Country;
import com.example.blade.myweatherapp.model.Province;
import com.example.blade.myweatherapp.util.HttpCallBackListener;
import com.example.blade.myweatherapp.util.HttpUtil;
import com.example.blade.myweatherapp.util.ResponseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blade on 2016/3/9.
 */
public class ChooseActivity extends Activity {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTRY = 2;
    private ProgressDialog progressDialog;
    private ListView listView;
    private TextView tittleView;
    private ArrayAdapter<String> adapter;


    private List<Province> provinceList;
    private List<City> cityList;
    private List<Country> countryList;
    private Province provinceSelected;
    private City citySelected;
    private int selectedLevel;
    private MyWeatherDB myWeatherDB;
    private List<String> dataList = new ArrayList<>();

    private Boolean isFromWeatherActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_choose);
        listView = (ListView) findViewById(R.id.list_view);
        tittleView = (TextView) findViewById(R.id.tittle);

        isFromWeatherActivity=getIntent().getBooleanExtra("From_Weather_Activity",false);

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("city_selected",false)&&!isFromWeatherActivity){
            Intent intent=new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
            return;

        }


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        myWeatherDB = MyWeatherDB.getMyWeatherDB(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedLevel == LEVEL_PROVINCE) {
                    provinceSelected = provinceList.get(position);
                    queryCity();
                } else if (selectedLevel == LEVEL_CITY) {
                    citySelected = cityList.get(position);
                    queryCountry();
                }else if (selectedLevel==LEVEL_COUNTRY){
                    String countryCode=countryList.get(position).getCountryCode();
                    Intent intent=new Intent(ChooseActivity.this,WeatherActivity.class);
                    intent.putExtra("country_code",countryCode);
                    startActivity(intent);
                    finish();
                }
            }
        });
        queryProvince();
    }

    private void queryProvince() {
        provinceList = myWeatherDB.loadProvince();
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            tittleView.setText("中国");
            selectedLevel = LEVEL_PROVINCE;
        } else {
            queryServer(null, "province");
        }
    }

    private void queryCity() {
        cityList = myWeatherDB.loadCity(provinceSelected.getId());
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            tittleView.setText(provinceSelected.getProvinceName());
            selectedLevel = LEVEL_CITY;
        } else {
            queryServer(provinceSelected.getProvinceCode(), "city");
        }
    }

    private void queryCountry() {
        countryList = myWeatherDB.loadCountry(citySelected.getId());
        if (countryList.size() > 0) {
            dataList.clear();
            for (Country country : countryList) {
                dataList.add(country.getCountryName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            tittleView.setText(citySelected.getCityName());
            selectedLevel = LEVEL_COUNTRY;
        } else {
            queryServer(citySelected.getCityCode(), "country");
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {

        if (selectedLevel == LEVEL_COUNTRY) {
            queryCity();
        } else if (selectedLevel == LEVEL_CITY) {
            queryProvince();
        } else {
            if (isFromWeatherActivity){
                Intent intent=new Intent(this,WeatherActivity.class);
                startActivity(intent);
            }
            finish();
        }

    }

    private void queryServer(final String code, final String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml?level=2";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml?level=1";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                Boolean result = false;
                if ("province".equals(type)) {
                    result = ResponseUtil.handleProvinceRsponse(myWeatherDB, response);
                } else if ("city".equals(type)) {
                    result = ResponseUtil.handleCityRsponse(myWeatherDB, response, provinceSelected.getId());

                } else if ("country".equals(type)) {
                    result = ResponseUtil.handleCountryyRsponse(myWeatherDB, response, citySelected.getId());
                }
                if (result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvince();
                            }
                            else if ("city".equals(type)) {
                                queryCity();
                            }
                            else if ("country".equals(type)) {
                                queryCountry();
                            }
                        }
                    });
                }


            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseActivity.this, "加载失败", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });


    }


}
