package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.minglang.suiuu.R;

public class MySuiuuReleaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private CollapsingToolbarLayout collapsingToolbar;

//    private ViewPager releaseViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_suiuu_release);

        initView();

        setSupportActionBar(toolbar);
        collapsingToolbar.setTitle(getResources().getString(R.string.MyReleaseSuiuu));

        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.my_suiuu_release_Toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.my_suiuu_release_collapsingToolbarLayout);
//        releaseViewPager = (ViewPager) findViewById(R.id.my_suiuu_release_viewPager);
    }

    private void ViewAction() {
//        releaseViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

}
