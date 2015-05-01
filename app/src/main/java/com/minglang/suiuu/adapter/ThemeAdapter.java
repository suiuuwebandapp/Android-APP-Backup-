package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.Loop;
import com.minglang.suiuu.entity.LoopData;
import com.minglang.suiuu.utils.Utils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 圈子-主题 数据适配器
 * <p/>
 * Created by Administrator on 2015/4/21.
 */
public class ThemeAdapter extends BaseAdapter {

    private static final String TAG = ThemeAdapter.class.getSimpleName();

    private Context context;

    private Loop loopInfo;

    private List<LoopData> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions displayImageOptions;

    private String url = "http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150414141605_50758.png";

    private int screenWidth, screenHeight;

    public ThemeAdapter(Context context, Loop loopInfo, List<LoopData> list) {
        this.context = context;
        this.loopInfo = loopInfo;
        this.list = list;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.scroll1)
                .showImageForEmptyUri(R.drawable.scroll1).showImageOnFail(R.drawable.scroll1)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();

        Log.i(TAG, String.valueOf(list.size()));
    }

    public void setScreenParams(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
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

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_theme_grid, position);

        ImageView imageView = holder.getView(R.id.item_theme_image);
        TextView title = holder.getView(R.id.item_theme_title);

        LoopData loopData = list.get(position);

        imageLoader.displayImage(url, imageView, displayImageOptions);
        title.setText(loopData.getcName());

        int itemParams = screenWidth / 2 - Utils.newInstance(context).dip2px(10);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemParams, itemParams);

        convertView = holder.getConvertView();
        convertView.setLayoutParams(params);

        return convertView;
    }
}
