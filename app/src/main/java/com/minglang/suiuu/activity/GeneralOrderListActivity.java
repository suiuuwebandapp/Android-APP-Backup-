package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.GeneralOrderPagerAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.fragment.myorder.CompletedFragment;
import com.minglang.suiuu.fragment.myorder.NotFinishedFragment;
import com.minglang.suiuu.utils.StatusBarCompat;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 普通用户查看订单的页面
 */
public class GeneralOrderListActivity extends BaseAppCompatActivity {

    @BindColor(R.color.tr_black)
    int normalColor;

    @BindColor(R.color.mainColor)
    int selectedColor;

    @BindString(R.string.Completed)
    String str1;

    @BindString(R.string.NotFinished)
    String str2;

    @Bind(R.id.my_order_list_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.my_order_list_tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.my_order_list_view_page)
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_order_list);

        StatusBarCompat.compat(this);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);

        tabLayout.setTabTextColors(normalColor, selectedColor);

        List<String> titleList = new ArrayList<>();
        titleList.add(str1);
        titleList.add(str2);

        tabLayout.addTab(tabLayout.newTab().setText(str1), true);
        tabLayout.addTab(tabLayout.newTab().setText(str2), false);
        tabLayout.setTabTextColors(normalColor, selectedColor);

        userSign = SuiuuInfo.ReadUserSign(this);
        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        CompletedFragment completedFragment = CompletedFragment.newInstance(userSign, verification, token);
        NotFinishedFragment notFinishedFragment = NotFinishedFragment.newInstance(userSign, verification, token);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(completedFragment);
        fragmentList.add(notFinishedFragment);

        FragmentManager fm = getSupportFragmentManager();
        GeneralOrderPagerAdapter generalOrderPagerAdapter
                = new GeneralOrderPagerAdapter(fm, fragmentList, titleList);
        viewPager.setAdapter(generalOrderPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(generalOrderPagerAdapter);
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