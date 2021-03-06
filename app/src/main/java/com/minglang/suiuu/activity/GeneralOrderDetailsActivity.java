package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.reflect.TypeToken;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.GeneralOrderDetails;
import com.minglang.suiuu.entity.GeneralOrderDetails.GeneralOrderDetailsData.ContactEntity;
import com.minglang.suiuu.entity.GeneralOrderDetails.GeneralOrderDetailsData.InfoEntity;
import com.minglang.suiuu.entity.GeneralOrderDetails.GeneralOrderDetailsData.PublisherBaseEntity;
import com.minglang.suiuu.entity.ServiceInfo;
import com.minglang.suiuu.entity.TripJsonInfo;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.StatusBarCompat;
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

    private static final String RELATE_ID = "relateId";

    private static final String TRIP_ID = "tripId";
    private static final String HEAD_IMG = "headImg";
    private static final String CLASS_NAME = "className";

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
     * 订单创建时间
     */
    @Bind(R.id.order_details_create_time)
    TextView orderCreateTime;

    /**
     * 订单号
     */
    @Bind(R.id.order_details_number)
    TextView orderNumberView;

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
     * 主要联系人
     */
    @Bind(R.id.order_details_main_contact)
    TextView mainContact;

    /**
     * 微信号
     */
    @Bind(R.id.order_details_wechat_number)
    TextView weChatNumber;

    /**
     * 联系号码
     */
    @Bind(R.id.order_details_contact_number)
    TextView ContactNumber;

    /**
     * 备用联系号码
     */
    @Bind(R.id.order_details_standby_contact_number)
    TextView standbyContactNumber;

    /**
     * 紧急联系人
     */
    @Bind(R.id.order_details_emergency_contact)
    TextView emergencyContact;

    /**
     * 紧急联系号码
     */
    @Bind(R.id.order_details_emergency_contact_number)
    TextView emergencyContactNumber;

    @Bind(R.id.order_details_chat)
    ImageView chatView;

    @Bind(R.id.order_details_back)
    ImageView orderDetailsBack;

    /**
     * 支付
     */
    @Bind(R.id.order_details_repay)
    Button orderDetailsRepay;

    @Bind(R.id.order_details_suiuu_user_layout)
    RelativeLayout suiuuUserInfoLayout;

    /**
     * 取消订单
     */
    @Bind(R.id.order_details_cancel)
    Button orderDetailsCancel;

    /**
     * 申请退款
     */
    @Bind(R.id.order_details_apply_refund)
    Button orderDetailsApplyRefund;

    /**
     * 确认游玩
     */
    @Bind(R.id.order_details_confirm)
    Button orderDetailsConfirmPlay;

    /**
     * 删除订单
     */
    @Bind(R.id.order_details_delete)
    Button orderDetailsDelete;

    /**
     * 支付/取消Layout
     */
    @Bind(R.id.order_details_bottom_1_layout)
    CardView bottom_1_layout;

    /**
     * 退款/确认Layout
     */
    @Bind(R.id.order_details_bottom_2_layout)
    CardView bottom_2_layout;

    /**
     * 删除订单Layout
     */
    @Bind(R.id.order_details_bottom_3_layout)
    CardView bottom_3_layout;

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

    private PublisherBaseEntity publisherBaseEntity;
    private InfoEntity infoEntity;
    private TripJsonInfo tripJsonInfo;
    private ContactEntity contactEntity;

    /**
     * 订单状态
     */
    private String status;

    private String failureImagePath;

    private JsonUtils jsonUtils;

    private ProgressDialog commitDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_order_details);

        StatusBarCompat.compat(this);
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

        jsonUtils = JsonUtils.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        commitDialog = new ProgressDialog(this);
        commitDialog.setMessage("正在提交请求，请稍候...");
        commitDialog.setCanceledOnTouchOutside(false);

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

        titleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralOrderDetailsActivity.this, SuiuuDetailsActivity.class);
                intent.putExtra(TRIP_ID, infoEntity.getTripId());
                if (publisherBaseEntity == null) {
                    intent.putExtra(USER_SIGN, "");
                    intent.putExtra(HEAD_IMG, "");
                } else {
                    intent.putExtra(USER_SIGN, publisherBaseEntity.getUserSign());
                    intent.putExtra(HEAD_IMG, publisherBaseEntity.getHeadImg());
                }
                intent.putExtra(CLASS_NAME, TAG);
                startActivity(intent);
            }
        });

        headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralOrderDetailsActivity.this, PersonalMainPagerActivity.class);
                intent.putExtra(USER_SIGN, publisherBaseEntity.getUserSign());
                startActivity(intent);
            }
        });

        chatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publisherBaseEntity != null) {
                    String userSign = publisherBaseEntity.getUserSign();
                    String headImage = publisherBaseEntity.getHeadImg();

                    Intent intent = new Intent(GeneralOrderDetailsActivity.this, PrivateLetterChatActivity.class);
                    intent.putExtra(RELATE_ID, userSign);
                    intent.putExtra(HEAD_IMG, headImage);
                    startActivity(intent);
                }
            }
        });

        //重新支付按钮
        orderDetailsRepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralOrderDetailsActivity.this, SuiuuPayActivity.class);
                intent.putExtra(PEOPLE_NUMBER, infoEntity.getPersonCount());
                intent.putExtra(TIME, infoEntity.getBeginDate());
                intent.putExtra(TOTAL_PRICE, infoEntity.getTotalPrice());
                intent.putExtra(DESTINATION, tripJsonInfo.getInfo().getTitle());
                intent.putExtra(ORDER_NUMBER, orderNumber);
                startActivity(intent);
                finish();
            }
        });

        //取消订单
        orderDetailsCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder();
            }
        });

        //申请退款
        orderDetailsApplyRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status) {
                    case "1":
                        userApplyRefund();
                        break;

                    case "2":
                        userApplyRefundMessage();
                        break;
                }
            }
        });

        //确认游玩
        orderDetailsConfirmPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userConfirmPlay();
            }
        });

        //删除订单
        orderDetailsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOrder();
            }
        });

    }

    /**
     * 取消订单
     */
    private void cancelOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", infoEntity.getOrderId());
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.userCancelOrder + "?token=" + token, new CancelOrderCallBack(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 申请退款(不需要填写理由)
     */
    private void userApplyRefund() {
        if (commitDialog != null && !commitDialog.isShowing()) {
            commitDialog.show();
        }

        OkHttpManager.Params params = new OkHttpManager.Params("orderId", infoEntity.getOrderId());
        try {
            String url = HttpNewServicePath.userApplyRefundPath1 + "?" + TOKEN + "=" + token;
            OkHttpManager.onPostAsynRequest(url, new OkHttpManager.ResultCallback<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        String status = object.getString(STATUS);
                        switch (status) {
                            case "1":
                                Toast.makeText(GeneralOrderDetailsActivity.this, "申请退款成功！", Toast.LENGTH_SHORT).show();
                                getGeneralUserOrderData4Service();
                                break;

                            case "-1":
                                Toast.makeText(GeneralOrderDetailsActivity.this, SystemException, Toast.LENGTH_SHORT).show();
                                break;

                            case "-2":
                                Toast.makeText(GeneralOrderDetailsActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
                                break;

                        }
                    } catch (Exception ex) {
                        L.e(TAG, "数据解析失败:" + ex.getMessage());
                    }
                }

                @Override
                public void onError(Request request, Exception e) {
                    Toast.makeText(GeneralOrderDetailsActivity.this, "申请退款失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                    L.e(TAG, "申请退款网络请求失败:" + e.getMessage());
                }

                @Override
                public void onFinish() {
                    hideDialog();
                }

            }, params);
        } catch (Exception e) {
            hideDialog();
            L.e(TAG, "申请退款网络请求异常:" + e.getMessage());
            Toast.makeText(GeneralOrderDetailsActivity.this, "申请退款失败，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 申请退款(需要填写理由)
     */
    private void userApplyRefundMessage() {
        if (commitDialog != null && !commitDialog.isShowing()) {
            commitDialog.show();
        }

        final EditText editText = new EditText(this);
        editText.setHint("请输入退款理由");

        new AlertDialog.Builder(GeneralOrderDetailsActivity.this)
                .setView(editText)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (commitDialog != null && !commitDialog.isShowing()) {
                            commitDialog.show();
                        }

                        String strMessage = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(strMessage)) {
                            Toast.makeText(GeneralOrderDetailsActivity.this, "退款理由不能为空！", Toast.LENGTH_SHORT).show();
                        } else {
                            OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[2];
                            paramsArray[0] = new OkHttpManager.Params("orderId", infoEntity.getOrderId());
                            paramsArray[1] = new OkHttpManager.Params("message", strMessage);

                            try {
                                String url = HttpNewServicePath.userApplyRefundPath2 + "?" + TOKEN + "=" + token;
                                OkHttpManager.onPostAsynRequest(url, new OkHttpManager.ResultCallback<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject object = new JSONObject(response);
                                            String status = object.getString(STATUS);
                                            switch (status) {
                                                case "1":
                                                    Toast.makeText(GeneralOrderDetailsActivity.this, "申请退款成功！", Toast.LENGTH_SHORT).show();
                                                    getGeneralUserOrderData4Service();
                                                    break;

                                                case "-1":
                                                    Toast.makeText(GeneralOrderDetailsActivity.this, SystemException, Toast.LENGTH_SHORT).show();
                                                    break;

                                                case "-2":
                                                    Toast.makeText(GeneralOrderDetailsActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
                                                    break;

                                            }
                                        } catch (Exception ex) {
                                            L.e(TAG, "数据解析失败:" + ex.getMessage());
                                        }
                                    }

                                    @Override
                                    public void onError(Request request, Exception e) {
                                        Toast.makeText(GeneralOrderDetailsActivity.this, "申请退款失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                                        L.e(TAG, "申请退款网络请求失败:" + e.getMessage());
                                    }

                                    @Override
                                    public void onFinish() {
                                        hideDialog();
                                    }

                                }, paramsArray);
                            } catch (Exception e) {
                                hideDialog();
                                L.e(TAG, "申请退款网络请求异常:" + e.getMessage());
                                Toast.makeText(GeneralOrderDetailsActivity.this, "申请退款失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create().show();
    }

    /**
     * 确认游玩
     */
    private void userConfirmPlay() {
        if (commitDialog != null && !commitDialog.isShowing()) {
            commitDialog.show();
        }

        try {
            String url = HttpNewServicePath.userComfirmPlayPath + "?" + TOKEN + "=" + token;
            OkHttpManager.onPostAsynRequest(url, new OkHttpManager.ResultCallback<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        String status = object.getString(STATUS);
                        switch (status) {
                            case "1":
                                Toast.makeText(GeneralOrderDetailsActivity.this, "确认游玩成功！", Toast.LENGTH_SHORT).show();
                                getGeneralUserOrderData4Service();
                                break;

                            case "-1":
                                Toast.makeText(GeneralOrderDetailsActivity.this, SystemException, Toast.LENGTH_SHORT).show();
                                break;

                            case "-2":
                                Toast.makeText(GeneralOrderDetailsActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
                                break;

                        }
                    } catch (Exception ex) {
                        L.e(TAG, "确认游玩数据解析失败:" + ex.getMessage());
                        Toast.makeText(GeneralOrderDetailsActivity.this, "确认游玩错误，请稍候再试！", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Request request, Exception e) {
                    L.e(TAG, "确认游玩错误:" + e.getMessage());
                    Toast.makeText(GeneralOrderDetailsActivity.this, "确认游玩错误，请稍候再试！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinish() {
                    hideDialog();
                }

            }, new OkHttpManager.Params("orderId", infoEntity.getOrderId()));
        } catch (Exception e) {
            hideDialog();
            L.e(TAG, "确认游玩异常:" + e.getMessage());
            Toast.makeText(GeneralOrderDetailsActivity.this, "确认游玩异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 删除订单
     */
    private void deleteOrder() {
        if (commitDialog != null && !commitDialog.isShowing()) {
            commitDialog.show();
        }

        Map<String, String> map = new HashMap<>();
        map.put("orderId", infoEntity.getOrderId());
        try {
            String url = HttpNewServicePath.userDeleteOrder + "?" + TOKEN + "=" + token;
            L.i(TAG, "删除订单URL:" + url);
            OkHttpManager.onPostAsynRequest(url, new OkHttpManager.ResultCallback<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        String status = object.getString(STATUS);
                        switch (status) {
                            case "1":
                                Toast.makeText(GeneralOrderDetailsActivity.this, "删除订单成功！", Toast.LENGTH_SHORT).show();
                                finish();
                                break;

                            case "-1":
                                Toast.makeText(GeneralOrderDetailsActivity.this, SystemException, Toast.LENGTH_SHORT).show();
                                break;

                            case "-2":
                                Toast.makeText(GeneralOrderDetailsActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (Exception e) {
                        L.e(TAG, "删除订单数据解析异常:" + e.getMessage());
                    }
                }

                @Override
                public void onError(Request request, Exception e) {
                    L.e(TAG, "删除订单错误:" + e.getMessage());
                    Toast.makeText(GeneralOrderDetailsActivity.this, "订单删除出现错误，请稍候再试", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinish() {
                    hideDialog();
                }

            }, map);
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(GeneralOrderDetailsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 得到订单数据
     */
    private void getGeneralUserOrderData4Service() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
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

    /**
     * 隐藏进度框
     */
    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (commitDialog != null && commitDialog.isShowing()) {
            commitDialog.dismiss();
        }

    }

    /**
     * 数据绑定到View
     *
     * @param str 订单数据
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(this, NoData, Toast.LENGTH_SHORT).show();
        } else {
            if (serviceLayout.getChildCount() > 0) {
                serviceLayout.removeAllViews();
                serviceLayout.addView(serviceTitleLayout);
            }
            try {
                JSONObject object = new JSONObject(str);
                String status = object.getString(STATUS);
                switch (status) {
                    case "1":
                        GeneralOrderDetails details = null;
                        try {
                            details = jsonUtils.fromJSON(GeneralOrderDetails.class, str);
                        } catch (Exception e) {
                            L.e(TAG, "数据解析失败:" + e.getMessage());
                        } finally {
                            if (details != null) {
                                GeneralOrderDetails.GeneralOrderDetailsData detailsData = details.getData();
                                publisherBaseEntity = detailsData.getPublisherBase();
                                infoEntity = detailsData.getInfo();
                                contactEntity = detailsData.getContact();

                                showOrderInfo();
                                showMyInfo();
                                showPersonalInfo();
                            }
                        }
                        break;

                    case "-1":
                        Toast.makeText(GeneralOrderDetailsActivity.this, SystemException, Toast.LENGTH_SHORT).show();
                        break;

                    case "-2":
                        Toast.makeText(GeneralOrderDetailsActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (Exception e) {
                L.e(TAG, "数据绑定异常:" + e.getLocalizedMessage());
                Toast.makeText(GeneralOrderDetailsActivity.this, DataError, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 显示订单信息
     */
    private void showOrderInfo() {
        if (infoEntity != null) {
            String jsonInfo = infoEntity.getTripJsonInfo();
            if (!TextUtils.isEmpty(jsonInfo)) {
                try {
                    tripJsonInfo = jsonUtils.fromJSON(TripJsonInfo.class, jsonInfo);
                    if (tripJsonInfo != null) {
                        String title = tripJsonInfo.getInfo().getTitle();//标题
                        if (!TextUtils.isEmpty(title)) {
                            orderDetailsTitle.setText(title);
                        }

                        String strOrderScore = tripJsonInfo.getInfo().getScore(); //星级评价
                        if (!TextUtils.isEmpty(strOrderScore)) {
                            float score = Float.valueOf(strOrderScore);
                            orderStarIndicator.setRating(score);
                        } else {
                            orderStarIndicator.setRating(DEFAULT_SCORE);
                        }

                        String strOrderCreateTime = infoEntity.getCreateTime();//订单创建时间
                        if (!TextUtils.isEmpty(strOrderCreateTime)) {
                            orderCreateTime.setText(strOrderCreateTime);
                        }

                        String strOrderNumber = infoEntity.getOrderNumber();//订单号
                        if (!TextUtils.isEmpty(strOrderNumber)) {
                            orderNumberView.setText(strOrderNumber);
                        }

                    }
                } catch (Exception exception) {
                    L.e(TAG, "旅图数据解析失败:" + exception.getMessage());
                }
            } else {
                orderDetailsTitle.setText("");
                orderStarIndicator.setRating(DEFAULT_SCORE);
            }

            String basePrice = infoEntity.getBasePrice();//订单基础金额
            if (!TextUtils.isEmpty(basePrice)) {
                baseOrderPrice = Float.valueOf(basePrice);
                baseOrderPriceView.setText(String.format("%s%s", "￥", basePrice));
            }

            String beginDate = infoEntity.getBeginDate();//开始日期
            if (!TextUtils.isEmpty(beginDate)) {
                orderDetailsDate.setText(beginDate);
            }

            String startTime = infoEntity.getStartTime(); //开始时间
            if (!TextUtils.isEmpty(startTime)) {
                orderDetailsTime.setText(startTime);
            }

            String personCount = infoEntity.getPersonCount();//随游人数
            if (!TextUtils.isEmpty(personCount)) {
                orderDetailsSuiuuNumber.setText(String.format("%s%s", personCount, "人"));
            }

            String strServiceInfo = infoEntity.getServiceInfo();
            bindAdditionalServices(strServiceInfo); //附加服务列表

            TextView totalOrderPriceView = (TextView) inflater.inflate(R.layout.item_service_location_layout2, serviceLayout, false);
            totalOrderPriceView.setText(String.format("%s%s", "总价￥", totalOrderPrice));//订单总价
            serviceLayout.addView(totalOrderPriceView);

            status = infoEntity.getStatus();//订单状态
            showOrderStatus(status);
            showBottomLayout(status);
        } else {
            orderDetailsTitle.setText("");
            orderStarIndicator.setRating(DEFAULT_SCORE);
            baseOrderPriceView.setText("");
            orderDetailsDate.setText("");
            orderDetailsTime.setText("");
            orderDetailsSuiuuNumber.setText("0人");
            orderStatus.setText("订单状态未知");
        }
    }

    /**
     * 绑定附加服务
     *
     * @param strServiceInfo 附加服务Json字符串
     */
    private void bindAdditionalServices(String strServiceInfo) {
        if (!TextUtils.isEmpty(strServiceInfo)) {
            if (!strServiceInfo.equals("[]")) {
                try {
                    List<ServiceInfo> serviceInfoList = jsonUtils.fromJSON(new TypeToken<List<ServiceInfo>>() {
                    }.getType(), strServiceInfo);
                    if (serviceInfoList != null && serviceInfoList.size() > 0) {
                        float allServicePrice = 0f;//所有附加服务的总价
                        for (int i = 0; i < serviceInfoList.size(); i++) {
                            View serviceItemLayout = inflater.inflate(R.layout.item_service_location_layout, serviceLayout, false);
                            TextView serviceNameView = (TextView) serviceItemLayout.findViewById(R.id.order_details_service_name);
                            TextView servicePriceView = (TextView) serviceItemLayout.findViewById(R.id.order_details_service_prices);

                            String serviceItemTitle = serviceInfoList.get(i).getTitle();//单项附加服务名称
                            if (!TextUtils.isEmpty(serviceItemTitle)) {
                                serviceNameView.setText(serviceItemTitle);
                            }

                            String strServiceItemPrice = serviceInfoList.get(i).getMoney();//单项附加服务价格
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
                    }
                } catch (Exception ex) {
                    L.e(TAG, "附加服务数据解析失败:" + ex.getMessage());
                }
            } else {
                totalOrderPrice = baseOrderPrice;
            }
        }
    }

    /**
     * 显示订单状态
     *
     * @param status 订单状态
     */
    private void showOrderStatus(String status) {
        if (!TextUtils.isEmpty(status)) {
            switch (status) {
                case "0":
                    orderStatus.setText("待支付");
                    bottom_1_layout.setVisibility(View.VISIBLE);
                    break;

                case "1"://等待随友接单
                    orderStatus.setText("已支付 待确认");
                    break;

                case "2":
                    orderStatus.setText("已支付 已确认");
                    break;

                case "3":
                    bottom_1_layout.setVisibility(View.VISIBLE);
                    orderDetailsCancel.setVisibility(View.GONE);
                    orderDetailsRepay.setText(DeleteOrder);
                    orderStatus.setText("未支付 已取消");
                    break;

                case "4":
                    orderStatus.setText("待退款");
                    break;

                case "5":
                    bottom_1_layout.setVisibility(View.VISIBLE);
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
    }

    /**
     * 显示底部按钮布局
     *
     * @param status 订单状态
     */
    private void showBottomLayout(String status) {
        if (!TextUtils.isEmpty(status)) {
            switch (status) {
                case "0"://待支付
                    bottom_1_layout.setVisibility(View.VISIBLE);
                    bottom_2_layout.setVisibility(View.GONE);
                    bottom_3_layout.setVisibility(View.GONE);
                    suiuuUserInfoLayout.setVisibility(View.GONE);
                    break;

                case "1"://已支付 待确认 等待随友接单
                    bottom_1_layout.setVisibility(View.GONE);
                    bottom_2_layout.setVisibility(View.VISIBLE);
                    bottom_3_layout.setVisibility(View.GONE);
                    orderDetailsConfirmPlay.setVisibility(View.INVISIBLE);
                    suiuuUserInfoLayout.setVisibility(View.GONE);
                    break;

                case "2"://已支付 已确认
                    bottom_1_layout.setVisibility(View.GONE);
                    bottom_2_layout.setVisibility(View.VISIBLE);
                    bottom_3_layout.setVisibility(View.GONE);
                    break;

                case "3"://未支付 已取消
                    bottom_1_layout.setVisibility(View.GONE);
                    bottom_2_layout.setVisibility(View.GONE);
                    bottom_3_layout.setVisibility(View.VISIBLE);
                    suiuuUserInfoLayout.setVisibility(View.GONE);
                    break;

                case "4"://待退款
                    bottom_1_layout.setVisibility(View.GONE);
                    bottom_2_layout.setVisibility(View.VISIBLE);
                    bottom_3_layout.setVisibility(View.GONE);
                    orderDetailsApplyRefund.setEnabled(false);
                    orderDetailsConfirmPlay.setEnabled(false);
                    break;

                case "5"://退款成功
                    bottom_1_layout.setVisibility(View.GONE);
                    bottom_2_layout.setVisibility(View.GONE);
                    bottom_3_layout.setVisibility(View.VISIBLE);
                    break;

                case "6"://游玩结束 待付款给随友
                    bottom_1_layout.setVisibility(View.GONE);
                    bottom_2_layout.setVisibility(View.GONE);
                    bottom_3_layout.setVisibility(View.VISIBLE);
                    break;

                case "7"://结束，已经付款给随友
                    bottom_1_layout.setVisibility(View.GONE);
                    bottom_2_layout.setVisibility(View.GONE);
                    bottom_3_layout.setVisibility(View.VISIBLE);
                    break;

                case "8"://退款审核中
                    bottom_1_layout.setVisibility(View.GONE);
                    bottom_2_layout.setVisibility(View.VISIBLE);
                    bottom_3_layout.setVisibility(View.GONE);
                    orderDetailsApplyRefund.setEnabled(false);
                    orderDetailsConfirmPlay.setEnabled(false);
                    break;

                case "9"://退款审核失败
                    bottom_1_layout.setVisibility(View.GONE);
                    bottom_2_layout.setVisibility(View.GONE);
                    bottom_3_layout.setVisibility(View.VISIBLE);
                    break;

                case "10"://随友取消订单
                    bottom_1_layout.setVisibility(View.GONE);
                    bottom_2_layout.setVisibility(View.GONE);
                    bottom_3_layout.setVisibility(View.VISIBLE);
                    break;

                default:
                    bottom_1_layout.setVisibility(View.VISIBLE);
                    bottom_2_layout.setVisibility(View.GONE);
                    bottom_3_layout.setVisibility(View.GONE);
                    suiuuUserInfoLayout.setVisibility(View.GONE);
                    break;
            }
        } else {
            bottom_1_layout.setVisibility(View.VISIBLE);
            bottom_2_layout.setVisibility(View.GONE);
            bottom_3_layout.setVisibility(View.GONE);
            suiuuUserInfoLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 显示用户信息
     */
    private void showMyInfo() {
        if (contactEntity != null) {
            String strMainContact = contactEntity.getUsername(); //主要联系人
            if (!TextUtils.isEmpty(strMainContact)) {
                mainContact.setText(strMainContact);
            }

            String strWeChatNumber = contactEntity.getWechat();//微信号
            if (!TextUtils.isEmpty(strWeChatNumber)) {
                weChatNumber.setText(strWeChatNumber);
            }

            String strContactNumber = contactEntity.getPhone();//联系号码
            if (!TextUtils.isEmpty(strContactNumber)) {
                ContactNumber.setText(strContactNumber);
            }

            String strStandbyContactNumber = contactEntity.getSparePhone(); //备用联系号码
            if (!TextUtils.isEmpty(strStandbyContactNumber)) {
                standbyContactNumber.setText(strStandbyContactNumber);
            }

            String strEmergencyContact = contactEntity.getUrgentUsername();//紧急联系人
            if (!TextUtils.isEmpty(strEmergencyContact)) {
                emergencyContact.setText(strEmergencyContact);
            }

            String strEmergencyContactNumber = contactEntity.getUrgentPhone(); //紧急联系号码
            if (!TextUtils.isEmpty(strEmergencyContactNumber)) {
                emergencyContactNumber.setText(strEmergencyContactNumber);
            }

        }
    }

    private void showPersonalInfo() {
        try {
            if (publisherBaseEntity != null) {
                String headImagePath = publisherBaseEntity.getHeadImg();//用户头像
                if (!TextUtils.isEmpty(headImagePath)) {
                    headImageView.setImageURI(Uri.parse(headImagePath));
                } else {
                    headImageView.setImageURI(Uri.parse(failureImagePath));
                }

                String nickName = publisherBaseEntity.getNickname();//用户昵称
                if (!TextUtils.isEmpty(nickName)) {
                    userName.setText(nickName);
                }

                String phoneNumber = publisherBaseEntity.getPhone();//电话号码
                if (!TextUtils.isEmpty(phoneNumber)) {
                    orderDetailsPhone.setText(phoneNumber);
                }
            } else {
                userName.setText("");
                orderDetailsPhone.setText("");
                headImageView.setImageURI(Uri.parse(failureImagePath));
            }
        } catch (Exception e) {
            L.e(TAG, "个人数据显示异常:" + e.getLocalizedMessage());
        }
    }

    private class OrderDetailsResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "返回的数据:" + response);
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Exception:" + e.getMessage());
            Toast.makeText(GeneralOrderDetailsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }

    }

    private class CancelOrderCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            Log.i(TAG, "取消订单异常" + e.getMessage());
        }

        @Override
        public void onResponse(String response) {
            try {
                JSONObject result = new JSONObject(response);
                int results = (int) result.get(STATUS);
                switch (results) {
                    case 1:
                        Toast.makeText(GeneralOrderDetailsActivity.this, R.string.CancelOrderSuccess, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        Toast.makeText(GeneralOrderDetailsActivity.this, R.string.CancelOrderFailure, Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}