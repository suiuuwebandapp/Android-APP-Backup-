package com.minglang.suiuu.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 重写ScrollView，隐藏滚动条
 * <p/>
 * Created by Administrator on 2015/4/23.
 */
public class NoScrollBarGridView extends GridView {
    public NoScrollBarGridView(Context context) {
        super(context);
    }

    public NoScrollBarGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollBarGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
