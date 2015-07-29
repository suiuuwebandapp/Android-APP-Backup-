package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/7/27.
 * <p/>
 * 普通用户 未完成的订单实体类
 */
public class NotFinishedOrder {

    /**
     * data : [{"birthday":null,"orderNumber":"2015070649999748","orderId":"48","totalPrice":"746.00","travelCount":null,"serviceInfo":[{"money":"2000.00","tripId":"105","serviceId":"422","title":"独家包车","type":"0"}],"tripId":"119","school":null,"intro":null,"nickname":null,"startTime":"16:46:00","email":null,"basePrice":"746.00","info":null,"profession":null,"headImg":null,"sex":null,"userId":"a4c1406ff4cc382389f19bf6ec3e55c1","personCount":"1","beginDate":"2015-07-13","areaCode":null,"servicePrice":"0.00","createTime":"2015-07-06 16:47:13","phone":null,"userSign":null,"isDel":"0","tripJsonInfo":"{\"praise\":[],\"attention\":[],\"info\":{\"tripId\":\"119\",\"createPublisherId\":\"47\",\"createTime\":\"2015-06-04 15:04:05\",\"title\":\"\\u8fd9\\u79cd\\u73a9\\u6cd5\\uff0c\\u624d\\u80fd\\u91cc\\u91cc\\u5916\\u5916\\u73a9\\u8f6c\\u6089\\u5c3c\\u6b4c\\u5267\\u9662\",\"titleImg\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/119_reset.jpg\",\"countryId\":\"1083\",\"cityId\":\"1124\",\"lon\":null,\"lat\":null,\"basePrice\":\"746.00\",\"basePriceType\":\"2\",\"maxUserCount\":\"4\",\"isAirplane\":\"0\",\"isHotel\":\"0\",\"score\":\"0\",\"tripCount\":\"0\",\"startTime\":\"10:00:00\",\"endTime\":\"12:00:00\",\"travelTime\":\"2\",\"travelTimeType\":\"1\",\"intro\":\"\\u8fd9\\u79cd\\u73a9\\u6cd5\\uff0c\\u624d\\u80fd\\u91cc\\u91cc\\u5916\\u5916\\u73a9\\u8f6c\\u6089\\u5c3c\\u6b4c\\u5267\\u9662\",\"info\":\"\\u4f53\\u9a8c\\u80cc\\u666f\\uff1a\\n\\u6089\\u5c3c\\u6b4c\\u5267\\u9662\\u5750\\u843d\\u4e8e\\u6089\\u5c3c\\u6e2f\\uff0c\\u56e0\\u5176\\u5927\\u80c6\\u7684\\u5185\\u5916\\u8bbe\\u8ba1\\u4e14\\u4e09\\u9762\\u73af\\u6d77\\uff0c\\u662f\\u4e16\\u754c\\u8457\\u540d\\u7684\\u8868\\u6f14\\u827a\\u672f\\u4e2d\\u5fc3\\u3002\\u53c8\\u56e0\\u5176\\u72ec\\u7279\\u7684\\u5e06\\u8239\\u9020\\u578b\\u4e0e\\u80cc\\u666f\\u5904\\u7684\\u6089\\u5c3c\\u6e2f\\u6e7e\\u5927\\u6865\\u76f8\\u6620\\u6210\\u8da3\\uff0c\\u65e9\\u5df2\\u6210\\u4e3a\\u6089\\u5c3c\\u6700\\u5177\\u7279\\u8272\\u7684\\u5730\\u6807\\u6027\\u5efa\\u7b51\\u3002\\u4f5c\\u4e3a\\u6765\\u5230\\u6089\\u5c3c\\u4e00\\u5b9a\\u4e0d\\u80fd\\u9519\\u8fc7\\u7684\\u6700\\u4f1f\\u5927\\u5efa\\u7b51\\u4e4b\\u4e00\\uff0c\\u95e8\\u9053\\u751f\\u6d3b\\u5bb6\\u5c0f\\u7f8a\\u66f4\\u53ef\\u4ee5\\u5e26\\u7740\\u60a8\\u6df1\\u5ea6\\u7545\\u6e38\\u6b4c\\u5267\\u9662\\uff0c\\u611f\\u53d7\\u5b83\\u80cc\\u540e\\u7684\\u591a\\u6837\\u6545\\u4e8b\\u3002\\n\\n\\u63a8\\u8350\\u7406\\u7531\\uff1a\\n\\u6089\\u5c3c\\u6b4c\\u5267\\u9662\\u4e3b\\u8981\\u6709\\u4e24\\u4e2a\\u4e3b\\u5385\\uff08\\u97f3\\u4e50\\u5385\\u548c\\u6b4c\\u5267\\u5385\\uff09\\uff0c\\u53e6\\u6709\\u4e00\\u4e9b\\u5c0f\\u578b\\u5267\\u9662\\u3001\\u6f14\\u51fa\\u5385\\u7b49\\u9644\\u5c5e\\u8bbe\\u65bd\\u3002\\u95e8\\u9053\\u751f\\u6d3b\\u5bb6\\u5c0f\\u7f8a\\u4f1a\\u4e3a\\u60a8\\u8be6\\u7ec6\\u8bb2\\u89e3\\u97f3\\u4e50\\u5385\\u5185\\u7684\\u97f3\\u6548\\u8bbe\\u8ba1\\u3001\\u9690\\u853d\\u7684\\u4e16\\u754c\\u7ea7\\u4e50\\u5668\\u548c\\u5385\\u5185\\u53d1\\u751f\\u7684\\u5947\\u4eba\\u5f02\\u4e8b\\uff1b\\u4e5f\\u4f1a\\u5e26\\u60a8\\u7545\\u6e38\\u6b4c\\u5267\\u5385\\uff0c\\u4e3a\\u60a8\\u5c55\\u793a\\u6b4c\\u5267\\u8868\\u6f14\\u4e2d\\u6240\\u7528\\u5230\\u7684\\u5927\\u91cf\\u9053\\u5177\\u548c\\u5e03\\u666f\\uff0c\\u66f4\\u4e3a\\u60a8\\u4e00\\u4e00\\u6307\\u51fa\\u6b4c\\u5267\\u5385\\u5185\\u7684\\u6697\\u85cf\\u673a\\u5173\\uff0c\\u9886\\u7565\\u8bbe\\u8ba1\\u5e08\\u7684\\u5320\\u5fc3\\u72ec\\u8fd0\\uff1b\\u5e76\\u53ef\\u4ee5\\u8fd1\\u8ddd\\u79bb\\u611f\\u53d7\\u4e00\\u4e24\\u4e2a\\u5c0f\\u8868\\u6f14\\u5385\\u7684\\u5185\\u90e8\\u6e38\\u89c8\\u3002\\u5728\\u89c2\\u8d4f\\u6089\\u5c3c\\u6b4c\\u5267\\u9662\\u72ec\\u7279\\u8bbe\\u8ba1\\u7684\\u540c\\u65f6\\uff0c\\u5c0f\\u7f8a\\u4e5f\\u4f1a\\u5c06\\u5f53\\u521d\\u4e39\\u9ea6\\u8bbe\\u8ba1\\u5e08\\u5728\\u8bbe\\u8ba1\\u8fd9\\u4e2a\\u5de5\\u7a0b\\u65f6\\u6240\\u9762\\u5bf9\\u7684\\u620f\\u5267\\u6027\\u6545\\u4e8b\\u5a13\\u5a13\\u9053\\u6765\\uff0c\\u8ba9\\u60a8\\u771f\\u6b63\\u611f\\u53d7\\u4e0d\\u4e00\\u6837\\u7684\\u6089\\u5c3c\\u6b4c\\u5267\\u9662\\u3002\\n\\n1\\u3001\\u53f9\\u4e3a\\u89c2\\u6b62\\u7684\\u6b4c\\u5267\\u9662\\u4e3b\\u5385\\n2\\u3001\\u6697\\u85cf\\u673a\\u5173\\u7684\\u7b2c\\u4e8c\\u5927\\u5385\\n3\\u3001\\u7279\\u6709\\u7684\\u5e06\\u8239\\u9020\\u578b\\u4e0e\\u6089\\u5c3c\\u6e2f\\u6e7e\\u5927\\u6865\\u76f8\\u6620\\u6210\\u8da3\\n\\n\\u8d39\\u7528\\u5305\\u542b\\uff1a\\n\\u751f\\u6d3b\\u5bb6\\u4f53\\u9a8c\\u6df1\\u5ea6\\u8bb2\\u89e3 \\n\\u751f\\u6d3b\\u5bb6\\u5c0f\\u8d39 \\n\\u53c2\\u89c2\\u95e8\\u7968\\n\\n\\u6ce8\\u610f\\u4e8b\\u9879:\\n\\u7531\\u4e8e\\u6089\\u5c3c\\u5267\\u9662\\u662f\\u4e00\\u4e2a\\u6b63\\u5728\\u8fd0\\u8425\\u7684\\u827a\\u672f\\u8868\\u6f14\\u4e2d\\u5fc3\\uff0c\\u6709\\u6f14\\u51fa\\u65f6\\u4e0d\\u5141\\u8bb8\\u4efb\\u4f55\\u6e38\\u5ba2\\u53c2\\u89c2\\uff0c\\u6240\\u4ee5\\u6bcf\\u5929\\u4e0d\\u540c\\u65f6\\u95f4\\u6bb5\\u53ef\\u4ee5\\u8fdb\\u5165\\u7684\\u5385\\u90fd\\u4e0d\\u4e00\\u6837\\uff0c\\u4e0d\\u4fdd\\u8bc1\\u4f1a\\u53c2\\u89c2\\u5230\\u6240\\u6709\\u7684\\u8868\\u6f14\\u5385\\uff08\\u81f3\\u5c112\\u4e2a\\uff09\\u3002\\u9884\\u5b9a\\u4f53\\u9a8c\\u5f53\\u5929\\u6211\\u4f1a\\u8ddf\\u60a8\\u786e\\u8ba4\\u3002\\n\\n\\u4f53\\u9a8c\\u65f6\\u95f4\\uff1a\\u6bcf\\u5468\\u65e5\\n\\n\\u751f\\u6d3b\\u5bb6\\u4ecb\\u7ecd\\uff1a\\nChancy\\u9648\\u5c0f\\u7f8a\\u6765\\u5230\\u888b\\u9f20\\u56fd\\u5df2\\u7ecf5\\u5e74\\uff0c\\u80cc\\u5305\\u6e38\\u8fc7\\u5927\\u6fb3\\u6751\\uff0c\\u62c9\\u4e01\\u7f8e\\u6d32\\u3002\\u5728\\u5b66\\u897f\\u73ed\\u7259\\u8bed\\uff0c\\u8df3salsa\\u821e\\uff0c\\u7231\\u745c\\u4f3d\\u548c\\u6444\\u5f71\\uff01\\u4f5c\\u4e3a\\u6089\\u5c3c\\u6b4c\\u5267\\u9662\\u7684\\u4e2d\\u6587\\u8bb2\\u89e3\\u5458\\uff0c\\u5e0c\\u671b\\u80fd\\u5e26\\u7ed9\\u60a8\\u6df1\\u5ea6\\u6e38\\u7684\\u4f53\\u9a8c\\uff01\",\"tags\":\"\\u535a\\u7269\\u9986,\\u730e\\u5947\",\"status\":\"1\",\"countryCname\":\"\\u6fb3\\u5927\\u5229\\u4e9a\",\"countryEname\":\"Australia\",\"cityCname\":\"\\u6089\\u5c3c\",\"cityEname\":\"Sydney\"},\"picList\":[{\"picId\":\"3632\",\"tripId\":\"119\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_head\\/20150610081848_37585.jpg\"},{\"picId\":\"3633\",\"tripId\":\"119\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_head\\/20150610081651_80673.jpg\"},{\"picId\":\"3634\",\"tripId\":\"119\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/user_card\\/20150604150143_95672.jpg\"},{\"picId\":\"3635\",\"tripId\":\"119\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/user_card\\/20150604150129_61281.jpg\"},{\"picId\":\"3636\",\"tripId\":\"119\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/user_card\\/20150604150126_76764.jpg\"},{\"picId\":\"3637\",\"tripId\":\"119\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/user_card\\/20150604150123_71905.jpg\"},{\"picId\":\"3638\",\"tripId\":\"119\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/user_card\\/20150604150120_47136.jpg\"}],\"priceList\":[],\"publisherList\":[{\"nickname\":\"mendao\",\"phone\":\"13901358226\",\"areaCode\":null,\"email\":\"yikun@mendao.cn\",\"sex\":\"2\",\"birthday\":\"1980-01-01\",\"headImg\":\"http:\\/\\/image.suiuu.com\\/suiuu_head\\/20150616011330_80262.jpg\",\"hobby\":\"\",\"countryId\":\"336\",\"cityId\":\"337\",\"profession\":\"\\u5728\\u7ebf\\u4f53\\u9a8c\\u5e73\\u53f0\",\"school\":\"\",\"intro\":\"\\u9009\\u62e9\\u95e8\\u9053\\uff0c\\u5176\\u5b9e\\u662f\\u9009\\u62e9\\u4e00\\u79cd\\u751f\\u6d3b\\u7684\\u6001\\u5ea6\",\"info\":\"\\u95e8\\u9053\\uff08https:\\/\\/mendao.cn\\/\\uff09\\u6210\\u7acb\\u4e8e2014\\u5e749\\u6708\\uff0c\\u603b\\u90e8\\u4f4d\\u4e8e\\u6fb3\\u5927\\u5229\\u4e9a\\u7684\\u6089\\u5c3c\\u3002\\n\\n\\u95e8\\u9053\\uff0c\\u81f4\\u529b\\u4e8e\\u6253\\u9020\\u4e3a\\u5168\\u7403\\u7528\\u6237\\u63d0\\u4f9b\\u76ee\\u7684\\u5730\\u4f53\\u9a8c\\u7684\\u5728\\u7ebf\\u5e73\\u53f0\\u3002\\n\\n\\u4f5c\\u4e3a\\u7528\\u6237\\uff0c\\u95e8\\u9053\\u9080\\u8bf7\\u60a8\\u6210\\u4e3a\\u95e8\\u9053\\u884c\\u5bb6\\uff0c\\u901a\\u8fc7\\u95e8\\u9053\\u9884\\u8ba2\\u5177\\u6709\\u5f53\\u5730\\u7279\\u8272\\u7684\\u4f53\\u9a8c\\uff0c\\u8d34\\u8fd1\\u5f53\\u5730\\u751f\\u6d3b\\uff0c\\u4e86\\u89e3\\u5f53\\u5730\\u6587\\u5316\\uff0c\\u54c1\\u5c1d\\u5f53\\u5730\\u7f8e\\u98df\\uff0c\\u6b23\\u8d4f\\u5f53\\u5730\\u7f8e\\u666f\\uff0c\\u4eb2\\u8eab\\u53c2\\u4e0e\\u5404\\u79cd\\u5a31\\u4e50\\u4f11\\u95f2\\u6d3b\\u52a8\\uff0c\\u5e76\\u4e0e\\u5f53\\u5730\\u4eba\\u6210\\u4e3a\\u670b\\u53cb.\\n\\n\\u4f5c\\u4e3a\\u5f53\\u5730\\u4eba\\uff0c\\u95e8\\u9053\\u9080\\u8bf7\\u60a8\\u6210\\u4e3a\\u95e8\\u9053\\u751f\\u6d3b\\u5bb6\\uff0c\\u901a\\u8fc7\\u95e8\\u9053\\u53d1\\u5e03\\u5206\\u4eab\\u60a8\\u7684\\u4f53\\u9a8c\\uff0c\\u9080\\u8bf7\\u7528\\u6237\\u6765\\u53c2\\u4e0e\\u3002\\u4ee5\\u60a8\\u4e30\\u5bcc\\u7684\\u751f\\u6d3b\\u7ecf\\u9a8c\\u3001\\u70ed\\u60c5\\u7684\\u751f\\u6d3b\\u6001\\u5ea6\\u548c\\u53d1\\u5e03\\u7684\\u6709\\u8da3\\u7684\\u4f53\\u9a8c\\uff0c\\u8ba9\\u6bcf\\u4e00\\u4e2a\\u53c2\\u4e0e\\u7684\\u4eba\\u90fd\\u80fd\\u611f\\u53d7\\u5230\\u60a8\\u751f\\u6d3b\\u7684\\u8fd9\\u7247\\u571f\\u5730\\u7684\\u795e\\u5947\\u548c\\u667a\\u6167\\uff0c\\u4ece\\u60a8\\u7684\\u4f53\\u9a8c\\u4e2d\\u611f\\u53d7\\u5230\\u5f53\\u5730\\u4eba\\u7684\\u70ed\\u60c5\\u3001\\u6e29\\u99a8\\u548c\\u53cb\\u597d\\u3002\\n\\n\\u95e8\\u9053\\u76f8\\u4fe1\\u6700\\u597d\\u7684\\u751f\\u6d3b\\u4f53\\u9a8c\\u8981\\u9760\\u4eba\\u548c\\u4eba\\u7684\\u4ea4\\u6d41\\u548c\\u5206\\u4eab\\u6765\\u521b\\u9020\\u3002\\n\\u95e8\\u9053\\u4fdd\\u969c\\u6bcf\\u4e2a\\u4eba\\u7684\\u9690\\u79c1\\uff0c\\u4e3a\\u4e86\\u6700\\u5927\\u9650\\u5ea6\\u7684\\u4fdd\\u8bc1\\u7528\\u6237\\u4f53\\u9a8c\\u7684\\u8212\\u9002\\u548c\\u5b89\\u5168\\uff0c\\u5355\\u4e2a\\u4f53\\u9a8c\\u4eba\\u6570\\u4e0d\\u8d85\\u8fc78\\u4eba\\uff0c\\u540c\\u4e00\\u4e2a\\u4f53\\u9a8c\\u4e2d\\u53ea\\u6709\\u7528\\u6237\\u7684\\u670b\\u53cb\\u548c\\u5bb6\\u4eba\\uff0c\\u9664\\u975e\\u7528\\u6237\\u540c\\u610f\\u52a0\\u5165\\u964c\\u751f\\u4eba\\u3002\\n\\n\\u8054\\u7cfb\\u65b9\\u5f0f\\n\\u5fae\\u4fe1\\uff1amendaoau\\n\\u5fae\\u535a\\uff1aweibo.com\\/men2dao4\\n\\u90ae\\u4ef6\\uff1acontact@mendao.com.au\\n\\u7535\\u8bdd: +61 433 456 228\",\"travelCount\":\"0\",\"userSign\":\"c39679dc59293b1aa5efa0f65dec0a29\",\"tripPublisherId\":\"131\",\"tripId\":\"119\",\"publisherId\":\"47\",\"cityName\":\"\\u5317\\u4eac\",\"countryName\":\"\\u4e2d\\u56fd\"}],\"scenicList\":[{\"scenicId\":\"1670\",\"tripId\":\"119\",\"name\":\"\\u6089\\u5c3c\\u6b4c\\u5267\\u9662\",\"lon\":\"116.285591\",\"lat\":\"39.809334\"}],\"serviceList\":[],\"highlightList\":[{\"hlId\":\"574\",\"tripId\":\"119\",\"value\":\"\\u901a\\u5f80\\u4e16\\u754c\\u4e03\\u5927\\u9057\\u4ea7\\u4e4b\\u4e00\\u7684\\u6b4c\\u5267\\u9662\\u5185\\u573a\"},{\"hlId\":\"575\",\"tripId\":\"119\",\"value\":\"\\u6f14\\u594f\\u5385360\\u00b0\\u65e0\\u6b7b\\u89d2\\u89c2\\u6469\"},{\"hlId\":\"576\",\"tripId\":\"119\",\"value\":\"\\u91cc\\u91cc\\u5916\\u5916\\u4e86\\u89e3\\u6b4c\\u5267\\u9662\"},{\"hlId\":\"577\",\"tripId\":\"119\",\"value\":\"\\u503e\\u542c\\u6b4c\\u5267\\u9662\\u5916\\u5efa\\u7b51\\u7684\\u7531\\u6765\"},{\"hlId\":\"578\",\"tripId\":\"119\",\"value\":\"\\u4f53\\u9a8c\\u7ed3\\u675f\\uff0c\\u53ef\\u4ee5\\u8ba9\\u751f\\u6d3b\\u5bb6\\u5e2e\\u5fd9\\u9884\\u5b9a\\u5f53\\u5929\\u8868\\u6f14\"}],\"includeDetailList\":[{\"detailId\":\"1796\",\"tripId\":\"119\",\"name\":\"\\u751f\\u6d3b\\u5bb6\\u4f53\\u9a8c\\u6df1\\u5ea6\\u8bb2\\u89e3\",\"type\":\"1\"},{\"detailId\":\"1797\",\"tripId\":\"119\",\"name\":\"\\u751f\\u6d3b\\u5bb6\\u5c0f\\u8d39\",\"type\":\"1\"},{\"detailId\":\"1798\",\"tripId\":\"119\",\"name\":\"\\u53c2\\u89c2\\u95e8\\u7968\",\"type\":\"1\"},{\"detailId\":\"1799\",\"tripId\":\"119\",\"name\":\"\\u966a\\u540c\\u8bb2\\u89e3\",\"type\":\"1\"}],\"unIncludeDetailList\":[{\"detailId\":\"1800\",\"tripId\":\"119\",\"name\":\"\\u516c\\u5171\\u4ea4\\u901a\\u8d39\",\"type\":\"2\"},{\"detailId\":\"1801\",\"tripId\":\"119\",\"name\":\"\\u8d2d\\u7269\\u4ea7\\u751f\\u7684\\u8d39\\u7528\",\"type\":\"2\"},{\"detailId\":\"1802\",\"tripId\":\"119\",\"name\":\"\\u4f4f\\u5bbf\",\"type\":\"2\"},{\"detailId\":\"1803\",\"tripId\":\"119\",\"name\":\"\\u9910\\u996e\\u8d39\\u7528\",\"type\":\"2\"},{\"detailId\":\"1804\",\"tripId\":\"119\",\"name\":\"\\u63a5\\u9001\\u673a\",\"type\":\"2\"}],\"createPublisherInfo\":{\"nickname\":\"mendao\",\"phone\":\"13901358226\",\"areaCode\":null,\"email\":\"yikun@mendao.cn\",\"sex\":\"2\",\"birthday\":\"1980-01-01\",\"headImg\":\"http:\\/\\/image.suiuu.com\\/suiuu_head\\/20150616011330_80262.jpg\",\"hobby\":\"\",\"countryId\":\"336\",\"cityId\":\"337\",\"profession\":\"\\u5728\\u7ebf\\u4f53\\u9a8c\\u5e73\\u53f0\",\"school\":\"\",\"intro\":\"\\u9009\\u62e9\\u95e8\\u9053\\uff0c\\u5176\\u5b9e\\u662f\\u9009\\u62e9\\u4e00\\u79cd\\u751f\\u6d3b\\u7684\\u6001\\u5ea6\",\"info\":\"\\u95e8\\u9053\\uff08https:\\/\\/mendao.cn\\/\\uff09\\u6210\\u7acb\\u4e8e2014\\u5e749\\u6708\\uff0c\\u603b\\u90e8\\u4f4d\\u4e8e\\u6fb3\\u5927\\u5229\\u4e9a\\u7684\\u6089\\u5c3c\\u3002\\n\\n\\u95e8\\u9053\\uff0c\\u81f4\\u529b\\u4e8e\\u6253\\u9020\\u4e3a\\u5168\\u7403\\u7528\\u6237\\u63d0\\u4f9b\\u76ee\\u7684\\u5730\\u4f53\\u9a8c\\u7684\\u5728\\u7ebf\\u5e73\\u53f0\\u3002\\n\\n\\u4f5c\\u4e3a\\u7528\\u6237\\uff0c\\u95e8\\u9053\\u9080\\u8bf7\\u60a8\\u6210\\u4e3a\\u95e8\\u9053\\u884c\\u5bb6\\uff0c\\u901a\\u8fc7\\u95e8\\u9053\\u9884\\u8ba2\\u5177\\u6709\\u5f53\\u5730\\u7279\\u8272\\u7684\\u4f53\\u9a8c\\uff0c\\u8d34\\u8fd1\\u5f53\\u5730\\u751f\\u6d3b\\uff0c\\u4e86\\u89e3\\u5f53\\u5730\\u6587\\u5316\\uff0c\\u54c1\\u5c1d\\u5f53\\u5730\\u7f8e\\u98df\\uff0c\\u6b23\\u8d4f\\u5f53\\u5730\\u7f8e\\u666f\\uff0c\\u4eb2\\u8eab\\u53c2\\u4e0e\\u5404\\u79cd\\u5a31\\u4e50\\u4f11\\u95f2\\u6d3b\\u52a8\\uff0c\\u5e76\\u4e0e\\u5f53\\u5730\\u4eba\\u6210\\u4e3a\\u670b\\u53cb.\\n\\n\\u4f5c\\u4e3a\\u5f53\\u5730\\u4eba\\uff0c\\u95e8\\u9053\\u9080\\u8bf7\\u60a8\\u6210\\u4e3a\\u95e8\\u9053\\u751f\\u6d3b\\u5bb6\\uff0c\\u901a\\u8fc7\\u95e8\\u9053\\u53d1\\u5e03\\u5206\\u4eab\\u60a8\\u7684\\u4f53\\u9a8c\\uff0c\\u9080\\u8bf7\\u7528\\u6237\\u6765\\u53c2\\u4e0e\\u3002\\u4ee5\\u60a8\\u4e30\\u5bcc\\u7684\\u751f\\u6d3b\\u7ecf\\u9a8c\\u3001\\u70ed\\u60c5\\u7684\\u751f\\u6d3b\\u6001\\u5ea6\\u548c\\u53d1\\u5e03\\u7684\\u6709\\u8da3\\u7684\\u4f53\\u9a8c\\uff0c\\u8ba9\\u6bcf\\u4e00\\u4e2a\\u53c2\\u4e0e\\u7684\\u4eba\\u90fd\\u80fd\\u611f\\u53d7\\u5230\\u60a8\\u751f\\u6d3b\\u7684\\u8fd9\\u7247\\u571f\\u5730\\u7684\\u795e\\u5947\\u548c\\u667a\\u6167\\uff0c\\u4ece\\u60a8\\u7684\\u4f53\\u9a8c\\u4e2d\\u611f\\u53d7\\u5230\\u5f53\\u5730\\u4eba\\u7684\\u70ed\\u60c5\\u3001\\u6e29\\u99a8\\u548c\\u53cb\\u597d\\u3002\\n\\n\\u95e8\\u9053\\u76f8\\u4fe1\\u6700\\u597d\\u7684\\u751f\\u6d3b\\u4f53\\u9a8c\\u8981\\u9760\\u4eba\\u548c\\u4eba\\u7684\\u4ea4\\u6d41\\u548c\\u5206\\u4eab\\u6765\\u521b\\u9020\\u3002\\n\\u95e8\\u9053\\u4fdd\\u969c\\u6bcf\\u4e2a\\u4eba\\u7684\\u9690\\u79c1\\uff0c\\u4e3a\\u4e86\\u6700\\u5927\\u9650\\u5ea6\\u7684\\u4fdd\\u8bc1\\u7528\\u6237\\u4f53\\u9a8c\\u7684\\u8212\\u9002\\u548c\\u5b89\\u5168\\uff0c\\u5355\\u4e2a\\u4f53\\u9a8c\\u4eba\\u6570\\u4e0d\\u8d85\\u8fc78\\u4eba\\uff0c\\u540c\\u4e00\\u4e2a\\u4f53\\u9a8c\\u4e2d\\u53ea\\u6709\\u7528\\u6237\\u7684\\u670b\\u53cb\\u548c\\u5bb6\\u4eba\\uff0c\\u9664\\u975e\\u7528\\u6237\\u540c\\u610f\\u52a0\\u5165\\u964c\\u751f\\u4eba\\u3002\\n\\n\\u8054\\u7cfb\\u65b9\\u5f0f\\n\\u5fae\\u4fe1\\uff1amendaoau\\n\\u5fae\\u535a\\uff1aweibo.com\\/men2dao4\\n\\u90ae\\u4ef6\\uff1acontact@mendao.com.au\\n\\u7535\\u8bdd: +61 433 456 228\",\"travelCount\":\"0\",\"userSign\":\"c39679dc59293b1aa5efa0f65dec0a29\",\"tripPublisherId\":\"131\",\"tripId\":\"119\",\"publisherId\":\"47\",\"cityName\":\"\\u5317\\u4eac\",\"countryName\":\"\\u4e2d\\u56fd\"}}","status":"0","hobby":null},{"birthday":null,"orderNumber":"2015070654991005","orderId":"49","totalPrice":"1155.00","travelCount":null,"serviceInfo":"[]","tripId":"64","school":null,"intro":null,"nickname":null,"startTime":"17:17:00","email":null,"basePrice":"1155.00","info":null,"profession":null,"headImg":null,"sex":null,"userId":"a4c1406ff4cc382389f19bf6ec3e55c1","personCount":"1","beginDate":"2015-07-15","areaCode":null,"servicePrice":"0.00","createTime":"2015-07-06 17:17:42","phone":null,"userSign":null,"isDel":"0","tripJsonInfo":"{\"praise\":[],\"attention\":[],\"info\":{\"tripId\":\"64\",\"createPublisherId\":\"22\",\"createTime\":\"2015-05-03 14:26:14\",\"title\":\"\\u611f\\u53d7\\u65f6\\u5c1a\\u4e4b\\u90fd-\\u7c73\\u5170\",\"titleImg\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/64_reset.jpg\",\"countryId\":\"3884\",\"cityId\":\"3927\",\"lon\":null,\"lat\":null,\"basePrice\":\"1155.00\",\"basePriceType\":\"1\",\"maxUserCount\":\"7\",\"isAirplane\":\"0\",\"isHotel\":\"0\",\"score\":\"0\",\"tripCount\":\"0\",\"startTime\":\"09:00:00\",\"endTime\":\"19:00:00\",\"travelTime\":\"10\",\"travelTimeType\":\"1\",\"intro\":\"\\u611f\\u53d7\\u65f6\\u5c1a\\u4e4b\\u90fd-\\u7c73\\u5170\",\"info\":\"\\u7c73\\u5170\\u591a\\u83ab\\u5927\\u6559\\u5802\\u2014\\u2014\\u4e16\\u754c\\u4e0a\\u6700\\u5927\\u7684\\u54e5\\u7279\\u5f0f\\u5efa\\u7b51\\uff0c\\u6b27\\u6d32\\u6700\\u5927\\u7684\\u5927\\u7406\\u77f3\\u5efa\\u7b51\\uff0c\\u5916\\u89c2\\u6781\\u5c3d\\u534e\\u7f8e\\uff0c\\u5386\\u65f66\\u4e2a\\u4e16\\u7eaa\\u624d\\u5b8c\\u5de5\\u3002\\u62ff\\u7834\\u4ed1\\u66fe\\u5728\\u6b64\\u52a0\\u5195\\uff0c\\u8303\\u601d\\u54f2\\u66fe\\u5728\\u6b64\\u4e3e\\u529e\\u846c\\u793c\\u3002\\n\\u57c3\\u739b\\u52aa\\u57c3\\u83b1\\u4e8c\\u4e16\\u62f1\\u5eca\\u662f\\u4e16\\u754c\\u6700\\u53e4\\u8001\\u7684\\u8d2d\\u7269\\u8857\\u3002\\u5962\\u4f88\\u54c1\\u8857\\u5404\\u5927\\u724c\\u4e91\\u96c6\\uff0c\\u7edd\\u5bf9\\u7684\\u7406\\u60f3\\u8d2d\\u7269\\u4e4b\\u90fd\\u3002\\u6ee1\\u76ee\\u7684\\u65f6\\u5c1a\\u6f6e\\u4eba\\u3001\\u5e05\\u54e5\\u7f8e\\u5973\\uff0c\\u6216\\u8bb8\\u5728\\u4f60\\u4e0d\\u7ecf\\u610f\\u7684\\u4e00\\u77ac\\uff0c\\u5c31\\u4f1a\\u4e0e\\u660e\\u661f\\u5bf9\\u89c6\\u54df~\\u7c73\\u5170\\u4e5f\\u662f\\u4e16\\u754c\\u5404\\u5730\\u7f8e\\u98df\\u835f\\u8403\\u4e4b\\u5730\\uff0c\\u4f60\\u53ef\\u4ee5\\u5c3d\\u60c5\\u54c1\\u5c1d\\u5404\\u8272\\u7f8e\\u98df\\u5594\\uff01\\n\\u53c2\\u89c2\\u767b\\u9876\\u8457\\u540d\\u7684\\u7c73\\u5170\\u591a\\u83ab\\u5927\\u6559\\u5802\\uff0c\\u4fef\\u77b0\\u7c73\\u5170\\u5168\\u666f\\uff0c\\u6e38\\u89c8\\u7c73\\u5170\\u738b\\u5bab\\u3002\\u6d41\\u8fde\\u5728\\u4f0a\\u66fc\\u7956\\u5c14\\u4e8c\\u4e16\\u8d70\\u5eca\\u53ca\\u62ff\\u7834\\u4ed1\\u5c71\\u5962\\u4f88\\u54c1\\u8d2d\\u7269\\u8857\\u3002\\u53c2\\u89c2\\u7c73\\u5170\\u65af\\u798f\\u5c14\\u624e\\u57ce\\u5821\\u53ca\\u51ef\\u65cb\\u95e8\\u3002\\u63a0\\u5f71\\u610f\\u5927\\u5229\\u56fd\\u7acb\\u7c73\\u5170\\u5927\\u5b66\\u53ca\\u7c73\\u5170\\u56fd\\u7acb\\u5e03\\u96f7\\u62c9\\u7f8e\\u9662\\u3002\\u54c1\\u5c1d\\u7c73\\u5170\\u7279\\u8272\\u5e73\\u4ef7\\u5c0f\\u5403Luini\\u5927\\u997a\\u5b50\\u6216Spontini\\u539a\\u62ab\\u8428\\uff0c\\u4e5f\\u53ef\\u4ee5\\u6309\\u7167\\u60a8\\u7684\\u8981\\u6c42\\u54c1\\u5c1d\\u610f\\u5927\\u5229\\u7ecf\\u5178\\u83dc\\u5f0f\\u3002\\uff08\\u5907\\u6ce8\\uff1a\\u666f\\u70b9\\u4e4b\\u95f4\\u8ddd\\u79bb\\u4e0d\\u8fdc\\uff0c\\u4e00\\u822c\\u90fd\\u662f\\u6b65\\u884c\\u524d\\u5f80\\u3002\\uff09\\n\\u53ef\\u4ee5\\u4ee3\\u8ba2\\u5bbe\\u9986\\u4f4f\\u5bbf\\uff0c\\u4f46\\u623f\\u8d39\\u9700\\u8981\\u60a8\\u81ea\\u884c\\u627f\\u62c5 \\u3002\",\"tags\":\"\\u5bb6\\u5ead,\\u7f8e\\u98df,\\u535a\\u7269\\u9986,\\u6d6a\\u6f2b,\\u8d2d\\u7269\",\"status\":\"1\",\"countryCname\":\"\\u610f\\u5927\\u5229\",\"countryEname\":\"Italy\",\"cityCname\":\"\\u7c73\\u5170\",\"cityEname\":\"Milan\"},\"picList\":[{\"picId\":\"3353\",\"tripId\":\"64\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/user_card\\/20150511210942_68751.jpg\"},{\"picId\":\"3354\",\"tripId\":\"64\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/user_card\\/20150511210529_35790.jpg\"},{\"picId\":\"3355\",\"tripId\":\"64\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/user_card\\/20150511210258_25965.jpg\"},{\"picId\":\"3356\",\"tripId\":\"64\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/user_card\\/20150503142217_94442.jpg\"},{\"picId\":\"3357\",\"tripId\":\"64\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/user_card\\/20150503142213_16849.jpg\"},{\"picId\":\"3358\",\"tripId\":\"64\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/user_card\\/20150503142148_14956.jpg\"}],\"priceList\":[{\"priceId\":\"434\",\"tripId\":\"64\",\"minCount\":\"2\",\"maxCount\":\"3\",\"price\":\"600.00\"},{\"priceId\":\"435\",\"tripId\":\"64\",\"minCount\":\"4\",\"maxCount\":\"7\",\"price\":\"400.00\"}],\"publisherList\":[{\"nickname\":\"\\u9ad8\\u8574\\u73ca\",\"phone\":null,\"areaCode\":null,\"email\":\"gao-yun-shan@163.com\",\"sex\":\"2\",\"birthday\":\"0000-00-00\",\"headImg\":\"http:\\/\\/image.suiuu.com\\/suiuu_head\\/20150529144326_41513.jpg\",\"hobby\":\"\",\"countryId\":null,\"cityId\":null,\"profession\":\"\",\"school\":\"\",\"intro\":\"\",\"info\":\"\",\"travelCount\":\"0\",\"userSign\":\"30e72439290636b0b8b4fcc575c02f1e\",\"tripPublisherId\":\"62\",\"tripId\":\"64\",\"publisherId\":\"22\",\"cityName\":null,\"countryName\":null}],\"scenicList\":[{\"scenicId\":\"1613\",\"tripId\":\"64\",\"name\":\"\\u610f\\u5927\\u5229\\u7c73\\u5170\\u591a\\u83ab\\u5927\\u6559\\u5802\",\"lon\":\"9.1859243\",\"lat\":\"45.4654219\"},{\"scenicId\":\"1614\",\"tripId\":\"64\",\"name\":\"\\u7c73\\u5170\\u738b\\u5bab\",\"lon\":\"9.1859243\",\"lat\":\"45.4654219\"},{\"scenicId\":\"1615\",\"tripId\":\"64\",\"name\":\"\\u7c73\\u5170\\u56fd\\u7acb\\u5e03\\u96f7\\u62c9\\u7f8e\\u9662\",\"lon\":\"9.1859243\",\"lat\":\"45.4654219\"},{\"scenicId\":\"1616\",\"tripId\":\"64\",\"name\":\"\\u7c73\\u5170\\u5927\\u5b66\",\"lon\":\"9.1945843\",\"lat\":\"45.4601435\"}],\"serviceList\":[],\"highlightList\":[{\"hlId\":\"407\",\"tripId\":\"64\",\"value\":\"\\u5728\\u5927\\u6559\\u5802\\u4e0a\\u4fef\\u77b0\\u57ce\\u5e02\"},{\"hlId\":\"408\",\"tripId\":\"64\",\"value\":\"\\u54c1\\u5c1d\\u610f\\u5927\\u5229\\u7f8e\\u98df\"}],\"includeDetailList\":[{\"detailId\":\"1517\",\"tripId\":\"64\",\"name\":\"\\u5730\\u966a\\u5bfc\\u6e38\",\"type\":\"1\"},{\"detailId\":\"1518\",\"tripId\":\"64\",\"name\":\"\\u966a\\u540c\\u7ffb\\u8bd1\",\"type\":\"1\"}],\"unIncludeDetailList\":[{\"detailId\":\"1519\",\"tripId\":\"64\",\"name\":\"\\u9910\\u996e\\u8d39\\u7528\",\"type\":\"2\"},{\"detailId\":\"1520\",\"tripId\":\"64\",\"name\":\"\\u4f4f\\u5bbf\",\"type\":\"2\"}],\"createPublisherInfo\":{\"nickname\":\"\\u9ad8\\u8574\\u73ca\",\"phone\":null,\"areaCode\":null,\"email\":\"gao-yun-shan@163.com\",\"sex\":\"2\",\"birthday\":\"0000-00-00\",\"headImg\":\"http:\\/\\/image.suiuu.com\\/suiuu_head\\/20150529144326_41513.jpg\",\"hobby\":\"\",\"countryId\":null,\"cityId\":null,\"profession\":\"\",\"school\":\"\",\"intro\":\"\",\"info\":\"\",\"travelCount\":\"0\",\"userSign\":\"30e72439290636b0b8b4fcc575c02f1e\",\"tripPublisherId\":\"62\",\"tripId\":\"64\",\"publisherId\":\"22\",\"cityName\":null,\"countryName\":null}}","status":"0","hobby":null},{"birthday":null,"orderNumber":"2015070899524810","orderId":"53","totalPrice":"630.00","travelCount":null,"serviceInfo":"[]","tripId":"180","school":null,"intro":null,"nickname":null,"startTime":"16:14:00","email":null,"basePrice":"630.00","info":null,"profession":null,"headImg":null,"sex":null,"userId":"a4c1406ff4cc382389f19bf6ec3e55c1","personCount":"2","beginDate":"2015-07-24","areaCode":null,"servicePrice":"0.00","createTime":"2015-07-08 16:13:00","phone":null,"userSign":null,"isDel":"0","tripJsonInfo":"{\"praise\":[],\"attention\":[],\"info\":{\"tripId\":\"180\",\"createPublisherId\":\"51\",\"createTime\":\"2015-06-19 12:07:11\",\"title\":\"\\u65b0\\u52a0\\u5761\\u2014\\u2014\\u9a6c\\u6765\\u897f\\u4e9a\\u5305\\u8f66\",\"titleImg\":\"http:\\/\\/image.suiuu.com\\/suiuu_trip\\/20150619040318_54948_reset.jpg\",\"countryId\":\"3759\",\"cityId\":\"4150\",\"lon\":null,\"lat\":null,\"basePrice\":\"630.00\",\"basePriceType\":\"2\",\"maxUserCount\":\"7\",\"isAirplane\":\"0\",\"isHotel\":\"0\",\"score\":\"0\",\"tripCount\":\"0\",\"startTime\":\"08:00:00\",\"endTime\":\"21:00:00\",\"travelTime\":\"2\",\"travelTimeType\":\"1\",\"intro\":\"\\u65b0\\u52a0\\u5761\\u2014\\u2014\\u9a6c\\u6765\\u897f\\u4e9a\\u5305\\u8f66\",\"info\":\"\\u65b0\\u52a0\\u5761\\u5230\\u9a6c\\u6765\\u897f\\u4e9a\\u65b0\\u5c71\\u5305\\u8f66\\u670d\\u52a1\\u3002\",\"tags\":\"\\u5bb6\\u5ead\",\"status\":\"1\",\"countryCname\":\"\\u65b0\\u52a0\\u5761\",\"countryEname\":\"Singapore\",\"cityCname\":\"\\u65b0\\u52a0\\u5761\\u5e02\",\"cityEname\":\"Singapore City\"},\"picList\":[{\"picId\":\"4309\",\"tripId\":\"180\",\"title\":null,\"url\":\"http:\\/\\/image.suiuu.com\\/suiuu_head\\/20150619040400_90755.jpeg\"}],\"priceList\":[],\"publisherList\":[{\"nickname\":\"\\u90ed\\u5c0f\\u5929\",\"phone\":null,\"areaCode\":null,\"email\":\"52368947@qq.com\",\"sex\":\"2\",\"birthday\":\"0000-00-00\",\"headImg\":\"http:\\/\\/www.suiuu.com\\/assets\\/images\\/user_default.png\",\"hobby\":\"\",\"countryId\":null,\"cityId\":null,\"profession\":\"\",\"school\":\"\",\"intro\":\"\",\"info\":\"\",\"travelCount\":\"0\",\"userSign\":\"fa93b36cbc57308db2eec1e5b467777e\",\"tripPublisherId\":\"193\",\"tripId\":\"180\",\"publisherId\":\"51\",\"cityName\":null,\"countryName\":null}],\"scenicList\":[{\"scenicId\":\"1813\",\"tripId\":\"180\",\"name\":\"\\u65b0\\u52a0\\u5761\",\"lon\":\"103.819836\",\"lat\":\"1.352083\"},{\"scenicId\":\"1814\",\"tripId\":\"180\",\"name\":\"\\u65b0\\u5c71\",\"lon\":\"103.7413591\",\"lat\":\"1.492659\"}],\"serviceList\":[],\"highlightList\":[{\"hlId\":\"922\",\"tripId\":\"180\",\"value\":\"\\u65b0\\u52a0\\u5761\\u53bb\\u9a6c\\u6765\\u897f\\u4e9a\\u65b0\\u5c71\\uff08\\u4e50\\u9ad8\\u4e50\\u56ed\\uff09\"}],\"includeDetailList\":[{\"detailId\":\"2486\",\"tripId\":\"180\",\"name\":\"\\u5305\\u8f66\\u8d39\\u7528\",\"type\":\"1\"}],\"unIncludeDetailList\":[{\"detailId\":\"2487\",\"tripId\":\"180\",\"name\":\"\\u5176\\u4ed6\\u672a\\u63d0\\u53ca\\u8d39\\u7528\",\"type\":\"2\"}],\"createPublisherInfo\":{\"nickname\":\"\\u90ed\\u5c0f\\u5929\",\"phone\":null,\"areaCode\":null,\"email\":\"52368947@qq.com\",\"sex\":\"2\",\"birthday\":\"0000-00-00\",\"headImg\":\"http:\\/\\/www.suiuu.com\\/assets\\/images\\/user_default.png\",\"hobby\":\"\",\"countryId\":null,\"cityId\":null,\"profession\":\"\",\"school\":\"\",\"intro\":\"\",\"info\":\"\",\"travelCount\":\"0\",\"userSign\":\"fa93b36cbc57308db2eec1e5b467777e\",\"tripPublisherId\":\"193\",\"tripId\":\"180\",\"publisherId\":\"51\",\"cityName\":null,\"countryName\":null}}","status":"0","hobby":null}]
     * message :
     * status : 1
     * token : 1455605ea84a13799fad85063438e7d3
     */
    private List<NotFinishedOrderData> data;
    private String message;
    private int status;
    private String token;

