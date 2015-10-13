package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.SuiuuDetailsCommentAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.entity.SuiuuDetailsData;
import com.minglang.suiuu.entity.SuiuuDetailsData.DataEntity.CommentEntity.CommentDataEntity;
import com.minglang.suiuu.entity.SuiuuDetailsData.DataEntity.ServiceListEntity;
import com.minglang.suiuu.entity.UserBack.UserBackData;
import com.minglang.suiuu.fragment.mysuiuu.ParticipateFragment;
import com.minglang.suiuu.fragment.mysuiuu.PublishedFragment;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.minglang.suiuu.utils.qq.TencentConstant;
import com.minglang.suiuu.utils.wechat.WeChatConstant;
import com.squareup.okhttp.Request;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 项目名称：Suiuu
 * 类描述：suiuu的详细列表
 * 创建人：Administrator
 * 创建时间：2015/5/4 19:13
 * 修改人：Administrator
 * 修改时间：2015/5/4 19:13
 * 修改备注：
 */
public class SuiuuDetailsActivity extends BaseAppCompatActivity {

    private static final String TAG = SuiuuDetailsActivity.class.getSimpleName();

    private static final int COMMENT_SUCCESS = 20;

    private static final String TRIP_ID = "tripId";
    private static final String R_ID = "rId";
    private static final String NICK_NAME = "nickName";

    private static final String CONTENT = "content";
    private static final String HEAD_IMG = "headImg";
    private static final String NICK_NAME_ = "nickname";

    private static final String CLASS_NAME = "className";

    @BindString(R.string.LoginInvalid)
    String LoginInvalid;

    @BindString(R.string.DataRequestFailure)
    String DataRequestFailure;

    @BindString(R.string.SystemException)
    String SystemException;

    private String tripId;

    @Bind(R.id.suiuu_detail_web_view)
    WebView mWebView;

    private ProgressDialog progressDialog = null;

    @Bind(R.id.rl_suiuu_detail)
    RelativeLayout rl_suiuu_detail;

    @Bind(R.id.suiuu_details_back)
    ImageView back;

    //显示评论总数
    @Bind(R.id.suiuu_details_comment_number)
    TextView suiuuDetailsCommentNumber;

    //评论头像
    @Bind(R.id.sdv_comment_head_img)
    SimpleDraweeView commentHeadImageView;

    //评论输入框
    @Bind(R.id.suiuu_details_comment)
    EditText inputSuiuuCommentView;

    //评论显示列表
    @Bind(R.id.suiuu_details_comment_list_view)
    NoScrollBarListView suiuuDetailsCommentListView;

    /**
     * 咨询按钮
     */
    @Bind(R.id.suiuu_details_advisory)
    Button AdvisoryButton;

    /**
     * 预订按钮
     */
    @Bind(R.id.suiuu_details_booking)
    Button ReservationsButton;

    private SuiuuDetailsData detailsData;

    @Bind(R.id.to_comment_activity)
    TextView to_comment_activity;

    @Bind(R.id.suiuu_details_no_comment)
    LinearLayout suiuu_detail_no_comment;

    @Bind(R.id.suiuu_details_input_comment)
    LinearLayout suiuu_detail_input_comment;

    @Bind(R.id.suiuu_details_share)
    ImageView ShareSuiuuDetails;

    @Bind(R.id.suiuu_details_bottom_layout)
    RelativeLayout bottomLayout;

    private SuiuuDetailsCommentAdapter adapter;

    WebSettings webSettings;

    /**
     * 评论集合
     */
    private List<CommentDataEntity> listAll = new ArrayList<>();

    private AlertDialog setTagDialog;

    private String headImagePath;

    private List<ServiceListEntity> serviceList;

