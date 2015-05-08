package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 圈子文章列表Data部分数据实体类
 * <p/>
 * Created by Administrator on 2015/4/25.
 */
public class LoopArticleData {

    public String cId;

    public String cAddrId;

    public String aTitle;

    public String aContent;

    public String aImg;

    public String aCmtCount;

    public String aSupportCount;

    public String aCreateUserSign;

    public String aCreateTime;

    public String aLastUpdateTime;

    public String aStatus;

    public String aAddr;

    public String aImgList;

    public String aType;

    public String nickname;

    public String headImg;

    public String articleId;

    public String attentionId;

    public List<LoopArticleCommentList> commentList;

    public String praise;

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getcId() {
        return cId;
    }

    public String getcAddrId() {
        return cAddrId;
    }

    public String getaTitle() {
        return aTitle;
    }

    public String getaContent() {
        return aContent;
    }

    public String getaImg() {
        return aImg;
    }

    public String getaCmtCount() {
        return aCmtCount;
    }

    public String getaSupportCount() {
        return aSupportCount;
    }

    public String getaCreateUserSign() {
        return aCreateUserSign;
    }

    public String getaCreateTime() {
        return aCreateTime;
    }

    public String getaLastUpdateTime() {
        return aLastUpdateTime;
    }

    public String getaStatus() {
        return aStatus;
    }

    public String getaAddr() {
        return aAddr;
    }

    public String getaImgList() {
        return aImgList;
    }

    public String getaType() {
        return aType;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public String getAttentionId() {
        return attentionId;
    }

    public String getArticleId() {
        return articleId;
    }

    public List<LoopArticleCommentList> getCommentList() {
        return commentList;
    }

    @Override
    public String toString() {
        return "LoopArticleData{" +
                "cId='" + cId + '\'' +
                ", cAddrId='" + cAddrId + '\'' +
                ", aTitle='" + aTitle + '\'' +
                ", aContent='" + aContent + '\'' +
                ", aImg='" + aImg + '\'' +
                ", aCmtCount='" + aCmtCount + '\'' +
                ", aSupportCount='" + aSupportCount + '\'' +
                ", aCreateUserSign='" + aCreateUserSign + '\'' +
                ", aCreateTime='" + aCreateTime + '\'' +
                ", aLastUpdateTime='" + aLastUpdateTime + '\'' +
                ", aStatus='" + aStatus + '\'' +
                ", aAddr='" + aAddr + '\'' +
                ", aImgList='" + aImgList + '\'' +
                ", aType='" + aType + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headImg='" + headImg + '\'' +
                ", attentionId='" + attentionId + '\'' +
                ", articleId='" + articleId + '\'' +
                ", commentList=" + commentList +
                '}';
    }
}
