package com.minglang.suiuu.customview;

/**
 * 项目名称：Android-APP
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/4/28 21:13
 * 修改人：Administrator
 * 修改时间：2015/4/28 21:13
 * 修改备注：
 */
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class NoScrollBarListView extends ListView {

    public NoScrollBarListView(Context context) {
        super(context);
    }

    public NoScrollBarListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollBarListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}