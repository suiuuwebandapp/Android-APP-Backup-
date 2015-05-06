package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/6.
 */
public class AttentionUserBase {

    public List<AttentionUserData> data;

    public AttentionUserMsg msg;

    public List<AttentionUserData> getData() {
        return data;
    }

    public AttentionUserMsg getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "AttentionUserBase{" +
                "data=" + data +
                ", msg=" + msg +
                '}';
    }
}
