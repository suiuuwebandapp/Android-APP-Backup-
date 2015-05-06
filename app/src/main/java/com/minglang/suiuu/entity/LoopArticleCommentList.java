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

    public String getUserSign() {
        return userSign;
    }

    public String getContent() {
        return content;
    }

    public String getRelativeCommentId() {
        return relativeCommentId;
    }

    public String getSupportCount() {
        return supportCount;
    }

    public String getOpposeCount() {
        return opposeCount;
    }

    public String getcTime() {
        return cTime;
    }

    public String getcStatus() {
        return cStatus;
    }

    public String getcLastTime() {
        return cLastTime;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHeadImg() {
        return headImg;
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
