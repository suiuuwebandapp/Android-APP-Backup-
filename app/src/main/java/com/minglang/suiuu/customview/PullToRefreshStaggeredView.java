package com.minglang.suiuu.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;

import com.etsy.android.grid.StaggeredGridView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.pulltorefresh.OverscrollHelper;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshAdapterViewBase;
import com.minglang.suiuu.customview.pulltorefresh.internal.EmptyViewMethodAccessor;


public class PullToRefreshStaggeredView extends PullToRefreshAdapterViewBase<StaggeredGridView> {

    public PullToRefreshStaggeredView(Context context) {
        super(context);
    }

    public PullToRefreshStaggeredView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshStaggeredView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshStaggeredView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected StaggeredGridView createRefreshableView(Context context, AttributeSet attrs) {
        final StaggeredGridView gv;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            gv = new InternalStaggeredGridViewSDK9(context, attrs);
        } else {
            gv = new InternalStaggeredGridView(context, attrs);
        }
        gv.setId(R.id.gridView);
        return gv;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        ((StaggeredGridView) mRefreshableView).setAdapter(adapter);
    }

    class InternalStaggeredGridView extends StaggeredGridView implements EmptyViewMethodAccessor {

        public InternalStaggeredGridView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public void setEmptyView(View emptyView) {
            PullToRefreshStaggeredView.this.setEmptyView(emptyView);
        }

        @Override
        public void setEmptyViewInternal(View emptyView) {
            super.setEmptyView(emptyView);
        }
    }

    @TargetApi(9)
    final class InternalStaggeredGridViewSDK9 extends InternalStaggeredGridView {

        public InternalStaggeredGridViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                       int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

            final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                    scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

            // Does all of the hard work...
            OverscrollHelper.overScrollBy(PullToRefreshStaggeredView.this, deltaX, scrollX, deltaY, scrollY, isTouchEvent);

            return returnValue;
        }
    }

}
