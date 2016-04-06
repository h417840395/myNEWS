package com.blade.newsbeta1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

/**
 * Created by blade on 2016/4/2.
 */
public class DetailActivity extends Activity {
    private TextView channelName;
    private TextView title;
    private TextView source;
    private TextView pubDate;
    private TextView desc;
    private TextView longDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity_layout);


        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.scrollView_linear_layout);
        News news = (News) getIntent().getSerializableExtra("news");

        channelName = (TextView) findViewById(R.id.scrollView_channelName);
        title = (TextView) findViewById(R.id.scrollView_title);
        source = (TextView) findViewById(R.id.scrollView_source);
        pubDate = (TextView) findViewById(R.id.scrollView_pubDate);
        desc = (TextView) findViewById(R.id.scrollView_desc);
        longDesc = (TextView) findViewById(R.id.scrollView_longDesc);


        channelName.setText(news.getChannelName());
        title.setText(news.getTitle());
        source.setText(news.getSource());
        pubDate.setText(news.getPubDate());
        desc.setText(news.getDesc());
        longDesc.setText(news.getLongDesc());
        /**
         * 获取news对象内的img url列表  获取requesqueue
         * */
        ArrayList<String> imgList = news.getImgList();

        RequestQueue mRequestQueue = MyApplication.getRequestQueue();
        /**
         * 遍历img url列表 每有一个url就在scrollview里创建一个imageview
         * 使用volley 的imageloader 获取图片 并存入 lrucache
         * */
        for (String s : imgList) {
            Log.e("TAG", "onCreate: " + s);
            final ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.ic_launcher);
            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(480, 360);
            mParams.setMargins(2, 2, 2, 2);

            mLinearLayout.addView(imageView, mParams);

            ImageLoader imageLoader = new ImageLoader(mRequestQueue, new BitMapCache());
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            imageLoader.get(s, listener, 480, 360);


        }

    }
}
