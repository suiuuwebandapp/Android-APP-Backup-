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
import com.minglang.suiuu.entity.TripImage.TripImageData.TripImageItemData;
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
public class TripImageAdapter extends BaseAdapter {

    //private static final String LAT = "lat";
    //private static final String LNG = "lng";

    private static final String USER_SIGN = "userSign";

    private Context context;
    private List<TripImageItemData> list;

    //private String lat;
    //private String lng;

    public TripImageAdapter(Context context, List<TripImageItemData> list) {
        this.context = context;
        this.list = list;
        //Map map = SuiuuInfo.ReadUserLocation(context);
        //this.lat = (String) map.get(LAT);
        //this.lng = (String) map.get(LNG);
    }

    public void updateData(List<TripImageItemData> list) {
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
        ViewHolder holderData = ViewHolder.get(context, convertView, parent, R.layout.item_trip_image, position);
        convertView = holderData.getConvertView();

        SimpleDraweeView picContent = holderData.getView(R.id.trip_image_item_picture);
        SimpleDraweeView headImageView = holderData.getView(R.id.trip_image_head_portrait);
        //TextView distanceView = holderData.getView(R.id.trip_image_location_distance);
        TextView titleView = holderData.getView(R.id.trip_image_title);
        TextView tagView = holderData.getView(R.id.trip_image_tag);
        TextView loveNumberView = holderData.getView(R.id.trip_image_love_number);

        TripImageItemData itemData = list.get(position);

        //String longitude = itemData.getLon();
        //String latitude = itemData.getLat();
        //String cityName = itemData.getCity();
        //if (!TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(cityName)) {
        //double distance = AppUtils.distanceByLngLat(Double.valueOf(lng), Double.valueOf(lat),
        //Double.valueOf(longitude), Double.valueOf(latitude));
        //distanceView.setText(String.format("%s%s%s%s", cityName, "\n", distance, "KM"));
        //} else {
        //distanceView.setText("");
        //}

        String title = itemData.getTitle();
        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        } else {
            titleView.setText("");
        }

        String tags = itemData.getTags();
        if (!TextUtils.isEmpty(tags)) {
            tagView.setText(tags.replace(",", " "));
        } else {
            tagView.setText("");
        }

        String attentionCount = itemData.getAttentionCount();
        if (!TextUtils.isEmpty(attentionCount)) {
            loveNumberView.setText(attentionCount);
        } else {
            loveNumberView.setText("");
        }

        String titleImagePath = itemData.getTitleImg();
        if (!TextUtils.isEmpty(titleImagePath)) {
            picContent.setImageURI(Uri.parse(titleImagePath));
        } else {
            picContent.setImageURI(Uri.parse("res://com.suiuu.minglang/" + R.drawable.loading_error));
        }

        String headImagePath = itemData.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            headImageView.setImageURI(Uri.parse(itemData.getHeadImg()));
        } else {
            headImageView.setImageURI(Uri.parse("res://com.suiuu.minglang/" + R.drawable.default_head_image_error));
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
            String userSign = list.get(index).getUserSign();
            Intent intent = new Intent(context, PersonalMainPagerActivity.class);
            intent.putExtra(USER_SIGN, userSign);
            context.startActivity(intent);
        }

    }

}