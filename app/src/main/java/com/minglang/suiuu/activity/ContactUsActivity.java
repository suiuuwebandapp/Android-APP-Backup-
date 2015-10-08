package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

/**
 * 联系我们
 */
public class ContactUsActivity extends BaseAppCompatActivity {

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.contact_us_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.contact_us_feed_back)
    Button feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
        initView();
        viewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

    }

    private void viewAction() {
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactUsActivity.this, FeedbackActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}