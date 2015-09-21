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
import com.minglang.suiuu.entity.TripImageDetails.TripImageDetailsData.CommentEntity;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/21 18:39
 * 修改人：Administrator
 * 修改时间：2015/7/21 18:39
 * 修改备注：
 */
public class CommonCommentAdapter extends BaseAdapter {

    private Context context;

    private List<CommentDataEntity> list;
    private List<CommentEntity> tripList;
    private String type;

    public CommonCommentAdapter(Context context, List<CommentDataEntity> list) {
        this.context = context;
        this.list = list;
    }

    public CommonCommentAdapter(Context context, List<CommentEntity> list, String type) {
        this.context = context;
        this.tripList = list;
        this.type = type;
    }

    public void onDateChange(List<CommentDataEntity> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }
    public void onDateChangeType(List<CommentEntity> list) {
        this.tripList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if ("1".equals(type)) {
            if (tripList != null && tripList.size() > 0) {
                return tripList.size();
            } else {
                return 0;
            }
        } else {
            if (list != null && list.size() > 0) {
                return list.size();
            } else {
                return 0;
            }
        }
    }

    @Override
    public Object getItem(int position) {
        if ("1".equals(type)) {
            if (tripList != null && tripList.size() > 0) {
                return tripList.get(position);
            } else {
                return null;
            }
        } else {
            if (list != null && list.size() > 0) {
                return list.get(position);
            } else {
                return null;
            }
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
        SimpleDraweeView headImage = holder.getView(R.id.item_comment_head_image);
        TextView title = holder.getView(R.id.item_comment_title);
        TextView content = holder.getView(R.id.item_comment_content);

        if ("1".equals(type)) {
            if (!TextUtils.isEmpty(tripList.get(position).getHeadImg())) {
                Uri uri = Uri.parse(tripList.get(position).getHeadImg());
                headImage.setImageURI(uri);
            }

            title.setText(tripList.get(position).getNickname());
            content.setText(tripList.get(position).getComment());

        } else {
            if (!TextUtils.isEmpty(list.get(position).getHeadImg())) {
                Uri uri = Uri.parse(list.get(position).getHeadImg());
                headImage.setImageURI(uri);
            }

            title.setText(list.get(position).getNickname());

            if (!TextUtils.isEmpty(list.get(position).getRTitle())) {
                content.setText(list.get(position).getRTitle() + "  " + list.get(position).getContent());
            } else {
                content.setText(list.get(position).getContent());
            }
        }
        return convertView;
    }

}