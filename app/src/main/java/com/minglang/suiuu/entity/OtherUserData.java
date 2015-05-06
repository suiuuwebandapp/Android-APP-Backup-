package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/1.
 */
public class OtherUserData {

    public OtherUserDataInfo user;

    public String fansNumb;

    public OtherUserDataAttentionRest attentionRst;

    public String AttentionNumb;

    public List<OtherUserDataArticle> articleList;

    private SuiuuDataForMessage msg;

    public SuiuuDataForMessage getMsg() {
        return msg;
    }

    public void setMsg(SuiuuDataForMessage msg) {
        this.msg = msg;
    }

    public OtherUserDataInfo getUser() {
        return user;
    }

    public void setUser(OtherUserDataInfo user) {
        this.user = user;
    }

    public String getFansNumb() {
        return fansNumb;
    }

    public void setFansNumb(String fansNumb) {
        this.fansNumb = fansNumb;
    }

    public OtherUserDataAttentionRest getAttentionRst() {
        return attentionRst;
    }

    public void setAttentionRst(OtherUserDataAttentionRest attentionRst) {
        this.attentionRst = attentionRst;
    }

    public String getAttentionNumb() {
        return AttentionNumb;
    }

    public void setAttentionNumb(String attentionNumb) {
        AttentionNumb = attentionNumb;
    }

    public List<OtherUserDataArticle> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<OtherUserDataArticle> articleList) {
        this.articleList = articleList;
    }

    @Override
    public String toString() {
        return "OtherUserData{" +
                "user=" + user +
                ", fansNumb='" + fansNumb + '\'' +
                ", attentionRst=" + attentionRst +
                ", AttentionNumb='" + AttentionNumb + '\'' +
                ", articleList=" + articleList +
                '}';
    }
}
