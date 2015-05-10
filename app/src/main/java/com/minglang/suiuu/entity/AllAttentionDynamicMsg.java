package com.minglang.suiuu.entity;

/**
 * Created by LZY on 2015/5/10 0010.
 */
public class AllAttentionDynamicMsg {

    public String currentPage;

    public String startRow;

    public String pageSize;

    public String sortName;

    public String sortType;

    public String totalCount;

    public boolean showAll;

    public String getCurrentPage() {
        return currentPage;
    }

    public String getStartRow() {
        return startRow;
    }

    public String getPageSize() {
        return pageSize;
    }

    public String getSortName() {
        return sortName;
    }

    public String getSortType() {
        return sortType;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public boolean isShowAll() {
        return showAll;
    }

    @Override
    public String toString() {
        return "AllAttentionDynamicMsg{" +
                "currentPage='" + currentPage + '\'' +
                ", startRow='" + startRow + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", sortName='" + sortName + '\'' +
                ", sortType='" + sortType + '\'' +
                ", totalCount='" + totalCount + '\'' +
                ", showAll=" + showAll +
                '}';
    }
}
