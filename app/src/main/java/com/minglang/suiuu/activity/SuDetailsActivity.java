package com.minglang.suiuu.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.SystemBarTintManager;

public class SuDetailsActivity extends Activity {

    /**
     * 返回键
     */
    private ImageView back;

    /**
     * 点赞按钮
     */
    private TextView praise;

    /**
     * 收藏按钮
     */
    private TextView collection;

    /**
     * 滑动页面布局
     */
    private RelativeLayout slideLayout;

    /**
     * 图片滑动
     */
    private ViewPager viewPager;

    /**
     * 头像
     */
    private ImageView headImage;

    /**
     * 用户名
     */
    private TextView userName;

    /**
     * 用户个性签名
     */
    private TextView userSignature;

    /**
     * 星级评价啊
     */
    private RatingBar ratingBar;

    /**
     * 内容
     */
    private TextView content;

    private boolean isPraise = false;

    private boolean isCollection = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_su_details);


        initView();

        ViewAction();

    }

    private void ViewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPraise) {
                    Drawable white = getResources().getDrawable(R.drawable.icon_praise_white);
                    white.setBounds(0, 0, white.getMinimumWidth(), white.getMinimumHeight());
                    praise.setCompoundDrawables(white, null, null, null);
                    praise.setTextColor(getResources().getColor(R.color.white));
                    isPraise = false;
                } else {
                    Drawable red = getResources().getDrawable(R.drawable.icon_praise_red);
                    red.setBounds(0, 0, red.getMinimumWidth(), red.getMinimumHeight());
                    praise.setCompoundDrawables(red, null, null, null);
                    praise.setTextColor(getResources().getColor(R.color.click_red));
                    isPraise = true;
                }

            }
        });

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isCollection) {
                    Drawable white = getResources().getDrawable(R.drawable.icon_collection_white);
                    white.setBounds(0, 0, white.getMinimumWidth(), white.getMinimumHeight());
                    collection.setCompoundDrawables(white, null, null, null);
                    collection.setTextColor(getResources().getColor(R.color.white));
                    isCollection = false;
                } else {
                    Drawable yellow = getResources().getDrawable(R.drawable.icon_collection_yellow);
                    yellow.setBounds(0, 0, yellow.getMinimumWidth(), yellow.getMinimumHeight());
                    collection.setCompoundDrawables(yellow, null, null, null);
                    collection.setTextColor(getResources().getColor(R.color.click_yellow));
                    isCollection = true;
                }

            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    /**
     * 初始化方法
     */
    private void initView() {

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        int statusHeight = mTintManager.getConfig().getStatusBarHeight();

        /**
         系统版本是否高于4.4
         */
        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKITKAT) {
            RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.SuDetailsRootLayout);
            rootLayout.setPadding(0, statusHeight, 0, 0);
        }

        back = (ImageView) findViewById(R.id.SuDetailsBack);

        praise = (TextView) findViewById(R.id.SuDetailsPraise);

        collection = (TextView) findViewById(R.id.loop_article_collection);

        slideLayout = (RelativeLayout) findViewById(R.id.SuDetailsSlideLayout);

        viewPager = (ViewPager) findViewById(R.id.SuDetailsPager);

        headImage = (ImageView) findViewById(R.id.SuDetailsHeadImage);

        userName = (TextView) findViewById(R.id.SuDetailsUserName);
        userSignature = (TextView) findViewById(R.id.SuDetailsSignature);

        ratingBar = (RatingBar) findViewById(R.id.SuDetailsRatingBar);

        content = (TextView) findViewById(R.id.SuDetailsContent);

        RelativeLayout.LayoutParams slideLayoutParams = new RelativeLayout.LayoutParams(slideLayout.getLayoutParams());
        slideLayoutParams.height = screenHeight / 3;
        slideLayoutParams.width = screenWidth;
        slideLayout.setLayoutParams(slideLayoutParams);
    }


}
