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
import com.minglang.suiuu.entity.MainCommunity;
import com.minglang.suiuu.entity.MainCommunity.MainCommunityData.MainCommunityItemData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 首页-问答社区-数据适配器
 * <p/>
 * Created by Administrator on 2015/7/3.
 */
public class CommunityAdapter extends BaseAdapter {

    private Context context;

    private List<MainCommunityItemData> list;

    public CommunityAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<MainCommunity.MainCommunityData.MainCommunityItemData> list) {
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_community, position);
        convertView = holder.getConvertView();

        SimpleDraweeView headImageView = holder.getView(R.id.item_community_header_image);
        TextView countView = holder.getView(R.id.item_community_count);
        TextView titleView = holder.getView(R.id.item_community_title);
        TextView cotentView = holder.getView(R.id.item_community_content);

        MainCommunityItemData itemData = list.get(position);

        String headImagePath = itemData.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            headImageView.setImageURI(Uri.parse(headImagePath));
        } else {
            headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
        }

        String strCount = itemData.getANumber();
        if (!TextUtils.isEmpty(strCount)) {
            countView.setText(strCount);
        } else {
            countView.setText("0");
        }

        String strTitle = itemData.getQTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            titleView.setText(strTitle);
        } else {
            titleView.setText("");
        }

        String strCotent = itemData.getQContent();
        if (!TextUtils.isEmpty(strCotent)) {
            cotentView.setText(strCotent);
        } else {
            cotentView.setText("");
        }

        return convertView;
    }

}