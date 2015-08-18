package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.OrderManageAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.fragment.order.ConfirmOrderFragment;
import com.minglang.suiuu.fragment.order.NewOrderFragment;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 订单管理页面-仅限随友使用
 * <p/>
 * 查看我的订单
 */
public class OrderManageActivity extends BaseAppCompatActivity {

    @BindColor(R.color.white)
    int titleTextColor;

    @BindString(R.string.NewOrder)
    String newOrder;

    @BindString(R.string.ConfirmOrder)
    String confirmOrder;

    @BindColor(R.color.tr_black)
    int normalColor;

    @BindColor(R.color.mainColor)
    int selectedColor;

    @Bind(R.id.orderManageToolbar)
    Toolbar toolbar;

    @Bind(R.id.orderManageTabLayout)
    TabLayout tabLayout;

    @Bind(R.id.orderManageViewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manage);
        ButterKnife.bind(this);
        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        toolbar.setTitleTextColor(titleTextColor);
        setSupportActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText(newOrder), true);
        tabLayout.addTab(tabLayout.newTab().setText(confirmOrder), false);
        tabLayout.setTabTextColors(normalColor, selectedColor);

        String userSign = SuiuuInfo.ReadUserSign(this);
        String verification = SuiuuInfo.ReadVerification(this);

        NewOrderFragment newOrderFragment = NewOrderFragment.newInstance(userSign, verification);
        ConfirmOrderFragment confirmOrderFragment = ConfirmOrderFragment.newInstance(userSign, verification);

        List<String> titleList = new ArrayList<>();
        titleList.add(newOrder);
        titleList.add(confirmOrder);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(newOrderFragment);
        fragmentList.add(confirmOrderFragment);

        OrderManageAdapter adapter = new OrderManageAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}