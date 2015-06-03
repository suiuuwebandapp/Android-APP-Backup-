package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 设置页面
 */
public class SettingActivity extends BaseActivity {

    private static final String TAG = SettingActivity.class.getSimpleName();

    private static final String[] SETTINGS = {"个人设置", "通用设置", "检查更新", "联系我们", "反馈", "去评分"};

    private ImageView settingBack;

    private ListView settingList;

    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        TextView tv_top_center = (TextView) findViewById(R.id.tv_top_center);
        tv_top_center.setVisibility(View.VISIBLE);
        tv_top_center.setText(R.string.setting);

        TextView tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        tv_top_right.setVisibility(View.INVISIBLE);

        btn_logout = (Button) findViewById(R.id.btn_logout);
        if (!TextUtils.isEmpty(EMChatManager.getInstance().getCurrentUser())) {
            btn_logout.setText(getString(R.string.button_logout));
        }

        LinearLayout settingLayout = (LinearLayout) findViewById(R.id.settingRootLayout);
        if (isKITKAT) {
            if (navigationBarHeight <= 0) {
                settingLayout.setPadding(0, statusBarHeight, 0, 0);
            } else {
                settingLayout.setPadding(0, statusBarHeight, 0, navigationBarHeight);
            }
        } else {
            if (navigationBarHeight > 0) {
                settingLayout.setPadding(0, statusBarHeight, 0, navigationBarHeight);
            } else {
                settingLayout.setPadding(0, 0, 0, navigationBarHeight);
            }
        }

        settingBack = (ImageView) findViewById(R.id.iv_top_back);
        settingList = (ListView) findViewById(R.id.settingList);

        List<String> stringList = new ArrayList<>();
        Collections.addAll(stringList, SETTINGS);
        SettingAdapter adapter = new SettingAdapter(this, stringList);
        settingList.setAdapter(adapter);
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
                        startActivity( new Intent(SettingActivity.this, PersonalSettingActivity.class));
                        break;

                    case 1:
                        startActivity(new Intent(SettingActivity.this, NormalSettingActivity.class));
                        break;

                    case 2:
                        break;

                    case 3:
                        startActivity(new Intent(SettingActivity.this, ContactUsActivity.class));
                        break;

                    case 4:
                        startActivity(new Intent(SettingActivity.this, FeedbackActivity.class));
                        break;

                    case 5:
                        String mAddress = "market://details?id=" + getPackageName();
                        Intent marketIntent = new Intent("android.intent.action.VIEW");
                        marketIntent.setData(Uri.parse(mAddress));
                        startActivity(marketIntent);
                        break;
                }
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }

    public void logout() {
        final ProgressDialog pd = new ProgressDialog(this);
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        SuiuuApplication.getInstance().logout(new EMCallBack() {

            @Override
            public void onSuccess() {
                SettingActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
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
            public void onProgress(int progress, String status) {
                DeBugLog.i(TAG, "progress:" + String.valueOf(progress));
                DeBugLog.i(TAG, "status:" + status);
            }

            @Override
            public void onError(int code, String message) {
                DeBugLog.e(TAG, "code:" + String.valueOf(code));
                DeBugLog.e(TAG, "message:" + message);
            }
        });
    }

}