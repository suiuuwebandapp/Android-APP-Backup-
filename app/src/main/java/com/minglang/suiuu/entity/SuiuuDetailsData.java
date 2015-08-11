package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/21 16:55
 * 修改人：Administrator
 * 修改时间：2015/7/21 16:55
 * 修改备注：
 */
public class SuiuuDetailsData {
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
        private List<PublisherListEntity> publisherList;
        private List<ScenicListEntity> scenicList;
        private List<PraiseEntity> praise;
        private List<?> priceList;
        private CreatePublisherInfoEntity createPublisherInfo;
        private List<PicListEntity> picList;
        private List<ServiceListEntity> serviceList;
        private List<IncludeDetailListEntity> includeDetailList;
        private List<AttentionEntity> attention;
        private CommentEntity comment;
        private List<HighlightListEntity> highlightList;
        private InfoEntity info;
        private List<UnIncludeDetailListEntity> unIncludeDetailList;

        public void setPublisherList(List<PublisherListEntity> publisherList) {
            this.publisherList = publisherList;
        }

        public void setScenicList(List<ScenicListEntity> scenicList) {
            this.scenicList = scenicList;
        }

        public void setPraise(List<PraiseEntity> praise) {
            this.praise = praise;
        }

        public void setPriceList(List<?> priceList) {
            this.priceList = priceList;
        }

        public void setCreatePublisherInfo(CreatePublisherInfoEntity createPublisherInfo) {
            this.createPublisherInfo = createPublisherInfo;
        }

        public void setPicList(List<PicListEntity> picList) {
            this.picList = picList;
        }

        public void setServiceList(List<ServiceListEntity> serviceList) {
            this.serviceList = serviceList;
        }

        public void setIncludeDetailList(List<IncludeDetailListEntity> includeDetailList) {
            this.includeDetailList = includeDetailList;
        }

        public void setAttention(List<AttentionEntity> attention) {
            this.attention = attention;
        }

        public void setComment(CommentEntity comment) {
            this.comment = comment;
        }

        public void setHighlightList(List<HighlightListEntity> highlightList) {
            this.highlightList = highlightList;
        }

        public void setInfo(InfoEntity info) {
            this.info = info;
        }

        public void setUnIncludeDetailList(List<UnIncludeDetailListEntity> unIncludeDetailList) {
            this.unIncludeDetailList = unIncludeDetailList;
        }

        public List<PublisherListEntity> getPublisherList() {
            return publisherList;
        }

        public List<ScenicListEntity> getScenicList() {
            return scenicList;
        }

        public List<PraiseEntity> getPraise() {
            return praise;
        }

        public List<?> getPriceList() {
            return priceList;
        }

        public CreatePublisherInfoEntity getCreatePublisherInfo() {
            return createPublisherInfo;
        }

        public List<PicListEntity> getPicList() {
            return picList;
        }

        public List<ServiceListEntity> getServiceList() {
            return serviceList;
        }

        public List<IncludeDetailListEntity> getIncludeDetailList() {
            return includeDetailList;
        }

        public List<AttentionEntity> getAttention() {
            return attention;
        }

        public CommentEntity getComment() {
            return comment;
        }

        public List<HighlightListEntity> getHighlightList() {
            return highlightList;
        }

        public InfoEntity getInfo() {
            return info;
        }

        public List<UnIncludeDetailListEntity> getUnIncludeDetailList() {
            return unIncludeDetailList;
        }

