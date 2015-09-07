package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.AttentionSuiuu.AttentionSuiuuData.AttentionSuiuuItemData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 关注的用户的数据适配器
 * <p/>
 * Created by Administrator on 2015/4/27.
 */
public class AttentionSuiuuAdapter extends BaseAdapter {

    private static final String TAG = AttentionSuiuuAdapter.class.getSimpleName();

    private Context context;

    private List<AttentionSuiuuItemData> list;

    private int screenWidth;

    public AttentionSuiuuAdapter(Context context) {
        this.context = context;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setList(List<AttentionSuiuuItemData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null) {
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_attention_suiuu, position);
        convertView = holder.getConvertView();

        int itemLength = screenWidth / 2;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemLength, AbsListView.LayoutParams.WRAP_CONTENT);
        convertView.setLayoutParams(params);

        SimpleDraweeView imageView = holder.getView(R.id.item_attention_suiuu_image);
        TextView titleView = holder.getView(R.id.item_attention_suiuu_title);
        TextView heartView = holder.getView(R.id.item_attention_suiuu_heart);
        TextView commentView = holder.getView(R.id.item_attention_suiuu_comment);

        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(itemLength, itemLength);
        imageView.setLayoutParams(imageParams);

        String imagePath = list.get(position).getTitleImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageView.setImageURI(Uri.parse(imagePath));
        }

        String strTitle = list.get(position).getTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            titleView.setText(strTitle);
        } else {
            titleView.setText("暂无标题");
        }

        String collectCount = list.get(position).getCollectCount();
        if (!TextUtils.isEmpty(collectCount)) {
            heartView.setText(collectCount);
        } else {
            heartView.setText("0");
        }

        String commentCount = list.get(position).getCommentCount();
        if (!TextUtils.isEmpty(commentCount)) {
            commentView.setText(commentCount);
        } else {
            commentView.setText("0");
        }

        return convertView;

    }

}