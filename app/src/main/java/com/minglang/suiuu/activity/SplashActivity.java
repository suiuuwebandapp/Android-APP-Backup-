package com.minglang.suiuu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.chat.chat.DemoHXSDKHelper;

public class SplashActivity extends Activity{
    private ImageView iv_backGround;
    private ImageView iv_backGround2;
    private ImageView iv_showInCenter;
    private int height = 0;
    private int width = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        height = metric.heightPixels;  // 屏幕高度（像素）
        width = metric.widthPixels;
        iv_backGround = (ImageView) findViewById(R.id.iv_background);
        iv_backGround2 = (ImageView) findViewById(R.id.iv_background2);
        iv_showInCenter = (ImageView) findViewById(R.id.im_splash);
        AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
        animation.setDuration(1500);
        findViewById(R.id.root).startAnimation(animation);

        final Animation transAnim = new TranslateAnimation(0,-50, 0, 0);
        transAnim.setFillAfter(true);
        transAnim.setDuration(3500);

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
                iv_showInCenter.setPadding(0,height/3,0,0);
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
                if (DemoHXSDKHelper.getInstance().isLogined()) {
                    boolean autoLogin = true;
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }else {
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

}
