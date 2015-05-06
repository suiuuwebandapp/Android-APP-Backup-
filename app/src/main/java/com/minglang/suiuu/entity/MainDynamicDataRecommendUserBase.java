package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/6.
 */
public class MainDynamicDataRecommendUserBase {

    public List<MainDynamicDataRecommendUser> data;

    public MainDynamicMsg msg;

    public List<MainDynamicDataRecommendUser> getData() {
        return data;
    }

    public MainDynamicMsg getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "MainDynamicDataRecommendUserBase{" +
                "data=" + data +
                ", msg=" + msg +
                '}';
    }
}
