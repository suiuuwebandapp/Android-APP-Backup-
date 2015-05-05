package com.minglang.suiuu.entity;

/**
 * 圈子文章数据实体类
 * <p/>
 * Created by Administrator on 2015/4/25.
 */
public class LoopArticle {

    public String status;

    public LoopArticleData data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public LoopArticleData getData() {
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
        return "LoopArticle{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
