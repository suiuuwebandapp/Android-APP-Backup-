package com.minglang.suiuu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 项目名称：Android-APP
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/4/27 17:00
 * 修改人：Administrator
 * 修改时间：2015/4/27 17:00
 * 修改备注：
 */
public class MyGridView extends GridView {
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
