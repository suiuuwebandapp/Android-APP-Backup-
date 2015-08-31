package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/8/31.
 *
 * 转账信息
 */
public class TransferAccounts {

    /**
     * status : 1
     * data : {"list":[{"accountRecordId":"1","userId":"a4c1406ff4cc382389f19bf6ec3e55c1","type":"1","relateId":null,"info":"富士山周边游","money":"4800.00","recordTime":"2015-06-25 18:27:13","status":null},{"accountRecordId":"2","userId":"a4c1406ff4cc382389f19bf6ec3e55c1","type":"1","relateId":null,"info":"法国最浪漫温馨之路","money":"2100.00","recordTime":"2015-06-26 16:52:39","status":null}],"msg":{"currentPage":1,"startRow":0,"pageSize":10,"sortName":null,"sortType":"asc","totalCount":"2","showAll":false}}
     * message :
     * token : fb91ce5cb446d969eeda8c17c3650754
     */

    private int status;
    private TransferAccountsData data;
    private String message;
    private String token;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(TransferAccountsData data) {
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

    public TransferAccountsData getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public static class TransferAccountsData {
        /**
         * list : [{"accountRecordId":"1","userId":"a4c1406ff4cc382389f19bf6ec3e55c1","type":"1","relateId":null,"info":"富士山周边游","money":"4800.00","recordTime":"2015-06-25 18:27:13","status":null},{"accountRecordId":"2","userId":"a4c1406ff4cc382389f19bf6ec3e55c1","type":"1","relateId":null,"info":"法国最浪漫温馨之路","money":"2100.00","recordTime":"2015-06-26 16:52:39","status":null}]
         * msg : {"currentPage":1,"startRow":0,"pageSize":10,"sortName":null,"sortType":"asc","totalCount":"2","showAll":false}
         */

        private MsgEntity msg;
        private List<TransferAccountsItemData> list;

        public void setMsg(MsgEntity msg) {
            this.msg = msg;
        }

        public void setList(List<TransferAccountsItemData> list) {
            this.list = list;
        }

        public MsgEntity getMsg() {
            return msg;
        }

        public List<TransferAccountsItemData> getList() {
            return list;
        }

        public static class MsgEntity {
            /**
             * currentPage : 1
             * startRow : 0
             * pageSize : 10
             * sortName : null
             * sortType : asc
             * totalCount : 2
             * showAll : false
             */

            private int currentPage;
            private int startRow;
            private int pageSize;
            private Object sortName;
            private String sortType;
            private String totalCount;
            private boolean showAll;

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public void setStartRow(int startRow) {
                this.startRow = startRow;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public void setSortName(Object sortName) {
                this.sortName = sortName;
            }

            public void setSortType(String sortType) {
                this.sortType = sortType;
            }

            public void setTotalCount(String totalCount) {
                this.totalCount = totalCount;
            }

            public void setShowAll(boolean showAll) {
                this.showAll = showAll;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public int getStartRow() {
                return startRow;
            }

            public int getPageSize() {
                return pageSize;
            }

            public Object getSortName() {
                return sortName;
            }

            public String getSortType() {
                return sortType;
            }

            public String getTotalCount() {
                return totalCount;
            }

            public boolean getShowAll() {
                return showAll;
            }
        }

        public static class TransferAccountsItemData {
            /**
             * accountRecordId : 1
             * userId : a4c1406ff4cc382389f19bf6ec3e55c1
             * type : 1
             * relateId : null
             * info : 富士山周边游
             * money : 4800.00
             * recordTime : 2015-06-25 18:27:13
             * status : null
             */

            private String accountRecordId;
            private String userId;
            private String type;
            private Object relateId;
            private String info;
            private String money;
            private String recordTime;
            private Object status;

            public void setAccountRecordId(String accountRecordId) {
                this.accountRecordId = accountRecordId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setRelateId(Object relateId) {
                this.relateId = relateId;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public void setRecordTime(String recordTime) {
                this.recordTime = recordTime;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public String getAccountRecordId() {
                return accountRecordId;
            }

            public String getUserId() {
                return userId;
            }

            public String getType() {
                return type;
            }

            public Object getRelateId() {
                return relateId;
            }

            public String getInfo() {
                return info;
            }

            public String getMoney() {
                return money;
            }

            public String getRecordTime() {
                return recordTime;
            }

            public Object getStatus() {
                return status;
            }
        }
    }

}