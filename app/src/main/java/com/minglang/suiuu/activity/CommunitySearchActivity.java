package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshListView;
import com.minglang.suiuu.utils.DeBugLog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 问答社区-搜索
 */
public class CommunitySearchActivity extends BaseAppCompatActivity {

    private static final String TAG = CommunitySearchActivity.class.getSimpleName();

    @Bind(R.id.community_search_layout_back)
    ImageView back;

    @Bind(R.id.community_search_view)
    EditText searchView;

    @Bind(R.id.community_search_btn)
    ImageView searchBtn;

    @Bind(R.id.community_search_list_view)
    PullToRefreshListView pullToRefreshListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_search);

        ButterKnife.bind(this);
        ViewAction();
    }

    private void ViewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = searchView.getText().toString().trim();
                DeBugLog.i(TAG, "str:" + str);
                if (TextUtils.isEmpty(str)) {
                    setResult(RESULT_CANCELED);
                } else {
                    setResult(RESULT_OK);
                }
                finish();
            }
        });

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(CommunitySearchActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(CommunitySearchActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                pullToRefreshListView.onRefreshComplete();
            }
        });

    }

}