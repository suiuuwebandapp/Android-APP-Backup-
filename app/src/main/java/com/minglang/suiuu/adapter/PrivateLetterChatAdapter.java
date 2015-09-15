package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.PrivateChat;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.List;

/**
 * Created by Administrator on 2015/9/14.
 * <p/>
 * 私信聊天数据适配器
 */
public class PrivateLetterChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SEND_MESSAGE = 1;
    private static final int ACCEPT_MESSAGE = 2;

    private Context context;

    private List<PrivateChat> list;

    private String failureImagePath;

    public PrivateLetterChatAdapter(Context context, List<PrivateChat> list) {
        this.context = context;
        this.list = list;
        failureImagePath = "res://com.minglang.suiuu/" + R.drawable.default_head_image_error;
    }

    @Override
    public int getItemViewType(int position) {
        PrivateChat chat = list.get(position);
        if (!TextUtils.isEmpty(chat.getTo_client_id())) {
            return SEND_MESSAGE;
        } else {
            return ACCEPT_MESSAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SEND_MESSAGE:
                View sendView = LayoutInflater.from(context).inflate(R.layout.item_letter_send, parent, false);
                return new MyLetterChatViewHolder(sendView);

            case ACCEPT_MESSAGE:
                View acceptView = LayoutInflater.from(context).inflate(R.layout.item_letter_accept, parent, false);
                return new OtherLetterChatViewHolder(acceptView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case SEND_MESSAGE:
                MyLetterChatViewHolder myLetterChatViewHolder = (MyLetterChatViewHolder) holder;
                String headImagePath = SuiuuInfo.ReadUserData(context).getHeadImg();
                if (!TextUtils.isEmpty(headImagePath)) {
                    myLetterChatViewHolder.headImageView.setImageURI(Uri.parse(headImagePath));
                } else {
                    myLetterChatViewHolder.headImageView.setImageURI(Uri.parse(failureImagePath));
                }

                String content = list.get(position).getContent();
                if (!TextUtils.isEmpty(content)) {
                    myLetterChatViewHolder.contentView.setText(content);
                } else {
                    myLetterChatViewHolder.contentView.setText("");
                }
                break;

            case ACCEPT_MESSAGE:
                OtherLetterChatViewHolder otherLetterChatViewHolder = (OtherLetterChatViewHolder) holder;
                String otherHeadImagePath = list.get(position).getSender_HeadImg();
                if (!TextUtils.isEmpty(otherHeadImagePath)) {
                    otherLetterChatViewHolder.headImageView.setImageURI(Uri.parse(otherHeadImagePath));
                } else {
                    otherLetterChatViewHolder.headImageView.setImageURI(Uri.parse(failureImagePath));
                }

                String otherContent = list.get(position).getContent();
                if (!TextUtils.isEmpty(otherContent)) {
                    otherLetterChatViewHolder.contentView.setText(otherContent);
                } else {
                    otherLetterChatViewHolder.contentView.setText("");
                }
                break;
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

    private class MyLetterChatViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView headImageView;
        private TextView contentView;

        public MyLetterChatViewHolder(View itemView) {
            super(itemView);
            headImageView = (SimpleDraweeView) itemView.findViewById(R.id.item_letter_send_head_view);
            contentView = (TextView) itemView.findViewById(R.id.item_letter_send_content_view);
        }

    }

    private class OtherLetterChatViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView headImageView;
        private TextView contentView;

        public OtherLetterChatViewHolder(View itemView) {
            super(itemView);
            headImageView = (SimpleDraweeView) itemView.findViewById(R.id.item_letter_accept_head_view);
            contentView = (TextView) itemView.findViewById(R.id.item_letter_accept_content_view);
        }

    }

}