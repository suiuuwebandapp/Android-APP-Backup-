package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.basic.BaseHolderAdapter;
import com.minglang.suiuu.entity.AttentionProblem.AttentionProblemData.AttentionProblemItemData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * 关注的问答的数据适配器
 * <p/>
 * Created by Administrator on 2015/7/21.
 */
public class AttentionProblemAdapter extends BaseHolderAdapter<AttentionProblemItemData> {

    public AttentionProblemAdapter(Context context, List<AttentionProblemItemData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, AttentionProblemItemData item, long position) {
        SimpleDraweeView headView = helper.getView(R.id.item_attention_problem_head_view);
        TextView commentNumber = helper.getView(R.id.item_attention_problem_comment_number);
        TextView titleView = helper.getView(R.id.item_attention_problem_title);
        TextView contentView = helper.getView(R.id.item_attention_problem_content);

        String headImagePath = item.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            headView.setImageURI(Uri.parse(headImagePath));
        } else {
            headView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image));
        }

        String strCommentNumber = item.getaNumber();
        if (!TextUtils.isEmpty(strCommentNumber)) {
            commentNumber.setText(strCommentNumber);
        } else {
            commentNumber.setText("0");
        }

        String strTitle = item.getQTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            titleView.setText(strTitle);
        } else {
            titleView.setText("");
        }

        String strContent = item.getQContent();
        if (!TextUtils.isEmpty(strContent)) {
            contentView.setText(strContent);
        } else {
            contentView.setText("");
        }

    }

}