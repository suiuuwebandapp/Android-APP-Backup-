package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/6/12 15:46
 * 修改人：Administrator
 * 修改时间：2015/6/12 15:46
 * 修改备注：
 */
public class SuiuuPayActivity extends BaseActivity {
    private ImageView iv_top_back;
    private TextView tv_top_center;
    private TextView tv_top_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_pay);
        initView();
        viewAction();
    }

    private void initView() {
        iv_top_back = (ImageView) findViewById(R.id.iv_top_back);
        tv_top_center = (TextView) findViewById(R.id.tv_top_center);
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
    }

    private void viewAction() {
        iv_top_back.setOnClickListener(new Myclick());
        tv_top_center.setVisibility(View.VISIBLE);
        tv_top_center.setText("支付");
        tv_top_right.setVisibility(View.INVISIBLE);

    }
    class Myclick implements View.OnClickListener {

        /**
         * @param v
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_top_back:
                    finish();
                    break;
                default:
                    break;
            }
        }
    }
 }
