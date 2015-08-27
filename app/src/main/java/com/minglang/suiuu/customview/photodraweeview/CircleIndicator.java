package com.minglang.suiuu.customview.photodraweeview;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.minglang.suiuu.R;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/8/27 11:08
 * 修改人：Administrator
 * 修改时间：2015/8/27 11:08
 * 修改备注：
 */
public class CircleIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {
    private static final int DEFAULT_INDICATOR_WIDTH = 5;
    private ViewPager mViewpager;
    private ViewPager.OnPageChangeListener mViewPagerOnPageChangeListener;
    private int mIndicatorMargin;
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int mAnimatorResId;
    private int mAnimatorReverseResId;
    private int mIndicatorBackground;
    private int mIndicatorUnselectedBackground;
    private int mCurrentPosition;
    private Animator mAnimationOut;
    private Animator mAnimationIn;

    public CircleIndicator(Context context) {
        super(context);
        this.mAnimatorResId = R.anim.scale_with_alpha;
        this.mAnimatorReverseResId = -1;
        this.mIndicatorBackground = R.drawable.white_radius;
        this.mIndicatorUnselectedBackground = R.drawable.white_radius;
        this.mCurrentPosition = 0;
        this.init(context, (AttributeSet)null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mAnimatorResId = R.anim.scale_with_alpha;
        this.mAnimatorReverseResId = -1;
        this.mIndicatorBackground = R.drawable.white_radius;
        this.mIndicatorUnselectedBackground = R.drawable.white_radius;
        this.mCurrentPosition = 0;
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.setOrientation(HORIZONTAL);
        this.setGravity(17);
        this.handleTypedArray(context, attrs);
        this.mAnimationOut = AnimatorInflater.loadAnimator(context, this.mAnimatorResId);
        if(this.mAnimatorReverseResId == -1) {
            this.mAnimationIn = AnimatorInflater.loadAnimator(context, this.mAnimatorResId);
            this.mAnimationIn.setInterpolator(new CircleIndicator.ReverseInterpolator());
        } else {
            this.mAnimationIn = AnimatorInflater.loadAnimator(context, this.mAnimatorReverseResId);
        }

    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if(attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleIndicator);
            this.mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
            this.mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_height, -1);
            this.mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.CircleIndicator_ci_margin, -1);
            this.mAnimatorResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator, R.anim.scale_with_alpha);
            this.mAnimatorReverseResId = typedArray.getResourceId(R.styleable.CircleIndicator_ci_animator_reverse, -1);
            this.mIndicatorBackground = typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable, R.drawable.white_radius);
            this.mIndicatorUnselectedBackground = typedArray.getResourceId(R.styleable.CircleIndicator_ci_drawable_unselected, this.mIndicatorBackground);
            typedArray.recycle();
        }

        this.mIndicatorWidth = this.mIndicatorWidth == -1?this.dip2px(5.0F):this.mIndicatorWidth;
        this.mIndicatorHeight = this.mIndicatorHeight == -1?this.dip2px(5.0F):this.mIndicatorHeight;
        this.mIndicatorMargin = this.mIndicatorMargin == -1?this.dip2px(5.0F):this.mIndicatorMargin;
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewpager = viewPager;
        this.mCurrentPosition = this.mViewpager.getCurrentItem();
        this.createIndicators(viewPager);
        this.mViewpager.setOnPageChangeListener(this);
        this.onPageSelected(this.mCurrentPosition);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        if(this.mViewpager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        } else {
            this.mViewPagerOnPageChangeListener = onPageChangeListener;
            this.mViewpager.setOnPageChangeListener(this);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(this.mViewPagerOnPageChangeListener != null) {
            this.mViewPagerOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

    }

    public void onPageSelected(int position) {
        if(this.mViewPagerOnPageChangeListener != null) {
            this.mViewPagerOnPageChangeListener.onPageSelected(position);
        }

        if(this.mAnimationIn.isRunning()) {
            this.mAnimationIn.end();
        }

        if(this.mAnimationOut.isRunning()) {
            this.mAnimationOut.end();
        }

        View currentIndicator = this.getChildAt(this.mCurrentPosition);
        currentIndicator.setBackgroundResource(this.mIndicatorUnselectedBackground);
        this.mAnimationIn.setTarget(currentIndicator);
        this.mAnimationIn.start();
        View selectedIndicator = this.getChildAt(position);
        selectedIndicator.setBackgroundResource(this.mIndicatorBackground);
        this.mAnimationOut.setTarget(selectedIndicator);
        this.mAnimationOut.start();
        this.mCurrentPosition = position;
    }

    public void onPageScrollStateChanged(int state) {
        if(this.mViewPagerOnPageChangeListener != null) {
            this.mViewPagerOnPageChangeListener.onPageScrollStateChanged(state);
        }

    }

    private void createIndicators(ViewPager viewPager) {
        this.removeAllViews();
        int count = viewPager.getAdapter().getCount();
        if(count > 0) {
            this.addIndicator(this.mIndicatorBackground, this.mAnimationOut);

            for(int i = 1; i < count; ++i) {
                this.addIndicator(this.mIndicatorUnselectedBackground, this.mAnimationIn);
            }

        }
    }

    private void addIndicator(int backgroundDrawableId, Animator animator) {
        if(animator.isRunning()) {
            animator.end();
        }

        View Indicator = new View(this.getContext());
        Indicator.setBackgroundResource(backgroundDrawableId);
        this.addView(Indicator, this.mIndicatorWidth, this.mIndicatorHeight);
        LinearLayout.LayoutParams lp = (LayoutParams)Indicator.getLayoutParams();
        lp.leftMargin = this.mIndicatorMargin;
        lp.rightMargin = this.mIndicatorMargin;
        Indicator.setLayoutParams(lp);
        animator.setTarget(Indicator);
        animator.start();
    }

    public int dip2px(float dpValue) {
        float scale = this.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    private class ReverseInterpolator implements Interpolator {
        private ReverseInterpolator() {
        }

        public float getInterpolation(float value) {
            return Math.abs(1.0F - value);
        }
    }
}
