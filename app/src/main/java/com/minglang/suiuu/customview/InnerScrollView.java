package com.minglang.suiuu.customview;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class InnerScrollView extends ScrollView {

    Handler handler;

    /**
     */
    public ScrollView parentScrollView;

    public InnerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        handler = new Handler();
    }

    private int lastScrollDelta = 0;

    public void resume() {
        overScrollBy(0, -lastScrollDelta, 0, getScrollY(), 0, getScrollRange(), 0, 0, true);
        lastScrollDelta = 0;
    }

    int mTop = 10;

    /**
     * 将targetView滚到最顶端
     */
    public void scrollTo(final View targetView) {

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                int oldScrollY = getScrollY();
                int top = targetView.getTop() - mTop;
                final int delatY = top - oldScrollY;
                lastScrollDelta = delatY;
                smoothScrollTo(0, top);
            }
        }, 50);

    }

    private int getScrollRange() {
        int scrollRange = 0;
        if (getChildCount() > 0) {
            View child = getChildAt(0);
            scrollRange = Math.max(0, child.getHeight() - (getHeight()));
        }
        return scrollRange;
    }

    int currentY;

    @Override
    public boolean onInterceptTouchEvent(@NonNull MotionEvent ev) {
        if (parentScrollView == null) {
            return super.onInterceptTouchEvent(ev);
        } else {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                // 将父scrollview的滚动事件拦截
                currentY = (int)ev.getY();
                setParentScrollAble(false);
                return super.onInterceptTouchEvent(ev);
            } else if (ev.getAction() == MotionEvent.ACTION_UP) {
                // 把滚动事件恢复给父Scrollview
                setParentScrollAble(true);
            }
        }
        return super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        View child = getChildAt(0);
        if (parentScrollView != null) {
            if (ev.getAction() == MotionEvent.ACTION_MOVE) {

                int height = child.getMeasuredHeight();
                height = height - getMeasuredHeight();

                int scrollY = getScrollY();
                int y = (int)ev.getY();

                if (currentY < y) { // 手指向下滑动
                    if (scrollY <= 0) {  // 如果向下滑动到头，就把滚动交给父Scrollview
                        setParentScrollAble(true);
                        return false;
                    } else {
                        setParentScrollAble(false);
                    }
                } else if (currentY > y) {
                    if (scrollY >= height) { // 如果向上滑动到头，就把滚动交给父Scrollview
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
     * 是否把滚动事件交给父scrollview
     *
     * @param flag 滚动事件标记
     */
    private void setParentScrollAble(boolean flag) {
        parentScrollView.requestDisallowInterceptTouchEvent(!flag);
    }

}
