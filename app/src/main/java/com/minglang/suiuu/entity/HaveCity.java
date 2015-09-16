package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 * <p/>
 * 已有的城市数据实体类
 */
public class HaveCity {

    /**
     * status : 1
     * data : [{"qCityId":"4126","cname":"巴明吉-班戈","ename":"Baming Ji - Bangor"}]
     * message :
     * token : a5b1af5676075833eaab0e9fffc9d6f6
     */

    private int status;
    private String message;
    private String token;
    private List<HaveCityData> data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setData(List<HaveCityData> data) {
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

    public List<HaveCityData> getData() {
        return data;
    }

    public static class HaveCityData {
        /**
         * qCityId : 4126
         * cname : 巴明吉-班戈
         * ename : Baming Ji - Bangor
         */

        private String qCityId;
        private String cname;
        private String ename;

        public void setQCityId(String qCityId) {
            this.qCityId = qCityId;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getQCityId() {
            return qCityId;
        }

        public String getCname() {
            return cname;
        }

        public String getEname() {
            return ename;
        }

    }
}