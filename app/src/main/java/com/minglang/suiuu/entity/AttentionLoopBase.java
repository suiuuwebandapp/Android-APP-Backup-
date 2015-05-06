package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/6.
 */
public class AttentionLoopBase {

    public List<AttentionLoopData> data;

    public AttentionLoopMsg msg;

    public List<AttentionLoopData> getData() {
        return data;
    }

    public AttentionLoopMsg getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "AttentionLoopBase{" +
                "data=" + data +
                ", msg=" + msg +
                '}';
    }
}
