package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/5/13.
 */
public class CountryAssistData {

    public String id;

    public String cname;

    public String ename;

    public String code;

    public String areaCode;

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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    @Override
    public String toString() {
        return "CountryAssistData{" +
                "id='" + id + '\'' +
                ", cname='" + cname + '\'' +
                ", ename='" + ename + '\'' +
                ", code='" + code + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", firstLetter='" + firstLetter + '\'' +
                '}';
    }
}
