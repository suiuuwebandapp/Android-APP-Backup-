package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 粉丝数据实体类
 * <p/>
 * Created by Administrator on 2015/5/1.
 */
public class Fans {

    public String status;

    public List<FansData> data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FansData> getData() {
        return data;
    }

    public void setData(List<FansData> data) {
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
        return "Fans{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
