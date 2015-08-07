package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by a on 2015/8/6.
 * 订单消息数据实体类
 */
public class MsgOrder {

    /**
     * data : {"trip":{"msg":{"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"1"},"data":[{"rType":"1","headImg":"http://www.suiuu.com/assets/images/user_default.png","remindId":"3","relativeId":"45","nickname":"15111993537","title":null,"createUserSign":"22e64460c94f3b44bc0a8790ed146d3b","content":"","url":"","relativeType":"2"}]},"order":{"msg":{"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"1"},"data":[{"rType":"7","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","remindId":"4","relativeId":"1","nickname":"dorlen","createUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","content":"","url":"","relativeType":"2"}]}}
     * message :
     * status : 1
     * token : ff8ec5e64f795102fdafec4c9c5efb53
     */
    private MsgOrderData data;
    private String message;
    private int status;
    private String token;

    public void setData(MsgOrderData data) {
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

    public MsgOrderData getData() {
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

    public static class MsgOrderData {
        /**
         * trip : {"msg":{"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"1"},"data":[{"rType":"1","headImg":"http://www.suiuu.com/assets/images/user_default.png","remindId":"3","relativeId":"45","nickname":"15111993537","title":null,"createUserSign":"22e64460c94f3b44bc0a8790ed146d3b","content":"","url":"","relativeType":"2"}]}
         * order : {"msg":{"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"1"},"data":[{"rType":"7","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","remindId":"4","relativeId":"1","nickname":"dorlen","createUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","content":"","url":"","relativeType":"2"}]}
         */
        private TripEntity trip;
        private OrderEntity order;

        public void setTrip(TripEntity trip) {
            this.trip = trip;
        }

        public void setOrder(OrderEntity order) {
            this.order = order;
        }

        public TripEntity getTrip() {
            return trip;
        }

        public OrderEntity getOrder() {
            return order;
        }

        public static class TripEntity {
            /**
             * msg : {"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"1"}
             * data : [{"rType":"1","headImg":"http://www.suiuu.com/assets/images/user_default.png","remindId":"3","relativeId":"45","nickname":"15111993537","title":null,"createUserSign":"22e64460c94f3b44bc0a8790ed146d3b","content":"","url":"","relativeType":"2"}]
             */
            private MsgEntity msg;
            private List<MsgTripItemData> data;

            public void setMsg(MsgEntity msg) {
                this.msg = msg;
            }

            public void setData(List<MsgTripItemData> data) {
                this.data = data;
            }

            public MsgEntity getMsg() {
                return msg;
            }

            public List<MsgTripItemData> getData() {
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

            public static class MsgTripItemData {
                /**
                 * rType : 1
                 * headImg : http://www.suiuu.com/assets/images/user_default.png
                 * remindId : 3
                 * relativeId : 45
                 * nickname : 15111993537
                 * title : null
                 * createUserSign : 22e64460c94f3b44bc0a8790ed146d3b
                 * content :
                 * url :
                 * relativeType : 2
                 */
                private String rType;
                private String headImg;
                private String remindId;
                private String relativeId;
                private String nickname;
                private String title;
                private String createUserSign;
                private String content;
                private String url;
                private String relativeType;

                public void setRType(String rType) {
                    this.rType = rType;
                }

                public void setHeadImg(String headImg) {
                    this.headImg = headImg;
                }

                public void setRemindId(String remindId) {
                    this.remindId = remindId;
                }

                public void setRelativeId(String relativeId) {
                    this.relativeId = relativeId;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setCreateUserSign(String createUserSign) {
                    this.createUserSign = createUserSign;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public void setRelativeType(String relativeType) {
                    this.relativeType = relativeType;
                }

                public String getRType() {
                    return rType;
                }

                public String getHeadImg() {
                    return headImg;
                }

                public String getRemindId() {
                    return remindId;
                }

                public String getRelativeId() {
                    return relativeId;
                }

                public String getNickname() {
                    return nickname;
                }

                public String getTitle() {
                    return title;
                }

                public String getCreateUserSign() {
                    return createUserSign;
                }

                public String getContent() {
                    return content;
                }

                public String getUrl() {
                    return url;
                }

                public String getRelativeType() {
                    return relativeType;
                }
            }
        }

        public static class OrderEntity {
            /**
             * msg : {"sortName":null,"showAll":false,"startRow":0,"sortType":"asc","pageSize":10,"currentPage":1,"totalCount":"1"}
             * data : [{"rType":"7","headImg":"http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg","remindId":"4","relativeId":"1","nickname":"dorlen","createUserSign":"a4c1406ff4cc382389f19bf6ec3e55c1","content":"","url":"","relativeType":"2"}]
             */
            private MsgEntity msg;
            private List<MsgOrderItemData> data;

            public void setMsg(MsgEntity msg) {
                this.msg = msg;
            }

            public void setData(List<MsgOrderItemData> data) {
                this.data = data;
            }

            public MsgEntity getMsg() {
                return msg;
            }

            public List<MsgOrderItemData> getData() {
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

            public static class MsgOrderItemData {
                /**
                 * rType : 7
                 * headImg : http://image.suiuu.com/suiuu_head/20150519053006_33633.jpg
                 * remindId : 4
                 * relativeId : 1
                 * nickname : dorlen
                 * createUserSign : a4c1406ff4cc382389f19bf6ec3e55c1
                 * content :
                 * url :
                 * relativeType : 2
                 */
                private String rType;
                private String headImg;
                private String remindId;
                private String relativeId;
                private String nickname;
                private String createUserSign;
                private String content;
                private String url;
                private String relativeType;

                public void setRType(String rType) {
                    this.rType = rType;
                }

                public void setHeadImg(String headImg) {
                    this.headImg = headImg;
                }

                public void setRemindId(String remindId) {
                    this.remindId = remindId;
                }

                public void setRelativeId(String relativeId) {
                    this.relativeId = relativeId;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public void setCreateUserSign(String createUserSign) {
                    this.createUserSign = createUserSign;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public void setRelativeType(String relativeType) {
                    this.relativeType = relativeType;
                }

                public String getRType() {
                    return rType;
                }

                public String getHeadImg() {
                    return headImg;
                }

                public String getRemindId() {
                    return remindId;
                }

                public String getRelativeId() {
                    return relativeId;
                }

                public String getNickname() {
                    return nickname;
                }

                public String getCreateUserSign() {
                    return createUserSign;
                }

                public String getContent() {
                    return content;
                }

                public String getUrl() {
                    return url;
                }

                public String getRelativeType() {
                    return relativeType;
                }

            }

        }

    }

}