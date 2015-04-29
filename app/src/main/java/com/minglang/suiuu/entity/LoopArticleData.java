package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 圈子文章列表Data部分数据实体类
 * <p/>
 * Created by Administrator on 2015/4/25.
 */
public class LoopArticleData {

    public String articleId;

    public String cId;

    public String aTitle;

    public String aContent;

    public String aImg;

    public String aCmtCount;

    public String aImgList;

    public String aSupportCount;

    public String aCreateUserSign;

    public String aCreateTime;

    public String aLastUpdateTime;

    public String aStatus;

    public String aAddr;

    public String nickname;

    public String headImg;

    public List<LoopArticleCommentList> commentList;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getaTitle() {
        return aTitle;
    }

    public void setaTitle(String aTitle) {
        this.aTitle = aTitle;
    }

    public String getaContent() {
        return aContent;
    }

    public void setaContent(String aContent) {
        this.aContent = aContent;
    }

    public String getaImg() {
        return aImg;
    }

    public void setaImg(String aImg) {
        this.aImg = aImg;
    }

    public String getaCmtCount() {
        return aCmtCount;
    }

    public void setaCmtCount(String aCmtCount) {
        this.aCmtCount = aCmtCount;
    }

    public String getaImgList() {
        return aImgList;
    }

    public void setaImgList(String aImgList) {
        this.aImgList = aImgList;
    }

    public String getaSupportCount() {
        return aSupportCount;
    }

    public void setaSupportCount(String aSupportCount) {
        this.aSupportCount = aSupportCount;
    }

    public String getaCreateUserSign() {
        return aCreateUserSign;
    }

    public void setaCreateUserSign(String aCreateUserSign) {
        this.aCreateUserSign = aCreateUserSign;
    }

    public String getaCreateTime() {
        return aCreateTime;
    }

    public void setaCreateTime(String aCreateTime) {
        this.aCreateTime = aCreateTime;
    }

    public String getaLastUpdateTime() {
        return aLastUpdateTime;
    }

    public void setaLastUpdateTime(String aLastUpdateTime) {
        this.aLastUpdateTime = aLastUpdateTime;
    }

    public String getaStatus() {
        return aStatus;
    }

    public void setaStatus(String aStatus) {
        this.aStatus = aStatus;
    }

    public String getaAddr() {
        return aAddr;
    }

    public void setaAddr(String aAddr) {
        this.aAddr = aAddr;
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

    public List<LoopArticleCommentList> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<LoopArticleCommentList> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "LoopArticleData{" +
                "articleId='" + articleId + '\'' +
                ", cId='" + cId + '\'' +
                ", aTitle='" + aTitle + '\'' +
                ", aContent='" + aContent + '\'' +
                ", aImg='" + aImg + '\'' +
                ", aCmtCount='" + aCmtCount + '\'' +
                ", aImgList='" + aImgList + '\'' +
                ", aSupportCount='" + aSupportCount + '\'' +
                ", aCreateUserSign='" + aCreateUserSign + '\'' +
                ", aCreateTime='" + aCreateTime + '\'' +
                ", aLastUpdateTime='" + aLastUpdateTime + '\'' +
                ", aStatus='" + aStatus + '\'' +
                ", aAddr='" + aAddr + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headImg='" + headImg + '\'' +
                ", commentList=" + commentList +
                '}';
    }
}
