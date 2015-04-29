package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/4/22.
 */
public class LoopDetailsData {

    public List<LoopDetailsDataList> data;

    public String attentionId;

    public List<LoopDetailsDataList> getData() {
        return data;
    }

    public void setData(List<LoopDetailsDataList> data) {
        this.data = data;
    }

    public String getAttentionId() {
        return attentionId;
    }

    public void setAttentionId(String attentionId) {
        this.attentionId = attentionId;
    }

    @Override
    public String toString() {
        return "LoopDetailsData{" +
                "data=" + data +
                ", attentionId='" + attentionId + '\'' +
                '}';
    }
}
