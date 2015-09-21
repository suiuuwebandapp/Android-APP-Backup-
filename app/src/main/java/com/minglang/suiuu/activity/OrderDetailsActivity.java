package com.minglang.suiuu.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.OrderDetails;
import com.minglang.suiuu.entity.OrderDetails.OrderDetailsData.InfoEntity;
import com.minglang.suiuu.entity.OrderDetails.OrderDetailsData.UserInfoEntity;
import com.minglang.suiuu.entity.TripJsonInfo;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.AppUtils;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
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
    private static final String ORDER_STATUS = "orderStatus";

    private static final String ORDER_NUMBER = "orderNumber";

    private static final String ORDER_ID = "orderId";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private static final String NEW = "new";
    private static final String CONFIRM = "Confirm";

    private static final String TRIP_ID = "tripId";

    /**
     * 订单ID
     */
    private String strID;

    @BindString(R.string.load_wait)
    String dialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.SystemException)
    String systemException;

    @BindString(R.string.DataException)
    String DataException;

    @BindString(R.string.UnknownError)
    String UnknownError;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.CancelOrderSuccess)
    String cancelOrderSuc;

    @BindString(R.string.ConfirmOrderSuccess)
    String confirmOrderSuc;

    @BindString(R.string.IgnoreOrderSuccess)
    String ignoreOrderSuc;

    @BindString(R.string.inputRefundReason)
    String refundReason;

    @BindString(R.string.refundReasonNotNull)
    String refundReasonNotNull;

    /**
     * 订单详情地址
     */
    private String orderDetailsDataPath;

    /**
     * 取消订单地址
     */
    private String cancelOrderPath;

    /**
     * 确认订单地址
     */
    private String confirmOrderPath;

    /**
     * 忽略订单地址
     */
    private String ignoreOrderPath;

    /**
     * 取消订单理由
     */
    private String cancelOrderReason;

    @Bind(R.id.suiuu_order_details_back)
    ImageView suiuuOrderDetailsBack;

    @Bind(R.id.order_details_head_frame)
    PtrClassicFrameLayout mPtrFrame;

    @Bind(R.id.suiuu_order_details_scroll_view)
    ScrollView scrollView;

    @Bind(R.id.suiuu_order_details_head_image)
    SimpleDraweeView headImageView;

    @Bind(R.id.suiuu_order_details_status)
    TextView orderStatus;

    @Bind(R.id.suiuu_order_details_user_name)
    TextView userNameView;

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
    TextView orderPriceView;

    @Bind(R.id.suiuu_order_details_phone_number)
    TextView phoneNumberView;

    @Bind(R.id.suiuu_order_details_call_phone)
    ImageView callPhoneBtn;

    @Bind(R.id.suiuu_order_details_email_address)
    TextView emailAddressView;

    @Bind(R.id.order_details_btn_layout_1)
    RelativeLayout bottomLayout1;

    @Bind(R.id.order_details_btn_layout_2)
    LinearLayout bottomLayout2;

    @Bind(R.id.order_details_bottom_1_btn)
    Button cancelOrderBtn;

    @Bind(R.id.order_details_bottom_2_btn_1)
    Button confirmOrderBtn;

    @Bind(R.id.order_details_bottom_2_btn_2)
    Button ignoreOrderBtn;

    private Context context;

    private boolean isPullToRefresh = true;

    private ProgressDialog progressDialog;

    private InfoEntity infoEntity;

    private OrderDetailsResultCallback orderDetailsResultCallback = new OrderDetailsResultCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        initView();
        viewAction();

        String[] keyArray = new String[]{ORDER_NUMBER, HttpServicePath.key, TOKEN};
        String[] valueArray = new String[]{strID, verification, token};
        getData4Service(orderDetailsDataPath, keyArray, valueArray, orderDetailsResultCallback);

    }

    private void initView() {
        Intent oldIntent = getIntent();
        strID = oldIntent.getStringExtra(ID);
        String selectOrderStatus = oldIntent.getStringExtra(ORDER_STATUS);
        L.i(TAG, "订单ID:" + strID + ",orderStatus:" + selectOrderStatus);

        if (selectOrderStatus.equals(NEW)) {
            bottomLayout1.setVisibility(View.VISIBLE);
        } else if (selectOrderStatus.equals(CONFIRM)) {
            bottomLayout2.setVisibility(View.VISIBLE);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(dialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        int paddingParams = AppUtils.newInstance().dip2px(15, this);

        MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, paddingParams, 0, paddingParams);
        header.setPtrFrameLayout(mPtrFrame);

        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setPinContent(true);

        context = OrderDetailsActivity.this;

        orderDetailsDataPath = HttpNewServicePath.getOrderDetailsDataPath;

        cancelOrderPath = HttpNewServicePath.setCancelOrderDataPath;
        confirmOrderPath = HttpNewServicePath.setConfirmOrderDataPath;
        ignoreOrderPath = HttpNewServicePath.setIgnoreOrderDataPath;

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
                String[] keyArray = new String[]{ORDER_NUMBER, HttpServicePath.key, TOKEN};
                String[] valueArray = new String[]{strID, verification, token};
                getData4Service(orderDetailsDataPath, keyArray, valueArray, orderDetailsResultCallback);
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

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripId = infoEntity.getTripId();
                Intent intent = new Intent(OrderDetailsActivity.this, SuiuuDetailsActivity.class);
                intent.putExtra(TRIP_ID, tripId);
                startActivity(intent);
            }
        });

        callPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberView.getText().toString();
                if (!TextUtils.isEmpty(phoneNumber)) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                    context.startActivity(intent);
                }
            }
        });

        cancelOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(context);
                new AlertDialog.Builder(context)
                        .setTitle(refundReason).setView(editText)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelOrderReason = editText.getText().toString().trim();
                                L.i(TAG, "退款理由:" + cancelOrderReason);

                                if (TextUtils.isEmpty(cancelOrderPath)) {
                                    Toast.makeText(context, refundReasonNotNull, Toast.LENGTH_SHORT).show();
                                } else {
                                    String[] keyArray = new String[]{ORDER_NUMBER, HttpServicePath.key, TOKEN};
                                    String[] valueArray = new String[]{strID, cancelOrderReason, token};
                                    getData4Service(cancelOrderPath, keyArray, valueArray, new CancelOrderResultCallback());
                                }

                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .create().show();
            }
        });

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] keyArray = new String[]{ORDER_ID, TOKEN};
                String[] valueArray = new String[]{strID, token};
                getData4Service(confirmOrderPath, keyArray, valueArray, new ConfirmOrderResultCallback());
            }
        });

        ignoreOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] keyArray = new String[]{ORDER_ID, TOKEN};
                String[] valueArray = new String[]{strID, token};
                getData4Service(ignoreOrderPath, keyArray, valueArray, new IgnoreOrderResultCallback());
            }
        });

    }

    private void getData4Service(String path, String[] keyArray, String[] valueArray,
                                 OkHttpManager.ResultCallback<String> requestCallBack) {
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        String url = addUrlAndParams(path, keyArray, valueArray);

        try {
            OkHttpManager.onGetAsynRequest(url, requestCallBack);
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        mPtrFrame.refreshComplete();
    }

    /**
     * 将数据绑定到View
     *
     * @param str 返回的Json数据
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(this, NoData, Toast.LENGTH_SHORT).show();
        } else {
            JsonUtils jsonUtils = JsonUtils.getInstance();
            try {
                OrderDetails orderDetails = jsonUtils.fromJSON(OrderDetails.class, str);
                infoEntity = orderDetails.getData().getInfo();
                UserInfoEntity userInfo = orderDetails.getData().getUserInfo();

                String headImagePath = userInfo.getHeadImg();
                if (!TextUtils.isEmpty(headImagePath)) {
                    headImageView.setImageURI(Uri.parse(headImagePath));
                } else {
                    headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
                }

                String strUserName = userInfo.getNickname();
                if (!TextUtils.isEmpty(strUserName)) {
                    userNameView.setText(strUserName);
                } else {
                    userNameView.setText(strUserName);
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
                    orderPriceView.setText(strOrderPrice);
                } else {
                    orderPriceView.setText("");
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
                                confirmOrderBtn.setEnabled(true);
                                ignoreOrderBtn.setEnabled(true);
                                break;

                            case "2":
                                orderStatus.setText("已支付 已确认");
                                cancelOrderBtn.setEnabled(true);
                                confirmOrderBtn.setEnabled(false);
                                ignoreOrderBtn.setEnabled(false);
                                break;

                            case "3":
                                orderStatus.setText("未支付 已取消");
                                cancelOrderBtn.setEnabled(false);
                                confirmOrderBtn.setEnabled(false);
                                ignoreOrderBtn.setEnabled(false);
                                break;

                            case "4":
                                orderStatus.setText("待退款");
                                cancelOrderBtn.setEnabled(false);
                                confirmOrderBtn.setEnabled(false);
                                ignoreOrderBtn.setEnabled(false);
                                break;

                            case "5":
                                orderStatus.setText("退款成功");
                                cancelOrderBtn.setEnabled(false);
                                confirmOrderBtn.setEnabled(false);
                                ignoreOrderBtn.setEnabled(false);
                                break;

                            case "6":
                                orderStatus.setText("游玩结束 待付款给随友");
                                cancelOrderBtn.setEnabled(false);
                                confirmOrderBtn.setEnabled(false);
                                ignoreOrderBtn.setEnabled(false);
                                break;

                            case "7":
                                orderStatus.setText("结束，已经付款给随友");
                                cancelOrderBtn.setEnabled(false);
                                confirmOrderBtn.setEnabled(false);
                                ignoreOrderBtn.setEnabled(false);
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
                                cancelOrderBtn.setEnabled(false);
                                confirmOrderBtn.setEnabled(false);
                                ignoreOrderBtn.setEnabled(false);
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
                L.e(TAG, "绑定数据异常:" + e.getMessage());
                handleException(str, DataError);
            }

        }

    }

    private void cancelOrder(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject object = new JSONObject(str);
                String status = object.getString(STATUS);
                String returnData = object.getString(DATA);

                if (!TextUtils.isEmpty(status)) {
                    switch (status) {
                        case "1":
                            Toast.makeText(context, cancelOrderSuc, Toast.LENGTH_SHORT).show();
                            break;
                        case "-1":
                            Toast.makeText(context, systemException, Toast.LENGTH_SHORT).show();
                            break;
                        case "-2":
                            Toast.makeText(context, returnData, Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(context, UnknownError, Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                L.e(TAG, "解析异常:" + e.getMessage());
                handleException(str, DataException);
            }
        } else {
            Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmOrder(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject object = new JSONObject(str);
                String status = object.getString(STATUS);
                String returnData = object.getString(DATA);

                if (!TextUtils.isEmpty(status)) {
                    switch (status) {
                        case "1":
                            Toast.makeText(context, confirmOrderSuc, Toast.LENGTH_SHORT).show();
                            break;
                        case "-1":
                            Toast.makeText(context, systemException, Toast.LENGTH_SHORT).show();
                            break;
                        case "-2":
                            Toast.makeText(context, returnData, Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(context, UnknownError, Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                L.e(TAG, "解析异常:" + e.getMessage());
                handleException(str, DataException);
            }
        } else {
            Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
        }
    }

    private void ignoreOrder(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject object = new JSONObject(str);
                String status = object.getString(STATUS);
                String returnData = object.getString(DATA);

                if (!TextUtils.isEmpty(status)) {
                    switch (status) {
                        case "1":
                            Toast.makeText(context, ignoreOrderSuc, Toast.LENGTH_SHORT).show();
                            break;
                        case "-1":
                            Toast.makeText(context, systemException, Toast.LENGTH_SHORT).show();
                            break;
                        case "-2":
                            Toast.makeText(context, returnData, Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(context, UnknownError, Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                L.e(TAG, "解析异常:" + e.getMessage());
                handleException(str, DataException);
            }
        } else {
            Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleException(String str, String error) {
        try {
            JSONObject object = new JSONObject(str);
            String status = object.getString(STATUS);
            switch (status) {
                case "-1":
                    Toast.makeText(context, SystemException, Toast.LENGTH_SHORT).show();
                    break;
                case "-2":
                    Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (Exception e) {
            L.e(TAG, "二次解析异常:" + e.getMessage());
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        }
    }

    private class OrderDetailsResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "订单详情数据:" + response);
            hideDialog();
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Exception:" + e.getMessage());
            hideDialog();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    private class CancelOrderResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "取消订单数据:" + response);
            hideDialog();
            cancelOrder(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            hideDialog();
            L.e(TAG, "Cancel Exception:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    private class ConfirmOrderResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "确认订单返回的数据:" + response);
            hideDialog();
            confirmOrder(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Confirm Exception:" + e.getMessage());
            hideDialog();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    private class IgnoreOrderResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "忽略订单数据:" + response);
            hideDialog();
            ignoreOrder(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Ignore Exception:" + e.getMessage());
            hideDialog();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

}