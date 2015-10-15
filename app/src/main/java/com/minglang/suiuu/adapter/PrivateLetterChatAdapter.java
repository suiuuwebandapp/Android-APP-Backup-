package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.PrivateChat;
import com.minglang.suiuu.entity.PrivateChat.PrivateChatData;
import com.minglang.suiuu.utils.DateUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.List;

/**
 * Created by Administrator on 2015/9/14.
 * <p>
 * 私信聊天数据适配器
 */
public class PrivateLetterChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = PrivateLetterChatAdapter.class.getSimpleName();

    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final int SEND_MESSAGE = 1;
    private static final int ACCEPT_MESSAGE = 2;

    private Context context;

    private List<PrivateChat.PrivateChatData> list;

    private String otherHeadImagePath;

    private String failureImagePath;

    private String userSign;

    public PrivateLetterChatAdapter(Context context, List<PrivateChatData> list) {
        this.context = context;
        this.list = list;
        failureImagePath = "res://com.minglang.suiuu/" + R.drawable.default_head_image_error;
        userSign = SuiuuInfo.ReadUserSign(context);
    }

    public void setList(List<PrivateChat.PrivateChatData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOtherHeadImagePath(String otherHeadImagePath) {
        this.otherHeadImagePath = otherHeadImagePath;
    }

    @Override
    public int getItemViewType(int position) {
        String senderId = list.get(position).getSenderId();
        if (TextUtils.isEmpty(senderId)) {
            return SEND_MESSAGE;
        } else {
            if (senderId.equals(userSign)) {
                return SEND_MESSAGE;
            } else {
                return ACCEPT_MESSAGE;
            }
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
                MyLetterChatViewHolder myHolder = (MyLetterChatViewHolder) holder;
                String time1 = list.get(position).getSendTime();
                try {
                    if (position == 0) {
                        myHolder.timestampView.setText(DateUtils.getTimestamp2String(DateUtils.String2Date(TIME_FORMAT, time1)));
                        myHolder.timestampView.setVisibility(View.VISIBLE);
                    } else {
                        if (DateUtils.isCloseEnough(DateUtils.String2Long(TIME_FORMAT, time1),
                                DateUtils.String2Long(TIME_FORMAT, list.get(position - 1).getSendTime()))) {
                            myHolder.timestampView.setVisibility(View.GONE);
                        } else {
                            myHolder.timestampView.setText(DateUtils.getTimestamp2String(DateUtils.String2Date(TIME_FORMAT, time1)));
                            myHolder.timestampView.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e) {
                    L.e(TAG, "数据异常:" + e.getMessage());
                }

                String headImagePath = SuiuuInfo.ReadUserData(context).getHeadImg();
                if (!TextUtils.isEmpty(headImagePath)) {
                    myHolder.headImageView.setImageURI(Uri.parse(headImagePath));
                } else {
                    myHolder.headImageView.setImageURI(Uri.parse(failureImagePath));
                }

                String content = list.get(position).getContent();
                if (!TextUtils.isEmpty(content)) {
                    myHolder.contentView.setText(Html.fromHtml(content));
                } else {
                    myHolder.contentView.setText("");
                }
                break;

            case ACCEPT_MESSAGE:
                OtherLetterChatViewHolder otherHolder = (OtherLetterChatViewHolder) holder;
                String time01 = list.get(position).getSendTime();
                try {
                    if (position == 0) {
                        otherHolder.timestampView.setText(DateUtils.getTimestamp2String(DateUtils.String2Date(TIME_FORMAT, time01)));
                        otherHolder.timestampView.setVisibility(View.VISIBLE);
                    } else {
                        String time02 = list.get(position - 1).getSendTime();
                        if (DateUtils.isCloseEnough(DateUtils.String2Long(TIME_FORMAT, time01), DateUtils.String2Long(TIME_FORMAT, time02))) {
                            otherHolder.timestampView.setVisibility(View.GONE);
                        } else {
                            otherHolder.timestampView.setText(DateUtils.getTimestamp2String(DateUtils.String2Date(TIME_FORMAT, time01)));
                            otherHolder.timestampView.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e) {
                    L.e(TAG, "数据异常:" + e.getMessage());
                }

                if (!TextUtils.isEmpty(otherHeadImagePath)) {
                    otherHolder.headImageView.setImageURI(Uri.parse(otherHeadImagePath));
                } else {
                    otherHolder.headImageView.setImageURI(Uri.parse(failureImagePath));
                }

                String otherContent = list.get(position).getContent();
                if (!TextUtils.isEmpty(otherContent)) {
                    otherHolder.contentView.setText(otherContent);
                } else {
                    otherHolder.contentView.setText("");
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

        private TextView timestampView;
        private SimpleDraweeView headImageView;
        private TextView contentView;

        public MyLetterChatViewHolder(View itemView) {
            super(itemView);
            timestampView = (TextView) itemView.findViewById(R.id.item_letter_send_time_stamp);
            headImageView = (SimpleDraweeView) itemView.findViewById(R.id.item_letter_send_head_view);
            contentView = (TextView) itemView.findViewById(R.id.item_letter_send_content_view);
        }

    }

    private class OtherLetterChatViewHolder extends RecyclerView.ViewHolder {

        private TextView timestampView;
        private SimpleDraweeView headImageView;
        private TextView contentView;

        public OtherLetterChatViewHolder(View itemView) {
            super(itemView);
            timestampView = (TextView) itemView.findViewById(R.id.item_letter_accept_time_stamp);
            headImageView = (SimpleDraweeView) itemView.findViewById(R.id.item_letter_accept_head_view);
            contentView = (TextView) itemView.findViewById(R.id.item_letter_accept_content_view);
        }

    }

}