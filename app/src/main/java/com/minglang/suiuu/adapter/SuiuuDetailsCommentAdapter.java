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
import com.minglang.suiuu.entity.SuiuuDetailsData.DataEntity.CommentEntity.CommentDataEntity;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 * <p/>
 * 随游详情评论数据适配器
 */
public class SuiuuDetailsCommentAdapter extends BaseAdapter {

    private Context context;

    private List<CommentDataEntity> list;

    public SuiuuDetailsCommentAdapter(Context context, List<CommentDataEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<CommentDataEntity> list) {
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

        SimpleDraweeView headImageView = holder.getView(R.id.item_comment_head_image);
        TextView titleView = holder.getView(R.id.item_comment_title);
        TextView commentView = holder.getView(R.id.item_comment_content);

        String headImage = list.get(position).getHeadImg();
        if (!TextUtils.isEmpty(headImage)) {
            headImageView.setImageURI(Uri.parse(headImage));
        } else {
            headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
        }

        String nickName = list.get(position).getNickname();
        if (!TextUtils.isEmpty(nickName)) {
            titleView.setText(nickName);
        } else {
            titleView.setText("");
        }

        String comment = list.get(position).getContent();
        if (!TextUtils.isEmpty(comment)) {
            commentView.setText(comment);
        } else {
            commentView.setText("");
        }

        return convertView;
    }
}
