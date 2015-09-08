package com.minglang.suiuu.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.ShowBigPictureActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：Suiuu
 * 类描述：文章详细页面随拍的adapter
 * 创建人：Administrator
 * 创建时间：2015/5/4 9:55
 * 修改人：Administrator
 * 修改时间：2015/5/4 9:55
 * 修改备注：
 */
public class showPicDescriptionAdapter extends BaseAdapter {

    private Context context;
    private List<String> imageList;
    private List<String> contentList;

    public showPicDescriptionAdapter(Context context, List<String> imageList, List<String> conentList) {
        this.context = context;
        this.imageList = imageList;
        this.contentList = conentList;
    }

    @Override
    public int getCount() {
        if (imageList != null && imageList.size() > 0) {
            return imageList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (imageList != null && imageList.size() > 0) {
            return imageList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view;
        if (convertView != null && convertView instanceof LinearLayout) {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        } else {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item_show_article_detail, null);
            holder.picContent = (SimpleDraweeView) view.findViewById(R.id.iv_show_pic);
            holder.textContent = (TextView) view.findViewById(R.id.tv_show_text_description);
            view.setTag(holder);
        }

        Uri uri = Uri.parse(imageList.get(position));
        holder.picContent.setImageURI(uri);
        if (contentList.size() >= 1) {
            if (TextUtils.isEmpty(contentList.get(position))) {
                holder.textContent.setVisibility(View.GONE);
            } else {
                holder.textContent.setVisibility(View.VISIBLE);
                holder.textContent.setText(contentList.get(position));
            }
        }

        holder.picContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ShowBigPictureActivity.class);
                intent.putStringArrayListExtra("picList", (ArrayList<String>) imageList);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });

        return view;
    }

    static class ViewHolder {
        SimpleDraweeView picContent;
        TextView textContent;
    }

}