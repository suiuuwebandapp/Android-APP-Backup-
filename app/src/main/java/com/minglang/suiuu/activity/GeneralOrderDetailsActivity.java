package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.reflect.TypeToken;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.GeneralOrderDetails;
import com.minglang.suiuu.entity.GeneralOrderDetails.GeneralOrderDetailsData;
import com.minglang.suiuu.entity.GeneralOrderDetails.GeneralOrderDetailsData.InfoEntity;
import com.minglang.suiuu.entity.GeneralOrderDetails.GeneralOrderDetailsData.PublisherBaseEntity;
import com.minglang.suiuu.entity.ServiceInfo;
import com.minglang.suiuu.entity.TripJsonInfo;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 订单详情页面-普通用户
 */
public class GeneralOrderDetailsActivity extends BaseAppCompatActivity {

    private static final String TAG = GeneralOrderDetailsActivity.class.getSimpleName();

    private static final String ORDER_NUMBER = "orderNumber";
    private static final String TITLE_IMG = "titleImg";

    private String PEOPLE_NUMBER = "peopleNumber";
    private String TIME = "time";
    private String TOTAL_PRICE = "total_price";
    private String DESTINATION = "destination";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private static final float DEFAULT_SCORE = 0f;

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.deleteOrder)
    String DeleteOrder;

