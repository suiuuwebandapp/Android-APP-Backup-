package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/5/1.
 */
public class OtherUserDataArticle {

    public String cId;

    public String cAddrId;

    public String aTitle;

    public String aImg;

    public String aCmtCount;

    public String aSupportCount;

    public String articleId;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcAddrId() {
        return cAddrId;
    }

    public void setcAddrId(String cAddrId) {
        this.cAddrId = cAddrId;
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

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "OtherUserDataArticle{" +
                "cId='" + cId + '\'' +
                ", cAddrId='" + cAddrId + '\'' +
                ", aTitle='" + aTitle + '\'' +
                ", aImg='" + aImg + '\'' +
                ", aCmtCount='" + aCmtCount + '\'' +
                ", aSupportCount='" + aSupportCount + '\'' +
                ", articleId='" + articleId + '\'' +
                '}';
    }
}
