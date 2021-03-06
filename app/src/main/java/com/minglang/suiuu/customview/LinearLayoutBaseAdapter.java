package com.minglang.suiuu.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * 用LinearLayout实现ListView的适配器
 * Created by Administrator on 2015/4/30.
 */
public abstract class LinearLayoutBaseAdapter {

    private List<?> list;
    private Context context;

    public LinearLayoutBaseAdapter(Context context, List<?> list) {
        this.context = context;
        this.list = list;
    }

    public LayoutInflater getLayoutInflater() {
        if (context != null) {
            return LayoutInflater.from(context);
        }

        return null;
    }

    public int getCount() {
        if (list != null) {
            return list.size();
        }

        return 0;
    }

    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }

        return null;
    }

    /**
     * 供子类复写
     *
     * @param position 位置
     * @return item的View
     */
    public abstract View getView(int position);

}
