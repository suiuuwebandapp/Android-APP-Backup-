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

    public String isPublisher;

    public String countryId;

    public String cityId;

    public String lon;

    public String lat;

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
                ", isPublisher='" + isPublisher + '\'' +
                ", countryId='" + countryId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
