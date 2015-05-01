package com.minglang.suiuu.entity;

/**
 * 粉丝数据Data部分
 * <p/>
 * Created by Administrator on 2015/5/1.
 */
public class FansData {

    /**
     * 头像地址
     */
    public String headImg;

    /**
     * 昵称
     */
    public String nickname;

    /**
     * 简介
     */
    public String intro;

    /**
     * 标识
     */
    public String userSign;

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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    @Override
    public String toString() {
        return "FansData{" +
                "headImg='" + headImg + '\'' +
                ", nickname='" + nickname + '\'' +
                ", intro='" + intro + '\'' +
                ", userSign='" + userSign + '\'' +
                '}';
    }
}
