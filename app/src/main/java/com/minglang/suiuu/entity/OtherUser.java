package com.minglang.suiuu.entity;

/**
 * 其他用户数据实体类
 * <p/>
 * Created by Administrator on 2015/5/1.
 */
public class OtherUser {

    public String status;

    public OtherUserData data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OtherUserData getData() {
        return data;
    }

    public void setData(OtherUserData data) {
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
        return "OtherUser{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
