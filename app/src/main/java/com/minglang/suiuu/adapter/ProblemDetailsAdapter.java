package com.minglang.suiuu.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.PersonalMainPagerActivity;
import com.minglang.suiuu.entity.ProblemDetails;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemClickListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

/**
 * Created by Administrator on 2015/9/18.
 * <p/>
 * 问答详情页数据适配器
 */
public class ProblemDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String USER_SIGN = "userSign";

    private Context context;

    private List<ProblemDetails.CommunityItemData.AnswerEntity> list;

    private final SparseBooleanArray sparseBooleanArray;

    private RecyclerViewOnItemClickListener onItemClickListener;

    public ProblemDetailsAdapter(Context context) {
        this.context = context;
        sparseBooleanArray = new SparseBooleanArray();
    }

    public void setList(List<ProblemDetails.CommunityItemData.AnswerEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_community_layout, parent, false);
        return new ProblemDetailsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProblemDetailsViewHolder viewHolder = (ProblemDetailsViewHolder) holder;

        ProblemDetails.CommunityItemData.AnswerEntity answerEntity = list.get(position);

        String headImagePath = answerEntity.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            viewHolder.headImageView.setImageURI(Uri.parse(headImagePath));
        } else {
            viewHolder.headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
        }

        String strName = answerEntity.getNickname();
        if (!TextUtils.isEmpty(strName)) {
            viewHolder.nameView.setText(strName);
        } else {
            viewHolder.nameView.setText("");
        }

        String strDateAndTime = answerEntity.getACreateTime();
        if (!TextUtils.isEmpty(strDateAndTime)) {
            viewHolder.dateAndTimeView.setText(strDateAndTime);
        } else {
            viewHolder.dateAndTimeView.setText("");
        }

        String strContent = answerEntity.getAContent();
        if (!TextUtils.isEmpty(strContent)) {
            viewHolder.detailsView.setText(Html.fromHtml(strContent), sparseBooleanArray, position);
        } else {
            viewHolder.detailsView.setText("");
        }

        viewHolder.headImageView.setOnClickListener(new HeadViewClickListener(position));

        final View itemView = viewHolder.itemView;
        final int location = position;
        if (onItemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(itemView,location);
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

    private class ProblemDetailsViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView headImageView;
        TextView nameView;
        TextView dateAndTimeView;
        ExpandableTextView detailsView;

        public ProblemDetailsViewHolder(View itemView) {
            super(itemView);
            headImageView = (SimpleDraweeView) itemView.findViewById(R.id.item_community_layout_4_head_image_view);
            nameView = (TextView) itemView.findViewById(R.id.item_community_layout_4_name);
            dateAndTimeView = (TextView) itemView.findViewById(R.id.item_community_layout_4_date_time);
            detailsView = (ExpandableTextView) itemView.findViewById(R.id.item_community_layout_4_expandable_content);
        }
    }

    private class HeadViewClickListener implements View.OnClickListener {

        private int position;

        private HeadViewClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            String userSign = list.get(position).getAUserSign();
            Intent intent = new Intent(context, PersonalMainPagerActivity.class);
            intent.putExtra(USER_SIGN, userSign);
            context.startActivity(intent);
        }

    }

}