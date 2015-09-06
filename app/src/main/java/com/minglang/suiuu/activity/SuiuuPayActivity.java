package com.minglang.suiuu.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.pingplusplus.android.PaymentActivity;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    private static final int REQUEST_CODE_PAYMENT = 111;
    private ImageView iv_top_back;
    private TextView tv_top_center;
    private TextView tv_top_right;
    private String peopleNumber;
    private String time;
    private String total_price;
    private String destination;
    private String orderNumber;
    private TextView tv_suiuu_pay_detail;
    private ImageView iv_pay_wechat;
    private ImageView iv_pay_alipay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_pay);
        peopleNumber = this.getIntent().getStringExtra("peopleNumber");
        time = this.getIntent().getStringExtra("time");
        total_price = this.getIntent().getStringExtra("total_price");
        destination = this.getIntent().getStringExtra("destination");
        orderNumber = this.getIntent().getStringExtra("orderNumber");
        initView();
        viewAction();
    }

    private void initView() {
        iv_top_back = (ImageView) findViewById(R.id.iv_top_back);
        tv_top_center = (TextView) findViewById(R.id.tv_top_center);
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        tv_suiuu_pay_detail = (TextView) findViewById(R.id.tv_suiuu_pay_detail);
        iv_pay_wechat = (ImageView) findViewById(R.id.iv_pay_wechat);
        iv_pay_alipay = (ImageView) findViewById(R.id.iv_pay_alipay);
    }

    private void viewAction() {
        iv_top_back.setOnClickListener(new Myclick());
        iv_pay_alipay.setOnClickListener(new Myclick());
        iv_pay_wechat.setOnClickListener(new Myclick());
        tv_top_center.setVisibility(View.VISIBLE);
        tv_top_center.setText("支付");
        tv_top_right.setVisibility(View.INVISIBLE);
        tv_suiuu_pay_detail.setText(Html.fromHtml("<font color=#000000>你选择了随游</font> " + destination + "<font color=#000000>,并将在</font>" + time + "<font color=#000000>出行,人数为</font>" + peopleNumber + "<font color=#000000>人,选择单项服务</font>" + 0 + "<font color=#000000>个,总价为 ￥:</font>" + total_price));
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
                case R.id.iv_pay_alipay:
                    getCharge(orderNumber, "alipay");
                    break;
                case R.id.iv_pay_wechat:
                    getCharge(orderNumber, "wx");
                    break;
                default:
                    break;
            }
        }
    }

    //获取订单的接口
    private void getCharge(String orderNumber, String payWay) {
        Map<String,String> map = new HashMap<>();
        map.put("orderNumber", orderNumber);
        map.put("channel", payWay);
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.getCharge + "?token=" + SuiuuInfo.ReadAppTimeSign(SuiuuPayActivity.this), new getChargeCallBack(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得charge对象接口
     */
    class getChargeCallBack extends OkHttpManager.ResultCallback<String> {
        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(SuiuuPayActivity.this, "请稍候再试！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            try {

                Intent intent = new Intent();
                String packageName = getPackageName();
                ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
                intent.setComponent(componentName);
                intent.putExtra(PaymentActivity.EXTRA_CHARGE, response);
                startActivityForResult(intent, REQUEST_CODE_PAYMENT);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(SuiuuPayActivity.this, "请稍候再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - payment succeed
             * "fail"    - payment failed
             * "cancel"  - user canceld
             * "invalid" - payment plugin not installed
             * 如果是银联渠道返回 invalid，调用 UPPayAssistEx.installUPPayPlugin(this); 安装银联安全支付控件。
             */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "User canceled", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                startActivity(new Intent(SuiuuPayActivity.this,GeneralOrderListActivity.class));
            }
        }
    }
}