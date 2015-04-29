package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/4/27.
 */
public class AttentionLoopData {

    public String cName;

    public String cId;

    public String cpic;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getCpic() {
        return cpic;
    }

    public void setCpic(String cpic) {
        this.cpic = cpic;
    }

    @Override
    public String toString() {
        return "AttentionThemeData{" +
                "cName='" + cName + '\'' +
                ", cId='" + cId + '\'' +
                ", cpic='" + cpic + '\'' +
                '}';
    }
}
