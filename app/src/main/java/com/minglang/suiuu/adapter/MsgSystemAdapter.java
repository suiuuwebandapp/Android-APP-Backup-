package com.minglang.suiuu.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.minglang.suiuu.R;
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
        TextView contentView = helper.getView(R.id.item_msg_system_content);

        String content = item.getContent();
        if (!TextUtils.isEmpty(content)) {
            contentView.setText(content);
        } else {
            contentView.setText("");
        }

    }

}