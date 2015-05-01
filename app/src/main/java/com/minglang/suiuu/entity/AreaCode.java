package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 国际电话区号数据实体类
 * <p/>
 * Created by Administrator on 2015/5/1.
 */
public class AreaCode {

    public String status;

    public List<AreaCodeData> data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AreaCodeData> getData() {
        return data;
    }

    public void setData(List<AreaCodeData> data) {
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
        return "AreaCode{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
