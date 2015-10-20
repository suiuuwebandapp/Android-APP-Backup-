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
import com.minglang.suiuu.fragment.attention.AttentionImageFragment;
import com.minglang.suiuu.fragment.attention.AttentionProblemFragment;
import com.minglang.suiuu.fragment.attention.AttentionSuiuuFragment;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.StatusBarCompat;
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

    private static final String TAG = AttentionActivity.class.getSimpleName();

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);

        StatusBarCompat.compat(this);
        ButterKnife.bind(this);

        initView();
    }

    /**
     * 初始化方法
     */
    private void initView() {

        userSign = getIntent().getStringExtra(USER_SIGN);
        L.i(TAG, "传递的UserSign:" + userSign);

        setSupportActionBar(toolbar);

        List<String> titleList = new ArrayList<>();
        titleList.add(str1);
        titleList.add(str2);
        titleList.add(str3);

        tabLayout.addTab(tabLayout.newTab().setText(str1), true);
        tabLayout.addTab(tabLayout.newTab().setText(str2), false);
        tabLayout.addTab(tabLayout.newTab().setText(str3), false);
        tabLayout.setTabTextColors(normalColor, selectedColor);

        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        viewPager.setOffscreenPageLimit(3);

        //关注的旅图
        AttentionImageFragment attentionImageFragment = AttentionImageFragment.newInstance(userSign, verification, token);
        //关注的随游
        AttentionSuiuuFragment attentionSuiuuFragment = AttentionSuiuuFragment.newInstance(userSign, verification, token);
        //关注的问答
        AttentionProblemFragment attentionProblemFragment = AttentionProblemFragment.newInstance(userSign, verification, token);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(attentionImageFragment);
        fragmentList.add(attentionSuiuuFragment);
        fragmentList.add(attentionProblemFragment);

        AttentionPagerAdapter attentionPagerAdapter
                = new AttentionPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(attentionPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(attentionPagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}