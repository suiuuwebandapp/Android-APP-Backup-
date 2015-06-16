package com.minglang.suiuu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.JoinEntity;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 已参加的数据适配器
 * <p/>
 * Created by Administrator on 2015/6/12.
 */
public class JoinAdapter extends BaseAdapter {

    private Context context;

    private List<JoinEntity> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    public JoinAdapter(Context context) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setList(List<JoinEntity> list) {
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

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_my_suiuu_join, position);
        convertView = holder.getConvertView();

        CircleImageView headImageView = holder.getView(R.id.item_my_suiuu_join_head_image);
        TextView nameView = holder.getView(R.id.item_my_suiuu_join_name);
        TextView locationView = holder.getView(R.id.item_my_suiuu_join_location);
        Button removeBtn = holder.getView(R.id.item_my_suiuu_join_remove);

        JoinEntity entity = list.get(position);

        imageLoader.displayImage(entity.getImagePath(), headImageView, options);
        nameView.setText(entity.getUserName());
        locationView.setText(entity.getUserLocation());

        if (entity.isRemoveFlag()) {
            removeBtn.setTag(position);
            removeBtn.setOnClickListener(new RemoveClickListener(position));
        } else {
            removeBtn.setEnabled(false);
        }

        return convertView;
    }

    private class RemoveClickListener implements View.OnClickListener {

        private int index;

        private RemoveClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "position:" + index, Toast.LENGTH_SHORT).show();
        }

    }

}
