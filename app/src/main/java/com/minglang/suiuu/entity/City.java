package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/13.
 */
public class City {

    public String status;

    public List<CityData>data;

    public String message;

    public String token;

    public String getStatus() {
        return status;
    }

    public List<CityData> getData() {
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
        return "City{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
