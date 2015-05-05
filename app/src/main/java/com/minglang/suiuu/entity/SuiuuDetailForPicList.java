package com.minglang.suiuu.entity;

/**
 * 项目名称：Suiuu
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/5/5 9:47
 * 修改人：Administrator
 * 修改时间：2015/5/5 9:47
 * 修改备注：
 */
public class SuiuuDetailForPicList {
    private String picId;
    private String tripId;
    private String title;
    private String url;

    @Override
    public String toString() {
        return "SuiuuDatePicList{" +
                "picId='" + picId + '\'' +
                ", tripId='" + tripId + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
