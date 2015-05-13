package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 国家数据实体类
 * <p/>
 * Created by Administrator on 2015/5/13.
 */
public class Country {

    public String status;

    public List<CountryData>data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public List<CountryData> getData() {
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
        return "Country{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
