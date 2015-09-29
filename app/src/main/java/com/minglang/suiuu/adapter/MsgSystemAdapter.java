package com.minglang.suiuu.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.basic.BaseHolderAdapter;
import com.minglang.suiuu.entity.MsgSystem.MsgSystemData.MsgSystemItemData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by a on 2015/8/6.
 * 系统消息数据适配器
 */
public class MsgSystemAdapter extends BaseHolderAdapter<MsgSystemItemData> {

    public MsgSystemAdapter(Context context, List<MsgSystemItemData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, MsgSystemItemData item, long position) {
        TextView userNameView = helper.getView(R.id.item_msg_system_user_name);
        TextView titleView = helper.getView(R.id.item_msg_system_title);

        TextView joinView = helper.getView(R.id.item_msg_system_join);
        TextView consentJoinView = helper.getView(R.id.item_msg_system_consent_join);
        TextView refusalJoinView = helper.getView(R.id.item_msg_system_refusal_join);
        TextView removeView = helper.getView(R.id.item_msg_system_remove);

        String userName = item.getNickname();
        if (!TextUtils.isEmpty(userName)) {
            userNameView.setText(Html.fromHtml("<u>" + userName + "</u>"));
        } else {
            userNameView.setText("");
        }

        String title = item.getTitle();
        if (!TextUtils.isEmpty(title)) {
            titleView.setText(Html.fromHtml("<u>" + title + "</u>"));
        } else {
            titleView.setText("");
        }

        String type = item.getRelativeType();
        if (!TextUtils.isEmpty(type)) {
            switch (type) {
                case "5":
                    joinView.setVisibility(View.VISIBLE);
                    consentJoinView.setVisibility(View.GONE);
                    refusalJoinView.setVisibility(View.GONE);
                    removeView.setVisibility(View.GONE);
                    break;

                case "13":
                    joinView.setVisibility(View.GONE);
                    consentJoinView.setVisibility(View.VISIBLE);
                    refusalJoinView.setVisibility(View.GONE);
                    removeView.setVisibility(View.GONE);
                    break;

                case "14":
                    joinView.setVisibility(View.GONE);
                    consentJoinView.setVisibility(View.GONE);
                    refusalJoinView.setVisibility(View.VISIBLE);
                    removeView.setVisibility(View.GONE);
                    break;

                case "15":
                    joinView.setVisibility(View.GONE);
                    consentJoinView.setVisibility(View.GONE);
                    refusalJoinView.setVisibility(View.GONE);
                    removeView.setVisibility(View.VISIBLE);
                    break;

                default:
                    joinView.setVisibility(View.GONE);
                    consentJoinView.setVisibility(View.GONE);
                    refusalJoinView.setVisibility(View.GONE);
                    removeView.setVisibility(View.GONE);
                    break;
            }
        } else {
            joinView.setVisibility(View.GONE);
            consentJoinView.setVisibility(View.GONE);
            refusalJoinView.setVisibility(View.GONE);
            removeView.setVisibility(View.GONE);
        }

    }

}