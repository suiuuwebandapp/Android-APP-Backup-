package com.minglang.suiuu.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2015/7/31.
 *
 * 用户的随游数据实体类
 */
public class UserSuiuu {

    /**
     * status : 1
     * data : [{"tripId":"104","createPublisherId":"11","createTime":"2015-05-13 11:41:49","title":"游东京感受日本三道文化-花道、茶道、书道","titleImg":"http://image.suiuu.com/suiuu_head/20150724021112_79300.png","countryId":"3005","cityId":"3018","lon":null,"lat":null,"basePrice":"1103","oldPrice":"1050.00","basePriceType":"1","maxUserCount":"6","score":"10","tripCount":"3","startTime":"09:00:00","endTime":"19:00:00","travelTime":"10","travelTimeType":"1","intro":"游东京感受日本三道文化-花道、茶道、书道","info":"文化一日游，有内涵、讲深度、有趣味。从庄严地天皇居所，到人文圣地东大，夜晚领略歌舞伎町的歌舞升平，体验菊与刀的纯正日本精神。","tags":"家庭,美食,猎奇,自然,浪漫,购物","commentCount":"1","collectCount":"0","isHot":"0","type":"1","status":"1","nickname":"dorlen","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","count":null,"names":"包车,东京成田空港接机,东京羽田空港","userSign":"a4c1406ff4cc382389f19bf6ec3e55c1"}]
     * message :
     * token : 764baef79fc634ef85f7ba56aea9ddd5
     */

    private int status;
    private String message;
    private String token;
    @SerializedName("data")
    private List<UserSuiuuData> userSuiuuData;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserSuiuuData(List<UserSuiuuData> UserSuiuuData) {
        this.userSuiuuData = UserSuiuuData;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public List<UserSuiuuData> getUserSuiuuData() {
        return userSuiuuData;
    }

    public static class UserSuiuuData {
        /**
         * tripId : 104
         * createPublisherId : 11
         * createTime : 2015-05-13 11:41:49
         * title : 游东京感受日本三道文化-花道、茶道、书道
         * titleImg : http://image.suiuu.com/suiuu_head/20150724021112_79300.png
         * countryId : 3005
         * cityId : 3018
         * lon : null
         * lat : null
         * basePrice : 1103
         * oldPrice : 1050.00
         * basePriceType : 1
         * maxUserCount : 6
         * score : 10
         * tripCount : 3
         * startTime : 09:00:00
         * endTime : 19:00:00
         * travelTime : 10
         * travelTimeType : 1
         * intro : 游东京感受日本三道文化-花道、茶道、书道
         * info : 文化一日游，有内涵、讲深度、有趣味。从庄严地天皇居所，到人文圣地东大，夜晚领略歌舞伎町的歌舞升平，体验菊与刀的纯正日本精神。
         * tags : 家庭,美食,猎奇,自然,浪漫,购物
         * commentCount : 1
         * collectCount : 0
         * isHot : 0
         * type : 1
         * status : 1
         * nickname : dorlen
         * headImg : http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg
         * count : null
         * names : 包车,东京成田空港接机,东京羽田空港
         * userSign : a4c1406ff4cc382389f19bf6ec3e55c1
         */

        private String tripId;
        private String createPublisherId;
        private String createTime;
        private String title;
        private String titleImg;
        private String countryId;
        private String cityId;
        private Object lon;
        private Object lat;
        private String basePrice;
        private String oldPrice;
        private String basePriceType;
        private String maxUserCount;
        private String score;
        private String tripCount;
        private String startTime;
        private String endTime;
        private String travelTime;
        private String travelTimeType;
        private String intro;
        private String info;
        private String tags;
        private String commentCount;
        private String collectCount;
        private String isHot;
        private String type;
        private String status;
        private String nickname;
        private String headImg;
        private Object count;
        private String names;
        private String userSign;

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public void setCreatePublisherId(String createPublisherId) {
            this.createPublisherId = createPublisherId;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTitleImg(String titleImg) {
            this.titleImg = titleImg;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public void setLon(Object lon) {
            this.lon = lon;
        }

        public void setLat(Object lat) {
            this.lat = lat;
        }

        public void setBasePrice(String basePrice) {
            this.basePrice = basePrice;
        }

        public void setOldPrice(String oldPrice) {
            this.oldPrice = oldPrice;
        }

        public void setBasePriceType(String basePriceType) {
            this.basePriceType = basePriceType;
        }

        public void setMaxUserCount(String maxUserCount) {
            this.maxUserCount = maxUserCount;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setTripCount(String tripCount) {
            this.tripCount = tripCount;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public void setTravelTime(String travelTime) {
            this.travelTime = travelTime;
        }

        public void setTravelTimeType(String travelTimeType) {
            this.travelTimeType = travelTimeType;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public void setCommentCount(String commentCount) {
            this.commentCount = commentCount;
        }

        public void setCollectCount(String collectCount) {
            this.collectCount = collectCount;
        }

        public void setIsHot(String isHot) {
            this.isHot = isHot;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public void setCount(Object count) {
            this.count = count;
        }

        public void setNames(String names) {
            this.names = names;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public String getTripId() {
            return tripId;
        }

        public String getCreatePublisherId() {
            return createPublisherId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getTitle() {
            return title;
        }

        public String getTitleImg() {
            return titleImg;
        }

        public String getCountryId() {
            return countryId;
        }

        public String getCityId() {
            return cityId;
        }

        public Object getLon() {
            return lon;
        }

        public Object getLat() {
            return lat;
        }

        public String getBasePrice() {
            return basePrice;
        }

        public String getOldPrice() {
            return oldPrice;
        }

        public String getBasePriceType() {
            return basePriceType;
        }

        public String getMaxUserCount() {
            return maxUserCount;
        }

        public String getScore() {
            return score;
        }

        public String getTripCount() {
            return tripCount;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getTravelTime() {
            return travelTime;
        }

        public String getTravelTimeType() {
            return travelTimeType;
        }

        public String getIntro() {
            return intro;
        }

        public String getInfo() {
            return info;
        }

        public String getTags() {
            return tags;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public String getCollectCount() {
            return collectCount;
        }

        public String getIsHot() {
            return isHot;
        }

        public String getType() {
            return type;
        }

        public String getStatus() {
            return status;
        }

        public String getNickname() {
            return nickname;
        }

        public String getHeadImg() {
            return headImg;
        }

        public Object getCount() {
            return count;
        }

        public String getNames() {
            return names;
        }

        public String getUserSign() {
            return userSign;
        }

    }

}