    public void setData(List<NotFinishedOrderData> data) {
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

    public List<NotFinishedOrderData> getData() {
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

    public static class NotFinishedOrderData {
        /**
         * birthday : null
         * orderNumber : 2015070649999748
         * orderId : 48
         * totalPrice : 746.00
         * travelCount : null
         * serviceInfo : [{"money":"2000.00","tripId":"105","serviceId":"422","title":"独家包车","type":"0"}]
         * tripId : 119
         * school : null
         * intro : null
         * nickname : null
         * startTime : 16:46:00
         * email : null
         * basePrice : 746.00
         * info : null
         * profession : null
         * headImg : null
         * sex : null
         * userId : a4c1406ff4cc382389f19bf6ec3e55c1
         * personCount : 1
         * beginDate : 2015-07-13
         * areaCode : null
         * servicePrice : 0.00
         * createTime : 2015-07-06 16:47:13
         * phone : null
         * userSign : null
         * isDel : 0
         * tripJsonInfo : {"praise":[],"attention":[],"info":{"tripId":"119","createPublisherId":"47","createTime":"2015-06-04 15:04:05","title":"\u8fd9\u79cd\u73a9\u6cd5\uff0c\u624d\u80fd\u91cc\u91cc\u5916\u5916\u73a9\u8f6c\u6089\u5c3c\u6b4c\u5267\u9662","titleImg":"http:\/\/image.suiuu.com\/suiuu_trip\/119_reset.jpg","countryId":"1083","cityId":"1124","lon":null,"lat":null,"basePrice":"746.00","basePriceType":"2","maxUserCount":"4","isAirplane":"0","isHotel":"0","score":"0","tripCount":"0","startTime":"10:00:00","endTime":"12:00:00","travelTime":"2","travelTimeType":"1","intro":"\u8fd9\u79cd\u73a9\u6cd5\uff0c\u624d\u80fd\u91cc\u91cc\u5916\u5916\u73a9\u8f6c\u6089\u5c3c\u6b4c\u5267\u9662","info":"\u4f53\u9a8c\u80cc\u666f\uff1a\n\u6089\u5c3c\u6b4c\u5267\u9662\u5750\u843d\u4e8e\u6089\u5c3c\u6e2f\uff0c\u56e0\u5176\u5927\u80c6\u7684\u5185\u5916\u8bbe\u8ba1\u4e14\u4e09\u9762\u73af\u6d77\uff0c\u662f\u4e16\u754c\u8457\u540d\u7684\u8868\u6f14\u827a\u672f\u4e2d\u5fc3\u3002\u53c8\u56e0\u5176\u72ec\u7279\u7684\u5e06\u8239\u9020\u578b\u4e0e\u80cc\u666f\u5904\u7684\u6089\u5c3c\u6e2f\u6e7e\u5927\u6865\u76f8\u6620\u6210\u8da3\uff0c\u65e9\u5df2\u6210\u4e3a\u6089\u5c3c\u6700\u5177\u7279\u8272\u7684\u5730\u6807\u6027\u5efa\u7b51\u3002\u4f5c\u4e3a\u6765\u5230\u6089\u5c3c\u4e00\u5b9a\u4e0d\u80fd\u9519\u8fc7\u7684\u6700\u4f1f\u5927\u5efa\u7b51\u4e4b\u4e00\uff0c\u95e8\u9053\u751f\u6d3b\u5bb6\u5c0f\u7f8a\u66f4\u53ef\u4ee5\u5e26\u7740\u60a8\u6df1\u5ea6\u7545\u6e38\u6b4c\u5267\u9662\uff0c\u611f\u53d7\u5b83\u80cc\u540e\u7684\u591a\u6837\u6545\u4e8b\u3002\n\n\u63a8\u8350\u7406\u7531\uff1a\n\u6089\u5c3c\u6b4c\u5267\u9662\u4e3b\u8981\u6709\u4e24\u4e2a\u4e3b\u5385\uff08\u97f3\u4e50\u5385\u548c\u6b4c\u5267\u5385\uff09\uff0c\u53e6\u6709\u4e00\u4e9b\u5c0f\u578b\u5267\u9662\u3001\u6f14\u51fa\u5385\u7b49\u9644\u5c5e\u8bbe\u65bd\u3002\u95e8\u9053\u751f\u6d3b\u5bb6\u5c0f\u7f8a\u4f1a\u4e3a\u60a8\u8be6\u7ec6\u8bb2\u89e3\u97f3\u4e50\u5385\u5185\u7684\u97f3\u6548\u8bbe\u8ba1\u3001\u9690\u853d\u7684\u4e16\u754c\u7ea7\u4e50\u5668\u548c\u5385\u5185\u53d1\u751f\u7684\u5947\u4eba\u5f02\u4e8b\uff1b\u4e5f\u4f1a\u5e26\u60a8\u7545\u6e38\u6b4c\u5267\u5385\uff0c\u4e3a\u60a8\u5c55\u793a\u6b4c\u5267\u8868\u6f14\u4e2d\u6240\u7528\u5230\u7684\u5927\u91cf\u9053\u5177\u548c\u5e03\u666f\uff0c\u66f4\u4e3a\u60a8\u4e00\u4e00\u6307\u51fa\u6b4c\u5267\u5385\u5185\u7684\u6697\u85cf\u673a\u5173\uff0c\u9886\u7565\u8bbe\u8ba1\u5e08\u7684\u5320\u5fc3\u72ec\u8fd0\uff1b\u5e76\u53ef\u4ee5\u8fd1\u8ddd\u79bb\u611f\u53d7\u4e00\u4e24\u4e2a\u5c0f\u8868\u6f14\u5385\u7684\u5185\u90e8\u6e38\u89c8\u3002\u5728\u89c2\u8d4f\u6089\u5c3c\u6b4c\u5267\u9662\u72ec\u7279\u8bbe\u8ba1\u7684\u540c\u65f6\uff0c\u5c0f\u7f8a\u4e5f\u4f1a\u5c06\u5f53\u521d\u4e39\u9ea6\u8bbe\u8ba1\u5e08\u5728\u8bbe\u8ba1\u8fd9\u4e2a\u5de5\u7a0b\u65f6\u6240\u9762\u5bf9\u7684\u620f\u5267\u6027\u6545\u4e8b\u5a13\u5a13\u9053\u6765\uff0c\u8ba9\u60a8\u771f\u6b63\u611f\u53d7\u4e0d\u4e00\u6837\u7684\u6089\u5c3c\u6b4c\u5267\u9662\u3002\n\n1\u3001\u53f9\u4e3a\u89c2\u6b62\u7684\u6b4c\u5267\u9662\u4e3b\u5385\n2\u3001\u6697\u85cf\u673a\u5173\u7684\u7b2c\u4e8c\u5927\u5385\n3\u3001\u7279\u6709\u7684\u5e06\u8239\u9020\u578b\u4e0e\u6089\u5c3c\u6e2f\u6e7e\u5927\u6865\u76f8\u6620\u6210\u8da3\n\n\u8d39\u7528\u5305\u542b\uff1a\n\u751f\u6d3b\u5bb6\u4f53\u9a8c\u6df1\u5ea6\u8bb2\u89e3 \n\u751f\u6d3b\u5bb6\u5c0f\u8d39 \n\u53c2\u89c2\u95e8\u7968\n\n\u6ce8\u610f\u4e8b\u9879:\n\u7531\u4e8e\u6089\u5c3c\u5267\u9662\u662f\u4e00\u4e2a\u6b63\u5728\u8fd0\u8425\u7684\u827a\u672f\u8868\u6f14\u4e2d\u5fc3\uff0c\u6709\u6f14\u51fa\u65f6\u4e0d\u5141\u8bb8\u4efb\u4f55\u6e38\u5ba2\u53c2\u89c2\uff0c\u6240\u4ee5\u6bcf\u5929\u4e0d\u540c\u65f6\u95f4\u6bb5\u53ef\u4ee5\u8fdb\u5165\u7684\u5385\u90fd\u4e0d\u4e00\u6837\uff0c\u4e0d\u4fdd\u8bc1\u4f1a\u53c2\u89c2\u5230\u6240\u6709\u7684\u8868\u6f14\u5385\uff08\u81f3\u5c112\u4e2a\uff09\u3002\u9884\u5b9a\u4f53\u9a8c\u5f53\u5929\u6211\u4f1a\u8ddf\u60a8\u786e\u8ba4\u3002\n\n\u4f53\u9a8c\u65f6\u95f4\uff1a\u6bcf\u5468\u65e5\n\n\u751f\u6d3b\u5bb6\u4ecb\u7ecd\uff1a\nChancy\u9648\u5c0f\u7f8a\u6765\u5230\u888b\u9f20\u56fd\u5df2\u7ecf5\u5e74\uff0c\u80cc\u5305\u6e38\u8fc7\u5927\u6fb3\u6751\uff0c\u62c9\u4e01\u7f8e\u6d32\u3002\u5728\u5b66\u897f\u73ed\u7259\u8bed\uff0c\u8df3salsa\u821e\uff0c\u7231\u745c\u4f3d\u548c\u6444\u5f71\uff01\u4f5c\u4e3a\u6089\u5c3c\u6b4c\u5267\u9662\u7684\u4e2d\u6587\u8bb2\u89e3\u5458\uff0c\u5e0c\u671b\u80fd\u5e26\u7ed9\u60a8\u6df1\u5ea6\u6e38\u7684\u4f53\u9a8c\uff01","tags":"\u535a\u7269\u9986,\u730e\u5947","status":"1","countryCname":"\u6fb3\u5927\u5229\u4e9a","countryEname":"Australia","cityCname":"\u6089\u5c3c","cityEname":"Sydney"},"picList":[{"picId":"3632","tripId":"119","title":null,"url":"http:\/\/image.suiuu.com\/suiuu_head\/20150610081848_37585.jpg"},{"picId":"3633","tripId":"119","title":null,"url":"http:\/\/image.suiuu.com\/suiuu_head\/20150610081651_80673.jpg"},{"picId":"3634","tripId":"119","title":null,"url":"http:\/\/image.suiuu.com\/user_card\/20150604150143_95672.jpg"},{"picId":"3635","tripId":"119","title":null,"url":"http:\/\/image.suiuu.com\/user_card\/20150604150129_61281.jpg"},{"picId":"3636","tripId":"119","title":null,"url":"http:\/\/image.suiuu.com\/user_card\/20150604150126_76764.jpg"},{"picId":"3637","tripId":"119","title":null,"url":"http:\/\/image.suiuu.com\/user_card\/20150604150123_71905.jpg"},{"picId":"3638","tripId":"119","title":null,"url":"http:\/\/image.suiuu.com\/user_card\/20150604150120_47136.jpg"}],"priceList":[],"publisherList":[{"nickname":"mendao","phone":"13901358226","areaCode":null,"email":"yikun@mendao.cn","sex":"2","birthday":"1980-01-01","headImg":"http:\/\/image.suiuu.com\/suiuu_head\/20150616011330_80262.jpg","hobby":"","countryId":"336","cityId":"337","profession":"\u5728\u7ebf\u4f53\u9a8c\u5e73\u53f0","school":"","intro":"\u9009\u62e9\u95e8\u9053\uff0c\u5176\u5b9e\u662f\u9009\u62e9\u4e00\u79cd\u751f\u6d3b\u7684\u6001\u5ea6","info":"\u95e8\u9053\uff08https:\/\/mendao.cn\/\uff09\u6210\u7acb\u4e8e2014\u5e749\u6708\uff0c\u603b\u90e8\u4f4d\u4e8e\u6fb3\u5927\u5229\u4e9a\u7684\u6089\u5c3c\u3002\n\n\u95e8\u9053\uff0c\u81f4\u529b\u4e8e\u6253\u9020\u4e3a\u5168\u7403\u7528\u6237\u63d0\u4f9b\u76ee\u7684\u5730\u4f53\u9a8c\u7684\u5728\u7ebf\u5e73\u53f0\u3002\n\n\u4f5c\u4e3a\u7528\u6237\uff0c\u95e8\u9053\u9080\u8bf7\u60a8\u6210\u4e3a\u95e8\u9053\u884c\u5bb6\uff0c\u901a\u8fc7\u95e8\u9053\u9884\u8ba2\u5177\u6709\u5f53\u5730\u7279\u8272\u7684\u4f53\u9a8c\uff0c\u8d34\u8fd1\u5f53\u5730\u751f\u6d3b\uff0c\u4e86\u89e3\u5f53\u5730\u6587\u5316\uff0c\u54c1\u5c1d\u5f53\u5730\u7f8e\u98df\uff0c\u6b23\u8d4f\u5f53\u5730\u7f8e\u666f\uff0c\u4eb2\u8eab\u53c2\u4e0e\u5404\u79cd\u5a31\u4e50\u4f11\u95f2\u6d3b\u52a8\uff0c\u5e76\u4e0e\u5f53\u5730\u4eba\u6210\u4e3a\u670b\u53cb.\n\n\u4f5c\u4e3a\u5f53\u5730\u4eba\uff0c\u95e8\u9053\u9080\u8bf7\u60a8\u6210\u4e3a\u95e8\u9053\u751f\u6d3b\u5bb6\uff0c\u901a\u8fc7\u95e8\u9053\u53d1\u5e03\u5206\u4eab\u60a8\u7684\u4f53\u9a8c\uff0c\u9080\u8bf7\u7528\u6237\u6765\u53c2\u4e0e\u3002\u4ee5\u60a8\u4e30\u5bcc\u7684\u751f\u6d3b\u7ecf\u9a8c\u3001\u70ed\u60c5\u7684\u751f\u6d3b\u6001\u5ea6\u548c\u53d1\u5e03\u7684\u6709\u8da3\u7684\u4f53\u9a8c\uff0c\u8ba9\u6bcf\u4e00\u4e2a\u53c2\u4e0e\u7684\u4eba\u90fd\u80fd\u611f\u53d7\u5230\u60a8\u751f\u6d3b\u7684\u8fd9\u7247\u571f\u5730\u7684\u795e\u5947\u548c\u667a\u6167\uff0c\u4ece\u60a8\u7684\u4f53\u9a8c\u4e2d\u611f\u53d7\u5230\u5f53\u5730\u4eba\u7684\u70ed\u60c5\u3001\u6e29\u99a8\u548c\u53cb\u597d\u3002\n\n\u95e8\u9053\u76f8\u4fe1\u6700\u597d\u7684\u751f\u6d3b\u4f53\u9a8c\u8981\u9760\u4eba\u548c\u4eba\u7684\u4ea4\u6d41\u548c\u5206\u4eab\u6765\u521b\u9020\u3002\n\u95e8\u9053\u4fdd\u969c\u6bcf\u4e2a\u4eba\u7684\u9690\u79c1\uff0c\u4e3a\u4e86\u6700\u5927\u9650\u5ea6\u7684\u4fdd\u8bc1\u7528\u6237\u4f53\u9a8c\u7684\u8212\u9002\u548c\u5b89\u5168\uff0c\u5355\u4e2a\u4f53\u9a8c\u4eba\u6570\u4e0d\u8d85\u8fc78\u4eba\uff0c\u540c\u4e00\u4e2a\u4f53\u9a8c\u4e2d\u53ea\u6709\u7528\u6237\u7684\u670b\u53cb\u548c\u5bb6\u4eba\uff0c\u9664\u975e\u7528\u6237\u540c\u610f\u52a0\u5165\u964c\u751f\u4eba\u3002\n\n\u8054\u7cfb\u65b9\u5f0f\n\u5fae\u4fe1\uff1amendaoau\n\u5fae\u535a\uff1aweibo.com\/men2dao4\n\u90ae\u4ef6\uff1acontact@mendao.com.au\n\u7535\u8bdd: +61 433 456 228","travelCount":"0","userSign":"c39679dc59293b1aa5efa0f65dec0a29","tripPublisherId":"131","tripId":"119","publisherId":"47","cityName":"\u5317\u4eac","countryName":"\u4e2d\u56fd"}],"scenicList":[{"scenicId":"1670","tripId":"119","name":"\u6089\u5c3c\u6b4c\u5267\u9662","lon":"116.285591","lat":"39.809334"}],"serviceList":[],"highlightList":[{"hlId":"574","tripId":"119","value":"\u901a\u5f80\u4e16\u754c\u4e03\u5927\u9057\u4ea7\u4e4b\u4e00\u7684\u6b4c\u5267\u9662\u5185\u573a"},{"hlId":"575","tripId":"119","value":"\u6f14\u594f\u5385360\u00b0\u65e0\u6b7b\u89d2\u89c2\u6469"},{"hlId":"576","tripId":"119","value":"\u91cc\u91cc\u5916\u5916\u4e86\u89e3\u6b4c\u5267\u9662"},{"hlId":"577","tripId":"119","value":"\u503e\u542c\u6b4c\u5267\u9662\u5916\u5efa\u7b51\u7684\u7531\u6765"},{"hlId":"578","tripId":"119","value":"\u4f53\u9a8c\u7ed3\u675f\uff0c\u53ef\u4ee5\u8ba9\u751f\u6d3b\u5bb6\u5e2e\u5fd9\u9884\u5b9a\u5f53\u5929\u8868\u6f14"}],"includeDetailList":[{"detailId":"1796","tripId":"119","name":"\u751f\u6d3b\u5bb6\u4f53\u9a8c\u6df1\u5ea6\u8bb2\u89e3","type":"1"},{"detailId":"1797","tripId":"119","name":"\u751f\u6d3b\u5bb6\u5c0f\u8d39","type":"1"},{"detailId":"1798","tripId":"119","name":"\u53c2\u89c2\u95e8\u7968","type":"1"},{"detailId":"1799","tripId":"119","name":"\u966a\u540c\u8bb2\u89e3","type":"1"}],"unIncludeDetailList":[{"detailId":"1800","tripId":"119","name":"\u516c\u5171\u4ea4\u901a\u8d39","type":"2"},{"detailId":"1801","tripId":"119","name":"\u8d2d\u7269\u4ea7\u751f\u7684\u8d39\u7528","type":"2"},{"detailId":"1802","tripId":"119","name":"\u4f4f\u5bbf","type":"2"},{"detailId":"1803","tripId":"119","name":"\u9910\u996e\u8d39\u7528","type":"2"},{"detailId":"1804","tripId":"119","name":"\u63a5\u9001\u673a","type":"2"}],"createPublisherInfo":{"nickname":"mendao","phone":"13901358226","areaCode":null,"email":"yikun@mendao.cn","sex":"2","birthday":"1980-01-01","headImg":"http:\/\/image.suiuu.com\/suiuu_head\/20150616011330_80262.jpg","hobby":"","countryId":"336","cityId":"337","profession":"\u5728\u7ebf\u4f53\u9a8c\u5e73\u53f0","school":"","intro":"\u9009\u62e9\u95e8\u9053\uff0c\u5176\u5b9e\u662f\u9009\u62e9\u4e00\u79cd\u751f\u6d3b\u7684\u6001\u5ea6","info":"\u95e8\u9053\uff08https:\/\/mendao.cn\/\uff09\u6210\u7acb\u4e8e2014\u5e749\u6708\uff0c\u603b\u90e8\u4f4d\u4e8e\u6fb3\u5927\u5229\u4e9a\u7684\u6089\u5c3c\u3002\n\n\u95e8\u9053\uff0c\u81f4\u529b\u4e8e\u6253\u9020\u4e3a\u5168\u7403\u7528\u6237\u63d0\u4f9b\u76ee\u7684\u5730\u4f53\u9a8c\u7684\u5728\u7ebf\u5e73\u53f0\u3002\n\n\u4f5c\u4e3a\u7528\u6237\uff0c\u95e8\u9053\u9080\u8bf7\u60a8\u6210\u4e3a\u95e8\u9053\u884c\u5bb6\uff0c\u901a\u8fc7\u95e8\u9053\u9884\u8ba2\u5177\u6709\u5f53\u5730\u7279\u8272\u7684\u4f53\u9a8c\uff0c\u8d34\u8fd1\u5f53\u5730\u751f\u6d3b\uff0c\u4e86\u89e3\u5f53\u5730\u6587\u5316\uff0c\u54c1\u5c1d\u5f53\u5730\u7f8e\u98df\uff0c\u6b23\u8d4f\u5f53\u5730\u7f8e\u666f\uff0c\u4eb2\u8eab\u53c2\u4e0e\u5404\u79cd\u5a31\u4e50\u4f11\u95f2\u6d3b\u52a8\uff0c\u5e76\u4e0e\u5f53\u5730\u4eba\u6210\u4e3a\u670b\u53cb.\n\n\u4f5c\u4e3a\u5f53\u5730\u4eba\uff0c\u95e8\u9053\u9080\u8bf7\u60a8\u6210\u4e3a\u95e8\u9053\u751f\u6d3b\u5bb6\uff0c\u901a\u8fc7\u95e8\u9053\u53d1\u5e03\u5206\u4eab\u60a8\u7684\u4f53\u9a8c\uff0c\u9080\u8bf7\u7528\u6237\u6765\u53c2\u4e0e\u3002\u4ee5\u60a8\u4e30\u5bcc\u7684\u751f\u6d3b\u7ecf\u9a8c\u3001\u70ed\u60c5\u7684\u751f\u6d3b\u6001\u5ea6\u548c\u53d1\u5e03\u7684\u6709\u8da3\u7684\u4f53\u9a8c\uff0c\u8ba9\u6bcf\u4e00\u4e2a\u53c2\u4e0e\u7684\u4eba\u90fd\u80fd\u611f\u53d7\u5230\u60a8\u751f\u6d3b\u7684\u8fd9\u7247\u571f\u5730\u7684\u795e\u5947\u548c\u667a\u6167\uff0c\u4ece\u60a8\u7684\u4f53\u9a8c\u4e2d\u611f\u53d7\u5230\u5f53\u5730\u4eba\u7684\u70ed\u60c5\u3001\u6e29\u99a8\u548c\u53cb\u597d\u3002\n\n\u95e8\u9053\u76f8\u4fe1\u6700\u597d\u7684\u751f\u6d3b\u4f53\u9a8c\u8981\u9760\u4eba\u548c\u4eba\u7684\u4ea4\u6d41\u548c\u5206\u4eab\u6765\u521b\u9020\u3002\n\u95e8\u9053\u4fdd\u969c\u6bcf\u4e2a\u4eba\u7684\u9690\u79c1\uff0c\u4e3a\u4e86\u6700\u5927\u9650\u5ea6\u7684\u4fdd\u8bc1\u7528\u6237\u4f53\u9a8c\u7684\u8212\u9002\u548c\u5b89\u5168\uff0c\u5355\u4e2a\u4f53\u9a8c\u4eba\u6570\u4e0d\u8d85\u8fc78\u4eba\uff0c\u540c\u4e00\u4e2a\u4f53\u9a8c\u4e2d\u53ea\u6709\u7528\u6237\u7684\u670b\u53cb\u548c\u5bb6\u4eba\uff0c\u9664\u975e\u7528\u6237\u540c\u610f\u52a0\u5165\u964c\u751f\u4eba\u3002\n\n\u8054\u7cfb\u65b9\u5f0f\n\u5fae\u4fe1\uff1amendaoau\n\u5fae\u535a\uff1aweibo.com\/men2dao4\n\u90ae\u4ef6\uff1acontact@mendao.com.au\n\u7535\u8bdd: +61 433 456 228","travelCount":"0","userSign":"c39679dc59293b1aa5efa0f65dec0a29","tripPublisherId":"131","tripId":"119","publisherId":"47","cityName":"\u5317\u4eac","countryName":"\u4e2d\u56fd"}}
         * status : 0
         * hobby : null
         */
        private String birthday;
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

        public static class ServiceInfoEntity {
            /**
             * money : 2000.00
             * tripId : 105
             * serviceId : 422
             * title : 独家包车
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

    }

}