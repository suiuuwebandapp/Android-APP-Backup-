package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.AttentionPagerAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.fragment.attention.AttentionLoopFragment;
import com.minglang.suiuu.fragment.attention.AttentionUserFragment;
import com.minglang.suiuu.utils.DeBugLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注页面
 */
public class AttentionActivity extends BaseActivity {

    private static final String TAG = AttentionActivity.class.getSimpleName();

    /**
     * 返回键
     */
    private ImageView attentionBack;

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

        attentionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        attentionPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                DeBugLog.i(TAG, "position:" + position + ",positionOffset:" + positionOffset +
                        ",positionOffsetPixels" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                attentionSliderView.setPadding(0, 0, 0, 0);

                switch (position) {
                    case 0:
                        attentionThemeTitle.setTextColor(getResources().getColor(R.color.slider_line_color));
                        attentionUserTitle.setTextColor(getResources().getColor(R.color.textColor));
                        break;
                    case 1:
                        attentionThemeTitle.setTextColor(getResources().getColor(R.color.textColor));
                        attentionUserTitle.setTextColor(getResources().getColor(R.color.slider_line_color));
                        break;
                }

                Animation anim = new TranslateAnimation(tabWidth * currIndex + offsetX, tabWidth * position + offsetX, 0, 0);
                currIndex = position;
                anim.setFillAfter(true);
                anim.setDuration(200);
                attentionSliderView.startAnimation(anim);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                DeBugLog.i(TAG, "state:" + state);
            }
        });

        attentionThemeTitle.setOnClickListener(new AttentionClick(0));
        attentionUserTitle.setOnClickListener(new AttentionClick(1));

    }

    /**
     * 初始化方法
     */
    private void initView() {
        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.attentionRootLayout);
        if (isKITKAT) {
            if (navigationBarHeight <= 0) {
                rootLayout.setPadding(0, statusBarHeight, 0, 0);
            } else {
                rootLayout.setPadding(0, statusBarHeight, 0, navigationBarHeight);
            }
        }

        attentionBack = (ImageView) findViewById(R.id.attentionBack);
        attentionPager = (ViewPager) findViewById(R.id.attentionPager);
        attentionSliderView = (ImageView) findViewById(R.id.attention_sliderView);
        attentionThemeTitle = (TextView) findViewById(R.id.attention_theme_title);
        attentionUserTitle = (TextView) findViewById(R.id.attention_user_title);

        //关注圈子页面
        AttentionLoopFragment attentionLoopFragment = AttentionLoopFragment.newInstance(userSign, verification);
        //关注用户页面
        AttentionUserFragment attentionUserFragment = AttentionUserFragment.newInstance(userSign, verification);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(attentionLoopFragment);
        fragmentList.add(attentionUserFragment);
        AttentionPagerAdapter attentionPagerAdapter = new AttentionPagerAdapter(fm, fragmentList);
        attentionPager.setAdapter(attentionPagerAdapter);

        initImageView();
    }

    private void initImageView() {
        tabWidth = screenWidth / 2;
        if (sliderImageWidth > tabWidth) {
            attentionSliderView.getLayoutParams().width = tabWidth;
            sliderImageWidth = tabWidth;
        }

        offsetX = (tabWidth - sliderImageWidth) / 2;
        attentionSliderView.setPadding(offsetX, 0, 0, 0);
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
