package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.SuiuuDataList;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 项目名称：Suiuu
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/5/4 15:54
 * 修改人：Administrator
 * 修改时间：2015/5/4 15:54
 * 修改备注：
 */
public class ShowSuiuuAdapter extends BaseAdapter {
    private Context context;
    private List<SuiuuDataList> list;
    private DisplayImageOptions options;

    public ShowSuiuuAdapter(Context context, List<SuiuuDataList> list) {
        this.context = context;
        this.list = list;
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_suiuu_image)
                .showImageForEmptyUri(R.drawable.default_suiuu_image).showImageOnFail(R.drawable.default_suiuu_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }
    public void onDateChange(List<SuiuuDataList> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.adapter_suiuu, position);
        convertView = holder.getConvertView();

        SimpleDraweeView picContent = holder.getView(R.id.item_attention_dynamic_image);
        CircleImageView cirleImage = holder.getView(R.id.ci_suiuu_img);
        TextView tv_suiuu_name = holder.getView(R.id.tv_item_suiuu_name);
        TextView tv_item_suiuu_price = holder.getView(R.id.tv_item_suiuu_price);
        RatingBar rb_suiuu_star = holder.getView(R.id.rb_suiuu_star);
        rb_suiuu_star.setRating(Integer.valueOf(list.get(position).getScore()));
        tv_item_suiuu_price.setText("￥:  " +list.get(position).getBasePrice());
        ImageLoader.getInstance().displayImage(list.get(position).getHeadImg(), cirleImage, options);
        tv_suiuu_name.setText(list.get(position).getTitle().trim().toString());
        Uri uri = Uri.parse(list.get(position).getTitleImg());
        picContent.setImageURI(uri);
        return convertView;
    }

}