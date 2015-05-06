package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/6.
 */
public class FansBase {

    List<FansData> data;

    public FansMsg msg;

    public List<FansData> getData() {
        return data;
    }

    public FansMsg getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "FansBase{" +
                "data=" + data +
                ", msg=" + msg +
                '}';
    }
}
