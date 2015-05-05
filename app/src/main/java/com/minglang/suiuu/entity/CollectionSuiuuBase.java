package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/5.
 */
public class CollectionSuiuuBase {

    public List<CollectionSuiuuData> data;

    public CollectionSuiuuMsg msg;

    public List<CollectionSuiuuData> getData() {
        return data;
    }

    public CollectionSuiuuMsg getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "CollectionSuiuuBase{" +
                "data=" + data +
                ", msg=" + msg +
                '}';
    }
}
