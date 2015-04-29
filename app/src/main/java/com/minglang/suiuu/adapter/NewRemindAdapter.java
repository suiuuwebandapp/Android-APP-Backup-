package com.minglang.suiuu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by LZY on 2015/3/24 0024.
 */
public class NewRemindAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public NewRemindAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
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
}
