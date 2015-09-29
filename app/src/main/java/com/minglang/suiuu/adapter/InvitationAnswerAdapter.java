package com.minglang.suiuu.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.basic.BaseHolderAdapter;
import com.minglang.suiuu.entity.InvitationAnswer.InvitationAnswerData.InviteUserEntity;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2015/8/18.
 * <p/>
 * 邀请回答用户的数据适配器
 */
public class InvitationAnswerAdapter extends BaseHolderAdapter<InviteUserEntity> {

    private static final String TAG = InvitationAnswerAdapter.class.getSimpleName();

    private Set<Integer> set;

    private SparseBooleanArray sparseBooleanArray;

    public InvitationAnswerAdapter(Context context, List<InviteUserEntity> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        set = new HashSet<>();
        sparseBooleanArray = new SparseBooleanArray();
    }

    public Set<Integer> getSet() {
        return set;
    }

    public SparseBooleanArray getSparseBooleanArray() {
        return sparseBooleanArray;
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

        selectUser.setOnCheckedChangeListener(new SelectCheckedChangeListener(position));

    }

    private class SelectCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        private long position;

        private SelectCheckedChangeListener(long position) {
            this.position = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            L.i(TAG, "position:" + position);

            if (isChecked) {
                set.add(Long.bitCount(position));
            } else {
                set.remove(Long.bitCount(position));
            }

            L.i(TAG, "Set<Integer> set:" + set.toString());

            sparseBooleanArray.put(Long.bitCount(position), isChecked);

            L.i(TAG, "sparseBooleanArray:" + sparseBooleanArray.toString());

        }

    }

}