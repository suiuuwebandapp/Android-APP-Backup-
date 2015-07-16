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
import com.minglang.suiuu.entity.TripGallery;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/15 11:29
 * 修改人：Administrator
 * 修改时间：2015/7/15 11:29
 * 修改备注：
 */
public class TripGalleryAdapter extends BaseAdapter {

    private Context context;
    private List<TripGallery> list;

    public TripGalleryAdapter(Context context, List<TripGallery> list) {
        this.context = context;
        this.list = list;
    }

    public void onDateChange(List<TripGallery> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_trip_gallery, position);
        convertView = holder.getConvertView();
        SimpleDraweeView picContent = holder.getView(R.id.sdv_trip_gallery_item_picture);
        SimpleDraweeView headPotrait = holder.getView(R.id.civ_trip_gallery_headpotrait);
        TextView tv_location_distance = holder.getView(R.id.tv_trip_gallery_location_distance);
        TextView trip_gallery_name = holder.getView(R.id.tv_trip_gallery_name);
        TextView trip_gallery_tag = holder.getView(R.id.tv_trip_gallery_tag);
        TextView trip_gallery_loveNumber = holder.getView(R.id.tv_trip_gallery_love_number);
        tv_location_distance.setText(list.get(position).getLocationDistance());
        trip_gallery_name.setText(list.get(position).getTripGalleryName());
        trip_gallery_tag.setText(list.get(position).getTripGalleryTag());
        trip_gallery_loveNumber.setText(list.get(position).getLoveNumber());
        Uri uri = Uri.parse(list.get(position).getHeadUrl());
        picContent.setImageURI(uri);
        if(!TextUtils.isEmpty(list.get(position).getHeadPotrait())) {
            Uri uri1 = Uri.parse(list.get(position).getHeadPotrait());
            headPotrait.setImageURI(uri1);
        }
        return convertView;
    }
}
