package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by a on 2015/8/6.
 *
 * 问答消息数据实体类
 */
public class MsgQuestion {

    /**
     * data : {"msg":{"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"1"},"data":[{"rType":"5","qTitle":"测试01","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","remindId":"4","relativeId":"1","nickname":"dorlen","qId":"1","createUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","content":"","url":"","relativeType":"2"}]}
     * message :
     * status : 1
     * token : a65871d64f0ce6796ef01a6ecf379fca
     */
    private MsgQuestionData data;
    private String message;
    private int status;
    private String token;

    public void setData(MsgQuestionData data) {
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

    public MsgQuestionData getData() {
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

    public static class MsgQuestionData {

        /**
         * msg : {"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"1"}
         * data : [{"rType":"5","qTitle":"测试01","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","remindId":"4","relativeId":"1","nickname":"dorlen","qId":"1","createUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","content":"","url":"","relativeType":"2"}]
         */
        private MsgEntity msg;
        private List<MsgQuestionItemData> data;

        public void setMsg(MsgEntity msg) {
            this.msg = msg;
        }

        public void setData(List<MsgQuestionItemData> data) {
            this.data = data;
        }

        public MsgEntity getMsg() {
            return msg;
        }

        public List<MsgQuestionItemData> getData() {
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

        public static class MsgQuestionItemData {
            /**
             * rType : 5
             * qTitle : 测试01
             * headImg : http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg
             * remindId : 4
             * relativeId : 1
             * nickname : dorlen
             * qId : 1
             * createUserSign : a4c1406ff4cc382389f19bf6ec3e55c1
             * content :
             * url :
             * relativeType : 2
             */
            private String rType;
            private String qTitle;
            private String headImg;
            private String remindId;
            private String relativeId;
            private String nickname;
            private String qId;
            private String createUserSign;
            private String content;
            private String url;
            private String relativeType;

            public void setRType(String rType) {
                this.rType = rType;
            }

            public void setQTitle(String qTitle) {
                this.qTitle = qTitle;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public void setRemindId(String remindId) {
                this.remindId = remindId;
            }

            public void setRelativeId(String relativeId) {
                this.relativeId = relativeId;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setQId(String qId) {
                this.qId = qId;
            }

            public void setCreateUserSign(String createUserSign) {
                this.createUserSign = createUserSign;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setRelativeType(String relativeType) {
                this.relativeType = relativeType;
            }

            public String getRType() {
                return rType;
            }

            public String getQTitle() {
                return qTitle;
            }

            public String getHeadImg() {
                return headImg;
            }

            public String getRemindId() {
                return remindId;
            }

            public String getRelativeId() {
                return relativeId;
            }

            public String getNickname() {
                return nickname;
            }

            public String getQId() {
                return qId;
            }

            public String getCreateUserSign() {
                return createUserSign;
            }

            public String getContent() {
                return content;
            }

            public String getUrl() {
                return url;
            }

            public String getRelativeType() {
                return relativeType;
            }
        }

    }

}