package com.minglang.suiuu.adapter;

import android.content.Context;

import com.minglang.suiuu.entity.MsgOrder.MsgOrderData.OrderEntity.MsgOrderItemData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by a on 2015/8/6.
 * <p/>
 * 订单消息数据适配器
 */
public class MsgOrderAdapter extends BaseHolderAdapter<MsgOrderItemData> {

    public MsgOrderAdapter(Context context, List<MsgOrderItemData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, MsgOrderItemData item, long position) {

    }

}