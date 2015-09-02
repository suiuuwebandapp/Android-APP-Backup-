package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/9/2.
 * <p/>
 * 个人中心数据实体类
 */
public class PersonalCenter {

    /**
     * status : 1
     * data : {"userInfo":{"userId":"38","nickname":"dorlen","surname":"","name":"","email":"277646935@qq.com","phone":"15311445352","areaCode":"+86","sex":"0","birthday":"1991-02-14","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","hobby":"","school":"","qq":"","wechat":"","intro":"一座城市，一些岁月，和一个人。","info":"来自西南的小姑娘","travelCount":"0","registerIp":"180.153.206.19","status":"1","registerTime":"2015-05-01 13:46:18","lastLoginTime":"2015-05-01 13:46:18","userSign":"a4c1406ff4cc382389f19bf6ec3e55c1","isPublisher":"1","cityId":"342","countryId":"336","lon":"","lat":"","profession":"业余导游","balance":"0.02","countryCname":"中国","countryEname":"China","cityCname":"朝阳","cityEname":"Chaoyang"},"tripList":[{"tripId":"104","createPublisherId":"11","createTime":"2015-05-13 11:41:49","title":"游东京感受日本三道文化-花道、茶道、书道","titleImg":"http://image.suiuu.com/suiuu_head/20150724021112_79300.png","countryId":"3005","cityId":"3018","lon":null,"lat":null,"basePrice":"1103","oldPrice":"1050.00","basePriceType":"1","maxUserCount":"6","score":"10","tripCount":"3","startTime":"09:00:00","endTime":"19:00:00","travelTime":"10","travelTimeType":"1","intro":"游东京感受日本三道文化-花道、茶道、书道","info":"文化一日游，有内涵、讲深度、有趣味。从庄严地天皇居所，到人文圣地东大，夜晚领略歌舞伎町的歌舞升平，体验菊与刀的纯正日本精神。","tags":"家庭,美食,猎奇,自然,浪漫,购物","commentCount":"1","collectCount":"0","isHot":"0","type":"1","status":"1","nickname":"dorlen","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","count":null,"names":"包车,东京成田空港接机,东京羽田空港","userSign":"a4c1406ff4cc382389f19bf6ec3e55c1"}],"joinList":[{"tripId":"104","createPublisherId":"11","createTime":"2015-05-13 11:41:49","title":"游东京感受日本三道文化-花道、茶道、书道","titleImg":"http://image.suiuu.com/suiuu_head/20150724021112_79300.png","countryId":"3005","cityId":"3018","lon":null,"lat":null,"basePrice":"1103","oldPrice":"1050.00","basePriceType":"1","maxUserCount":"6","score":"10","tripCount":"3","startTime":"09:00:00","endTime":"19:00:00","travelTime":"10","travelTimeType":"1","intro":"游东京感受日本三道文化-花道、茶道、书道","info":"文化一日游，有内涵、讲深度、有趣味。从庄严地天皇居所，到人文圣地东大，夜晚领略歌舞伎町的歌舞升平，体验菊与刀的纯正日本精神。","tags":"家庭,美食,猎奇,自然,浪漫,购物","commentCount":"1","collectCount":"0","isHot":"0","type":"1","status":"1","nickname":"dorlen","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","count":null,"names":"包车,东京成田空港接机,东京羽田空港"}],"commentNumb":"0","commentInfo":[{"commentId":"67","userSign":"a4c1406ff4cc382389f19bf6ec3e55c1","content":"厉害","rTitle":"","replayCommentId":"0","supportCount":"0","opposeCount":"0","cTime":"2015-06-20 23:31:15","tripId":"185","isTravel":"2","rUserSign":null,"title":"香港上环步行--古色古香","rnickname":null,"rheadImg":null,"nickname":"dorlen","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg"}],"userCard":{"cardId":"1","userId":"a4c1406ff4cc382389f19bf6ec3e55c1","name":"","number":"","img":"http://image.suiuu.com/user_card/20150805143429_80912.png","authHistory":"","updateTime":"2015-08-06 10:27:34","status":"0"},"userAptitude":{"aptitudeId":"1","userId":"a4c1406ff4cc382389f19bf6ec3e55c1","applyTime":"2015-08-06 11:30:41","status":"0"},"tpCount":"0","QaCount":"0","collectCount":"4"}
     * message :
     * token : 1660a206ce95183f39d80479d0b9f1f2
     */

