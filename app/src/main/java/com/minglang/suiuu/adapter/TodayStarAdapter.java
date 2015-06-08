package com.minglang.suiuu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.OtherUserActivity;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.MainDynamicDataRecommendUser;
import com.minglang.suiuu.utils.Utils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * 首页，今日之星数据适配器
 * <p/>
 * Created by Administrator on 2015/4/29.
 */
public class TodayStarAdapter extends BaseAdapter {

    private Context context;

    private List<MainDynamicDataRecommendUser> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions options1, options2;

    private int screenWidth;

    private ImageLoadingListener imageLoadingListener;

    public TodayStarAdapter(Context context, List<MainDynamicDataRecommendUser> list) {
        this.context = context;
        this.list = list;

        imageLoader = ImageLoader.getInstance();

        options1 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

        options2 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image).showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setScreenParams(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setImageLoadingListener(ImageLoadingListener imageLoadingListener) {
        this.imageLoadingListener = imageLoadingListener;
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_today_star, position);

        ImageView imageView = holder.getView(R.id.item_today_star_image);
        CircleImageView headImageViw = holder.getView(R.id.item_today_star_head_image);
        TextView userName = holder.getView(R.id.item_today_star_user_name);
        TextView fansCount = holder.getView(R.id.item_today_star_fans_count);

        String imagePath = list.get(position).getrImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageLoader.displayImage(imagePath, imageView, options1, imageLoadingListener);
        }

        String headImagePath = list.get(position).getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(headImagePath, headImageViw, options2);
        }

        String strUserName = list.get(position).getNickname();
        if (!TextUtils.isEmpty(strUserName)) {
            userName.setText(strUserName);
        } else {
            userName.setText("");
        }

        String strFansCount = list.get(position).getNumb();
        if (!TextUtils.isEmpty(strFansCount)) {
            fansCount.setText(strFansCount);
        } else {
            fansCount.setText("0");
        }

        headImageViw.setTag(position);
        headImageViw.setOnClickListener(new MyClickListener(position));

        convertView = holder.getConvertView();

        int itemParams = screenWidth / 2 - Utils.newInstance().dip2px(10, context);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemParams, itemParams);

        convertView.setLayoutParams(params);

        return convertView;
    }

    private class MyClickListener implements View.OnClickListener {

        private int index;

        private MyClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            String userSign = list.get(index).getUserSign();
            Intent intent = new Intent(context, OtherUserActivity.class);
            intent.putExtra("userSign", userSign);
            context.startActivity(intent);
        }
    }

}
