package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.CollectionSuiuuData;
import com.minglang.suiuu.utils.Utils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 收藏的随游数据适配器
 * <p/>
 * Created by Administrator on 2015/5/5.
 */
public class CollectionSuiuuAdapter extends BaseAdapter {

    private static final String TAG = CollectionSuiuuAdapter.class.getSimpleName();

    private Context context;

    private List<CollectionSuiuuData> list;

    private int screenWidth;
    private int screenHeight;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    public CollectionSuiuuAdapter(Context context) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();

        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.scroll1)
                .showImageForEmptyUri(R.drawable.scroll1).showImageOnFail(R.drawable.scroll1)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public CollectionSuiuuAdapter(Context context, List<CollectionSuiuuData> list) {
        this.context = context;
        this.list = list;

        imageLoader = ImageLoader.getInstance();

        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.scroll1)
                .showImageForEmptyUri(R.drawable.scroll1).showImageOnFail(R.drawable.scroll1)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();

    }

    public void setScreenParams(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        Log.i(TAG, "屏幕显示区域高度为:" + String.valueOf(this.screenHeight));
    }

    public void setListData(List<CollectionSuiuuData> list) {
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

        CollectionSuiuuData data = list.get(position);

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_collection_suiuu, position);
        ImageView mainImage = holder.getView(R.id.item_collection_suiuu_image);
        CircleImageView headImage = holder.getView(R.id.item_collection_suiuu_head_image);
        TextView userName = holder.getView(R.id.item_collection_suiuu_name);
        TextView title = holder.getView(R.id.item_collection_suiuu_title);
        RatingBar ratingBar = holder.getView(R.id.item_collection_loop_indicator);

        String imagePath = data.getTitleImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageLoader.displayImage(imagePath.trim(), mainImage, options);
        }
        Log.i(TAG, "收藏随游图片地址:" + imagePath);

        String headImagePath = data.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(headImagePath.trim(), headImage);
        }
        Log.i(TAG, "收藏随游用户头像地址:" + headImagePath);

        String strUserName = data.getNickname();
        if (!TextUtils.isEmpty(strUserName)) {
            userName.setText(strUserName);
        } else {
            userName.setText("");
        }

        String strTitle = data.getTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            title.setText(strTitle);
        } else {
            title.setText("");
        }

        String strScore = data.getScore();
        if (!TextUtils.isEmpty(strScore)) {
            float score = Float.valueOf(strScore);
            ratingBar.setRating(score);
        } else {
            ratingBar.setRating(0f);
        }

        convertView = holder.getConvertView();

        int itemParams = screenWidth / 2 - Utils.newInstance(context).dip2px(10);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemParams, itemParams);
        convertView.setLayoutParams(params);

        return convertView;
    }
}
