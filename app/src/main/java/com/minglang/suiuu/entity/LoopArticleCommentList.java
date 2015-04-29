package com.minglang.suiuu.entity;

/**
 * 圈子文章评论部分数据实体类
 * <p/>
 * Created by Administrator on 2015/4/25.
 */
public class LoopArticleCommentList {

    public String commentId;

    public String userSign;

    public String content;

    public String relativeCommentId;

    public String supportCount;

    public String opposeCount;

    public String cTime;

    public String cStatus;

    public String cLastTime;

    public String articleId;

    public String nickname;

    public String headImg;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRelativeCommentId() {
        return relativeCommentId;
    }

    public void setRelativeCommentId(String relativeCommentId) {
        this.relativeCommentId = relativeCommentId;
    }

    public String getSupportCount() {
        return supportCount;
    }

    public void setSupportCount(String supportCount) {
        this.supportCount = supportCount;
    }

    public String getOpposeCount() {
        return opposeCount;
    }

    public void setOpposeCount(String opposeCount) {
        this.opposeCount = opposeCount;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String getcStatus() {
        return cStatus;
    }

    public void setcStatus(String cStatus) {
        this.cStatus = cStatus;
    }

    public String getcLastTime() {
        return cLastTime;
    }

    public void setcLastTime(String cLastTime) {
        this.cLastTime = cLastTime;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

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

    @Override
    public String toString() {
        return "LoopArticleCommentList{" +
                "commentId='" + commentId + '\'' +
                ", userSign='" + userSign + '\'' +
                ", content='" + content + '\'' +
                ", relativeCommentId='" + relativeCommentId + '\'' +
                ", supportCount='" + supportCount + '\'' +
                ", opposeCount='" + opposeCount + '\'' +
                ", cTime='" + cTime + '\'' +
                ", cStatus='" + cStatus + '\'' +
                ", cLastTime='" + cLastTime + '\'' +
                ", articleId='" + articleId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headImg='" + headImg + '\'' +
                '}';
    }
}
