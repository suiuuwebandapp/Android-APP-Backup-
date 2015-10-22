package com.minglang.suiuu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.utils.DateTimePickDialogUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.StatusBarCompat;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：随游订单确认页面
 * 创建人：Administrator
 * 创建时间：2015/6/11 15:08
 * 修改人：Administrator
 * 修改时间：2015/6/11 15:08
 * 修改备注：
 */
public class SuiuuOrderConfirmActivity extends BaseActivity {

    private static final String TAG = SuiuuOrderConfirmActivity.class.getSimpleName();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);

    @Bind(R.id.select_travel_date)
    TextView travelDate;

    @Bind(R.id.select_travel_time)
    TextView travelTime;

    private String initTime; // 初始化结束时间

    @Bind(R.id.add_personal_number)
    ImageView iv_plus;

    @Bind(R.id.less_personal_number)
    ImageView iv_release;

    @Bind(R.id.tv_enjoy_number)
    TextView tv_enjoy_number;

    private String titleInfo;
    private String titleImg;

    @Bind(R.id.suiuu_order_title_image)
    SimpleDraweeView iv_suiuu_order_titlePic;

    @Bind(R.id.suiuu_order_title)
    TextView tv_suiuu_order_title;

    @Bind(R.id.suiuu_order_back)
    ImageView suiuu_order_back;

    @Bind(R.id.suiuu_order_service_list_layout)
    LinearLayout serviceLayout;

    @Bind(R.id.bb_suiuu_order_pay)
    BootstrapButton bb_suiuu_order_pay;

    private Float price;

    @Bind(R.id.tv_order_price)
    TextView tv_order_price;

    @Bind(R.id.suiuu_item_service)
    TextView suiuuItemService;

    private String tripId;

    private int enjoy_peopleNumber;

    private String[] serviceNameArray;
    private String[] serviceIdArray;
    private String[] servicePriceArray;

    private int serviceListSize;

    private float AllServicePrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_order);

        StatusBarCompat.compat(this);
        ButterKnife.bind(this);

        titleInfo = this.getIntent().getStringExtra("titleInfo");
        titleImg = this.getIntent().getStringExtra("titleImg");
        tripId = this.getIntent().getStringExtra("tripId");
        price = Float.valueOf(this.getIntent().getStringExtra("price"));
        serviceNameArray = getIntent().getStringArrayExtra("serviceName");
        serviceIdArray = getIntent().getStringArrayExtra("serviceId");
        servicePriceArray = getIntent().getStringArrayExtra("servicePrice");
        serviceListSize = getIntent().getIntExtra("serviceListSize", 0);

        initView();
        viewAction();
    }

    private void initView() {
        token = SuiuuInfo.ReadAppTimeSign(SuiuuOrderConfirmActivity.this);

        initTime = sdf.format(new Date());
        String[] split = initTime.split(" ");

        travelDate.setText(split[0].replace("年", "-").replace("月", "-").replace("日", ""));
        travelTime.setText(split[1]);

        if (serviceListSize > 0) {
            for (int i = 0; i < serviceListSize; i++) {
                View serviceView = LayoutInflater.from(this).inflate(R.layout.item_suiuu_service, serviceLayout, false);
                TextView nameView = (TextView) serviceView.findViewById(R.id.suiuu_service_name);
                TextView priceView = (TextView) serviceView.findViewById(R.id.suiuu_service_price);

                float itemServicePrice = Float.valueOf(servicePriceArray[i]);
                AllServicePrice = AllServicePrice + itemServicePrice;
                nameView.setText(serviceNameArray[i]);
                priceView.setText(String.format("%s%s", "￥", itemServicePrice));

                serviceLayout.addView(serviceView);
            }
        } else {
            suiuuItemService.setVisibility(View.GONE);
        }
    }

    private void viewAction() {
        Uri uri = Uri.parse(titleImg);
        iv_suiuu_order_titlePic.setImageURI(uri);
        tv_suiuu_order_title.setText(titleInfo);

        travelDate.setOnClickListener(new MyClick());
        travelTime.setOnClickListener(new MyClick());
        iv_release.setOnClickListener(new MyClick());
        iv_plus.setOnClickListener(new MyClick());

        suiuu_order_back.setOnClickListener(new MyClick());
        bb_suiuu_order_pay.setOnClickListener(new MyClick());

        float AllPrice = AllServicePrice + price;

        tv_order_price.setText(String.format("%s%s", "总价: ", Float.toString(AllPrice)));
    }

    //获取订单的接口
    private void createOrderNumber() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String myString = travelDate.getText().toString() + " " + travelTime.getText().toString();

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
        }

        String ids = "";
        if (serviceIdArray != null && serviceIdArray.length > 0) {
            for (int i = 0; i < serviceIdArray.length; i++) {
                if (i == serviceIdArray.length - 1) {
                    ids = ids + serviceIdArray[i];
                } else {
                    ids = ids + serviceIdArray[i] + ",";
                }
            }
        }

        L.i(TAG, "单项服务ID:" + ids);

        Map<String, String> map = new HashMap<>();
        map.put("tripId", tripId);
        map.put("peopleCount", Integer.toString(enjoy_peopleNumber));
        map.put("beginDate", travelDate.getText().toString());
        map.put("startTime", travelTime.getText().toString());
        map.put("type", "2");
        map.put("serviceIds", ids);

        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.createOrderNumber + "?token=" + token, new createOrderCallBack(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成订单回调接口
     */
    class createOrderCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(SuiuuOrderConfirmActivity.this, "访问失败，请稍候再试！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            try {
                JSONObject message = new JSONObject(response);
                String status = message.getString("status");
                String orderNumber;
                if ("1".equals(status)) {
                    orderNumber = message.getString("data");
                    Intent intent = new Intent(SuiuuOrderConfirmActivity.this, OrderContactInformationActivity.class);
                    intent.putExtra("peopleNumber", Integer.toString(enjoy_peopleNumber));
                    intent.putExtra("time", travelDate.getText());
                    intent.putExtra("total_price", tv_order_price.getText());
                    intent.putExtra("destination", titleInfo);
                    intent.putExtra("orderNumber", orderNumber);
                    startActivity(intent);
                    finish();
                } else if ("-2".equals(status)) {
                    orderNumber = message.getString("data");
                    Toast.makeText(SuiuuOrderConfirmActivity.this, orderNumber, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(SuiuuOrderConfirmActivity.this, "点赞失败，请稍候再试", Toast.LENGTH_SHORT).show();
            }
        }

    }

    class MyClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            enjoy_peopleNumber = Integer.valueOf(String.valueOf(tv_enjoy_number.getText()));
            switch (v.getId()) {
                case R.id.select_travel_date:
                    DateTimePickDialogUtils dateTimePicKDialog = new DateTimePickDialogUtils(
                            SuiuuOrderConfirmActivity.this, initTime, null);
                    dateTimePicKDialog.dateTimePicKDialog(1, travelDate);
                    break;

                case R.id.select_travel_time:
                    DateTimePickDialogUtils dateTimePicKDialog1 = new DateTimePickDialogUtils(
                            SuiuuOrderConfirmActivity.this, initTime, travelDate.getText().toString());
                    dateTimePicKDialog1.dateTimePicKDialog(2, travelTime);
                    break;
                case R.id.less_personal_number:
                    if (enjoy_peopleNumber != 1) {
                        tv_enjoy_number.setText(String.valueOf(enjoy_peopleNumber - 1));
                        tv_order_price.setText(String.format("%s%s", "总价: ", Float.toString(price * (enjoy_peopleNumber - 1))));
                    }
                    break;

                case R.id.add_personal_number:
                    tv_enjoy_number.setText(String.valueOf(enjoy_peopleNumber + 1));
                    tv_order_price.setText(String.format("%s%s", "总价: ", Float.toString(price * (enjoy_peopleNumber + 1))));
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