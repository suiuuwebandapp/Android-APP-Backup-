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
import com.minglang.suiuu.entity.AttentionUserData;
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
public class AttentionUserAdapter extends BaseAdapter {

    private Context context;

    private List<AttentionUserData> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions displayImageOptions;

    public AttentionUserAdapter(Context context, List<AttentionUserData> list) {
        this.context = context;
        this.list = list;

        imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }

        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image2)
                .showImageForEmptyUri(R.drawable.default_head_image2).showImageOnFail(R.drawable.default_head_image2)
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

        AttentionUserData attentionUserData = list.get(position);

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_attention_user, position);

        ImageView headImage = holder.getView(R.id.item_attention_user_head_image);
        TextView title = holder.getView(R.id.item_attention_user_title);
        TextView content = holder.getView(R.id.item_attention_user_content);

        String headImagePath = attentionUserData.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(headImagePath, headImage, displayImageOptions);
        }

        String str_title = attentionUserData.getNickname();
        if (!TextUtils.isEmpty(str_title)) {
            title.setText(str_title);
        } else {
            title.setText("");
        }


        String str_content = attentionUserData.getIntro();
        if (!TextUtils.isEmpty(str_content)) {
            content.setText(str_content);
        } else {
            content.setText("");
        }
        return holder.getConvertView();
    }
}
