package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 问答社区页面列表数据实体类
 * <p/>
 * Created by Administrator on 2015/7/16.
 */
public class MainCommunity {

    /**
     * data : {"msg":{"sortName":"pvNumber","showAll":false,"startRow":0,"sortType":"DESC","pageSize":10,"currentPage":1,"totalCount":"7"},"data":[{"qTitle":"测试01","qCityId":"0","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"1","pvNumber":"5","aNumber":"0","qInviteAskUser":"085963dc0af031709b032725e3ef18f5","qAddr":"地址01","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-13 14:27:06","qContent":"内容01","qCountryId":"0","qTag":"1"},{"qTitle":"测试02","qCityId":"15","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"2","pvNumber":"0","aNumber":"0","qInviteAskUser":"085963dc0af031709b032725e3ef18f5","qAddr":"地址02","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-13 18:10:40","qContent":"内容02","qCountryId":"14","qTag":"1"},{"qTitle":"测试03","qCityId":"15","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"3","pvNumber":"0","aNumber":"3","qInviteAskUser":"085963dc0af031709b032725e3ef18f5","qAddr":"地址03","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-13 18:10:56","qContent":"内容03","qCountryId":"7","qTag":"1"},{"qTitle":"测试004","qCityId":"221","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"4","pvNumber":"0","aNumber":"0","qInviteAskUser":"测试004","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 15:58:26","qContent":"测试004","qCountryId":"5","qTag":"1,2,3"},{"qTitle":"测试004","qCityId":"221","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"5","pvNumber":"0","aNumber":"0","qInviteAskUser":"测试004","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 15:59:04","qContent":"测试004","qCountryId":"5","qTag":"1,2,3"},{"qTitle":"测试004","qCityId":"221","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"6","pvNumber":"0","aNumber":"0","qInviteAskUser":"测试004","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 16:23:07","qContent":"测试004","qCountryId":"5","qTag":"1,3,5"},{"qTitle":"测试004","qCityId":"221","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"7","pvNumber":"0","aNumber":"0","qInviteAskUser":"测试004","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 16:23:17","qContent":"测试004","qCountryId":"5","qTag":"1,3,4"}]}
     * message :
     * status : 1
     * token : fe5111df294e2735bb110dde5fded9fd
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
         * msg : {"sortName":"pvNumber","showAll":false,"startRow":0,"sortType":"DESC","pageSize":10,"currentPage":1,"totalCount":"7"}
         * data : [{"qTitle":"测试01","qCityId":"0","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"1","pvNumber":"5","aNumber":"0","qInviteAskUser":"085963dc0af031709b032725e3ef18f5","qAddr":"地址01","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-13 14:27:06","qContent":"内容01","qCountryId":"0","qTag":"1"},{"qTitle":"测试02","qCityId":"15","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"2","pvNumber":"0","aNumber":"0","qInviteAskUser":"085963dc0af031709b032725e3ef18f5","qAddr":"地址02","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-13 18:10:40","qContent":"内容02","qCountryId":"14","qTag":"1"},{"qTitle":"测试03","qCityId":"15","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"3","pvNumber":"0","aNumber":"3","qInviteAskUser":"085963dc0af031709b032725e3ef18f5","qAddr":"地址03","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-13 18:10:56","qContent":"内容03","qCountryId":"7","qTag":"1"},{"qTitle":"测试004","qCityId":"221","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"4","pvNumber":"0","aNumber":"0","qInviteAskUser":"测试004","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 15:58:26","qContent":"测试004","qCountryId":"5","qTag":"1,2,3"},{"qTitle":"测试004","qCityId":"221","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"5","pvNumber":"0","aNumber":"0","qInviteAskUser":"测试004","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 15:59:04","qContent":"测试004","qCountryId":"5","qTag":"1,2,3"},{"qTitle":"测试004","qCityId":"221","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"6","pvNumber":"0","aNumber":"0","qInviteAskUser":"测试004","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 16:23:07","qContent":"测试004","qCountryId":"5","qTag":"1,3,5"},{"qTitle":"测试004","qCityId":"221","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"7","pvNumber":"0","aNumber":"0","qInviteAskUser":"测试004","qAddr":"测试004","qUserSign":"085963dc0af031709b032725e3ef18f5","qCreateTime":"2015-07-14 16:23:17","qContent":"测试004","qCountryId":"5","qTag":"1,3,4"}]
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
             * totalCount : 7
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
             * qTitle : 测试01
             * qCityId : 0
             * attentionNumber : 0
             * headImg : http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg
             * qId : 1
             * pvNumber : 5
             * aNumber : 0
             * qInviteAskUser : 085963dc0af031709b032725e3ef18f5
             * qAddr : 地址01
             * qUserSign : 085963dc0af031709b032725e3ef18f5
             * qCreateTime : 2015-07-13 14:27:06
             * qContent : 内容01
             * qCountryId : 0
             * qTag : 1
             */
            private String qTitle;
            private String qCityId;
            private String attentionNumber;
            private String headImg;
            private String qId;
            private String pvNumber;
            private String aNumber;
            private String qInviteAskUser;
            private String qAddr;
            private String qUserSign;
            private String qCreateTime;
            private String qContent;
            private String qCountryId;
            private String qTag;

            public void setQTitle(String qTitle) {
                this.qTitle = qTitle;
            }

            public void setQCityId(String qCityId) {
                this.qCityId = qCityId;
            }

            public void setAttentionNumber(String attentionNumber) {
                this.attentionNumber = attentionNumber;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public void setQId(String qId) {
                this.qId = qId;
            }

            public void setPvNumber(String pvNumber) {
                this.pvNumber = pvNumber;
            }

            public void setANumber(String aNumber) {
                this.aNumber = aNumber;
            }

            public void setQInviteAskUser(String qInviteAskUser) {
                this.qInviteAskUser = qInviteAskUser;
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

            public void setQTag(String qTag) {
                this.qTag = qTag;
            }

            public String getQTitle() {
                return qTitle;
            }

            public String getQCityId() {
                return qCityId;
            }

            public String getAttentionNumber() {
                return attentionNumber;
            }

            public String getHeadImg() {
                return headImg;
            }

            public String getQId() {
                return qId;
            }

            public String getPvNumber() {
                return pvNumber;
            }

            public String getANumber() {
                return aNumber;
            }

            public String getQInviteAskUser() {
                return qInviteAskUser;
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

            public String getQTag() {
                return qTag;
            }
        }
    }
}