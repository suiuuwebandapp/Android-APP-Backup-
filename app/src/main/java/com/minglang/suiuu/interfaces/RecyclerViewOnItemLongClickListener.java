package com.minglang.suiuu.interfaces;

import android.view.View;

/**
 * Created by Administrator on 2015/7/31.
 * <p/>
 * RecyclerView的Item的长按点击回调接口
 */
public interface RecyclerViewOnItemLongClickListener {
    void onItemLongClick(View view, int position);
}