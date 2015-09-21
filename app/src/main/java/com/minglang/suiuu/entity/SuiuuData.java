package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 部分接口统一数据实体类
 * <p/>
 * Created by Administrator on 2015/4/28.
 */
public class SuiuuData {

    public String status;

    public List<SuiuuItemData> data;

    public SuiuuDataForMessage message;

    public String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SuiuuItemData> getData() {
        return data;
    }

    public void setData(List<SuiuuItemData> data) {
        this.data = data;
    }

    public SuiuuDataForMessage getMessage() {
        return message;
    }

    public void setMessage(SuiuuDataForMessage message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
