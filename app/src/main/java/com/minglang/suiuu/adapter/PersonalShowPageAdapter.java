package com.minglang.suiuu.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.List;

/**
 * 个人中心页面适配器
 * <p/>
 * Created by Administrator on 2015/5/11.
 */
public class PersonalShowPageAdapter extends PagerAdapter {

    private Context context;

    private List<View> list;

    public PersonalShowPageAdapter(Context context, List<View> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        GridView gridView = (GridView) list.get(position);
        container.addView(gridView);
        return gridView;
    }
}
