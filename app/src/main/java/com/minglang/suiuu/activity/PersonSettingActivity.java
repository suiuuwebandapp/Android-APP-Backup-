package com.minglang.suiuu.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.SystemBarTintManager;

public class PersonSettingActivity extends Activity {

    private ImageView personalSettingBack;

    private LinearLayout personalSettingHeadLayout;

    private ImageView personalSettingHeadImage;

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

        personalSettingHeadLayout.setOnClickListener(new View.OnClickListener() {
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

        personalSettingHeadLayout = (LinearLayout) findViewById(R.id.personalSettingHeadLayout);

        personalSettingHeadImage = (ImageView) findViewById(R.id.personalSettingHeadImage);

        editNickName = (EditText) findViewById(R.id.editNickName);
        editLocation = (EditText) findViewById(R.id.editLocation);
        editTrade = (EditText) findViewById(R.id.editTrade);
        editSign = (EditText) findViewById(R.id.editSign);
    }

}
