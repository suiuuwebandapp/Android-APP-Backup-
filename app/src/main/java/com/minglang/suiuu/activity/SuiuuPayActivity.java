package com.minglang.suiuu.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.entity.GeneralOrderDetails;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.pingplusplus.android.PaymentActivity;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

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
    @BindString(R.string.NetworkAnomaly)
    String NetworkError;
    private static final int REQUEST_CODE_PAYMENT = 111;
    @Bind(R.id.iv_top_back)
    ImageView iv_top_back;
    @Bind(R.id.tv_top_center)
    TextView tv_top_center;
    @Bind(R.id.tv_top_right_more)
    ImageView tv_top_right_more;
    private String orderNumber;
    @Bind(R.id.tv_pay_wechat)
    TextView tv_pay_wechat;
    @Bind(R.id.tv_pay_alipay)
    TextView tv_pay_alipay;
    /**
     * 随游名字
     */
    @Bind(R.id.tv_suiuu_name)
    TextView tv_suiuu_name;
    /**
     * 随游订单创建时间
     */
    @Bind(R.id.tv_order_create_time)
    TextView tv_order_create_time;
    /**
     * 随游订单号
     */
    @Bind(R.id.tv_order_number)
    TextView tv_order_number;
    /**
     * 随游订单号
     */
    @Bind(R.id.tv_total_price)
    TextView tv_total_price;
    /**
     * 中间信息
     */
    @Bind(R.id.ll_center_message)
    LinearLayout ll_center_message;
    /**
     * 底部信息
     */
    @Bind(R.id.ll_bottom_message)
    LinearLayout ll_bottom_message;

    private static final String ORDER_NUMBER = "orderNumber";
    private TextProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_pay);
        ButterKnife.bind(this);
        orderNumber = this.getIntent().getStringExtra("orderNumber");
        initView();
        viewAction();
        getOrderDetail();
    }

    private void initView() {
        dialog = new TextProgressDialog(this);
        tv_top_right_more.setVisibility(View.GONE);
        tv_pay_alipay = (TextView) findViewById(R.id.tv_pay_alipay);
        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);
    }

    private void viewAction() {
        iv_top_back.setOnClickListener(new Myclick());
        tv_pay_alipay.setOnClickListener(new Myclick());
        tv_pay_wechat.setOnClickListener(new Myclick());
        tv_top_center.setVisibility(View.VISIBLE);
        tv_top_center.setText("支付");
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
                case R.id.tv_pay_alipay:
                    getCharge(orderNumber, "alipay");
                    break;
                case R.id.tv_pay_wechat:
                    getCharge(orderNumber, "wx");
                    break;
                default:
                    break;
            }
        }
    }

    //获取订单的接口
    private void getCharge(String orderNumber, String payWay) {
        Map<String, String> map = new HashMap<>();
        map.put("orderNumber", orderNumber);
        map.put("channel", payWay);
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.getCharge + "?token=" + SuiuuInfo.ReadAppTimeSign(SuiuuPayActivity.this), new getChargeCallBack(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取订单详情
     */
    private void getOrderDetail() {
        dialog.showDialog();
        String[] keyArray = new String[]{ORDER_NUMBER, HttpServicePath.key, TOKEN};
        String[] valueArray = new String[]{orderNumber, verification, token};
        String url = addUrlAndParams(HttpNewServicePath.getGeneralUserOrderDetailsPath, keyArray, valueArray);
        try {
            OkHttpManager.onGetAsynRequest(url, new OrderDetailsResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(SuiuuPayActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
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

    private class OrderDetailsResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            dialog.dismissDialog();
            fullData(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(SuiuuPayActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    private void fullData(String st) {
        GeneralOrderDetails details = JsonUtils.getInstance().fromJSON(GeneralOrderDetails.class, st);
        GeneralOrderDetails.GeneralOrderDetailsData.InfoEntity info = details.getData().getInfo();
        GeneralOrderDetails.GeneralOrderDetailsData.ContackEntity contact = details.getData().getContact();
        tv_suiuu_name.setText(contact.getDestination());
        tv_order_create_time.setText("创建时间:"+info.getCreateTime());
        tv_order_number.setText("订单号:"+info.getOrderNumber());
        tv_total_price.setText("总价:"+info.getTotalPrice());
        String[] centerMessage = {"目的地:" + contact.getDestination()};
        String[] bottomMessage = {"主要联系人:" + contact.getUsername(),"微信号:"+contact.getWechat(),"联系号码:"+contact.getPhone(),"备用联系号码:"+contact.getSparePhone(),"紧急联系人:"+contact.getUrgentUsername(),"紧急联系号码:"+contact.getUrgentPhone()};
        View itemView;
        TextView tv;
        for (int i = 0; i < centerMessage.length; i++) {
            itemView = LayoutInflater.from(this).inflate(R.layout.suiuu_pay_textview, null);
            tv = (TextView) itemView.findViewById(R.id.tv);
            tv.setText(centerMessage[i]);
            ll_center_message.addView(tv);
        }
        for (int i = 0; i < bottomMessage.length; i++) {
            itemView = LayoutInflater.from(this).inflate(R.layout.suiuu_pay_textview, null);
            tv = (TextView) itemView.findViewById(R.id.tv);
            tv.setText(bottomMessage[i]);
            ll_bottom_message.addView(tv);
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
                startActivity(new Intent(SuiuuPayActivity.this, GeneralOrderListActivity.class));
            }
        }
    }
}