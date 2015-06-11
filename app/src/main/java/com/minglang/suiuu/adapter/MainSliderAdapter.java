package com.minglang.suiuu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.Utils;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

public class MainSliderAdapter extends BaseAdapter {

    private Context context;

    private List<String> list;

    private int itemScreenHeight;

    public MainSliderAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void setScreenHeight(int screenHeight) {
        int tempScreenHeight1 = screenHeight / 12;
        int tempScreenHeight2 = Utils.newInstance().px2dip(60, context);
        itemScreenHeight = tempScreenHeight1 > tempScreenHeight2 ? tempScreenHeight1 : tempScreenHeight2;
        DeBugLog.i("MainSliderAdapter", "itemScreenHeight:" + itemScreenHeight);
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        if (list != null && list.size() > 0) {
            return list.get(position);
        } else {
            return position;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String str = list.get(position);
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_slider, position);
        convertView = holder.getConvertView();

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, itemScreenHeight);
        convertView.setLayoutParams(params);

        TextView textView = holder.getView(R.id.itemSliderText);
        textView.setText(str);
        return convertView;
    }

}
