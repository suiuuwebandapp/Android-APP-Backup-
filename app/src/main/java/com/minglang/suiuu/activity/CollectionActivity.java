package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.CollectionAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.fragment.collection.CollectionLoopFragment;
import com.minglang.suiuu.fragment.collection.CollectionSuiuuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 收藏页面
 */
public class CollectionActivity extends BaseActivity {

    /**
     * 返回
     */
    @Bind(R.id.collectionBack)
    ImageView collectionBack;

    @Bind(R.id.collectionPager)
    ViewPager collectionPager;

    @Bind(R.id.collectionTabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        ButterKnife.bind(this);

        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        tabLayout.setTabTextColors(getResources().getColor(R.color.tr_black),
                getResources().getColor(R.color.text_select_true));

        List<String> titleList = new ArrayList<>();

        String str1 = getResources().getString(R.string.title2);
        String str2 = getResources().getString(R.string.title3);

        titleList.add(str1);
        titleList.add(str2);

        tabLayout.addTab(tabLayout.newTab().setText(str1), true);
        tabLayout.addTab(tabLayout.newTab().setText(str2), false);

        CollectionLoopFragment collectionLoopFragment = CollectionLoopFragment.newInstance(userSign, verification);
        CollectionSuiuuFragment collectionSuiuuFragment = CollectionSuiuuFragment.newInstance(userSign, verification);

        List<Fragment> collectionList = new ArrayList<>();
        collectionList.add(collectionLoopFragment);
        collectionList.add(collectionSuiuuFragment);

        CollectionAdapter collectionAdapter = new CollectionAdapter(fm, collectionList, titleList);
        collectionPager.setAdapter(collectionAdapter);

        tabLayout.setupWithViewPager(collectionPager);
        tabLayout.setTabsFromPagerAdapter(collectionAdapter);
    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        collectionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        collectionPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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