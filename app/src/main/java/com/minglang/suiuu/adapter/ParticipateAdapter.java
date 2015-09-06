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
import com.minglang.suiuu.entity.Participate;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 参加的随游数据适配器
 * <p/>
 * Created by Administrator on 2015/6/23.
 */
public class ParticipateAdapter extends BaseAdapter {

    private Context context;
    private List<Participate.ParticipateData> list;

    public ParticipateAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Participate.ParticipateData> list) {
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_participate_layout, position);
        convertView = holder.getConvertView();

        SimpleDraweeView participateImage = holder.getView(R.id.item_participate_image);
        TextView participateTitle = holder.getView(R.id.item_participate_title);
        TextView participateIntro = holder.getView(R.id.item_participate_intro);

        Participate.ParticipateData participateData = list.get(position);

        String imagePath = participateData.getTitleImg();
        if (!TextUtils.isEmpty(imagePath)) {
            Uri uri = Uri.parse(imagePath);
            participateImage.setImageURI(uri);
        }

        String title = participateData.getTitle();
        if (!TextUtils.isEmpty(title)) {
            participateTitle.setText(title);
        } else {
            participateTitle.setText("暂无标题");
        }

        String intro = participateData.getIntro();
        if (!TextUtils.isEmpty(intro)) {
            participateIntro.setText(intro);
        } else {
            participateIntro.setText("暂无简介");
        }

        return convertView;
    }

}
