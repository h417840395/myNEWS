package com.blade.newsbeta1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by blade on 2016/4/2.
 */
public class NewsAdapter extends BaseAdapter {

    private List<News> mList;
    private LayoutInflater mInflater;

    public NewsAdapter(Context context, List<News> mList) {
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *使用viewholder 和convertview 对listview进行优化
     * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        class ViewHolder {
            public TextView title;
            public TextView desc;
            public TextView pubDate;
            public TextView source;
        }
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_tiem, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.desc);
            viewHolder.pubDate = (TextView) convertView.findViewById(R.id.pubDate);
            viewHolder.source = (TextView) convertView.findViewById(R.id.source);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        News news = mList.get(position);
        viewHolder.title.setText(news.getTitle());
        viewHolder.desc.setText(news.getDesc());
        viewHolder.pubDate.setText(news.getPubDate());
        viewHolder.source.setText(news.getSource() + ":");

        return convertView;
    }

}