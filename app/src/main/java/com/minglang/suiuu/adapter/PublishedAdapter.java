package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.Published;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 发布的随游数据适配器
 * <p/>
 * Created by Administrator on 2015/6/19.
 */
public class PublishedAdapter extends BaseAdapter {

    private Context context;

    private List<Published.PublishedData> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    public PublishedAdapter(Context context) {
        this.context = context;
        init();
    }

    public PublishedAdapter(Context context, List<Published.PublishedData> list) {
        this.context = context;
        this.list = list;
        init();
    }

    private void init() {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setList(List<Published.PublishedData> list) {
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_published_layout, position);
        ImageView publishImage = holder.getView(R.id.item_published_image);
        TextView publishTitle = holder.getView(R.id.item_published_title);
        TextView publishIntro = holder.getView(R.id.item_published_intro);

        Published.PublishedData publishedData = list.get(position);

        String imagePath = publishedData.getTitleImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageLoader.displayImage(imagePath, publishImage, options);
        }

        String title = publishedData.getTitle();
        if (!TextUtils.isEmpty(title)) {
            publishTitle.setText(title);
        } else {
            publishTitle.setText("暂无标题");
        }

        String intro = publishedData.getIntro();
        if (!TextUtils.isEmpty(intro)) {
            publishIntro.setText(intro);
        } else {
            publishIntro.setText("暂无简介");
        }

        return convertView;
    }

}
