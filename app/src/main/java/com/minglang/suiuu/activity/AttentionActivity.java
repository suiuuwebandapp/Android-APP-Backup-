package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.AttentionPagerAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.fragment.attention.AttentionGalleryFragment;
import com.minglang.suiuu.fragment.attention.AttentionProblemFragment;
import com.minglang.suiuu.fragment.attention.AttentionSuiuuFragment;
import com.minglang.suiuu.utils.SuiuuInfo;

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

    @BindColor(R.color.white)
    int titleColor;

    @BindColor(R.color.tr_black)
    int normalColor;

    @BindColor(R.color.mainColor)
    int selectedColor;

    @BindString(R.string.MainTitle1)
    String str1;

    @BindString(R.string.MainTitle2)
    String str2;

    @BindString(R.string.QuestionsAndAnswers)
    String str3;

    @Bind(R.id.attention_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.attention_view_pager)
    ViewPager viewPager;

    @Bind(R.id.attention_tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
        ButterKnife.bind(this);
        initView();
        viewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        List<String> titleList = new ArrayList<>();
        titleList.add(str1);
        titleList.add(str2);
        titleList.add(str3);

        tabLayout.addTab(tabLayout.newTab().setText(str1), true);
        tabLayout.addTab(tabLayout.newTab().setText(str2), false);
        tabLayout.addTab(tabLayout.newTab().setText(str3), false);
        tabLayout.setTabTextColors(normalColor, selectedColor);

        userSign = SuiuuInfo.ReadUserSign(this);
        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        viewPager.setOffscreenPageLimit(3);

        //关注的旅图
        AttentionGalleryFragment attentionGalleryFragment = AttentionGalleryFragment.newInstance(userSign, verification, token);
        //关注的随游
        AttentionSuiuuFragment attentionSuiuuFragment = AttentionSuiuuFragment.newInstance(userSign, verification, token);
        //关注的问答
        AttentionProblemFragment attentionProblemFragment = AttentionProblemFragment.newInstance(userSign, verification, token);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(attentionGalleryFragment);
        fragmentList.add(attentionSuiuuFragment);
        fragmentList.add(attentionProblemFragment);

        AttentionPagerAdapter attentionPagerAdapter
                = new AttentionPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(attentionPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(attentionPagerAdapter);
    }

    /**
     * 控件动作
     */
    private void viewAction() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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