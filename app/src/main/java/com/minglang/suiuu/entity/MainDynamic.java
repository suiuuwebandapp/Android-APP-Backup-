package com.minglang.suiuu.entity;

/**
 * 首页动态数据实体类
 * <p/>
 * Created by Administrator on 2015/4/29.
 */
public class MainDynamic {

    public String status;

    public MainDynamicData data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MainDynamicData getData() {
        return data;
    }

    public void setData(MainDynamicData data) {
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
        return "MainDynamic{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
