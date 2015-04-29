package com.minglang.suiuu.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.SystemBarTintManager;
import com.minglang.suiuu.utils.Utils;

/**
 * 我的关注
 */
public class MyAttentionActivity extends Activity {

    private ImageView MyAttentionBack;

    private ListView MyAttentionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attention);

        initView();

        ViewAction();

    }

    private void ViewAction() {
        MyAttentionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {

        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        int statusHeight = Utils.newInstance(this).getStatusHeight();

        /**
         系统版本是否高于4.4
         */
        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKITKAT) {
            LinearLayout rootLayout = (LinearLayout) findViewById(R.id.myAttentionRootLayout);
            rootLayout.setPadding(0, statusHeight, 0, 0);
        }

        MyAttentionBack = (ImageView) findViewById(R.id.MyAttentionBack);
        MyAttentionList = (ListView) findViewById(R.id.MyAttentionList);
    }

}
