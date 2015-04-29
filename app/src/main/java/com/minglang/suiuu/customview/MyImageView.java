package com.minglang.suiuu.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by LZY on 2015/3/25 0025.
 */
public class MyImageView extends ImageView {
    private OnMeasureListener onMeasureListener;

    public void setOnMeasureListener(OnMeasureListener onMeasureListener) {
        this.onMeasureListener = onMeasureListener;
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (onMeasureListener != null) {
            onMeasureListener.onMeasureSize(getMeasuredWidth(),
                    getMeasuredHeight());
        }
    }

    public interface OnMeasureListener {
        public void onMeasureSize(int width, int height);
    }

}
