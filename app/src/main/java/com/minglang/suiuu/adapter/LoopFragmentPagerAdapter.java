package com.minglang.suiuu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * LoopFragment中嵌套的ViewPager+Fragment的适配器
 * <p/>
 * Created by LZY on 2015/3/18 0018.
 */
public class LoopFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public LoopFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
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

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
