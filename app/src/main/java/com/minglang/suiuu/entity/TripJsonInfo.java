package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/7/28.
 *
 * 我的订单-TripJsonInfo数据
 *
 */
public class TripJsonInfo {

    /**
     * createPublisherInfo : {"birthday":"1991-02-14","profession":"","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","travelCount":"0","tripPublisherId":"119","sex":"0","tripId":"104","cityId":"342","countryId":"336","areaCode":"+86","publisherId":"11","cityName":"朝阳","phone":"15311445352","school":"","userSign":"a4c1406ff4cc382389f19bf6ec3e55c1","intro":"一座城市，一些岁月，和一个人。","nickname":"277*****@qq.com","countryName":"中国","email":"277646935@qq.com","hobby":"","info":"来自西南的小姑娘"}
     * picList : [{"tripId":"104","title":null,"picId":"1980","url":"http://image.suiuu.com/user_card/20150513113650_87996.jpg"},{"tripId":"104","title":null,"picId":"1981","url":"http://image.suiuu.com/user_card/20150513113647_92382.jpg"},{"tripId":"104","title":null,"picId":"1982","url":"http://image.suiuu.com/user_card/20150513112125_67834.jpg"},{"tripId":"104","title":null,"picId":"1983","url":"http://image.suiuu.com/user_card/20150513112144_73623.jpg"}]
     * serviceList : [{"money":"1200.00","tripId":"104","serviceId":"441","title":"包车","type":"0"},{"money":"1200.00","tripId":"104","serviceId":"442","title":"东京成田空港接机","type":"0"},{"money":"800.00","tripId":"104","serviceId":"443","title":"东京羽田空港","type":"0"}]
     * includeDetailList : [{"name":"地陪导游","detailId":"99","tripId":"104","type":"1"},{"name":"陪同翻译","detailId":"100","tripId":"104","type":"1"},{"name":"包车费用","detailId":"101","tripId":"104","type":"1"}]
     * attention : [{"addTime":"2015-06-23 18:05:23","userSign":"085963dc0af031709b032725e3ef18f5","relativeId":"41","attentionId":"174","relativeType":"6","status":"1"}]
     * publisherList : [{"birthday":"1991-02-14","profession":"","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","travelCount":"0","tripPublisherId":"119","sex":"0","tripId":"104","cityId":"342","countryId":"336","areaCode":"+86","publisherId":"11","cityName":"朝阳","phone":"15311445352","school":"","userSign":"a4c1406ff4cc382389f19bf6ec3e55c1","intro":"一座城市，一些岁月，和一个人。","nickname":"277*****@qq.com","countryName":"中国","email":"277646935@qq.com","hobby":"","info":"来自西南的小姑娘"}]
     * scenicList : [{"scenicId":"1053","name":"东京","tripId":"104","lon":"139.6917064","lat":"35.6894875"},{"scenicId":"1054","name":"富士山","tripId":"104","lon":"138.795814","lat":"35.483528"}]
     * praise : [{"addTime":"2015-06-23 18:05:23","userSign":"085963dc0af031709b032725e3ef18f5","relativeId":"41","attentionId":"174","relativeType":"6","status":"1"}]
     * highlightList : [{"tripId":"104","value":"免费导游哦~","hlId":"27"}]
     * info : {"cityCname":"东京","isAirplane":"0","titleImg":"http://image.suiuu.com/suiuu_trip/104_reset.jpg","tripId":"104","lon":null,"cityId":"3018","title":"东京文化之旅","countryId":"3005","score":"0","travelTimeType":"1","tripCount":"0","intro":"体验纯正的日本文化精神","createPublisherId":"11","maxUserCount":"6","startTime":"09:00:00","cityEname":"Tokyo","lat":null,"basePrice":"800.00","info":"文化一日游，有内涵、讲深度、有趣味。从庄严地天皇居所，到人文圣地东大，夜晚领略歌舞伎町的歌舞升平，体验菊与刀的纯正日本精神。","travelTime":"10","countryEname":"Japan","tags":"家庭,美食,猎奇,自然,浪漫,购物","createTime":"2015-05-13 11:41:49","countryCname":"日本","endTime":"19:00:00","isHotel":"0","basePriceType":"1","status":"1"}
     * priceList : [{"price":"250.00","tripId":"101","minCount":"1","priceId":"203","maxCount":"4"}]
     * unIncludeDetailList : [{"name":".餐宿：需包导游餐食费用或补贴100元/餐；若旅程较长需要住宿时包住宿费","detailId":"102","tripId":"104","type":"2"},{"name":"接/送机（包车时间内免费）","detailId":"103","tripId":"104","type":"2"}]
     */
    private CreatePublisherInfoEntity createPublisherInfo;
    private List<PicListEntity> picList;
    private List<ServiceListEntity> serviceList;
    private List<IncludeDetailListEntity> includeDetailList;
    private List<AttentionEntity> attention;
    private List<PublisherListEntity> publisherList;
    private List<ScenicListEntity> scenicList;
    private List<PraiseEntity> praise;
    private List<HighlightListEntity> highlightList;
    private InfoEntity info;
    private List<PriceListEntity> priceList;
    private List<UnIncludeDetailListEntity> unIncludeDetailList;

