package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.minglang.suiuu.R;

/**
 * 问题详情页
 */
public class CommunityItemActivity extends AppCompatActivity {

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
    }

    private void ViewAction(){


    }

}