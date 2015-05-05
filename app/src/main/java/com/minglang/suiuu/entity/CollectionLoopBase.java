package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/5.
 */
public class CollectionLoopBase {

    public List<CollectionLoopData> data;

    public CollectionLoopMsg msg;

    public List<CollectionLoopData> getData() {
        return data;
    }

    public CollectionLoopMsg getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "CollectionLoopBase{" +
                "data=" + data +
                ", msg=" + msg +
                '}';
    }
}
