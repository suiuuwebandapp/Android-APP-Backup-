package com.minglang.suiuu.entity;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/21 17:04
 * 修改人：Administrator
 * 修改时间：2015/7/21 17:04
 * 修改备注：
 */
public class SuiuuDataForMessage {

    private String currentPage;
    private String startRow;
    private String pageSize;
    private String sortName;
    private String sortType;
    private String totalCount;
    private String showAll;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getStartRow() {
        return startRow;
    }

    public void setStartRow(String startRow) {
        this.startRow = startRow;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getShowAll() {
        return showAll;
    }

    public void setShowAll(String showAll) {
        this.showAll = showAll;
    }

    @Override
    public String toString() {
        return "SuiuuDataForMessage{" +
                "currentPage='" + currentPage + '\'' +
                ", startRow='" + startRow + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", sortName='" + sortName + '\'' +
                ", sortType='" + sortType + '\'' +
                ", totalCount='" + totalCount + '\'' +
                ", showAll='" + showAll + '\'' +
                '}';
    }
}
