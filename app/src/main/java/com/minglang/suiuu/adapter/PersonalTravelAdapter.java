package com.minglang.suiuu.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.UserTravel.UserTravelData.UserTravelItemData;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by Administrator on 2015/8/3.
 * <p/>
 * 用户个人主页旅图数据适配器
 */
public class PersonalTravelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserTravelItemData> list;

    private ImageLoader imageLoader;
    private DisplayImageOptions options1;
    private DisplayImageOptions options2;

    private RecyclerViewOnItemClickListener onItemClickListener;

    public PersonalTravelAdapter() {
        imageLoader = ImageLoader.getInstance();

        options1 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading)
                .showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

        options2 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image)
                .showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }


    public void setList(List<UserTravelItemData> list) {
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
            imageLoader.displayImage(mainImagePath, travelViewHolder.mainImageView, options1);
        }

        String headImagePath = list.get(position).getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(headImagePath, travelViewHolder.headImageView, options2);
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

        private ImageView mainImageView;
        private CircleImageView headImageView;
        private TextView titleView;

        public PersonalTravelViewHolder(View itemView) {
            super(itemView);
            mainImageView = (ImageView) itemView.findViewById(R.id.item_personal_travel_main_image);
            headImageView = (CircleImageView) itemView.findViewById(R.id.item_personal_travel_head_image);
            titleView = (TextView) itemView.findViewById(R.id.item_personal_travel_title);
        }
    }

}