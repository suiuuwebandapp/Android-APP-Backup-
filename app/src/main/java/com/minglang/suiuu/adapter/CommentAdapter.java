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
import java.util.Objects;

/**
 * Created by Administrator on 2015/4/9.
 */
public class CommentAdapter extends BaseAdapter {

    private Context context;

    private List<Objects> list;

    public CommentAdapter(Context context){
        this.context =context;
    }

    public CommentAdapter(Context context, List<Objects> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<Objects> list){
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

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);

            holder.headImage = (ImageView) convertView.findViewById(R.id.item_comment_head_image);
            holder.title = (TextView) convertView.findViewById(R.id.item_comment_title);
            holder.content = (TextView) convertView.findViewById(R.id.item_comment_content);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    class ViewHolder {
        ImageView headImage;
        TextView title;
        TextView content;
    }

}
