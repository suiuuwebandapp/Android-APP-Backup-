package com.minglang.suiuu.adapter;

import android.content.Context;

import com.minglang.suiuu.entity.MsgTripGallery.MsgTripGalleryData.MsgTripGalleryItemData;
import com.minglang.suiuu.utils.ViewHolder;

import java.util.List;

/**
 * Created by a on 2015/8/6.
 *
 * 旅途消息数据适配器
 */
public class MsgTripGalleryAdapter extends BaseHolderAdapter<MsgTripGalleryItemData> {

    public MsgTripGalleryAdapter(Context context, List<MsgTripGalleryItemData> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, MsgTripGalleryItemData item, long position) {

    }

}