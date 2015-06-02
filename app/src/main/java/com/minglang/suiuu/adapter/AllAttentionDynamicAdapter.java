package com.minglang.suiuu.adapter;

import android.app.Activity;
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
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.AllAttentionDynamicData;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
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

    private int screenHeight;

    public AllAttentionDynamicAdapter(Context context) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();

        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();

        screenHeight = new ScreenUtils((Activity) context).getScreenHeight();
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

        ViewHolder holder = ViewHolder.get(context, convertView, parent,
                R.layout.item_all_attention_dynamic, position);

        convertView = holder.getConvertView();
        AbsListView.LayoutParams params = new AbsListView
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight / 4);
        convertView.setLayoutParams(params);

        ImageView mainImage = holder.getView(R.id.item_all_attention_dynamic_image);
        CircleImageView headImage = holder.getView(R.id.item_all_attention_dynamic_head);
        TextView title = holder.getView(R.id.item_all_attention_dynamic_title);
        TextView content = holder.getView(R.id.item_all_attention_dynamic_content);

        String mainImagePath = data.getaImg();
        if (!TextUtils.isEmpty(mainImagePath)) {
            imageLoader.displayImage(AppConstant.IMG_FROM_SUIUU_CONTENT + mainImagePath,
                    mainImage, displayImageOptions);
        }

        String headImagePath = data.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(AppConstant.IMG_FROM_SUIUU_CONTENT + headImagePath, headImage);
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

        return convertView;
    }
}
