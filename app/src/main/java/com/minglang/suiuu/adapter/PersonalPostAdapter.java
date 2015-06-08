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
import com.minglang.suiuu.entity.OtherUserDataArticle;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.Utils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 用户个人主页贴子列表数据适配器
 * <p/>
 * Created by Administrator on 2015/5/11.
 */
public class PersonalPostAdapter extends BaseAdapter {

    private Context context;

    private List<OtherUserDataArticle> list;

    private ImageLoader imageLoader;

    private int screenWidth;

    private DisplayImageOptions displayImageOptions;

    public PersonalPostAdapter(Context context) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();
        screenWidth = new ScreenUtils((Activity) context).getScreenWidth();
        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setList(List<OtherUserDataArticle> list) {
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

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_personal_post, position);
        ImageView mainImageView = holder.getView(R.id.item_personal_post_image);
        TextView content = holder.getView(R.id.item_personal_post_content);
        TextView comment = holder.getView(R.id.item_personal_post_comments);
        TextView praise = holder.getView(R.id.item_personal_post_praise);

        String strImagePath = list.get(position).getaImg();
        if (!TextUtils.isEmpty(strImagePath)) {
            imageLoader.displayImage(strImagePath, mainImageView, displayImageOptions);
        }

        String strContent = list.get(position).getaTitle();
        if (!TextUtils.isEmpty(strContent)) {
            content.setText(strContent);
        } else {
            content.setText("");
        }

        String strComment = list.get(position).getaCmtCount();
        if (!TextUtils.isEmpty(strComment)) {
            comment.setText(strComment);
        } else {
            comment.setText("0");
        }

        String strPraise = list.get(position).getaSupportCount();
        if (!TextUtils.isEmpty(strPraise)) {
            praise.setText(strPraise);
        } else {
            praise.setText("0");
        }

        convertView = holder.getConvertView();
        int itemWidth = screenWidth / 2 - Utils.newInstance().dip2px(10,context);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemWidth, itemWidth);
        convertView.setLayoutParams(params);

        return convertView;
    }
}
