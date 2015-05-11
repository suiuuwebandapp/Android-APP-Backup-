package com.minglang.suiuu.entity;

/**
 * 项目名称：Suiuu
 * 类描述：个人主页中随游列表的实体
 * 创建人：Administrator
 * 创建时间：2015/5/7 12:48
 * 修改人：Administrator
 * 修改时间：2015/5/7 12:48
 * 修改备注：
 */
public class TravelList {
    private String tripId;
    private String score;
    private String nickname;
    private String headImg;
    private String title;
    private String titleImg;

    public String getTripId() {
        return tripId;
    }

    public String getScore() {
        return score;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleImg() {
        return titleImg;
    }

    @Override
    public String toString() {
        return "TravelList{" +
                "tripId='" + tripId + '\'' +
                ", score='" + score + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headImg='" + headImg + '\'' +
                ", title='" + title + '\'' +
                ", titleImg='" + titleImg + '\'' +
                '}';
    }

}
