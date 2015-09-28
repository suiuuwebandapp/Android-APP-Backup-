package com.minglang.suiuu.entity;

import java.util.List;

/**
 *
 * 关注的问答-数据实体类
 *
 * Created by Administrator on 2015/7/21.
 */
public class AttentionProblem {

    /**
     * data : {"msg":{"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"1"},"data":[{"qTitle":"测试02","qCityId":"15","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"2","pvNumber":"0","qInviteAskUser":"085963dc0af031709b032725e3ef18f5","qAddr":"地址02","qUserSign":"085963dc0af031709b032725e3ef18f5","userSign":"085963dc0af031709b032725e3ef18f5","nickname":"✨yao","qCreateTime":"2015-07-13 18:10:40","qContent":"内容02","qCountryId":"14","qTag":"1"}]}
     * message :
     * status : 1
     * token : 0f88c73b8912ebf0556c184bf8321261
     */
    private AttentionProblemData data;
    private String message;
    private int status;
    private String token;

    public void setData(AttentionProblemData data) {
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

    public AttentionProblemData getData() {
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

    public static class AttentionProblemData {
        /**
         * msg : {"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"1"}
         * data : [{"qTitle":"测试02","qCityId":"15","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"2","pvNumber":"0","qInviteAskUser":"085963dc0af031709b032725e3ef18f5","qAddr":"地址02","qUserSign":"085963dc0af031709b032725e3ef18f5","userSign":"085963dc0af031709b032725e3ef18f5","nickname":"✨yao","qCreateTime":"2015-07-13 18:10:40","qContent":"内容02","qCountryId":"14","qTag":"1"}]
         */
        private MsgEntity msg;
        private List<AttentionProblemItemData> data;

        public void setMsg(MsgEntity msg) {
            this.msg = msg;
        }

        public void setData(List<AttentionProblemItemData> data) {
            this.data = data;
        }

        public MsgEntity getMsg() {
            return msg;
        }

        public List<AttentionProblemItemData> getData() {
            return data;
        }

        public static class MsgEntity {
            /**
             * sortName : null
             * showAll : false
             * startRow : 0
             * sortType : asc
             * pageSize : 10
             * currentPage : 1
             * totalCount : 1
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

        public static class AttentionProblemItemData {
            /**
             * qTitle : 测试02
             * qCityId : 15
             * attentionNumber : 0
             * headImg : http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg
             * qId : 2
             * pvNumber : 0
             * qInviteAskUser : 085963dc0af031709b032725e3ef18f5
             * qAddr : 地址02
             * qUserSign : 085963dc0af031709b032725e3ef18f5
             * userSign : 085963dc0af031709b032725e3ef18f5
             * nickname : ✨yao
             * qCreateTime : 2015-07-13 18:10:40
             * qContent : 内容02
             * qCountryId : 14
             * qTag : 1
             */
            private String qTitle;
            private String qCityId;
            private String attentionNumber;
            private String headImg;
            private String qId;
            private String pvNumber;
            private String qInviteAskUser;
            private String qAddr;
            private String qUserSign;
            private String userSign;
            private String nickname;
            private String qCreateTime;
            private String qContent;
            private String qCountryId;
            private String qTag;
            private String aNumber;

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

            public void setQInviteAskUser(String qInviteAskUser) {
                this.qInviteAskUser = qInviteAskUser;
            }

            public void setQAddr(String qAddr) {
                this.qAddr = qAddr;
            }

            public void setQUserSign(String qUserSign) {
                this.qUserSign = qUserSign;
            }

            public void setUserSign(String userSign) {
                this.userSign = userSign;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
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

            public void setaNumber(String aNumber) {
                this.aNumber = aNumber;
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

            public String getQInviteAskUser() {
                return qInviteAskUser;
            }

            public String getQAddr() {
                return qAddr;
            }

            public String getQUserSign() {
                return qUserSign;
            }

            public String getUserSign() {
                return userSign;
            }

            public String getNickname() {
                return nickname;
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

            public String getaNumber() {
                return aNumber;
            }
        }

    }

}