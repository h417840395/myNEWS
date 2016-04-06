package com.blade.newsbeta1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PagerActivity extends FragmentActivity {

    public static final String CHANNEL_URL = "http://apis.baidu.com/showapi_open_bus/channel_news/channel_news";
    private List<Channel> channelList;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabViewAdapter tabViewAdapter;
    private List<Fragment> fragmentList;
    private List<String> channelTitleList;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity_layout);

        new AsyncTask4ChannelList().execute(CHANNEL_URL);


        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        channelList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        channelTitleList = new ArrayList<>();
        /**
         * 将tablayout viewpager adapter 相关联
         * */
        mFragmentManager = getSupportFragmentManager();
        tabViewAdapter = new TabViewAdapter(mFragmentManager, channelTitleList, fragmentList);
        mViewPager.setAdapter(tabViewAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    /**
     * 自定义asynctask用于获取频道列表
     */
    class AsyncTask4ChannelList extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            channelList.clear();
            /**
             * 解析json 存入频道列表  遍历频道列表
             * 将频道名存入tablayout所需的channel title list
             * 频道id存入fragment并创建fragment
             * 将fragment存入fragment list
             * */
            channelList.addAll(JsonParse.json2ChannelList(s));
            Log.e("TAG", "onPostExecute: " + channelList.size());
            for (Channel c : channelList) {
                String name = c.getName();
                String channelId = c.getChannelId();
                channelTitleList.add(name);
                Fragment f = Fragment4Viewpager.getFragment(channelId);
                fragmentList.add(f);
            }
            Log.e("TAG", "onCreate: " + channelTitleList.size() + "_____" + fragmentList.size());
            /**
             * 更新adapter
             * */
            tabViewAdapter.notifyDataSetChanged();
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder response = new StringBuilder();
            HttpURLConnection connection = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                connection.setRequestProperty("apikey", "9aec17617f513066b86c892499468211");
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            Log.e("TAG", "doInBackground: " + response.length());
            return response.toString();
        }


    }


}
