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
import com.minglang.suiuu.entity.AttentionLoopData;
import com.minglang.suiuu.utils.Utils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 关注的圈子的数据适配器
 * <p/>
 * Created by Administrator on 2015/4/27.
 */
public class AttentionLoopAdapter extends BaseAdapter {

    private Context context;


    private List<AttentionLoopData> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions displayImageOptions;

    private int screenWidth;

    public AttentionLoopAdapter(Context context) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }

        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.user_background)
                .showImageForEmptyUri(R.drawable.user_background).showImageOnFail(R.drawable.user_background)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setScreenParams(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setList(List<AttentionLoopData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null) {
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_attention_loop, position);

        ImageView imageView = holder.getView(R.id.item_attention_loop_image);
        String imageURL = list.get(position).getCpic();
        if (!TextUtils.isEmpty(imageURL)) {
            imageLoader.displayImage(imageURL, imageView, displayImageOptions);
        }

        TextView title = holder.getView(R.id.item_attention_loop_title);
        String str_title = list.get(position).getcName();
        title.setText(str_title);

        convertView = holder.getConvertView();

        int itemParams = screenWidth / 3 - Utils.newInstance(context).dip2px(10);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemParams, itemParams);
        convertView.setLayoutParams(params);

        return convertView;
    }
}
