package com.minglang.suiuu.entity;

/**
 * 项目名称：Suiuu
 * 类描述：随游详细信息中的InFo
 * 创建人：Administrator
 * 创建时间：2015/5/4 19:37
 * 修改人：Administrator
 * 修改时间：2015/5/4 19:37
 * 修改备注：
 */
public class SuiuuDetailForInfo {
    private String tripId;
    private String createPublisherId;
    private String createTime;
    private String title;
    private String titleImg;
    private String countryId;
    private String cityId;
    private String lon;
    private String lat;
    private String basePrice;
    private String maxUserCount;
    private String isAirplane;
    private String isHotel;
    private String score;
    private String startTime;
    private String endTime;
    private String travelTime;
    private String travelTimeType;
    private String intro;
    private String info;
    private String tags;
    private String status;

    public String getMaxUserCount() {
        return maxUserCount;
    }

    public void setMaxUserCount(String maxUserCount) {
        this.maxUserCount = maxUserCount;
    }

    @Override
    public String toString() {
        return "SuiuuDataInfo{" +
                "tripId='" + tripId + '\'' +
                ", createPublisherId='" + createPublisherId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", title='" + title + '\'' +
                ", titleImg='" + titleImg + '\'' +
                ", countryId='" + countryId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", basePrice='" + basePrice + '\'' +
                ", makeUserCount='" + '\'' +
                ", isAirplane='" + isAirplane + '\'' +
                ", isHotel='" + isHotel + '\'' +
                ", score='" + score + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", travelTime='" + travelTime + '\'' +
                ", travelTimeType='" + travelTimeType + '\'' +
                ", intro='" + intro + '\'' +
                ", info='" + info + '\'' +
                ", tags='" + tags + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getCreatePublisherId() {
        return createPublisherId;
    }

    public void setCreatePublisherId(String createPublisherId) {
        this.createPublisherId = createPublisherId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
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

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }


    public String getIsAirplane() {
        return isAirplane;
    }

    public void setIsAirplane(String isAirplane) {
        this.isAirplane = isAirplane;
    }

    public String getIsHotel() {
        return isHotel;
    }

    public void setIsHotel(String isHotel) {
        this.isHotel = isHotel;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getTravelTimeType() {
        return travelTimeType;
    }

    public void setTravelTimeType(String travelTimeType) {
        this.travelTimeType = travelTimeType;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
