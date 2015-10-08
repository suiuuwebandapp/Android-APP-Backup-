package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.entity.GeneralOrderDetails;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
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
public class SuiuuPayActivity extends BaseAppCompatActivity {

    private static final String TAG = SuiuuPayActivity.class.getSimpleName();

    private static final String ORDER_NUMBER = "orderNumber";
    private static final String CHANNEL = "channel";

    private static final int REQUEST_CODE_PAYMENT = 111;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @Bind(R.id.suiuu_pay_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.tv_pay_wechat)
    TextView wechatPayButton;

    @Bind(R.id.tv_pay_alipay)
    TextView aliPayButton;

    /**
     * 随游名字
     */
    @Bind(R.id.suiuu_order_title)
    TextView suiuuOrderTitle;

    /**
     * 随游订单创建时间
     */
    @Bind(R.id.suiuu_order_create_time)
    TextView suiuuOrderCreateTime;

    /**
     * 随游订单号
     */
    @Bind(R.id.suiuu_order_number)
    TextView suiuuOrderNumber;

    /**
     * 订单总价
     */
    @Bind(R.id.suiuu_total_price)
    TextView suiuuOrderTotalPrice;

    /**
     * 中间信息
     */
    @Bind(R.id.ll_center_message)
    LinearLayout suiuuOrderCenterMessage;

    /**
     * 底部信息
     */
    @Bind(R.id.ll_bottom_message)
    LinearLayout suiuuOrderBottomMessage;

    private String orderNumber;

    private TextProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_pay);
        ButterKnife.bind(this);

        orderNumber = this.getIntent().getStringExtra(ORDER_NUMBER);

        initView();
        viewAction();
        getOrderDetail();
    }

    private void initView() {
        dialog = new TextProgressDialog(this);

        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        setSupportActionBar(toolbar);
    }

    private void viewAction() {
        aliPayButton.setOnClickListener(new PayClickListener());
        wechatPayButton.setOnClickListener(new PayClickListener());
    }

    class PayClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.tv_pay_alipay:
                    getCharge(orderNumber, "alipay");
                    break;

                case R.id.tv_pay_wechat:
                    getCharge(orderNumber, "wx");
                    break;
            }
        }

    }

    //获取订单的接口
    private void getCharge(String orderNumber, String payWay) {
        Map<String, String> map = new HashMap<>();
        map.put(ORDER_NUMBER, orderNumber);
        map.put(CHANNEL, payWay);
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.getCharge + "?" + TOKEN + "=" + token, new getChargeCallBack(), map);
        } catch (IOException e) {
            L.e(TAG, "获取订单网络请求异常:" + e.getMessage());
        }
    }

    /**
     * 获取订单详情
     */
    private void getOrderDetail() {
        dialog.show();
        String[] keyArray = new String[]{ORDER_NUMBER, HttpNewServicePath.key, TOKEN};
        String[] valueArray = new String[]{orderNumber, verification, token};
        String url = addUrlAndParams(HttpNewServicePath.getGeneralUserOrderDetailsPath, keyArray, valueArray);
        try {
            OkHttpManager.onGetAsynRequest(url, new OrderDetailsResultCallback());
        } catch (IOException e) {
            L.e(TAG, "获取订单详情网络请求异常:" + e.getMessage());
            dialog.dismiss();
            Toast.makeText(SuiuuPayActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获得charge对象接口
     */
    class getChargeCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "获得charge网络请求错误:" + e.getMessage());
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

    /**
     * 订单详情回调
     */
    private class OrderDetailsResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            fullData(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "获得订单详情网络请求错误:" + e.getMessage());
            Toast.makeText(SuiuuPayActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            dialog.dismiss();
        }

    }

    @SuppressLint("InflateParams")
    private void fullData(String st) {

        GeneralOrderDetails details = JsonUtils.getInstance().fromJSON(GeneralOrderDetails.class, st);
        GeneralOrderDetails.GeneralOrderDetailsData.InfoEntity info = details.getData().getInfo();
        GeneralOrderDetails.GeneralOrderDetailsData.ContactEntity contact = details.getData().getContact();

        suiuuOrderTitle.setText(contact.getDestination());
        suiuuOrderCreateTime.setText(String.format("%s%s", "创建时间:", info.getCreateTime()));
        suiuuOrderNumber.setText(String.format("%s%s", "订单号:", info.getOrderNumber()));
        suiuuOrderTotalPrice.setText(String.format("%s%s", "总价:", info.getTotalPrice()));

        String[] centerMessage = {"目的地:" + contact.getDestination()};
        String[] bottomMessage = {"主要联系人:" + contact.getUsername(), "微信号:" + contact.getWechat(),
                "联系号码:" + contact.getPhone(), "备用联系号码:" + contact.getSparePhone(),
                "紧急联系人:" + contact.getUrgentUsername(), "紧急联系号码:" + contact.getUrgentPhone()};

        View itemView;
        TextView tv;

        for (String aCenterMessage : centerMessage) {
            itemView = LayoutInflater.from(this).inflate(R.layout.suiuu_pay_textview, null);
            tv = (TextView) itemView.findViewById(R.id.tv);
            tv.setText(aCenterMessage);
            suiuuOrderCenterMessage.addView(tv);
        }

        for (String aBottomMessage : bottomMessage) {
            itemView = LayoutInflater.from(this).inflate(R.layout.suiuu_pay_textview, null);
            tv = (TextView) itemView.findViewById(R.id.tv);
            tv.setText(aBottomMessage);
            suiuuOrderBottomMessage.addView(tv);
        }

    }

    /**
     * 处理返回值
     * "success" - payment succeed
     * "fail"    - payment failed
     * "cancel"  - user canceld
     * "invalid" - payment plugin not installed
     * 如果是银联渠道返回 invalid，调用 UPPayAssistEx.installUPPayPlugin(this); 安装银联安全支付控件。
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {

                String result = data.getExtras().getString("pay_result");

                if (!TextUtils.isEmpty(result)) {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    switch (result) {
                        case "success":
                            startActivity(new Intent(SuiuuPayActivity.this, GeneralOrderListActivity.class));
                            finish();
                            break;
                    }
                }

                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                L.e(TAG, "回调错误信息:" + errorMsg);

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "User canceled", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                startActivity(new Intent(SuiuuPayActivity.this, GeneralOrderListActivity.class));
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(SuiuuPayActivity.this, GeneralOrderListActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}