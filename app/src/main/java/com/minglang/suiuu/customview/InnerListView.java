package com.minglang.suiuu.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

public class InnerListView extends ListView {

    ScrollView parentScrollView;

    public ScrollView getParentScrollView() {
        return parentScrollView;
    }

    public void setParentScrollView(ScrollView parentScrollView) {
        this.parentScrollView = parentScrollView;
    }

    private int maxHeight;

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public InnerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (maxHeight > -1) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int currentY;

    @Override
    public boolean onInterceptTouchEvent(@NonNull MotionEvent ev) {
        if (parentScrollView == null) {
            return super.onInterceptTouchEvent(ev);
        } else {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    currentY = (int) ev.getY();
                    setParentScrollAble(false);
                    return super.onInterceptTouchEvent(ev);
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:

                case MotionEvent.ACTION_CANCEL:
                    setParentScrollAble(true);
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 获取listView的滚动位置，如果直接用getScrollY();一直是0。所以要自己算下<br>
     *
     * @return listView的滚动位置
     */
    private int getTrueScrollY() {
        View c = getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = getFirstVisiblePosition();// listView中 当前可视的第一个item的序号
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {

        if (parentScrollView != null) {
            if (ev.getAction() == MotionEvent.ACTION_MOVE) {

                int itemCount = getAdapter().getCount();
                View child = getChildAt(0);

                int height = child.getMeasuredHeight() * itemCount;
                height = height - getMeasuredHeight();

                int scrollY = getTrueScrollY();
                int y = (int) ev.getY();

                if (currentY < y) { // 手指向下滑动
                    if (scrollY <= 0) { // 如果向下滑动到头，就把滚动交给父Scrollview
                        setParentScrollAble(true);
                        return false;
                    } else {
                        setParentScrollAble(false);
                    }
                } else if (currentY > y) {
                    if (scrollY >= height) {   // 如果向上滑动到头，就把滚动交给父Scrollview
                        setParentScrollAble(true);
                        return false;
                    } else {
                        setParentScrollAble(false);
                    }
                }
                currentY = y;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * @param flag 滚动事件标记
     */
    private void setParentScrollAble(boolean flag) {
        parentScrollView.requestDisallowInterceptTouchEvent(!flag);
    }

}
