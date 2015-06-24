package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 发布的随游实体类
 * <p/>
 * Created by Administrator on 2015/6/19.
 */
public class Published {

    /**
     * data : [{"isAirplane":"0","titleImg":"http://image.suiuu.com/suiuu_trip/57_reset.jpg","tripId":"57","lon":null,"cityId":"2984","title":"【包车】葡萄牙里斯本激情之旅","countryId":"2974","score":"0","travelTimeType":"1","tripCount":"0","intro":"畅游葡萄牙首都里斯本","createPublisherId":"36","nickname":"章吉","maxUserCount":"8","startTime":"09:00:00","lat":null,"basePrice":"2450.00","info":"沿着狭长而陡峭的道路你就一路来到了葡萄牙里斯本--最有韵味的欧洲城市之一。在老石桥下，在修道院门前，你都能感受到这座古老城市在用一砖一瓦诉说着历史的痕迹。而当夜色降临，天气慢慢凉爽，街头欢笑畅饮的人群又会让你感受到充满活力的一面。这就是里斯本，一个白天夜晚都很美的地方。\n\n如果您想去其他的地方也可以私信我说明，一定尽量满足您的要求！","travelTime":"9","headImg":"http://www.suiuu.com/assets/images/user_default.png","count":"1","tags":"家庭,自然,浪漫","names":null,"createTime":"2015-05-03 11:38:05","endTime":"18:00:00","isHotel":"0","basePriceType":"2","status":"1"}]
     * message :
     * status : 1
     * token : 8e3ebc84b3e29316d137eb0ba8ad8c3c
     */
    private List<PublishedData> data;
    private String message;
    private int status;
    private String token;

    public void setData(List<PublishedData> data) {
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

    public List<PublishedData> getData() {
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

    public class PublishedData {
        /**
         * isAirplane : 0
         * titleImg : http://image.suiuu.com/suiuu_trip/57_reset.jpg
         * tripId : 57
         * lon : null
         * cityId : 2984
         * title : 【包车】葡萄牙里斯本激情之旅
         * countryId : 2974
         * score : 0
         * travelTimeType : 1
         * tripCount : 0
         * intro : 畅游葡萄牙首都里斯本
         * createPublisherId : 36
         * nickname : 章吉
         * maxUserCount : 8
         * startTime : 09:00:00
         * lat : null
         * basePrice : 2450.00
         * info : 沿着狭长而陡峭的道路你就一路来到了葡萄牙里斯本--最有韵味的欧洲城市之一。在老石桥下，在修道院门前，你都能感受到这座古老城市在用一砖一瓦诉说着历史的痕迹。而当夜色降临，天气慢慢凉爽，街头欢笑畅饮的人群又会让你感受到充满活力的一面。这就是里斯本，一个白天夜晚都很美的地方。
         如果您想去其他的地方也可以私信我说明，一定尽量满足您的要求！
         * travelTime : 9
         * headImg : http://www.suiuu.com/assets/images/user_default.png
         * count : 1
         * tags : 家庭,自然,浪漫
         * names : null
         * createTime : 2015-05-03 11:38:05
         * endTime : 18:00:00
         * isHotel : 0
         * basePriceType : 2
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
