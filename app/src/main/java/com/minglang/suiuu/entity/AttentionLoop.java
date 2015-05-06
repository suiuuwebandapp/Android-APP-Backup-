package com.minglang.suiuu.entity;

/**
 * 关注的圈子的数据实体类
 * <p/>
 * Created by Administrator on 2015/4/27.
 */
public class AttentionLoop {

    public String status;

    public AttentionLoopBase data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public AttentionLoopBase getData() {
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
        return "AttentionLoop{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
