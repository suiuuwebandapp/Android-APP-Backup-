package com.minglang.suiuu.entity;

/**
 * 关注的用户的数据实体类
 * <p/>
 * Created by Administrator on 2015/4/27.
 */
public class AttentionUser {

    public String status;

    public AttentionUserBase data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public AttentionUserBase getData() {
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
        return "AttentionUser{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
