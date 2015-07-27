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
public class MyOrderListPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private List<String> titleList;

    public MyOrderListPageAdapter(FragmentManager fm, List<Fragment> list, List<String> titleList) {
        super(fm);
        this.list = list;
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Fragment getItem(int i) {
        if (list != null && list.size() > 0) {
            return list.get(i);
        } else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

}