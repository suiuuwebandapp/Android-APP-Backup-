package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.SuiuuDeatailData;
import com.minglang.suiuu.entity.TripGalleryDetail;

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

    private List<SuiuuDeatailData.DataEntity.CommentEntity.CommentDataEntity> list;
    private List<TripGalleryDetail.DataEntity.CommentEntity> tripList;
    private String type;
    public CommonCommentAdapter(Context context,List<SuiuuDeatailData.DataEntity.CommentEntity.CommentDataEntity> list) {
        this.context = context;
        this.list = list;
    }
    public CommonCommentAdapter(Context context,List<TripGalleryDetail.DataEntity.CommentEntity> list,String type) {
        this.context = context;
        this.tripList = list;
        this.type = type;
    }
//    public void setList(List<SuiuuDeatailData.DataEntity.CommentEntity.CommentDataEntity> list) {
//        this.list = list;
//        notifyDataSetChanged();
//    }
    public void onDateChange(List<SuiuuDeatailData.DataEntity.CommentEntity.CommentDataEntity> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if("1".equals(type)) {
            if (tripList != null && tripList.size() > 0) {
                return tripList.size();
            } else {
                return 0;
            }
        }else {
            if (list != null && list.size() > 0) {
                return list.size();
            } else {
                return 0;
            }
        }

    }

    @Override
    public Object getItem(int position) {
        if("1".equals(type)) {
            if (tripList != null && tripList.size() > 0) {
                return tripList.get(position);
            } else {
                return null;
            }
        }else {
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
            holder.headImage = (SimpleDraweeView) convertView.findViewById(R.id.item_comment_head_image);
            holder.title = (TextView) convertView.findViewById(R.id.item_comment_title);
            holder.content = (TextView) convertView.findViewById(R.id.item_comment_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if("1".equals(type)) {
            if (!TextUtils.isEmpty(tripList.get(position).getHeadImg())) {
                Uri uri = Uri.parse(tripList.get(position).getHeadImg());
                holder.headImage.setImageURI(uri);
            }
            holder.title.setText(tripList.get(position).getNickname());
//            if (!TextUtils.isEmpty(tripList.get(position).get)) {
//                holder.content.setText(tripList.get(position).getRTitle() + "  " + tripList.get(position).getContent());
//            } else {
                holder.content.setText(tripList.get(position).getComment());
//            }
        }else{
            if (!TextUtils.isEmpty(list.get(position).getHeadImg())) {
                Uri uri = Uri.parse(list.get(position).getHeadImg());
                holder.headImage.setImageURI(uri);
            }
            holder.title.setText(list.get(position).getNickname());
            if (!TextUtils.isEmpty(list.get(position).getRTitle())) {
                holder.content.setText(list.get(position).getRTitle() + "  " + list.get(position).getContent());
            } else {
                holder.content.setText(list.get(position).getContent());
            }
        }

        return convertView;
    }
    class ViewHolder {
        SimpleDraweeView headImage;
        TextView title;
        TextView content;

    }
}
