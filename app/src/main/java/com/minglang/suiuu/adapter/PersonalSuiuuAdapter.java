package com.minglang.suiuu.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.UserSuiuu.UserSuiuuData;
import com.minglang.suiuu.interfaces.RecyclerOnItemClickListener;
import com.minglang.suiuu.interfaces.RecyclerOnItemLongClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 用户个人主页随游列表数据适配器
 * <p/>
 * Created by Administrator on 2015/5/11.
 */
public class PersonalSuiuuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<UserSuiuuData> list;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    private RecyclerOnItemClickListener onItemClickListener;
    private RecyclerOnItemLongClickListener onItemLongClickListener;

    public PersonalSuiuuAdapter(Context context) {
        this.context = context;

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading)
                .showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setList(List<UserSuiuuData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(RecyclerOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(RecyclerOnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
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
            imageLoader.displayImage(imagePath, suiuuViewHolder.mainImage, options);
        }

        String strTitle = list.get(position).getTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            suiuuViewHolder.suiuuTitle.setText(strTitle);
        } else {
            suiuuViewHolder.suiuuTitle.setText("");
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

    private class PersonalSuiuuViewHolder extends RecyclerView.ViewHolder {

        public ImageView mainImage;
        public TextView suiuuTitle;
        public TextView praiseCount;
        public TextView commentCount;

        public PersonalSuiuuViewHolder(View itemView) {
            super(itemView);
            mainImage = (ImageView) itemView.findViewById(R.id.item_personal_suiuu_image);
            suiuuTitle = (TextView) itemView.findViewById(R.id.item_personal_suiuu_title);
            praiseCount = (TextView) itemView.findViewById(R.id.item_personal_suiuu_praise);
            commentCount = (TextView) itemView.findViewById(R.id.item_personal_suiuu_comment);
        }
    }

}