package com.minglang.suiuu.entity;

/**
 * 项目名称：Suiuu
 * 类描述：随游详细信息中publisherList的实体
 * 创建人：Administrator
 * 创建时间：2015/5/5 9:51
 * 修改人：Administrator
 * 修改时间：2015/5/5 9:51
 * 修改备注：
 */
public class SuiuuDetailForPublisherList {
    private String nickname;
    private String phone;
    private String areaCode;
    private String email;
    private String sex;
    private String birthday;
    private String headImg;
    private String hobby;
    private String profession;
    private String school;
    private String intro;
    private String info;
    private String travelCount;
    private String tripPublisherId;
    private String tripId;
    private String publisherId;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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

    public String getTripPublisherId() {
        return tripPublisherId;
    }

    public void setTripPublisherId(String tripPublisherId) {
        this.tripPublisherId = tripPublisherId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    @Override
    public String toString() {
        return "SuiuuDetailForPublisherList{" +
                "nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", headImg='" + headImg + '\'' +
                ", hobby='" + hobby + '\'' +
                ", profession='" + profession + '\'' +
                ", school='" + school + '\'' +
                ", intro='" + intro + '\'' +
                ", info='" + info + '\'' +
                ", travelCount='" + travelCount + '\'' +
                ", tripPublisherId='" + tripPublisherId + '\'' +
                ", tripId='" + tripId + '\'' +
                ", publisherId='" + publisherId + '\'' +
                '}';
    }
}
