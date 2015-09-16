package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.MainCommunity;
import com.minglang.suiuu.entity.MainCommunity.MainCommunityData.MainCommunityItemData;
import com.minglang.suiuu.utils.ViewHolder;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

/**
 * 首页-问答社区-数据适配器
 * <p/>
 * Created by Administrator on 2015/7/3.
 */
public class CommunityAdapter extends BaseAdapter {

    private Context context;

    private List<MainCommunityItemData> list;

    private final SparseBooleanArray sparseBooleanArray;

    private OnCommunityClickListener onCommunityClickListener;

    public CommunityAdapter(Context context) {
        this.context = context;
        sparseBooleanArray = new SparseBooleanArray();
    }

    public void setList(List<MainCommunity.MainCommunityData.MainCommunityItemData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnCommunityClickListener(OnCommunityClickListener onCommunityClickListener) {
        this.onCommunityClickListener = onCommunityClickListener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(context, convertView, parent, R.layout.item_community, position);
        convertView = holder.getConvertView();

        SimpleDraweeView headImageView = holder.getView(R.id.item_community_header_image);
        TextView countView = holder.getView(R.id.item_community_count);
        TextView titleView = holder.getView(R.id.item_community_title);
        ExpandableTextView cotentView = holder.getView(R.id.item_community_expandable_content);

        MainCommunityItemData itemData = list.get(position);

        String headImagePath = itemData.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            headImageView.setImageURI(Uri.parse(headImagePath));
        } else {
            headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
        }

        String strCount = itemData.getANumber();
        if (!TextUtils.isEmpty(strCount)) {
            countView.setText(Html.fromHtml(strCount));
        } else {
            countView.setText("0");
        }

        String strTitle = itemData.getQTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            titleView.setText(strTitle);
        } else {
            titleView.setText("");
        }

        String strCotent = itemData.getQContent();
        if (!TextUtils.isEmpty(strCotent)) {
            cotentView.setText(Html.fromHtml(strCotent), sparseBooleanArray, position);
        } else {
            cotentView.setText("");
        }

        if (onCommunityClickListener != null) {
            convertView.setOnClickListener(new ItemViewClickListener(convertView, position));
        }

        return convertView;
    }

    public interface OnCommunityClickListener {
        void onClickListener(View itemView, int position);
    }

    private class ItemViewClickListener implements View.OnClickListener {

        private View itemView;

        private int position;

        private ItemViewClickListener(View itemView, int position) {
            this.itemView = itemView;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            onCommunityClickListener.onClickListener(itemView, position);
        }
    }

}