package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * 我的随游数据适配器
 */
public class MySuiuuAdapter extends BaseAdapter {

    private Context context;

    private int screenHeight;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    public MySuiuuAdapter(Context context) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    @Override
    public int getCount() {
        return 0;
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_my_suiuu, position);
        convertView = holder.getConvertView();

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight / 4);
        convertView.setLayoutParams(params);

        ImageView mainImageView = holder.getView(R.id.item_my_suiuu_image);
        imageLoader.displayImage("", mainImageView, options);

        TextView titleView = holder.getView(R.id.item_my_suiuu_title);

        TextView content = holder.getView(R.id.item_my_suiuu_content);

        ImageView tagImageView = holder.getView(R.id.item_my_suiuu_tag);

        return convertView;
    }
}
