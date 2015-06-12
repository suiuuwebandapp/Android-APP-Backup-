package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.SuiuuDataList;
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
    private List<SuiuuDataList> list;
//    private ImageLoader imageLoader;
//    private DisplayImageOptions options;

    public ShowSuiuuAdapter(Context context, List<SuiuuDataList> list) {
        this.context = context;
        this.list = list;
//        imageLoader = ImageLoader.getInstance();
//        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
//                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
//                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
//                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
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
        TextView title = holder.getView(R.id.item_attention_dynamic_title);
        TextView description = holder.getView(R.id.item_attention_dynamic_content);

        title.setText(list.get(position).getTitle());
        description.setText(list.get(position).getIntro());
//        imageLoader.displayImage(list.get(position).getTitleImg(), picContent, options);
        Uri uri = Uri.parse(list.get(position).getTitleImg());
        picContent.setImageURI(uri);
        return convertView;
    }


}