package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/5/1.
 */
public class OtherUserData {

    public OtherUserDataInfo user;

    public String fansNumb;

    public List<OtherUserDataAttentionRest> attentionRst;

    public String AttentionNumb;

    public List<OtherUserDataArticle> articleList;

    private List<TravelList> travelList;

    public OtherUserDataInfo getUser() {
        return user;
    }

    public String getFansNumb() {
        return fansNumb;
    }

    public List<OtherUserDataAttentionRest> getAttentionRst() {
        return attentionRst;
    }

    public String getAttentionNumb() {
        return AttentionNumb;
    }

    public List<OtherUserDataArticle> getArticleList() {
        return articleList;
    }

    public List<TravelList> getTravelList() {
        return travelList;
    }

    @Override
    public String toString() {
        return "OtherUserData{" +
                "user=" + user +
                ", fansNumb='" + fansNumb + '\'' +
                ", attentionRst=" + attentionRst +
                ", AttentionNumb='" + AttentionNumb + '\'' +
                ", articleList=" + articleList +
                ", travelList=" + travelList +
                '}';
    }
}
