package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/8/24.
 * <p/>
 * 用户绑定帐号数据实体类
 */
public class AccountInfo {

    /**
     * data : [{"accountId":"2","createTime":"2015-06-25 15:09:54","updateTime":"2015-06-25 15:09:54","type":"2","userId":"a878aec71a73356a83f2c02474c789b7","isDel":"0","account":"dadixu@hotmail.com","username":"徐大地"},{"accountId":"3","createTime":"2015-06-25 15:10:43","updateTime":"2015-06-25 15:10:43","type":"1","userId":"a878aec71a73356a83f2c02474c789b7","isDel":"0","account":"ogLOhuCrH6DPJgSxXv-jKbobhjj8","username":"徐大地"}]
     * message :
     * status : 1
     * token : c832b3c505b31eecfef169d08c98627d
     */
    private List<AccountInfoData> data;
    private String message;
    private int status;
    private String token;

    public void setData(List<AccountInfoData> data) {
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

    public List<AccountInfoData> getData() {
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

    public static class AccountInfoData {
        /**
         * accountId : 2
         * createTime : 2015-06-25 15:09:54
         * updateTime : 2015-06-25 15:09:54
         * type : 2
         * userId : a878aec71a73356a83f2c02474c789b7
         * isDel : 0
         * account : dadixu@hotmail.com
         * username : 徐大地
         */
        private String accountId;
        private String createTime;
        private String updateTime;
        private String type;
        private String userId;
        private String isDel;
        private String account;
        private String username;

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setIsDel(String isDel) {
            this.isDel = isDel;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public String getType() {
            return type;
        }

        public String getUserId() {
            return userId;
        }

        public String getIsDel() {
            return isDel;
        }

        public String getAccount() {
            return account;
        }

        public String getUsername() {
            return username;
        }
    }

}