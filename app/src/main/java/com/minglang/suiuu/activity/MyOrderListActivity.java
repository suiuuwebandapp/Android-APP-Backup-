package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.MyOrderListPageAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.fragment.myorder.CompletedFragment;
import com.minglang.suiuu.fragment.myorder.NotFinishedFragment;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

public class MyOrderListActivity extends BaseAppCompatActivity {

    @BindColor(R.color.tr_black)
    int normalColor;

    @BindColor(R.color.mainColor)
    int selectedColor;

    @BindString(R.string.Completed)
    String str1;

    @BindString(R.string.NotFinished)
    String str2;

    @Bind(R.id.my_order_list_page_back)
    ImageView myOrderListPageBack;

    @Bind(R.id.my_order_list_tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.my_order_list_view_page)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);
        ButterKnife.bind(this);
        initView();
        ViewAction();
    }

    private void initView() {
        tabLayout.setTabTextColors(normalColor, selectedColor);

        List<String> titleList = new ArrayList<>();
        titleList.add(str1);
        titleList.add(str2);

        tabLayout.addTab(tabLayout.newTab().setText(str1), true);
        tabLayout.addTab(tabLayout.newTab().setText(str2), false);
        tabLayout.setTabTextColors(normalColor, selectedColor);

        userSign = SuiuuInfo.ReadUserSign(this);
        verification = SuiuuInfo.ReadVerification(this);

        CompletedFragment completedFragment = CompletedFragment.newInstance(userSign, verification);
        NotFinishedFragment notFinishedFragment = NotFinishedFragment.newInstance(userSign, verification);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(completedFragment);
        fragmentList.add(notFinishedFragment);

        FragmentManager fm = getSupportFragmentManager();
        MyOrderListPageAdapter myOrderListPageAdapter = new MyOrderListPageAdapter(fm, fragmentList, titleList);
        viewPager.setAdapter(myOrderListPageAdapter);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(myOrderListPageAdapter);
    }

    private void ViewAction() {
        myOrderListPageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}