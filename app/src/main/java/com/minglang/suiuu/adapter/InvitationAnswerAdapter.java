package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.entity.InvitationAnswer.InvitationAnswerData.InviteUserEntity;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/8/18.
 * <p/>
 * 邀请回答用户的数据适配器
 */
public class InvitationAnswerAdapter extends BaseHolderAdapter<InviteUserEntity> {

    private OnLoadInvitationAnswerUserListener onLoadInvitationAnswerUserListener;

    public InvitationAnswerAdapter(Context context, List<InviteUserEntity> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    public void setOnLoadInvitationAnswerUserListener(OnLoadInvitationAnswerUserListener onLoadInvitationAnswerUserListener) {
        this.onLoadInvitationAnswerUserListener = onLoadInvitationAnswerUserListener;
    }

    @Override
    public void convert(ViewHolder helper, InviteUserEntity item, long position) {
        SimpleDraweeView headImageView = helper.getView(R.id.invitation_answer_user_head_view);
        TextView userNameView = helper.getView(R.id.invitation_answer_user_name);
        TextView userContentView = helper.getView(R.id.invitation_answer_user_content);
        ImageButton addUser = helper.getView(R.id.invitation_answer_user_add);

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

        addUser.setOnClickListener(new AddUserOnClickListener(item, position));

    }

    private class AddUserOnClickListener implements View.OnClickListener {

        private InviteUserEntity inviteUserEntity;
        private long position;

        private AddUserOnClickListener(InviteUserEntity inviteUserEntity, long position) {
            this.inviteUserEntity = inviteUserEntity;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (onLoadInvitationAnswerUserListener != null)
                onLoadInvitationAnswerUserListener.onLoadInvitation(inviteUserEntity, position);
        }
    }

    public interface OnLoadInvitationAnswerUserListener {
        void onLoadInvitation(InviteUserEntity inviteUserEntity, long position);
    }

}