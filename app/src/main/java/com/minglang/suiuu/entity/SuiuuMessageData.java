package com.minglang.suiuu.entity;

public class SuiuuMessageData {

    public String relativeId;

    public String relativeType;

    public String remindId;

    public String headImg;

    public String nickname;

    public String createUserSign;

    public String getRelativeId() {
        return relativeId;
    }

    public String rType;

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

    public String getCreateUserSign() {
        return createUserSign;
    }

    public String getRtype() {
        return rType;
    }

    @Override
    public String toString() {
        return "SuiuuMessageData{" +
                "relativeId='" + relativeId + '\'' +
                ", relativeType='" + relativeType + '\'' +
                ", remindId='" + remindId + '\'' +
                ", headImg='" + headImg + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createUserSign='" + createUserSign + '\'' +
                ", rType='" + rType + '\'' +
                '}';
    }
}
