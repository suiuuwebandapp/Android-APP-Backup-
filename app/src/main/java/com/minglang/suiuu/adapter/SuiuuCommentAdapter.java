package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.SuiuuComment.SuiuuCommentData.SuiuuCommentItemData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/9/8.
 * <p/>
 * 随游评论数据适配器
 */
public class SuiuuCommentAdapter extends BaseHolderAdapter<SuiuuCommentItemData> {

    private String failureImagePath;

    public SuiuuCommentAdapter(Context context, List<SuiuuCommentItemData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        failureImagePath = "res://com.minglang.suiuu/" + R.drawable.default_head_image_error;
    }

    @Override
    public void convert(ViewHolder helper, SuiuuCommentItemData item, long position) {
        SimpleDraweeView headImageView = helper.getView(R.id.item_suiuu_comment_header_image);
        TextView titleView = helper.getView(R.id.item_suiuu_comment_title);
        TextView contentView = helper.getView(R.id.item_suiuu_comment_content);

        String headImagePath = item.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            headImageView.setImageURI(Uri.parse(headImagePath));
        } else {
            headImageView.setImageURI(Uri.parse(failureImagePath));
        }

        String title = item.getTitle();
        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        } else {
            titleView.setText("");
        }

        String content = item.getContent();
        if (!TextUtils.isEmpty(content)) {
            contentView.setText(content);
        } else {
            contentView.setText("");
        }

    }

}