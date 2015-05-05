package com.minglang.suiuu.entity;

/**
 * 项目名称：Suiuu
 * 类描述：随游详细信息中ScenicList实体
 * 创建人：Administrator
 * 创建时间：2015/5/5 9:53
 * 修改人：Administrator
 * 修改时间：2015/5/5 9:53
 * 修改备注：
 */
public class SuiuuDetailForScenicList {
    private String ScenicId;
    private String tripId;
    private String name;
    private String lon;
    private String lat;

    @Override
    public String toString() {
        return "SuiuuDataInfoScenicList{" +
                "ScenicId='" + ScenicId + '\'' +
                ", tripId='" + tripId + '\'' +
                ", name='" + name + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }

    public String getScenicId() {
        return ScenicId;
    }

    public void setScenicId(String scenicId) {
        ScenicId = scenicId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
