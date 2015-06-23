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
import com.minglang.suiuu.entity.Participate;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 参加的随游数据适配器
 * <p/>
 * Created by Administrator on 2015/6/23.
 */
public class ParticipateAdapter extends BaseAdapter {

    private Context context;

    private List<Participate.ParticipateData> list;

    private int screenHeight;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    public ParticipateAdapter(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setList(List<Participate.ParticipateData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
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

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight / 4);
        convertView.setLayoutParams(params);

        ImageView participateImage = holder.getView(R.id.item_participate_image);
        TextView participateTitle = holder.getView(R.id.item_participate_title);
        TextView participateIntro = holder.getView(R.id.item_participate_intro);

        Participate.ParticipateData participateData = list.get(position);

        String imagePath = participateData.getTitleImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageLoader.displayImage(imagePath, participateImage, options);
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
