package com.minglang.suiuu.entity;

/**
 * 项目名称：Suiuu
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/5/5 10:32
 * 修改人：Administrator
 * 修改时间：2015/5/5 10:32
 * 修改备注：
 */
public class SuiuuDataDetail {
    private String status;
    private SuiuuDetailForData data;
    private String message;
    private String token;

    @Override
    public String toString() {
        return "SuiuuDataDetail{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SuiuuDetailForData getData() {
        return data;
    }

    public void setData(SuiuuDetailForData data) {
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
}
