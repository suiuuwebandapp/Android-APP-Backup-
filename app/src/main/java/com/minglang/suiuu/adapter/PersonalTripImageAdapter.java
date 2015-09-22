package com.minglang.suiuu.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.UserTravel.UserTravelData.UserTravelItemData;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemClickListener;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemLongClickListener;

import java.util.List;

/**
 * Created by Administrator on 2015/8/3.
 * <p/>
 * 用户个人主页旅图数据适配器
 */
public class PersonalTripImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserTravelItemData> list;

    private RecyclerViewOnItemClickListener onItemClickListener;

    private RecyclerViewOnItemLongClickListener onItemLongClickListener;

    private String failureImagePath, failureImagePath2;

    public PersonalTripImageAdapter() {
        failureImagePath = "res://com.minglang.suiuu/" + R.drawable.loading_error;
        failureImagePath2 = "res://com.minglang.suiuu/" + R.drawable.default_head_image_error;
    }

    public void setList(List<UserTravelItemData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(RecyclerViewOnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 添加Item到指定位置
     *
     * @param itemData 指定位置Item数据
     * @param position 位置
     */
    public void addItem(UserTravelItemData itemData, int position) {
        list.add(position, itemData);
        notifyItemInserted(position);
    }

    /**
     * 移除指定位置Item
     *
     * @param position 位置
     */
    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personal_travel, null);
        return new PersonalTravelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PersonalTravelViewHolder travelViewHolder = (PersonalTravelViewHolder) holder;

        String mainImagePath = list.get(position).getTitleImg();
        if (!TextUtils.isEmpty(mainImagePath)) {
            travelViewHolder.mainImageView.setImageURI(Uri.parse(mainImagePath));
        } else {
            travelViewHolder.mainImageView.setImageURI(Uri.parse(failureImagePath));
        }

        String headImagePath = list.get(position).getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            travelViewHolder.headImageView.setImageURI(Uri.parse(headImagePath));
        } else {
            travelViewHolder.headImageView.setImageURI(Uri.parse(failureImagePath2));
        }

        String title = list.get(position).getTitle();
        if (!TextUtils.isEmpty(title)) {
            travelViewHolder.titleView.setText(title);
        } else {
            travelViewHolder.titleView.setText("");
        }

        final View itemView = travelViewHolder.itemView;
        final int location = travelViewHolder.getLayoutPosition();

        if (onItemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(itemView, location);
                }
            });
        }

        if (onItemLongClickListener != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickListener.onItemLongClick(itemView, location);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    private class PersonalTravelViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView mainImageView;
        private SimpleDraweeView headImageView;
        private TextView titleView;

        public PersonalTravelViewHolder(View itemView) {
            super(itemView);
            mainImageView = (SimpleDraweeView) itemView.findViewById(R.id.item_personal_travel_main_image);
            headImageView = (SimpleDraweeView) itemView.findViewById(R.id.item_personal_travel_head_image);
            titleView = (TextView) itemView.findViewById(R.id.item_personal_travel_title);
        }

    }

}