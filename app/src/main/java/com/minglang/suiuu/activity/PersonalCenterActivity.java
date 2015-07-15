package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.PersonalCenterAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.fragment.center.PersonalProblemFragment;
import com.minglang.suiuu.fragment.center.PersonalSuiuuFragment;
import com.minglang.suiuu.fragment.center.PersonalTravelFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

public class PersonalCenterActivity extends BaseAppCompatActivity {

    @BindString(R.string.mainTitle1)
    String str1;

    @BindString(R.string.mainTitle2)
    String str2;

    @BindString(R.string.Problem)
    String str3;

    @Bind(R.id.personal_center_collapsing_toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    @Bind(R.id.personal_center_toolbar)
    Toolbar toolbar;

//    @Bind(R.id.personal_center_main_image)
//    ImageView mainImageView;

    @Bind(R.id.personal_center_tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.personal_center_view_pager)
    ViewPager viewPager;

    private List<Fragment> fragmentList = new ArrayList<>();

    private List<String> titleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbarLayout.setTitle("个人中心");
        toolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.mainColor));
        toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        toolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.mainColor));

        titleList.add(str1);
        titleList.add(str2);
        titleList.add(str3);

        tabLayout.addTab(tabLayout.newTab().setText(str1), true);
        tabLayout.addTab(tabLayout.newTab().setText(str2), false);
        tabLayout.addTab(tabLayout.newTab().setText(str3), false);

        PersonalTravelFragment travelFragment = PersonalTravelFragment.newInstance(userSign, verification);
        PersonalSuiuuFragment suiuuFragment = PersonalSuiuuFragment.newInstance(userSign, verification);
        PersonalProblemFragment problemFragment = PersonalProblemFragment.newInstance(userSign, verification);

        fragmentList.add(travelFragment);
        fragmentList.add(suiuuFragment);
        fragmentList.add(problemFragment);

        PersonalCenterAdapter personalCenterAdapter
                = new PersonalCenterAdapter(getSupportFragmentManager(), fragmentList, titleList);

        viewPager.setAdapter(personalCenterAdapter);

        tabLayout.setTabsFromPagerAdapter(personalCenterAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_personal_center, menu);
        return true;
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