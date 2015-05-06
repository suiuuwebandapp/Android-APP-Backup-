package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/4/27.
 */
public class AttentionUserData {

    public String nickname;

    public String headImg;

    public String intro;

    public String hobby;

    public  String userSign;

    public String getNickname() {
        return nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getIntro() {
        return intro;
    }

    public String getHobby() {
        return hobby;
    }

    public String getUserSign() {
        return userSign;
    }

    @Override
    public String toString() {
        return "AttentionUserData{" +
                "nickname='" + nickname + '\'' +
                ", headImg='" + headImg + '\'' +
                ", intro='" + intro + '\'' +
                ", hobby='" + hobby + '\'' +
                ", userSign='" + userSign + '\'' +
                '}';
    }
}
