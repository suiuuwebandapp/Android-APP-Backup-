package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.NewRemindPageAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.fragment.remind.MsgOrderFragment;
import com.minglang.suiuu.fragment.remind.MsgQuestionFragment;
import com.minglang.suiuu.fragment.remind.MsgSystemFragment;
import com.minglang.suiuu.fragment.remind.MsgTripImageFragment;
import com.minglang.suiuu.utils.StatusBarCompat;
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

    @BindString(R.string.NewAt)
    String str1;

    @BindString(R.string.NewComment)
    String str2;

    @BindString(R.string.NewReply)
    String str3;

    @BindString(R.string.NewAttention)
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
    private MsgTripImageFragment msgTripImageFragment;

    /**
     * 新评论页面
     */
    private MsgQuestionFragment msgQuestionFragment;

    /**
     * 新回复页面
     */
    private MsgOrderFragment msgOrderFragment;

    /**
     * 新关注页面
     */
    private MsgSystemFragment msgSystemFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_remind);

        StatusBarCompat.compat(this);
        ButterKnife.bind(this);

        initView();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        setSupportActionBar(toolbar);

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
        fragmentList.add(msgTripImageFragment);
        fragmentList.add(msgQuestionFragment);
        fragmentList.add(msgOrderFragment);
        fragmentList.add(msgSystemFragment);

        FragmentManager fm = getSupportFragmentManager();

        NewRemindPageAdapter newRemindPageAdapter = new NewRemindPageAdapter(fm, fragmentList, titleList);
        newRemindPager.setAdapter(newRemindPageAdapter);

        tabLayout.setupWithViewPager(newRemindPager);
        tabLayout.setTabsFromPagerAdapter(newRemindPageAdapter);
    }

    private void CreateFragment() {
        userSign = SuiuuInfo.ReadUserSign(this);
        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        msgTripImageFragment = MsgTripImageFragment.newInstance(userSign, verification, token);
        msgQuestionFragment = MsgQuestionFragment.newInstance(userSign, verification, token);
        msgOrderFragment = MsgOrderFragment.newInstance(userSign, verification, token);
        msgSystemFragment = MsgSystemFragment.newInstance(userSign, verification, token);
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