    public void setCreatePublisherInfo(CreatePublisherInfoEntity createPublisherInfo) {
        this.createPublisherInfo = createPublisherInfo;
    }

    public void setPicList(List<PicListEntity> picList) {
        this.picList = picList;
    }

    public void setServiceList(List<ServiceListEntity> serviceList) {
        this.serviceList = serviceList;
    }

    public void setIncludeDetailList(List<IncludeDetailListEntity> includeDetailList) {
        this.includeDetailList = includeDetailList;
    }

    public void setAttention(List<AttentionEntity> attention) {
        this.attention = attention;
    }

    public void setPublisherList(List<PublisherListEntity> publisherList) {
        this.publisherList = publisherList;
    }

    public void setScenicList(List<ScenicListEntity> scenicList) {
        this.scenicList = scenicList;
    }

    public void setPraise(List<PraiseEntity> praise) {
        this.praise = praise;
    }

    public void setHighlightList(List<HighlightListEntity> highlightList) {
        this.highlightList = highlightList;
    }

    public void setInfo(InfoEntity info) {
        this.info = info;
    }

    public void setPriceList(List<PriceListEntity> priceList) {
        this.priceList = priceList;
    }

    public void setUnIncludeDetailList(List<UnIncludeDetailListEntity> unIncludeDetailList) {
        this.unIncludeDetailList = unIncludeDetailList;
    }

    public CreatePublisherInfoEntity getCreatePublisherInfo() {
        return createPublisherInfo;
    }

    public List<PicListEntity> getPicList() {
        return picList;
    }

    public List<ServiceListEntity> getServiceList() {
        return serviceList;
    }

    public List<IncludeDetailListEntity> getIncludeDetailList() {
        return includeDetailList;
    }

    public List<AttentionEntity> getAttention() {
        return attention;
    }

    public List<PublisherListEntity> getPublisherList() {
        return publisherList;
    }

    public List<ScenicListEntity> getScenicList() {
        return scenicList;
    }

    public List<PraiseEntity> getPraise() {
        return praise;
    }

    public List<HighlightListEntity> getHighlightList() {
        return highlightList;
    }

    public InfoEntity getInfo() {
        return info;
    }

    public List<PriceListEntity> getPriceList() {
        return priceList;
    }

    public List<UnIncludeDetailListEntity> getUnIncludeDetailList() {
        return unIncludeDetailList;
    }

