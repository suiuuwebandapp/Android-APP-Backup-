package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/4/21.
 */
public class LoopData {

    public String cId;

    public String cType;

    public String cName;

    public String cpic;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getCpic() {
        return cpic;
    }

    public void setCpic(String cpic) {
        this.cpic = cpic;
    }

    @Override
    public String toString() {
        return "ThemeData{" +
                "cId='" + cId + '\'' +
                ", cType='" + cType + '\'' +
                ", cName='" + cName + '\'' +
                ", cpic='" + cpic + '\'' +
                '}';
    }
}
