package com.minglang.suiuu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.ViewHolder;

public class MainSliderAdapter extends BaseAdapter {

    private Context context;
    private String[] titleArray;

    private int itemScreenHeight;

    public MainSliderAdapter(Context context, String[] titleArray) {
        this.context = context;
        this.titleArray = titleArray;
    }

    public void setScreenHeight(int screenHeight) {
        int tempScreenHeight1 = screenHeight / 12;
        int tempScreenHeight2 = AppUtils.px2dip(60, context);
        itemScreenHeight = tempScreenHeight1 > tempScreenHeight2 ? tempScreenHeight1 : tempScreenHeight2;
    }

    @Override
    public int getCount() {
        if (titleArray != null && titleArray.length > 0) {
            return titleArray.length;
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (titleArray != null && titleArray.length > 0) {
            return titleArray[position];
        } else {
            return position;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String str = titleArray[position];
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_slider, position);
        convertView = holder.getConvertView();

        AbsListView.LayoutParams params =
                new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, itemScreenHeight);
        convertView.setLayoutParams(params);

        TextView textView = holder.getView(R.id.item_slider_text);
        textView.setText(str);
        return convertView;
    }

}