        public class PublisherListEntity {
            /**
             * birthday : 0000-00-00
             * profession :
             * headImg : http://www.suiuu.com/assets/images/user_default.png
             * travelCount : 0
             * tripPublisherId : 42
             * sex : 2
             * tripId : 44
             * cityId : null
             * countryId : null
             * areaCode : null
             * publisherId : 26
             * cityName : null
             * phone : null
             * school :
             * userSign : 0c73410ed4033cf8b32c4ab1f2e86aaa
             * intro :
             * nickname : 历伟
             * countryName : null
             * email : weili9963@hotmail.com
             * hobby :
             * info :
             */
            private String birthday;
            private String profession;
            private String headImg;
            private String travelCount;
            private String tripPublisherId;
            private String sex;
            private String tripId;
            private String cityId;
            private String countryId;
            private String areaCode;
            private String publisherId;
            private String cityName;
            private String phone;
            private String school;
            private String userSign;
            private String intro;
            private String nickname;
            private String countryName;
            private String email;
            private String hobby;
            private String info;

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public void setProfession(String profession) {
                this.profession = profession;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public void setTravelCount(String travelCount) {
                this.travelCount = travelCount;
            }

            public void setTripPublisherId(String tripPublisherId) {
                this.tripPublisherId = tripPublisherId;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public void setCountryId(String countryId) {
                this.countryId = countryId;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public void setPublisherId(String publisherId) {
                this.publisherId = publisherId;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
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

            public void setCountryName(String countryName) {
                this.countryName = countryName;
            }

            public void setEmail(String email) {
                this.email = email;
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

            public String getHeadImg() {
                return headImg;
            }

            public String getTravelCount() {
                return travelCount;
            }

            public String getTripPublisherId() {
                return tripPublisherId;
            }

            public String getSex() {
                return sex;
            }

            public String getTripId() {
                return tripId;
            }

            public String getCityId() {
                return cityId;
            }

            public String getCountryId() {
                return countryId;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public String getPublisherId() {
                return publisherId;
            }

            public String getCityName() {
                return cityName;
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

            public String getCountryName() {
                return countryName;
            }

            public String getEmail() {
                return email;
            }

            public String getHobby() {
                return hobby;
            }

            public String getInfo() {
                return info;
            }
        }

        public class ScenicListEntity {
            /**
             * scenicId : 1596
             * name : 维也纳
             * tripId : 44
             * lon : 16.3738189
             * lat : 48.2081743
             */
            private String scenicId;
            private String name;
            private String tripId;
            private String lon;
            private String lat;

            public void setScenicId(String scenicId) {
                this.scenicId = scenicId;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getScenicId() {
                return scenicId;
            }

            public String getName() {
                return name;
            }

            public String getTripId() {
                return tripId;
            }

            public String getLon() {
                return lon;
            }

            public String getLat() {
                return lat;
            }
        }

        public class PraiseEntity {
            /**
             * addTime : 2015-06-08 11:33:04
             * userSign : 22e64460c94f3b44bc0a8790ed146d3b
             * relativeId : 44
             * attentionId : 162
             * relativeType : 11
             * status : 1
             */
            private String addTime;
            private String userSign;
            private String relativeId;
            private String attentionId;
            private String relativeType;
            private String status;

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public void setUserSign(String userSign) {
                this.userSign = userSign;
            }

            public void setRelativeId(String relativeId) {
                this.relativeId = relativeId;
            }

            public void setAttentionId(String attentionId) {
                this.attentionId = attentionId;
            }

            public void setRelativeType(String relativeType) {
                this.relativeType = relativeType;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAddTime() {
                return addTime;
            }

            public String getUserSign() {
                return userSign;
            }

            public String getRelativeId() {
                return relativeId;
            }

            public String getAttentionId() {
                return attentionId;
            }

            public String getRelativeType() {
                return relativeType;
            }

            public String getStatus() {
                return status;
            }
        }

        public class CreatePublisherInfoEntity {
            /**
             * birthday : 0000-00-00
             * profession :
             * headImg : http://www.suiuu.com/assets/images/user_default.png
             * travelCount : 0
             * tripPublisherId : 42
             * sex : 2
             * tripId : 44
             * cityId : null
             * countryId : null
             * areaCode : null
             * publisherId : 26
             * cityName : null
             * phone : null
             * school :
             * userSign : 0c73410ed4033cf8b32c4ab1f2e86aaa
             * intro :
             * nickname : 历伟
             * countryName : null
             * email : weili9963@hotmail.com
             * hobby :
             * info :
             */
            private String birthday;
            private String profession;
            private String headImg;
            private String travelCount;
            private String tripPublisherId;
            private String sex;
            private String tripId;
            private String cityId;
            private String countryId;
            private String areaCode;
            private String publisherId;
            private String cityName;
            private String phone;
            private String school;
            private String userSign;
            private String intro;
            private String nickname;
            private String countryName;
            private String email;
            private String hobby;
            private String info;

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public void setProfession(String profession) {
                this.profession = profession;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public void setTravelCount(String travelCount) {
                this.travelCount = travelCount;
            }

            public void setTripPublisherId(String tripPublisherId) {
                this.tripPublisherId = tripPublisherId;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public void setCountryId(String countryId) {
                this.countryId = countryId;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public void setPublisherId(String publisherId) {
                this.publisherId = publisherId;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
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

            public void setCountryName(String countryName) {
                this.countryName = countryName;
            }

            public void setEmail(String email) {
                this.email = email;
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

            public String getHeadImg() {
                return headImg;
            }

            public String getTravelCount() {
                return travelCount;
            }

            public String getTripPublisherId() {
                return tripPublisherId;
            }

            public String getSex() {
                return sex;
            }

            public String getTripId() {
                return tripId;
            }

            public String getCityId() {
                return cityId;
            }

            public String getCountryId() {
                return countryId;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public String getPublisherId() {
                return publisherId;
            }

            public String getCityName() {
                return cityName;
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

            public String getCountryName() {
                return countryName;
            }

            public String getEmail() {
                return email;
            }

            public String getHobby() {
                return hobby;
            }

            public String getInfo() {
                return info;
            }
        }

        public class PicListEntity {
            /**
             * tripId : 44
             * title : null
             * picId : 3287
             * url : http://image.suiuu.com/suiuu_head/20150610092047_19915.jpg
             */
            private String tripId;
            private String title;
            private String picId;
            private String url;

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setPicId(String picId) {
                this.picId = picId;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getTripId() {
                return tripId;
            }

            public String getTitle() {
                return title;
            }

            public String getPicId() {
                return picId;
            }

            public String getUrl() {
                return url;
            }
        }

        public class ServiceListEntity {
            /**
             * money : 280.00
             * tripId : 44
             * serviceId : 463
             * title : 接机-奔驰9座轿车
             * type : 0
             */
            private String money;
            private String tripId;
            private String serviceId;
            private String title;
            private String type;

            public void setMoney(String money) {
                this.money = money;
            }

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

            public void setServiceId(String serviceId) {
                this.serviceId = serviceId;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getMoney() {
                return money;
            }

            public String getTripId() {
                return tripId;
            }

            public String getServiceId() {
                return serviceId;
            }

            public String getTitle() {
                return title;
            }

            public String getType() {
                return type;
            }
        }

        public class IncludeDetailListEntity {
            /**
             * name : 导游费用
             * detailId : 1446
             * tripId : 44
             * type : 1
             */
            private String name;
            private String detailId;
            private String tripId;
            private String type;

            public void setName(String name) {
                this.name = name;
            }

            public void setDetailId(String detailId) {
                this.detailId = detailId;
            }

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public String getDetailId() {
                return detailId;
            }

            public String getTripId() {
                return tripId;
            }

            public String getType() {
                return type;
            }
        }

        public class AttentionEntity {
            /**
             * addTime : 2015-07-22 09:41:51
             * userSign : 22e64460c94f3b44bc0a8790ed146d3b
             * relativeId : 44
             * attentionId : 214
             * relativeType : 6
             * status : 1
             */
            private String addTime;
            private String userSign;
            private String relativeId;
            private String attentionId;
            private String relativeType;
            private String status;

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public void setUserSign(String userSign) {
                this.userSign = userSign;
            }

            public void setRelativeId(String relativeId) {
                this.relativeId = relativeId;
            }

            public void setAttentionId(String attentionId) {
                this.attentionId = attentionId;
            }

            public void setRelativeType(String relativeType) {
                this.relativeType = relativeType;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAddTime() {
                return addTime;
            }

            public String getUserSign() {
                return userSign;
            }

            public String getRelativeId() {
                return relativeId;
            }

            public String getAttentionId() {
                return attentionId;
            }

            public String getRelativeType() {
                return relativeType;
            }

            public String getStatus() {
                return status;
            }
        }

        public class CommentEntity {
            /**
             * msg : {"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"0"}
             * data : []
             */
            private MsgEntity msg;
            private List<CommentDataEntity> data;

            public void setMsg(MsgEntity msg) {
                this.msg = msg;
            }

            public void setData(List<CommentDataEntity> data) {
                this.data = data;
            }

            public MsgEntity getMsg() {
                return msg;
            }

            public List<CommentDataEntity> getData() {
                return data;
            }

            public class MsgEntity {
                /**
                 * sortName : null
                 * showAll : false
                 * startRow : 0
                 * sortType : asc
                 * pageSize : 10
                 * currentPage : 1
                 * totalCount : 0
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
            public class CommentDataEntity {
                /**
                 * headImg : http://www.suiuu.com/assets/images/user_default.png
                 * userSign : 22e64460c94f3b44bc0a8790ed146d3b
                 * travelCount : null
                 * isTravel : 2
                 * nickname : 15111993537
                 * commentId : 68
                 * content : jjkkd
                 * rTitle : null
                 * status : null
                 */
                private String headImg;
                private String userSign;
                private String travelCount;
                private String isTravel;
                private String nickname;
                private String commentId;
                private String content;
                private String rTitle;
                private String status;

                public void setHeadImg(String headImg) {
                    this.headImg = headImg;
                }

                public void setUserSign(String userSign) {
                    this.userSign = userSign;
                }

                public void setTravelCount(String travelCount) {
                    this.travelCount = travelCount;
                }

                public void setIsTravel(String isTravel) {
                    this.isTravel = isTravel;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public void setCommentId(String commentId) {
                    this.commentId = commentId;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public void setRTitle(String rTitle) {
                    this.rTitle = rTitle;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getHeadImg() {
                    return headImg;
                }

                public String getUserSign() {
                    return userSign;
                }

                public String getTravelCount() {
                    return travelCount;
                }

                public String getIsTravel() {
                    return isTravel;
                }

                public String getNickname() {
                    return nickname;
                }

                public String getCommentId() {
                    return commentId;
                }

                public String getContent() {
                    return content;
                }

                public String getRTitle() {
                    return rTitle;
                }

                public String getStatus() {
                    return status;
                }
            }
        }

        public class HighlightListEntity {
            /**
             * tripId : 44
             * value : 音乐之都
             * hlId : 387
             */
            private String tripId;
            private String value;
            private String hlId;

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public void setHlId(String hlId) {
                this.hlId = hlId;
            }

            public String getTripId() {
                return tripId;
            }

            public String getValue() {
                return value;
            }

            public String getHlId() {
                return hlId;
            }
        }

        public class InfoEntity {
            /**
             * cityCname : 维也纳
             * isAirplane : 0
             * titleImg : http://image.suiuu.com/suiuu_trip/44_reset.jpg
             * tripId : 44
             * lon : null
             * cityId : 1080
             * title : 【包车】维也纳之恋
             * countryId : 1072
             * score : 0
             * travelTimeType : 1
             * tripCount : 0
             * intro : 【包车】维也纳之恋
             * createPublisherId : 26
             * maxUserCount : 8
             * startTime : 09:00:00
             * cityEname : Vienna
             * lat : null
             * basePrice : 2058.00
             * info : 详情描述：
             我是奥地利维也纳欧洲地接导游领队（欧盟官方导游证件编号.ChL219AA）和小团司机兼导游（营运证号码.51193/2006）厉伟，有十几年的欧洲旅游包车导游，带旅行团经验！
             可提供奥地利和欧洲其他地方.德国，意大利，瑞士，法国，荷比卢，捷克，匈牙利，斯洛伐克，斯洛文尼亚等国多日游，包车旅游导游服务。
             车型：可选择德国欧宝7座车或奔驰9座车。
             * travelTime : 10
             * countryEname : Austria
             * tags : 家庭,猎奇
             * createTime : 2015-05-01 16:54:49
             * countryCname : 奥地利
             * endTime : 19:00:00
             * isHotel : 0
             * basePriceType : 2
             * status : 1
             */
            private String cityCname;
            private String isAirplane;
            private String titleImg;
            private String tripId;
            private String lon;
            private String cityId;
            private String title;
            private String countryId;
            private String score;
            private String travelTimeType;
            private String tripCount;
            private String intro;
            private String createPublisherId;
            private String maxUserCount;
            private String startTime;
            private String cityEname;
            private String lat;
            private String basePrice;
            private String info;
            private String travelTime;
            private String countryEname;
            private String tags;
            private String createTime;
            private String countryCname;
            private String endTime;
            private String isHotel;
            private String basePriceType;
            private String status;

            public void setCityCname(String cityCname) {
                this.cityCname = cityCname;
            }

            public void setIsAirplane(String isAirplane) {
                this.isAirplane = isAirplane;
            }

            public void setTitleImg(String titleImg) {
                this.titleImg = titleImg;
            }

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setCountryId(String countryId) {
                this.countryId = countryId;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public void setTravelTimeType(String travelTimeType) {
                this.travelTimeType = travelTimeType;
            }

            public void setTripCount(String tripCount) {
                this.tripCount = tripCount;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public void setCreatePublisherId(String createPublisherId) {
                this.createPublisherId = createPublisherId;
            }

            public void setMaxUserCount(String maxUserCount) {
                this.maxUserCount = maxUserCount;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public void setCityEname(String cityEname) {
                this.cityEname = cityEname;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public void setBasePrice(String basePrice) {
                this.basePrice = basePrice;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public void setTravelTime(String travelTime) {
                this.travelTime = travelTime;
            }

            public void setCountryEname(String countryEname) {
                this.countryEname = countryEname;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setCountryCname(String countryCname) {
                this.countryCname = countryCname;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public void setIsHotel(String isHotel) {
                this.isHotel = isHotel;
            }

            public void setBasePriceType(String basePriceType) {
                this.basePriceType = basePriceType;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCityCname() {
                return cityCname;
            }

            public String getIsAirplane() {
                return isAirplane;
            }

            public String getTitleImg() {
                return titleImg;
            }

            public String getTripId() {
                return tripId;
            }

            public String getLon() {
                return lon;
            }

            public String getCityId() {
                return cityId;
            }

            public String getTitle() {
                return title;
            }

            public String getCountryId() {
                return countryId;
            }

            public String getScore() {
                return score;
            }

            public String getTravelTimeType() {
                return travelTimeType;
            }

            public String getTripCount() {
                return tripCount;
            }

            public String getIntro() {
                return intro;
            }

            public String getCreatePublisherId() {
                return createPublisherId;
            }

            public String getMaxUserCount() {
                return maxUserCount;
            }

            public String getStartTime() {
                return startTime;
            }

            public String getCityEname() {
                return cityEname;
            }

            public String getLat() {
                return lat;
            }

            public String getBasePrice() {
                return basePrice;
            }

            public String getInfo() {
                return info;
            }

            public String getTravelTime() {
                return travelTime;
            }

            public String getCountryEname() {
                return countryEname;
            }

            public String getTags() {
                return tags;
            }

            public String getCreateTime() {
                return createTime;
            }

            public String getCountryCname() {
                return countryCname;
            }

            public String getEndTime() {
                return endTime;
            }

            public String getIsHotel() {
                return isHotel;
            }

            public String getBasePriceType() {
                return basePriceType;
            }

            public String getStatus() {
                return status;
            }
        }

        public class UnIncludeDetailListEntity {
            /**
             * name : 餐饮费用
             * detailId : 1449
             * tripId : 44
             * type : 2
             */
            private String name;
            private String detailId;
            private String tripId;
            private String type;

            public void setName(String name) {
                this.name = name;
            }

            public void setDetailId(String detailId) {
                this.detailId = detailId;
            }

            public void setTripId(String tripId) {
                this.tripId = tripId;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public String getDetailId() {
                return detailId;
            }

            public String getTripId() {
                return tripId;
            }

            public String getType() {
                return type;
            }
        }
    }
}
