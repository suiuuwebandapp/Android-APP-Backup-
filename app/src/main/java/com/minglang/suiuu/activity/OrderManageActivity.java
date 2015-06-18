package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.OrderManageAdapter;
import com.minglang.suiuu.fragment.order.ConfirmOrderFragment;
import com.minglang.suiuu.fragment.order.NewOrderFragment;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单管理页面
 * <p/>
 * 查看我的订单
 */
public class OrderManageActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manage);

        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.orderManageToolbar);
        toolbar.setTitle(getResources().getString(R.string.OrderManage));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        String newOrder = "新订单";
        String confirmOrder = "已接单";

        TabLayout tabLayout = (TabLayout) findViewById(R.id.orderManageTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(newOrder), true);
        tabLayout.addTab(tabLayout.newTab().setText(confirmOrder), false);
        tabLayout.setTabTextColors(getResources().getColor(R.color.tr_black), getResources().getColor(R.color.text_select_true));

        viewPager = (ViewPager) findViewById(R.id.orderManageViewPager);

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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

}
