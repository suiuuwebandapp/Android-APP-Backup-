package com.minglang.suiuu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.utils.ViewHolder;

/**
 * 首页-问答社区-数据适配器
 * <p/>
 * Created by Administrator on 2015/7/3.
 */
public class CommunityAdapter extends BaseAdapter {

    private static final String TAG = CommunityAdapter.class.getSimpleName();

    private Context context;

    public CommunityAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_community, position);

        CircleImageView headImageView = holder.getView(R.id.item_community_header_image);
        TextView countView = holder.getView(R.id.item_community_count);
        TextView titleView = holder.getView(R.id.item_community_title);
        TextView cotentView = holder.getView(R.id.item_community_content);

        return convertView;
    }

}