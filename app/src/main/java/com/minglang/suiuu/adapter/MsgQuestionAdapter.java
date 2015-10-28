package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class MsgQuestionAdapter extends BaseAdapter {

    private static final int ATTENTION = 0;
    private static final int QUESTION = 1;
    private static final int INVITATION = 2;

    private Context context;

    private List<MsgQuestionItemData> list;

    public MsgQuestionAdapter(Context context, List<MsgQuestionItemData> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<MsgQuestionItemData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (list != null && list.size() > 0) {
            return list.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        String type = list.get(position).getRelativeType();
        switch (type) {
            case "4":
                return ATTENTION;

            case "7":
                return QUESTION;

            case "8":
                return INVITATION;
        }
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case ATTENTION:
                ViewHolder holder4 = ViewHolder.get(context, convertView, parent, R.layout.item_msg_question_4, position);
                convertView = holder4.getConvertView();

                SimpleDraweeView headImageView_4 = holder4.getView(R.id.item_msg_question_4_head_image_view);
                TextView userNameView_4 = holder4.getView(R.id.item_msg_question_4_user_name);
                TextView problemView_4 = holder4.getView(R.id.item_msg_question_4_problem_view);

                headImageView_4.setImageURI(Uri.parse(list.get(position).getHeadImg()));
                String userName = list.get(position).getNickname();
                if (!TextUtils.isEmpty(userName)) {
                    userNameView_4.setText(userName);
                }

                String problem = list.get(position).getQTitle();
                if (!TextUtils.isEmpty(problem)) {
                    problemView_4.setText(problem);
                }
                break;

            case QUESTION:
                ViewHolder holder7 = ViewHolder.get(context, convertView, parent, R.layout.item_msg_question_7, position);
                convertView = holder7.getConvertView();

                SimpleDraweeView headImageView_7 = holder7.getView(R.id.item_msg_question_7_head_image_view);
                TextView userNameView_7 = holder7.getView(R.id.item_msg_question_7_user_name);
                TextView problemView_7 = holder7.getView(R.id.item_msg_question_7_problem_view);

                headImageView_7.setImageURI(Uri.parse(list.get(position).getHeadImg()));
                String userName7 = list.get(position).getNickname();
                if (!TextUtils.isEmpty(userName7)) {
                    userNameView_7.setText(userName7);
                }

                String problem7 = list.get(position).getQTitle();
                if (!TextUtils.isEmpty(problem7)) {
                    problemView_7.setText(problem7);
                }
                break;

            case INVITATION:
                ViewHolder holder8 = ViewHolder.get(context, convertView, parent, R.layout.item_msg_question_8, position);
                convertView = holder8.getConvertView();

                SimpleDraweeView headImageView_8 = holder8.getView(R.id.item_msg_question_8_head_image_view);
                TextView userNameView_8 = holder8.getView(R.id.item_msg_question_8_user_name);
                TextView problemView_8 = holder8.getView(R.id.item_msg_question_8_problem_view);

                headImageView_8.setImageURI(Uri.parse(list.get(position).getHeadImg()));
                String userName8 = list.get(position).getNickname();
                if (!TextUtils.isEmpty(userName8)) {
                    userNameView_8.setText(userName8);
                }

                String problem8 = list.get(position).getQTitle();
                if (!TextUtils.isEmpty(problem8)) {
                    problemView_8.setText(problem8);
                }
                break;
        }
        return convertView;
    }

}