package com.minglang.suiuu.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2015/4/30.
 */
public abstract class LinearLayoutBaseAdapter {

    private List<? extends Object> list;
    private Context context;

    public LinearLayoutBaseAdapter(Context context, List<? extends Object> list) {
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
     * @param position
     * @return
     */
    public abstract View getView(int position);

}
