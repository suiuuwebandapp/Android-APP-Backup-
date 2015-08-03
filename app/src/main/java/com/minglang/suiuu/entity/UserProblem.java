package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/8/3.
 * <p/>
 * 用户的问答社区数据实体类
 */
public class UserProblem {

    /**
     * data : {"msg":{"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"7"},"data":[{"qTitle":"测试01","qCityId":"0","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"1","pvNumber":"6","qInviteAskUser":"085963dc0af031709b032725e3ef18f5","qAddr":"地址01","qUserSign":"085963dc0af031709b032725e3ef18f5","intro":"","nickname":"✨yao","qCreateTime":"2015-07-13 14:27:06","qContent":"内容01","qCountryId":"0","qTag":"1","info":""}]}
     * message :
     * status : 1
     * token : 50f3600830bcbbec936622882afefe40
     */
    private UserProblemData data;
    private String message;
    private int status;
    private String token;

    public void setData(UserProblemData data) {
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

    public UserProblemData getData() {
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

    public static class UserProblemData {
        /**
         * msg : {"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"7"}
         * data : [{"qTitle":"测试01","qCityId":"0","attentionNumber":"0","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","qId":"1","pvNumber":"6","qInviteAskUser":"085963dc0af031709b032725e3ef18f5","qAddr":"地址01","qUserSign":"085963dc0af031709b032725e3ef18f5","intro":"","nickname":"✨yao","qCreateTime":"2015-07-13 14:27:06","qContent":"内容01","qCountryId":"0","qTag":"1","info":""}]
         */
        private MsgEntity msg;
        private List<UserProblemItemData> data;

        public void setMsg(MsgEntity msg) {
            this.msg = msg;
        }

        public void setData(List<UserProblemItemData> data) {
            this.data = data;
        }

        public MsgEntity getMsg() {
            return msg;
        }

        public List<UserProblemItemData> getData() {
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

        public static class UserProblemItemData {

            /**
             * qTitle : 测试01
             * qCityId : 0
             * attentionNumber : 0
             * headImg : http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg
             * qId : 1
             * pvNumber : 6
             * qInviteAskUser : 085963dc0af031709b032725e3ef18f5
             * qAddr : 地址01
             * qUserSign : 085963dc0af031709b032725e3ef18f5
             * intro :
             * nickname : ✨yao
             * qCreateTime : 2015-07-13 14:27:06
             * qContent : 内容01
             * qCountryId : 0
             * qTag : 1
             * info :
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
            private String intro;
            private String nickname;
            private String qCreateTime;
            private String qContent;
            private String qCountryId;
            private String qTag;
            private String info;

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

            public void setIntro(String intro) {
                this.intro = intro;
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

            public void setInfo(String info) {
                this.info = info;
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

            public String getIntro() {
                return intro;
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

            public String getInfo() {
                return info;
            }
        }

    }

}