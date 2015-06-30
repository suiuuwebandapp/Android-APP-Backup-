package com.minglang.suiuu.entity;

import java.util.List;

public class NewApply {


    /**
     * data : [{"birthday":"0000-00-00","idCard":null,"travelCount":"0","lon":null,"tripId":"105","cityId":null,"countryId":null,"score":"0","applyId":"8","publisherId":"38","tripCount":"0","school":"","intro":"","nickname":"蔡金億","lat":null,"email":"a0925905195@gmail.com","info":"","profession":"","headImg":"http://image.suiuu.com/suiuu_head/20150529144852_36504.jpg","registerTime":"2015-05-29 19:45:58","kind":null,"sex":"2","userPublisherId":"38","leadCount":"0","userId":"cb61186471cf3b949354c6f2be5c0d4b","sendTime":"2015-06-10 10:44:48","areaCode":null,"phone":null,"idCardImg":null,"lastUpdateTime":"2015-05-29 19:45:58","status":"0","hobby":""}]
     * message :
     * status : 1
     * token : 471f5ff246e9098e1c229e47637049b2
     */
    private List<NewApplyData> data;
    private String message;
    private int status;
    private String token;

    public void setData(List<NewApplyData> data) {
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

    public List<NewApplyData> getData() {
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

    public class NewApplyData {
        /**
         * birthday : 0000-00-00
         * idCard : null
         * travelCount : 0
         * lon : null
         * tripId : 105
         * cityId : null
         * countryId : null
         * score : 0
         * applyId : 8
         * publisherId : 38
         * tripCount : 0
         * school :
         * intro :
         * nickname : 蔡金億
         * lat : null
         * email : a0925905195@gmail.com
         * info :
         * profession :
         * headImg : http://image.suiuu.com/suiuu_head/20150529144852_36504.jpg
         * registerTime : 2015-05-29 19:45:58
         * kind : null
         * sex : 2
         * userPublisherId : 38
         * leadCount : 0
         * userId : cb61186471cf3b949354c6f2be5c0d4b
         * sendTime : 2015-06-10 10:44:48
         * areaCode : null
         * phone : null
         * idCardImg : null
         * lastUpdateTime : 2015-05-29 19:45:58
         * status : 0
         * hobby :
         */
        private String birthday;
        private String idCard;
        private String travelCount;
        private String lon;
        private String tripId;
        private String cityId;
        private String countryId;
        private String score;
        private String applyId;
        private String publisherId;
        private String tripCount;
        private String school;
        private String intro;
        private String nickname;
        private String lat;
        private String email;
        private String info;
        private String profession;
        private String headImg;
        private String registerTime;
        private String kind;
        private String sex;
        private String userPublisherId;
        private String leadCount;
        private String userId;
        private String sendTime;
        private String areaCode;
        private String phone;
        private String idCardImg;
        private String lastUpdateTime;
        private String status;
        private String hobby;

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public void setTravelCount(String travelCount) {
            this.travelCount = travelCount;
        }

        public void setLon(String lon) {
            this.lon = lon;
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

        public void setScore(String score) {
            this.score = score;
        }

        public void setApplyId(String applyId) {
            this.applyId = applyId;
        }

        public void setPublisherId(String publisherId) {
            this.publisherId = publisherId;
        }

        public void setTripCount(String tripCount) {
            this.tripCount = tripCount;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setUserPublisherId(String userPublisherId) {
            this.userPublisherId = userPublisherId;
        }

        public void setLeadCount(String leadCount) {
            this.leadCount = leadCount;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setIdCardImg(String idCardImg) {
            this.idCardImg = idCardImg;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getIdCard() {
            return idCard;
        }

        public String getTravelCount() {
            return travelCount;
        }

        public String getLon() {
            return lon;
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

        public String getScore() {
            return score;
        }

        public String getApplyId() {
            return applyId;
        }

        public String getPublisherId() {
            return publisherId;
        }

        public String getTripCount() {
            return tripCount;
        }

        public String getSchool() {
            return school;
        }

        public String getIntro() {
            return intro;
        }

        public String getNickname() {
            return nickname;
        }

        public String getLat() {
            return lat;
        }

        public String getEmail() {
            return email;
        }

        public String getInfo() {
            return info;
        }

        public String getProfession() {
            return profession;
        }

        public String getHeadImg() {
            return headImg;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public String getKind() {
            return kind;
        }

        public String getSex() {
            return sex;
        }

        public String getUserPublisherId() {
            return userPublisherId;
        }

        public String getLeadCount() {
            return leadCount;
        }

        public String getUserId() {
            return userId;
        }

        public String getSendTime() {
            return sendTime;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public String getPhone() {
            return phone;
        }

        public String getIdCardImg() {
            return idCardImg;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public String getStatus() {
            return status;
        }

        public String getHobby() {
            return hobby;
        }
    }

}
