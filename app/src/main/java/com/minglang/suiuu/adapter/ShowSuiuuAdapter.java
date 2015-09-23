package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.SuiuuItemData;
import com.minglang.suiuu.utils.ViewHolder;

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
    private List<SuiuuItemData> list;

    public ShowSuiuuAdapter(Context context, List<SuiuuItemData> list) {
        this.context = context;
        this.list = list;
    }

    public void upDateData(List<SuiuuItemData> list) {
        this.list = list;
        this.notifyDataSetChanged();
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_suiuu, position);
        convertView = holder.getConvertView();

        SimpleDraweeView mainImageView = holder.getView(R.id.item_attention_dynamic_image);
        SimpleDraweeView headImageView = holder.getView(R.id.item_suiuu_head_image_view);
        TextView suiuuNameView = holder.getView(R.id.item_suiuu_name);
        TextView itemSuiuuPrice = holder.getView(R.id.item_suiuu_price);
        RatingBar rb_suiuu_star = holder.getView(R.id.item_suiuu_star_bar);

        rb_suiuu_star.setRating(Math.round(Double.valueOf(list.get(position).getScore().replace(".", "")) / 2));

        String basePrice = list.get(position).getBasePrice();
        if (!TextUtils.isEmpty(basePrice)) {
            itemSuiuuPrice.setText(String.format("%s%s", "￥:  ", basePrice));
        } else {
            itemSuiuuPrice.setText("0");
        }

        String title = list.get(position).getTitle().trim();
        if (!TextUtils.isEmpty(title)) {
            suiuuNameView.setText(title);
        } else {
            suiuuNameView.setText("");
        }

        String titleImagePath = list.get(position).getTitleImg();
        if (!TextUtils.isEmpty(titleImagePath)) {
            mainImageView.setImageURI(Uri.parse(titleImagePath));
        } else {
            mainImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.loading_error));
        }

        String headImagePath = list.get(position).getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            headImageView.setImageURI(Uri.parse(headImagePath));
        } else {
            headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
        }

        return convertView;
    }

}