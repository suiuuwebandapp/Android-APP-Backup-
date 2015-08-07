package com.minglang.suiuu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.ViewHolder;

/**
 * 问答社区-下拉菜单数据适配器
 */
public class CommunitySortAdapter extends BaseAdapter {

    private String[] stringArray;
    private Context context;

    public CommunitySortAdapter(String[] stringArray, Context context) {
        this.stringArray = stringArray;
        this.context = context;
    }

    @Override
    public int getCount() {
        return stringArray.length;
    }

    @Override
    public Object getItem(int position) {
        return stringArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_community_sort, position);
        convertView = holder.getConvertView();
        TextView textView = holder.getView(R.id.item_community_text);
        textView.setText(stringArray[position]);
        return convertView;
    }

}