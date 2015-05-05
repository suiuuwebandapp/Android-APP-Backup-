package com.minglang.suiuu.entity;

/**
 * 项目名称：Suiuu
 * 类描述：随游详细信息中关注的信息
 * 创建人：Administrator
 * 创建时间：2015/5/4 19:34
 * 修改人：Administrator
 * 修改时间：2015/5/4 19:34
 * 修改备注：
 */
public class SuiuuDetailForAttention {
    private String attentionId;
    private String relativeId;
    private String relativeType;
    private String status;
    private String addTime;
    private String userSign;

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
        return "SuiuuDataAttention{" +
                "attentionId='" + attentionId + '\'' +
                ", relativeId='" + relativeId + '\'' +
                ", relativeType='" + relativeType + '\'' +
                ", status='" + status + '\'' +
                ", addTime='" + addTime + '\'' +
                ", userSign='" + userSign + '\'' +
                '}';
    }
}
