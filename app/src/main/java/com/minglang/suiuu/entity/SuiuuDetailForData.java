package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 项目名称：Suiuu
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/5/5 10:36
 * 修改人：Administrator
 * 修改时间：2015/5/5 10:36
 * 修改备注：
 */
public class SuiuuDetailForData {
    private List<SuiuuDetailForPraise> praise;
    private List<SuiuuDetailForAttention> attention;
    private SuiuuDetailForInfo info;
    private List<SuiuuDetailForPicList> picList;
    private List<SuiuuDetailForPriceList> priceList;
    private List<SuiuuDetailForPublisherList> publisherList;
    private List<SuiuuDetailForScenicList> scenicList;
    private List<SuiuuDetailForServiceList> serviceList;
    private SuiuuDetailForPublisherList createPublisherInfo;
    @Override
    public String toString() {
        return "SuiuuDetailForData{" +
                "praise=" + praise +
                ", attention=" + attention +
                ", info=" + info +
                ", picList=" + picList +
                ", priceList=" + priceList +
                ", publisherList=" + publisherList +
                ", scenicList=" + scenicList +
                ", serviceList=" + serviceList +
                '}';
    }

    public SuiuuDetailForPublisherList getCreatePublisherInfo() {
        return createPublisherInfo;
    }

    public void setCreatePublisherInfo(SuiuuDetailForPublisherList createPublisherInfo) {
        this.createPublisherInfo = createPublisherInfo;
    }

    public List<SuiuuDetailForPraise> getPraise() {
        return praise;
    }

    public void setPraise(List<SuiuuDetailForPraise> praise) {
        this.praise = praise;
    }

    public List<SuiuuDetailForAttention> getAttention() {
        return attention;
    }

    public void setAttention(List<SuiuuDetailForAttention> attention) {
        this.attention = attention;
    }

    public SuiuuDetailForInfo getInfo() {
        return info;
    }

    public void setInfo(SuiuuDetailForInfo info) {
        this.info = info;
    }

    public List<SuiuuDetailForPicList> getPicList() {
        return picList;
    }

    public void setPicList(List<SuiuuDetailForPicList> picList) {
        this.picList = picList;
    }

    public List<SuiuuDetailForPriceList> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<SuiuuDetailForPriceList> priceList) {
        this.priceList = priceList;
    }

    public List<SuiuuDetailForPublisherList> getPublisherList() {
        return publisherList;
    }

    public void setPublisherList(List<SuiuuDetailForPublisherList> publisherList) {
        this.publisherList = publisherList;
    }

    public List<SuiuuDetailForScenicList> getScenicList() {
        return scenicList;
    }

    public void setScenicList(List<SuiuuDetailForScenicList> scenicList) {
        this.scenicList = scenicList;
    }

    public List<SuiuuDetailForServiceList> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<SuiuuDetailForServiceList> serviceList) {
        this.serviceList = serviceList;
    }
}
