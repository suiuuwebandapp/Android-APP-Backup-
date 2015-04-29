package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 关注的圈子的数据实体类
 * <p/>
 * Created by Administrator on 2015/4/27.
 */
public class AttentionLoop {

    public String status;

    public List<AttentionLoopData> data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AttentionLoopData> getData() {
        return data;
    }

    public void setData(List<AttentionLoopData> data) {
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
        return "AttentionLoop{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
