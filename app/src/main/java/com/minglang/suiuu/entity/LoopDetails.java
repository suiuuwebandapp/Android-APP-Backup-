package com.minglang.suiuu.entity;

/**
 * 圈子详情数据实体类
 * <p/>
 * Created by Administrator on 2015/4/22.
 */
public class LoopDetails {

    public String status;

    public LoopDetailsData data;

    public String message;

    public String page_list;

    public String pa;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoopDetailsData getData() {
        return data;
    }

    public void setData(LoopDetailsData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPage_list() {
        return page_list;
    }

    public void setPage_list(String page_list) {
        this.page_list = page_list;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    @Override
    public String toString() {
        return "LoopDetails{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", page_list='" + page_list + '\'' +
                ", pa='" + pa + '\'' +
                '}';
    }
}
