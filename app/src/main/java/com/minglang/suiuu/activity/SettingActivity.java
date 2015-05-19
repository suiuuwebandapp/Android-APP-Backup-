package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.SettingAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.chat.chat.DemoApplication;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置页面
 */
public class SettingActivity extends BaseActivity {

    private static final String[] SETTINGS = {"个人设置", "通用设置", "检查更新", "联系我们", "反馈", "去评分"};

    private List<String> stringList;

    private ImageView settingBack;

    private ListView settingList;

    private Button btn_logout;

    private TextView tv_top_right;

    private TextView tv_top_center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

        ViewAction();

    }

    private void ViewAction() {

        settingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        settingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:
                        Intent intent0 = new Intent(SettingActivity.this, PersonalSettingActivity.class);
                        startActivity(intent0);
                        break;

                    case 1:
                        startActivity(new Intent(SettingActivity.this, NormalSettingActivity.class));
                        break;

                    case 2:
                        break;

                    case 3:
                        Intent intent3 = new Intent(SettingActivity.this, ContactUsActivity.class);
                        startActivity(intent3);
                        break;

                    case 4:
                        Intent intent4 = new Intent(SettingActivity.this, FeedbackActivity.class);
                        startActivity(intent4);
                        break;

                    case 5:
                        break;
                }
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuiuuInfo.ClearSuiuuInfo(SettingActivity.this);
                SuiuuInfo.ClearSuiuuThird(SettingActivity.this);
                logout();
            }
        });

    }

    /**
     * 初始化方法
     */
    private void initView() {
        tv_top_center = (TextView) findViewById(R.id.tv_top_center);
        tv_top_center.setVisibility(View.VISIBLE);
        tv_top_center.setText(R.string.setting);
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        tv_top_right.setVisibility(View.INVISIBLE);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        if (!TextUtils.isEmpty(EMChatManager.getInstance().getCurrentUser())) {
            btn_logout.setText(getString(R.string.button_logout));
        }
        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        int statusBarHeight = mTintManager.getConfig().getStatusBarHeight();

        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKITKAT) {
            LinearLayout settingLayout = (LinearLayout) findViewById(R.id.settingRootLayout);
            settingLayout.setPadding(0, statusBarHeight, 0, 0);
        }

        settingBack = (ImageView) findViewById(R.id.iv_top_back);
        settingList = (ListView) findViewById(R.id.settingList);

        stringList = new ArrayList<>();

        for (String s : SETTINGS) {
            stringList.add(s);
        }

        SettingAdapter adapter = new SettingAdapter(this, stringList);

        settingList.setAdapter(adapter);

    }

    public void logout() {
        final ProgressDialog pd = new ProgressDialog(this);
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        DemoApplication.getInstance().logout(new EMCallBack() {

            @Override
            public void onSuccess() {
                SettingActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        // 重新显示登陆页面
                        (SettingActivity.this).finish();
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class));

                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String message) {
            }
        });
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}