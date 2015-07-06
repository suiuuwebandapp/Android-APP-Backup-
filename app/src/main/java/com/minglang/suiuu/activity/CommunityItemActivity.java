package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshListView;

/**
 * 问题详情页
 */
public class CommunityItemActivity extends AppCompatActivity {

    private PullToRefreshListView pullToRefreshListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_item);

        initView();
        ViewAction();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.community_item_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("问题");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.community_item_list_view);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void ViewAction(){

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

}