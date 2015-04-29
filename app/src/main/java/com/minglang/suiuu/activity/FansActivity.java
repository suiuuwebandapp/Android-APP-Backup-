package com.minglang.suiuu.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.SystemBarTintManager;

/**
 * 粉丝页面
 */
public class FansActivity extends Activity {

    private ImageView fansBack;

    private ListView fansList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans);

        initView();

        ViewAction();

    }

    private void ViewAction() {
        fansBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fansList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    /**
     * 初始化
     */
    private void initView() {

        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        int statusHeight = mTintManager.getConfig().getStatusBarHeight();

        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKITKAT) {
            RelativeLayout fansRootLayout = (RelativeLayout) findViewById(R.id.fansRootLayout);
            fansRootLayout.setPadding(0, statusHeight, 0, 0);
        }

        fansBack = (ImageView) findViewById(R.id.fansBack);
        fansList = (ListView) findViewById(R.id.fansList);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
