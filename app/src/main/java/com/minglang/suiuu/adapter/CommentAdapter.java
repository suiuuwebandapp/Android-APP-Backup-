package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.LoopArticleCommentList;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

public class CommentAdapter extends BaseAdapter {

    private Context context;

    private List<LoopArticleCommentList> list;

    public CommentAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<LoopArticleCommentList> list) {
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_comment, position);
        convertView = holder.getConvertView();

        SimpleDraweeView headImageView = (SimpleDraweeView) convertView.findViewById(R.id.item_comment_head_image);
        TextView titleView = (TextView) convertView.findViewById(R.id.item_comment_title);
        TextView contentView = (TextView) convertView.findViewById(R.id.item_comment_content);
        TextView timeView = (TextView) convertView.findViewById(R.id.tv_time);

        String headImagePath = list.get(position).getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            headImageView.setImageURI(Uri.parse(headImagePath));
        } else {
            headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
        }

        String title = list.get(position).getrTitle();
        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        } else {
            titleView.setText("");
        }

        String time = list.get(position).getcTime();
        if (!TextUtils.isEmpty(time)) {
            timeView.setText(time);
        } else {
            timeView.setText("");
        }

        String content = list.get(position).getContent();
        if (!TextUtils.isEmpty(content)) {
            contentView.setText(content);
        } else {
            contentView.setText("");

        }

        return convertView;
    }


}
