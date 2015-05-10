package com.minglang.suiuu.entity;


/**
 * 详细关注动态数据实体类
 * <p/>
 * Created by Administrator on 2015/4/30.
 */
public class AllAttentionDynamic {

    public String status;

    public AllAttentionDynamicBase data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public AllAttentionDynamicBase getData() {
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
        return "AllAttentionDynamic{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
