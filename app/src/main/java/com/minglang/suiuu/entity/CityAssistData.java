package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/5/13.
 */
public class CityAssistData {

    public String id;

    public String cname;

    public String ename;

    public String code;

    public String countryId;

    public String firstLetter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    @Override
    public String toString() {
        return "CityAssistData{" +
                "id='" + id + '\'' +
                ", cname='" + cname + '\'' +
                ", ename='" + ename + '\'' +
                ", code='" + code + '\'' +
                ", countryId='" + countryId + '\'' +
                ", firstLetter='" + firstLetter + '\'' +
                '}';
    }
}
