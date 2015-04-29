package com.minglang.suiuu.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2015/4/20.
 */
public class LoopScrollPagerAdapter extends PagerAdapter {

    private Context context;

    private List<ImageView> list;

    public LoopScrollPagerAdapter(Context context, List<ImageView> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = list.get(position);
        container.addView(imageView);
        return imageView;
    }
}
