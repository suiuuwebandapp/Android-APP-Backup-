package com.minglang.suiuu.entity;

import java.io.Serializable;

/**
 * 登陆返回数据实体类
 * <p/>
 * Created by Administrator on 2015/4/25.
 */
public class UserBack implements Serializable {

    private static final long serialVersionUID = -150698419334277565L;

    private String status;

    private UserBackData data;

    private String message;

    private String token;

    public String getStatus() {
        return status;
    }

    public UserBackData getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "UserBack{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public class UserBackData {

        private String userId;

        private String nickname;

        private String surname;

        private String name;

        private String password;

        private String email;

        private String phone;

        private String areaCode;

        private String sex;

        private String birthday;

        private String headImg;

        private String hobby;

        private String profession;

        private String school;

        private String qq;

        private String wechat;

        private String intro;

        private String info;

        private String travelCount;

        private String registerIp;

        private String registerTime;

        private String lastLoginIp;

        private String lastLoginTime;

        private String userSign;

        private String status;

        private String isPublisher;

        private String countryId;

        private String cityId;

        private String lon;

        private String lat;

        private String balance;

        private String version;

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public void setTravelCount(String travelCount) {
            this.travelCount = travelCount;
        }

        public void setRegisterIp(String registerIp) {
            this.registerIp = registerIp;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public void setLastLoginIp(String lastLoginIp) {
            this.lastLoginIp = lastLoginIp;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setIsPublisher(String isPublisher) {
            this.isPublisher = isPublisher;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUserId() {
            return userId;
        }

        public String getNickname() {
            return nickname;
        }

        public String getSurname() {
            return surname;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public String getSex() {
            return sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getHeadImg() {
            return headImg;
        }

        public String getHobby() {
            return hobby;
        }

        public String getProfession() {
            return profession;
        }

        public String getSchool() {
            return school;
        }

        public String getQq() {
            return qq;
        }

        public String getWechat() {
            return wechat;
        }

        public String getIntro() {
            return intro;
        }

        public String getInfo() {
            return info;
        }

        public String getTravelCount() {
            return travelCount;
        }

        public String getRegisterIp() {
            return registerIp;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public String getLastLoginIp() {
            return lastLoginIp;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public String getUserSign() {
            return userSign;
        }

        public String getStatus() {
            return status;
        }

        public String getIsPublisher() {
            return isPublisher;
        }

        public String getCountryId() {
            return countryId;
        }

        public String getCityId() {
            return cityId;
        }

        public String getLon() {
            return lon;
        }

        public String getLat() {
            return lat;
        }

        public String getBalance() {
            return balance;
        }

        public String getVersion() {
            return version;
        }

        @Override
        public String toString() {
            return "UserBackData{" +
                    "userId='" + userId + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", password='" + password + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    ", areaCode='" + areaCode + '\'' +
                    ", sex='" + sex + '\'' +
                    ", birthday='" + birthday + '\'' +
                    ", headImg='" + headImg + '\'' +
                    ", hobby='" + hobby + '\'' +
                    ", profession='" + profession + '\'' +
                    ", school='" + school + '\'' +
                    ", intro='" + intro + '\'' +
                    ", info='" + info + '\'' +
                    ", travelCount='" + travelCount + '\'' +
                    ", registerIp='" + registerIp + '\'' +
                    ", registerTime='" + registerTime + '\'' +
                    ", lastLoginIp='" + lastLoginIp + '\'' +
                    ", lastLoginTime='" + lastLoginTime + '\'' +
                    ", userSign='" + userSign + '\'' +
                    ", status='" + status + '\'' +
                    ", isPublisher='" + isPublisher + '\'' +
                    ", countryId='" + countryId + '\'' +
                    ", cityId='" + cityId + '\'' +
                    ", lon='" + lon + '\'' +
                    ", lat='" + lat + '\'' +
                    ", balance='" + balance + '\'' +
                    ", version='" + version + '\'' +
                    '}';
        }
    }

}