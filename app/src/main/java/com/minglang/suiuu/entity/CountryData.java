package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/5/13.
 */
public class CountryData implements Comparable<CountryData>{

    public String id;

    public String cname;

    public String ename;

    public String code;

    public String areaCode;

    public String getId() {
        return id;
    }

    public String getCname() {
        return cname;
    }

    public String getEname() {
        return ename;
    }

    public String getCode() {
        return code;
    }

    public String getAreaCode() {
        return areaCode;
    }

    @Override
    public String toString() {
        return "CountryData{" +
                "id='" + id + '\'' +
                ", cname='" + cname + '\'' +
                ", ename='" + ename + '\'' +
                ", code='" + code + '\'' +
                ", areaCode='" + areaCode + '\'' +
                '}';
    }

    @Override
    public int compareTo(CountryData another) {
        return 0;
    }
}
