package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/5/1.
 */
public class OtherUserDataInfo {

    public String userId;

    public String nickname;

    public String email;

    public String phone;

    public String areaCode;

    public String sex;

    public String birthday;

    public String headImg;

    public String hobby;

    public String school;

    public String intro;

    public String info;

    public String travelCount;

    public String registerIp;

    public String registerTime;

    public String lastLoginTime;

    public String userSign;

    public String isPublisher;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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

    public String getTravelCount() {
        return travelCount;
    }

    public void setTravelCount(String travelCount) {
        this.travelCount = travelCount;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public String getIsPublisher() {
        return isPublisher;
    }

    public void setIsPublisher(String isPublisher) {
        this.isPublisher = isPublisher;
    }

    @Override
    public String toString() {
        return "OtherUserDataInfo{" +
                "userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", headImg='" + headImg + '\'' +
                ", hobby='" + hobby + '\'' +
                ", school='" + school + '\'' +
                ", intro='" + intro + '\'' +
                ", info='" + info + '\'' +
                ", travelCount='" + travelCount + '\'' +
                ", registerIp='" + registerIp + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", userSign='" + userSign + '\'' +
                ", isPublisher='" + isPublisher + '\'' +
                '}';
    }
}
