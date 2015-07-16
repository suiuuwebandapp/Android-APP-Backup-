package com.minglang.suiuu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.ViewHolder;

public class CommunitySortAdapter extends BaseAdapter {

    private String[] stringArray;

    private Context context;

    private OnItemClickListener onItemClickListener;

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

        final View view = convertView;
        final int index = position;

        if (onItemClickListener != null) {

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(view, index);
                }
            });

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(view, index);
                    return false;
                }
            });
        }

        return convertView;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

}