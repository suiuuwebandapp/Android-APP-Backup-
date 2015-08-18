package com.minglang.suiuu.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SuiuuSearchActivity;
import com.minglang.suiuu.entity.TripGallery;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.ArrayList;
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
public class TripGalleryAdapter1 extends BaseAdapter {
    public static final int SEARCH = 0;// 7种不同的布局
    public static final int SETTAG = 1;
    public static final int DATA = 2;
    private Context context;
    private List<TripGallery.DataEntity.TripGalleryDataInfo> list;
    private String lat;
    private String lng;

    private List<ImageView> clickImageList = new ArrayList<>();
    private List<ImageView> imageList = new ArrayList<>();
    private List<String> clickString = new ArrayList<>();
    private String clickTag = "";

    public TripGalleryAdapter1(Context context, List<TripGallery.DataEntity.TripGalleryDataInfo> list) {
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
            return list.size() + 2;
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
        //顶部的搜索页面
        int type = getItemViewType(position);
        switch (type) {
            case SEARCH:
                ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_trip_gallery1, position);
                convertView = holder.getConvertView();
                TextView trip_gallery_search = holder.getView(R.id.trip_gallery_search);
                trip_gallery_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SuiuuSearchActivity.class);
                        intent.putExtra("searchClass", 1);
                        context.startActivity(intent);
                    }
                });
                break;
            case SETTAG:
                ViewHolder holder1 = ViewHolder.get(context, convertView, parent, R.layout.item_trip_gallery2, position);
                convertView = holder1.getConvertView();
                LinearLayout trip_gallery_search1 = holder1.getView(R.id.galleryLinearLayout);
                showTag(trip_gallery_search1);
                break;
            case DATA:
                ViewHolder holderData = ViewHolder.get(context, convertView, parent, R.layout.item_trip_gallery, position);
                convertView = holderData.getConvertView();
                SimpleDraweeView picContent = holderData.getView(R.id.sdv_trip_gallery_item_picture);
                SimpleDraweeView headPortrait = holderData.getView(R.id.civ_trip_gallery_head_portrait);
                TextView tv_location_distance = holderData.getView(R.id.tv_trip_gallery_location_distance);
                TextView trip_gallery_name = holderData.getView(R.id.tv_trip_gallery_name);
                TextView trip_gallery_tag = holderData.getView(R.id.tv_trip_gallery_tag);
                TextView trip_gallery_loveNumber = holderData.getView(R.id.tv_trip_gallery_love_number);
                LatLng lngLat = new LatLng(Double.valueOf(list.get(position-2).getLon()),
                        Double.valueOf(list.get(position-2).getLat()));
                LatLng lngLat1 = new LatLng(Double.valueOf(lng), Double.valueOf(lat));
                float v1 = AMapUtils.calculateLineDistance(lngLat, lngLat1);
                tv_location_distance.setText(String.valueOf((double)v1).length() >= 4 ?
                        String.valueOf(v1).substring(0, 4) : String.valueOf(v1));
                trip_gallery_name.setText(list.get(position-2).getTitle());
                trip_gallery_tag.setText(list.get(position-2).getTags().replace(",", " "));
                trip_gallery_loveNumber.setText(list.get(position-2).getAttentionCount());
                Uri uri = Uri.parse(list.get(position-2).getTitleImg());
                picContent.setImageURI(uri);
                if (!TextUtils.isEmpty(list.get(position-2).getHeadImg())) {
                    Uri uri1 = Uri.parse(list.get(position-2).getHeadImg());
                    headPortrait.setImageURI(uri1);
                }
                break;
            default:
                break;
        }
        return convertView;
    }


    private void showTag(LinearLayout mGalleryLinearLayout) {
        int[] mPhotosIntArray = new int[]{R.drawable.tag_family, R.drawable.tag_shopping, R.drawable.tag_nature,
                R.drawable.tag_dangerous, R.drawable.tag_romantic, R.drawable.tag_museam, R.drawable.tag_novelty};
        final String[] mTagIntArray = context.getResources().getStringArray(R.array.tripGalleryTagName);
        View itemView;
        ImageView imageView;
        TextView textView;
        for (int i = 0; i < mPhotosIntArray.length; i++) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_trip_gallery_tag, null);
            itemView.setTag(i);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_trip_gallery_tag);
            textView = (TextView) itemView.findViewById(R.id.tv_item_trip_gallery_tag);
            imageList.add(imageView);
            imageView.setImageResource(mPhotosIntArray[i]);
            textView.setText(mTagIntArray[i]);
            itemView.setPadding(10, 0, 0, 0);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTag = "";
                    int tag = (int) v.getTag();
                    if (clickImageList.contains(imageList.get(tag))) {
                        imageList.get(tag).setBackgroundResource(0);
                        clickImageList.remove(imageList.get(tag));
                        clickString.remove(mTagIntArray[tag]);
                    } else {
                        imageList.get(tag).setBackgroundResource(R.drawable.imageview_border_style);
                        clickImageList.add(imageList.get(tag));
                        clickString.add(mTagIntArray[tag]);
                    }
                }
            });
            mGalleryLinearLayout.addView(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position > 1) {
            return DATA;
        }
        return position;
    }

    /**
     * 返回所有的layout的数量
     */
    @Override
    public int getViewTypeCount() {
        return 3;
    }
}