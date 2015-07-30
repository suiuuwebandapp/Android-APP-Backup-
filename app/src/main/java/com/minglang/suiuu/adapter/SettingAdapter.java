package com.minglang.suiuu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.ViewHolder;

/**
 * Created by LZY on 2015/3/19 0019.
 * <p/>
 * 设置页面的数据适配器
 */
public class SettingAdapter extends BaseAdapter {

    private Context context;

    private String[] stringArray;

    public SettingAdapter(Context context, String[] stringArray) {
        this.context = context;
        this.stringArray = stringArray;
    }

    @Override
    public int getCount() {
        if (stringArray != null && stringArray.length > 0) {
            return stringArray.length;
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
        if (stringArray != null && stringArray.length > 0) {
            return stringArray[position];
        } else {
            return null;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_setting, position);
        convertView = holder.getConvertView();
        TextView textView = holder.getView(R.id.itemSettingText);
        textView.setText(stringArray[position]);
        return convertView;
    }

}