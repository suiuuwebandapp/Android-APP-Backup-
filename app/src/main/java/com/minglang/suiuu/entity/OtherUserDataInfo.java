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

    public String getNickname() {
        return nickname;
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

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public String getUserSign() {
        return userSign;
    }

    public String getIsPublisher() {
        return isPublisher;
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
