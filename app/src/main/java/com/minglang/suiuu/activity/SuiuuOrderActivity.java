package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.utils.DateTimePickDialogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/6/11 15:08
 * 修改人：Administrator
 * 修改时间：2015/6/11 15:08
 * 修改备注：
 */
public class SuiuuOrderActivity extends BaseActivity {
    private TextView tv_travel_time;
    private String initTime; // 初始化结束时间
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_order);
        initView();
        viewAction();
    }

    private void initView() {
        initTime = sdf.format(new Date());
        tv_travel_time = (TextView) findViewById(R.id.tv_travel_time);
        tv_travel_time.setText(initTime);
    }

    private void viewAction() {
        tv_travel_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        SuiuuOrderActivity.this, initTime);
                dateTimePicKDialog.dateTimePicKDialog(tv_travel_time);
            }
        });
    }
}
