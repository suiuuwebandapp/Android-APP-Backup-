package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.DeBugLog;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

/**
 * 管理收款方式页面
 */
public class ReceivablesWayActivity extends BaseAppCompatActivity {

    private static final String TAG = ReceivablesWayActivity.class.getSimpleName();

    private static final String KEY = "key";

    private static final String ALIPAY = "alipay";
    private static final String WECHAT = "weChat";

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.receivables_way_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.receivables_way_list_view)
    ListView receivablesWayListView;

    @Bind(R.id.receivables_way_layout)
    LinearLayout linearLayout;

    @Bind(R.id.receivables_way_alipay)
    Button alipayButton;

    @Bind(R.id.receivables_way_wechat)
    Button weChatButton;

    private Animation animationEnter;
    private Animation animationExit;

    private boolean isClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivables_way);
        ButterKnife.bind(this);
        initView();
        viewAction();
    }

    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        float layoutHeight = getResources().getDimension(R.dimen.layout_150dp);

        animationEnter = new TranslateAnimation(0, 0, layoutHeight, 0);
        animationEnter.setDuration(1000);

        animationExit = new TranslateAnimation(0, 0, 0, layoutHeight);
        animationExit.setDuration(500);
    }

    private void viewAction() {

        animationEnter.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationExit.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linearLayout.clearAnimation();
                linearLayout.setVisibility(View.GONE);
                if (isClick) {
                    Intent intent0 = new Intent(ReceivablesWayActivity.this, AddReceivablesWayActivity.class);
                    intent0.putExtra(KEY, ALIPAY);
                    startActivity(intent0);
                } else {
                    Intent intent1 = new Intent(ReceivablesWayActivity.this, AddReceivablesWayActivity.class);
                    intent1.putExtra(KEY, WECHAT);
                    startActivity(intent1);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        alipayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = true;
                linearLayout.clearAnimation();
                linearLayout.setAnimation(animationExit);
            }
        });

        weChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = false;
                linearLayout.clearAnimation();
                linearLayout.setAnimation(animationExit);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_receivables_way, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;

            case R.id.add_receivables:
                DeBugLog.i(TAG, "click add_receivables");
                linearLayout.setAnimation(animationEnter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}