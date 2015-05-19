package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.MainDynamicDataLoop;
import com.minglang.suiuu.utils.Utils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 圈子动态数据适配器
 * <p/>
 * Created by Administrator on 2015/4/30.
 */
public class LoopDynamicAdapter extends BaseAdapter {

    private Context context;

    private List<MainDynamicDataLoop> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions displayImageOptions;

    private int screenWidth;

    public LoopDynamicAdapter(Context context, List<MainDynamicDataLoop> list) {
        this.context = context;
        this.list = list;

        imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }

        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.user_background)
                .showImageForEmptyUri(R.drawable.user_background).showImageOnFail(R.drawable.user_background)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setScreenParams(int screenWidth) {
        this.screenWidth = screenWidth;
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_loop_dynamic, position);

        ImageView imageView = holder.getView(R.id.item_loop_dynamic_image);
        TextView name = holder.getView(R.id.item_loop_dynamic_address);
        TextView info = holder.getView(R.id.item_loop_dynamic_info);

        String imagePath = list.get(position).getaImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageLoader.displayImage(imagePath, imageView, displayImageOptions);
        }

        String strName = list.get(position).getcName();
        if (!TextUtils.isEmpty(strName)) {
            name.setText(strName);
        }

        String strInfo = list.get(position).getaTitle();
        if (!TextUtils.isEmpty(strInfo)) {
            info.setText(strInfo);
        }

        convertView = holder.getConvertView();

        int itemParams = screenWidth / 2 - Utils.newInstance(context).dip2px(10);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemParams, itemParams);
        convertView.setLayoutParams(params);

        return convertView;
    }
}
