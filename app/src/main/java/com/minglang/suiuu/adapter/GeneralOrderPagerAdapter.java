package com.minglang.suiuu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/7/27.
 * <p/>
 * 普通用户订单相关页面适配器
 */
public class GeneralOrderPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titleList;

    public GeneralOrderPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        if (fragmentList != null && fragmentList.size() > 0) {
            return fragmentList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (fragmentList != null && fragmentList.size() > 0) {
            return fragmentList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

}