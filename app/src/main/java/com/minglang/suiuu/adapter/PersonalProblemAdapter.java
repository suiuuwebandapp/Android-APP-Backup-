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
import com.minglang.suiuu.entity.UserProblem.UserProblemData.UserProblemItemData;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2015/8/3.
 * <p/>
 * 用户个人主页问答数据适配器
 */
public class PersonalProblemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserProblemItemData> list;

    private RecyclerViewOnItemClickListener onItemClickListener;

    private String failureImagePath;

    public PersonalProblemAdapter() {
        failureImagePath = "res://com.minglang.suiuu/" + R.drawable.default_head_image_error;
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
            problemViewHolder.headImageView.setImageURI(Uri.parse(headImagePath));
        } else {
            problemViewHolder.headImageView.setImageURI(Uri.parse(failureImagePath));
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

        private SimpleDraweeView headImageView;
        private TextView problemCountView;
        private TextView problemTitleView;
        private TextView problemContentView;

        public PersonalProblemViewHolder(View itemView) {
            super(itemView);
            headImageView = (SimpleDraweeView) itemView.findViewById(R.id.item_personal_problem_header_image);
            problemCountView = (TextView) itemView.findViewById(R.id.item_personal_problem_count);
            problemTitleView = (TextView) itemView.findViewById(R.id.item_personal_problem_title);
            problemContentView = (TextView) itemView.findViewById(R.id.item_personal_problem_content);
        }

    }

}
