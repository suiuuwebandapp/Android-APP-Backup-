package com.minglang.suiuu.entity;

/**
 * 部分接口统一数据实体类
 * <p/>
 * Created by Administrator on 2015/4/28.
 */
public class SuiuuReturnDate {

    public String status;

    public SuiuuData data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public SuiuuData getData() {
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
        return "BaseCollection{" +
                "status='" + status + '\'' +
                ", data='" + data + '\'' +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
