package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/4/27.
 */
public class AttentionUserData {

    public String nickname;

    public String headImg;

    public String intro;

    public String hobby;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "AttentionUserData{" +
                "nickname='" + nickname + '\'' +
                ", headImg='" + headImg + '\'' +
                ", intro='" + intro + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }
}
