package com.minglang.suiuu.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/4/29.
 */
public class MainDynamicDataCircleDynamic {

    public List<MainDynamicDataCircleDynamicTheme> theme;

    public List<MainDynamicDataCircleDynamicAddress> address;

    public List<MainDynamicDataCircleDynamicTheme> getTheme() {
        return theme;
    }

    public void setTheme(List<MainDynamicDataCircleDynamicTheme> theme) {
        this.theme = theme;
    }

    public List<MainDynamicDataCircleDynamicAddress> getAddress() {
        return address;
    }

    public void setAddress(List<MainDynamicDataCircleDynamicAddress> address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MainDynamicDataCircleDynamic{" +
                "theme=" + theme +
                ", address=" + address +
                '}';
    }
}
