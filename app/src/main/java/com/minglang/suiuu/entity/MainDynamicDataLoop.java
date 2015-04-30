package com.minglang.suiuu.entity;

/**
 * 首页圈子动态-主题与地区统一数据实体类
 * <p/>
 * Created by Administrator on 2015/4/30.
 */
public class MainDynamicDataLoop {

    public String aTitle;

    public String articleId;

    public String aImg;

    public String cpic;

    public String cName;

    public String cId;

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

    public String getaImg() {
        return aImg;
    }

    public void setaImg(String aImg) {
        this.aImg = aImg;
    }

    public String getCpic() {
        return cpic;
    }

    public void setCpic(String cpic) {
        this.cpic = cpic;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    @Override
    public String toString() {
        return "MainDynamicDataLoopUnified{" +
                "aTitle='" + aTitle + '\'' +
                ", articleId='" + articleId + '\'' +
                ", aImg='" + aImg + '\'' +
                ", cpic='" + cpic + '\'' +
                ", cName='" + cName + '\'' +
                ", cId='" + cId + '\'' +
                '}';
    }
}
