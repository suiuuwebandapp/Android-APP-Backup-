package com.minglang.suiuu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.utils.ViewHolder;

/**
 * 社区条目数据适配器
 * <p/>
 * Created by Administrator on 2015/7/3.
 */
public class CommunityItemAdapter extends BaseAdapter {

    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;

    private Context context;

    public CommunityItemAdapter(Context context) {
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
    public int getItemViewType(int position) {
        if (position == 0) {
            return ONE;
        } else if (position == 1) {
            return TWO;
        } else if (position == 2) {
            return THREE;
        } else {
            return FOUR;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int viewType = getItemViewType(position);

        switch (viewType) {

            case ONE:
                ViewHolder holder1 = ViewHolder.get(context, convertView, parent,
                        R.layout.item_community_layout_1, position);
                convertView = holder1.getConvertView();

                CircleImageView headImageView1 = holder1.getView(R.id.
                        item_community_layout_1_head_view);
                TextView problemText = holder1.getView(R.id.
                        item_community_layout_1_problem);
                FlowLayout flowLayout = holder1.getView(R.id.
                        item_community_layout_1_flow_layout);

                break;

            case TWO:
                ViewHolder holder2 = ViewHolder.get(context, convertView, parent,
                        R.layout.item_community_layout_2, position);
                convertView = holder2.getConvertView();

                TextView problemContent = holder2.getView(R.id.
                        item_community_layout_2_problem);
                break;

            case THREE:
                ViewHolder holder3 = ViewHolder.get(context, convertView, parent,
                        R.layout.item_community_layout_3, position);
                convertView = holder3.getConvertView();

                TextView attentionView = holder3.getView(R.id.
                        item_community_layout_3_attention);
                TextView answerView = holder3.getView(R.id.
                        item_community_layout_3_answer);

                break;

            case FOUR:
                ViewHolder holder4 = ViewHolder.get(context, convertView, parent,
                        R.layout.item_community_layout_4, position);
                convertView = holder4.getConvertView();

                CircleImageView headImageView2 = holder4.getView(R.id.
                        item_community_layout_4_head_image_view);
                TextView nameView = holder4.getView(R.id.
                        item_community_layout_4_name);
                TextView dateAndTimeView = holder4.getView(R.id.
                        item_community_layout_4_date_time);
                TextView detailsView = holder4.getView(R.id.
                        item_community_layout_4_details);
                break;

        }

        return convertView;
    }

}