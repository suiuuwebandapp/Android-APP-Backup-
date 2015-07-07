package com.minglang.suiuu.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.minglang.suiuu.R;

/**
 * 用LinearLayout实现ListView
 * <p/>
 * Created by Administrator on 2015/4/30.
 */
public class LinearLayoutForListView extends LinearLayout {

    private LinearLayoutBaseAdapter adapter;
    private OnItemClickListener onItemClickListener;

    private Context context;

    private int lineViewHeight;

    private int lineViewColor = R.color.transparent;

    public LinearLayoutForListView(Context context) {
        super(context);
        this.context = context;
        //LinearLayoutForListView.this.setOrientation(LinearLayout.VERTICAL);
    }

    public LinearLayoutForListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //LinearLayoutForListView.this.setOrientation(LinearLayout.VERTICAL);
    }

    public void setAdapter(LinearLayoutBaseAdapter adapter) {
        this.adapter = adapter;
        // setAdapter 时添加 view
        bindView();
    }

    public void setLineViewHeight(int lineViewHeight) {
        this.lineViewHeight = lineViewHeight;
    }

    public void setLineViewColor(int lineViewColor) {
        this.lineViewColor = lineViewColor;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

    /**
     * 绑定 adapter 中所有的 view
     */
    private void bindView() {
        if (adapter == null) {
            return;
        }

        for (int i = 0; i < adapter.getCount(); i++) {
            final View v = adapter.getView(i);
            final int tmp = i;
            final Object obj = adapter.getItem(i);

            // view 点击事件触发时回调我们自己的接口
            v.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClicked(v, obj, tmp);
                    }
                }
            });

            addView(v);

            if (lineViewHeight == 0) {
                lineViewHeight = 10;
            }

            View lineView = new View(context);
            lineView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, lineViewHeight));
            lineView.setBackgroundColor(getResources().getColor(lineViewColor));
            addView(lineView);
        }
    }

    /**
     * 回调接口
     */
    public interface OnItemClickListener {
        /**
         * @param v        点击的 view
         * @param obj      点击的 view 所绑定的对象
         * @param position 点击位置的 index
         */
        public void onItemClicked(View v, Object obj, int position);
    }

}
