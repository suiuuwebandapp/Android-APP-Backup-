package com.minglang.suiuu.entity;

/**
 *
 * 随友信息
 *
 * Created by Administrator on 2015/6/30.
 */
public class SuiuuUser {


    /**
     * data : {"publisher":{"registerTime":"2015-06-05 15:18:36","idCard":null,"kind":"1","userPublisherId":"49","leadCount":"0","lon":null,"cityId":"341","userId":"e5c42ea7b11d3a4e91bc4fcc4b72cee8","countryId":"336","score":"0","tripCount":"0","idCardImg":"http://image.suiuu.com/user_card/20150605151749_63911.jpg","lat":null,"lastUpdateTime":"2015-06-05 15:18:36"},"user":{"birthday":"0000-00-00","profession":"","cityCname":"宣武","headImg":"http://tp3.sinaimg.cn/2429367174/180/5682258880/1","travelCount":"0","sex":"1","countryEname":"China","lon":null,"cityId":"341","countryId":"336","isPublisher":"1","phone":"18801413431","school":"","userSign":"e5c42ea7b11d3a4e91bc4fcc4b72cee8","intro":"","nickname":"爷们miku","countryCname":"中国","cityEname":"Xuanwu","email":"rodg2351@suiuu.com","lat":null,"hobby":"","info":""}}
     * message :
     * status : 1
     * token : 0690bf411b8dd2ee620db77a40866838
     */
    private SuiuuUserData data;
    private String message;
    private int status;
    private String token;

