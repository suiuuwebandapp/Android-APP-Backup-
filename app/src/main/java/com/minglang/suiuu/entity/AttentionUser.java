package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 关注的用户的数据实体类
 * <p/>
 * Created by Administrator on 2015/4/27.
 */
public class AttentionUser {

    public String status;

    public List<AttentionUserData> data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AttentionUserData> getData() {
        return data;
    }

    public void setData(List<AttentionUserData> data) {
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
        return "AttentionUser{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
