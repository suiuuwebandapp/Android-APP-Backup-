package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/8/3.
 * <p/>
 * 用户旅图的数据实体类
 */
public class UserTravel {

    /**
     * data : {"msg":{"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"2"},"data":[{"country":"test01","titleImg":"","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","city":"test01","lon":"0.152","attentionCount":"0","title":"test01","tags":"test01","commentCount":"2","contents":"sfasdf","userSign":"085963dc0af031709b032725e3ef18f5","createTime":"2015-07-15 17:25:24","picList":"sdsfdfsdf","intro":"","nickname":"✨yao","id":"1","lat":"13.527","info":""}]}
     * message :
     * status : 1
     * token : 9692223817779d6c792c21f5a0824bc5
     */
    private UserTravelData data;
    private String message;
    private int status;
    private String token;

    public void setData(UserTravelData data) {
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

    public UserTravelData getData() {
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

    public static class UserTravelData {

        /**
         * msg : {"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"2"}
         * data : [{"country":"test01","titleImg":"","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","city":"test01","lon":"0.152","attentionCount":"0","title":"test01","tags":"test01","commentCount":"2","contents":"sfasdf","userSign":"085963dc0af031709b032725e3ef18f5","createTime":"2015-07-15 17:25:24","picList":"sdsfdfsdf","intro":"","nickname":"✨yao","id":"1","lat":"13.527","info":""}]
         */
        private MsgEntity msg;
        private List<UserTravelItemData> data;

        public void setMsg(MsgEntity msg) {
            this.msg = msg;
        }

        public void setData(List<UserTravelItemData> data) {
            this.data = data;
        }

        public MsgEntity getMsg() {
            return msg;
        }

        public List<UserTravelItemData> getData() {
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
             * totalCount : 2
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

        public static class UserTravelItemData {
            /**
             * country : test01
             * titleImg :
             * headImg : http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg
             * city : test01
             * lon : 0.152
             * attentionCount : 0
             * title : test01
             * tags : test01
             * commentCount : 2
             * contents : sfasdf
             * userSign : 085963dc0af031709b032725e3ef18f5
             * createTime : 2015-07-15 17:25:24
             * picList : sdsfdfsdf
             * intro :
             * nickname : ✨yao
             * id : 1
             * lat : 13.527
             * info :
             */
            private String country;
            private String titleImg;
            private String headImg;
            private String city;
            private String lon;
            private String attentionCount;
            private String title;
            private String tags;
            private String commentCount;
            private String contents;
            private String userSign;
            private String createTime;
            private String picList;
            private String intro;
            private String nickname;
            private String id;
            private String lat;
            private String info;

            public void setCountry(String country) {
                this.country = country;
            }

            public void setTitleImg(String titleImg) {
                this.titleImg = titleImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public void setAttentionCount(String attentionCount) {
                this.attentionCount = attentionCount;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public void setCommentCount(String commentCount) {
                this.commentCount = commentCount;
            }

            public void setContents(String contents) {
                this.contents = contents;
            }

            public void setUserSign(String userSign) {
                this.userSign = userSign;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setPicList(String picList) {
                this.picList = picList;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getCountry() {
                return country;
            }

            public String getTitleImg() {
                return titleImg;
            }

            public String getHeadImg() {
                return headImg;
            }

            public String getCity() {
                return city;
            }

            public String getLon() {
                return lon;
            }

            public String getAttentionCount() {
                return attentionCount;
            }

            public String getTitle() {
                return title;
            }

            public String getTags() {
                return tags;
            }

            public String getCommentCount() {
                return commentCount;
            }

            public String getContents() {
                return contents;
            }

            public String getUserSign() {
                return userSign;
            }

            public String getCreateTime() {
                return createTime;
            }

            public String getPicList() {
                return picList;
            }

            public String getIntro() {
                return intro;
            }

            public String getNickname() {
                return nickname;
            }

            public String getId() {
                return id;
            }

            public String getLat() {
                return lat;
            }

            public String getInfo() {
                return info;
            }
        }

    }

}