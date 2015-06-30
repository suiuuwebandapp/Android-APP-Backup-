package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.AttentionPagerAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.fragment.attention.AttentionLoopFragment;
import com.minglang.suiuu.fragment.attention.AttentionUserFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注页面
 */
public class AttentionActivity extends BaseActivity {

    private static final String TAG = AttentionActivity.class.getSimpleName();

    /**
     * 返回键
     */
    private ImageView attentionBack;

    private ViewPager attentionPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);

        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        attentionBack = (ImageView) findViewById(R.id.attentionBack);
        attentionPager = (ViewPager) findViewById(R.id.attentionPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.attentionTabLayout);
        tabLayout.setTabTextColors(getResources().getColor(R.color.tr_black),
                getResources().getColor(R.color.text_select_true));

        List<String> titleList = new ArrayList<>();

        String str1 = getResources().getString(R.string.title2);
        String str2 = getResources().getString(R.string.user);

        titleList.add(str1);
        titleList.add(str2);

        tabLayout.addTab(tabLayout.newTab().setText(str1), true);
        tabLayout.addTab(tabLayout.newTab().setText(str2), false);

        //关注圈子页面
        AttentionLoopFragment attentionLoopFragment = AttentionLoopFragment.newInstance(userSign, verification);
        //关注用户页面
        AttentionUserFragment attentionUserFragment = AttentionUserFragment.newInstance(userSign, verification);
        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(attentionLoopFragment);
        fragmentList.add(attentionUserFragment);

        AttentionPagerAdapter attentionPagerAdapter = new AttentionPagerAdapter(fm, fragmentList, titleList);
        attentionPager.setAdapter(attentionPagerAdapter);

        tabLayout.setupWithViewPager(attentionPager);
        tabLayout.setTabsFromPagerAdapter(attentionPagerAdapter);

    }


    /**
     * 控件动作
     */
    private void ViewAction() {

        attentionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        attentionPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });


    }


}
