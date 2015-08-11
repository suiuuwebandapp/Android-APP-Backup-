package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.AttentionPagerAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.fragment.attention.AttentionGalleryFragment;
import com.minglang.suiuu.fragment.attention.AttentionProblemFragment;
import com.minglang.suiuu.fragment.attention.AttentionSuiuuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 关注页面
 */
public class AttentionActivity extends BaseAppCompatActivity {

    @BindColor(R.color.tr_black)
    int normalColor;

    @BindColor(R.color.mainColor)
    int selectedColor;

    @BindString(R.string.mainTitle1)
    String str1;

    @BindString(R.string.mainTitle2)
    String str2;

    @BindString(R.string.QuestionsAndAnswers)
    String str3;

    @Bind(R.id.attentionBack)
    ImageView attentionBack;

    @Bind(R.id.attentionPager)
    ViewPager attentionPager;

    @Bind(R.id.attentionTabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);

        ButterKnife.bind(this);
        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        List<String> titleList = new ArrayList<>();
        titleList.add(str1);
        titleList.add(str2);
        titleList.add(str3);

        tabLayout.addTab(tabLayout.newTab().setText(str1), true);
        tabLayout.addTab(tabLayout.newTab().setText(str2), false);
        tabLayout.addTab(tabLayout.newTab().setText(str3), false);
        tabLayout.setTabTextColors(normalColor, selectedColor);

        //关注的旅图
        AttentionGalleryFragment attentionGalleryFragment = AttentionGalleryFragment.newInstance(userSign, verification);
        //关注的随游
        AttentionSuiuuFragment attentionSuiuuFragment = AttentionSuiuuFragment.newInstance(userSign, verification);
        //关注的问答
        AttentionProblemFragment attentionProblemFragment = AttentionProblemFragment.newInstance(userSign, verification);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(attentionGalleryFragment);
        fragmentList.add(attentionSuiuuFragment);
        fragmentList.add(attentionProblemFragment);

        AttentionPagerAdapter attentionPagerAdapter
                = new AttentionPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
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