//    @Bind(R.id.general_order_details_head_frame)
//    PtrClassicFrameLayout mPtrFrame;

    @Bind(R.id.general_order_details_parent_view)
    ScrollView scrollView;

    @Bind(R.id.order_details_title_image_view)
    SimpleDraweeView titleImageView;

    /**
     * 订单标题
     */
    @Bind(R.id.order_details_title)
    TextView orderDetailsTitle;

    /**
     * 订单基础价格
     */
    @Bind(R.id.order_details_base_price)
    TextView baseOrderPriceView;

    /**
     * 订单星级评价指示器
     */
    @Bind(R.id.order_details_indicator)
    RatingBar orderStarIndicator;

    /**
     * 头像
     */
    @Bind(R.id.order_details_head_image_view)
    SimpleDraweeView headImageView;

    /**
     * 用户名
     */
    @Bind(R.id.order_details_name)
    TextView userName;

    /**
     * 订单状态
     */
    @Bind(R.id.order_details_status)
    TextView orderStatus;

    /**
     * 联系电话
     */
    @Bind(R.id.order_details_phone)
    TextView orderDetailsPhone;

    /**
     * 出发日期
     */
    @Bind(R.id.order_details_date)
    TextView orderDetailsDate;

    /**
     * 开始时间
     */
    @Bind(R.id.order_details_time)
    TextView orderDetailsTime;

    /**
     * 最大随游人数
     */
    @Bind(R.id.order_details_suiuu_number)
    TextView orderDetailsSuiuuNumber;

    /**
     * 附加服务Layout
     */
    @Bind(R.id.order_details_service_layout)
    LinearLayout serviceLayout;

    @Bind(R.id.order_details_additional_service_layout)
    LinearLayout serviceTitleLayout;

    /**
     * 附加服务数量
     */
    @Bind(R.id.order_details_additional_service_prices)
    TextView orderDetailsService;

    @Bind(R.id.order_details_back)
    ImageView orderDetailsBack;

    /**
     * 底部左边按钮
     */
    @Bind(R.id.order_details_repay)
    Button orderDetailsRepay;

    /**
     * 底部左边按钮
     */
    @Bind(R.id.order_details_cancel)
    Button orderDetailsCancel;

    /**
     * 底部按钮布局
     */
    @Bind(R.id.order_details_bottom_layout)
    CardView orderDetailsBottomLayout;

    private boolean isPullToRefresh = true;

    private String orderNumber;

    private ProgressDialog progressDialog;

    private LayoutInflater inflater;

    /**
     * 订单基础价格
     */
    private float baseOrderPrice = 0f;

    /**
     * 订单总价
     */
    private float totalOrderPrice = 0f;

    private InfoEntity infoEntity;
    private TripJsonInfo tripJsonInfo;

    /**
     * 订单状态
     */
    private String status;

    private String failureImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_order_details);
        ButterKnife.bind(this);
        initView();
        viewAction();
        getGeneralUserOrderData4Service();
    }

    private void initView() {
        orderNumber = getIntent().getStringExtra(ORDER_NUMBER);
        String titleImg = getIntent().getStringExtra(TITLE_IMG);

        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        failureImagePath = "res://com.minglang.suiuu/" + R.drawable.default_head_image_error;

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        inflater = LayoutInflater.from(this);

        if (!TextUtils.isEmpty(titleImg)) {
            titleImageView.setImageURI(Uri.parse(titleImg));
        }

    }

    private void viewAction() {

        orderDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //重新支付按钮
        orderDetailsRepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status) {
                    case "0":
                        Intent intent = new Intent(GeneralOrderDetailsActivity.this, SuiuuPayActivity.class);
                        intent.putExtra(PEOPLE_NUMBER, infoEntity.getPersonCount());
                        intent.putExtra(TIME, infoEntity.getBeginDate());
                        intent.putExtra(TOTAL_PRICE, infoEntity.getTotalPrice());
                        intent.putExtra(DESTINATION, tripJsonInfo.getInfo().getTitle());
                        intent.putExtra(ORDER_NUMBER, orderNumber);
                        startActivity(intent);
                        finish();
                        break;
                    case "3":
                        deleteOrder();
                        break;
                    case "5":
                        deleteOrder();
                        break;
                }


            }
        });

        //取消订单
        orderDetailsCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder();
            }
        });

    }

    private void cancelOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", infoEntity.getOrderId());
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.userCancelOrder + "?token=" + token, new CancelOrderCallBack(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", infoEntity.getOrderId());
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.userDeleteOrder + "?token=" + token, new CancelOrderCallBack(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getGeneralUserOrderData4Service() {
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        String[] keyArray = new String[]{ORDER_NUMBER, HttpNewServicePath.key, TOKEN};
        String[] valueArray = new String[]{orderNumber, verification, token};
        String url = addUrlAndParams(HttpNewServicePath.getGeneralUserOrderDetailsPath, keyArray, valueArray);
        L.i(TAG, "订单数据请求URL:" + url);
        try {
            OkHttpManager.onGetAsynRequest(url, new OrderDetailsResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(GeneralOrderDetailsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(this, NoData, Toast.LENGTH_SHORT).show();
        } else {
            JsonUtils jsonUtils = JsonUtils.getInstance();

            if (serviceLayout.getChildCount() > 0) {
                serviceLayout.removeAllViews();
                serviceLayout.addView(serviceTitleLayout);
            }

            try {
                GeneralOrderDetails details = jsonUtils.fromJSON(GeneralOrderDetails.class, str);
                if (details != null) {
                    GeneralOrderDetailsData detailsData = details.getData();

                    if (detailsData != null) {
                        PublisherBaseEntity publisherBaseEntity = detailsData.getPublisherBase();
                        infoEntity = detailsData.getInfo();

                        if (infoEntity != null) {
                            String jsonInfo = infoEntity.getTripJsonInfo();

                            if (!TextUtils.isEmpty(jsonInfo)) {
                                try {
                                    tripJsonInfo = jsonUtils.fromJSON(TripJsonInfo.class, jsonInfo);
                                    if (tripJsonInfo != null) {

                                        //标题
                                        String title = tripJsonInfo.getInfo().getTitle();
                                        if (!TextUtils.isEmpty(title)) {
                                            orderDetailsTitle.setText(title);
                                        } else {
                                            orderDetailsTitle.setText("");
                                        }

                                        //星级评价
                                        String strOrderScore = tripJsonInfo.getInfo().getScore();
                                        if (!TextUtils.isEmpty(strOrderScore)) {
                                            float score = Float.valueOf(strOrderScore);
                                            orderStarIndicator.setRating(score);
                                        } else {
                                            orderStarIndicator.setRating(DEFAULT_SCORE);
                                        }

                                    }
                                } catch (Exception exception) {
                                    L.e(TAG, "旅图数据解析失败:" + exception.getMessage());
                                }
                            } else {
                                orderDetailsTitle.setText("");
                                orderStarIndicator.setRating(DEFAULT_SCORE);
                            }

                            //订单基础金额
                            String basePrice = infoEntity.getBasePrice();
                            if (!TextUtils.isEmpty(basePrice)) {
                                baseOrderPrice = Float.valueOf(basePrice);
                                baseOrderPriceView.setText(basePrice);
                            } else {
                                baseOrderPriceView.setText("");
                            }

                            //开始日期
                            String beginDate = infoEntity.getBeginDate();
                            if (!TextUtils.isEmpty(beginDate)) {
                                orderDetailsDate.setText(beginDate);
                            } else {
                                orderDetailsDate.setText("");
                            }

                            //开始时间
                            String startTime = infoEntity.getStartTime();
                            if (!TextUtils.isEmpty(startTime)) {
                                orderDetailsTime.setText(startTime);
                            } else {
                                orderDetailsTime.setText("");
                            }

                            //随游人数
                            String personCount = infoEntity.getPersonCount();
                            if (!TextUtils.isEmpty(personCount)) {
                                orderDetailsSuiuuNumber.setText(String.format("%s%s", personCount, "人"));
                            } else {
                                orderDetailsSuiuuNumber.setText("0人");
                            }

                            //附加服务列表
                            String strServiceInfo = infoEntity.getServiceInfo();
                            if (!TextUtils.isEmpty(strServiceInfo)) {

                                if (!strServiceInfo.equals("[]")) {
                                    try {
                                        List<ServiceInfo> serviceInfoList = jsonUtils.fromJSON(new TypeToken<List<ServiceInfo>>() {
                                        }.getType(), strServiceInfo);

                                        if (serviceInfoList != null && serviceInfoList.size() > 0) {
                                            orderDetailsService.setText(String.valueOf(serviceInfoList.size()));

                                            //所有附加服务的总价
                                            float allServicePrice = 0f;

                                            for (int i = 0; i < serviceInfoList.size(); i++) {
                                                View serviceItemLayout = inflater.inflate
                                                        (R.layout.item_service_location_layout, serviceLayout, false);
                                                TextView serviceNameView = (TextView) serviceItemLayout
                                                        .findViewById(R.id.order_details_service_name);
                                                TextView servicePriceView = (TextView) serviceItemLayout
                                                        .findViewById(R.id.order_details_service_prices);

                                                //单项附加服务名称
                                                String serviceItemTitle = serviceInfoList.get(i).getTitle();
                                                if (!TextUtils.isEmpty(serviceItemTitle)) {
                                                    serviceNameView.setText(serviceItemTitle);
                                                }

                                                //单项附加服务价格
                                                String strServiceItemPrice = serviceInfoList.get(i).getMoney();
                                                float serviceItemPrice = 0f;
                                                if (!TextUtils.isEmpty(strServiceItemPrice)) {
                                                    serviceItemPrice = Float.valueOf(strServiceItemPrice);
                                                    servicePriceView.setText(strServiceItemPrice);
                                                }

                                                allServicePrice = allServicePrice + serviceItemPrice;
                                                serviceLayout.addView(serviceItemLayout);
                                            }

                                            totalOrderPrice = baseOrderPrice + allServicePrice;

                                        } else {
                                            totalOrderPrice = baseOrderPrice;
                                            orderDetailsService.setText("0");
                                        }
                                    } catch (Exception ex) {
                                        L.e(TAG, "附加服务数据解析失败:" + ex.getMessage());
                                    }
                                } else {
                                    totalOrderPrice = baseOrderPrice;
                                    orderDetailsService.setText("0");
                                }

                            }

                            //订单总价
                            TextView totalOrderPriceView = (TextView) inflater
                                    .inflate(R.layout.item_service_location_layout2, serviceLayout, false);
                            totalOrderPriceView.setText(String.format("%s%s", "总价:", totalOrderPrice));

                            serviceLayout.addView(totalOrderPriceView);

                            //订单状态
                            status = infoEntity.getStatus();
                            if (!TextUtils.isEmpty(status)) {
                                switch (status) {
                                    case "0":
                                        orderStatus.setText("待支付");
                                        orderDetailsBottomLayout.setVisibility(View.VISIBLE);
                                        break;

                                    case "1":
                                        orderStatus.setText("已支付 待确认");
                                        break;

                                    case "2":
                                        orderStatus.setText("已支付 已确认");
                                        break;

                                    case "3":
                                        orderDetailsBottomLayout.setVisibility(View.VISIBLE);
                                        orderDetailsCancel.setVisibility(View.GONE);
                                        orderDetailsRepay.setText(DeleteOrder);
                                        orderStatus.setText("未支付 已取消");
                                        break;

                                    case "4":
                                        orderStatus.setText("待退款");
                                        break;

                                    case "5":
                                        orderDetailsBottomLayout.setVisibility(View.VISIBLE);
                                        orderDetailsCancel.setVisibility(View.GONE);
                                        orderDetailsCancel.setText(DeleteOrder);
                                        orderStatus.setText("退款成功");
                                        break;

                                    case "6":
                                        orderStatus.setText("游玩结束 待付款给随友");
                                        break;

                                    case "7":
                                        orderStatus.setText("结束，已经付款给随友");
                                        break;

                                    case "8":
                                        orderStatus.setText("退款审核中");
                                        break;

                                    case "9":
                                        orderStatus.setText("退款审核失败");
                                        break;

                                    case "10":
                                        orderStatus.setText("随友取消订单");
                                        break;

                                    default:
                                        orderStatus.setText("订单状态未知");
                                        break;
                                }
                            } else {
                                orderStatus.setText("订单状态未知");
                            }

                        } else {
                            orderDetailsTitle.setText("");
                            orderStarIndicator.setRating(DEFAULT_SCORE);
                            baseOrderPriceView.setText("");
                            orderDetailsDate.setText("");
                            orderDetailsTime.setText("");
                            orderDetailsSuiuuNumber.setText("0人");
                            orderDetailsService.setText("0");
                            orderStatus.setText("订单状态未知");
                        }

                        if (publisherBaseEntity != null) {
                            //用户头像
                            String headImagePath = publisherBaseEntity.getHeadImg();
                            if (!TextUtils.isEmpty(headImagePath)) {
                                headImageView.setImageURI(Uri.parse(headImagePath));
                            } else {
                                headImageView.setImageURI(Uri.parse(failureImagePath));
                            }

                            //用户昵称
                            String nickName = publisherBaseEntity.getNickname();
                            if (!TextUtils.isEmpty(nickName)) {
                                userName.setText(nickName);
                            } else {
                                userName.setText("");
                            }

                            //电话号码
                            String phoneNumber = publisherBaseEntity.getPhone();
                            if (!TextUtils.isEmpty(phoneNumber)) {
                                orderDetailsPhone.setText(phoneNumber);
                            } else {
                                orderDetailsPhone.setText("");
                            }

                        } else {
                            userName.setText("");
                            orderDetailsPhone.setText("");
                            headImageView.setImageURI(Uri.parse(failureImagePath));
                        }

                    }

                }

            } catch (Exception e) {
                L.e(TAG, "数据绑定异常:" + e.getMessage());
                try {
                    JSONObject object = new JSONObject(str);
                    String status = object.getString(STATUS);
                    if (status.equals("-1")) {
                        Toast.makeText(GeneralOrderDetailsActivity.this, SystemException, Toast.LENGTH_SHORT).show();
                    } else if (status.equals("-2")) {
                        Toast.makeText(GeneralOrderDetailsActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    Toast.makeText(GeneralOrderDetailsActivity.this, DataError, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class OrderDetailsResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "返回的数据:" + response);
            hideDialog();
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Exception:" + e.getMessage());
            hideDialog();
            Toast.makeText(GeneralOrderDetailsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    private class CancelOrderCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            Log.i("suiuu", "---" + e.toString());
        }

        @Override
        public void onResponse(String response) {
            try {
                JSONObject result = new JSONObject(response);
                int results = (int) result.get("status");
                if (results == 1) {
                    if ("0".equals(status)) {
                        Toast.makeText(GeneralOrderDetailsActivity.this, R.string.CancelOrderSuccess, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GeneralOrderDetailsActivity.this, R.string.DeleteOrderSuccess, Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else {
                    if ("0".equals(status)) {
                        Toast.makeText(GeneralOrderDetailsActivity.this, R.string.CancelOrderFailure, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GeneralOrderDetailsActivity.this, R.string.DeleteOrderFailure, Toast.LENGTH_SHORT).show();

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}