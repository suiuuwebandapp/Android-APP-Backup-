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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                '}';
    }
}
