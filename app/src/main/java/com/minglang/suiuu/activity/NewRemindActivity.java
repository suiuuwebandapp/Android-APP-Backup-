package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_remind);

        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        RelativeLayout newRemindRootLayout = (RelativeLayout) findViewById(R.id.newRemindRootLayout);
        if (isKITKAT) {
            if (navigationBarHeight <= 0) {
                newRemindRootLayout.setPadding(0, statusBarHeight, 0, 0);
            } else {
                newRemindRootLayout.setPadding(0, statusBarHeight, 0, navigationBarHeight);
            }
        }

        newRemindBack = (ImageView) findViewById(R.id.newRemindBack);

        newAt = (TextView) findViewById(R.id.newAt);
        newComment = (TextView) findViewById(R.id.newComment);
        newReply = (TextView) findViewById(R.id.newReply);
        newAttention = (TextView) findViewById(R.id.newAttention);

        newRemindSlider = (ImageView) findViewById(R.id.newRemindSlider);

        newRemindPager = (ViewPager) findViewById(R.id.newRemindPager);
        newRemindPager.setOffscreenPageLimit(4);

        initImageView();
        CreateFragment();

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(newAtFragment);
        fragmentList.add(newCommentFragment);
        fragmentList.add(newReplyFragment);
        fragmentList.add(newAttentionFragment);

        NewRemindAdapter newRemindAdapter = new NewRemindAdapter(fm, fragmentList);
        newRemindPager.setAdapter(newRemindAdapter);
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

        newRemindPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
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

                Animation anim = new TranslateAnimation(tabWidth * currIndex + offsetX, tabWidth * position + offsetX, 0, 0);
                currIndex = position;
                anim.setFillAfter(true);
                anim.setDuration(200);
                newRemindSlider.startAnimation(anim);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initImageView() {
        tabWidth = screenWidth / 4;
        if (sliderImageWidth > tabWidth) {
            newRemindSlider.getLayoutParams().width = tabWidth;
            sliderImageWidth = tabWidth;
        }
        offsetX = (tabWidth - sliderImageWidth) / 2;
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
