package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/9/8.
 * <p/>
 * 随游评论数据实体类
 */
public class SuiuuComment {


    /**
     * status : 1
     * data : {"data":[{"commentId":"67","userSign":"a4c1406ff4cc382389f19bf6ec3e55c1","content":"厉害","rTitle":"","replayCommentId":"0","supportCount":"0","opposeCount":"0","cTime":"2015-06-20 23:31:15","tripId":"185","isTravel":"2","rUserSign":null,"title":"香港上环步行--古色古香","rnickname":null,"rheadImg":null,"nickname":"dorlen","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg"}],"msg":{"currentPage":"1","startRow":0,"pageSize":"10","sortName":null,"sortType":"asc","totalCount":"1","showAll":false}}
     * message :
     * token : b8ffff8677f451405dd1d519566bf125
     */

    private int status;
    private SuiuuCommentData data;
    private String message;
    private String token;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(SuiuuCommentData data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public SuiuuCommentData getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public static class SuiuuCommentData {
        /**
         * data : [{"commentId":"67","userSign":"a4c1406ff4cc382389f19bf6ec3e55c1","content":"厉害","rTitle":"","replayCommentId":"0","supportCount":"0","opposeCount":"0","cTime":"2015-06-20 23:31:15","tripId":"185","isTravel":"2","rUserSign":null,"title":"香港上环步行--古色古香","rnickname":null,"rheadImg":null,"nickname":"dorlen","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg"}]
         * msg : {"currentPage":"1","startRow":0,"pageSize":"10","sortName":null,"sortType":"asc","totalCount":"1","showAll":false}
         */

        private MsgEntity msg;
        private List<SuiuuCommentItemData> data;

        public void setMsg(MsgEntity msg) {
            this.msg = msg;
        }

        public void setData(List<SuiuuCommentItemData> data) {
            this.data = data;
        }

        public MsgEntity getMsg() {
            return msg;
        }

        public List<SuiuuCommentItemData> getData() {
            return data;
        }

        public static class MsgEntity {
            /**
             * currentPage : 1
             * startRow : 0
             * pageSize : 10
             * sortName : null
             * sortType : asc
             * totalCount : 1
             * showAll : false
             */

            private String currentPage;
            private int startRow;
            private String pageSize;
            private Object sortName;
            private String sortType;
            private String totalCount;
            private boolean showAll;

            public void setCurrentPage(String currentPage) {
                this.currentPage = currentPage;
            }

            public void setStartRow(int startRow) {
                this.startRow = startRow;
            }

            public void setPageSize(String pageSize) {
                this.pageSize = pageSize;
            }

            public void setSortName(Object sortName) {
                this.sortName = sortName;
            }

            public void setSortType(String sortType) {
                this.sortType = sortType;
            }

            public void setTotalCount(String totalCount) {
                this.totalCount = totalCount;
            }

            public void setShowAll(boolean showAll) {
                this.showAll = showAll;
            }

            public String getCurrentPage() {
                return currentPage;
            }

            public int getStartRow() {
                return startRow;
            }

            public String getPageSize() {
                return pageSize;
            }

            public Object getSortName() {
                return sortName;
            }

            public String getSortType() {
                return sortType;
            }

            public String getTotalCount() {
                return totalCount;
            }

            public boolean getShowAll() {
                return showAll;
            }
        }

        public static class SuiuuCommentItemData {
            /**
             * commentId : 67
             * userSign : a4c1406ff4cc382389f19bf6ec3e55c1
             * content : 厉害
             * rTitle :
             * replayCommentId : 0
             * supportCount : 0
             * opposeCount : 0
             * cTime : 2015-06-20 23:31:15
             * tripId : 185
             * isTravel : 2
             * rUserSign : null
             * title : 香港上环步行--古色古香
             * rnickname : null
             * rheadImg : null
             * nickname : dorlen
             * headImg : http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg
             */

            private String commentId;
            private String userSign;
            private String content;
            private String rTitle;
            private String replayCommentId;
            private String supportCount;
            private String opposeCount;
            private String cTime;
            private String tripId;
            private String isTravel;
            private Object rUserSign;
            private String title;
            private Object rnickname;
            private Object rheadImg;
            private String nickname;
            private String headImg;

            public void setCommentId(String commentId) {
                this.commentId = commentId;
            }

            public void setUserSign(String userSign) {
                this.userSign = userSign;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setRTitle(String rTitle) {
                this.rTitle = rTitle;
            }

            public void setReplayCommentId(String replayCommentId) {
                this.replayCommentId = replayCommentId;
            }

            public void setSupportCount(String supportCount) {
                this.supportCount = supportCount;
            }

            public void setOpposeCount(String opposeCount) {
                this.opposeCount = opposeCount;
            }

            public void setCTime(String cTime) {
                this.cTime = cTime;
            }

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

            public void setIsTravel(String isTravel) {
                this.isTravel = isTravel;
            }

            public void setRUserSign(Object rUserSign) {
                this.rUserSign = rUserSign;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setRnickname(Object rnickname) {
                this.rnickname = rnickname;
            }

            public void setRheadImg(Object rheadImg) {
                this.rheadImg = rheadImg;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getCommentId() {
                return commentId;
            }

            public String getUserSign() {
                return userSign;
            }

            public String getContent() {
                return content;
            }

            public String getRTitle() {
                return rTitle;
            }

            public String getReplayCommentId() {
                return replayCommentId;
            }

            public String getSupportCount() {
                return supportCount;
            }

            public String getOpposeCount() {
                return opposeCount;
            }

            public String getCTime() {
                return cTime;
            }

            public String getTripId() {
                return tripId;
            }

            public String getIsTravel() {
                return isTravel;
            }

            public Object getRUserSign() {
                return rUserSign;
            }

            public String getTitle() {
                return title;
            }

            public Object getRnickname() {
                return rnickname;
            }

            public Object getRheadImg() {
                return rheadImg;
            }

            public String getNickname() {
                return nickname;
            }

            public String getHeadImg() {
                return headImg;
            }
        }
    }
}