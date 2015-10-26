package com.minglang.suiuu.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import com.minglang.suiuu.entity.TripJsonInfo.ServiceListEntity;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.StatusBarCompat;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
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
 * 随友用户订单详情页
 */
public class OrderDetailsActivity extends BaseAppCompatActivity {

    private static final String TAG = OrderDetailsActivity.class.getSimpleName();

    private static final String ID = "id";
    private static final String TITLE_IMAGE = "titleImage";

    private static final String ORDER_NUMBER = "orderNumber";

    private static final String ORDER_ID = "orderId";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private static final String TRIP_ID = "tripId";
    private static final String CLASS_NAME = "className";

    private static final String RELATE_ID = "relateId";

    private static final String USER_SIGN = "userSign";

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

    @Bind(R.id.suiuu_order_details_background_image)
    SimpleDraweeView titleImageView;

    @Bind(R.id.suiuu_order_details_base_price)
    TextView basePriceView;

    @Bind(R.id.suiuu_order_details_head_image)
    SimpleDraweeView headImageView;

    @Bind(R.id.suiuu_order_details_user_name)
    TextView userNameView;

    @Bind(R.id.suiuu_order_details_indicator)
    RatingBar indicator;

    @Bind(R.id.suiuu_order_details_chat)
    ImageView chatView;

    @Bind(R.id.suiuu_order_details_create_time)
    TextView orderCreateTimeView;

    @Bind(R.id.suiuu_order_details_number)
    TextView orderNumberView;

    @Bind(R.id.suiuu_order_details_title)
    TextView titleView;

    @Bind(R.id.suiuu_order_details_date)
    TextView dateView;

    @Bind(R.id.suiuu_order_details_time)
    TextView timeView;

    @Bind(R.id.suiuu_order_details_people_number)
    TextView peopleNumberView;

    @Bind(R.id.suiuu_order_details_price)
    TextView orderPriceView;

    @Bind(R.id.suiuu_order_details_phone_number)
    TextView phoneNumberView;

    @Bind(R.id.suiuu_order_details_phone_layout)
    LinearLayout phoneNumberLayout;

    @Bind(R.id.suiuu_order_details_service_layout)
    LinearLayout serviceLayout;

    @Bind(R.id.order_details_btn_layout_2)
    LinearLayout bottomLayout;

    @Bind(R.id.order_details_bottom_1_btn)
    Button cancelOrderBtn;

    @Bind(R.id.order_details_bottom_2_btn_1)
    Button confirmOrderBtn;

    @Bind(R.id.order_details_bottom_2_btn_2)
    Button ignoreOrderBtn;

    private Context context;

    private boolean isPullToRefresh = true;

    private ProgressDialog progressDialog;

    private UserInfoEntity userInfo;

    private InfoEntity infoEntity;

    private OrderDetailsResultCallback orderDetailsResultCallback = new OrderDetailsResultCallback();

    private JsonUtils jsonUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        StatusBarCompat.compat(this);
        ButterKnife.bind(this);

        initView();
        viewAction();

