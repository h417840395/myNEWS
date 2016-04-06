package com.example.blade.myweatherapp.util;

import android.text.TextUtils;

import com.example.blade.myweatherapp.database.MyWeatherDB;
import com.example.blade.myweatherapp.model.City;
import com.example.blade.myweatherapp.model.Country;
import com.example.blade.myweatherapp.model.Province;



/**
 * Created by blade on 2016/3/9.
 */
public class ResponseUtil {

    public synchronized static boolean handleProvinceRsponse(MyWeatherDB myWeatherDB, String response) {

        if (!TextUtils.isEmpty(response)) {
            String[] allProvince = response.split(",");
            if (allProvince != null && allProvince.length != 0) {
                for (String p : allProvince) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    myWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }


    public synchronized static boolean handleCityRsponse(MyWeatherDB myWeatherDB, String response,int provinceId) {

        if (!TextUtils.isEmpty(response)) {
            String[] allCity = response.split(",");
            if (allCity != null && allCity.length != 0) {
                for (String p : allCity) {
                    String[] array = p.split("\\|");
                    City city=new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);

                    city.setProvinceId(provinceId);
                    myWeatherDB.savePCity(city);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCountryyRsponse(MyWeatherDB myWeatherDB, String response,int cityId) {

        if (!TextUtils.isEmpty(response)) {
            String[] allCountry = response.split(",");
            if (allCountry != null && allCountry.length != 0) {
                for (String p : allCountry) {
                    String[] array = p.split("\\|");
                    Country country=new Country();
                    country.setCountryCode(array[0]);
                    country.setCountryName(array[1]);

                    country.setCityId(cityId);
                    myWeatherDB.savePCountry(country);
                }
                return true;

            }


        }

        return false;
    }
}


