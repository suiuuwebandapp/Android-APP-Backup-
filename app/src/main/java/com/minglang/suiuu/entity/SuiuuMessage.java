package com.minglang.suiuu.entity;

/**
 * 消息数据实体类
 * <p/>
 * Created by Administrator on 2015/5/3.
 */
public class SuiuuMessage {

    public String status;

    public SuiuuMessageBase data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public SuiuuMessageBase getData() {
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
        return "SuiuuMessage{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
