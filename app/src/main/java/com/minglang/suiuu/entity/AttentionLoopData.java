package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/4/27.
 */
public class AttentionLoopData {

    public String cName;

    public String cId;

    public String cpic;

    public String cType;

    public String getcName() {
        return cName;
    }

    public String getcId() {
        return cId;
    }

    public String getCpic() {
        return cpic;
    }

    public String getcType() {
        return cType;
    }

    @Override
    public String toString() {
        return "AttentionLoopData{" +
                "cName='" + cName + '\'' +
                ", cId='" + cId + '\'' +
                ", cpic='" + cpic + '\'' +
                ", cType='" + cType + '\'' +
                '}';
    }
}
