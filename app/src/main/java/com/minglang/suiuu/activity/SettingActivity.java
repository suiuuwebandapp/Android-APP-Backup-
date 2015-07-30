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
import android.widget.ListView;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.SettingAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.SuiuuInfo;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 设置页面
 */
public class SettingActivity extends BaseAppCompatActivity {

    private static final String TAG = SettingActivity.class.getSimpleName();

    @BindString(R.string.setting)
    String strSetting;

    @BindString(R.string.button_logout)
    String logoutButtonText;

    @BindString(R.string.Are_logged_out)
    String logoutText;

    @Bind(R.id.tv_top_center)
    TextView tv_top_center;

    @Bind(R.id.tv_top_right)
    TextView tv_top_right;

    @Bind(R.id.iv_top_back)
    ImageView settingBack;

    @Bind(R.id.settingList)
    ListView settingList;

    @Bind(R.id.btn_logout)
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);
        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        String[] SETTINGS = getResources().getStringArray(R.array.personalList);

        tv_top_center.setVisibility(View.VISIBLE);
        tv_top_center.setText(strSetting);

        tv_top_right.setVisibility(View.INVISIBLE);

        if (!TextUtils.isEmpty(EMChatManager.getInstance().getCurrentUser())) {
            btn_logout.setText(logoutButtonText);
        }

        SettingAdapter adapter = new SettingAdapter(this, SETTINGS);
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
                        startActivity(new Intent(SettingActivity.this, PersonalSettingActivity.class));
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

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(logoutText);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        SuiuuApplication.getInstance().logout(new EMCallBack() {

            @Override
            public void onSuccess() {
                SettingActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
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