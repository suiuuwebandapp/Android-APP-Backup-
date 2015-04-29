package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/4/28.
 */
public class CollectionLoopData {
    public String headImg;

    public String nickname;

    public String articleId;

    public String aTitle;

    public String aImg;

    public String aCmtCount;

    public String aSupportCount;

    public String aContent;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getaTitle() {
        return aTitle;
    }

    public void setaTitle(String aTitle) {
        this.aTitle = aTitle;
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

    public String getaSupportCount() {
        return aSupportCount;
    }

    public void setaSupportCount(String aSupportCount) {
        this.aSupportCount = aSupportCount;
    }

    public String getaContent() {
        return aContent;
    }

    public void setaContent(String aContent) {
        this.aContent = aContent;
    }

    @Override
    public String toString() {
        return "CollectionLoopData{" +
                "headImg='" + headImg + '\'' +
                ", nickname='" + nickname + '\'' +
                ", articleId='" + articleId + '\'' +
                ", aTitle='" + aTitle + '\'' +
                ", aImg='" + aImg + '\'' +
                ", aCmtCount='" + aCmtCount + '\'' +
                ", aSupportCount='" + aSupportCount + '\'' +
                ", aContent='" + aContent + '\'' +
                '}';
    }
}
