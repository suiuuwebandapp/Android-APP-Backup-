package com.minglang.suiuu.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.UserProblem.UserProblemData.UserProblemItemData;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by Administrator on 2015/8/3.
 * <p/>
 * 用户个人主页问答数据适配器
 */
public class PersonalProblemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserProblemItemData> list;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    private RecyclerViewOnItemClickListener onItemClickListener;

    public PersonalProblemAdapter() {
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image)
                .showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setList(List<UserProblemItemData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personal_problem, null);
        return new PersonalProblemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PersonalProblemViewHolder problemViewHolder = (PersonalProblemViewHolder) holder;

        String headImagePath = list.get(position).getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(headImagePath, problemViewHolder.headImageView, options);
        }

        String strCount = list.get(position).getAttentionNumber();
        if (!TextUtils.isEmpty(strCount)) {
            problemViewHolder.problemCountView.setText(strCount);
        } else {
            problemViewHolder.problemCountView.setText("");
        }

        String strTitle = list.get(position).getQTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            problemViewHolder.problemTitleView.setText(strTitle);
        } else {
            problemViewHolder.problemTitleView.setText("");
        }

        String strContent = list.get(position).getQContent();
        if (!TextUtils.isEmpty(strContent)) {
            problemViewHolder.problemContentView.setText(strContent);
        } else {
            problemViewHolder.problemContentView.setText("");
        }

        final View itemView = problemViewHolder.itemView;
        final int location = problemViewHolder.getLayoutPosition();

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

    private class PersonalProblemViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView headImageView;
        private TextView problemCountView;
        private TextView problemTitleView;
        private TextView problemContentView;

        public PersonalProblemViewHolder(View itemView) {
            super(itemView);
            headImageView = (CircleImageView) itemView.findViewById(R.id.item_personal_problem_header_image);
            problemCountView = (TextView) itemView.findViewById(R.id.item_personal_problem_count);
            problemTitleView = (TextView) itemView.findViewById(R.id.item_personal_problem_title);
            problemContentView = (TextView) itemView.findViewById(R.id.item_personal_problem_content);
        }

    }

}
