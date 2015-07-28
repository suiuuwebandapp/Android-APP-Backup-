package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/7/27.
 * <p/>
 * 普通用户 已完成的订单数据实体类
 */
public class CompletedOrder {

    /**
     * data : [{"birthday":null,"isComment":null,"orderNumber":"2015062698505050","orderId":"33","totalPrice":"500.00","travelCount":null,"serviceInfo":"[]","tripId":"133","school":null,"intro":null,"nickname":null,"startTime":"07:50:00","email":null,"basePrice":"500.00","info":null,"profession":null,"headImg":null,"sex":null,"userId":"a4c1406ff4cc382389f19bf6ec3e55c1","personCount":"1","beginDate":"2015-06-27","areaCode":null,"servicePrice":"0.00","createTime":"2015-06-26 13:47:55","phone":null,"userSign":null,"isDel":"0","tripJsonInfo":"{\"praise\":[],\"attention\":[],\"info\":{\"tripId\":\"133\",\"createPublisherId\":\"17\",\"createTime\":\"2015-06-07 17:36:33\",\"title\":\"\\u5927\\u5b66\\u535a\\u7269\\u9986\\u6559\\u80b2\\u6e38\",\"titleImg\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150607093224_46657_reset.jpg\",\"countryId\":\"3759\",\"cityId\":\"4150\",\"lon\":null,\"lat\":null,\"basePrice\":\"500.00\",\"basePriceType\":\"2\",\"maxUserCount\":\"10\",\"isAirplane\":\"0\",\"isHotel\":\"0\",\"score\":\"0\",\"tripCount\":\"0\",\"startTime\":\"09:00:00\",\"endTime\":\"19:00:00\",\"travelTime\":\"10\",\"travelTimeType\":\"1\",\"intro\":\"\\u5927\\u5b66\\u535a\\u7269\\u9986\\u6559\\u80b2\\u6e38\",\"info\":\"\\u522b\\u4ee5\\u4e3a\\u65b0\\u52a0\\u5761\\u53ea\\u6709\\u8d2d\\u7269\\u5546\\u573a\\u548c\\u6e38\\u4e50\\u56ed\\uff0c\\u8fd9\\u91cc\\u4e5f\\u662f\\u535a\\u7269\\u9986\\u7684\\u805a\\u96c6\\u5730\\u3002\\u4eab\\u7528\\u8fc7\\u4e00\\u987f\\u7f8e\\u7f8e\\u7684\\u65b0\\u52a0\\u5761\\u5f0f\\u65e9\\u9910\\u4e4b\\u540e\\uff0c\\u6211\\u4eec\\u5c31\\u53ef\\u4ee5\\u5f9c\\u5f89\\u5728\\u77e5\\u8bc6\\u7684\\u6d77\\u6d0b\\u5566\\u3002\\n\\u65b0\\u52a0\\u5761\\u56fd\\u7acb\\u5927\\u5b66\\uff0c\\u56fd\\u5bb6\\u535a\\u7269\\u9986\\uff0c\\u79d1\\u5b66\\u827a\\u672f\\u9986\\uff0c\\u56fd\\u5bb6\\u97f3\\u4e50\\u5385\\uff0c\\u571f\\u751f\\u534e\\u4eba\\u535a\\u7269\\u9986\\u3002\\n\\u65b0\\u52a0\\u5761\\u53ef\\u662f\\u6253\\u5f00\\u5927\\u95e8\\u6b22\\u8fce\\u60a8\\u54e6~~\",\"tags\":\"\\u5bb6\\u5ead,\\u535a\\u7269\\u9986\",\"status\":\"1\",\"countryCname\":\"\\u65b0\\u52a0\\u5761\",\"countryEname\":\"Singapore\",\"cityCname\":\"\\u65b0\\u52a0\\u5761\\u5e02\",\"cityEname\":\"Singapore City\"},\"picList\":[{\"picId\":\"3047\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609230056_83318.jpg\"},{\"picId\":\"3048\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609230017_91416.jpg\"},{\"picId\":\"3049\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609230006_12679.jpg\"},{\"picId\":\"3050\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609225942_82930.jpg\"},{\"picId\":\"3051\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609230001_26552.jpg\"},{\"picId\":\"3052\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609225917_48328.jpg\"}],\"priceList\":[],\"publisherList\":[{\"nickname\":\"emma*****gmail.com\",\"phone\":\"93811856\",\"areaCode\":\"+65\",\"email\":\"emmaxia23@gmail.com\",\"sex\":\"2\",\"birthday\":\"0000-00-00\",\"headImg\":\"http:\\/\\/www.suiuu.com\\/assets\\/images\\/user_default.png\",\"hobby\":\"\",\"profession\":\"\",\"school\":\"\",\"intro\":\"\",\"info\":\"\",\"travelCount\":\"0\",\"userSign\":\"aa605a0e46de32d3968137d0f54d6684\",\"tripPublisherId\":\"146\",\"tripId\":\"133\",\"publisherId\":\"17\"}],\"scenicList\":[{\"scenicId\":\"1558\",\"tripId\":\"133\",\"name\":\"\\u65b0\\u52a0\\u5761\\u56fd\\u5bb6\\u535a\\u7269\\u9986\",\"lon\":\"103.819836\",\"lat\":\"1.352083\"}],\"serviceList\":[],\"highlightList\":[],\"includeDetailList\":[{\"detailId\":\"1201\",\"tripId\":\"133\",\"name\":\"\\u966a\\u540c\\u8bb2\\u89e3\",\"type\":\"1\"},{\"detailId\":\"1202\",\"tripId\":\"133\",\"name\":\"\\u968f\\u884c\\u7ffb\\u8bd1\",\"type\":\"1\"},{\"detailId\":\"1203\",\"tripId\":\"133\",\"name\":\"\\u5f53\\u5730\\u4eba\\u4ea4\\u901a\\u8d39\\u7528\",\"type\":\"1\"}],\"unIncludeDetailList\":[{\"detailId\":\"1204\",\"tripId\":\"133\",\"name\":\"\\u95e8\\u7968\\u8d39\\u7528\",\"type\":\"2\"},{\"detailId\":\"1205\",\"tripId\":\"133\",\"name\":\"\\u9910\\u996e\\u8d39\\u7528\",\"type\":\"2\"},{\"detailId\":\"1206\",\"tripId\":\"133\",\"name\":\"\\u4f4f\\u5bbf\",\"type\":\"2\"}],\"createPublisherInfo\":{\"nickname\":\"emma*****gmail.com\",\"phone\":\"93811856\",\"areaCode\":\"+65\",\"email\":\"emmaxia23@gmail.com\",\"sex\":\"2\",\"birthday\":\"0000-00-00\",\"headImg\":\"http:\\/\\/www.suiuu.com\\/assets\\/images\\/user_default.png\",\"hobby\":\"\",\"profession\":\"\",\"school\":\"\",\"intro\":\"\",\"info\":\"\",\"travelCount\":\"0\",\"userSign\":\"aa605a0e46de32d3968137d0f54d6684\",\"tripPublisherId\":\"146\",\"tripId\":\"133\",\"publisherId\":\"17\"}}","status":"3","hobby":null},{"birthday":null,"isComment":null,"orderNumber":"2015062910199485","orderId":"39","totalPrice":"500.00","travelCount":null,"serviceInfo":"[]","tripId":"133","school":null,"intro":null,"nickname":null,"startTime":"08:05:00","email":null,"basePrice":"500.00","info":null,"profession":null,"headImg":null,"sex":null,"userId":"a4c1406ff4cc382389f19bf6ec3e55c1","personCount":"6","beginDate":"2015-07-11","areaCode":null,"servicePrice":"0.00","createTime":"2015-06-29 16:04:14","phone":null,"userSign":null,"isDel":"0","tripJsonInfo":"{\"praise\":[],\"attention\":[],\"info\":{\"tripId\":\"133\",\"createPublisherId\":\"17\",\"createTime\":\"2015-06-07 17:36:33\",\"title\":\"\\u5927\\u5b66\\u535a\\u7269\\u9986\\u6559\\u80b2\\u6e38\",\"titleImg\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150607093224_46657_reset.jpg\",\"countryId\":\"3759\",\"cityId\":\"4150\",\"lon\":null,\"lat\":null,\"basePrice\":\"500.00\",\"basePriceType\":\"2\",\"maxUserCount\":\"10\",\"isAirplane\":\"0\",\"isHotel\":\"0\",\"score\":\"0\",\"tripCount\":\"0\",\"startTime\":\"09:00:00\",\"endTime\":\"19:00:00\",\"travelTime\":\"10\",\"travelTimeType\":\"1\",\"intro\":\"\\u5927\\u5b66\\u535a\\u7269\\u9986\\u6559\\u80b2\\u6e38\",\"info\":\"\\u522b\\u4ee5\\u4e3a\\u65b0\\u52a0\\u5761\\u53ea\\u6709\\u8d2d\\u7269\\u5546\\u573a\\u548c\\u6e38\\u4e50\\u56ed\\uff0c\\u8fd9\\u91cc\\u4e5f\\u662f\\u535a\\u7269\\u9986\\u7684\\u805a\\u96c6\\u5730\\u3002\\u4eab\\u7528\\u8fc7\\u4e00\\u987f\\u7f8e\\u7f8e\\u7684\\u65b0\\u52a0\\u5761\\u5f0f\\u65e9\\u9910\\u4e4b\\u540e\\uff0c\\u6211\\u4eec\\u5c31\\u53ef\\u4ee5\\u5f9c\\u5f89\\u5728\\u77e5\\u8bc6\\u7684\\u6d77\\u6d0b\\u5566\\u3002\\n\\u65b0\\u52a0\\u5761\\u56fd\\u7acb\\u5927\\u5b66\\uff0c\\u56fd\\u5bb6\\u535a\\u7269\\u9986\\uff0c\\u79d1\\u5b66\\u827a\\u672f\\u9986\\uff0c\\u56fd\\u5bb6\\u97f3\\u4e50\\u5385\\uff0c\\u571f\\u751f\\u534e\\u4eba\\u535a\\u7269\\u9986\\u3002\\n\\u65b0\\u52a0\\u5761\\u53ef\\u662f\\u6253\\u5f00\\u5927\\u95e8\\u6b22\\u8fce\\u60a8\\u54e6~~\",\"tags\":\"\\u5bb6\\u5ead,\\u535a\\u7269\\u9986\",\"status\":\"1\",\"countryCname\":\"\\u65b0\\u52a0\\u5761\",\"countryEname\":\"Singapore\",\"cityCname\":\"\\u65b0\\u52a0\\u5761\\u5e02\",\"cityEname\":\"Singapore City\"},\"picList\":[{\"picId\":\"3047\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609230056_83318.jpg\"},{\"picId\":\"3048\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609230017_91416.jpg\"},{\"picId\":\"3049\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609230006_12679.jpg\"},{\"picId\":\"3050\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609225942_82930.jpg\"},{\"picId\":\"3051\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609230001_26552.jpg\"},{\"picId\":\"3052\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609225917_48328.jpg\"}],\"priceList\":[],\"publisherList\":[{\"nickname\":\"emma*****gmail.com\",\"phone\":\"93811856\",\"areaCode\":\"+65\",\"email\":\"emmaxia23@gmail.com\",\"sex\":\"2\",\"birthday\":\"0000-00-00\",\"headImg\":\"http:\\/\\/www.suiuu.com\\/assets\\/images\\/user_default.png\",\"hobby\":\"\",\"profession\":\"\",\"school\":\"\",\"intro\":\"\",\"info\":\"\",\"travelCount\":\"0\",\"userSign\":\"aa605a0e46de32d3968137d0f54d6684\",\"tripPublisherId\":\"146\",\"tripId\":\"133\",\"publisherId\":\"17\"}],\"scenicList\":[{\"scenicId\":\"1558\",\"tripId\":\"133\",\"name\":\"\\u65b0\\u52a0\\u5761\\u56fd\\u5bb6\\u535a\\u7269\\u9986\",\"lon\":\"103.819836\",\"lat\":\"1.352083\"}],\"serviceList\":[],\"highlightList\":[],\"includeDetailList\":[{\"detailId\":\"1201\",\"tripId\":\"133\",\"name\":\"\\u966a\\u540c\\u8bb2\\u89e3\",\"type\":\"1\"},{\"detailId\":\"1202\",\"tripId\":\"133\",\"name\":\"\\u968f\\u884c\\u7ffb\\u8bd1\",\"type\":\"1\"},{\"detailId\":\"1203\",\"tripId\":\"133\",\"name\":\"\\u5f53\\u5730\\u4eba\\u4ea4\\u901a\\u8d39\\u7528\",\"type\":\"1\"}],\"unIncludeDetailList\":[{\"detailId\":\"1204\",\"tripId\":\"133\",\"name\":\"\\u95e8\\u7968\\u8d39\\u7528\",\"type\":\"2\"},{\"detailId\":\"1205\",\"tripId\":\"133\",\"name\":\"\\u9910\\u996e\\u8d39\\u7528\",\"type\":\"2\"},{\"detailId\":\"1206\",\"tripId\":\"133\",\"name\":\"\\u4f4f\\u5bbf\",\"type\":\"2\"}],\"createPublisherInfo\":{\"nickname\":\"emma*****gmail.com\",\"phone\":\"93811856\",\"areaCode\":\"+65\",\"email\":\"emmaxia23@gmail.com\",\"sex\":\"2\",\"birthday\":\"0000-00-00\",\"headImg\":\"http:\\/\\/www.suiuu.com\\/assets\\/images\\/user_default.png\",\"hobby\":\"\",\"profession\":\"\",\"school\":\"\",\"intro\":\"\",\"info\":\"\",\"travelCount\":\"0\",\"userSign\":\"aa605a0e46de32d3968137d0f54d6684\",\"tripPublisherId\":\"146\",\"tripId\":\"133\",\"publisherId\":\"17\"}}","status":"3","hobby":null},{"birthday":null,"isComment":null,"orderNumber":"2015062953505653","orderId":"40","totalPrice":"500.00","travelCount":null,"serviceInfo":"[]","tripId":"133","school":null,"intro":null,"nickname":null,"startTime":"15:06:00","email":null,"basePrice":"500.00","info":null,"profession":null,"headImg":null,"sex":null,"userId":"a4c1406ff4cc382389f19bf6ec3e55c1","personCount":"6","beginDate":"2015-07-11","areaCode":null,"servicePrice":"0.00","createTime":"2015-06-29 16:04:37","phone":null,"userSign":null,"isDel":"0","tripJsonInfo":"{\"praise\":[],\"attention\":[],\"info\":{\"tripId\":\"133\",\"createPublisherId\":\"17\",\"createTime\":\"2015-06-07 17:36:33\",\"title\":\"\\u5927\\u5b66\\u535a\\u7269\\u9986\\u6559\\u80b2\\u6e38\",\"titleImg\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150607093224_46657_reset.jpg\",\"countryId\":\"3759\",\"cityId\":\"4150\",\"lon\":null,\"lat\":null,\"basePrice\":\"500.00\",\"basePriceType\":\"2\",\"maxUserCount\":\"10\",\"isAirplane\":\"0\",\"isHotel\":\"0\",\"score\":\"0\",\"tripCount\":\"0\",\"startTime\":\"09:00:00\",\"endTime\":\"19:00:00\",\"travelTime\":\"10\",\"travelTimeType\":\"1\",\"intro\":\"\\u5927\\u5b66\\u535a\\u7269\\u9986\\u6559\\u80b2\\u6e38\",\"info\":\"\\u522b\\u4ee5\\u4e3a\\u65b0\\u52a0\\u5761\\u53ea\\u6709\\u8d2d\\u7269\\u5546\\u573a\\u548c\\u6e38\\u4e50\\u56ed\\uff0c\\u8fd9\\u91cc\\u4e5f\\u662f\\u535a\\u7269\\u9986\\u7684\\u805a\\u96c6\\u5730\\u3002\\u4eab\\u7528\\u8fc7\\u4e00\\u987f\\u7f8e\\u7f8e\\u7684\\u65b0\\u52a0\\u5761\\u5f0f\\u65e9\\u9910\\u4e4b\\u540e\\uff0c\\u6211\\u4eec\\u5c31\\u53ef\\u4ee5\\u5f9c\\u5f89\\u5728\\u77e5\\u8bc6\\u7684\\u6d77\\u6d0b\\u5566\\u3002\\n\\u65b0\\u52a0\\u5761\\u56fd\\u7acb\\u5927\\u5b66\\uff0c\\u56fd\\u5bb6\\u535a\\u7269\\u9986\\uff0c\\u79d1\\u5b66\\u827a\\u672f\\u9986\\uff0c\\u56fd\\u5bb6\\u97f3\\u4e50\\u5385\\uff0c\\u571f\\u751f\\u534e\\u4eba\\u535a\\u7269\\u9986\\u3002\\n\\u65b0\\u52a0\\u5761\\u53ef\\u662f\\u6253\\u5f00\\u5927\\u95e8\\u6b22\\u8fce\\u60a8\\u54e6~~\",\"tags\":\"\\u5bb6\\u5ead,\\u535a\\u7269\\u9986\",\"status\":\"1\",\"countryCname\":\"\\u65b0\\u52a0\\u5761\",\"countryEname\":\"Singapore\",\"cityCname\":\"\\u65b0\\u52a0\\u5761\\u5e02\",\"cityEname\":\"Singapore City\"},\"picList\":[{\"picId\":\"3047\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609230056_83318.jpg\"},{\"picId\":\"3048\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609230017_91416.jpg\"},{\"picId\":\"3049\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609230006_12679.jpg\"},{\"picId\":\"3050\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609225942_82930.jpg\"},{\"picId\":\"3051\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609230001_26552.jpg\"},{\"picId\":\"3052\",\"tripId\":\"133\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150609225917_48328.jpg\"}],\"priceList\":[],\"publisherList\":[{\"nickname\":\"emma*****gmail.com\",\"phone\":\"93811856\",\"areaCode\":\"+65\",\"email\":\"emmaxia23@gmail.com\",\"sex\":\"2\",\"birthday\":\"0000-00-00\",\"headImg\":\"http:\\/\\/www.suiuu.com\\/assets\\/images\\/user_default.png\",\"hobby\":\"\",\"profession\":\"\",\"school\":\"\",\"intro\":\"\",\"info\":\"\",\"travelCount\":\"0\",\"userSign\":\"aa605a0e46de32d3968137d0f54d6684\",\"tripPublisherId\":\"146\",\"tripId\":\"133\",\"publisherId\":\"17\"}],\"scenicList\":[{\"scenicId\":\"1558\",\"tripId\":\"133\",\"name\":\"\\u65b0\\u52a0\\u5761\\u56fd\\u5bb6\\u535a\\u7269\\u9986\",\"lon\":\"103.819836\",\"lat\":\"1.352083\"}],\"serviceList\":[],\"highlightList\":[],\"includeDetailList\":[{\"detailId\":\"1201\",\"tripId\":\"133\",\"name\":\"\\u966a\\u540c\\u8bb2\\u89e3\",\"type\":\"1\"},{\"detailId\":\"1202\",\"tripId\":\"133\",\"name\":\"\\u968f\\u884c\\u7ffb\\u8bd1\",\"type\":\"1\"},{\"detailId\":\"1203\",\"tripId\":\"133\",\"name\":\"\\u5f53\\u5730\\u4eba\\u4ea4\\u901a\\u8d39\\u7528\",\"type\":\"1\"}],\"unIncludeDetailList\":[{\"detailId\":\"1204\",\"tripId\":\"133\",\"name\":\"\\u95e8\\u7968\\u8d39\\u7528\",\"type\":\"2\"},{\"detailId\":\"1205\",\"tripId\":\"133\",\"name\":\"\\u9910\\u996e\\u8d39\\u7528\",\"type\":\"2\"},{\"detailId\":\"1206\",\"tripId\":\"133\",\"name\":\"\\u4f4f\\u5bbf\",\"type\":\"2\"}],\"createPublisherInfo\":{\"nickname\":\"emma*****gmail.com\",\"phone\":\"93811856\",\"areaCode\":\"+65\",\"email\":\"emmaxia23@gmail.com\",\"sex\":\"2\",\"birthday\":\"0000-00-00\",\"headImg\":\"http:\\/\\/www.suiuu.com\\/assets\\/images\\/user_default.png\",\"hobby\":\"\",\"profession\":\"\",\"school\":\"\",\"intro\":\"\",\"info\":\"\",\"travelCount\":\"0\",\"userSign\":\"aa605a0e46de32d3968137d0f54d6684\",\"tripPublisherId\":\"146\",\"tripId\":\"133\",\"publisherId\":\"17\"}}","status":"3","hobby":null}]
     * message :
     * status : 1
     * token : 338054cdefa2f7db89b251ce2b0a7a4b
     */
    private List<CompletedOrderData> data;
    private String message;
    private int status;
    private String token;

