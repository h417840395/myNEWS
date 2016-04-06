package com.blade.newsbeta1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by blade on 2016/4/3.
 */
public class Fragment4Viewpager extends Fragment {
    /**
     * 自设getfragment方法 创建一个bundle 将参数存入bundle再存入fragment 实现fragment的解耦
     * 当fragment oncreateview时将参数取出
     */
    public static Fragment4Viewpager getFragment(String channelId) {

        Bundle bundle = new Bundle();
        bundle.putString("channelId", channelId);
        Fragment4Viewpager fragment = new Fragment4Viewpager();
        fragment.setArguments(bundle);

        return fragment;
    }


    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<News> mNewsesList;
    private NewsAdapter newsAdapter;

    public static final String URL = "http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
    /**
     * page为新闻的页数 用于计数
     */
    private int page = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        final String channelId = getArguments().getString("channelId");
        final String firstURL = URL + "?channelId=" + channelId + "&page=" + page;

        mListView = (ListView) view.findViewById(R.id.detail_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mNewsesList = new ArrayList<>();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTask4NewsList(true).execute(firstURL);
            }
        });

        /***
         * 启动asynctask获取新闻列表
         */
        new AsyncTask4NewsList(true).execute(firstURL);
        /**
         * 给listview设置adapter
         * 并给listview设置on item click listener 单击跳转到详情页面
         * */
        newsAdapter = new NewsAdapter(getActivity(), mNewsesList);
        mListView.setAdapter(newsAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = mNewsesList.get(position);
                Intent intent4DetailActivity = new Intent(getActivity(), DetailActivity.class);
                intent4DetailActivity.putExtra("news", news);
                startActivity(intent4DetailActivity);
            }
        });
        /**
         * 给listview设置on scroll state change listener
         * 监听其滚动状态  若滚到底部 启动asynctask 读取下一页
         * */
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {

                    String urlGetNext = URL + "?channelId=" + channelId + "&page=" + String.valueOf(++page);
                    new AsyncTask4NewsList(false).execute(urlGetNext);
                }
            }
        });

        return view;
    }

    /**
     * 自定义asynctask用于获取新闻详情
     * 自定义一个flag 开始任务时传入true说明 清空新闻列表并更新
     * 若传入false 说明在原列表基础上 添加更新的内容
     */
    class AsyncTask4NewsList extends AsyncTask<String, Void, String> {

        private boolean flag;

        public AsyncTask4NewsList(boolean flag) {
            this.flag = flag;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            List<News> jsonList = JsonParse.Json2NewsList(s);

            if (flag) {
//                Log.e("TAG", "onPostExecute: " + jsonList.size());
                mNewsesList.clear();
                mNewsesList.addAll(jsonList);
                newsAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                mNewsesList.addAll(jsonList);
                newsAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected String doInBackground(String... params) {


            StringBuilder response = new StringBuilder();
            HttpURLConnection connection = null;

            try {

                java.net.URL url = new URL(params[0]);
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
