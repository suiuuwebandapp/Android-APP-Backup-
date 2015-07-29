package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/7/29.
 * <p/>
 * 服务数据实体类
 */
public class ServiceInfo {

    /**
     * money : 2000.00
     * tripId : 105
     * serviceId : 422
     * title : 独家包车
     * type : 0
     */
    private String money;
    private String tripId;
    private String serviceId;
    private String title;
    private String type;

    public void setMoney(String money) {
        this.money = money;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public String getTripId() {
        return tripId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }
}
