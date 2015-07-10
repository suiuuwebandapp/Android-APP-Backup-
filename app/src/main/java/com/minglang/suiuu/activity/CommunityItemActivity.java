package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ScrollView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase.Mode;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshScrollView;
import com.minglang.suiuu.utils.DeBugLog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 问题详情页
 */
public class CommunityItemActivity extends BaseAppCompatActivity {

    private static final String TAG = CommunityItemActivity.class.getSimpleName();

    private static final int COMPLETE = 1;

    @Bind(R.id.community_item_refresh_scroll_view)
    static PullToRefreshScrollView pullToRefreshScrollView;

    @Bind(R.id.noScrollListView)
    NoScrollBarListView noScrollBarListView;

    @Bind(R.id.community_item_toolbar)
    Toolbar toolbar;

    private static final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case COMPLETE:
                    pullToRefreshScrollView.onRefreshComplete();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_item);

        ButterKnife.bind(this);

        initView();
        ViewAction();
    }

    private void initView() {

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("问题");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        pullToRefreshScrollView.setMode(Mode.BOTH);
    }

    private void ViewAction() {

        noScrollBarListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                DeBugLog.i(TAG, "firstVisibleItem:" + firstVisibleItem + ",visibleItemCount:" + visibleItemCount
                        + ",totalItemCount:" + totalItemCount);
            }
        });

        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                String label = DateUtils.formatDateTime(CommunityItemActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                handler.sendEmptyMessageAtTime(COMPLETE, 2500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                String label = DateUtils.formatDateTime(CommunityItemActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                handler.sendEmptyMessageAtTime(COMPLETE, 2500);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}