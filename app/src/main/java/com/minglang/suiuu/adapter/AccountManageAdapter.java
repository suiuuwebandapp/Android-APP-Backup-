package com.minglang.suiuu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.basic.BaseHolderAdapter;
import com.minglang.suiuu.entity.TransferAccounts.TransferAccountsData.TransferAccountsItemData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2015/8/31.
 * <p/>
 * 账户转账信息数据适配器
 */
public class AccountManageAdapter extends BaseHolderAdapter<TransferAccountsItemData> {

    public AccountManageAdapter(Context context, List<TransferAccountsItemData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, TransferAccountsItemData item, long position) {
        TextView dateView = helper.getView(R.id.item_account_manage_date);
        TextView infoView = helper.getView(R.id.item_account_manage_info);
        TextView moneyView = helper.getView(R.id.item_account_manage_money);

        String date = item.getRecordTime();
        if (!TextUtils.isEmpty(date)) {
            dateView.setText(date);
        } else {
            dateView.setText("");
        }

        String info = item.getInfo();
        if (!TextUtils.isEmpty(info)) {
            infoView.setText(info);
        } else {
            infoView.setText("");
        }

        String money = item.getMoney();
        if (!TextUtils.isEmpty(money)) {
            moneyView.setText(money);
        } else {
            moneyView.setText("0.0");
        }
    }

}