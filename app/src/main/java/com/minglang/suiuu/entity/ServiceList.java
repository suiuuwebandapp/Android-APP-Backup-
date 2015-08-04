package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/8/4.
 * <p/>
 * 服务数据实体类
 */
public class ServiceList {

    private String money;
    private String tripId;
    private String serviceId;
    private String title;
    private String type;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
