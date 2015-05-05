package com.minglang.suiuu.entity;

/**
 * 项目名称：Suiuu
 * 类描述：随游详细信息中priceList实体
 * 创建人：Administrator
 * 创建时间：2015/5/5 9:49
 * 修改人：Administrator
 * 修改时间：2015/5/5 9:49
 * 修改备注：
 */
public class SuiuuDetailForPriceList {
    private String priceId;
    private String tripId;
    private String minCount;
    private String maxCount;
    private String price;

    @Override
    public String toString() {
        return "SuiuuDateInfoPriceList{" +
                "priceId='" + priceId + '\'' +
                ", tripId='" + tripId + '\'' +
                ", minCount='" + minCount + '\'' +
                ", maxCount='" + maxCount + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getMinCount() {
        return minCount;
    }

    public void setMinCount(String minCount) {
        this.minCount = minCount;
    }

    public String getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(String maxCount) {
        this.maxCount = maxCount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
