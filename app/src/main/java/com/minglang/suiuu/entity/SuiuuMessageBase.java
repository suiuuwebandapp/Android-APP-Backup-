package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/7.
 */
public class SuiuuMessageBase {

    public List<SuiuuMessageData> data;

    public SuiuuMessageMsg msg;

    public List<SuiuuMessageData> getData() {
        return data;
    }

    public SuiuuMessageMsg getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "SuiuuMessageBase{" +
                "data=" + data +
                ", msg=" + msg +
                '}';
    }
}
