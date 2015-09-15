package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.MsgQuestion.MsgQuestionData.MsgQuestionItemData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by a on 2015/8/6.
 * 问答息数据实体类
 */
public class MsgQuestionAdapter extends BaseHolderAdapter<MsgQuestionItemData> {

    public MsgQuestionAdapter(Context context, List<MsgQuestionItemData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, MsgQuestionItemData item, long position) {
        SimpleDraweeView headImageView = helper.getView(R.id.item_msg_question_head_image_view);
        TextView userNameView = helper.getView(R.id.item_msg_question_user_name);
        TextView infoView = helper.getView(R.id.item_msg_question_info);
        TextView actionView = helper.getView(R.id.item_msg_question_action);

        String headImagePath = item.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            headImageView.setImageURI(Uri.parse(headImagePath));
        } else {
            headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
        }

        String userName = item.getNickname();
        if (!TextUtils.isEmpty(userName)) {
            userNameView.setText(Html.fromHtml("<u>" + userName + "</u>"));
        } else {
            userNameView.setText("");
        }

        String info = item.getQTitle();
        if (!TextUtils.isEmpty(info)) {
            infoView.setText(Html.fromHtml("<u>" + info + "</u>"));
        } else {
            infoView.setText("");
        }

        String action = item.getRelativeType();
        if (!TextUtils.isEmpty(action)) {
            switch (action) {
                case "4":
                    actionView.setText("关注了你");
                    break;
                case "7":
                    actionView.setText("回答了你");
                    break;
                case "8":
                    actionView.setText("邀请了你");
                    break;
            }
        } else {
            actionView.setText("");
        }

    }

}