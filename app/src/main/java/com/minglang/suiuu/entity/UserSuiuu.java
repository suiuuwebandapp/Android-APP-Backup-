package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/7/31.
 *
 * 用户的随游数据实体类
 */
public class UserSuiuu {

    /**
     * data : [{"isAirplane":"0","titleImg":"http://image.suiuu.com/suiuu_trip/104_reset.jpg","tripId":"104","lon":null,"cityId":"3018","title":"东京文化之旅","countryId":"3005","score":"0","travelTimeType":"1","tripCount":"0","intro":"体验纯正的日本文化精神","createPublisherId":"11","nickname":"277*****@qq.com","maxUserCount":"6","startTime":"09:00:00","lat":null,"basePrice":"800.00","info":"文化一日游，有内涵、讲深度、有趣味。从庄严地天皇居所，到人文圣地东大，夜晚领略歌舞伎町的歌舞升平，体验菊与刀的纯正日本精神。","travelTime":"10","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","count":null,"tags":"家庭,美食,猎奇,自然,浪漫,购物","names":"包车,东京成田空港接机,东京羽田空港","createTime":"2015-05-13 11:41:49","endTime":"19:00:00","isHotel":"0","basePriceType":"1","status":"1"}]
     * message :
     * status : 1
     * token : 43283c41262ed097515375f2d55ef424
     */
    private List<UserSuiuuData> data;
    private String message;
    private int status;
    private String token;

    public void setData(List<UserSuiuuData> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<UserSuiuuData> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public static class UserSuiuuData {
        /**
         * isAirplane : 0
         * titleImg : http://image.suiuu.com/suiuu_trip/104_reset.jpg
         * tripId : 104
         * lon : null
         * cityId : 3018
         * title : 东京文化之旅
         * countryId : 3005
         * score : 0
         * travelTimeType : 1
         * tripCount : 0
         * intro : 体验纯正的日本文化精神
         * createPublisherId : 11
         * nickname : 277*****@qq.com
         * maxUserCount : 6
         * startTime : 09:00:00
         * lat : null
         * basePrice : 800.00
         * info : 文化一日游，有内涵、讲深度、有趣味。从庄严地天皇居所，到人文圣地东大，夜晚领略歌舞伎町的歌舞升平，体验菊与刀的纯正日本精神。
         * travelTime : 10
         * headImg : http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg
         * count : null
         * tags : 家庭,美食,猎奇,自然,浪漫,购物
         * names : 包车,东京成田空港接机,东京羽田空港
         * createTime : 2015-05-13 11:41:49
         * endTime : 19:00:00
         * isHotel : 0
         * basePriceType : 1
         * status : 1
         */
        private String isAirplane;
        private String titleImg;
        private String tripId;
        private String lon;
        private String cityId;
        private String title;
        private String countryId;
        private String score;
        private String travelTimeType;
        private String tripCount;
        private String intro;
        private String createPublisherId;
        private String nickname;
        private String maxUserCount;
        private String startTime;
        private String lat;
        private String basePrice;
        private String info;
        private String travelTime;
        private String headImg;
        private String count;
        private String tags;
        private String names;
        private String createTime;
        private String endTime;
        private String isHotel;
        private String basePriceType;
        private String status;

        public void setIsAirplane(String isAirplane) {
            this.isAirplane = isAirplane;
        }

        public void setTitleImg(String titleImg) {
            this.titleImg = titleImg;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setTravelTimeType(String travelTimeType) {
            this.travelTimeType = travelTimeType;
        }

        public void setTripCount(String tripCount) {
            this.tripCount = tripCount;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public void setCreatePublisherId(String createPublisherId) {
            this.createPublisherId = createPublisherId;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setMaxUserCount(String maxUserCount) {
            this.maxUserCount = maxUserCount;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public void setBasePrice(String basePrice) {
            this.basePrice = basePrice;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public void setTravelTime(String travelTime) {
            this.travelTime = travelTime;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public void setNames(String names) {
            this.names = names;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public void setIsHotel(String isHotel) {
            this.isHotel = isHotel;
        }

        public void setBasePriceType(String basePriceType) {
            this.basePriceType = basePriceType;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsAirplane() {
            return isAirplane;
        }

        public String getTitleImg() {
            return titleImg;
        }

        public String getTripId() {
            return tripId;
        }

        public String getLon() {
            return lon;
        }

        public String getCityId() {
            return cityId;
        }

        public String getTitle() {
            return title;
        }

        public String getCountryId() {
            return countryId;
        }

        public String getScore() {
            return score;
        }

        public String getTravelTimeType() {
            return travelTimeType;
        }

        public String getTripCount() {
            return tripCount;
        }

        public String getIntro() {
            return intro;
        }

        public String getCreatePublisherId() {
            return createPublisherId;
        }

        public String getNickname() {
            return nickname;
        }

        public String getMaxUserCount() {
            return maxUserCount;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getLat() {
            return lat;
        }

        public String getBasePrice() {
            return basePrice;
        }

        public String getInfo() {
            return info;
        }

        public String getTravelTime() {
            return travelTime;
        }

        public String getHeadImg() {
            return headImg;
        }

        public String getCount() {
            return count;
        }

        public String getTags() {
            return tags;
        }

        public String getNames() {
            return names;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getIsHotel() {
            return isHotel;
        }

        public String getBasePriceType() {
            return basePriceType;
        }

        public String getStatus() {
            return status;
        }

    }
}