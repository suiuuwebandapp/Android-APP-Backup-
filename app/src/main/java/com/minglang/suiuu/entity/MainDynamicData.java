package com.minglang.suiuu.entity;

import java.util.List;

/**
 * 首页动态数据部分实体类
 * <p/>
 * Created by Administrator on 2015/4/29.
 */
public class MainDynamicData {

    //圈子动态数据
    public List<MainDynamicDataLoop> circleDynamic;
    //关注动态数据
    public List<MainDynamicDataUser> userDynamic;
    //今日之星数据
    public List<MainDynamicDataRecommendUser> recommendUser;

    public List<MainDynamicDataLoop> getCircleDynamic() {
        return circleDynamic;
    }

    public void setCircleDynamic(List<MainDynamicDataLoop> circleDynamic) {
        this.circleDynamic = circleDynamic;
    }

    public List<MainDynamicDataUser> getUserDynamic() {
        return userDynamic;
    }

    public void setUserDynamic(List<MainDynamicDataUser> userDynamic) {
        this.userDynamic = userDynamic;
    }

    public List<MainDynamicDataRecommendUser> getRecommendUser() {
        return recommendUser;
    }

    public void setRecommendUser(List<MainDynamicDataRecommendUser> recommendUser) {
        this.recommendUser = recommendUser;
    }

    @Override
    public String toString() {
        return "MainDynamicData{" +
                "circleDynamic=" + circleDynamic +
                ", userDynamic=" + userDynamic +
                ", recommendUser=" + recommendUser +
                '}';
    }
}
