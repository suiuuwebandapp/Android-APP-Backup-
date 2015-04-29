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
import com.minglang.suiuu.entity.AttentionLoop;
import com.minglang.suiuu.entity.AttentionLoopData;
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

    private AttentionLoop attentionLoop;

    private List<AttentionLoopData> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions displayImageOptions;

    public AttentionLoopAdapter(Context context, AttentionLoop attentionLoop, List<AttentionLoopData> list) {
        this.context = context;
        this.attentionLoop = attentionLoop;
        this.list = list;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.scroll1)
                .showImageForEmptyUri(R.drawable.scroll1).showImageOnFail(R.drawable.scroll1)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();
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
        } else {

        }

        TextView title = holder.getView(R.id.item_attention_loop_title);
        String str_title = list.get(position).getcName();
        title.setText(str_title);

        return holder.getConvertView();
    }
}
