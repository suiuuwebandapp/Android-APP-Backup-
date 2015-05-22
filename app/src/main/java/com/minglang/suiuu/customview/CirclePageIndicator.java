package com.minglang.suiuu.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.DeBugLog;

/**
 * 简易ViewPager指示器
 * <p/>
 * Created by Administrator on 2015/5/22.
 */
public class CirclePageIndicator extends LinearLayout {

    private static final String TAG = "CirclePageIndicator";

    public static final int DEFAULT_INDICATOR_SPACING = 5;

    private int activePosition;
    private int indicatorSpacing;

    public CirclePageIndicator(Context context) {
        this(context, null);
    }

    public CirclePageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CirclePageIndicator, 0, 0);
        try {
            indicatorSpacing = a.getDimensionPixelSize(R.styleable.CirclePageIndicator_indicator_spacing,
                    DEFAULT_INDICATOR_SPACING);
        } catch (Exception e) {
            DeBugLog.e(TAG, "getDimensionPixelSize is fail!  " + e.getMessage());
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        if (!(getLayoutParams() instanceof FrameLayout.LayoutParams)) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.BOTTOM | Gravity.START;
            setLayoutParams(params);
        }
    }

    public void addIndicator(int count) {
        for (int i = 0; i < count; i++) {
            ImageView img = new ImageView(getContext());
            LayoutParams params = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = indicatorSpacing;
            params.rightMargin = indicatorSpacing;
            img.setImageResource(R.drawable.circle_indicator_stroke);
            addView(img, params);
        }

        if (count > 0) {
            ((ImageView) getChildAt(0)).setImageResource(R.drawable.circle_indicator_solid);
        }
    }

    public void updateIndicator(int position) {
        if (activePosition != position) {
            ((ImageView) getChildAt(activePosition)).setImageResource(R.drawable.circle_indicator_stroke);
            ((ImageView) getChildAt(position)).setImageResource(R.drawable.circle_indicator_solid);
            activePosition = position;
        }
    }


}
