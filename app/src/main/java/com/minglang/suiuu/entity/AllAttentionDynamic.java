package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 详细关注动态数据实体类
 * <p/>
 * Created by Administrator on 2015/4/30.
 */
public class AllAttentionDynamic {

    public String status;

    public List<AllAttentionDynamicData> data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AllAttentionDynamicData> getData() {
        return data;
    }

    public void setData(List<AllAttentionDynamicData> data) {
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
        return "AllAttentionDynamic{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
