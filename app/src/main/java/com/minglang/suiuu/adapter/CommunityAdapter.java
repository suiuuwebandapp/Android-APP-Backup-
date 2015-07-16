package com.minglang.suiuu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.MainCommunity;
import com.minglang.suiuu.utils.ViewHolder;
import com.minglang.suiuu.entity.MainCommunity.MainCommunityData.*;

import java.util.List;

/**
 * 首页-问答社区-数据适配器
 * <p/>
 * Created by Administrator on 2015/7/3.
 */
public class CommunityAdapter extends BaseAdapter {

    private static final String TAG = CommunityAdapter.class.getSimpleName();

    private Context context;

    private List<MainCommunityItemData> list;

    public CommunityAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<MainCommunity.MainCommunityData.MainCommunityItemData> list) {
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
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_community, position);

        CircleImageView headImageView = holder.getView(R.id.item_community_header_image);
        TextView countView = holder.getView(R.id.item_community_count);
        TextView titleView = holder.getView(R.id.item_community_title);
        TextView cotentView = holder.getView(R.id.item_community_content);

        return convertView;
    }

}