package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 收藏的圈子数据实体类
 * <p/>
 * Created by Administrator on 2015/4/28.
 */
public class CollectionLoop {

    public String status;

    public List<CollectionLoopData> data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CollectionLoopData> getData() {
        return data;
    }

    public void setData(List<CollectionLoopData> data) {
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
        return "CollectionLoop{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
