package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/4/29.
 */
public class MainDynamicDataCircleDynamicAddress {

    public String cName;

    public String cpic;

    public String aImg;

    public String cAddrId;

    public String aTitle;

    public String articleId;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getCpic() {
        return cpic;
    }

    public void setCpic(String cpic) {
        this.cpic = cpic;
    }

    public String getaImg() {
        return aImg;
    }

    public void setaImg(String aImg) {
        this.aImg = aImg;
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

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "MainDynamicDataCircleDynamicAddress{" +
                "cName='" + cName + '\'' +
                ", cpic='" + cpic + '\'' +
                ", aImg='" + aImg + '\'' +
                ", cAddrId='" + cAddrId + '\'' +
                ", aTitle='" + aTitle + '\'' +
                ", articleId='" + articleId + '\'' +
                '}';
    }
}
