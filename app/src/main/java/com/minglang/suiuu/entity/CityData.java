package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/5/13.
 */
public class CityData {

    public String id;

    public String cname;

    public String ename;

    public String code;

    public String countryId;

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

    @Override
    public String toString() {
        return "CityData{" +
                "id='" + id + '\'' +
                ", cname='" + cname + '\'' +
                ", ename='" + ename + '\'' +
                ", code='" + code + '\'' +
                ", countryId='" + countryId + '\'' +
                '}';
    }
}
