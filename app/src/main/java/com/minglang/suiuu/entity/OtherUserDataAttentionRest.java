package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/5/1.
 */
public class OtherUserDataAttentionRest {

    public String attentionId;

    public String relativeId;

    public String relativeType;

    public String status;

    public String addTime;

    public String userSign;

    public String getAttentionId() {
        return attentionId;
    }

    public String getRelativeId() {
        return relativeId;
    }

    public String getRelativeType() {
        return relativeType;
    }

    public String getStatus() {
        return status;
    }

    public String getAddTime() {
        return addTime;
    }

    public String getUserSign() {
        return userSign;
    }

    @Override
    public String toString() {
        return "OtherUserDataAttentionRest{" +
                "attentionId='" + attentionId + '\'' +
                ", relativeId='" + relativeId + '\'' +
                ", relativeType='" + relativeType + '\'' +
                ", status='" + status + '\'' +
                ", addTime='" + addTime + '\'' +
                ", userSign='" + userSign + '\'' +
                '}';
    }
}
