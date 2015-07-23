package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.NewRemindAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.fragment.remind.NewAtFragment;
import com.minglang.suiuu.fragment.remind.NewAttentionFragment;
import com.minglang.suiuu.fragment.remind.NewCommentFragment;
import com.minglang.suiuu.fragment.remind.NewReplyFragment;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 新提醒页面
 */

public class NewRemindActivity extends BaseAppCompatActivity {

    @BindString(R.string.newAt)
    String str1;

    @BindString(R.string.newComment)
    String str2;

    @BindString(R.string.newReply)
    String str3;

    @BindString(R.string.newAttention)
    String str4;

    @BindColor(R.color.tr_black)
    int normalColor;

    @BindColor(R.color.mainColor)
    int selectedColor;

    @Bind(R.id.new_remind_toolbar)
    Toolbar toolbar;

    @Bind(R.id.newRemindPager)
    ViewPager newRemindPager;

    @Bind(R.id.new_remind_tab_layout)
    TabLayout tabLayout;

    /**
     * 新@页面
     */
    private NewAtFragment newAtFragment;

    /**
     * 新评论页面
     */
    private NewCommentFragment newCommentFragment;

    /**
     * 新回复页面
     */
    private NewReplyFragment newReplyFragment;

    /**
     * 新关注页面
     */
    private NewAttentionFragment newAttentionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_remind);

        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化方法
     */
    private void initView() {

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        newRemindPager.setOffscreenPageLimit(4);

        List<String> titleList = new ArrayList<>();
        titleList.add(str1);
        titleList.add(str2);
        titleList.add(str3);
        titleList.add(str4);

        tabLayout.addTab(tabLayout.newTab().setText(str1), true);
        tabLayout.addTab(tabLayout.newTab().setText(str2), false);
        tabLayout.addTab(tabLayout.newTab().setText(str3), false);
        tabLayout.addTab(tabLayout.newTab().setText(str4), false);
        tabLayout.setTabTextColors(normalColor, selectedColor);

        CreateFragment();

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(newAtFragment);
        fragmentList.add(newCommentFragment);
        fragmentList.add(newReplyFragment);
        fragmentList.add(newAttentionFragment);

        NewRemindAdapter newRemindAdapter = new NewRemindAdapter(getSupportFragmentManager(), fragmentList, titleList);
        newRemindPager.setAdapter(newRemindAdapter);

        tabLayout.setupWithViewPager(newRemindPager);
        tabLayout.setTabsFromPagerAdapter(newRemindAdapter);
    }

    private void CreateFragment() {
        userSign = SuiuuInfo.ReadUserSign(this);
        verification = SuiuuInfo.ReadVerification(this);

        newAtFragment = NewAtFragment.newInstance(userSign, verification);
        newCommentFragment = NewCommentFragment.newInstance(userSign, verification);
        newReplyFragment = NewReplyFragment.newInstance(userSign, verification);
        newAttentionFragment = NewAttentionFragment.newInstance(userSign, verification);
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

}