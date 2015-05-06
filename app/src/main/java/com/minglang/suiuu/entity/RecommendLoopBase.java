package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/6.
 */
public class RecommendLoopBase {

    public List<RecommendLoopData> data;

    public RecommendLoopMsg msg;

    public List<RecommendLoopData> getData() {
        return data;
    }

    public RecommendLoopMsg getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "RecommendLoopBase{" +
                "data=" + data +
                ", msg=" + msg +
                '}';
    }
}
