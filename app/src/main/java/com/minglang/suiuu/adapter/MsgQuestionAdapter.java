package com.minglang.suiuu.adapter;

import android.content.Context;

import com.minglang.suiuu.entity.MsgQuestion.MsgQuestionData.MsgQuestionItemData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by a on 2015/8/6.
 * 问答息数据实体类
 */
public class MsgQuestionAdapter extends BaseHolderAdapter<MsgQuestionItemData> {

    public MsgQuestionAdapter(Context context, List<MsgQuestionItemData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, MsgQuestionItemData item, long position) {

    }

}