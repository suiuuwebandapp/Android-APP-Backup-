package com.minglang.suiuu.entity;

/**
 * 第三方登陆信息返回数据实体类
 * <p/>
 * Created by Administrator on 2015/4/25.
 */
public class UserBackData {

    public String userId;

    public String nickname;

    public String password;

    public String email;

    public String phone;

    public String areaCode;

    public String sex;

    public String birthday;

    public String headImg;

    public String hobby;

    public String profession;

    public String school;

    public String intro;

    public String info;

    public String travelCount;

    public String registerIp;

    public String registerTime;

    public String lastLoginIp;

    public String lastLoginTime;

    public String userSign;

    public String status;

    public String isPublisher;

    public String countryId;

    public String cityId;

    public String lon;

    public String lat;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
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

    @Override
    public String toString() {
        return "UserBackData{" +
                "lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", cityId='" + cityId + '\'' +
                ", countryId='" + countryId + '\'' +
                ", isPublisher='" + isPublisher + '\'' +
                ", status='" + status + '\'' +
                ", userSign='" + userSign + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", registerIp='" + registerIp + '\'' +
                ", travelCount='" + travelCount + '\'' +
                ", info='" + info + '\'' +
                ", intro='" + intro + '\'' +
                ", school='" + school + '\'' +
                ", profession='" + profession + '\'' +
                ", hobby='" + hobby + '\'' +
                ", headImg='" + headImg + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
