package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/24 12:11
 * 修改人：Administrator
 * 修改时间：2015/7/24 12:11
 * 修改备注：
 */
public class TripGalleryDetail {

    /**
     * data : {"like":[{"country":"中国","titleImg":"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_7476.jpg","address":"","headImg":"http://www.suiuu.com/assets/images/user_default.png","city":"北京市","lon":"116.423511","attentionCount":"0","title":"如此的美","tags":"购物  惊险  浪漫  ","commentCount":"1","contents":null,"userSign":"22e64460c94f3b44bc0a8790ed146d3b","createTime":"2015-07-23 16:22:01","picList":"[\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_7476.jpg\",\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_9280.jpg\",\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037234_3539.jpg\"]","id":"4","lat":"39.949467"}],"attention":[],"comment":[{"tpId":"4","supportCount":"0","headImg":"http://www.suiuu.com/assets/images/user_default.png","userSign":"22e64460c94f3b44bc0a8790ed146d3b","createTime":"2015-07-24 12:10:05","nickname":"15111993537","comment":"哎哟 不错哦","id":"8"}],"info":{"country":"中国","titleImg":"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_7476.jpg","address":"","headImg":"http://www.suiuu.com/assets/images/user_default.png","city":"北京市","lon":"116.423511","attentionCount":"0","title":"如此的美","tags":"购物  惊险  浪漫  ","commentCount":"1","contents":null,"userSign":"22e64460c94f3b44bc0a8790ed146d3b","createTime":"2015-07-23 16:22:01","picList":"[\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_7476.jpg\",\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_9280.jpg\",\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037234_3539.jpg\"]","nickname":"15111993537","id":"4","lat":"39.949467"}}
     * message :
     * status : 1
     * token : 02d4ac6d4814e6dadac076c2197ecf99
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
         * like : [{"country":"中国","titleImg":"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_7476.jpg","address":"","headImg":"http://www.suiuu.com/assets/images/user_default.png","city":"北京市","lon":"116.423511","attentionCount":"0","title":"如此的美","tags":"购物  惊险  浪漫  ","commentCount":"1","contents":null,"userSign":"22e64460c94f3b44bc0a8790ed146d3b","createTime":"2015-07-23 16:22:01","picList":"[\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_7476.jpg\",\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_9280.jpg\",\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037234_3539.jpg\"]","id":"4","lat":"39.949467"}]
         * attention : []
         * comment : [{"tpId":"4","supportCount":"0","headImg":"http://www.suiuu.com/assets/images/user_default.png","userSign":"22e64460c94f3b44bc0a8790ed146d3b","createTime":"2015-07-24 12:10:05","nickname":"15111993537","comment":"哎哟 不错哦","id":"8"}]
         * info : {"country":"中国","titleImg":"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_7476.jpg","address":"","headImg":"http://www.suiuu.com/assets/images/user_default.png","city":"北京市","lon":"116.423511","attentionCount":"0","title":"如此的美","tags":"购物  惊险  浪漫  ","commentCount":"1","contents":null,"userSign":"22e64460c94f3b44bc0a8790ed146d3b","createTime":"2015-07-23 16:22:01","picList":"[\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_7476.jpg\",\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_9280.jpg\",\"http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037234_3539.jpg\"]","nickname":"15111993537","id":"4","lat":"39.949467"}
         */
        private List<LikeEntity> like;
        private List<?> attention;
        private List<CommentEntity> comment;
        private InfoEntity info;

        public void setLike(List<LikeEntity> like) {
            this.like = like;
        }

        public void setAttention(List<?> attention) {
            this.attention = attention;
        }

        public void setComment(List<CommentEntity> comment) {
            this.comment = comment;
        }

        public void setInfo(InfoEntity info) {
            this.info = info;
        }

        public List<LikeEntity> getLike() {
            return like;
        }

        public List<?> getAttention() {
            return attention;
        }

        public List<CommentEntity> getComment() {
            return comment;
        }

        public InfoEntity getInfo() {
            return info;
        }

        public class LikeEntity {
            /**
             * country : 中国
             * titleImg : http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_7476.jpg
             * address :
             * headImg : http://www.suiuu.com/assets/images/user_default.png
             * city : 北京市
             * lon : 116.423511
             * attentionCount : 0
             * title : 如此的美
             * tags : 购物  惊险  浪漫
             * commentCount : 1
             * contents : null
             * userSign : 22e64460c94f3b44bc0a8790ed146d3b
             * createTime : 2015-07-23 16:22:01
             * picList : ["http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_7476.jpg","http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_9280.jpg","http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037234_3539.jpg"]
             * id : 4
             * lat : 39.949467
             */
            private String country;
            private String titleImg;
            private String address;
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

            public void setAddress(String address) {
                this.address = address;
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

            public String getAddress() {
                return address;
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

        public class CommentEntity {
            /**
             * tpId : 4
             * supportCount : 0
             * headImg : http://www.suiuu.com/assets/images/user_default.png
             * userSign : 22e64460c94f3b44bc0a8790ed146d3b
             * createTime : 2015-07-24 12:10:05
             * nickname : 15111993537
             * comment : 哎哟 不错哦
             * id : 8
             */
            private String tpId;
            private String supportCount;
            private String headImg;
            private String userSign;
            private String createTime;
            private String nickname;
            private String comment;
            private String id;

            public void setTpId(String tpId) {
                this.tpId = tpId;
            }

            public void setSupportCount(String supportCount) {
                this.supportCount = supportCount;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public void setUserSign(String userSign) {
                this.userSign = userSign;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTpId() {
                return tpId;
            }

            public String getSupportCount() {
                return supportCount;
            }

            public String getHeadImg() {
                return headImg;
            }

            public String getUserSign() {
                return userSign;
            }

            public String getCreateTime() {
                return createTime;
            }

            public String getNickname() {
                return nickname;
            }

            public String getComment() {
                return comment;
            }

            public String getId() {
                return id;
            }
        }

        public class InfoEntity {
            /**
             * country : 中国
             * titleImg : http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_7476.jpg
             * address :
             * headImg : http://www.suiuu.com/assets/images/user_default.png
             * city : 北京市
             * lon : 116.423511
             * attentionCount : 0
             * title : 如此的美
             * tags : 购物  惊险  浪漫
             * commentCount : 1
             * contents : null
             * userSign : 22e64460c94f3b44bc0a8790ed146d3b
             * createTime : 2015-07-23 16:22:01
             * picList : ["http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_7476.jpg","http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037235_9280.jpg","http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_content/1378037234_3539.jpg"]
             * nickname : 15111993537
             * id : 4
             * lat : 39.949467
             */
            private String country;
            private String titleImg;
            private String address;
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
            private String nickname;
            private String id;
            private String lat;

            public void setCountry(String country) {
                this.country = country;
            }

            public void setTitleImg(String titleImg) {
                this.titleImg = titleImg;
            }

            public void setAddress(String address) {
                this.address = address;
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

            public void setNickname(String nickname) {
                this.nickname = nickname;
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

            public String getAddress() {
                return address;
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

            public String getNickname() {
                return nickname;
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