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
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.AllAttentionDynamicData;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 详细关注数据适配器
 * <p/>
 * Created by Administrator on 2015/4/30.
 */
public class AllAttentionDynamicAdapter extends BaseAdapter {

    private Context context;

    private List<AllAttentionDynamicData> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions displayImageOptions;

    public AllAttentionDynamicAdapter(Context context){
        this.context = context;
    }

    public AllAttentionDynamicAdapter(Context context, List<AllAttentionDynamicData> list) {
        this.context = context;
        this.list = list;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_background_image)
                .showImageForEmptyUri(R.drawable.default_background_image).showImageOnFail(R.drawable.default_background_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();
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

    public void setList(List<AllAttentionDynamicData> list) {
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AllAttentionDynamicData data = list.get(position);

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_all_attention_dtnamic, position);

        ImageView mainImage = holder.getView(R.id.item_all_attention_dynamic_image);
        CircleImageView headImage = holder.getView(R.id.item_all_attention_dynamic_head);
        TextView title = holder.getView(R.id.item_all_attention_dynamic_title);
        TextView content = holder.getView(R.id.item_all_attention_dynamic_content);

        String mainImagePath = data.getaImg();
        if (!TextUtils.isEmpty(mainImagePath)) {
            imageLoader.displayImage(mainImagePath, mainImage, displayImageOptions);
        }

        String headImagePath = data.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(headImagePath, headImage);
        }

        String strTitle = data.getaTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            title.setText(strTitle);
        } else {
            title.setText("");
        }

        String strContent = data.getaContent();
        if (!TextUtils.isEmpty(strContent)) {
            content.setText(strContent);
        } else {
            content.setText("");
        }

        convertView = holder.getConvertView();
        return convertView;
    }
}
