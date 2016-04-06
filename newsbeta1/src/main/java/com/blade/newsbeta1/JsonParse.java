package com.blade.newsbeta1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blade on 2016/4/3.
 */
public class JsonParse {
    /**
     * 用于解析频道列表 并返回list<>
     */
    public static List<Channel> json2ChannelList(String response) {
        List<Channel> channelList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONObject("showapi_res_body").getJSONArray("channelList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                Channel channel = new Channel();
                channel.setName(obj.getString("name"));
                channel.setChannelId(obj.getString("channelId"));

                channelList.add(channel);
            }
        } catch (Exception e) {
            Log.e("TAG", "json2ChannelList: ", e);
        }
        Log.e("TAG", "json2ChannelList: channellist------" + channelList.size());
        return channelList;
    }

    /**
     *用于解析新闻列表 并返回list<>
     * */
    public static List<News> Json2NewsList(String response) {

        List<News> jsonList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist");

            for (int i = 0; i < jsonArray.length(); i++) {
                News news = new News();
                JSONObject obj = jsonArray.getJSONObject(i);
                news.setChannelId(obj.getString("channelId"));
                news.setChannelName(obj.getString("channelName"));
                news.setDesc(obj.getString("desc"));
                news.setLink(obj.getString("link"));
//                news.setLongDesc(obj.getString("long_desc"));
                news.setLongDesc(obj.optString("long_desc", null));
                news.setSource(obj.getString("source"));
                news.setTitle(obj.getString("title"));
                news.setPubDate(obj.getString("pubDate"));

                JSONArray array = obj.getJSONArray("imageurls");
                for (int x = 0; x < array.length(); x++) {
                    JSONObject img = array.getJSONObject(x);
                    news.setImg(img.getString("url"));
                }
                jsonList.add(news);
            }
        } catch (JSONException e) {
            Log.e("TAG", "Json2NewsList: " + e);
        }
        return jsonList;
    }

}
