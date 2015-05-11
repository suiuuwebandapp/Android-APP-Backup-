package com.minglang.suiuu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.utils.SystemBarTintManager;

public class PersonSettingActivity extends Activity {

    /**
     * 返回按钮
     */
    private ImageView personalSettingBack;

    private TextView save;

    /**
     * 头像ImageView
     */
    private CircleImageView headImageView;

    private EditText editNickName;

    private EditText editLocation;

    private EditText editTrade;

    private EditText editSign;

    private String str_NickName;

    private String str_Location;

    private String str_Trade;

    private String str_Sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_setting);

        initView();

        ViewAction();

    }

    private void ViewAction() {

        personalSettingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_NickName = editNickName.getText().toString().trim();
                str_Location = editLocation.getText().toString().trim();
                str_Trade = editTrade.getText().toString().trim();
                str_Sign = editSign.getText().toString().trim();

                Toast.makeText(PersonSettingActivity.this, "昵称:" +
                        str_NickName +",位置:" + str_Location
                        + ",职业:" + str_Trade + ",签名:" + str_Sign,
                        Toast.LENGTH_SHORT).show();
            }
        });

        headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void initView() {

        /****************设置状态栏颜色*************/
        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        SystemBarTintManager.SystemBarConfig systemBarConfig = mTintManager.getConfig();

        int statusBarHeight = systemBarConfig.getStatusBarHeight();

        /**
         系统版本是否高于4.4
         */
        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKITKAT) {
            RelativeLayout personalRootLayout = (RelativeLayout) findViewById(R.id.personalRootLayout);
            personalRootLayout.setPadding(0, statusBarHeight, 0, 0);
        }

        personalSettingBack = (ImageView) findViewById(R.id.personalSettingBack);

        save = (TextView) findViewById(R.id.personal_save);

        headImageView = (CircleImageView) findViewById(R.id.headImageView);

        editNickName = (EditText) findViewById(R.id.editNickName);
        editLocation = (EditText) findViewById(R.id.editLocation);
        editTrade = (EditText) findViewById(R.id.editTrade);
        editSign = (EditText) findViewById(R.id.editSign);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
