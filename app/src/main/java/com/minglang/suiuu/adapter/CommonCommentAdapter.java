package com.minglang.suiuu.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.PersonalMainPagerActivity;
import com.minglang.suiuu.entity.SuiuuDetailsData.DataEntity.CommentEntity.CommentDataEntity;
import com.minglang.suiuu.entity.TripImageDetails.TripImageDetailsData.CommentData;
import com.minglang.suiuu.utils.L;
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

    private static final String TAG = CommonCommentAdapter.class.getSimpleName();

    private static final String USER_SIGN = "userSign";

    private Context context;

    private List<CommentDataEntity> list;
    private List<CommentData> tripList;
    private String type;

    public CommonCommentAdapter(Context context, List<CommentDataEntity> list) {
        this.context = context;
        this.list = list;
    }

    public CommonCommentAdapter(Context context, List<CommentData> list, String type) {
        this.context = context;
        this.tripList = list;
        this.type = type;
    }

    public void onDateChange(List<CommentDataEntity> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void onDateChangeType(List<CommentData> list) {
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

        SimpleDraweeView headImageView = holder.getView(R.id.item_comment_head_image);
        TextView titleView = holder.getView(R.id.item_comment_title);
        TextView commentView = holder.getView(R.id.item_comment_content);

        if ("1".equals(type)) {
            String headImage = tripList.get(position).getHeadImg();
            if (!TextUtils.isEmpty(headImage)) {
                headImageView.setImageURI(Uri.parse(headImage));
            } else {
                headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
            }

            String nickName = tripList.get(position).getNickname();
            if (!TextUtils.isEmpty(nickName)) {
                titleView.setText(nickName);
            } else {
                titleView.setText("");
            }

            String comment = tripList.get(position).getComment();
            if (!TextUtils.isEmpty(comment)) {
                commentView.setText(comment);
            } else {
                commentView.setText("");
            }

        } else {
            if (tripList != null && tripList.size() > 0) {
                String headImage = tripList.get(position).getHeadImg();
                if (!TextUtils.isEmpty(headImage)) {
                    headImageView.setImageURI(Uri.parse(headImage));
                } else {
                    headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
                }

                String nickName = tripList.get(position).getNickname();
                if (!TextUtils.isEmpty(nickName)) {
                    titleView.setText(nickName);
                } else {
                    titleView.setText("");
                }

                String rTitle = list.get(position).getRTitle();
                String content = list.get(position).getContent();
                if (!TextUtils.isEmpty(rTitle)) {
                    if (!TextUtils.isEmpty(content)) {
                        commentView.setText(String.format("%s%s%s", rTitle, "  ", content));
                    } else {
                        commentView.setText(rTitle);
                    }
                } else {
                    if (!TextUtils.isEmpty(content)) {
                        commentView.setText(content);
                    } else {
                        commentView.setText("");
                    }
                }
            } else {
                L.e(TAG, "tripList is null");
            }
        }

        headImageView.setOnClickListener(new HeadImageView(position));

        return convertView;
    }

    private class HeadImageView implements View.OnClickListener {

        private int index;

        private HeadImageView(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            try {
                String userSign = tripList.get(index).getUserSign();
                L.i(TAG, USER_SIGN + ":" + userSign);
                if (!TextUtils.isEmpty(userSign)) {
                    Intent intent = new Intent(context, PersonalMainPagerActivity.class);
                    intent.putExtra(USER_SIGN, userSign);
                    context.startActivity(intent);
                }
            } catch (Exception e) {
                L.e(TAG, "暂无UserSign:" + e.getMessage());
            }
        }

    }

}