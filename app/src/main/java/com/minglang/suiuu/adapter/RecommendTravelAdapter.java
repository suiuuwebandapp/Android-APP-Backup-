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
import com.minglang.suiuu.entity.MainDynamicDataRecommendTravel;
import com.minglang.suiuu.utils.Utils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 热门动态数据适配器
 * <p/>
 * Created by Administrator on 2015/5/3.
 */
public class RecommendTravelAdapter extends BaseAdapter {

    private Context context;

    private List<MainDynamicDataRecommendTravel> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions options1, options2;

    private int ScreenHeight;

    public RecommendTravelAdapter(Context context, List<MainDynamicDataRecommendTravel> list) {
        this.context = context;
        this.list = list;

        imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }

        options1 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.user_background)
                .showImageForEmptyUri(R.drawable.user_background).showImageOnFail(R.drawable.user_background)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

        options2 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image).showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

    }

    public void setScreenHeight(int ScreenHeight) {
        this.ScreenHeight = ScreenHeight;
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

        MainDynamicDataRecommendTravel recommendTravel = list.get(position);

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_recommend_reavel, position);
        convertView = holder.getConvertView();
        int itemHeight = ScreenHeight / 4 - Utils.newInstance(context).dip2px(10);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
        convertView.setLayoutParams(params);

        ImageView imageView = holder.getView(R.id.item_recommend_travel_image);
        CircleImageView circleImageView = holder.getView(R.id.item_recommend_travel_head);
        TextView title = holder.getView(R.id.item_recommend_travel_title);
        TextView content = holder.getView(R.id.item_recommend_travel_content);

        String imagePath = recommendTravel.getTitleImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageLoader.displayImage(imagePath, imageView, options1);
        }

        String headPath = recommendTravel.getHeadImg();
        if (!TextUtils.isEmpty(headPath)) {
            imageLoader.displayImage(headPath, circleImageView, options2);
        }

        String strTitle = recommendTravel.getTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            title.setText(strTitle);
        } else {
            title.setText("");
        }

        String strIntro = recommendTravel.getIntro();
        if (!TextUtils.isEmpty(strIntro)) {
            content.setText(strIntro);
        } else {
            content.setText("");
        }

        circleImageView.setOnClickListener(new MyClickListener(position));

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
