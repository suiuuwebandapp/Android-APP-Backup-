package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.text.Html;
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
    private String peopleNumber;
    private String time;
    private String total_price;
    private String destinnation;
    private TextView tv_suiuu_pay_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_pay);
        peopleNumber = this.getIntent().getStringExtra("peopleNumber");
        time = this.getIntent().getStringExtra("time");
        total_price = this.getIntent().getStringExtra("total_price");
        destinnation = this.getIntent().getStringExtra("destinnation");
        initView();
        viewAction();
    }

    private void initView() {
        iv_top_back = (ImageView) findViewById(R.id.iv_top_back);
        tv_top_center = (TextView) findViewById(R.id.tv_top_center);
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        tv_suiuu_pay_detail = (TextView) findViewById(R.id.tv_suiuu_pay_detail);
    }

    private void viewAction() {
        iv_top_back.setOnClickListener(new Myclick());
        tv_top_center.setVisibility(View.VISIBLE);
        tv_top_center.setText("支付");
        tv_top_right.setVisibility(View.INVISIBLE);
        tv_suiuu_pay_detail.setText(Html.fromHtml("<font color=#000000>你选择了随游</font> " +destinnation+"<font color=#000000>,并将在</font>"+time+"<font color=#000000>出行,人数为</font>"+peopleNumber+"<font color=#000000>人,选择单项服务</font>"+0+"<font color=#000000>个,总价为 ￥:</font>"+total_price));
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
