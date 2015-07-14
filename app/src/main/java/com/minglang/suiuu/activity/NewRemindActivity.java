package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.NewRemindAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.fragment.remind.NewAtFragment;
import com.minglang.suiuu.fragment.remind.NewAttentionFragment;
import com.minglang.suiuu.fragment.remind.NewCommentFragment;
import com.minglang.suiuu.fragment.remind.NewReplyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 新提醒页面
 */

public class NewRemindActivity extends BaseActivity {

    /**
     * 返回键
     */
    @Bind(R.id.newRemindBack)
    ImageView newRemindBack;

    @Bind(R.id.newRemindPager)
    ViewPager newRemindPager;

    @Bind(R.id.newRemindTabLayout)
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
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        newRemindPager.setOffscreenPageLimit(4);

        String str1 = getResources().getString(R.string.newAt);
        String str2 = getResources().getString(R.string.newComment);
        String str3 = getResources().getString(R.string.newReply);
        String str4 = getResources().getString(R.string.newAttention);

        List<String> titleList = new ArrayList<>();
        titleList.add(str1);
        titleList.add(str2);
        titleList.add(str3);
        titleList.add(str4);

        tabLayout.addTab(tabLayout.newTab().setText(str1), true);
        tabLayout.addTab(tabLayout.newTab().setText(str2), false);
        tabLayout.addTab(tabLayout.newTab().setText(str3), false);
        tabLayout.addTab(tabLayout.newTab().setText(str4), false);
        tabLayout.setTabTextColors(getResources().getColor(R.color.tr_black),
                getResources().getColor(R.color.text_select_true));

        CreateFragment();

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(newAtFragment);
        fragmentList.add(newCommentFragment);
        fragmentList.add(newReplyFragment);
        fragmentList.add(newAttentionFragment);

        NewRemindAdapter newRemindAdapter = new NewRemindAdapter(fm, fragmentList, titleList);
        newRemindPager.setAdapter(newRemindAdapter);

        tabLayout.setupWithViewPager(newRemindPager);
        tabLayout.setTabsFromPagerAdapter(newRemindAdapter);
    }

    /**
     * 控件动作
     */
    private void ViewAction() {
        newRemindBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void CreateFragment() {
        newAtFragment = NewAtFragment.newInstance(userSign, verification);
        newCommentFragment = NewCommentFragment.newInstance(userSign, verification);
        newReplyFragment = NewReplyFragment.newInstance(userSign, verification);
        newAttentionFragment = NewAttentionFragment.newInstance(userSign, verification);
    }

}