package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.service.LocationService;
import com.minglang.suiuu.utils.SuiuuInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseAppCompatActivity{

    @Bind(R.id.splash_root_layout)
    RelativeLayout rootLayout;

    @Bind(R.id.iv_background)
    ImageView iv_backGround;

    @Bind(R.id.iv_background2)
    ImageView iv_backGround2;

    @Bind(R.id.im_splash)
    ImageView iv_showInCenter;

    private AlphaAnimation animation;

    private Animation transAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        init();
        viewAction();
    }

    public void init() {
        animation = new AlphaAnimation(0.2f, 1.0f);
        animation.setDuration(1500);

        transAnim = new TranslateAnimation(0, -50, 0, 0);
        transAnim.setFillAfter(true);
        transAnim.setDuration(2500);

        startService(new Intent(this, LocationService.class));
    }

    private void viewAction() {

        rootLayout.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_backGround.setVisibility(View.GONE);
                iv_backGround2.setVisibility(View.INVISIBLE);

                iv_backGround2.setImageResource(R.drawable.splash2);
                iv_showInCenter.setImageResource(R.drawable.splash_text);

                iv_showInCenter.setPadding(0, screenHeight / 3, 0, 0);
                iv_backGround2.setAnimation(transAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

        transAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //		 如果用户名密码都有，直接进入主页面
                if (!TextUtils.isEmpty(SuiuuInfo.ReadUserData(SplashActivity.this).getNickname())) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginsMainActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

    }

}