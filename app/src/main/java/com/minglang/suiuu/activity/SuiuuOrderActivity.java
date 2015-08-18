package com.minglang.suiuu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.utils.DateTimePickDialogUtils;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.SuHttpRequest;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    private TextView tv_travel_date;
    private TextView tv_travel_time;
    private String initTime; // 初始化结束时间
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm",Locale.getDefault());
    private ImageView iv_release;
    private TextView tv_enjoy_number;
    private ImageView iv_plus;
    private String titleInfo;
    private String titleImg;
    private SimpleDraweeView iv_suiuu_order_titlePic;
    private TextView tv_suiuu_order_title;
    private ImageView suiuu_order_back;
    private BootstrapButton bb_suiuu_order_pay;
    private Float price;
    private TextView tv_order_price;
    private String tripId;

    private int enjoy_peopleNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_order);
        titleInfo = this.getIntent().getStringExtra("titleInfo");
        titleImg = this.getIntent().getStringExtra("titleImg");
        tripId = this.getIntent().getStringExtra("tripId");
        price = Float.valueOf(this.getIntent().getStringExtra("price"));
        initView();
        viewAction();
    }

    private void initView() {
        initTime = sdf.format(new Date());
        String[] split = initTime.split(" ");
        tv_travel_date = (TextView) findViewById(R.id.tv_travel_date);
        tv_travel_time = (TextView) findViewById(R.id.tv_travel_time);
        tv_travel_date.setText(split[0].replace("年", "-").replace("月", "-").replace("日", ""));
        tv_travel_time.setText(split[1]);
        iv_release = (ImageView) findViewById(R.id.iv_release);
        tv_enjoy_number = (TextView) findViewById(R.id.tv_enjoy_number);
        iv_plus = (ImageView) findViewById(R.id.iv_plus);
        iv_suiuu_order_titlePic = (SimpleDraweeView) findViewById(R.id.iv_suiuu_order_titlePic);
        tv_suiuu_order_title = (TextView) findViewById(R.id.tv_suiuu_order_title);
        suiuu_order_back = (ImageView) findViewById(R.id.suiuu_order_back);
        bb_suiuu_order_pay = (BootstrapButton) findViewById(R.id.bb_suiuu_order_pay);
        tv_order_price = (TextView) findViewById(R.id.tv_order_price);

    }

    private void viewAction() {
        Uri uri = Uri.parse(titleImg);
        iv_suiuu_order_titlePic.setImageURI(uri);
        tv_suiuu_order_title.setText(titleInfo);
        tv_travel_date.setOnClickListener(new MyClick());
        tv_travel_time.setOnClickListener(new MyClick());
        iv_release.setOnClickListener(new MyClick());
        iv_plus.setOnClickListener(new MyClick());
        suiuu_order_back.setOnClickListener(new MyClick());
        bb_suiuu_order_pay.setOnClickListener(new MyClick());
        tv_order_price.setText("总价: " + Float.toString(price));
    }

    //获取订单的接口
    private void createOrderNumber() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String myString = tv_travel_date.getText().toString() + " " + tv_travel_time.getText().toString();

        Date d = null;

        try {
            d = sdf1.parse(myString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean flag = false;

        if (d != null) {
            flag = d.before(new Date());
        }

        if (flag) {
            Toast.makeText(this, "请选择出行日期", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(this, "晚于今天", Toast.LENGTH_SHORT).show();
        }

        RequestParams params = new RequestParams();
        params.addBodyParameter("tripId", tripId);
        params.addBodyParameter("peopleCount", Integer.toString(enjoy_peopleNumber));
        params.addBodyParameter("beginDate", tv_travel_date.getText().toString());
        params.addBodyParameter("startTime", tv_travel_time.getText().toString());
        params.addBodyParameter("type", "2");
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.createOrderNumber, new createOrderCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
    }


    /**
     * 生成订单回调接口
     */
    class createOrderCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            Log.i("suiuu", "order=" + stringResponseInfo.result);
            try {
                JSONObject message = new JSONObject(stringResponseInfo.result);

                String status = message.getString("status");
                String orderNumber;
                if ("1".equals(status)) {
                    orderNumber = message.getString("data");
                    Intent intent = new Intent(SuiuuOrderActivity.this, SuiuuPayActivity.class);
                    intent.putExtra("peopleNumber", Integer.toString(enjoy_peopleNumber));
                    intent.putExtra("time", tv_travel_date.getText());
                    intent.putExtra("total_price", tv_order_price.getText());
                    intent.putExtra("destinnation", titleInfo);
                    intent.putExtra("orderNumber", orderNumber);
                    startActivity(intent);
                    Log.i("suiuu", "orderNumber" + orderNumber);
                } else if ("-2".equals(status)) {
                    orderNumber = message.getString("data");
                    Toast.makeText(SuiuuOrderActivity.this, orderNumber, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(SuiuuOrderActivity.this, "点赞失败，请稍候再试", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Toast.makeText(SuiuuOrderActivity.this, "访问失败，请稍候再试！" + s, Toast.LENGTH_SHORT).show();

        }
    }


    class MyClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            enjoy_peopleNumber = Integer.valueOf(String.valueOf(tv_enjoy_number.getText()));
            switch (v.getId()) {
                case R.id.tv_travel_date:
                    DateTimePickDialogUtils dateTimePicKDialog = new DateTimePickDialogUtils(
                            SuiuuOrderActivity.this, initTime, null);
                    dateTimePicKDialog.dateTimePicKDialog(1, tv_travel_date);
                    break;
                case R.id.tv_travel_time:
                    DateTimePickDialogUtils dateTimePicKDialog1 = new DateTimePickDialogUtils(
                            SuiuuOrderActivity.this, initTime, tv_travel_date.getText().toString());
                    dateTimePicKDialog1.dateTimePicKDialog(2, tv_travel_time);
                    break;
                case R.id.iv_release:
                    if (enjoy_peopleNumber != 1) {
                        tv_enjoy_number.setText(String.valueOf(enjoy_peopleNumber - 1));
                        tv_order_price.setText("总价: " + Float.toString(price * (enjoy_peopleNumber - 1)));

                    }
                    break;
                case R.id.iv_plus:
                    tv_enjoy_number.setText(String.valueOf(enjoy_peopleNumber + 1));
                    tv_order_price.setText("总价: " + Float.toString(price * (enjoy_peopleNumber + 1)));

                    break;
                case R.id.suiuu_order_back:
                    finish();
                    break;
                case R.id.bb_suiuu_order_pay:
                    createOrderNumber();


                    break;
                default:
                    break;
            }
        }
    }


}
