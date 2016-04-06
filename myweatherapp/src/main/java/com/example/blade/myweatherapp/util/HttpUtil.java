package com.example.blade.myweatherapp.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by blade on 2016/3/9.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address, final HttpCallBackListener httpCallBackListener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                    URL url = new URL(address);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.setConnectTimeout(8000);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    if (httpCallBackListener != null) {
                        httpCallBackListener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (httpCallBackListener != null)
                        httpCallBackListener.onError(e);
                } finally {
                    if (httpURLConnection != null)
                        httpURLConnection.disconnect();
                }


            }
        }).start();


    }
}
