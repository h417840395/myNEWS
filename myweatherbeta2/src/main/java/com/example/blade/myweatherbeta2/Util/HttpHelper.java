package com.example.blade.myweatherbeta2.Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by blade on 2016/3/21.
 */
public class HttpHelper {

    public static void sendRequest(final String cityNum, final HttpCallBackListener httpCallBackListener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                    URL url = new URL("http://wthrcdn.etouch.cn/weather_mini?citykey=" + cityNum);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("get");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                        if (httpCallBackListener != null) {
                            httpCallBackListener.onFinish(response.toString());
                        }
                    }

                } catch (Exception e) {
                    httpCallBackListener.onError(e);
                } finally {
                    if (httpURLConnection != null) {
                    }
                    httpURLConnection.disconnect();
                }


            }
        }).start();

    }
}
