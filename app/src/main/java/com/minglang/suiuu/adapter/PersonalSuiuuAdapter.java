package com.minglang.suiuu.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.TravelList;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * 用户个人主页随游列表数据适配器
 * <p/>
 * Created by Administrator on 2015/5/11.
 */
public class PersonalSuiuuAdapter extends BaseAdapter {

    private static final String TAG = PersonalSuiuuAdapter.class.getSimpleName();

    private Context context;

    private List<TravelList> list;

    private ImageLoader imageLoader;

    private int screenWidth;

    public PersonalSuiuuAdapter(Context context) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }

        screenWidth = new ScreenUtils((Activity) context).getScreenWidth();
    }

    public void setList(List<TravelList> list) {
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

        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_personal_suiuu, position);
        ImageView mainImageView = holder.getView(R.id.item_personal_suiuu_image);
        TextView content = holder.getView(R.id.item_personal_suiuu_content);
        TextView comment = holder.getView(R.id.item_personal_suiuu_comments);
        TextView praise = holder.getView(R.id.item_personal_suiuu_praise);

        String imagePath = list.get(position).getTitleImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageLoader.displayImage(imagePath, mainImageView);
        }

        String strContent = list.get(position).getTitle();
        if (!TextUtils.isEmpty(strContent)) {
            content.setText(strContent);
        } else {
            content.setText("");
        }

        convertView = holder.getConvertView();
        int itemWidth = screenWidth / 2;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemWidth, itemWidth);
        convertView.setLayoutParams(params);

        return convertView;
    }
}
