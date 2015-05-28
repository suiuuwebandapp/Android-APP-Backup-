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
import com.minglang.suiuu.entity.MainDynamicDataUser;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * 首页关注动态数据适配器
 * <p/>
 * Created by Administrator on 2015/4/30.
 */
public class AttentionDynamicAdapter extends BaseAdapter {

    private Context context;

    private List<MainDynamicDataUser> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions options1, options2;

    private int ScreenHeight;

    private ImageLoadingListener imageLoadingListener;

    public AttentionDynamicAdapter(Context context, List<MainDynamicDataUser> list) {
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

    public void setScreenHeight(int ScreenHeight) {
        this.ScreenHeight = ScreenHeight;
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
        MainDynamicDataUser user = list.get(position);

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_attention_dynamic, position);
        convertView = holder.getConvertView();
        int itemHeight = ScreenHeight / 4;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
        convertView.setLayoutParams(params);

        ImageView imageView = holder.getView(R.id.item_attention_dynamic_image);
        CircleImageView circleImageView = holder.getView(R.id.item_attention_dynamic_head);
        TextView title = holder.getView(R.id.item_attention_dynamic_title);
        TextView content = holder.getView(R.id.item_attention_dynamic_content);

        String imagePath = user.getaImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageLoader.displayImage(imagePath, imageView, options1,imageLoadingListener);
        }

        String headPath = user.getHeadImg();
        if (!TextUtils.isEmpty(headPath)) {
            imageLoader.displayImage(headPath, circleImageView, options2);
        }

        String strTitle = user.getaTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            title.setText(strTitle);
        } else {
            title.setText("");
        }

        String strContent = user.getaContent();
        if (!TextUtils.isEmpty(strContent)) {
            content.setText(strContent);
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
