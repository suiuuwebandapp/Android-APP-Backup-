package com.minglang.suiuu.entity;

/**
 * 粉丝数据实体类
 * <p/>
 * Created by Administrator on 2015/5/1.
 */
public class Fans {

    public String status;

    public FansBase data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public FansBase getData() {
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
        return "Fans{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