    public static class CreatePublisherInfoEntity {
        /**
         * birthday : 1991-02-14
         * profession :
         * headImg : http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg
         * travelCount : 0
         * tripPublisherId : 119
         * sex : 0
         * tripId : 104
         * cityId : 342
         * countryId : 336
         * areaCode : +86
         * publisherId : 11
         * cityName : 朝阳
         * phone : 15311445352
         * school :
         * userSign : a4c1406ff4cc382389f19bf6ec3e55c1
         * intro : 一座城市，一些岁月，和一个人。
         * nickname : 277*****@qq.com
         * countryName : 中国
         * email : 277646935@qq.com
         * hobby :
         * info : 来自西南的小姑娘
         */
        private String birthday;
        private String profession;
        private String headImg;
        private String travelCount;
        private String tripPublisherId;
        private String sex;
        private String tripId;
        private String cityId;
        private String countryId;
        private String areaCode;
        private String publisherId;
        private String cityName;
        private String phone;
        private String school;
        private String userSign;
        private String intro;
        private String nickname;
        private String countryName;
        private String email;
        private String hobby;
        private String info;

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public void setTravelCount(String travelCount) {
            this.travelCount = travelCount;
        }

        public void setTripPublisherId(String tripPublisherId) {
            this.tripPublisherId = tripPublisherId;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public void setPublisherId(String publisherId) {
            this.publisherId = publisherId;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getProfession() {
            return profession;
        }

        public String getHeadImg() {
            return headImg;
        }

        public String getTravelCount() {
            return travelCount;
        }

        public String getTripPublisherId() {
            return tripPublisherId;
        }

        public String getSex() {
            return sex;
        }

        public String getTripId() {
            return tripId;
        }

        public String getCityId() {
            return cityId;
        }

        public String getCountryId() {
            return countryId;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public String getPublisherId() {
            return publisherId;
        }

        public String getCityName() {
            return cityName;
        }

        public String getPhone() {
            return phone;
        }

        public String getSchool() {
            return school;
        }

        public String getUserSign() {
            return userSign;
        }

        public String getIntro() {
            return intro;
        }

        public String getNickname() {
            return nickname;
        }

        public String getCountryName() {
            return countryName;
        }

        public String getEmail() {
            return email;
        }

        public String getHobby() {
            return hobby;
        }

        public String getInfo() {
            return info;
        }
    }

    public static class PicListEntity {
        /**
         * tripId : 104
         * title : null
         * picId : 1980
         * url : http://image.suiuu.com/user_card/20150513113650_87996.jpg
         */
        private String tripId;
        private String title;
        private String picId;
        private String url;

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setPicId(String picId) {
            this.picId = picId;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTripId() {
            return tripId;
        }

        public String getTitle() {
            return title;
        }

        public String getPicId() {
            return picId;
        }

        public String getUrl() {
            return url;
        }
    }

    public static class ServiceListEntity {
        /**
         * money : 1200.00
         * tripId : 104
         * serviceId : 441
         * title : 包车
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

    public static class IncludeDetailListEntity {
        /**
         * name : 地陪导游
         * detailId : 99
         * tripId : 104
         * type : 1
         */
        private String name;
        private String detailId;
        private String tripId;
        private String type;

        public void setName(String name) {
            this.name = name;
        }

        public void setDetailId(String detailId) {
            this.detailId = detailId;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getDetailId() {
            return detailId;
        }

        public String getTripId() {
            return tripId;
        }

        public String getType() {
            return type;
        }
    }

    public static class AttentionEntity {
        /**
         * addTime : 2015-06-23 18:05:23
         * userSign : 085963dc0af031709b032725e3ef18f5
         * relativeId : 41
         * attentionId : 174
         * relativeType : 6
         * status : 1
         */
        private String addTime;
        private String userSign;
        private String relativeId;
        private String attentionId;
        private String relativeType;
        private String status;

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public void setRelativeId(String relativeId) {
            this.relativeId = relativeId;
        }

        public void setAttentionId(String attentionId) {
            this.attentionId = attentionId;
        }

        public void setRelativeType(String relativeType) {
            this.relativeType = relativeType;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddTime() {
            return addTime;
        }

        public String getUserSign() {
            return userSign;
        }

        public String getRelativeId() {
            return relativeId;
        }

        public String getAttentionId() {
            return attentionId;
        }

        public String getRelativeType() {
            return relativeType;
        }

        public String getStatus() {
            return status;
        }
    }

    public static class PublisherListEntity {
        /**
         * birthday : 1991-02-14
         * profession :
         * headImg : http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg
         * travelCount : 0
         * tripPublisherId : 119
         * sex : 0
         * tripId : 104
         * cityId : 342
         * countryId : 336
         * areaCode : +86
         * publisherId : 11
         * cityName : 朝阳
         * phone : 15311445352
         * school :
         * userSign : a4c1406ff4cc382389f19bf6ec3e55c1
         * intro : 一座城市，一些岁月，和一个人。
         * nickname : 277*****@qq.com
         * countryName : 中国
         * email : 277646935@qq.com
         * hobby :
         * info : 来自西南的小姑娘
         */
        private String birthday;
        private String profession;
        private String headImg;
        private String travelCount;
        private String tripPublisherId;
        private String sex;
        private String tripId;
        private String cityId;
        private String countryId;
        private String areaCode;
        private String publisherId;
        private String cityName;
        private String phone;
        private String school;
        private String userSign;
        private String intro;
        private String nickname;
        private String countryName;
        private String email;
        private String hobby;
        private String info;

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public void setTravelCount(String travelCount) {
            this.travelCount = travelCount;
        }

        public void setTripPublisherId(String tripPublisherId) {
            this.tripPublisherId = tripPublisherId;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public void setPublisherId(String publisherId) {
            this.publisherId = publisherId;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getProfession() {
            return profession;
        }

        public String getHeadImg() {
            return headImg;
        }

        public String getTravelCount() {
            return travelCount;
        }

        public String getTripPublisherId() {
            return tripPublisherId;
        }

        public String getSex() {
            return sex;
        }

        public String getTripId() {
            return tripId;
        }

        public String getCityId() {
            return cityId;
        }

        public String getCountryId() {
            return countryId;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public String getPublisherId() {
            return publisherId;
        }

        public String getCityName() {
            return cityName;
        }

        public String getPhone() {
            return phone;
        }

        public String getSchool() {
            return school;
        }

        public String getUserSign() {
            return userSign;
        }

        public String getIntro() {
            return intro;
        }

        public String getNickname() {
            return nickname;
        }

        public String getCountryName() {
            return countryName;
        }

        public String getEmail() {
            return email;
        }

        public String getHobby() {
            return hobby;
        }

        public String getInfo() {
            return info;
        }
    }

    public static class ScenicListEntity {
        /**
         * scenicId : 1053
         * name : 东京
         * tripId : 104
         * lon : 139.6917064
         * lat : 35.6894875
         */
        private String scenicId;
        private String name;
        private String tripId;
        private String lon;
        private String lat;

        public void setScenicId(String scenicId) {
            this.scenicId = scenicId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getScenicId() {
            return scenicId;
        }

        public String getName() {
            return name;
        }

        public String getTripId() {
            return tripId;
        }

        public String getLon() {
            return lon;
        }

        public String getLat() {
            return lat;
        }
    }

    public static class PraiseEntity {
        /**
         * addTime : 2015-06-23 18:05:23
         * userSign : 085963dc0af031709b032725e3ef18f5
         * relativeId : 41
         * attentionId : 174
         * relativeType : 6
         * status : 1
         */
        private String addTime;
        private String userSign;
        private String relativeId;
        private String attentionId;
        private String relativeType;
        private String status;

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public void setRelativeId(String relativeId) {
            this.relativeId = relativeId;
        }

        public void setAttentionId(String attentionId) {
            this.attentionId = attentionId;
        }

        public void setRelativeType(String relativeType) {
            this.relativeType = relativeType;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddTime() {
            return addTime;
        }

        public String getUserSign() {
            return userSign;
        }

        public String getRelativeId() {
            return relativeId;
        }

        public String getAttentionId() {
            return attentionId;
        }

        public String getRelativeType() {
            return relativeType;
        }

        public String getStatus() {
            return status;
        }
    }

    public static class HighlightListEntity {
        /**
         * tripId : 104
         * value : 免费导游哦~
         * hlId : 27
         */
        private String tripId;
        private String value;
        private String hlId;

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void setHlId(String hlId) {
            this.hlId = hlId;
        }

        public String getTripId() {
            return tripId;
        }

        public String getValue() {
            return value;
        }

        public String getHlId() {
            return hlId;
        }
    }

    public static class InfoEntity {
        /**
         * cityCname : 东京
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
         * maxUserCount : 6
         * startTime : 09:00:00
         * cityEname : Tokyo
         * lat : null
         * basePrice : 800.00
         * info : 文化一日游，有内涵、讲深度、有趣味。从庄严地天皇居所，到人文圣地东大，夜晚领略歌舞伎町的歌舞升平，体验菊与刀的纯正日本精神。
         * travelTime : 10
         * countryEname : Japan
         * tags : 家庭,美食,猎奇,自然,浪漫,购物
         * createTime : 2015-05-13 11:41:49
         * countryCname : 日本
         * endTime : 19:00:00
         * isHotel : 0
         * basePriceType : 1
         * status : 1
         */
        private String cityCname;
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
        private String maxUserCount;
        private String startTime;
        private String cityEname;
        private String lat;
        private String basePrice;
        private String info;
        private String travelTime;
        private String countryEname;
        private String tags;
        private String createTime;
        private String countryCname;
        private String endTime;
        private String isHotel;
        private String basePriceType;
        private String status;

        public void setCityCname(String cityCname) {
            this.cityCname = cityCname;
        }

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

        public void setMaxUserCount(String maxUserCount) {
            this.maxUserCount = maxUserCount;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public void setCityEname(String cityEname) {
            this.cityEname = cityEname;
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

        public void setCountryEname(String countryEname) {
            this.countryEname = countryEname;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setCountryCname(String countryCname) {
            this.countryCname = countryCname;
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

        public String getCityCname() {
            return cityCname;
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

        public String getMaxUserCount() {
            return maxUserCount;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getCityEname() {
            return cityEname;
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

        public String getCountryEname() {
            return countryEname;
        }

        public String getTags() {
            return tags;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getCountryCname() {
            return countryCname;
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

    public static class PriceListEntity {
        /**
         * price : 250.00
         * tripId : 101
         * minCount : 1
         * priceId : 203
         * maxCount : 4
         */
        private String price;
        private String tripId;
        private String minCount;
        private String priceId;
        private String maxCount;

        public void setPrice(String price) {
            this.price = price;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public void setMinCount(String minCount) {
            this.minCount = minCount;
        }

        public void setPriceId(String priceId) {
            this.priceId = priceId;
        }

        public void setMaxCount(String maxCount) {
            this.maxCount = maxCount;
        }

        public String getPrice() {
            return price;
        }

        public String getTripId() {
            return tripId;
        }

        public String getMinCount() {
            return minCount;
        }

        public String getPriceId() {
            return priceId;
        }

        public String getMaxCount() {
            return maxCount;
        }
    }

    public static class UnIncludeDetailListEntity {
        /**
         * name : .餐宿：需包导游餐食费用或补贴100元/餐；若旅程较长需要住宿时包住宿费
         * detailId : 102
         * tripId : 104
         * type : 2
         */
        private String name;
        private String detailId;
        private String tripId;
        private String type;

        public void setName(String name) {
            this.name = name;
        }

        public void setDetailId(String detailId) {
            this.detailId = detailId;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getDetailId() {
            return detailId;
        }

        public String getTripId() {
            return tripId;
        }

        public String getType() {
            return type;
        }
    }

}