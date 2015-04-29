package com.minglang.suiuu.entity;

import java.io.Serializable;

/**
 * 登陆返回数据实体类
 * <p/>
 * Created by Administrator on 2015/4/25.
 */
public class UserBack implements Serializable {

    private static final long serialVersionUID = -150698419334277565L;

    public String status;

    public UserBackData data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserBackData getData() {
        return data;
    }

    public void setData(UserBackData data) {
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
        return "UserBack{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
