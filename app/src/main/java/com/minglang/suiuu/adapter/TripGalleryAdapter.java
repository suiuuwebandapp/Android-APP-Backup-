package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.TripGallery;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;
import java.util.Map;


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
    private List<TripGallery.DataEntity.TripGalleryDataInfo> list;
    private String lat;
    private String lng;

    public TripGalleryAdapter(Context context, List<TripGallery.DataEntity.TripGalleryDataInfo> list) {
        this.context = context;
        this.list = list;
        Map map = SuiuuInfo.ReadUserLocation(context);
        this.lat = (String) map.get("lat");
        this.lng = (String) map.get("lng");

    }

    public void onDateChange(List<TripGallery.DataEntity.TripGalleryDataInfo> list) {
        this.list = list;
        this.notifyDataSetChanged();
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_trip_gallery, position);
        convertView = holder.getConvertView();
        SimpleDraweeView picContent = holder.getView(R.id.sdv_trip_gallery_item_picture);
        SimpleDraweeView headPortrait = holder.getView(R.id.civ_trip_gallery_head_portrait);
        TextView tv_location_distance = holder.getView(R.id.tv_trip_gallery_location_distance);
        TextView trip_gallery_name = holder.getView(R.id.tv_trip_gallery_name);
        TextView trip_gallery_tag = holder.getView(R.id.tv_trip_gallery_tag);
        TextView trip_gallery_loveNumber = holder.getView(R.id.tv_trip_gallery_love_number);
        LatLng lngLat = new LatLng(Double.valueOf(list.get(position).getLon()),
                Double.valueOf(list.get(position).getLat()));
        LatLng lngLat1 = new LatLng(Double.valueOf(lng), Double.valueOf(lat));
        float v1 = AMapUtils.calculateLineDistance(lngLat, lngLat1);
        tv_location_distance.setText(String.valueOf(v1).length() >= 4 ?
                String.valueOf(v1).substring(0, 4) : String.valueOf(v1));
        trip_gallery_name.setText(list.get(position).getTitle());
        trip_gallery_tag.setText(list.get(position).getTags().replace(",", " "));
        trip_gallery_loveNumber.setText(list.get(position).getAttentionCount());
        Uri uri = Uri.parse(list.get(position).getTitleImg());
        picContent.setImageURI(uri);
        if (!TextUtils.isEmpty(list.get(position).getHeadImg())) {
            Uri uri1 = Uri.parse(list.get(position).getHeadImg());
            headPortrait.setImageURI(uri1);
        }
        return convertView;
    }

}