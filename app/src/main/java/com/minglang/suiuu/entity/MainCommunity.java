package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 问答社区页面列表数据实体类
 * <p/>
 * Created by Administrator on 2015/7/16.
 */
public class MainCommunity {

    /**
     * data : {"msg":{"sortName":"pvNumber","showAll":false,"startRow":0,"sortType":"DESC","pageSize":10,"currentPage":1,"totalCount":"4"},"data":[{"qTitle":"测试004","qInviteAskUser":"测试004","qCityId":"221","Number":"0","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 15:58:26","qContent":"测试004","qCountryId":"5","qId":"4","qTag":"1,2,3","pvNumber":"0"},{"qTitle":"测试004","qInviteAskUser":"测试004","qCityId":"221","Number":"0","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 15:59:04","qContent":"测试004","qCountryId":"5","qId":"5","qTag":"1,2,3","pvNumber":"0"},{"qTitle":"测试004","qInviteAskUser":"测试004","qCityId":"221","Number":"0","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 16:23:07","qContent":"测试004","qCountryId":"5","qId":"6","qTag":"1,3,5","pvNumber":"0"},{"qTitle":"测试004","qInviteAskUser":"测试004","qCityId":"221","Number":"0","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 16:23:17","qContent":"测试004","qCountryId":"5","qId":"7","qTag":"1,3,4","pvNumber":"0"}]}
     * message :
     * status : 1
     * token : 655d3f0cf9eb2898d1280b4ebcf261dd
     */
    private MainCommunityData data;
    private String message;
    private int status;
    private String token;

    public void setData(MainCommunityData data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public MainCommunityData getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public class MainCommunityData {
        /**
         * msg : {"sortName":"pvNumber","showAll":false,"startRow":0,"sortType":"DESC","pageSize":10,"currentPage":1,"totalCount":"4"}
         * data : [{"qTitle":"测试004","qInviteAskUser":"测试004","qCityId":"221","Number":"0","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 15:58:26","qContent":"测试004","qCountryId":"5","qId":"4","qTag":"1,2,3","pvNumber":"0"},{"qTitle":"测试004","qInviteAskUser":"测试004","qCityId":"221","Number":"0","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 15:59:04","qContent":"测试004","qCountryId":"5","qId":"5","qTag":"1,2,3","pvNumber":"0"},{"qTitle":"测试004","qInviteAskUser":"测试004","qCityId":"221","Number":"0","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 16:23:07","qContent":"测试004","qCountryId":"5","qId":"6","qTag":"1,3,5","pvNumber":"0"},{"qTitle":"测试004","qInviteAskUser":"测试004","qCityId":"221","Number":"0","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 16:23:17","qContent":"测试004","qCountryId":"5","qId":"7","qTag":"1,3,4","pvNumber":"0"}]
         */
        private MsgEntity msg;
        private List<MainCommunityItemData> data;

        public void setMsg(MsgEntity msg) {
            this.msg = msg;
        }

        public void setData(List<MainCommunityItemData> data) {
            this.data = data;
        }

        public MsgEntity getMsg() {
            return msg;
        }

        public List<MainCommunityItemData> getData() {
            return data;
        }

        public class MsgEntity {
            /**
             * sortName : pvNumber
             * showAll : false
             * startRow : 0
             * sortType : DESC
             * pageSize : 10
             * currentPage : 1
             * totalCount : 4
             */
            private String sortName;
            private boolean showAll;
            private int startRow;
            private String sortType;
            private int pageSize;
            private int currentPage;
            private String totalCount;

            public void setSortName(String sortName) {
                this.sortName = sortName;
            }

            public void setShowAll(boolean showAll) {
                this.showAll = showAll;
            }

            public void setStartRow(int startRow) {
                this.startRow = startRow;
            }

            public void setSortType(String sortType) {
                this.sortType = sortType;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public void setTotalCount(String totalCount) {
                this.totalCount = totalCount;
            }

            public String getSortName() {
                return sortName;
            }

            public boolean isShowAll() {
                return showAll;
            }

            public int getStartRow() {
                return startRow;
            }

            public String getSortType() {
                return sortType;
            }

            public int getPageSize() {
                return pageSize;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public String getTotalCount() {
                return totalCount;
            }
        }

        public class MainCommunityItemData {
            /**
             * qTitle : 测试004
             * qInviteAskUser : 测试004
             * qCityId : 221
             * Number : 0
             * qAddr : 测试004
             * qUserSign : 085963dc0af031709b032725e3ef18f5
             * qCreateTime : 2015-07-14 15:58:26
             * qContent : 测试004
             * qCountryId : 5
             * qId : 4
             * qTag : 1,2,3
             * pvNumber : 0
             */
            private String qTitle;
            private String qInviteAskUser;
            private String qCityId;
            private String Number;
            private String qAddr;
            private String qUserSign;
            private String qCreateTime;
            private String qContent;
            private String qCountryId;
            private String qId;
            private String qTag;
            private String pvNumber;

            public void setQTitle(String qTitle) {
                this.qTitle = qTitle;
            }

            public void setQInviteAskUser(String qInviteAskUser) {
                this.qInviteAskUser = qInviteAskUser;
            }

            public void setQCityId(String qCityId) {
                this.qCityId = qCityId;
            }

            public void setNumber(String Number) {
                this.Number = Number;
            }

            public void setQAddr(String qAddr) {
                this.qAddr = qAddr;
            }

            public void setQUserSign(String qUserSign) {
                this.qUserSign = qUserSign;
            }

            public void setQCreateTime(String qCreateTime) {
                this.qCreateTime = qCreateTime;
            }

            public void setQContent(String qContent) {
                this.qContent = qContent;
            }

            public void setQCountryId(String qCountryId) {
                this.qCountryId = qCountryId;
            }

            public void setQId(String qId) {
                this.qId = qId;
            }

            public void setQTag(String qTag) {
                this.qTag = qTag;
            }

            public void setPvNumber(String pvNumber) {
                this.pvNumber = pvNumber;
            }

            public String getQTitle() {
                return qTitle;
            }

            public String getQInviteAskUser() {
                return qInviteAskUser;
            }

            public String getQCityId() {
                return qCityId;
            }

            public String getNumber() {
                return Number;
            }

            public String getQAddr() {
                return qAddr;
            }

            public String getQUserSign() {
                return qUserSign;
            }

            public String getQCreateTime() {
                return qCreateTime;
            }

            public String getQContent() {
                return qContent;
            }

            public String getQCountryId() {
                return qCountryId;
            }

            public String getQId() {
                return qId;
            }

            public String getQTag() {
                return qTag;
            }

            public String getPvNumber() {
                return pvNumber;
            }
        }
    }

}