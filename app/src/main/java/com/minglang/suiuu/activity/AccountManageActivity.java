package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.widget.ListView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.JoinAdapter;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshListView;
import com.minglang.suiuu.entity.JoinEntity;

import java.util.ArrayList;
import java.util.List;

public class AccountManageActivity extends AppCompatActivity {

    private PullToRefreshListView pullToRefreshListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);

        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.account_balance_toolbar);
        toolbar.setTitle(getResources().getString(R.string.AccountManage));
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.account_balance_list_view);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        ListView listView = pullToRefreshListView.getRefreshableView();

        List<JoinEntity> joinEntityList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            JoinEntity entity = new JoinEntity();
            entity.setImagePath("http://q.qlogo.cn/qqapp/1104557000/C7A47D7E23F617EA5E0CF13B14A036FA/100");
            entity.setUserName("佚名" + i);
            entity.setUserLocation("北京");
            entity.setRemoveFlag(true);
            joinEntityList.add(entity);
        }
        JoinAdapter adapter = new JoinAdapter(this);
        adapter.setList(joinEntityList);

        listView.setAdapter(adapter);

    }

    private void ViewAction() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(AccountManageActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                pullToRefreshListView.onRefreshComplete();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(AccountManageActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                pullToRefreshListView.onRefreshComplete();

            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}