package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 * 已有的国家数据实体类
 */
public class HaveCountry {

    /**
     * status : 1
     * data : [{"qCountryId":"336","cname":"中国","ename":"China"}]
     * message :
     * token : 45ebb5e6da02f2f3ee06125c4760120a
     */

    private int status;
    private String message;
    private String token;
    private List<HaveCountryData> data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setData(List<HaveCountryData> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public List<HaveCountryData> getData() {
        return data;
    }

    public static class HaveCountryData {
        /**
         * qCountryId : 336
         * cname : 中国
         * ename : China
         */

        private String qCountryId;
        private String cname;
        private String ename;

        public void setQCountryId(String qCountryId) {
            this.qCountryId = qCountryId;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getQCountryId() {
            return qCountryId;
        }

        public String getCname() {
            return cname;
        }

        public String getEname() {
            return ename;
        }
    }
}