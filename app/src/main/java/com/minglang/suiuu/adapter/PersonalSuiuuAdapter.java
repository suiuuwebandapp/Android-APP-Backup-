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
 * Created by Administrator on 2015/5/11.
 * <p/>
 * 用户个人主页列表数据适配器
 */
public class PersonalSuiuuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TripListEntity> list;

    private RecyclerViewOnItemClickListener onItemClickListener;

    public PersonalSuiuuAdapter() {

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personal_suiuu, null);
        return new PersonalSuiuuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PersonalSuiuuViewHolder suiuuViewHolder = (PersonalSuiuuViewHolder) holder;

        String imagePath = list.get(position).getHeadImg();
        if (!TextUtils.isEmpty(imagePath)) {
            Uri uri = Uri.parse(imagePath);
            suiuuViewHolder.mainImage.setImageURI(uri);
        }

        String strTitle = list.get(position).getTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            suiuuViewHolder.suiuuTitle.setText(strTitle);
        } else {
            suiuuViewHolder.suiuuTitle.setText("");
        }

        String collectCount = list.get(position).getCollectCount();
        if (!TextUtils.isEmpty(collectCount)) {
            suiuuViewHolder.praiseCount.setText(collectCount);
        } else {
            suiuuViewHolder.praiseCount.setText("");
        }

        String commentCount = list.get(position).getCommentCount();
        if (!TextUtils.isEmpty(commentCount)) {
            suiuuViewHolder.commentCount.setText(commentCount);
        } else {
            suiuuViewHolder.commentCount.setText("0");
        }

        final View itemView = suiuuViewHolder.itemView;
        final int location = suiuuViewHolder.getLayoutPosition();
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

    private class PersonalSuiuuViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView mainImage;
        public TextView suiuuTitle;
        public TextView praiseCount;
        public TextView commentCount;

        public PersonalSuiuuViewHolder(View itemView) {
            super(itemView);
            mainImage = (SimpleDraweeView) itemView.findViewById(R.id.item_personal_suiuu_image);
            suiuuTitle = (TextView) itemView.findViewById(R.id.item_personal_suiuu_title);
            praiseCount = (TextView) itemView.findViewById(R.id.item_personal_suiuu_praise);
            commentCount = (TextView) itemView.findViewById(R.id.item_personal_suiuu_comment);
        }

    }

}