package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.AttentionGallery.AttentionGalleryData.AttentionGalleryItemData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 关注的圈子的数据适配器
 * <p/>
 * Created by Administrator on 2015/4/27.
 */
public class AttentionGalleryAdapter extends BaseAdapter {

    private Context context;

    private List<AttentionGalleryItemData> list;

    private int screenWidth;

    public AttentionGalleryAdapter(Context context) {
        this.context = context;
    }

    public void setScreenParams(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setList(List<AttentionGalleryItemData> list) {
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_attention_gallery, position);
        convertView = holder.getConvertView();

        int itemLength = screenWidth / 2;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemLength, AbsListView.LayoutParams.WRAP_CONTENT);
        convertView.setLayoutParams(params);

        SimpleDraweeView imageView = holder.getView(R.id.item_attention_gallery_image);
        SimpleDraweeView headView = holder.getView(R.id.item_attention_gallery_head_image_view);
        TextView titleView = holder.getView(R.id.item_attention_gallery_title);

        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(itemLength, itemLength);
        imageView.setLayoutParams(imageParams);

        String imagePath = list.get(position).getTitleImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageView.setImageURI(Uri.parse(imagePath));
        } else {
            imageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.loading_error));
        }

        String headImagePath = list.get(position).getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            headView.setImageURI(Uri.parse(headImagePath));
        } else {
            imageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image));
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