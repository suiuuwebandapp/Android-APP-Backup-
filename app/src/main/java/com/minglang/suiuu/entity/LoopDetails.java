package com.minglang.suiuu.entity;

/**
 * 圈子文章列表数据实体类
 * <p/>
 * Created by Administrator on 2015/4/22.
 */
public class LoopDetails {

    public String status;

    public LoopDetailsData data;

    public String message;

    public String getStatus() {
        return status;
    }

    public LoopDetailsData getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "LoopDetails{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