    private int status;
    private PersonalCenterData data;
    private String message;
    private String token;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(PersonalCenterData data) {
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

    public PersonalCenterData getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public static class PersonalCenterData {
        /**
         * userInfo : {"userId":"38","nickname":"dorlen","surname":"","name":"","email":"277646935@qq.com","phone":"15311445352","areaCode":"+86","sex":"0","birthday":"1991-02-14","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","hobby":"","school":"","qq":"","wechat":"","intro":"一座城市，一些岁月，和一个人。","info":"来自西南的小姑娘","travelCount":"0","registerIp":"180.153.206.19","status":"1","registerTime":"2015-05-01 13:46:18","lastLoginTime":"2015-05-01 13:46:18","userSign":"a4c1406ff4cc382389f19bf6ec3e55c1","isPublisher":"1","cityId":"342","countryId":"336","lon":"","lat":"","profession":"业余导游","balance":"0.02","countryCname":"中国","countryEname":"China","cityCname":"朝阳","cityEname":"Chaoyang"}
         * tripList : [{"tripId":"104","createPublisherId":"11","createTime":"2015-05-13 11:41:49","title":"游东京感受日本三道文化-花道、茶道、书道","titleImg":"http://image.suiuu.com/suiuu_head/20150724021112_79300.png","countryId":"3005","cityId":"3018","lon":null,"lat":null,"basePrice":"1103","oldPrice":"1050.00","basePriceType":"1","maxUserCount":"6","score":"10","tripCount":"3","startTime":"09:00:00","endTime":"19:00:00","travelTime":"10","travelTimeType":"1","intro":"游东京感受日本三道文化-花道、茶道、书道","info":"文化一日游，有内涵、讲深度、有趣味。从庄严地天皇居所，到人文圣地东大，夜晚领略歌舞伎町的歌舞升平，体验菊与刀的纯正日本精神。","tags":"家庭,美食,猎奇,自然,浪漫,购物","commentCount":"1","collectCount":"0","isHot":"0","type":"1","status":"1","nickname":"dorlen","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","count":null,"names":"包车,东京成田空港接机,东京羽田空港","userSign":"a4c1406ff4cc382389f19bf6ec3e55c1"}]
         * joinList : [{"tripId":"104","createPublisherId":"11","createTime":"2015-05-13 11:41:49","title":"游东京感受日本三道文化-花道、茶道、书道","titleImg":"http://image.suiuu.com/suiuu_head/20150724021112_79300.png","countryId":"3005","cityId":"3018","lon":null,"lat":null,"basePrice":"1103","oldPrice":"1050.00","basePriceType":"1","maxUserCount":"6","score":"10","tripCount":"3","startTime":"09:00:00","endTime":"19:00:00","travelTime":"10","travelTimeType":"1","intro":"游东京感受日本三道文化-花道、茶道、书道","info":"文化一日游，有内涵、讲深度、有趣味。从庄严地天皇居所，到人文圣地东大，夜晚领略歌舞伎町的歌舞升平，体验菊与刀的纯正日本精神。","tags":"家庭,美食,猎奇,自然,浪漫,购物","commentCount":"1","collectCount":"0","isHot":"0","type":"1","status":"1","nickname":"dorlen","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","count":null,"names":"包车,东京成田空港接机,东京羽田空港"}]
         * commentNumb : 0
         * commentInfo : [{"commentId":"67","userSign":"a4c1406ff4cc382389f19bf6ec3e55c1","content":"厉害","rTitle":"","replayCommentId":"0","supportCount":"0","opposeCount":"0","cTime":"2015-06-20 23:31:15","tripId":"185","isTravel":"2","rUserSign":null,"title":"香港上环步行--古色古香","rnickname":null,"rheadImg":null,"nickname":"dorlen","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg"}]
         * userCard : {"cardId":"1","userId":"a4c1406ff4cc382389f19bf6ec3e55c1","name":"","number":"","img":"http://image.suiuu.com/user_card/20150805143429_80912.png","authHistory":"","updateTime":"2015-08-06 10:27:34","status":"0"}
         * userAptitude : {"aptitudeId":"1","userId":"a4c1406ff4cc382389f19bf6ec3e55c1","applyTime":"2015-08-06 11:30:41","status":"0"}
         * tpCount : 0
         * QaCount : 0
         * collectCount : 4
         */

        private UserInfoEntity userInfo;
        private String commentNumb;
        private UserCardEntity userCard;
        private UserAptitudeEntity userAptitude;
        private String tpCount;
        private String QaCount;
        private String collectCount;
        private List<TripListEntity> tripList;
        private List<JoinListEntity> joinList;
        private List<CommentInfoEntity> commentInfo;

        public void setUserInfo(UserInfoEntity userInfo) {
            this.userInfo = userInfo;
        }

        public void setCommentNumb(String commentNumb) {
            this.commentNumb = commentNumb;
        }

        public void setUserCard(UserCardEntity userCard) {
            this.userCard = userCard;
        }

        public void setUserAptitude(UserAptitudeEntity userAptitude) {
            this.userAptitude = userAptitude;
        }

        public void setTpCount(String tpCount) {
            this.tpCount = tpCount;
        }

        public void setQaCount(String QaCount) {
            this.QaCount = QaCount;
        }

        public void setCollectCount(String collectCount) {
            this.collectCount = collectCount;
        }

        public void setTripList(List<TripListEntity> tripList) {
            this.tripList = tripList;
        }

        public void setJoinList(List<JoinListEntity> joinList) {
            this.joinList = joinList;
        }

        public void setCommentInfo(List<CommentInfoEntity> commentInfo) {
            this.commentInfo = commentInfo;
        }

        public UserInfoEntity getUserInfo() {
            return userInfo;
        }

        public String getCommentNumb() {
            return commentNumb;
        }

        public UserCardEntity getUserCard() {
            return userCard;
        }

        public UserAptitudeEntity getUserAptitude() {
            return userAptitude;
        }

        public String getTpCount() {
            return tpCount;
        }

        public String getQaCount() {
            return QaCount;
        }

        public String getCollectCount() {
            return collectCount;
        }

        public List<TripListEntity> getTripList() {
            return tripList;
        }

        public List<JoinListEntity> getJoinList() {
            return joinList;
        }

        public List<CommentInfoEntity> getCommentInfo() {
            return commentInfo;
        }

        public static class UserInfoEntity {
            /**
             * userId : 38
             * nickname : dorlen
             * surname :
             * name :
             * email : 277646935@qq.com
             * phone : 15311445352
             * areaCode : +86
             * sex : 0
             * birthday : 1991-02-14
             * headImg : http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg
             * hobby :
             * school :
             * qq :
             * wechat :
             * intro : 一座城市，一些岁月，和一个人。
             * info : 来自西南的小姑娘
             * travelCount : 0
             * registerIp : 180.153.206.19
             * status : 1
             * registerTime : 2015-05-01 13:46:18
             * lastLoginTime : 2015-05-01 13:46:18
             * userSign : a4c1406ff4cc382389f19bf6ec3e55c1
             * isPublisher : 1
             * cityId : 342
             * countryId : 336
             * lon :
             * lat :
             * profession : 业余导游
             * balance : 0.02
             * countryCname : 中国
             * countryEname : China
             * cityCname : 朝阳
             * cityEname : Chaoyang
             */

            private String userId;
            private String nickname;
            private String surname;
            private String name;
            private String email;
            private String phone;
            private String areaCode;
            private String sex;
            private String birthday;
            private String headImg;
            private String hobby;
            private String school;
            private String qq;
            private String wechat;
            private String intro;
            private String info;
            private String travelCount;
            private String registerIp;
            private String status;
            private String registerTime;
            private String lastLoginTime;
            private String userSign;
            private String isPublisher;
            private String cityId;
            private String countryId;
            private String lon;
            private String lat;
            private String profession;
            private String balance;
            private String countryCname;
            private String countryEname;
            private String cityCname;
            private String cityEname;

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setSurname(String surname) {
                this.surname = surname;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public void setHobby(String hobby) {
                this.hobby = hobby;
            }

            public void setSchool(String school) {
                this.school = school;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public void setWechat(String wechat) {
                this.wechat = wechat;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public void setTravelCount(String travelCount) {
                this.travelCount = travelCount;
            }

            public void setRegisterIp(String registerIp) {
                this.registerIp = registerIp;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setRegisterTime(String registerTime) {
                this.registerTime = registerTime;
            }

            public void setLastLoginTime(String lastLoginTime) {
                this.lastLoginTime = lastLoginTime;
            }

            public void setUserSign(String userSign) {
                this.userSign = userSign;
            }

            public void setIsPublisher(String isPublisher) {
                this.isPublisher = isPublisher;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public void setCountryId(String countryId) {
                this.countryId = countryId;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public void setProfession(String profession) {
                this.profession = profession;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public void setCountryCname(String countryCname) {
                this.countryCname = countryCname;
            }

            public void setCountryEname(String countryEname) {
                this.countryEname = countryEname;
            }

            public void setCityCname(String cityCname) {
                this.cityCname = cityCname;
            }

            public void setCityEname(String cityEname) {
                this.cityEname = cityEname;
            }

            public String getUserId() {
                return userId;
            }

            public String getNickname() {
                return nickname;
            }

            public String getSurname() {
                return surname;
            }

            public String getName() {
                return name;
            }

            public String getEmail() {
                return email;
            }

            public String getPhone() {
                return phone;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public String getSex() {
                return sex;
            }

            public String getBirthday() {
                return birthday;
            }

            public String getHeadImg() {
                return headImg;
            }

            public String getHobby() {
                return hobby;
            }

            public String getSchool() {
                return school;
            }

            public String getQq() {
                return qq;
            }

            public String getWechat() {
                return wechat;
            }

            public String getIntro() {
                return intro;
            }

            public String getInfo() {
                return info;
            }

            public String getTravelCount() {
                return travelCount;
            }

            public String getRegisterIp() {
                return registerIp;
            }

            public String getStatus() {
                return status;
            }

            public String getRegisterTime() {
                return registerTime;
            }

            public String getLastLoginTime() {
                return lastLoginTime;
            }

            public String getUserSign() {
                return userSign;
            }

            public String getIsPublisher() {
                return isPublisher;
            }

            public String getCityId() {
                return cityId;
            }

            public String getCountryId() {
                return countryId;
            }

            public String getLon() {
                return lon;
            }

            public String getLat() {
                return lat;
            }

            public String getProfession() {
                return profession;
            }

            public String getBalance() {
                return balance;
            }

            public String getCountryCname() {
                return countryCname;
            }

            public String getCountryEname() {
                return countryEname;
            }

            public String getCityCname() {
                return cityCname;
            }

            public String getCityEname() {
                return cityEname;
            }
        }

        public static class UserCardEntity {
            /**
             * cardId : 1
             * userId : a4c1406ff4cc382389f19bf6ec3e55c1
             * name :
             * number :
             * img : http://image.suiuu.com/user_card/20150805143429_80912.png
             * authHistory :
             * updateTime : 2015-08-06 10:27:34
             * status : 0
             */

            private String cardId;
            private String userId;
            private String name;
            private String number;
            private String img;
            private String authHistory;
            private String updateTime;
            private String status;

            public void setCardId(String cardId) {
                this.cardId = cardId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public void setAuthHistory(String authHistory) {
                this.authHistory = authHistory;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCardId() {
                return cardId;
            }

            public String getUserId() {
                return userId;
            }

            public String getName() {
                return name;
            }

            public String getNumber() {
                return number;
            }

            public String getImg() {
                return img;
            }

            public String getAuthHistory() {
                return authHistory;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public String getStatus() {
                return status;
            }
        }

        public static class UserAptitudeEntity {
            /**
             * aptitudeId : 1
             * userId : a4c1406ff4cc382389f19bf6ec3e55c1
             * applyTime : 2015-08-06 11:30:41
             * status : 0
             */

            private String aptitudeId;
            private String userId;
            private String applyTime;
            private String status;

            public void setAptitudeId(String aptitudeId) {
                this.aptitudeId = aptitudeId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public void setApplyTime(String applyTime) {
                this.applyTime = applyTime;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAptitudeId() {
                return aptitudeId;
            }

            public String getUserId() {
                return userId;
            }

            public String getApplyTime() {
                return applyTime;
            }

            public String getStatus() {
                return status;
            }
        }

        public static class TripListEntity {
            /**
             * tripId : 104
             * createPublisherId : 11
             * createTime : 2015-05-13 11:41:49
             * title : 游东京感受日本三道文化-花道、茶道、书道
             * titleImg : http://image.suiuu.com/suiuu_head/20150724021112_79300.png
             * countryId : 3005
             * cityId : 3018
             * lon : null
             * lat : null
             * basePrice : 1103
             * oldPrice : 1050.00
             * basePriceType : 1
             * maxUserCount : 6
             * score : 10
             * tripCount : 3
             * startTime : 09:00:00
             * endTime : 19:00:00
             * travelTime : 10
             * travelTimeType : 1
             * intro : 游东京感受日本三道文化-花道、茶道、书道
             * info : 文化一日游，有内涵、讲深度、有趣味。从庄严地天皇居所，到人文圣地东大，夜晚领略歌舞伎町的歌舞升平，体验菊与刀的纯正日本精神。
             * tags : 家庭,美食,猎奇,自然,浪漫,购物
             * commentCount : 1
             * collectCount : 0
             * isHot : 0
             * type : 1
             * status : 1
             * nickname : dorlen
             * headImg : http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg
             * count : null
             * names : 包车,东京成田空港接机,东京羽田空港
             * userSign : a4c1406ff4cc382389f19bf6ec3e55c1
             */

            private String tripId;
            private String createPublisherId;
            private String createTime;
            private String title;
            private String titleImg;
            private String countryId;
            private String cityId;
            private Object lon;
            private Object lat;
            private String basePrice;
            private String oldPrice;
            private String basePriceType;
            private String maxUserCount;
            private String score;
            private String tripCount;
            private String startTime;
            private String endTime;
            private String travelTime;
            private String travelTimeType;
            private String intro;
            private String info;
            private String tags;
            private String commentCount;
            private String collectCount;
            private String isHot;
            private String type;
            private String status;
            private String nickname;
            private String headImg;
            private Object count;
            private String names;
            private String userSign;

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

            public void setCreatePublisherId(String createPublisherId) {
                this.createPublisherId = createPublisherId;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setTitleImg(String titleImg) {
                this.titleImg = titleImg;
            }

            public void setCountryId(String countryId) {
                this.countryId = countryId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public void setLon(Object lon) {
                this.lon = lon;
            }

            public void setLat(Object lat) {
                this.lat = lat;
            }

            public void setBasePrice(String basePrice) {
                this.basePrice = basePrice;
            }

            public void setOldPrice(String oldPrice) {
                this.oldPrice = oldPrice;
            }

            public void setBasePriceType(String basePriceType) {
                this.basePriceType = basePriceType;
            }

            public void setMaxUserCount(String maxUserCount) {
                this.maxUserCount = maxUserCount;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public void setTripCount(String tripCount) {
                this.tripCount = tripCount;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public void setTravelTime(String travelTime) {
                this.travelTime = travelTime;
            }

            public void setTravelTimeType(String travelTimeType) {
                this.travelTimeType = travelTimeType;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public void setCommentCount(String commentCount) {
                this.commentCount = commentCount;
            }

            public void setCollectCount(String collectCount) {
                this.collectCount = collectCount;
            }

            public void setIsHot(String isHot) {
                this.isHot = isHot;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public void setCount(Object count) {
                this.count = count;
            }

            public void setNames(String names) {
                this.names = names;
            }

            public void setUserSign(String userSign) {
                this.userSign = userSign;
            }

            public String getTripId() {
                return tripId;
            }

            public String getCreatePublisherId() {
                return createPublisherId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public String getTitle() {
                return title;
            }

            public String getTitleImg() {
                return titleImg;
            }

            public String getCountryId() {
                return countryId;
            }

            public String getCityId() {
                return cityId;
            }

            public Object getLon() {
                return lon;
            }

            public Object getLat() {
                return lat;
            }

            public String getBasePrice() {
                return basePrice;
            }

            public String getOldPrice() {
                return oldPrice;
            }

            public String getBasePriceType() {
                return basePriceType;
            }

            public String getMaxUserCount() {
                return maxUserCount;
            }

            public String getScore() {
                return score;
            }

            public String getTripCount() {
                return tripCount;
            }

            public String getStartTime() {
                return startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public String getTravelTime() {
                return travelTime;
            }

            public String getTravelTimeType() {
                return travelTimeType;
            }

            public String getIntro() {
                return intro;
            }

            public String getInfo() {
                return info;
            }

            public String getTags() {
                return tags;
            }

            public String getCommentCount() {
                return commentCount;
            }

            public String getCollectCount() {
                return collectCount;
            }

            public String getIsHot() {
                return isHot;
            }

            public String getType() {
                return type;
            }

            public String getStatus() {
                return status;
            }

            public String getNickname() {
                return nickname;
            }

            public String getHeadImg() {
                return headImg;
            }

            public Object getCount() {
                return count;
            }

            public String getNames() {
                return names;
            }

            public String getUserSign() {
                return userSign;
            }
        }

        public static class JoinListEntity {
            /**
             * tripId : 104
             * createPublisherId : 11
             * createTime : 2015-05-13 11:41:49
             * title : 游东京感受日本三道文化-花道、茶道、书道
             * titleImg : http://image.suiuu.com/suiuu_head/20150724021112_79300.png
             * countryId : 3005
             * cityId : 3018
             * lon : null
             * lat : null
             * basePrice : 1103
             * oldPrice : 1050.00
             * basePriceType : 1
             * maxUserCount : 6
             * score : 10
             * tripCount : 3
             * startTime : 09:00:00
             * endTime : 19:00:00
             * travelTime : 10
             * travelTimeType : 1
             * intro : 游东京感受日本三道文化-花道、茶道、书道
             * info : 文化一日游，有内涵、讲深度、有趣味。从庄严地天皇居所，到人文圣地东大，夜晚领略歌舞伎町的歌舞升平，体验菊与刀的纯正日本精神。
             * tags : 家庭,美食,猎奇,自然,浪漫,购物
             * commentCount : 1
             * collectCount : 0
             * isHot : 0
             * type : 1
             * status : 1
             * nickname : dorlen
             * headImg : http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg
             * count : null
             * names : 包车,东京成田空港接机,东京羽田空港
             */

            private String tripId;
            private String createPublisherId;
            private String createTime;
            private String title;
            private String titleImg;
            private String countryId;
            private String cityId;
            private Object lon;
            private Object lat;
            private String basePrice;
            private String oldPrice;
            private String basePriceType;
            private String maxUserCount;
            private String score;
            private String tripCount;
            private String startTime;
            private String endTime;
            private String travelTime;
            private String travelTimeType;
            private String intro;
            private String info;
            private String tags;
            private String commentCount;
            private String collectCount;
            private String isHot;
            private String type;
            private String status;
            private String nickname;
            private String headImg;
            private Object count;
            private String names;

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

            public void setCreatePublisherId(String createPublisherId) {
                this.createPublisherId = createPublisherId;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setTitleImg(String titleImg) {
                this.titleImg = titleImg;
            }

            public void setCountryId(String countryId) {
                this.countryId = countryId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public void setLon(Object lon) {
                this.lon = lon;
            }

            public void setLat(Object lat) {
                this.lat = lat;
            }

            public void setBasePrice(String basePrice) {
                this.basePrice = basePrice;
            }

            public void setOldPrice(String oldPrice) {
                this.oldPrice = oldPrice;
            }

            public void setBasePriceType(String basePriceType) {
                this.basePriceType = basePriceType;
            }

            public void setMaxUserCount(String maxUserCount) {
                this.maxUserCount = maxUserCount;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public void setTripCount(String tripCount) {
                this.tripCount = tripCount;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public void setTravelTime(String travelTime) {
                this.travelTime = travelTime;
            }

            public void setTravelTimeType(String travelTimeType) {
                this.travelTimeType = travelTimeType;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public void setCommentCount(String commentCount) {
                this.commentCount = commentCount;
            }

            public void setCollectCount(String collectCount) {
                this.collectCount = collectCount;
            }

            public void setIsHot(String isHot) {
                this.isHot = isHot;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public void setCount(Object count) {
                this.count = count;
            }

            public void setNames(String names) {
                this.names = names;
            }

            public String getTripId() {
                return tripId;
            }

            public String getCreatePublisherId() {
                return createPublisherId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public String getTitle() {
                return title;
            }

            public String getTitleImg() {
                return titleImg;
            }

            public String getCountryId() {
                return countryId;
            }

            public String getCityId() {
                return cityId;
            }

            public Object getLon() {
                return lon;
            }

            public Object getLat() {
                return lat;
            }

            public String getBasePrice() {
                return basePrice;
            }

            public String getOldPrice() {
                return oldPrice;
            }

            public String getBasePriceType() {
                return basePriceType;
            }

            public String getMaxUserCount() {
                return maxUserCount;
            }

            public String getScore() {
                return score;
            }

            public String getTripCount() {
                return tripCount;
            }

            public String getStartTime() {
                return startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public String getTravelTime() {
                return travelTime;
            }

            public String getTravelTimeType() {
                return travelTimeType;
            }

            public String getIntro() {
                return intro;
            }

            public String getInfo() {
                return info;
            }

            public String getTags() {
                return tags;
            }

            public String getCommentCount() {
                return commentCount;
            }

            public String getCollectCount() {
                return collectCount;
            }

            public String getIsHot() {
                return isHot;
            }

            public String getType() {
                return type;
            }

            public String getStatus() {
                return status;
            }

            public String getNickname() {
                return nickname;
            }

            public String getHeadImg() {
                return headImg;
            }

            public Object getCount() {
                return count;
            }

            public String getNames() {
                return names;
            }
        }

        public static class CommentInfoEntity {
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