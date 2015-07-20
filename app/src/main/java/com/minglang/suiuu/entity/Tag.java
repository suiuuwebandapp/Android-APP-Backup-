package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 系统Tag的数据实体类
 * <p/>
 * Created by Administrator on 2015/7/20.
 */
public class Tag {

    /**
     * data : [{"tType":"3","tName":"test","tId":"1"},{"tType":"3","tName":"test3","tId":"2"}]
     * message :
     * status : 1
     * token : 00d36acbf7b012e9d7e9cc3cd7673714
     */
    private List<TagData> data;
    private String message;
    private int status;
    private String token;

    public void setData(List<TagData> data) {
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

    public List<TagData> getData() {
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

    public static class TagData {
        /**
         * tType : 3
         * tName : test
         * tId : 1
         */
        private String tType;
        private String tName;
        private String tId;

        public void setTType(String tType) {
            this.tType = tType;
        }

        public void setTName(String tName) {
            this.tName = tName;
        }

        public void setTId(String tId) {
            this.tId = tId;
        }

        public String getTType() {
            return tType;
        }

        public String getTName() {
            return tName;
        }

        public String getTId() {
            return tId;
        }
    }

}