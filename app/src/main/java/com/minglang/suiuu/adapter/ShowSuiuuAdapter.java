package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.SuiuuDataList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
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
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public ShowSuiuuAdapter(Context context, List<SuiuuDataList> list) {
        this.context = context;
        this.list = list;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.default_suiuu_image).showImageOnFail(R.drawable.loading)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();
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
        final ViewHolder holder;
        View view;
        if (convertView != null && convertView instanceof RelativeLayout) {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        } else {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.adapter_suiuu, null);
            holder.picContent = (ImageView) view.findViewById(R.id.item_attention_dynamic_image);
            holder.title = (TextView) view.findViewById(R.id.item_attention_dynamic_title);
            holder.description = (TextView) view.findViewById(R.id.item_attention_dynamic_content);
            view.setTag(holder);
        }
        holder.title.setText(list.get(position).getTitle());
        holder.description.setText(list.get(position).getIntro());
        imageLoader.displayImage(list.get(position).getTitleImg(), holder.picContent, options);
        return view;
    }

    static class ViewHolder {
        ImageView picContent;
        TextView title;
        TextView description;
    }
}