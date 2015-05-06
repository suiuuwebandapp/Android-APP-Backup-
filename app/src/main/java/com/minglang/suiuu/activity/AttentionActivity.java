package com.minglang.suiuu.activity;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.AttentionPagerAdapter;
import com.minglang.suiuu.fragment.attention.AttentionLoopFragment;
import com.minglang.suiuu.fragment.attention.AttentionUserFragment;
import com.minglang.suiuu.utils.SuiuuInformation;
import com.minglang.suiuu.utils.SystemBarTintManager;
import com.minglang.suiuu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注页面
 */
public class AttentionActivity extends FragmentActivity {

    private static final String TAG = AttentionActivity.class.getSimpleName();

    /**
     * 返回键
     */
    private ImageView AttentionBack;

    private ViewPager attentionPager;

    /**
     * tab头
     */
    private TextView attentionThemeTitle, attentionUserTitle;

    /**
     * 滑块
     */
    private ImageView attentionSliderView;

    private int currIndex = 1;// 当前页卡编号

    private int tabWidth;// 每个tab头的宽度

    private int offsetX;//偏移量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);

        initView();

        ViewAction();

    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        AttentionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
            }
        });

        attentionPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
//                Log.i(TAG, "偏移量:" + String.valueOf(v));
            }

            @Override
            public void onPageSelected(int i) {
                attentionSliderView.setPadding(0, 0, 0, 0);

                switch (i) {
                    case 0:
                        attentionThemeTitle.setTextColor(getResources().getColor(R.color.slider_line_color));
                        attentionUserTitle.setTextColor(getResources().getColor(R.color.textColor));
                        break;
                    case 1:
                        attentionThemeTitle.setTextColor(getResources().getColor(R.color.textColor));
                        attentionUserTitle.setTextColor(getResources().getColor(R.color.slider_line_color));
                        break;
                }

                Animation anim = new TranslateAnimation(tabWidth * currIndex + offsetX, tabWidth * i + offsetX, 0, 0);
                currIndex = i;
                anim.setFillAfter(true);
                anim.setDuration(200);
                attentionSliderView.startAnimation(anim);

//                Log.i(TAG, "offsetX:" + String.valueOf(offsetX));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        attentionThemeTitle.setOnClickListener(new AttentionClick(0));
        attentionUserTitle.setOnClickListener(new AttentionClick(1));

    }

    /**
     * 初始化方法
     */
    private void initView() {

        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        int statusHeight = Utils.newInstance(this).getStatusHeight();

        /**
         系统版本是否高于4.4
         */
        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKITKAT) {
            LinearLayout rootLayout = (LinearLayout) findViewById(R.id.attentionRootLayout);
            rootLayout.setPadding(0, statusHeight, 0, 0);
            Log.i(TAG, "状态栏高度:" + statusHeight);
        }

        AttentionBack = (ImageView) findViewById(R.id.AttentionBack);

        attentionPager = (ViewPager) findViewById(R.id.attentionPager);

        attentionSliderView = (ImageView) findViewById(R.id.attention_sliderView);

        attentionThemeTitle = (TextView) findViewById(R.id.attention_theme_title);
        attentionUserTitle = (TextView) findViewById(R.id.attention_user_title);

        String userSign = SuiuuInformation.ReadUserSign(this);
        String verification = SuiuuInformation.ReadVerification(this);

        List<Fragment> fragmentList = new ArrayList<>();

        FragmentManager fm = getSupportFragmentManager();

        /**
         关注圈子页面
         */
        AttentionLoopFragment attentionLoopFragment = AttentionLoopFragment.newInstance(userSign, verification);

        /**
         关注用户页面
         */
        AttentionUserFragment attentionUserFragment = AttentionUserFragment.newInstance(userSign, verification);

        fragmentList.add(attentionLoopFragment);
        fragmentList.add(attentionUserFragment);

        AttentionPagerAdapter attentionPagerAdapter = new AttentionPagerAdapter(fm, fragmentList);

        attentionPager.setAdapter(attentionPagerAdapter);

        initImageView();
    }

    private void initImageView() {
        //滑动图片宽度
        int sliderViewWidth = BitmapFactory.decodeResource(getResources(), R.drawable.slider).getWidth();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenW = dm.widthPixels;// 获取屏幕宽度

        tabWidth = screenW / 2;
        if (sliderViewWidth > tabWidth) {
            attentionSliderView.getLayoutParams().width = tabWidth;
            sliderViewWidth = tabWidth;
        }

        offsetX = (tabWidth - sliderViewWidth) / 2;
        attentionSliderView.setPadding(offsetX, 0, 0, 0);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
    }

    class AttentionClick implements View.OnClickListener {

        private int index;

        public AttentionClick(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            attentionPager.setCurrentItem(index);
        }
    }

}
