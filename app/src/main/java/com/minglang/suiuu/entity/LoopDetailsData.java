package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/4/22.
 */
public class LoopDetailsData {

    public List<LoopDetailsDataList> data;

    public String attentionId;

    public LoopDetailsDataMsg msg;

    public List<LoopDetailsDataList> getData() {
        return data;
    }

    public String getAttentionId() {
        return attentionId;
    }

    public LoopDetailsDataMsg getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "LoopDetailsData{" +
                "data=" + data +
                ", attentionId='" + attentionId + '\'' +
                ", msg=" + msg +
                '}';
    }
}
