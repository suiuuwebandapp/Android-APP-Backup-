package com.minglang.suiuu.entity;

/**
 * 部分接口统一数据实体类
 * <p/>
 * Created by Administrator on 2015/4/28.
 */
public class BaseCollection {

    public String status;

    public String data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
