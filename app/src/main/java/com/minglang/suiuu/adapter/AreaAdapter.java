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
import com.minglang.suiuu.entity.LoopBaseData;
import com.minglang.suiuu.utils.Utils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 圈子-地区 数据适配器
 * <p/>
 * Created by Administrator on 2015/4/21.
 */
public class AreaAdapter extends BaseAdapter {

    private Context context;

    private List<LoopBaseData> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    private int screenWidth;

    public  AreaAdapter(Context context){
        this.context = context;

        imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.scroll5)
                .showImageForEmptyUri(R.drawable.scroll5).showImageOnFail(R.drawable.scroll5)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setList(List<LoopBaseData> list) {
        this.list = list;
        notifyDataSetChanged();
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_area_grid, position);
        ImageView imageView = holder.getView(R.id.item_area_image);
        TextView title = holder.getView(R.id.item_area_title);

        LoopBaseData loopBaseData = list.get(position);

        String imagePath = loopBaseData.getCpic();
        if (!TextUtils.isEmpty(imagePath)) {
            imageLoader.displayImage(imagePath, imageView, options);
        }

        String name = loopBaseData.getcName();
        if (!TextUtils.isEmpty(name)) {
            title.setText(name);
        } else {
            title.setText("");
        }
        int itemParams = screenWidth / 2 - Utils.newInstance(context).dip2px(10);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemParams, itemParams);

        convertView = holder.getConvertView();
        convertView.setLayoutParams(params);

        return convertView;
    }
}
