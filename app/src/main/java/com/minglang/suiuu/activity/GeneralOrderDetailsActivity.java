package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.GeneralOrderDetails;
import com.minglang.suiuu.entity.GeneralOrderDetails.GeneralOrderDetailsData;
import com.minglang.suiuu.entity.GeneralOrderDetails.GeneralOrderDetailsData.InfoEntity;
import com.minglang.suiuu.entity.GeneralOrderDetails.GeneralOrderDetailsData.PublisherBaseEntity;
import com.minglang.suiuu.entity.ServiceInfo;
import com.minglang.suiuu.entity.TripJsonInfo;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 订单详情页面-普通用户
 */
public class GeneralOrderDetailsActivity extends BaseAppCompatActivity {
    private static final String TAG = GeneralOrderDetailsActivity.class.getSimpleName();

    private static final String ORDER_NUMBER = "orderNumber";

    private static final float DEFAULT_SCORE = 0f;

    @BindString(R.string.load_wait)
    String dialogMsg;

    @BindString(R.string.NoData)
    String dataNull;

    @BindString(R.string.NetworkAnomaly)
    String errorMsg;

    @Bind(R.id.general_order_details_head_frame)
    PtrClassicFrameLayout mPtrFrame;

    @Bind(R.id.general_order_details_parent_view)
    ScrollView scrollView;

    @Bind(R.id.order_details_base_price)
    TextView orderBasePrice;

    @Bind(R.id.order_details_title)
    TextView orderDetailsTitle;

    @Bind(R.id.order_details_indicator)
    RatingBar orderStarIndicator;

    @Bind(R.id.order_details_head_image_view)
    CircleImageView headImageView;

    @Bind(R.id.order_details_name)
    TextView userName;

    @Bind(R.id.order_details_status)
    TextView orderStatus;

    @Bind(R.id.order_details_phone)
    TextView orderDetailsPhone;

    @Bind(R.id.order_details_date)
    TextView orderDetailsDate;

    @Bind(R.id.order_details_time)
    TextView orderDetailsTime;

    @Bind(R.id.order_details_suiuu_number)
    TextView orderDetailsSuiuuNumber;

    @Bind(R.id.order_details_service_layout)
    LinearLayout serviceLayout;

    @Bind(R.id.order_details_additional_service_prices)
    TextView orderDetailsService;

    @Bind(R.id.order_details_back)
    ImageView orderDetailsBack;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    private boolean isPullToRefresh = true;

    private String orderNumber;

    private String verification;

    private ProgressDialog progressDialog;

    private LayoutInflater inflater;

