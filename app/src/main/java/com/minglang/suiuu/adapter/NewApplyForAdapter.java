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
import com.minglang.suiuu.entity.NewApplyForEntity;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 新申请数据适配器
 * <p/>
 * Created by Administrator on 2015/6/12.
 */
public class NewApplyForAdapter extends BaseAdapter {

    private Context context;

    private List<NewApplyForEntity> list;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    private int screenWidth, screenHeight;

    public NewApplyForAdapter(Context context) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setList(List<NewApplyForEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setScreenParams(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
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

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_my_suiuu_new_apply_for, position);
        convertView = holder.getConvertView();
        CircleImageView headImageView = holder.getView(R.id.item_my_suiuu_new_apply_for_head_image);
        TextView userNameView = holder.getView(R.id.item_my_suiuu_new_apply_for_user_name);
        Button ignoreBtn = holder.getView(R.id.item_my_suiuu_new_apply_for_ignore);
        Button agreeBtn = holder.getView(R.id.item_my_suiuu_new_apply_for_agree);

        NewApplyForEntity newApplyForEntity = list.get(position);

        imageLoader.displayImage(newApplyForEntity.getImagePath(), headImageView, options);
        userNameView.setText(newApplyForEntity.getUserName());

        int btnWidth = screenWidth / 16;
        int btnHeight = screenHeight / 16;

//        ignoreBtn.setWidth(btnWidth);
//        ignoreBtn.setHeight(btnHeight);

        ignoreBtn.setTag(position);
        ignoreBtn.setOnClickListener(new IgnoreClickListener(position));

//        agreeBtn.setWidth(btnWidth);
//        agreeBtn.setHeight(btnHeight);

        agreeBtn.setTag(position);
        agreeBtn.setOnClickListener(new AgreeClickListener(position));

        return convertView;
    }

    private class IgnoreClickListener implements View.OnClickListener {

        private int index;

        private IgnoreClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "ignore item" + index, Toast.LENGTH_SHORT).show();
        }

    }

    private class AgreeClickListener implements View.OnClickListener {

        private int index;

        private AgreeClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "agree item" + index, Toast.LENGTH_SHORT).show();
        }

    }

}
