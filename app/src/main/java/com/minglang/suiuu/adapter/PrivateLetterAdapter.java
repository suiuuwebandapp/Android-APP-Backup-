package com.minglang.suiuu.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.PersonalMainPagerActivity;
import com.minglang.suiuu.entity.PrivateLetter.PrivateLetterData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/9/11.
 * <p/>
 * 私信数据适配器
 */
public class PrivateLetterAdapter extends BaseHolderAdapter<PrivateLetterData> {

    private static final String USER_SIGN = "userSign";

    private Context context;

    public PrivateLetterAdapter(Context context, List<PrivateLetterData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder helper, PrivateLetterData item, long position) {
        SimpleDraweeView headImageView = helper.getView(R.id.item_private_letter_head_image_view);
        TextView userNameView = helper.getView(R.id.item_private_letter_user_name);
        TextView dateView = helper.getView(R.id.item_private_letter_date);
        TextView contentView = helper.getView(R.id.item_private_letter_content);

        String headImagePath = item.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            headImageView.setImageURI(Uri.parse(headImagePath));
        } else {
            headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
        }

        String userName = item.getNickname();
        if (!TextUtils.isEmpty(userName)) {
            userNameView.setText(userName);
        } else {
            userNameView.setText("");
        }

        String lastTime = item.getLastConcatTime();
        if (!TextUtils.isEmpty(lastTime)) {
            dateView.setText(lastTime);
        } else {
            dateView.setText("");
        }

        String lastContent = item.getLastContentInfo();
        if (!TextUtils.isEmpty(lastContent)) {
            contentView.setText(lastContent);
        } else {
            contentView.setText("");
        }

        headImageView.setOnClickListener(new HeadImageClickListener(item));

    }

    private class HeadImageClickListener implements View.OnClickListener {

        private PrivateLetterData item;

        private HeadImageClickListener(PrivateLetterData item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, PersonalMainPagerActivity.class);
            intent.putExtra(USER_SIGN, item.getRelateId());
            context.startActivity(intent);
        }
    }

}