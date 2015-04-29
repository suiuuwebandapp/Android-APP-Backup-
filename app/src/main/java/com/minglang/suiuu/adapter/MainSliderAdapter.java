package com.minglang.suiuu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;

import java.util.List;

/**
 * Created by LZY on 2015/3/30 0030.
 */
public class MainSliderAdapter extends BaseAdapter {

    private Context context;

    private List<String> list;

    public MainSliderAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
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
    public View getView(int position, View convertView,ViewGroup parent) {

        String str = list.get(position);

        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.item_slider, null);

            holder.icon = (ImageView) convertView.findViewById(R.id.itemSliderIcon);
            holder.text = (TextView) convertView.findViewById(R.id.itemSliderText);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (str) {

            case "收藏":
                holder.icon.setImageResource(R.drawable.item_icon_star2);
                break;

            case "关注":
                holder.icon.setImageResource(R.drawable.item_icon_attention2);
                break;

            case "消息":
                holder.icon.setImageResource(R.drawable.item_icon_infomation2);
                break;

            case "粉丝":
                holder.icon.setImageResource(R.drawable.item_icon_fans2);
                break;

            case "设置":
                holder.icon.setImageResource(R.drawable.item_icon_setting2);
                break;

            case "退出":
                holder.icon.setImageResource(R.drawable.item_icon_exit2);
                break;
        }

        holder.text.setText(str);

        return convertView;
    }

    class ViewHolder {

        ImageView icon;
        TextView text;

    }

}
