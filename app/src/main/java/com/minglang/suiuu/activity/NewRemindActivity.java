package com.minglang.suiuu.activity;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.NewRemindAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.fragment.remind.NewAtFragment;
import com.minglang.suiuu.fragment.remind.NewAttentionFragment;
import com.minglang.suiuu.fragment.remind.NewCommentFragment;
import com.minglang.suiuu.fragment.remind.NewReplyFragment;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 新提醒页面
 */

public class NewRemindActivity extends BaseActivity {

    /**
     * 返回键
     */
    private ImageView newRemindBack;

    private TextView newAt, newComment, newReply, newAttention;

    private ImageView newRemindSlider;

    private ViewPager newRemindPager;

    private int currIndex = 1;// 当前页卡编号

    private int tabWidth;// 每个tab头的宽度

    private int offsetX;//偏移量

    /**
     * 新@页面
     */
    private NewAtFragment newAtFragment;

    /**
     * 新评论页面
     */
    private NewCommentFragment newCommentFragment;

    /**
     * 新回复页面
     */
    private NewReplyFragment newReplyFragment;

    /**
     * 新关注页面
     */
    private NewAttentionFragment newAttentionFragment;

    private String userSign;

    private String verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_remind);

        userSign = SuiuuInfo.ReadUserSign(this);
        verification = SuiuuInfo.ReadVerification(this);

        initView();

        ViewAction();

    }

    /**
     * 控件动作
     */
    private void ViewAction() {
        newRemindBack.setOnClickListener(new NewRemindClick());

        newAt.setOnClickListener(new NewRemindClick(0));
        newComment.setOnClickListener(new NewRemindClick(1));
        newReply.setOnClickListener(new NewRemindClick(2));
        newAttention.setOnClickListener(new NewRemindClick(3));

        newRemindPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

                switch (i) {
                    case 0:
                        newAt.setTextColor(getResources().getColor(R.color.slider_line_color));
                        newComment.setTextColor(getResources().getColor(R.color.textColor));
                        newReply.setTextColor(getResources().getColor(R.color.textColor));
                        newAttention.setTextColor(getResources().getColor(R.color.textColor));
                        break;

                    case 1:
                        newAt.setTextColor(getResources().getColor(R.color.textColor));
                        newComment.setTextColor(getResources().getColor(R.color.slider_line_color));
                        newReply.setTextColor(getResources().getColor(R.color.textColor));
                        newAttention.setTextColor(getResources().getColor(R.color.textColor));
                        break;

                    case 2:
                        newAt.setTextColor(getResources().getColor(R.color.textColor));
                        newComment.setTextColor(getResources().getColor(R.color.textColor));
                        newReply.setTextColor(getResources().getColor(R.color.slider_line_color));
                        newAttention.setTextColor(getResources().getColor(R.color.textColor));
                        break;

                    case 3:
                        newAt.setTextColor(getResources().getColor(R.color.textColor));
                        newComment.setTextColor(getResources().getColor(R.color.textColor));
                        newReply.setTextColor(getResources().getColor(R.color.textColor));
                        newAttention.setTextColor(getResources().getColor(R.color.slider_line_color));
                        break;
                }

                Animation anim = new TranslateAnimation(tabWidth * currIndex + offsetX, tabWidth * i + offsetX, 0, 0);
                currIndex = i;
                anim.setFillAfter(true);
                anim.setDuration(200);
                newRemindSlider.startAnimation(anim);

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

        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        int statusHeight = mTintManager.getConfig().getStatusBarHeight();

        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKITKAT) {
            RelativeLayout newRemindRootLayout = (RelativeLayout) findViewById(R.id.newRemindRootLayout);
            newRemindRootLayout.setPadding(0, statusHeight, 0, 0);
        }

        newRemindBack = (ImageView) findViewById(R.id.newRemindBack);

        newAt = (TextView) findViewById(R.id.newAt);
        newComment = (TextView) findViewById(R.id.newComment);
        newReply = (TextView) findViewById(R.id.newReply);
        newAttention = (TextView) findViewById(R.id.newAttention);

        newRemindSlider = (ImageView) findViewById(R.id.newRemindSlider);

        newRemindPager = (ViewPager) findViewById(R.id.newRemindPager);
        newRemindPager.setOffscreenPageLimit(4);

        List<Fragment> fragmentList = new ArrayList<>();

        FragmentManager fm = getSupportFragmentManager();

        initImageView();

        CreateFragment();

        fragmentList.add(newAtFragment);
        fragmentList.add(newCommentFragment);
        fragmentList.add(newReplyFragment);
        fragmentList.add(newAttentionFragment);

        NewRemindAdapter newRemindAdapter = new NewRemindAdapter(fm, fragmentList);

        newRemindPager.setAdapter(newRemindAdapter);
    }

    private void initImageView() {
        int sliderViewWidth = BitmapFactory.decodeResource(getResources(), R.drawable.slider).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        tabWidth = screenW / 4;
        if (sliderViewWidth > tabWidth) {
            newRemindSlider.getLayoutParams().width = tabWidth;
            sliderViewWidth = tabWidth;
        }

        offsetX = (tabWidth - sliderViewWidth) / 2;
    }

    private void CreateFragment() {
        newAtFragment = NewAtFragment.newInstance(userSign, verification);
        newCommentFragment = NewCommentFragment.newInstance(userSign, verification);
        newReplyFragment = NewReplyFragment.newInstance(userSign, verification);
        newAttentionFragment = NewAttentionFragment.newInstance(userSign, verification);
    }

    class NewRemindClick implements View.OnClickListener {

        private int index;

        public NewRemindClick() {

        }

        public NewRemindClick(int i) {
            this.index = i;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.newRemindBack:
                    finish();
                    break;
                case R.id.newAt:
                    newRemindPager.setCurrentItem(index);
                    break;
                case R.id.newComment:
                    newRemindPager.setCurrentItem(index);
                    break;
                case R.id.newReply:
                    newRemindPager.setCurrentItem(index);
                    break;
                case R.id.newAttention:
                    newRemindPager.setCurrentItem(index);
                    break;
            }
        }
    }

}
