package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 旅图列表的实体
 */
public class TripGallery {

    /**
     * data : {"msg":{"sortName":"id","showAll":false,"startRow":0,"sortType":"DESC","pageSize":10,"currentPage":1,"totalCount":"6"},"data":[{"country":"中国","titleImg":"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150702113931.jpg","headImg":"http://www.suiuu.com/assets/images/user_default.png","city":"北京市","lon":"116.422956","attentionCount":"0","title":"咯你","tags":"购物  自然  ","commentCount":"0","contents":null,"userSign":"22e64460c94f3b44bc0a8790ed146d3b","createTime":"2015-07-23 13:28:28","picList":"[\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150702113931.jpg\"]","id":"6","lat":"39.949118"},{"country":"中国","titleImg":"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150702113931.jpg","headImg":"http://www.suiuu.com/assets/images/user_default.png","city":"北京市","lon":"116.423496","attentionCount":"0","title":"咯你","tags":"购物  ","commentCount":"0","contents":null,"userSign":"22e64460c94f3b44bc0a8790ed146d3b","createTime":"2015-07-23 11:19:57","picList":"[\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150702113931.jpg\"]","id":"5","lat":"39.949469"},{"country":"中国","titleImg":"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150702113931.jpg","headImg":"http://www.suiuu.com/assets/images/user_default.png","city":"北京市","lon":"116.423394","attentionCount":"0","title":"按摩","tags":"家庭  购物  ","commentCount":"0","contents":null,"userSign":"22e64460c94f3b44bc0a8790ed146d3b","createTime":"2015-07-23 11:10:53","picList":"[\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150702113931.jpg\"]","id":"4","lat":"39.949113"},{"country":"43","titleImg":"rewq","headImg":"http://www.suiuu.com/assets/images/user_default.png","city":"3e23","lon":"rf","attentionCount":"0","title":"dksaf","tags":"reqwrewq","commentCount":"0","contents":"reqwrewq","userSign":"22e64460c94f3b44bc0a8790ed146d3b","createTime":"2015-07-23 11:03:37","picList":"rewq","id":"3","lat":"erwqr"},{"country":"test02","titleImg":"","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","city":"test01","lon":"0.152","attentionCount":"0","title":"test02","tags":"test01","commentCount":"0","contents":"sfasdf","userSign":"085963dc0af031709b032725e3ef18f5","createTime":"2015-07-15 17:25:35","picList":"sdsfdfsdf","id":"2","lat":"13.527"},{"country":"test01","titleImg":"","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","city":"test01","lon":"0.152","attentionCount":"0","title":"test01","tags":"test01","commentCount":"2","contents":"sfasdf","userSign":"085963dc0af031709b032725e3ef18f5","createTime":"2015-07-15 17:25:24","picList":"sdsfdfsdf","id":"1","lat":"13.527"}]}
     * message :
     * status : 1
     * token : e933b4531e7620a301a198483a7514b4
     */
    private DataEntity data;
    private String message;
    private int status;
    private String token;

    public void setData(DataEntity data) {
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

    public DataEntity getData() {
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

    public class DataEntity {
        /**
         * msg : {"sortName":"id","showAll":false,"startRow":0,"sortType":"DESC","pageSize":10,"currentPage":1,"totalCount":"6"}
         * data : [{"country":"中国","titleImg":"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150702113931.jpg","headImg":"http://www.suiuu.com/assets/images/user_default.png","city":"北京市","lon":"116.422956","attentionCount":"0","title":"咯你","tags":"购物  自然  ","commentCount":"0","contents":null,"userSign":"22e64460c94f3b44bc0a8790ed146d3b","createTime":"2015-07-23 13:28:28","picList":"[\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150702113931.jpg\"]","id":"6","lat":"39.949118"},{"country":"中国","titleImg":"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150702113931.jpg","headImg":"http://www.suiuu.com/assets/images/user_default.png","city":"北京市","lon":"116.423496","attentionCount":"0","title":"咯你","tags":"购物  ","commentCount":"0","contents":null,"userSign":"22e64460c94f3b44bc0a8790ed146d3b","createTime":"2015-07-23 11:19:57","picList":"[\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150702113931.jpg\"]","id":"5","lat":"39.949469"},{"country":"中国","titleImg":"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150702113931.jpg","headImg":"http://www.suiuu.com/assets/images/user_default.png","city":"北京市","lon":"116.423394","attentionCount":"0","title":"按摩","tags":"家庭  购物  ","commentCount":"0","contents":null,"userSign":"22e64460c94f3b44bc0a8790ed146d3b","createTime":"2015-07-23 11:10:53","picList":"[\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150702113931.jpg\"]","id":"4","lat":"39.949113"},{"country":"43","titleImg":"rewq","headImg":"http://www.suiuu.com/assets/images/user_default.png","city":"3e23","lon":"rf","attentionCount":"0","title":"dksaf","tags":"reqwrewq","commentCount":"0","contents":"reqwrewq","userSign":"22e64460c94f3b44bc0a8790ed146d3b","createTime":"2015-07-23 11:03:37","picList":"rewq","id":"3","lat":"erwqr"},{"country":"test02","titleImg":"","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","city":"test01","lon":"0.152","attentionCount":"0","title":"test02","tags":"test01","commentCount":"0","contents":"sfasdf","userSign":"085963dc0af031709b032725e3ef18f5","createTime":"2015-07-15 17:25:35","picList":"sdsfdfsdf","id":"2","lat":"13.527"},{"country":"test01","titleImg":"","headImg":"http://image.suiuu.com/suiuu_head/20150514090607_29484.jpg","city":"test01","lon":"0.152","attentionCount":"0","title":"test01","tags":"test01","commentCount":"2","contents":"sfasdf","userSign":"085963dc0af031709b032725e3ef18f5","createTime":"2015-07-15 17:25:24","picList":"sdsfdfsdf","id":"1","lat":"13.527"}]
         */
        private MsgEntity msg;
        private List<TripGalleryDataInfo> data;

        public void setMsg(MsgEntity msg) {
            this.msg = msg;
        }

        public void setData(List<TripGalleryDataInfo> data) {
            this.data = data;
        }

        public MsgEntity getMsg() {
            return msg;
        }

        public List<TripGalleryDataInfo> getData() {
            return data;
        }

        public class MsgEntity {
            /**
             * sortName : id
             * showAll : false
             * startRow : 0
             * sortType : DESC
             * pageSize : 10
             * currentPage : 1
             * totalCount : 6
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

        public class TripGalleryDataInfo {
            /**
             * country : 中国
             * titleImg : http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150702113931.jpg
             * headImg : http://www.suiuu.com/assets/images/user_default.png
             * city : 北京市
             * lon : 116.422956
             * attentionCount : 0
             * title : 咯你
             * tags : 购物  自然
             * commentCount : 0
             * contents : null
             * userSign : 22e64460c94f3b44bc0a8790ed146d3b
             * createTime : 2015-07-23 13:28:28
             * picList : ["http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/20150702113931.jpg"]
             * id : 6
             * lat : 39.949118
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
            private String id;
            private String lat;

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

            public void setId(String id) {
                this.id = id;
            }

            public void setLat(String lat) {
                this.lat = lat;
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

            public String getId() {
                return id;
            }

            public String getLat() {
                return lat;
            }
        }
    }
}
