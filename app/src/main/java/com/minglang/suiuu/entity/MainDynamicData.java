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
    public MainDynamicDataUserBase userDynamic;
    //今日之星数据
    public MainDynamicDataRecommendUserBase recommendUser;
    //热门推荐数据
    public MainDynamicDataRecommendTravelBase recommendTravel;

    public List<MainDynamicDataLoop> getCircleDynamic() {
        return circleDynamic;
    }

    public MainDynamicDataUserBase getUserDynamic() {
        return userDynamic;
    }

    public MainDynamicDataRecommendUserBase getRecommendUser() {
        return recommendUser;
    }

    public MainDynamicDataRecommendTravelBase getRecommendTravel() {
        return recommendTravel;
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
