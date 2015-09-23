package com.minglang.suiuu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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

    @Bind(R.id.receivables_item_view)
    TextView receivablesItemView;

    @Bind(R.id.about_item_view)
    TextView aboutItemView;

    @Bind(R.id.contact_item_view)
    TextView contactItemView;

    @Bind(R.id.login_out_button)
    Button loginOutButton;

    private Context context;

    private LocalBroadcastManager localBroadcastManager;

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
        context = SettingActivity.this;

        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    private void viewAction() {

        normalItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, NormalSettingActivity.class));
            }
        });

        receivablesItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ReceivablesWayActivity.class));
            }
        });

        aboutItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AboutSuiuuActivity.class));
            }
        });

        contactItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ContactUsActivity.class));
            }
        });

        loginOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SuiuuInfo.ClearSuiuuInfo(context);
                SuiuuInfo.ClearWeChatInfo(context);
                SuiuuInfo.ClearAliPayInfo(context);

                localBroadcastManager.sendBroadcast(new Intent(TAG));

                SettingActivity.this.finish(); // 重新显示登陆页面

                startActivity(new Intent(context, FirstLoginActivity.class));
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