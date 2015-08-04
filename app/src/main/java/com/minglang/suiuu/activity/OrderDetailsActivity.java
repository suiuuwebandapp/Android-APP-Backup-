package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.OrderDetails;
import com.minglang.suiuu.entity.OrderDetails.OrderDetailsData.InfoEntity;
import com.minglang.suiuu.entity.OrderDetails.OrderDetailsData.UserInfoEntity;
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

public class OrderDetailsActivity extends BaseAppCompatActivity {

    private static final String TAG = OrderDetailsActivity.class.getSimpleName();

    private static final String ID = "id";

    private static final String ORDER_NUMBER = "orderNumber";

    private String strID;

    @BindString(R.string.load_wait)
    String dialogMsg;

    @BindString(R.string.NoData)
    String noData;

    @BindString(R.string.DataError)
    String errorString;

    @Bind(R.id.suiuu_order_details_back)
    ImageView suiuuOrderDetailsBack;

    @Bind(R.id.order_details_head_frame)
    PtrClassicFrameLayout mPtrFrame;

    @Bind(R.id.suiuu_order_details_scroll_view)
    ScrollView scrollView;

    @Bind(R.id.suiuu_order_details_head_image)
    CircleImageView headImageView;

    @Bind(R.id.suiuu_order_details_status)
    TextView orderStatus;

    @Bind(R.id.suiuu_order_details_user_name)
    TextView userName;

    @Bind(R.id.suiuu_order_details_chat)
    ImageView orderDetailsChat;

    @Bind(R.id.suiuu_order_details_btn)
    ImageView orderDetailsBtn;

    @Bind(R.id.suiuu_order_details_title)
    TextView titleView;

    @Bind(R.id.suiuu_order_details_date)
    TextView dateView;

    @Bind(R.id.suiuu_order_details_time)
    TextView timeView;

    @Bind(R.id.suiuu_order_details_people_number)
    TextView peopleNumberView;

    @Bind(R.id.suiuu_order_details_item_service)
    TextView itemServiceName;

    @Bind(R.id.suiuu_order_details_price)
    TextView orderPrice;

    @Bind(R.id.suiuu_order_details_phone_number)
    TextView phoneNumberView;

    @Bind(R.id.suiuu_order_details_call_phone)
    ImageView callPhoneBtn;

    @Bind(R.id.suiuu_order_details_email_address)
    TextView emailAddressView;

    private boolean isPullToRefresh = true;

    private ProgressDialog progressDialog;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);

        strID = getIntent().getStringExtra(ID);
        DeBugLog.i(TAG, "订单ID:" + strID);

        initView();
        viewAction();
        getOrderDetails4Service(buildRequestParams());
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(dialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image)
                .showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

        verification = SuiuuInfo.ReadVerification(this);

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

    }

    private void viewAction() {

        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, scrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                isPullToRefresh = false;
                getOrderDetails4Service(buildRequestParams());
            }

        });

        suiuuOrderDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orderDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        orderDetailsChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        callPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private RequestParams buildRequestParams() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter(ORDER_NUMBER, strID);
        return params;
    }

    private void getOrderDetails4Service(RequestParams params) {
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getOrderDetailsDataPath, new OrderDetailsRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        mPtrFrame.refreshComplete();
    }

    private void bindData2View(String str) {
        if (!TextUtils.isEmpty(str)) {
            Toast.makeText(this, noData, Toast.LENGTH_SHORT).show();
        } else {
            JsonUtils jsonUtils = JsonUtils.getInstance();
            try {
                OrderDetails orderDetails = jsonUtils.fromJSON(OrderDetails.class, str);
                InfoEntity infoEntity = orderDetails.getData().getInfo();
                UserInfoEntity userInfo = orderDetails.getData().getUserInfo();

                String headImagePath = userInfo.getHeadImg();
                if (!TextUtils.isEmpty(headImagePath)) {
                    imageLoader.displayImage(headImagePath, headImageView, options);
                }

                String strUserName = userInfo.getNickname();
                if (!TextUtils.isEmpty(strUserName)) {
                    userName.setText(strUserName);
                } else {
                    userName.setText(strUserName);
                }


                String beginDate = infoEntity.getBeginDate();
                if (!TextUtils.isEmpty(beginDate)) {
                    dateView.setText(beginDate);
                } else {
                    dateView.setText("");
                }

                String startTime = infoEntity.getStartTime();
                if (!TextUtils.isEmpty(startTime)) {
                    timeView.setText(startTime);
                } else {
                    timeView.setText("");
                }

                String strPersonalCount = infoEntity.getPersonCount();
                if (!TextUtils.isEmpty(strPersonalCount)) {
                    peopleNumberView.setText(strPersonalCount);
                } else {
                    peopleNumberView.setText("0");
                }

                String strOrderPrice = infoEntity.getTotalPrice();
                if (!TextUtils.isEmpty(strOrderPrice)) {
                    orderPrice.setText(strOrderPrice);
                } else {
                    orderPrice.setText("");
                }

                TripJsonInfo tripJsonInfo = jsonUtils.fromJSON(TripJsonInfo.class, infoEntity.getTripJsonInfo());
                if (tripJsonInfo != null) {

                    String strTitle = tripJsonInfo.getInfo().getTitle();
                    if (!TextUtils.isEmpty(strTitle)) {
                        titleView.setText(strTitle);
                    } else {
                        titleView.setText("");
                    }

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
                    } else {
                        orderStatus.setText("订单状态未知");
                    }

                    String strPhone = tripJsonInfo.getCreatePublisherInfo().getPhone();
                    if (!TextUtils.isEmpty(strPhone)) {
                        phoneNumberView.setText(strPhone);
                    } else {
                        phoneNumberView.setText("");
                    }

                    String strEmail = tripJsonInfo.getCreatePublisherInfo().getEmail();
                    if (!TextUtils.isEmpty(strEmail)) {
                        emailAddressView.setText(strEmail);
                    } else {
                        emailAddressView.setText("");
                    }

                    List<TripJsonInfo.ServiceListEntity> serviceList = tripJsonInfo.getServiceList();
                    if (serviceList != null && serviceList.size() > 0) {
                        String serviceName = null;
                        for (int i = 0; i < serviceList.size(); i++) {
                            String serviceTitle = serviceList.get(i).getTitle();
                            if (!TextUtils.isEmpty(serviceTitle)) {
                                serviceName = serviceName + " " + serviceTitle;
                            }
                        }

                        if (!TextUtils.isEmpty(serviceName)) {
                            itemServiceName.setText(serviceName);
                        } else {
                            itemServiceName.setText("");
                        }

                    }

                }

            } catch (Exception e) {
                DeBugLog.e(TAG, "绑定数据异常:" + e.getMessage());
                Toast.makeText(OrderDetailsActivity.this, errorString, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class OrderDetailsRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (isPullToRefresh)
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String str = responseInfo.result;
            hideDialog();
            bindData2View(str);
            DeBugLog.i(TAG, "订单详情数据:" + str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",Error:" + s);
            hideDialog();
            Toast.makeText(OrderDetailsActivity.this, errorString, Toast.LENGTH_SHORT).show();
        }

    }

}