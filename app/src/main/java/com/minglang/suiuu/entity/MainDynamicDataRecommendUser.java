package com.minglang.suiuu.entity;

/**
 * 今日之星数据
 * <p/>
 * Created by Administrator on 2015/4/29.
 */
public class MainDynamicDataRecommendUser {

    public String userSign;

    public String headImg;

    public String nickname;

    public String rImg;

    public String numb;

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getrImg() {
        return rImg;
    }

    public void setrImg(String rImg) {
        this.rImg = rImg;
    }

    public String getNumb() {
        return numb;
    }

    public void setNumb(String numb) {
        this.numb = numb;
    }

    @Override
    public String toString() {
        return "MainDynamicDataRecommendUser{" +
                "userSign='" + userSign + '\'' +
                ", headImg='" + headImg + '\'' +
                ", nickname='" + nickname + '\'' +
                ", rImg='" + rImg + '\'' +
                ", numb='" + numb + '\'' +
                '}';
    }
}
