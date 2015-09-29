package com.minglang.suiuu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.basic.BaseHolderAdapter;
import com.minglang.suiuu.entity.AccountInfo.AccountInfoData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/8/24.
 * <p/>
 * 收款方式列表数据适配器
 */
public class ReceivablesWayAdapter extends BaseHolderAdapter<AccountInfoData> {

    private Context context;

    private OnDeleteReceivablesItemListener onDeleteReceivablesItemListener;

    public ReceivablesWayAdapter(Context context, List<AccountInfoData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        this.context = context;
    }

    public void setOnDeleteReceivablesItemListener(OnDeleteReceivablesItemListener onDeleteReceivablesItemListener) {
        this.onDeleteReceivablesItemListener = onDeleteReceivablesItemListener;
    }

    @Override
    public void convert(ViewHolder helper, AccountInfoData item, long position) {
        TextView titleHeadView = helper.getView(R.id.item_receivables_way_title_head);
        TextView userNameView = helper.getView(R.id.item_receivables_way_user_name);
        ImageView deleteView = helper.getView(R.id.item_receivables_way_delete);

        String type = item.getType();
        if (!TextUtils.isEmpty(type)) {
            switch (type) {
                case "1":
                    titleHeadView.setText(context.getResources().getText(R.string.WeChatPay_));
                    break;

                case "2":
                    titleHeadView.setText(context.getResources().getText(R.string.AliPay_));
                    break;
            }
        }

        String userName = item.getUsername();
        if (!TextUtils.isEmpty(userName)) {
            userNameView.setText(userName);
        }

        deleteView.setOnClickListener(new ReceivablesClick(position));

    }

    private class ReceivablesClick implements View.OnClickListener {

        private long position;

        private ReceivablesClick(long position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (onDeleteReceivablesItemListener != null) {
                onDeleteReceivablesItemListener.onDeleteReceivablesItem(position);
            }
        }

    }

    public interface OnDeleteReceivablesItemListener {
        void onDeleteReceivablesItem(long position);
    }

}