    public void setData(List<CompletedOrderData> data) {
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

    public List<CompletedOrderData> getData() {
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

    public static class CompletedOrderData {
        /**
         * birthday : null
         * isComment : null
         * orderNumber : 2015062698505050
         * orderId : 33
         * totalPrice : 500.00
         * travelCount : null
         * serviceInfo : []
         * tripId : 133
         * school : null
         * intro : null
         * nickname : null
         * startTime : 07:50:00
         * email : null
         * basePrice : 500.00
         * info : null
         * profession : null
         * headImg : null
         * sex : null
         * userId : a4c1406ff4cc382389f19bf6ec3e55c1
         * personCount : 1
         * beginDate : 2015-06-27
         * areaCode : null
         * servicePrice : 0.00
         * createTime : 2015-06-26 13:47:55
         * phone : null
         * userSign : null
         * isDel : 0
         * tripJsonInfo : {"praise":[],"attention":[],"info":{"tripId":"133","createPublisherId":"17","createTime":"2015-06-07 17:36:33","title":"\u5927\u5b66\u535a\u7269\u9986\u6559\u80b2\u6e38","titleImg":"http:\/\/image.suiuu.com\/suiuu_trip\/20150607093224_46657_reset.jpg","countryId":"3759","cityId":"4150","lon":null,"lat":null,"basePrice":"500.00","basePriceType":"2","maxUserCount":"10","isAirplane":"0","isHotel":"0","score":"0","tripCount":"0","startTime":"09:00:00","endTime":"19:00:00","travelTime":"10","travelTimeType":"1","intro":"\u5927\u5b66\u535a\u7269\u9986\u6559\u80b2\u6e38","info":"\u522b\u4ee5\u4e3a\u65b0\u52a0\u5761\u53ea\u6709\u8d2d\u7269\u5546\u573a\u548c\u6e38\u4e50\u56ed\uff0c\u8fd9\u91cc\u4e5f\u662f\u535a\u7269\u9986\u7684\u805a\u96c6\u5730\u3002\u4eab\u7528\u8fc7\u4e00\u987f\u7f8e\u7f8e\u7684\u65b0\u52a0\u5761\u5f0f\u65e9\u9910\u4e4b\u540e\uff0c\u6211\u4eec\u5c31\u53ef\u4ee5\u5f9c\u5f89\u5728\u77e5\u8bc6\u7684\u6d77\u6d0b\u5566\u3002\n\u65b0\u52a0\u5761\u56fd\u7acb\u5927\u5b66\uff0c\u56fd\u5bb6\u535a\u7269\u9986\uff0c\u79d1\u5b66\u827a\u672f\u9986\uff0c\u56fd\u5bb6\u97f3\u4e50\u5385\uff0c\u571f\u751f\u534e\u4eba\u535a\u7269\u9986\u3002\n\u65b0\u52a0\u5761\u53ef\u662f\u6253\u5f00\u5927\u95e8\u6b22\u8fce\u60a8\u54e6~~","tags":"\u5bb6\u5ead,\u535a\u7269\u9986","status":"1","countryCname":"\u65b0\u52a0\u5761","countryEname":"Singapore","cityCname":"\u65b0\u52a0\u5761\u5e02","cityEname":"Singapore City"},"picList":[{"picId":"3047","tripId":"133","title":null,"url":"http:\/\/image.suiuu.com\/suiuu_trip\/20150609230056_83318.jpg"},{"picId":"3048","tripId":"133","title":null,"url":"http:\/\/image.suiuu.com\/suiuu_trip\/20150609230017_91416.jpg"},{"picId":"3049","tripId":"133","title":null,"url":"http:\/\/image.suiuu.com\/suiuu_trip\/20150609230006_12679.jpg"},{"picId":"3050","tripId":"133","title":null,"url":"http:\/\/image.suiuu.com\/suiuu_trip\/20150609225942_82930.jpg"},{"picId":"3051","tripId":"133","title":null,"url":"http:\/\/image.suiuu.com\/suiuu_trip\/20150609230001_26552.jpg"},{"picId":"3052","tripId":"133","title":null,"url":"http:\/\/image.suiuu.com\/suiuu_trip\/20150609225917_48328.jpg"}],"priceList":[],"publisherList":[{"nickname":"emma*****gmail.com","phone":"93811856","areaCode":"+65","email":"emmaxia23@gmail.com","sex":"2","birthday":"0000-00-00","headImg":"http:\/\/www.suiuu.com\/assets\/images\/user_default.png","hobby":"","profession":"","school":"","intro":"","info":"","travelCount":"0","userSign":"aa605a0e46de32d3968137d0f54d6684","tripPublisherId":"146","tripId":"133","publisherId":"17"}],"scenicList":[{"scenicId":"1558","tripId":"133","name":"\u65b0\u52a0\u5761\u56fd\u5bb6\u535a\u7269\u9986","lon":"103.819836","lat":"1.352083"}],"serviceList":[],"highlightList":[],"includeDetailList":[{"detailId":"1201","tripId":"133","name":"\u966a\u540c\u8bb2\u89e3","type":"1"},{"detailId":"1202","tripId":"133","name":"\u968f\u884c\u7ffb\u8bd1","type":"1"},{"detailId":"1203","tripId":"133","name":"\u5f53\u5730\u4eba\u4ea4\u901a\u8d39\u7528","type":"1"}],"unIncludeDetailList":[{"detailId":"1204","tripId":"133","name":"\u95e8\u7968\u8d39\u7528","type":"2"},{"detailId":"1205","tripId":"133","name":"\u9910\u996e\u8d39\u7528","type":"2"},{"detailId":"1206","tripId":"133","name":"\u4f4f\u5bbf","type":"2"}],"createPublisherInfo":{"nickname":"emma*****gmail.com","phone":"93811856","areaCode":"+65","email":"emmaxia23@gmail.com","sex":"2","birthday":"0000-00-00","headImg":"http:\/\/www.suiuu.com\/assets\/images\/user_default.png","hobby":"","profession":"","school":"","intro":"","info":"","travelCount":"0","userSign":"aa605a0e46de32d3968137d0f54d6684","tripPublisherId":"146","tripId":"133","publisherId":"17"}}
         * status : 3
         * hobby : null
         */
        private String birthday;
        private String isComment;
        private String orderNumber;
        private String orderId;
        private String totalPrice;
        private String travelCount;
        private String serviceInfo;
        private String tripId;
        private String school;
        private String intro;
        private String nickname;
        private String startTime;
        private String email;
        private String basePrice;
        private String info;
        private String profession;
        private String headImg;
        private String sex;
        private String userId;
        private String personCount;
        private String beginDate;
        private String areaCode;
        private String servicePrice;
        private String createTime;
        private String phone;
        private String userSign;
        private String isDel;
        private String tripJsonInfo;
        private String status;
        private String hobby;

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setIsComment(String isComment) {
            this.isComment = isComment;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public void setTravelCount(String travelCount) {
            this.travelCount = travelCount;
        }

        public void setServiceInfo(String serviceInfo) {
            this.serviceInfo = serviceInfo;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setBasePrice(String basePrice) {
            this.basePrice = basePrice;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setPersonCount(String personCount) {
            this.personCount = personCount;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public void setServicePrice(String servicePrice) {
            this.servicePrice = servicePrice;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public void setIsDel(String isDel) {
            this.isDel = isDel;
        }

        public void setTripJsonInfo(String tripJsonInfo) {
            this.tripJsonInfo = tripJsonInfo;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getIsComment() {
            return isComment;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public String getTravelCount() {
            return travelCount;
        }

        public String getServiceInfo() {
            return serviceInfo;
        }

        public String getTripId() {
            return tripId;
        }

        public String getSchool() {
            return school;
        }

        public String getIntro() {
            return intro;
        }

        public String getNickname() {
            return nickname;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEmail() {
            return email;
        }

        public String getBasePrice() {
            return basePrice;
        }

        public String getInfo() {
            return info;
        }

        public String getProfession() {
            return profession;
        }

        public String getHeadImg() {
            return headImg;
        }

        public String getSex() {
            return sex;
        }

        public String getUserId() {
            return userId;
        }

        public String getPersonCount() {
            return personCount;
        }

        public String getBeginDate() {
            return beginDate;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public String getServicePrice() {
            return servicePrice;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getPhone() {
            return phone;
        }

        public String getUserSign() {
            return userSign;
        }

        public String getIsDel() {
            return isDel;
        }

        public String getTripJsonInfo() {
            return tripJsonInfo;
        }

        public String getStatus() {
            return status;
        }

        public String getHobby() {
            return hobby;
        }
    }
}