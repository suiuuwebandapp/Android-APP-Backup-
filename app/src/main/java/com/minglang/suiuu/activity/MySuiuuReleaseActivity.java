package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.MySuiuuReleaseAdapter;
import com.minglang.suiuu.fragment.suiuu.JoinFragment;
import com.minglang.suiuu.fragment.suiuu.NewApplyForFragment;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.List;

public class MySuiuuReleaseActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    private ViewPager releaseViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_suiuu_release);

        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        releaseViewPager = (ViewPager) findViewById(R.id.my_suiuu_release_viewPager);

        String join = "已参加";
        String applyFor = "新申请";

        tabLayout = (TabLayout) findViewById(R.id.my_suiuu_release_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(join), true);
        tabLayout.addTab(tabLayout.newTab().setText(applyFor), false);
        tabLayout.setTabTextColors(getResources().getColor(R.color.tr_black), getResources().getColor(R.color.text_select_true));

        String userSign = SuiuuInfo.ReadUserSign(this);
        String verification = SuiuuInfo.ReadVerification(this);

        JoinFragment joinFragment = JoinFragment.newInstance(userSign, verification);
        NewApplyForFragment newApplyForFragment = NewApplyForFragment.newInstance(userSign, verification);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(joinFragment);
        fragments.add(newApplyForFragment);
        List<String> titleList = new ArrayList<>();
        titleList.add(join);
        titleList.add(applyFor);

        MySuiuuReleaseAdapter releaseAdapter = new MySuiuuReleaseAdapter(getSupportFragmentManager(), fragments, titleList);
        releaseViewPager.setAdapter(releaseAdapter);
        tabLayout.setupWithViewPager(releaseViewPager);
        tabLayout.setTabsFromPagerAdapter(releaseAdapter);
    }

    private void ViewAction() {

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        releaseViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
