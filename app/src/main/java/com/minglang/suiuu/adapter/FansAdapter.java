package com.minglang.suiuu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.FansData;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;


/**
 * 粉丝数据适配器
 * <p/>
 * Created by Administrator on 2015/5/1.
 */
public class FansAdapter extends BaseAdapter {

    private static final String TAG = FansAdapter.class.getSimpleName();

    private Context context;

    private List<FansData> list;

    private ImageLoader imageLoader;

    public FansAdapter(Context context, List<FansData> list) {
        this.context = context;
        this.list = list;
        imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }
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

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_fans, position);

        CircleImageView headImageView = holder.getView(R.id.item_fans_head);
        TextView name = holder.getView(R.id.item_fans_name);
        TextView intro = holder.getView(R.id.item_fans_intro);

        FansData data = list.get(position);

        String headImagePath = data.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(headImagePath, headImageView);
        }
        Log.i(TAG,"头像图片地址:"+headImagePath);

        String strName = data.getNickname();
        if (!TextUtils.isEmpty(strName)) {
            name.setText(strName);
        } else {
            name.setText("匿名用户");
        }

        String strIntro = data.getIntro();
        if (!TextUtils.isEmpty(strIntro)) {
            intro.setText(strIntro);
        } else {
            intro.setText("暂无简介");
        }

        convertView = holder.getConvertView();
        return convertView;
    }
}