    public void setData(SuiuuUserData data) {
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

    public SuiuuUserData getData() {
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

    public class SuiuuUserData {
        /**
         * publisher : {"registerTime":"2015-06-05 15:18:36","idCard":null,"kind":"1","userPublisherId":"49","leadCount":"0","lon":null,"cityId":"341","userId":"e5c42ea7b11d3a4e91bc4fcc4b72cee8","countryId":"336","score":"0","tripCount":"0","idCardImg":"http://image.suiuu.com/user_card/20150605151749_63911.jpg","lat":null,"lastUpdateTime":"2015-06-05 15:18:36"}
         * user : {"birthday":"0000-00-00","profession":"","cityCname":"宣武","headImg":"http://tp3.sinaimg.cn/2429367174/180/5682258880/1","travelCount":"0","sex":"1","countryEname":"China","lon":null,"cityId":"341","countryId":"336","isPublisher":"1","phone":"18801413431","school":"","userSign":"e5c42ea7b11d3a4e91bc4fcc4b72cee8","intro":"","nickname":"爷们miku","countryCname":"中国","cityEname":"Xuanwu","email":"rodg2351@suiuu.com","lat":null,"hobby":"","info":""}
         */
        private PublisherEntity publisher;
        private UserEntity user;

        public void setPublisher(PublisherEntity publisher) {
            this.publisher = publisher;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public PublisherEntity getPublisher() {
            return publisher;
        }

        public UserEntity getUser() {
            return user;
        }

        public class PublisherEntity {
            /**
             * registerTime : 2015-06-05 15:18:36
             * idCard : null
             * kind : 1
             * userPublisherId : 49
             * leadCount : 0
             * lon : null
             * cityId : 341
             * userId : e5c42ea7b11d3a4e91bc4fcc4b72cee8
             * countryId : 336
             * score : 0
             * tripCount : 0
             * idCardImg : http://image.suiuu.com/user_card/20150605151749_63911.jpg
             * lat : null
             * lastUpdateTime : 2015-06-05 15:18:36
             */
            private String registerTime;
            private String idCard;
            private String kind;
            private String userPublisherId;
            private String leadCount;
            private String lon;
            private String cityId;
            private String userId;
            private String countryId;
            private String score;
            private String tripCount;
            private String idCardImg;
            private String lat;
            private String lastUpdateTime;

            public void setRegisterTime(String registerTime) {
                this.registerTime = registerTime;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public void setKind(String kind) {
                this.kind = kind;
            }

            public void setUserPublisherId(String userPublisherId) {
                this.userPublisherId = userPublisherId;
            }

            public void setLeadCount(String leadCount) {
                this.leadCount = leadCount;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public void setCountryId(String countryId) {
                this.countryId = countryId;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public void setTripCount(String tripCount) {
                this.tripCount = tripCount;
            }

            public void setIdCardImg(String idCardImg) {
                this.idCardImg = idCardImg;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public void setLastUpdateTime(String lastUpdateTime) {
                this.lastUpdateTime = lastUpdateTime;
            }

            public String getRegisterTime() {
                return registerTime;
            }

            public String getIdCard() {
                return idCard;
            }

            public String getKind() {
                return kind;
            }

            public String getUserPublisherId() {
                return userPublisherId;
            }

            public String getLeadCount() {
                return leadCount;
            }

            public String getLon() {
                return lon;
            }

            public String getCityId() {
                return cityId;
            }

            public String getUserId() {
                return userId;
            }

            public String getCountryId() {
                return countryId;
            }

            public String getScore() {
                return score;
            }

            public String getTripCount() {
                return tripCount;
            }

            public String getIdCardImg() {
                return idCardImg;
            }

            public String getLat() {
                return lat;
            }

            public String getLastUpdateTime() {
                return lastUpdateTime;
            }
        }

        public class UserEntity {
            /**
             * birthday : 0000-00-00
             * profession :
             * cityCname : 宣武
             * headImg : http://tp3.sinaimg.cn/2429367174/180/5682258880/1
             * travelCount : 0
             * sex : 1
             * countryEname : China
             * lon : null
             * cityId : 341
             * countryId : 336
             * isPublisher : 1
             * phone : 18801413431
             * school :
             * userSign : e5c42ea7b11d3a4e91bc4fcc4b72cee8
             * intro :
             * nickname : 爷们miku
             * countryCname : 中国
             * cityEname : Xuanwu
             * email : rodg2351@suiuu.com
             * lat : null
             * hobby :
             * info :
             */
            private String birthday;
            private String profession;
            private String cityCname;
            private String headImg;
            private String travelCount;
            private String sex;
            private String countryEname;
            private String lon;
            private String cityId;
            private String countryId;
            private String isPublisher;
            private String phone;
            private String school;
            private String userSign;
            private String intro;
            private String nickname;
            private String countryCname;
            private String cityEname;
            private String email;
            private String lat;
            private String hobby;
            private String info;

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public void setProfession(String profession) {
                this.profession = profession;
            }

            public void setCityCname(String cityCname) {
                this.cityCname = cityCname;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public void setTravelCount(String travelCount) {
                this.travelCount = travelCount;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public void setCountryEname(String countryEname) {
                this.countryEname = countryEname;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public void setCountryId(String countryId) {
                this.countryId = countryId;
            }

            public void setIsPublisher(String isPublisher) {
                this.isPublisher = isPublisher;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public void setSchool(String school) {
                this.school = school;
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

            public void setCountryCname(String countryCname) {
                this.countryCname = countryCname;
            }

            public void setCityEname(String cityEname) {
                this.cityEname = cityEname;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public void setHobby(String hobby) {
                this.hobby = hobby;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getBirthday() {
                return birthday;
            }

            public String getProfession() {
                return profession;
            }

            public String getCityCname() {
                return cityCname;
            }

            public String getHeadImg() {
                return headImg;
            }

            public String getTravelCount() {
                return travelCount;
            }

            public String getSex() {
                return sex;
            }

            public String getCountryEname() {
                return countryEname;
            }

            public String getLon() {
                return lon;
            }

            public String getCityId() {
                return cityId;
            }

            public String getCountryId() {
                return countryId;
            }

            public String getIsPublisher() {
                return isPublisher;
            }

            public String getPhone() {
                return phone;
            }

            public String getSchool() {
                return school;
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

            public String getCountryCname() {
                return countryCname;
            }

            public String getCityEname() {
                return cityEname;
            }

            public String getEmail() {
                return email;
            }

            public String getLat() {
                return lat;
            }

            public String getHobby() {
                return hobby;
            }

            public String getInfo() {
                return info;
            }
        }
    }
}
