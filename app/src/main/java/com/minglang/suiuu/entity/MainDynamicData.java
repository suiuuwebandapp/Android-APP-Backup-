package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/4/29.
 */
public class MainDynamicData {

    public MainDynamicDataCircleDynamic circleDynamic;

    public List<MainDynamicDataUserDynamic> userDynamic;

    public List<MainDynamicDataRecommendUser> recommendUser;

    public MainDynamicDataCircleDynamic getCircleDynamic() {
        return circleDynamic;
    }

    public void setCircleDynamic(MainDynamicDataCircleDynamic circleDynamic) {
        this.circleDynamic = circleDynamic;
    }

    public List<MainDynamicDataUserDynamic> getUserDynamic() {
        return userDynamic;
    }

    public void setUserDynamic(List<MainDynamicDataUserDynamic> userDynamic) {
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