    private float orderTotalPrice = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_order_details);
        ButterKnife.bind(this);
        initView();
        ViewAction();
        getGeneralUserOrderData4Service(buildRequestParams());
    }

    private void initView() {
        orderNumber = getIntent().getStringExtra(ORDER_NUMBER);
        DeBugLog.i(TAG, "OrderID:" + orderNumber);

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image2)
                .showImageForEmptyUri(R.drawable.default_head_image2)
                .showImageOnFail(R.drawable.default_head_image2).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        verification = SuiuuInfo.ReadVerification(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(dialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        int paddingParams = Utils.newInstance().dip2px(15, this);

        MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, paddingParams, 0, paddingParams);
        header.setPtrFrameLayout(mPtrFrame);

        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setPinContent(true);

        inflater = LayoutInflater.from(this);
    }

    private void ViewAction() {

        orderDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, scrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                isPullToRefresh = false;
                getGeneralUserOrderData4Service(buildRequestParams());
            }

        });
    }

    private RequestParams buildRequestParams() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(ORDER_NUMBER, orderNumber);
        params.addBodyParameter(HttpServicePath.key, verification);
        return params;
    }

    private void getGeneralUserOrderData4Service(RequestParams params) {
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getGeneralUserOrderDetailsPath, new GeneralUserOrderDetailsRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        mPtrFrame.refreshComplete();
    }

    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(this, dataNull, Toast.LENGTH_SHORT).show();
        } else {
            JsonUtils jsonUtils = JsonUtils.getInstance();
            try {
                GeneralOrderDetails details = jsonUtils.fromJSON(GeneralOrderDetails.class, str);
                if (details != null) {
                    GeneralOrderDetailsData detailsData = details.getData();

                    if (detailsData != null) {
                        PublisherBaseEntity publisherBaseEntity = detailsData.getPublisherBase();
                        InfoEntity infoEntity = detailsData.getInfo();

                        if (infoEntity != null) {
                            String jsonInfo = infoEntity.getTripJsonInfo();
                            TripJsonInfo tripJsonInfo;

                            if (!TextUtils.isEmpty(jsonInfo)) {
                                tripJsonInfo = jsonUtils.fromJSON(TripJsonInfo.class, jsonInfo);
                                if (tripJsonInfo != null) {
                                    //标题
                                    String title = tripJsonInfo.getInfo().getTitle();
                                    if (!TextUtils.isEmpty(title)) {
                                        orderDetailsTitle.setText(title);
                                    } else {
                                        orderDetailsTitle.setText("");
                                    }
                                    DeBugLog.i(TAG, "Title:" + title);

                                    //星级评价
                                    String strOrderScore = tripJsonInfo.getInfo().getScore();
                                    if (!TextUtils.isEmpty(strOrderScore)) {
                                        float score = Float.valueOf(strOrderScore);
                                        orderStarIndicator.setRating(score);
                                    } else {
                                        orderStarIndicator.setRating(DEFAULT_SCORE);
                                    }
                                    DeBugLog.i(TAG, "Score:" + strOrderScore);

                                }
                            } else {
                                orderDetailsTitle.setText("");
                                orderStarIndicator.setRating(DEFAULT_SCORE);
                            }

                            //订单基础金额
                            String basePrice = infoEntity.getBasePrice();
                            if (!TextUtils.isEmpty(basePrice)) {
                                float basePriceNumber = Float.valueOf(basePrice);
                                orderTotalPrice = orderTotalPrice + basePriceNumber;
                                orderBasePrice.setText(basePrice);
                            } else {
                                orderBasePrice.setText("");
                            }
                            DeBugLog.i(TAG, "basePrice:" + basePrice);

                            //开始日期
                            String beginDate = infoEntity.getBeginDate();
                            if (!TextUtils.isEmpty(beginDate)) {
                                orderDetailsDate.setText(beginDate);
                            } else {
                                orderDetailsDate.setText("");
                            }
                            DeBugLog.i(TAG, "beginDate:" + beginDate);

                            //开始时间
                            String startTime = infoEntity.getStartTime();
                            if (!TextUtils.isEmpty(startTime)) {
                                orderDetailsTime.setText(startTime);
                            } else {
                                orderDetailsTime.setText("");
                            }
                            DeBugLog.i(TAG, "startTime:" + startTime);

                            //随游人数
                            String personCount = infoEntity.getPersonCount();
                            if (!TextUtils.isEmpty(personCount)) {
                                orderDetailsSuiuuNumber.setText(personCount + "人");
                            } else {
                                orderDetailsSuiuuNumber.setText("0人");
                            }
                            DeBugLog.i(TAG, "personCount:" + personCount);

                            //附加服务列表
                            String strServiceInfo = infoEntity.getServiceInfo();
                            DeBugLog.i(TAG, "附加服务数据:" + strServiceInfo);
                            if (!TextUtils.isEmpty(strServiceInfo)) {

                                if (!strServiceInfo.equals("[]")) {
                                    List<ServiceInfo> serviceInfoList
                                            = jsonUtils.fromJSON(new TypeToken<ServiceInfo>() {
                                    }.getType(), strServiceInfo);

                                    if (serviceInfoList != null && serviceInfoList.size() > 0) {
                                        orderDetailsService.setText(String.valueOf(serviceInfoList.size()));

                                        for (int i = 0; i < serviceInfoList.size(); i++) {
                                            View serviceItemLayout = inflater.inflate
                                                    (R.layout.item_service_location_layout, serviceLayout, false);
                                            TextView serviceNameView = (TextView) serviceItemLayout
                                                    .findViewById(R.id.order_details_service_name);
                                            TextView servicePriceView = (TextView) serviceItemLayout
                                                    .findViewById(R.id.order_details_service_prices);

                                            String serviceTitle = serviceInfoList.get(i).getTitle();
                                            if (!TextUtils.isEmpty(serviceTitle)) {
                                                serviceNameView.setText(serviceTitle);
                                            }

                                            String servicePrice = serviceInfoList.get(i).getMoney();
                                            float serviceItemPrice = 0f;
                                            if (!TextUtils.isEmpty(servicePrice)) {
                                                serviceItemPrice = Float.valueOf(servicePrice);
                                                servicePriceView.setText(servicePrice);
                                            }

                                            orderTotalPrice = orderTotalPrice + serviceItemPrice;
                                            serviceLayout.addView(serviceItemLayout);
                                        }
                                    } else {
                                        orderDetailsService.setText("0");
                                    }
                                } else {
                                    orderDetailsService.setText("0");
                                }

                            }

                            //订单总价
                            TextView orderTotalPriceView = (TextView) inflater
                                    .inflate(R.layout.item_service_location_layout2, serviceLayout, false);
                            orderTotalPriceView.setText("总价:" + orderTotalPrice);
                            serviceLayout.addView(orderTotalPriceView);
                            DeBugLog.i(TAG, "TotalPrice:" + orderTotalPrice);

                            //订单状态
                            String status = infoEntity.getStatus();
                            if (!TextUtils.isEmpty(status)) {
                                switch (status) {
                                    case "0":
                                        orderStatus.setText("待支付");
                                        break;
                                    case "1":
                                        orderStatus.setText("已支付 待确认");
                                        break;
                                    case "2":
                                        orderStatus.setText("已支付 已确认");
                                        break;
                                    case "3":
                                        orderStatus.setText("未支付 已取消");
                                        break;
                                    case "4":
                                        orderStatus.setText("待退款");
                                        break;
                                    case "5":
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
                                DeBugLog.i(TAG, "status:" + status);
                            } else {
                                orderStatus.setText("订单状态未知");
                            }

                        } else {
                            orderDetailsTitle.setText("");
                            orderStarIndicator.setRating(DEFAULT_SCORE);
                            orderBasePrice.setText("");
                            orderDetailsDate.setText("");
                            orderDetailsTime.setText("");
                            orderDetailsSuiuuNumber.setText("0人");
                            orderDetailsService.setText("0");
                            orderStatus.setText("订单状态未知");
                        }

                        if (publisherBaseEntity != null) {
                            //用户头像
                            String headImagePath = publisherBaseEntity.getHeadImg();
                            if (TextUtils.isEmpty(headImagePath)) {
                                imageLoader.displayImage(headImagePath, headImageView, options);
                            }
                            DeBugLog.i(TAG, "headImagePath:" + headImagePath);

                            //用户昵称
                            String nickName = publisherBaseEntity.getNickname();
                            if (!TextUtils.isEmpty(nickName)) {
                                userName.setText(nickName);
                            } else {
                                userName.setText("");
                            }
                            DeBugLog.i(TAG, "nickName:" + nickName);

                            //电话号码
                            String phoneNumber = publisherBaseEntity.getPhone();
                            if (!TextUtils.isEmpty(phoneNumber)) {
                                orderDetailsPhone.setText(phoneNumber);
                            } else {
                                orderDetailsPhone.setText("");
                            }
                            DeBugLog.i(TAG, "phoneNumber:" + phoneNumber);
                        } else {
                            userName.setText("");
                            orderDetailsPhone.setText("");
                        }

                    }

                }

            } catch (Exception e) {
                DeBugLog.e(TAG, "数据绑定异常:" + e.getMessage());
            }
        }
    }

    private class GeneralUserOrderDetailsRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (isPullToRefresh)
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            hideDialog();
            String str = responseInfo.result;
            bindData2View(str);
            DeBugLog.i(TAG, "返回的数据:" + str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",Error:" + s);
            hideDialog();
            Toast.makeText(GeneralOrderDetailsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }

    }

}