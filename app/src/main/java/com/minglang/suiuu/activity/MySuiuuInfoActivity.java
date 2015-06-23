package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.MySuiuuInfoAdapter;
import com.minglang.suiuu.fragment.mysuiuu.ParticipateFragment;
import com.minglang.suiuu.fragment.mysuiuu.PublishedFragment;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的随游页面
 * <p/>
 * 查看我已参加和发布的随游
 */
public class MySuiuuInfoActivity extends AppCompatActivity {

    private ImageView back;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_suiuu_info);

        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {

        back = (ImageView) findViewById(R.id.mySuiuuBack);

        String Published = "已发布";
        String Participate = "已参加";

        TabLayout tabLayout = (TabLayout) findViewById(R.id.mySuiuuTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(Published), true);
        tabLayout.addTab(tabLayout.newTab().setText(Participate), false);
        tabLayout.setTabTextColors(getResources().getColor(R.color.tr_black), getResources().getColor(R.color.text_select_true));

        viewPager = (ViewPager) findViewById(R.id.mySuiuuViewPager);

        String userSign = SuiuuInfo.ReadUserSign(this);
        String verification = SuiuuInfo.ReadVerification(this);

        PublishedFragment publishedFragment = PublishedFragment.newInstance(userSign, verification);
        ParticipateFragment participateFragment = ParticipateFragment.newInstance(userSign, verification);

        List<String> titleList = new ArrayList<>();
        titleList.add(Published);
        titleList.add(Participate);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(publishedFragment);
        fragmentList.add(participateFragment);

        MySuiuuInfoAdapter adapter = new MySuiuuInfoAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

}
