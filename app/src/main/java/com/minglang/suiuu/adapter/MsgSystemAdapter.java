package com.minglang.suiuu.adapter;

import android.content.Context;

import com.minglang.suiuu.entity.MsgSystem.MsgSystemData.MsgSystemItemData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by a on 2015/8/6.
 * 系统消息数据适配器
 */
public class MsgSystemAdapter extends BaseHolderAdapter<MsgSystemItemData>{

    public MsgSystemAdapter(Context context, List<MsgSystemItemData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, MsgSystemItemData item, long position) {

    }

}