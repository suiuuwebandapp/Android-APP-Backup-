package com.minglang.suiuu.entity;

/**
 * Created by Administrator on 2015/5/6.
 */
public class RecommendLoopData {

    public String cId;

    public String cName;

    public String cType;

    public String cpic;

    public String getcId() {
        return cId;
    }

    public String getcName() {
        return cName;
    }

    public String getcType() {
        return cType;
    }

    public String getCpic() {
        return cpic;
    }

    @Override
    public String toString() {
        return "RecommendLoopData{" +
                "cId='" + cId + '\'' +
                ", cName='" + cName + '\'' +
                ", cType='" + cType + '\'' +
                ", cpic='" + cpic + '\'' +
                '}';
    }
}
