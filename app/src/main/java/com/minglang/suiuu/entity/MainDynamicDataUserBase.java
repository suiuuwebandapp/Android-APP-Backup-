package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/6.
 */
public class MainDynamicDataUserBase {

    public List<MainDynamicDataUser> data;

    public MainDynamicMsg msg;

    public List<MainDynamicDataUser> getData() {
        return data;
    }

    public MainDynamicMsg getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "MainDynamicDataUserBase{" +
                "data=" + data +
                ", msg=" + msg +
                '}';
    }
}
