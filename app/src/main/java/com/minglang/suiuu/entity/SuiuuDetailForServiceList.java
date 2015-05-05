package com.minglang.suiuu.entity;

/**
 * 项目名称：Suiuu
 * 类描述： 随游详细信息中serviceList中的实体
 * 创建人：Administrator
 * 创建时间：2015/5/5 9:57
 * 修改人：Administrator
 * 修改时间：2015/5/5 9:57
 * 修改备注：
 */
public class SuiuuDetailForServiceList {
    private String serviceId;
    private String tripId;
    private String title;
    private String money;
    private String type;

    @Override
    public String toString() {
        return "SuiuuDataInfoServiceList{" +
                "serviceId='" + serviceId + '\'' +
                ", tripId='" + tripId + '\'' +
                ", title='" + title + '\'' +
                ", money='" + money + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