        String[] keyArray = new String[]{ORDER_NUMBER, HttpNewServicePath.key, TOKEN};
        String[] valueArray = new String[]{strID, verification, token};
        getData4Service(HttpNewServicePath.getOrderDetailsDataPath, keyArray, valueArray, orderDetailsResultCallback);
    }

    private void initView() {
        Intent oldIntent = getIntent();
        strID = oldIntent.getStringExtra(ID);
        String titleImage = oldIntent.getStringExtra(TITLE_IMAGE);
        L.i(TAG, "orderId:" + strID + ",titleImagePath:" + titleImage);

        jsonUtils = JsonUtils.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(dialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        int paddingParams = AppUtils.dip2px(15, this);

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

        cancelOrderPath = HttpNewServicePath.setCancelOrderDataPath;
        confirmOrderPath = HttpNewServicePath.setConfirmOrderDataPath;
        ignoreOrderPath = HttpNewServicePath.setIgnoreOrderDataPath;

        if (!TextUtils.isEmpty(titleImage)) {
            titleImageView.setImageURI(Uri.parse(titleImage));
        }

        phoneNumberLayout.setVisibility(View.GONE);

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
                String[] keyArray = new String[]{ORDER_NUMBER, HttpNewServicePath.key, TOKEN};
                String[] valueArray = new String[]{strID, verification, token};
                getData4Service(HttpNewServicePath.getOrderDetailsDataPath, keyArray, valueArray, orderDetailsResultCallback);
            }

        });

        suiuuOrderDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailsActivity.this, PersonalMainPagerActivity.class);
                intent.putExtra(USER_SIGN, userInfo.getUserSign());
                startActivity(intent);
            }
        });

        titleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripId = infoEntity.getTripId();
                Intent intent = new Intent(OrderDetailsActivity.this, SuiuuDetailsActivity.class);
                intent.putExtra(TRIP_ID, tripId);
                intent.putExtra(CLASS_NAME, TAG);
                startActivity(intent);
            }
        });

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripId = infoEntity.getTripId();
                Intent intent = new Intent(OrderDetailsActivity.this, SuiuuDetailsActivity.class);
                intent.putExtra(TRIP_ID, tripId);
                intent.putExtra(CLASS_NAME, TAG);
                startActivity(intent);
            }
        });

        chatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headImagePath = userInfo.getHeadImg();
                String userSign = userInfo.getUserSign();

                Intent intent = new Intent(OrderDetailsActivity.this, PrivateLetterChatActivity.class);
                intent.putExtra(RELATE_ID, userSign);
                intent.putExtra(HEAD_IMG, headImagePath);
                startActivity(intent);

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
                                if (TextUtils.isEmpty(cancelOrderPath)) {
                                    Toast.makeText(context, refundReasonNotNull, Toast.LENGTH_SHORT).show();
                                } else {
                                    String[] keyArray = new String[]{ORDER_NUMBER, HttpNewServicePath.key, TOKEN};
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
                String orderID = infoEntity.getOrderId();
                String url = addUrlAndParams(confirmOrderPath, new String[]{TOKEN}, new String[]{token});
                postData4Service(url, new ConfirmOrderResultCallback(), new OkHttpManager.Params(ORDER_ID, orderID));
            }
        });

        ignoreOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderID = infoEntity.getOrderId();
                String url = addUrlAndParams(ignoreOrderPath, new String[]{TOKEN}, new String[]{token});
                postData4Service(url, new IgnoreOrderResultCallback(), new OkHttpManager.Params(ORDER_ID, orderID));
            }
        });

    }

    private void getData4Service(String path, String[] keyArray, String[] valueArray, OkHttpManager.ResultCallback<String> requestCallBack) {
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        String url = addUrlAndParams(path, keyArray, valueArray);
        L.i(TAG, "订单详情URL:" + url);
        try {
            OkHttpManager.onGetAsynRequest(url, requestCallBack);
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    private void postData4Service(String path, OkHttpManager.ResultCallback<String> requestCallBack, OkHttpManager.Params... params) {
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        try {
            OkHttpManager.onPostAsynRequest(path, requestCallBack, params);
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
        } else try {
            JSONObject object = new JSONObject(str);
            String status = object.getString(STATUS);
            switch (status) {
                case "1":
                    OrderDetails orderDetails = jsonUtils.fromJSON(OrderDetails.class, str);
                    infoEntity = orderDetails.getData().getInfo();
                    userInfo = orderDetails.getData().getUserInfo();

                    showUserInfo();
                    showOrderInfo();
                    showTripInfo();
                    switchOrderStatus();
                    break;

                case "-1":
                    Toast.makeText(OrderDetailsActivity.this, SystemException, Toast.LENGTH_SHORT).show();
                    break;

                case "-2":
                    Toast.makeText(OrderDetailsActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
                    break;

                case "-3":
                    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    localBroadcastManager.sendBroadcast(new Intent(SettingActivity.class.getSimpleName()));
                    ReturnLoginActivity(this);
                    break;

                case "-4":
                    Toast.makeText(OrderDetailsActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
                    break;

            }
        } catch (Exception e) {
            L.e(TAG, "绑定数据异常:" + e.getMessage());
            Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
        }
    }

    private void showUserInfo() {
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
    }

    private void showOrderInfo() {
        String basePrice = infoEntity.getBasePrice();
        if (!TextUtils.isEmpty(basePrice)) {
            basePriceView.setText(basePrice);
        }

        String strOrderCreateTime = infoEntity.getCreateTime();
        if (!TextUtils.isEmpty(strOrderCreateTime)) {
            orderCreateTimeView.setText(strOrderCreateTime);
        }

        String strOrderNumber = infoEntity.getOrderNumber();
        if (!TextUtils.isEmpty(strOrderNumber)) {
            orderNumberView.setText(strOrderNumber);
        }

        String beginDate = infoEntity.getBeginDate();
        if (!TextUtils.isEmpty(beginDate)) {
            dateView.setText(beginDate);
        }

        String startTime = infoEntity.getStartTime();
        if (!TextUtils.isEmpty(startTime)) {
            timeView.setText(startTime);
        }

        String strPersonalCount = infoEntity.getPersonCount();
        if (!TextUtils.isEmpty(strPersonalCount)) {
            peopleNumberView.setText(strPersonalCount);
        }

        String strOrderPrice = infoEntity.getTotalPrice();
        if (!TextUtils.isEmpty(strOrderPrice)) {
            orderPriceView.setText(String.format("%s%s", "总价￥", strOrderPrice));
        }
    }

    private void showTripInfo() {
        try {
            TripJsonInfo tripJsonInfo = jsonUtils.fromJSON(TripJsonInfo.class, infoEntity.getTripJsonInfo());
            if (tripJsonInfo != null) {
                String strTitle = tripJsonInfo.getInfo().getTitle();
                if (!TextUtils.isEmpty(strTitle)) {
                    titleView.setText(strTitle);
                }

                String strScore = tripJsonInfo.getInfo().getScore();
                L.i(TAG, "星级评价:" + strScore);
                if (TextUtils.isEmpty(strScore)) {
                    indicator.setRating(0f);
                } else {
                    float score = Float.valueOf(strScore);
                    indicator.setRating(score);
                }

                String strPhone = tripJsonInfo.getCreatePublisherInfo().getPhone();
                if (!TextUtils.isEmpty(strPhone)) {
                    phoneNumberView.setText(strPhone);
                }

                List<ServiceListEntity> serviceList = tripJsonInfo.getServiceList();
                if (serviceList != null && serviceList.size() > 0) {
                    for (int i = 0; i < serviceList.size(); i++) {
                        View itemServiceView =
                                LayoutInflater.from(this).inflate(R.layout.layout_order_details_service_item, serviceLayout, false);
                        TextView serviceName = (TextView) itemServiceView.findViewById(R.id.suiuu_order_details_service_name);
                        TextView servicePrice = (TextView) itemServiceView.findViewById(R.id.suiuu_order_details_service_price);

                        serviceName.setText(serviceList.get(i).getTitle());
                        servicePrice.setText(serviceList.get(i).getMoney());

                        serviceLayout.addView(itemServiceView);
                    }
                } else {
                    serviceLayout.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            L.e(TAG, "Trip数据解析失败:" + e.getMessage());
        }
    }

    private void switchOrderStatus() {
        String status = infoEntity.getStatus();
        if (!TextUtils.isEmpty(status)) {
            L.i(TAG, "订单状态:" + status);
            switch (status) {
                case "1":
                    bottomLayout.setVisibility(View.VISIBLE);
                    break;

                case "2":
                    phoneNumberLayout.setVisibility(View.VISIBLE);
                    bottomLayout.setVisibility(View.GONE);
                    break;

                case "3":
                    phoneNumberLayout.setVisibility(View.GONE);
                    bottomLayout.setVisibility(View.GONE);
                    break;

                case "4":

                case "5":
                    phoneNumberLayout.setVisibility(View.GONE);
                    bottomLayout.setVisibility(View.GONE);
                    break;

                case "6":

                case "7":
                    phoneNumberLayout.setVisibility(View.VISIBLE);
                    bottomLayout.setVisibility(View.GONE);
                    break;

                case "8":

                case "9":
                    phoneNumberLayout.setVisibility(View.VISIBLE);
                    bottomLayout.setVisibility(View.GONE);
                    break;

                case "10":
                    phoneNumberLayout.setVisibility(View.GONE);
                    bottomLayout.setVisibility(View.GONE);
                    break;

                default:
                    phoneNumberLayout.setVisibility(View.VISIBLE);
                    bottomLayout.setVisibility(View.GONE);
                    break;
            }
        }
    }

    private void cancelOrder(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
        } else try {
            JSONObject object = new JSONObject(str);
            String status = object.getString(STATUS);
            if (!TextUtils.isEmpty(status)) {
                switch (status) {
                    case "1":
                        Toast.makeText(context, cancelOrderSuc, Toast.LENGTH_SHORT).show();
                        break;

                    case "-1":
                        Toast.makeText(OrderDetailsActivity.this, SystemException, Toast.LENGTH_SHORT).show();
                        break;

                    case "-2":
                        Toast.makeText(OrderDetailsActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
                        break;

                    case "-3":
                        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
                        localBroadcastManager.sendBroadcast(new Intent(SettingActivity.class.getSimpleName()));
                        ReturnLoginActivity(this);
                        break;

                    case "-4":
                        Toast.makeText(OrderDetailsActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, DataException, Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmOrder(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
        } else try {
            JSONObject object = new JSONObject(str);
            String status = object.getString(STATUS);
            if (!TextUtils.isEmpty(status)) {
                switch (status) {
                    case "1":
                        phoneNumberLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(context, confirmOrderSuc, Toast.LENGTH_SHORT).show();
                        break;

                    case "-1":
                        Toast.makeText(context, SystemException, Toast.LENGTH_SHORT).show();
                        break;

                    case "-2":
                        Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                        break;

                    case "-3":
                        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
                        localBroadcastManager.sendBroadcast(new Intent(SettingActivity.class.getSimpleName()));
                        ReturnLoginActivity(this);
                        break;

                    case "-4":
                        Toast.makeText(OrderDetailsActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, DataException, Toast.LENGTH_SHORT).show();
        }
    }

    private void ignoreOrder(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
        } else try {
            JSONObject object = new JSONObject(str);
            String status = object.getString(STATUS);

            if (!TextUtils.isEmpty(status)) {
                switch (status) {
                    case "1":
                        Toast.makeText(context, ignoreOrderSuc, Toast.LENGTH_SHORT).show();
                        break;

                    case "-1":
                        Toast.makeText(context, SystemException, Toast.LENGTH_SHORT).show();
                        break;

                    case "-2":
                        Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                        break;

                    case "-3":
                        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
                        localBroadcastManager.sendBroadcast(new Intent(SettingActivity.class.getSimpleName()));
                        ReturnLoginActivity(this);
                        break;

                    case "-4":
                        Toast.makeText(OrderDetailsActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, DataException, Toast.LENGTH_SHORT).show();
        }
    }

    private class OrderDetailsResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "订单详情数据:" + response);
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Exception:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }

    }

    private class CancelOrderResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "取消订单数据:" + response);
            cancelOrder(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Cancel Exception:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }

    }

    private class ConfirmOrderResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
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