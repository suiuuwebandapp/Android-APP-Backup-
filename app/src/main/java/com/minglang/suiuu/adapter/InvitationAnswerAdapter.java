package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.basic.BaseHolderAdapter;
import com.minglang.suiuu.entity.InvitationAnswer.InvitationAnswerData.InviteUserEntity;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/18.
 * <p/>
 * 邀请回答用户的数据适配器
 */
public class InvitationAnswerAdapter extends BaseHolderAdapter<InviteUserEntity> {

    private static final String TAG = InvitationAnswerAdapter.class.getSimpleName();

    private final List<String> userSignList = new ArrayList<>();

    public InvitationAnswerAdapter(Context context, List<InviteUserEntity> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    public  List<String> getUserSignList() {
        return userSignList;
    }

    @Override
    public void convert(ViewHolder helper, InviteUserEntity item, long position) {
        SimpleDraweeView headImageView = helper.getView(R.id.invitation_answer_user_head_view);
        TextView userNameView = helper.getView(R.id.invitation_answer_user_name);
        TextView userContentView = helper.getView(R.id.invitation_answer_user_content);
        CheckBox selectUser = helper.getView(R.id.invitation_answer_user_select);

        String headImagePath = item.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            headImageView.setImageURI(Uri.parse(headImagePath));
        }

        String userName = item.getNickname();
        if (!TextUtils.isEmpty(userName)) {
            userNameView.setText(userName);
        } else {
            userNameView.setText("");
        }

        userContentView.setText("");

        selectUser.setOnCheckedChangeListener(new SelectCheckedChangeListener(item));

    }

    private class SelectCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        private InviteUserEntity item;

        private SelectCheckedChangeListener(InviteUserEntity item) {
            this.item = item;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                userSignList.add(item.getUserSign());
            } else {
                userSignList.remove(item.getUserSign());
            }

            L.i(TAG, "userSignList:" + userSignList.toString());

        }

    }

}