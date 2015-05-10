package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by LZY on 2015/5/10 0010.
 */
public class AllAttentionDynamicBase {

    public List<AllAttentionDynamicData> data;

    public AllAttentionDynamicMsg msg;

    public List<AllAttentionDynamicData> getData() {
        return data;
    }

    public AllAttentionDynamicMsg getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "AllAttentionDynamicBase{" +
                "data=" + data +
                ", msg=" + msg +
                '}';
    }
}
