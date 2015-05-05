package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/5.
 */
public class LoopBaseDataObject {

    public List<LoopBaseData> data;

    public LoopBaseDataMsg msg;

    public List<LoopBaseData> getData() {
        return data;
    }

    public LoopBaseDataMsg getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "LoopBaseDataObject{" +
                "data=" + data +
                ", msg=" + msg +
                '}';
    }
}
