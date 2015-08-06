package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.MsgTripGallery.MsgTripGalleryData.MsgTripGalleryItemData;
import com.minglang.suiuu.entity.SuiuuMessage.SuiuuMessageBase.SuiuuMessageData;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;


/**
 * 消息数据适配器
 * <p/>
 * Created by Administrator on 2015/5/3.
 */
public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<MsgTripGalleryItemData> list;

    private ImageLoader imageLoader;

    private String type;

    private DisplayImageOptions displayImageOptions;

    public MessageAdapter(Context context) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();

        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image)
                .showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setList(List<MsgTripGalleryItemData> list) {
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_message_layout, position);
        convertView = holder.getConvertView();
        MsgTripGalleryItemData data = list.get(position);

        CircleImageView headImage = holder.getView(R.id.item_message_head_image);
        TextView info = holder.getView(R.id.item_message_info);

        String strHeadImage = data.getHeadImg();
        if (!TextUtils.isEmpty(strHeadImage)) {
            imageLoader.displayImage(strHeadImage, headImage, displayImageOptions);
        }

        String strInfo = data.getNickname();

        return convertView;
    }

}