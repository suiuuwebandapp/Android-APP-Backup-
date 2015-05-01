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

    public void setAttentionId(String attentionId) {
        this.attentionId = attentionId;
    }

    public String getRelativeId() {
        return relativeId;
    }

    public void setRelativeId(String relativeId) {
        this.relativeId = relativeId;
    }

    public String getRelativeType() {
        return relativeType;
    }

    public void setRelativeType(String relativeType) {
        this.relativeType = relativeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
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
