package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/5/4.
 */
public class CollectionSuiuuData {

    public String tripId;

    public String titleImg;

    public String title;

    public String intro;

    public String score;

    public String basePrice;

    public String userSign;

    public String headImg;

    public String nickname;

    public String getTripId() {
        return tripId;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public String getTitle() {
        return title;
    }

    public String getIntro() {
        return intro;
    }

    public String getScore() {
        return score;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public String getUserSign() {
        return userSign;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return "CollectionSuiuuData{" +
                "tripId='" + tripId + '\'' +
                ", titleImg='" + titleImg + '\'' +
                ", title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", score='" + score + '\'' +
                ", basePrice='" + basePrice + '\'' +
                ", userSign='" + userSign + '\'' +
                ", headImg='" + headImg + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
