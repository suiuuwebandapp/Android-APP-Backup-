package com.minglang.suiuu.entity;

public class HaveAssistData {

    /**
     * qCountryId : 336
     * cname : 中国
     * ename : China
     */

    private String qCountryId;
    private String cname;
    private String ename;
    private String firstLetter;

    public void setQCountryId(String qCountryId) {
        this.qCountryId = qCountryId;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
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

    public String getFirstLetter() {
        return firstLetter;
    }

}