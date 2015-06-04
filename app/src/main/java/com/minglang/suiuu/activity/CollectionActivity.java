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
import com.minglang.suiuu.adapter.CollectionAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.fragment.collection.CollectionLoopFragment;
import com.minglang.suiuu.fragment.collection.CollectionSuiuuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏页面
 */
public class CollectionActivity extends BaseActivity {

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
            }
        });

        collectionSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        collectionLoop.setOnClickListener(new CollectionClick(0));
        collectionSuiuu.setOnClickListener(new CollectionClick(1));

        collectionPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                collectionSlider.setPadding(0, 0, 0, 0);

                switch (position) {
                    case 0:
                        collectionLoop.setTextColor(getResources().getColor(R.color.slider_line_color));
                        collectionSuiuu.setTextColor(getResources().getColor(R.color.textColor));
                        break;
                    case 1:
                        collectionLoop.setTextColor(getResources().getColor(R.color.textColor));
                        collectionSuiuu.setTextColor(getResources().getColor(R.color.slider_line_color));
                        break;

                }

                Animation anim = new TranslateAnimation(tabWidth * currIndex + offsetX, tabWidth * position + offsetX, 0, 0);
                currIndex = position;
                anim.setFillAfter(true);
                anim.setDuration(200);
                collectionSlider.startAnimation(anim);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化方法
     */
    private void initView() {
        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.collectionRootLayout);
        if (isKITKAT) {
            if (navigationBarHeight <= 0) {
                rootLayout.setPadding(0, statusBarHeight, 0, 0);
            } else {
                rootLayout.setPadding(0, statusBarHeight, 0, navigationBarHeight);
            }
        }

        collectionBack = (ImageView) findViewById(R.id.collectionBack);
        collectionSearch = (ImageView) findViewById(R.id.collectionSearch);
        collectionLoop = (TextView) findViewById(R.id.collectionLoop);
        collectionSuiuu = (TextView) findViewById(R.id.collectionSuiuu);
        collectionSlider = (ImageView) findViewById(R.id.collectionSlider);
        collectionPager = (ViewPager) findViewById(R.id.collectionPager);

        CollectionLoopFragment collectionLoopFragment = CollectionLoopFragment.newInstance(userSign, verification);
        CollectionSuiuuFragment collectionSuiuuFragment = CollectionSuiuuFragment.newInstance(userSign, verification);

        List<Fragment> collectionList = new ArrayList<>();
        collectionList.add(collectionLoopFragment);
        collectionList.add(collectionSuiuuFragment);

        CollectionAdapter collectionAdapter = new CollectionAdapter(fm, collectionList);
        collectionPager.setAdapter(collectionAdapter);

        initImageView();
    }

    /**
     * 初始化相关图片
     */
    private void initImageView() {
        tabWidth = screenWidth / 2;
        if (sliderImageWidth > tabWidth) {
            collectionSlider.getLayoutParams().width = tabWidth;
            sliderImageWidth = tabWidth;
        }

        offsetX = (tabWidth - sliderImageWidth) / 2;
        collectionSlider.setPadding(offsetX, 0, 0, 0);
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
