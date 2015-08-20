package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.SuiuuInfo;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

/**
 * 设置页面
 */
public class SettingActivity extends BaseAppCompatActivity {

    private static final String TAG = SettingActivity.class.getSimpleName();

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.setting_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.normal_item_view)
    TextView normalItemView;

    @Bind(R.id.collect_item_view)
    TextView collectItemView;

    @Bind(R.id.about_item_view)
    TextView aboutItemView;

    @Bind(R.id.contact_item_view)
    TextView contactItemView;

    @Bind(R.id.login_out_button)
    Button loginOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
        viewAction();
    }

    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);
    }

    private void viewAction() {
        normalItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, NormalSettingActivity.class));
            }
        });

        collectItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        aboutItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AboutSuiuuActivity.class));
            }
        });

        contactItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ContactUsActivity.class));
            }
        });

        loginOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuiuuInfo.ClearSuiuuInfo(SettingActivity.this);
                SuiuuInfo.ClearSuiuuThird(SettingActivity.this);
                Intent intent = new Intent();
                intent.setAction(TAG);
                sendBroadcast(intent);
                SettingActivity.this.finish(); // 重新显示登陆页面
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
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