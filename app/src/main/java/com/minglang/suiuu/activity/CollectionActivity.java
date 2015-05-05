package com.minglang.suiuu.activity;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.CollectionAdapter;
import com.minglang.suiuu.fragment.collection.CollectionLoopFragment;
import com.minglang.suiuu.fragment.collection.CollectionSuiuuFragment;
import com.minglang.suiuu.utils.SuiuuInformation;
import com.minglang.suiuu.utils.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏页面
 */
public class CollectionActivity extends FragmentActivity {

    /**
     * 返回
     */
    private ImageView collectionBack;

    /**
     * 搜索
     */
    private ImageView collectionSearch;

    /**
     * 圈子Tab头
     */
    private TextView collectionLoop;

    /**
     * 路线Tab头
     */
    private TextView collectionSuiuu;

    /**
     * 滑块
     */
    private ImageView collectionSlider;

    private ViewPager collectionPager;

    private int currIndex = 1;// 当前页卡编号

    private int tabWidth;// 每个tab头的宽度

    private int offsetX;//偏移量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        initView();

        ViewAction();

    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        collectionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        collectionSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        collectionLoop.setOnClickListener(new CollectionClick(0));
        collectionSuiuu.setOnClickListener(new CollectionClick(1));

        collectionPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                collectionSlider.setPadding(0, 0, 0, 0);

                switch (i) {
                    case 0:
                        collectionLoop.setTextColor(getResources().getColor(R.color.slider_line_color));
                        collectionSuiuu.setTextColor(getResources().getColor(R.color.textColor));
                        break;
                    case 1:
                        collectionLoop.setTextColor(getResources().getColor(R.color.textColor));
                        collectionSuiuu.setTextColor(getResources().getColor(R.color.slider_line_color));
                        break;

                }

                Animation anim = new TranslateAnimation(tabWidth * currIndex + offsetX, tabWidth * i + offsetX, 0, 0);
                currIndex = i;
                anim.setFillAfter(true);
                anim.setDuration(200);
                collectionSlider.startAnimation(anim);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 初始化方法
     */
    private void initView() {

        /****************设置状态栏颜色*************/
        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        int statusHeight = mTintManager.getConfig().getStatusBarHeight();

        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKITKAT) {
            LinearLayout rootLayout = (LinearLayout) findViewById(R.id.collectionRootLayout);
            rootLayout.setPadding(0, statusHeight, 0, 0);
        }

        collectionBack = (ImageView) findViewById(R.id.collectionBack);
        collectionSearch = (ImageView) findViewById(R.id.collectionSearch);

        collectionLoop = (TextView) findViewById(R.id.collectionLoop);
        collectionSuiuu = (TextView) findViewById(R.id.collectionSuiuu);

        collectionSlider = (ImageView) findViewById(R.id.collectionSlider);

        collectionPager = (ViewPager) findViewById(R.id.collectionPager);

        List<Fragment> collectionList = new ArrayList<>();

        String userSign = SuiuuInformation.ReadUserSign(this);
        String verification = SuiuuInformation.ReadVerification(this);

        CollectionLoopFragment collectionLoopFragment = CollectionLoopFragment.newInstance(userSign, verification);
        CollectionSuiuuFragment collectionSuiuuFragment = CollectionSuiuuFragment.newInstance(userSign, verification);

        collectionList.add(collectionLoopFragment);
        collectionList.add(collectionSuiuuFragment);

        FragmentManager fm = getSupportFragmentManager();

        CollectionAdapter collectionAdapter = new CollectionAdapter(fm, collectionList);
        collectionPager.setAdapter(collectionAdapter);

        initImageView();
    }

    /**
     * 初始化相关图片
     */
    private void initImageView() {
        int sliderViewWidth = BitmapFactory.decodeResource(getResources(), R.drawable.slider).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        tabWidth = screenW / 2;
        if (sliderViewWidth > tabWidth) {
            collectionSlider.getLayoutParams().width = tabWidth;
            sliderViewWidth = tabWidth;
        }

        offsetX = (tabWidth - sliderViewWidth) / 2;
        collectionSlider.setPadding(offsetX, 0, 0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    class CollectionClick implements View.OnClickListener {

        private int index;

        public CollectionClick(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            collectionPager.setCurrentItem(index);
        }
    }

}
