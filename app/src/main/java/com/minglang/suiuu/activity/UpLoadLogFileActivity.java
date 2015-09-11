package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.SDCardUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

public class UpLoadLogFileActivity extends BaseAppCompatActivity {

    private static final String TAG = UpLoadLogFileActivity.class.getSimpleName();

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.up_load_lo_file_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.select_file_btn)
    Button selectFileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_log_file);
        ButterKnife.bind(this);
        initView();
        viewAction();
    }

    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);
    }

    private void viewAction() {
        selectFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = SDCardUtils.getExternalSdCardPath() + "/Suiuu";
                File logFolder = new File(path);
                File[] logFileArray = logFolder.listFiles();

                for (File logFile : logFileArray) {
                    DeBugLog.i(TAG, "logFile Path:" + logFile.getAbsolutePath());
                    DeBugLog.i(TAG, "logFile Name:" + logFile.getName());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
