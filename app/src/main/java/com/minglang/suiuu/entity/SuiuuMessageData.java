package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/5/3.
 */
public class SuiuuMessageData {

    public String relativeId;

    public String relativeType;

    public String remindId;

    public String headImg;

    public String nickname;

    public String getRelativeId() {
        return relativeId;
    }

    public String getRelativeType() {
        return relativeType;
    }

    public String getRemindId() {
        return remindId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return "SuiuuMessageData{" +
                "relativeId='" + relativeId + '\'' +
                ", relativeType='" + relativeType + '\'' +
                ", remindId='" + remindId + '\'' +
                ", headImg='" + headImg + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
