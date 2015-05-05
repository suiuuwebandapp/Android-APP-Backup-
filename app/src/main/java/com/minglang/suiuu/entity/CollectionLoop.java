package com.minglang.suiuu.entity;

/**
 * 收藏的圈子数据实体类
 * <p/>
 * Created by Administrator on 2015/4/28.
 */
public class CollectionLoop {

    public String status;

    public CollectionLoopBase data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public CollectionLoopBase getData() {
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
        return "CollectionLoop{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
