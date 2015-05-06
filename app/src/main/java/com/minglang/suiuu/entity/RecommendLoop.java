package com.minglang.suiuu.entity;

/**
 * 推荐的圈子的数据实体类
 * <p/>
 * Created by Administrator on 2015/5/6.
 */
public class RecommendLoop {

    public String status;

    public RecommendLoopBase data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public RecommendLoopBase getData() {
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
        return "RecommendLoop{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
