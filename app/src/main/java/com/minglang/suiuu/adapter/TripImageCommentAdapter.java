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
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 项目名称：Suiuu
 * 类描述：文章详细页面随拍的adapter
 * 创建人：Administrator
 * 创建时间：2015/5/4 9:55
 * 修改人：Administrator
 * 修改时间：2015/5/4 9:55
 * 修改备注：
 */
public class TripImageCommentAdapter extends BaseAdapter {

    private Context context;
    private List<String> imageList;
    private List<String> contentList;

    public TripImageCommentAdapter(Context context, List<String> imageList, List<String> conentList) {
        this.context = context;
        this.imageList = imageList;
        this.contentList = conentList;
    }

    @Override
    public int getCount() {
        if (imageList != null && imageList.size() > 0) {
            return imageList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (imageList != null && imageList.size() > 0) {
            return imageList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_trip_image_comment, position);
        convertView = holder.getConvertView();

        SimpleDraweeView headImageView = holder.getView(R.id.trip_image_comment_head_image_view);
        TextView contentView = holder.getView(R.id.trip_image_comment_content);

        String headImagePath = imageList.get(position);
        if (!TextUtils.isEmpty(headImagePath)) {
            headImageView.setImageURI(Uri.parse(headImagePath));
        } else {
            headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
        }

        if (contentList != null && contentList.size() > 0) {
            String content = contentList.get(position);
            if (!TextUtils.isEmpty(content)) {
                contentView.setText(content);
            } else {
                contentView.setText("");
            }
        } else {
            contentView.setText("");
        }

        headImageView.setOnClickListener(new HeadImageViewClick(position));

        return convertView;
    }

    private class HeadImageViewClick implements View.OnClickListener {

        private int index;

        private HeadImageViewClick(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {

        }

    }

}