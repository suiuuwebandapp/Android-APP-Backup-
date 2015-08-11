package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.Published.PublishedData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 发布的随游数据适配器
 * <p/>
 * Created by Administrator on 2015/6/19.
 */
public class PublishedAdapter extends BaseAdapter {

    private Context context;
    private List<PublishedData> list;

    public PublishedAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<PublishedData> list) {
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_published_layout, position);
        convertView = holder.getConvertView();

        SimpleDraweeView publishImage = holder.getView(R.id.item_published_image);
        TextView publishTitle = holder.getView(R.id.item_published_title);
        TextView publishIntro = holder.getView(R.id.item_published_intro);

        PublishedData publishedData = list.get(position);

        String imagePath = publishedData.getTitleImg();
        if (!TextUtils.isEmpty(imagePath)) {
            Uri uri = Uri.parse(imagePath);
            publishImage.setImageURI(uri);
        }

        String title = publishedData.getTitle();
        if (!TextUtils.isEmpty(title)) {
            publishTitle.setText(title);
        } else {
            publishTitle.setText("暂无标题");
        }

        String intro = publishedData.getIntro();
        if (!TextUtils.isEmpty(intro)) {
            publishIntro.setText(intro);
        } else {
            publishIntro.setText("暂无简介");
        }

        return convertView;
    }

}