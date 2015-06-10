package com.minglang.suiuu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.ViewHolder;


public class MySuiuuAdapter extends BaseAdapter {

    private Context context;

    public MySuiuuAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_my_suiuu, position);
        convertView = holder.getConvertView();

        return convertView;
    }
}
