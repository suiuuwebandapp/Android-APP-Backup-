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
 * Created by LZY on 2015/3/19 0019.
 */
public class SettingAdapter extends BaseAdapter {

    private Context context;

    private List<String> list;

    public SettingAdapter(Context context, List<String> list) {
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
            return null;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String str = list.get(position);

        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.item_setting,null);

            holder.icon = (ImageView) convertView.findViewById(R.id.itemSettingIcon);
            holder.text = (TextView) convertView.findViewById(R.id.itemSettingText);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        switch (str){

            case "个人设置":
                holder.icon.setImageResource(R.drawable.item_setting_personal);
                break;

            case "通用设置":
                holder.icon.setImageResource(R.drawable.item_setting_general);
                break;

            case "检查更新":
                holder.icon.setImageResource(R.drawable.item_setting_update);
                break;

            case "联系我们":
                holder.icon.setImageResource(R.drawable.item_setting_contact);
                break;

            case "反馈":
                holder.icon.setImageResource(R.drawable.item_setting_feedback);
                break;

            case "去评分":
                holder.icon.setImageResource(R.drawable.item_setting_score);
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
