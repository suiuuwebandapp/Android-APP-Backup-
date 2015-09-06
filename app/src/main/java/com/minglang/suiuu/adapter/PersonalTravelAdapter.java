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
import com.minglang.suiuu.entity.PersonalCenter.PersonalCenterData.TripListEntity;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2015/8/3.
 * <p/>
 * 用户个人主页旅图数据适配器
 */
public class PersonalTravelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TripListEntity> list;

    private RecyclerViewOnItemClickListener onItemClickListener;

    public PersonalTravelAdapter() {

    }

    public void setList(List<TripListEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
            Uri uri = Uri.parse(mainImagePath);
            travelViewHolder.mainImageView.setImageURI(uri);
        }

        String headImagePath = list.get(position).getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            Uri uri = Uri.parse(headImagePath);
            travelViewHolder.headImageView.setImageURI(uri);
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