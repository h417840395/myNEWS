package com.example.blade.myweatherbeta2.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.blade.myweatherbeta2.Model.City;

import java.util.List;

/**
 * Created by blade on 2016/3/22.
 */
public class CityAdapter extends ArrayAdapter<City> {
    int mId;

    public CityAdapter(Context context, int resource, List<City> objects) {
        super(context, resource, objects);
        mId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        City city = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(mId, null);
        } else {
            view = convertView;
        }
        return view;
    }
}
