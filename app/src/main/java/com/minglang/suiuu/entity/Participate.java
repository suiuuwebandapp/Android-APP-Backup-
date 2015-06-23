package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 参加的随游实体类
 * <p/>
 * Created by Administrator on 2015/6/23.
 */
public class Participate {

    /**
     * data : [{"isAirplane":"0","titleImg":"http://image.suiuu.com/suiuu_trip/50_reset.jpg","tripId":"50","lon":null,"cityId":"3943","title":"资深向导带你玩转威尼斯","countryId":"3884","score":"0","travelTimeType":"1","tripCount":"0","intro":"自驾车:奔驰高级商务轿车7人座viano","createPublisherId":"38","nickname":"蔡金億","maxUserCount":"6","startTime":"09:00:00","lat":null,"basePrice":"2240.00","info":"详情描述：\n1.可以帮忙联系包车；如果去荷兰其他城市需补贴（包）交通费用及食宿；\n2.超出里程：7元/公里（300公里之外）；\n3.超出时间：350元/小时（10小时之外）。\n\n价格包含：\n1.地陪导游；\n2.陪同翻译；\n3.包车费用。\n\n价格不含：\n1.与导游的交通费用；\n2.景点门票等费用；\n3.司机食宿：同餐或食补140元/天，如果去其他城市（非威尼斯本地）宿补210元/天。\n\n退款说明：\n作为用户，您的权益会在随游得到充分保障。\n作为旅行者，您如果选择预订随游产品，可以享受以下的退款政策：\n1.支付并提交订单后48小时无人接单，则订单自动取消，全额返还服务费；\n2.订单提交时间未满48小时，但超过订单预期服务时间的，全额返还服务费；\n3.在订单被接单之前取消订单，全额返还所支付费用；\n4.所提交订单被随友接单，在服务指定日期前5天可以申请取消预订并全额退款；\n5.在指定日期内5天可以申请退款，经平台审核后返还部分预订费用。\n在随游服务过程中及服务后且未确认完成服务前，可以提交退款请求，经平台调查审核后返还部分服务费用。\n\n保险保障：\n1.旅行保险一份100%赔付：\n您和随友旅行过程中如出现意外情况，随友和游客无需承担保险范围内的任何费用，随游网提供的旅行保险全权处理100%赔付。据统计90%以上的游客和随友的相处都非常愉快，如需赔付，您只需要提供现场相关证据照片，在48小时内与随游客服联系，即可享受保险保障。\n2.全天候客服热线：\n您和随友旅行的过程中，如果有任何问题，欢迎随时拨打随游网客服电话或在微信公众号上与客服进行沟通，我们7x24随叫随到，为您服务！","travelTime":"10","headImg":"http://image.suiuu.com/suiuu_head/20150529144852_36504.jpg","count":"1","tags":"家庭,猎奇","names":null,"createTime":"2015-05-01 18:25:16","endTime":"20:00:00","isHotel":"0","basePriceType":"1","status":"1"}]
     * message :
     * status : 1
     * token : 021c4744a4045d8a137d7f5902555287
     */
    private List<ParticipateData> data;
    private String message;
    private int status;
    private String token;

    public void setData(List<ParticipateData> data) {
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

    public List<ParticipateData> getData() {
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

    public class ParticipateData {
        /**
         * isAirplane : 0
         * titleImg : http://image.suiuu.com/suiuu_trip/50_reset.jpg
         * tripId : 50
         * lon : null
         * cityId : 3943
         * title : 资深向导带你玩转威尼斯
         * countryId : 3884
         * score : 0
         * travelTimeType : 1
         * tripCount : 0
         * intro : 自驾车:奔驰高级商务轿车7人座viano
         * createPublisherId : 38
         * nickname : 蔡金億
         * maxUserCount : 6
         * startTime : 09:00:00
         * lat : null
         * basePrice : 2240.00
         * info : 详情描述：
         * 1.可以帮忙联系包车；如果去荷兰其他城市需补贴（包）交通费用及食宿；
         * 2.超出里程：7元/公里（300公里之外）；
         * 3.超出时间：350元/小时（10小时之外）。
         * <p/>
         * 价格包含：
         * 1.地陪导游；
         * 2.陪同翻译；
         * 3.包车费用。
         * <p/>
         * 价格不含：
         * 1.与导游的交通费用；
         * 2.景点门票等费用；
         * 3.司机食宿：同餐或食补140元/天，如果去其他城市（非威尼斯本地）宿补210元/天。
         * <p/>
         * 退款说明：
         * 作为用户，您的权益会在随游得到充分保障。
         * 作为旅行者，您如果选择预订随游产品，可以享受以下的退款政策：
         * 1.支付并提交订单后48小时无人接单，则订单自动取消，全额返还服务费；
         * 2.订单提交时间未满48小时，但超过订单预期服务时间的，全额返还服务费；
         * 3.在订单被接单之前取消订单，全额返还所支付费用；
         * 4.所提交订单被随友接单，在服务指定日期前5天可以申请取消预订并全额退款；
         * 5.在指定日期内5天可以申请退款，经平台审核后返还部分预订费用。
         * 在随游服务过程中及服务后且未确认完成服务前，可以提交退款请求，经平台调查审核后返还部分服务费用。
         * <p/>
         * 保险保障：
         * 1.旅行保险一份100%赔付：
         * 您和随友旅行过程中如出现意外情况，随友和游客无需承担保险范围内的任何费用，随游网提供的旅行保险全权处理100%赔付。据统计90%以上的游客和随友的相处都非常愉快，如需赔付，您只需要提供现场相关证据照片，在48小时内与随游客服联系，即可享受保险保障。
         * 2.全天候客服热线：
         * 您和随友旅行的过程中，如果有任何问题，欢迎随时拨打随游网客服电话或在微信公众号上与客服进行沟通，我们7x24随叫随到，为您服务！
         * travelTime : 10
         * headImg : http://image.suiuu.com/suiuu_head/20150529144852_36504.jpg
         * count : 1
         * tags : 家庭,猎奇
         * names : null
         * createTime : 2015-05-01 18:25:16
         * endTime : 20:00:00
         * isHotel : 0
         * basePriceType : 1
         * status : 1
         */
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
        private String nickname;
        private String maxUserCount;
        private String startTime;
        private String lat;
        private String basePrice;
        private String info;
        private String travelTime;
        private String headImg;
        private String count;
        private String tags;
        private String names;
        private String createTime;
        private String endTime;
        private String isHotel;
        private String basePriceType;
        private String status;

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

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setMaxUserCount(String maxUserCount) {
            this.maxUserCount = maxUserCount;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
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

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public void setNames(String names) {
            this.names = names;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getNickname() {
            return nickname;
        }

        public String getMaxUserCount() {
            return maxUserCount;
        }

        public String getStartTime() {
            return startTime;
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

        public String getHeadImg() {
            return headImg;
        }

        public String getCount() {
            return count;
        }

        public String getTags() {
            return tags;
        }

        public String getNames() {
            return names;
        }

        public String getCreateTime() {
            return createTime;
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
}
