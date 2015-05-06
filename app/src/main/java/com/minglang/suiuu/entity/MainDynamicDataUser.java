package com.minglang.suiuu.entity;

/**
 * 关注动态数据实体类
 * <p/>
 * Created by Administrator on 2015/4/29.
 */
public class MainDynamicDataUser {

    public String aImg;

    public String aTitle;

    public String articleId;

    public String aStatus;

    public String headImg;

    public String nickname;

    public String userSign;

    public String aContent;

    public String getaImg() {
        return aImg;
    }

    public String getaTitle() {
        return aTitle;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getaStatus() {
        return aStatus;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUserSign() {
        return userSign;
    }

    public String getaContent() {
        return aContent;
    }

    @Override
    public String toString() {
        return "MainDynamicDataUserDynamic{" +
                "aImg='" + aImg + '\'' +
                ", aTitle='" + aTitle + '\'' +
                ", articleId='" + articleId + '\'' +
                ", aStatus='" + aStatus + '\'' +
                ", headImg='" + headImg + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userSign='" + userSign + '\'' +
                ", aContent='" + aContent + '\'' +
                '}';
    }
}
