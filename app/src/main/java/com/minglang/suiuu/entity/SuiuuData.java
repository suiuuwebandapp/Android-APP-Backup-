package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 项目名称：Suiuu
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/5/4 12:30
 * 修改人：Administrator
 * 修改时间：2015/5/4 12:30
 * 修改备注：
 */
public class SuiuuData {
    private String draw;
    private String currentPage;
    private String startRow;
    private String pageSize;
    private String sortName;
    private String sortType;
    private String totalCount;
    private String showAll;
    private List<SuiuuDataList> list;

    public String getDraw() {
        return draw;
    }

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

    public String getShowAll() {
        return showAll;
    }

    public List<SuiuuDataList> getList() {
        return list;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public void setStartRow(String startRow) {
        this.startRow = startRow;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public void setShowAll(String showAll) {
        this.showAll = showAll;
    }

    public void setList(List<SuiuuDataList> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "SuiuuData{" +
                "draw='" + draw + '\'' +
                ", currentPage='" + currentPage + '\'' +
                ", startRow='" + startRow + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", sortName='" + sortName + '\'' +
                ", sortType='" + sortType + '\'' +
                ", totalCount='" + totalCount + '\'' +
                ", showAll='" + showAll + '\'' +
                ", list=" + list +
                '}';
    }
}
