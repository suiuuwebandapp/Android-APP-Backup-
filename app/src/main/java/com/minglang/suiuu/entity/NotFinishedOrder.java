package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/7/27.
 * <p/>
 * 普通用户 未完成的订单实体类
 */
public class NotFinishedOrder {

    private List<NotFinishedOrderData> data;
    private String message;
    private int status;
    private String token;

    public void setData(List<NotFinishedOrderData> data) {
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

    public List<NotFinishedOrderData> getData() {
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

    public static class NotFinishedOrderData {

        private String birthday;
        private String orderNumber;
        private String orderId;
        private String totalPrice;
        private String travelCount;
        private String serviceInfo;
        private String tripId;
        private String school;
        private String intro;
        private String nickname;
        private String startTime;
        private String email;
        private String basePrice;
        private String info;
        private String profession;
        private String headImg;
        private String sex;
        private String userId;
        private String personCount;
        private String beginDate;
        private String areaCode;
        private String servicePrice;
        private String createTime;
        private String phone;
        private String userSign;
        private String isDel;
        private String tripJsonInfo;
        private String status;
        private String hobby;

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public void setTravelCount(String travelCount) {
            this.travelCount = travelCount;
        }

        public void setServiceInfo(String serviceInfo) {
            this.serviceInfo = serviceInfo;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
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

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setBasePrice(String basePrice) {
            this.basePrice = basePrice;
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

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setPersonCount(String personCount) {
            this.personCount = personCount;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public void setServicePrice(String servicePrice) {
            this.servicePrice = servicePrice;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public void setIsDel(String isDel) {
            this.isDel = isDel;
        }

        public void setTripJsonInfo(String tripJsonInfo) {
            this.tripJsonInfo = tripJsonInfo;
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

        public String getOrderNumber() {
            return orderNumber;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public String getTravelCount() {
            return travelCount;
        }

        public String getServiceInfo() {
            return serviceInfo;
        }

        public String getTripId() {
            return tripId;
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

        public String getStartTime() {
            return startTime;
        }

        public String getEmail() {
            return email;
        }

        public String getBasePrice() {
            return basePrice;
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

        public String getSex() {
            return sex;
        }

        public String getUserId() {
            return userId;
        }

        public String getPersonCount() {
            return personCount;
        }

        public String getBeginDate() {
            return beginDate;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public String getServicePrice() {
            return servicePrice;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getPhone() {
            return phone;
        }

        public String getUserSign() {
            return userSign;
        }

        public String getIsDel() {
            return isDel;
        }

        public String getTripJsonInfo() {
            return tripJsonInfo;
        }

        public String getStatus() {
            return status;
        }

        public String getHobby() {
            return hobby;
        }

    }

}