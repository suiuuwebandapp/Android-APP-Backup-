package com.minglang.suiuu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.minglang.suiuu.entity.Published;

import java.util.List;

/**
 * 发布的随游数据适配器
 * <p/>
 * Created by Administrator on 2015/6/19.
 */
public class PublishedAdapter extends BaseAdapter {

    private Context context;

    private List<Published.PublishedData> list;

    public PublishedAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Published.PublishedData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (list != null && list.size() > 0) {
            return list.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

}
