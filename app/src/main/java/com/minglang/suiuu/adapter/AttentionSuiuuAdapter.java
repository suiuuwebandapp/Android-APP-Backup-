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
import com.minglang.suiuu.entity.AttentionSuiuu.AttentionSuiuuData.AttentionSuiuuItemData;
import com.minglang.suiuu.utils.Utils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 关注的用户的数据适配器
 * <p/>
 * Created by Administrator on 2015/4/27.
 */
public class AttentionSuiuuAdapter extends BaseAdapter {

    private Context context;

    private List<AttentionSuiuuItemData> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    private int screenWidth;

    public AttentionSuiuuAdapter(Context context) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setList(List<AttentionSuiuuItemData> list) {
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_attention_suiuu, position);
        convertView = holder.getConvertView();

        int itemParams = screenWidth / 3 - Utils.newInstance().dip2px(10, context);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemParams, itemParams);
        convertView.setLayoutParams(params);

        ImageView imageView = holder.getView(R.id.item_attention_suiuu_image);
        TextView titleView = holder.getView(R.id.item_attention_suiuu_title);
        TextView heartView = holder.getView(R.id.item_attention_suiuu_heart);
        TextView commentView = holder.getView(R.id.item_attention_suiuu_comment);

        String imagePath = list.get(position).getTitleImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageLoader.displayImage(imagePath, imageView, options);
        }

        String strTitle = list.get(position).getTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            titleView.setText(strTitle);
        } else {
            titleView.setText("");
        }

        return convertView;
    }

}