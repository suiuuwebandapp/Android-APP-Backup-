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
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.LoopDetailsDataList;
import com.minglang.suiuu.utils.Utils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 圈子详情页数据适配器
 * <p/>
 * Created by Administrator on 2015/4/22.
 */
public class LoopDetailsAdapter extends BaseAdapter {

    private static final String TAG = LoopDetailsAdapter.class.getSimpleName();

    private Context context;

    private List<LoopDetailsDataList> list;

    private ImageLoader loader;

    private DisplayImageOptions displayImageOptions1, displayImageOptions2;

    private int screenWidth, screenHeight;

    public LoopDetailsAdapter(Context context) {
        this.context = context;

        loader = ImageLoader.getInstance();
        if (loader.isInited()) {
            loader.init(ImageLoaderConfiguration.createDefault(context));
        }

        displayImageOptions1 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_background_image)
                .showImageForEmptyUri(R.drawable.default_background_image).showImageOnFail(R.drawable.default_background_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

        displayImageOptions2 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image2)
                .showImageForEmptyUri(R.drawable.default_head_image2).showImageOnFail(R.drawable.default_head_image2)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public LoopDetailsAdapter(Context context, List<LoopDetailsDataList> list) {
        this.context = context;
        this.list = list;

        loader = ImageLoader.getInstance();
        if (loader.isInited()) {
            loader.init(ImageLoaderConfiguration.createDefault(context));
        }

        displayImageOptions1 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_background_image)
                .showImageForEmptyUri(R.drawable.default_background_image).showImageOnFail(R.drawable.default_background_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

        displayImageOptions2 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image2)
                .showImageForEmptyUri(R.drawable.default_head_image2).showImageOnFail(R.drawable.default_head_image2)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();

        Log.i(TAG, String.valueOf(this.list.size()));
    }

    public void setScreenParams(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void setDataList(List<LoopDetailsDataList> list) {
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

        LoopDetailsDataList loopDetailsDataList = list.get(position);

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_loop_details, position);

        ImageView mainImageView = holder.getView(R.id.item_loop_details_image);
        CircleImageView headImage = holder.getView(R.id.item_loop_details_head_image);

        TextView userName = holder.getView(R.id.item_loop_details_user_name);

        TextView title = holder.getView(R.id.item_loop_details_title);
        TextView praise = holder.getView(R.id.item_loop_details_praise);
        TextView comments = holder.getView(R.id.item_loop_details_comments);

        //加载主图片
        String imagePath = loopDetailsDataList.getaImg();
        Log.i(TAG, "imagePath:" + imagePath);
        if (!TextUtils.isEmpty(imagePath)) {
            loader.displayImage(imagePath, mainImageView, displayImageOptions1);
        }

        String headImagePath = loopDetailsDataList.getHeadImg();
        Log.i(TAG, "headImagePath:" + headImagePath);
        //加载头像
        if (!TextUtils.isEmpty(headImagePath)) {
            loader.displayImage(headImagePath, headImage, displayImageOptions2);
        }

        //加载用户名
        String str_userName = loopDetailsDataList.getNickname();
        if (TextUtils.isEmpty(str_userName)) {
            userName.setText("匿名");
        } else {
            userName.setText(str_userName);
        }

        //加载标题
        String str_title = loopDetailsDataList.getaTitle();
        title.setText(str_title);

        //加载评论数
        String comments_str = loopDetailsDataList.getaCmtCount();
        if (TextUtils.isEmpty(comments_str)) {
            comments.setText("0");
        } else {
            comments.setText(comments_str);
        }

        //加载被赞数
        String praise_str = loopDetailsDataList.getaSupportCount();
        if (TextUtils.isEmpty(praise_str)) {
            praise.setText("0");
        } else {
            praise.setText(praise_str);
        }

        int itemParams = screenWidth / 2 - Utils.newInstance(context).dip2px(10);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemParams, itemParams);

        convertView = holder.getConvertView();
        convertView.setLayoutParams(params);

        return convertView;
    }
}
