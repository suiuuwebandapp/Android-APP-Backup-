package com.minglang.suiuu.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.TracePagerAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.fragment.trace.TracePostFragment;
import com.minglang.suiuu.fragment.trace.TraceRouteFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看其他用户发的帖子和路线
 */

public class OtherUserTraceActivity extends BaseActivity {

    private TextView userNameView;

    private ImageView traceBack;

    private ImageView traceSlider;

    private TextView tracePost, traceRoute;

    private ViewPager tracePager;

    private int currIndex = 1;// 当前页卡编号

    private int tabWidth;// 每个tab头的宽度

    private int offsetX;//偏移量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_trace);

        initView();

        ViewAction();

    }

    private void ViewAction() {
        traceBack.setOnClickListener(new TraceClick());

        tracePost.setOnClickListener(new TraceClick(0));
        traceRoute.setOnClickListener(new TraceClick(1));

        tracePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

                switch (i){
                    case 0:
                        tracePost.setTextColor(getResources().getColor(R.color.slider_line_color));
                        traceRoute.setTextColor(getResources().getColor(R.color.textColor));
                        break;
                    case 1:
                        tracePost.setTextColor(getResources().getColor(R.color.textColor));
                        traceRoute.setTextColor(getResources().getColor(R.color.slider_line_color));
                        break;
                }

                Animation anim = new TranslateAnimation(tabWidth * currIndex + offsetX, tabWidth * i + offsetX, 0, 0);
                currIndex = i;
                anim.setFillAfter(true);
                anim.setDuration(200);
                traceSlider.startAnimation(anim);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initView() {
        userNameView = (TextView) findViewById(R.id.OtherUserTraceName);
        userNameView.setText("用户名");

        traceBack = (ImageView) findViewById(R.id.otherUserTraceBack);

        tracePost = (TextView) findViewById(R.id.otherUserTracePost);
        traceRoute = (TextView) findViewById(R.id.otherUserTraceRoute);

        traceSlider = (ImageView) findViewById(R.id.otherUserTraceSlider);

        tracePager = (ViewPager) findViewById(R.id.otherUserTracePager);

        TracePostFragment tracePostFragment = TracePostFragment.newInstance("a", "b");
        TraceRouteFragment traceRouteFragment = TraceRouteFragment.newInstance("c", "d");

        fm = getSupportFragmentManager();

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(tracePostFragment);
        fragmentList.add(traceRouteFragment);

        TracePagerAdapter tracePagerAdapter = new TracePagerAdapter(fm, fragmentList);
        
        tracePager.setAdapter(tracePagerAdapter);

        initImage();
    }

    private void initImage() {
        int sliderViewWidth = BitmapFactory.decodeResource(getResources(), R.drawable.slider).getWidth();
        tabWidth = screenWidth / 2;
        if (sliderViewWidth > tabWidth) {
            traceSlider.getLayoutParams().width = tabWidth;
            sliderViewWidth = tabWidth;
        }
        offsetX = (tabWidth - sliderViewWidth) / 2;
    }

    class TraceClick implements View.OnClickListener {

        private int index;

        public TraceClick() {

        }

        public TraceClick(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.otherUserTraceBack:
                    finish();
                    break;

                case R.id.otherUserTracePost:
                    tracePager.setCurrentItem(index);
                    break;

                case R.id.otherUserTraceRoute:
                    tracePager.setCurrentItem(index);
                    break;

            }
        }
    }

}
