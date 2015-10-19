package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.MySuiuuInfoAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.fragment.mysuiuu.ParticipateFragment;
import com.minglang.suiuu.fragment.mysuiuu.PublishedFragment;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 我的随游页面
 * <p/>
 * 查看我已参加和发布的随游
 */
public class MySuiuuInfoActivity extends BaseAppCompatActivity {

    private static final String TAG = MySuiuuInfoActivity.class.getSimpleName();

    @BindString(R.string.Published)
    String Published;

    @BindString(R.string.Participate)
    String Participate;

    @Bind(R.id.my_suiuu_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.my_suiuu_tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.my_suiuu_view_pager)
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_suiuu_info);
        ButterKnife.bind(this);
        initView();
        L.i(TAG, "screenWidth:" + screenWidth + ",screenHeight:" + screenHeight);
    }

    /**
     * 初始化方法
     */
    private void initView() {
        setSupportActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText(Published), true);
        tabLayout.addTab(tabLayout.newTab().setText(Participate), false);

        userSign = SuiuuInfo.ReadUserSign(this);
        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        PublishedFragment publishedFragment = PublishedFragment.newInstance(userSign, verification, token);
        ParticipateFragment participateFragment = ParticipateFragment.newInstance(userSign, verification, token);

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