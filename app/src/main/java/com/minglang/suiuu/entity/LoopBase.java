package com.minglang.suiuu.entity;

/**
 * 圈子数据实体类
 * <p/>
 * Created by Administrator on 2015/4/21.
 */
public class LoopBase {

    public String status;

    public LoopBaseDataObject data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public LoopBaseDataObject getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "LoopBase{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
