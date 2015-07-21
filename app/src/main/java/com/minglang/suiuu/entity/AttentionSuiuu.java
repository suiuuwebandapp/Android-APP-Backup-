package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 关注的随游数据实体类
 * <p/>
 * Created by Administrator on 2015/7/21.
 */
public class AttentionSuiuu {

    /**
     * data : {"msg":{"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"1"},"data":[{"score":"0","titleImg":"http://image.suiuu.com/suiuu_trip/41_reset.jpg","headImg":"http://www.suiuu.com/assets/images/user_default.png","userSign":"fcefd450496f3fe497af6841f6f12ae1","intro":"香榭丽舍大道(Champs-Elysees) \u2014\u2014> 凯旋门(ArcdeTriomphe) \u2014\u2014> 协和广场(PlacedelaConcorde)。","nickname":"王晟","tripId":"41","title":"【包车】法国巴黎最浪漫温馨之路","basePrice":"2205.00"}]}
     * message :
     * status : 1
     * token : 3f54f9ba9caf8f98e1a808c50ad2f183
     */
    private AttentionSuiuuData data;
    private String message;
    private int status;
    private String token;

    public void setData(AttentionSuiuuData data) {
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

    public AttentionSuiuuData getData() {
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

    public static class AttentionSuiuuData {
        /**
         * msg : {"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"1"}
         * data : [{"score":"0","titleImg":"http://image.suiuu.com/suiuu_trip/41_reset.jpg","headImg":"http://www.suiuu.com/assets/images/user_default.png","userSign":"fcefd450496f3fe497af6841f6f12ae1","intro":"香榭丽舍大道(Champs-Elysees) \u2014\u2014> 凯旋门(ArcdeTriomphe) \u2014\u2014> 协和广场(PlacedelaConcorde)。","nickname":"王晟","tripId":"41","title":"【包车】法国巴黎最浪漫温馨之路","basePrice":"2205.00"}]
         */
        private MsgEntity msg;
        private List<AttentionSuiuuItemData> data;

        public void setMsg(MsgEntity msg) {
            this.msg = msg;
        }

        public void setData(List<AttentionSuiuuItemData> data) {
            this.data = data;
        }

        public MsgEntity getMsg() {
            return msg;
        }

        public List<AttentionSuiuuItemData> getData() {
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

        public static class AttentionSuiuuItemData {
            /**
             * score : 0
             * titleImg : http://image.suiuu.com/suiuu_trip/41_reset.jpg
             * headImg : http://www.suiuu.com/assets/images/user_default.png
             * userSign : fcefd450496f3fe497af6841f6f12ae1
             * intro : 香榭丽舍大道(Champs-Elysees) ——> 凯旋门(ArcdeTriomphe) ——> 协和广场(PlacedelaConcorde)。
             * nickname : 王晟
             * tripId : 41
             * title : 【包车】法国巴黎最浪漫温馨之路
             * basePrice : 2205.00
             */
            private String score;
            private String titleImg;
            private String headImg;
            private String userSign;
            private String intro;
            private String nickname;
            private String tripId;
            private String title;
            private String basePrice;

            public void setScore(String score) {
                this.score = score;
            }

            public void setTitleImg(String titleImg) {
                this.titleImg = titleImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public void setUserSign(String userSign) {
                this.userSign = userSign;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setBasePrice(String basePrice) {
                this.basePrice = basePrice;
            }

            public String getScore() {
                return score;
            }

            public String getTitleImg() {
                return titleImg;
            }

            public String getHeadImg() {
                return headImg;
            }

            public String getUserSign() {
                return userSign;
            }

            public String getIntro() {
                return intro;
            }

            public String getNickname() {
                return nickname;
            }

            public String getTripId() {
                return tripId;
            }

            public String getTitle() {
                return title;
            }

            public String getBasePrice() {
                return basePrice;
            }
        }
    }
}