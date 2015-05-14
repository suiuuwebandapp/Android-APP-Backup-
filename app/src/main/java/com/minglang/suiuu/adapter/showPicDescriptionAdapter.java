package com.minglang.suiuu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.chat.activity.ShowBigImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

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
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public showPicDescriptionAdapter(Context context, List<String> imageList, List<String> conentList) {
        this.context = context;
        this.imageList = imageList;
        this.contentList = conentList;
        imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_suiuu_image)
                .showImageForEmptyUri(R.drawable.default_suiuu_image).showImageOnFail(R.drawable.default_suiuu_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public int getCount() {
        return imageList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view;
        if (convertView != null && convertView instanceof LinearLayout) {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        } else {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item_show_article_detail, null);
            holder.picContent = (ImageView) view.findViewById(R.id.iv_show_pic);
            holder.textContent = (TextView) view.findViewById(R.id.tv_show_text_description);
            view.setTag(holder);
        }

        imageLoader.displayImage(imageList.get(position), holder.picContent, options);


            if (TextUtils.isEmpty(contentList.get(position))) {
                holder.textContent.setVisibility(View.GONE);
            } else {
                holder.picContent.setVisibility(View.VISIBLE);
                holder.textContent.setText(contentList.get(position));
            }

            holder.picContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showPic = new Intent(context, ShowBigImage.class);
                    showPic.putExtra("remotepath", imageList.get(position));
                    showPic.putExtra("isHuanXin", false);
                    context.startActivity(showPic);
                }
            });
            return view;
        }


    static class ViewHolder {
        ImageView picContent;
        TextView textContent;
    }

}
