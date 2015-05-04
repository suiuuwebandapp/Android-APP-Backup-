package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/4.
 */
public class CollectionSuiuu {

    public String status;

    public List<CollectionSuiuuData> data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public List<CollectionSuiuuData> getData() {
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
        return "CollectionSuiuu{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