    private String[] serviceNameArray;
    private String[] serviceIdArray;
    private String[] servicePriceArray;

    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suiuu_details_activity);
        ButterKnife.bind(this);
        initView();
        getSuiuuDetailsData(tripId);
        showWebView();
        viewAction();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        tripId = getIntent().getStringExtra(TRIP_ID);
        userSign = getIntent().getStringExtra(USER_SIGN);
        headImagePath = getIntent().getStringExtra(HEAD_IMG);
        String className = getIntent().getStringExtra(CLASS_NAME);

        if (className.equals(ParticipateFragment.class.getSimpleName())) {
            bottomLayout.setVisibility(View.GONE);
        } else if (className.equals(PublishedFragment.class.getSimpleName())) {
            bottomLayout.setVisibility(View.GONE);
        } else {
            bottomLayout.setVisibility(View.VISIBLE);
        }

        //微博SSO
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this, WeChatConstant.APP_ID, WeChatConstant.APPSECRET);
        wxHandler.addToSocialSDK();

        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this, WeChatConstant.APP_ID, WeChatConstant.APPSECRET);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        //QQ好友
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, TencentConstant.APP_ID, TencentConstant.APP_KEY);
        qqSsoHandler.addToSocialSDK();

        mController.setShareContent("http://www.suiuu.com/view-trip/info?trip=" + tripId);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();

        webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        adapter = new SuiuuDetailsCommentAdapter(this, listAll);
        suiuuDetailsCommentListView.setAdapter(adapter);
    }

    private void viewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        commentHeadImageView.setOnClickListener(new MyOnClickListener());

        inputSuiuuCommentView.setOnClickListener(new MyOnClickListener());

        to_comment_activity.setOnClickListener(new MyOnClickListener());

        AdvisoryButton.setOnClickListener(new MyOnClickListener());

        ReservationsButton.setOnClickListener(new MyOnClickListener());

        suiuuDetailsCommentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SuiuuDetailsActivity.this, SendCommentActivity.class);
                intent.putExtra(TRIP_ID, tripId);
                intent.putExtra(R_ID, listAll.get(position).getCommentId());
                intent.putExtra(NICK_NAME, listAll.get(position).getNickname());
                startActivityForResult(intent, COMMENT_SUCCESS);

            }
        });

        ShareSuiuuDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.openShare(SuiuuDetailsActivity.this, false);
            }
        });

    }

    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
    private void showWebView() {
        // webView与js交互代码
        try {
            mWebView.requestFocus();

            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int progress) {
                    progressDialog.setMessage("已经加载" + progress + "%");
                    if (progress >= 85) {
                        rl_suiuu_detail.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                }
            });

            mWebView.setOnKeyListener(new View.OnKeyListener() {// webView can go back
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                    return false;
                }
            });

            mWebView.addJavascriptInterface(getHtmlObject(), "jsObj");

            mWebView.loadUrl("http://www.suiuu.com/app-travel/get-travel-info?trId=" + tripId + "&token=" + token);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getHtmlObject() {
        Object insertObj = new Object() {

            @JavascriptInterface
            public void jsAlert(String s) {
                Toast.makeText(SuiuuDetailsActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @JavascriptInterface
            public void showMask() {
                showSetTagDialog();
            }

            @JavascriptInterface
            public void userHomePage(String user) {
                Intent intent2UserActivity = new Intent(SuiuuDetailsActivity.this, PersonalMainPagerActivity.class);
                intent2UserActivity.putExtra("userSign", user);
                startActivity(intent2UserActivity);
            }

        };
        return insertObj;
    }

    //访问网络
    private void getSuiuuDetailsData(String tripId) {
        String[] keyArray1 = new String[]{TRIP_ID, "token"};
        String[] valueArray1 = new String[]{tripId, token};
        String url = addUrlAndParams(HttpNewServicePath.getSuiuuItemInfo, keyArray1, valueArray1);
        L.i(TAG, "请求URL:" + url);
        try {
            OkHttpManager.onGetAsynRequest(url, new SuiuuItemInfoCallBack());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求数据网络接口回调
     */
    private class SuiuuItemInfoCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String str) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                String status = jsonObject.getString(STATUS);

                switch (status) {
                    case "1":
                        detailsData = JsonUtils.getInstance().fromJSON(SuiuuDetailsData.class, str);
                        listAll = detailsData.getData().getComment().getData();
                        serviceList = detailsData.getData().getServiceList();
                        fullCommentList();
                        serviceNameArray = getServiceNameArray();
                        serviceIdArray = getServiceIdArray();
                        servicePriceArray = getServicePriceArray();

                        if (serviceNameArray != null) {
                            L.i(TAG, "ServiceName Array:" + Arrays.toString(serviceNameArray));
                        }

                        if (serviceIdArray != null) {
                            L.i(TAG, "ServiceId Array:" + Arrays.toString(serviceIdArray));
                        }

                        if (servicePriceArray != null) {
                            L.i(TAG, "ServicePrice Array:" + Arrays.toString(servicePriceArray));
                        }

                        break;

                    case "-1":
                        Toast.makeText(SuiuuDetailsActivity.this, SystemException, Toast.LENGTH_SHORT).show();
                        break;

                    case "-2":
                        Toast.makeText(SuiuuDetailsActivity.this, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        break;

                    case "-3":
                        Toast.makeText(SuiuuDetailsActivity.this, LoginInvalid, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SuiuuDetailsActivity.this, FirstLoginActivity.class);
                        SuiuuDetailsActivity.this.startActivity(intent);
                        SuiuuDetailsActivity.this.finish();
                        break;

                    default:
                        Toast.makeText(SuiuuDetailsActivity.this, DataRequestFailure, Toast.LENGTH_SHORT).show();
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(SuiuuDetailsActivity.this, DataRequestFailure, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(SuiuuDetailsActivity.this, DataRequestFailure, Toast.LENGTH_SHORT).show();
        }

    }

    public void fullCommentList() {
        if (listAll != null && listAll.size() > 0) {
            suiuuDetailsCommentNumber.setText(String.format("%s%s%s", "全部评论 (共", listAll.size(), "条评论)"));
            suiuu_detail_input_comment.setVisibility(View.VISIBLE);
            suiuu_detail_no_comment.setVisibility(View.GONE);
            //showList(listAll);
            adapter.setList(listAll);
        } else {
            suiuu_detail_no_comment.setVisibility(View.VISIBLE);
            suiuu_detail_input_comment.setVisibility(View.GONE);
        }
    }

    /**
     * 得到单项服务Title数组
     *
     * @return 单项服务Title数组
     */
    private String[] getServiceNameArray() {
        String[] serviceNameArray;
        if (serviceList != null && serviceList.size() > 0) {
            serviceNameArray = new String[serviceList.size()];

            for (int i = 0; i < serviceList.size(); i++) {
                serviceNameArray[i] = serviceList.get(i).getTitle();
            }

            return serviceNameArray;
        } else {
            return null;
        }
    }

    /**
     * 得到单项服务ID数组
     *
     * @return 单项服务ID数组
     */
    private String[] getServiceIdArray() {
        String[] serviceIdArray;
        if (serviceList != null && serviceList.size() > 0) {
            serviceIdArray = new String[serviceList.size()];

            for (int i = 0; i < serviceList.size(); i++) {
                serviceIdArray[i] = serviceList.get(i).getServiceId();
            }

            return serviceIdArray;
        } else {
            return null;
        }
    }

    /**
     * 得到单项服务价格数组
     *
     * @return 单项服务价格数组
     */
    private String[] getServicePriceArray() {
        String[] servicePriceArray;
        if (serviceList != null && serviceList.size() > 0) {
            servicePriceArray = new String[serviceList.size()];

            for (int i = 0; i < serviceList.size(); i++) {
                servicePriceArray[i] = serviceList.get(i).getMoney();
            }

            return servicePriceArray;
        } else {
            return null;
        }
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.sdv_comment_head_img:
                    //跳到个人中心
                    break;

                case R.id.suiuu_details_comment:
                    //跳到评论页
                    Intent intent = new Intent(SuiuuDetailsActivity.this, SendCommentActivity.class);
                    intent.putExtra(TRIP_ID, tripId);
                    startActivityForResult(intent, COMMENT_SUCCESS);
                    break;

                case R.id.to_comment_activity:
                    //跳到评论页
                    Intent commentIntent = new Intent(SuiuuDetailsActivity.this, SendCommentActivity.class);
                    commentIntent.putExtra(TRIP_ID, tripId);
                    startActivityForResult(commentIntent, COMMENT_SUCCESS);
                    break;

                case R.id.suiuu_details_advisory:
                    //跳到会话页面
                    Intent intentChat = new Intent(SuiuuDetailsActivity.this, PrivateLetterChatActivity.class);
                    intentChat.putExtra("relateId", userSign);
                    intentChat.putExtra(HEAD_IMG, headImagePath);
                    startActivity(intentChat);
                    break;

                case R.id.suiuu_details_booking:
                    //跳到预订页面
                    Intent intentSchedule = new Intent(SuiuuDetailsActivity.this, SuiuuOrderConfirmActivity.class);
                    intentSchedule.putExtra("titleInfo", detailsData.getData().getInfo().getTitle());
                    intentSchedule.putExtra("titleImg", detailsData.getData().getInfo().getTitleImg());
                    intentSchedule.putExtra("price", detailsData.getData().getInfo().getBasePrice());
                    intentSchedule.putExtra("tripId", detailsData.getData().getInfo().getTripId());
                    intentSchedule.putExtra("serviceName", serviceNameArray);
                    intentSchedule.putExtra("serviceId", serviceIdArray);
                    intentSchedule.putExtra("servicePrice", servicePriceArray);
                    intentSchedule.putExtra("serviceListSize", serviceList.size() > 0 ? serviceList.size() : 0);
                    startActivity(intentSchedule);
                    break;

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

        if (data != null && resultCode == COMMENT_SUCCESS) {
            UserBackData userBackData = SuiuuInfo.ReadUserData(this);
            String content = data.getStringExtra(CONTENT);

            JSONObject json = new JSONObject();
            try {
                json.put(HEAD_IMG, userBackData.getHeadImg());
                json.put(NICK_NAME_, userBackData.getNickname());
                json.put(CONTENT, content);

                CommentDataEntity newCommentData = JsonUtils.getInstance().fromJSON(CommentDataEntity.class, json.toString());
                listAll.add(0, newCommentData);
                fullCommentList();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 设置自定义标签弹出框
     */
    protected void showSetTagDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.suiuu_dialog, null);
        ImageView iv_cancel = (ImageView) view.findViewById(R.id.iv_suiuu_dialog_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTagDialog.dismiss();
            }
        });
        setTagDialog = builder.create();
        setTagDialog.setView(view, 0, 0, 0, 0);
        setTagDialog.show();
    }

}