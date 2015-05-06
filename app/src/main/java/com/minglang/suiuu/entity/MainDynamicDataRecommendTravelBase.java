package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/6.
 */
public class MainDynamicDataRecommendTravelBase {

    public List<MainDynamicDataRecommendTravel>data;

    public MainDynamicMsg msg;

    public List<MainDynamicDataRecommendTravel> getData() {
        return data;
    }

    public MainDynamicMsg getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "MainDynamicDataRecommendTravelBase{" +
                "data=" + data +
                ", msg=" + msg +
                '}';
    }
}
