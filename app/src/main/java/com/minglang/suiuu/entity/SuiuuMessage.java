package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 消息数据实体类
 * <p/>
 * Created by Administrator on 2015/5/3.
 */
public class SuiuuMessage {

    public String status;

    public List<SuiuuMessageData> data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public List<SuiuuMessageData> getData() {
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
