package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.AreaCodeAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.entity.AreaCode;
import com.minglang.suiuu.entity.AreaCodeData;
import com.minglang.suiuu.entity.RequestData;
import com.minglang.suiuu.entity.UserBack;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.MD5Utils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.qq.TencentConstant;
import com.minglang.suiuu.utils.wechat.WeChatConstant;
import com.squareup.okhttp.Request;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 登录页面
 */
@SuppressWarnings("deprecation")
public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final String AREA_CODE = "areaCode";

    private static final String PHONE = "phone";
    private static final String PASSWORD = "password";
    private static final String C_PASSWORD = "cPassword";
    private static final String NICK = "nick";
    private static final String VALIDATE_CODE = "validateCode";

    private static final String STATUS = "status";

    private static final String USER_NAME = "username";

    private static final String OPEN_ID = "openId";
    private static final String OPEN_ID_ = "openid";
    private static final String NICK_NAME = "nickname";
    private static final String SEX = "sex";
    private static final String HEAD_IMG = "headImg";
    private static final String TYPE = "type";
    private static final String SIGN = "sign";

    private static final String DATA = "data";

    @BindString(R.string.login_wait)
    String loginMessage;

    @BindString(R.string.register_wait)
    String registerMessage;

    @BindString(R.string.User_name_cannot_be_empty)
    String userNameNotNull;

    @BindString(R.string.Password_cannot_be_empty)
    String passwordNotNull;

    @BindString(R.string.InternationalCodeFailure)
    String repeatAreaCode;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkAnomaly;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.QQAuthorizedComplete)
    String QQAuthorizedComplete;

    @BindString(R.string.QQAuthorizedError)
    String QQAuthorizedError;

    @BindString(R.string.QQAuthorizedCancel)
    String QQAuthorizedCancel;

    @BindString(R.string.WeChatAuthorizedComplete)
    String WeChatAuthorizedComplete;

    @BindString(R.string.WeChatAuthorizedError)
    String WeChatAuthorizedError;

    @BindString(R.string.WeChatAuthorizedCancel)
    String WeChatAuthorizedCancel;

    @BindString(R.string.WeiboAuthorizedStart)
    String WeiboAuthorizedStart;

    @BindString(R.string.WeiboAuthorizedComplete)
    String WeiboAuthorizedComplete;

    @BindString(R.string.WeiboAuthorizedFailure)
    String WeiboAuthorizedFailure;

    @BindString(R.string.WeiboAuthorizedError)
    String WeiboAuthorizedError;

    @BindString(R.string.WeiboAuthorizedCancel)
    String WeiboAuthorizedCancel;

    @Bind(R.id.loginBtn)
    Button loginBtn;

    @Bind(R.id.registerBtn)
    Button registerBtn;

    //登陆PopupWindow
    private View loginRootView;

    private PopupWindow popupWindowLogin;

    private EditText popupLoginUserName;

    private EditText popupLoginPassword;

    private Button popupLoginBtn;

    //注册PopupWindow1
    private View popupRegisterView1;

    private PopupWindow popupWindowRegister1;

    private Spinner popupRegister1Spinner;

    private EditText popupRegister1PhoneNumber;

    private Button popupRegister1ObtainCaptcha;

    private AreaCodeAdapter areaCodeAdapter;

    //注册PopupWindow2
    private View popupRegisterView2;

    private PopupWindow popupWindowRegister2;

    private EditText editCaptcha;

    private EditText editPassWord;

    private EditText editConfirmPassWord;

    private Button registerButton;

    /**
     * 国际电话区号数据集合
     */
    private List<AreaCodeData> areaCodeDataList;

    /**
     * 国家名称
     */
    private String areaName;

    /**
     * 国际电话区号
     */
    private String areaCode;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 登陆用户名
     */
    private String loginUserName;

    /**
     * 登陆密码
     */
    private String loginPassword;

    /**
     * 验证码
     */
    private String VerificationCode;

    /**
     * 注册密码
     */
    private String registerPassword1;

    /**
     * 确认密码
     */
    private String registerPassword2;
    /**
     * 第三方登陆按钮
     */
    @Bind(R.id.weibo_login)
    ImageView weiBoLogin;

    @Bind(R.id.qq_login)
    ImageView qqLogin;

    @Bind(R.id.weChat_login)
    ImageView weChatLogin;

    private IWXAPI weChatApi;

    /**
     * 第三方登陆类型
     */
    private String type;

    /**
     * 登陆进度框
     */
    private ProgressDialog loginDialog;

    /**
     * 注册进度框
     */
    private ProgressDialog registerDialog;

    private ProgressDialog weChatLoadDialog;

    private UMSocialService mController;

    private ProgressDialog tencentLoginDialog;

    @Bind(R.id.login_main_logo)
    ImageView loginMainLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        getAreaCode();
        viewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        initDialog();
        initThirdParty();
        initPopupWindow();
    }

    /**
     * 初始化Dialog
     */
    private void initDialog() {
        loginDialog = new ProgressDialog(this);
        loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loginDialog.setCanceledOnTouchOutside(false);
        loginDialog.setCancelable(true);
        loginDialog.setMessage(loginMessage);

        registerDialog = new ProgressDialog(this);
        registerDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        registerDialog.setCanceledOnTouchOutside(false);
        registerDialog.setCancelable(true);
        registerDialog.setMessage(registerMessage);

        tencentLoginDialog = new ProgressDialog(this);
        tencentLoginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        tencentLoginDialog.setCanceledOnTouchOutside(false);
        tencentLoginDialog.setMessage(loginMessage);

        weChatLoadDialog = new ProgressDialog(this);
        weChatLoadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        weChatLoadDialog.setCanceledOnTouchOutside(false);
        weChatLoadDialog.setCancelable(true);
        weChatLoadDialog.setMessage(loginMessage);
    }

    /**
     * 初始化第三方相关实例
     */
    private void initThirdParty() {
        weChatApi = WXAPIFactory.createWXAPI(this, WeChatConstant.APP_ID, false);
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
    }

    /**
     * 初始化PopupWindow
     */
    @SuppressLint("InflateParams")
    private void initPopupWindow() {
        loginRootView = LayoutInflater.from(this).inflate(R.layout.popup_login, null);

        popupLoginUserName = (EditText) loginRootView.findViewById(R.id.userName);
        popupLoginPassword = (EditText) loginRootView.findViewById(R.id.userPassword);
        popupLoginBtn = (Button) loginRootView.findViewById(R.id.popupLoginBtn);

        ViewGroup.LayoutParams loginParams = popupLoginBtn.getLayoutParams();
        loginParams.width = screenWidth / 3;
        popupLoginBtn.setLayoutParams(loginParams);

        popupWindowLogin = new PopupWindow(loginRootView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindowLogin.setBackgroundDrawable(new BitmapDrawable());


        popupRegisterView1 = LayoutInflater.from(this).inflate(R.layout.popup_register1, null);

        popupRegister1Spinner = (Spinner) popupRegisterView1.findViewById(R.id.popup_register1_international_area_code);
        areaCodeAdapter = new AreaCodeAdapter(this);
        areaCodeAdapter.setZhCNLanguage(isZhCnLanguage);
        popupRegister1Spinner.setAdapter(areaCodeAdapter);

        popupRegister1PhoneNumber = (EditText) popupRegisterView1.findViewById(R.id.popup_register1_phone_number);
        popupRegister1ObtainCaptcha = (Button) popupRegisterView1.findViewById(R.id.popup_register1_button);

        ViewGroup.LayoutParams ObtainCaptchaParams = popupRegister1ObtainCaptcha.getLayoutParams();
        ObtainCaptchaParams.width = screenWidth / 3;
        popupRegister1ObtainCaptcha.setLayoutParams(ObtainCaptchaParams);

        popupWindowRegister1 = new PopupWindow(popupRegisterView1, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindowRegister1.setBackgroundDrawable(new BitmapDrawable());


        popupRegisterView2 = LayoutInflater.from(this).inflate(R.layout.popup_register2, null);

        editCaptcha = (EditText) popupRegisterView2.findViewById(R.id.popup_register2_captcha);
        editPassWord = (EditText) popupRegisterView2.findViewById(R.id.popup_register_password);
        editConfirmPassWord = (EditText) popupRegisterView2.findViewById(R.id.popup_register_confirm_password);
        registerButton = (Button) popupRegisterView2.findViewById(R.id.popup_register_button);

        ViewGroup.LayoutParams registerParams = registerButton.getLayoutParams();
        registerParams.width = screenWidth / 3;
        registerButton.setLayoutParams(registerParams);

        popupWindowRegister2 = new PopupWindow(popupRegisterView2, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindowRegister2.setBackgroundDrawable(new BitmapDrawable());
    }

    /**
     * 控件事件
     */
    private void viewAction() {

        loginMainLogo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (areaCodeDataList == null || areaCodeDataList.size() <= 0) {
                    getAreaCode();
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowLogin.showAtLocation(loginRootView,
                        Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowRegister1.showAtLocation(popupRegisterView1, Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        popupLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUserName = popupLoginUserName.getText().toString().trim();
                loginPassword = popupLoginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(loginUserName)) {
                    Toast.makeText(LoginActivity.this, userNameNotNull, Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(loginPassword)) {
                    Toast.makeText(LoginActivity.this, passwordNotNull, Toast.LENGTH_SHORT).show();
                } else {
                    suiuuLogin(loginUserName, loginPassword);
                }
            }
        });

        popupRegister1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (areaCodeDataList != null && areaCodeDataList.size() > 0) {
                    AreaCodeData data = areaCodeDataList.get(position);
                    areaName = data.getCname();
                    areaCode = data.getAreaCode();
                    String usAreaName = data.getEname();
                    if (isZhCnLanguage) {
                        Toast.makeText(LoginActivity.this,
                                "您已选择:" + areaName + "   " + areaCode, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "You have chosen:" + usAreaName + "   " + areaCode, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        popupRegister1ObtainCaptcha.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = popupRegister1PhoneNumber.getText().toString().trim();
                setPhoneNumber4Service();
            }
        });

        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                VerificationCode = editCaptcha.getText().toString().trim();
                registerPassword1 = editPassWord.getText().toString().trim();
                registerPassword2 = editConfirmPassWord.getText().toString().trim();

                if (TextUtils.isEmpty(VerificationCode)) {
                    Toast.makeText(LoginActivity.this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(registerPassword1)) {
                    Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(registerPassword2)) {
                    Toast.makeText(LoginActivity.this, "确认密码密码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    SuiuuInfo.ClearSuiuuInfo(LoginActivity.this);
                    SuiuuInfo.ClearSuiuuThird(LoginActivity.this);
                    register4Suiuu();
                }
            }
        });

        weiBoLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "3";
                mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.SINA, new MicroBlog4UMAuthListener());
            }
        });

        qqLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "1";

                UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginActivity.this, TencentConstant.APP_ID, TencentConstant.APP_KEY);
                qqSsoHandler.addToSocialSDK();

                mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, new QQLogin4UMAuthListener());
            }
        });

        weChatLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "2";

                if (!weChatApi.isWXAppInstalled()) {
                    Toast.makeText(LoginActivity.this, "您尚未安装微信，请安装后再用！", Toast.LENGTH_SHORT).show();
                    return;
                }

                UMWXHandler wxHandler = new UMWXHandler(LoginActivity.this, WeChatConstant.APP_ID, WeChatConstant.APPSECRET);
                wxHandler.addToSocialSDK();

                mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN, new WeChat4UMAuthListener());
            }
        });

    }

    /**
     * 获取国际电话区号
     */
    private void getAreaCode() {
        try {
            OkHttpManager.onGetAsynRequest(HttpNewServicePath.getAreaCodeDataPath, new AreaCodeResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class AreaCodeResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "国际电话区号数据请求失败:" + e.getMessage());
            Toast.makeText(LoginActivity.this, repeatAreaCode, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            try {
                AreaCode areaCode = JsonUtils.getInstance().fromJSON(AreaCode.class, response);
                areaCodeDataList = areaCode.getData();
                if (areaCodeDataList != null && areaCodeDataList.size() > 0) {
                    areaCodeAdapter.setList(areaCodeDataList);
                } else {
                    Toast.makeText(LoginActivity.this, repeatAreaCode, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "国际电话区号数据解析异常:" + e.getMessage());
                AbnormalHandle(response, repeatAreaCode);
            }
        }

    }

    /**
     * 把国际电话区号和手机号发送到服务器
     */
    private void setPhoneNumber4Service() {
        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[2];
        paramsArray[0] = new OkHttpManager.Params(AREA_CODE, areaCode);
        paramsArray[1] = new OkHttpManager.Params(PHONE, phoneNumber);

        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.SendAreaCodeAndPhoneNumber,
                    new PhoneNumberResultCallback(), paramsArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class PhoneNumberResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "发送手机号错误:" + e.getMessage());
            Toast.makeText(LoginActivity.this, "发送失败，请重试！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                String status = object.getString(STATUS);
                if ("1".equals(status.trim())) {
                    popupWindowRegister1.dismiss();
                    popupWindowRegister2.showAtLocation(popupRegisterView2, Gravity.CENTER_HORIZONTAL, 0, 0);
                    Toast.makeText(LoginActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "发送失败，请检查手机号码是否填写正确！", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                AbnormalHandle(response, "发送失败，请重试！");
            }
        }

    }

    /**
     * 注册方法
     */
    private void register4Suiuu() {
        if (registerDialog != null && !registerDialog.isShowing()) {
            registerDialog.show();
        }

        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[5];
        paramsArray[0] = new OkHttpManager.Params(PHONE, phoneNumber);
        paramsArray[1] = new OkHttpManager.Params(PASSWORD, registerPassword1);
        paramsArray[2] = new OkHttpManager.Params(C_PASSWORD, registerPassword2);
        paramsArray[3] = new OkHttpManager.Params(NICK, phoneNumber);
        paramsArray[4] = new OkHttpManager.Params(VALIDATE_CODE, VerificationCode);

        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.Register4SuiuuPath,
                    new RegisterResultCallback(), paramsArray);
        } catch (IOException e) {
            e.printStackTrace();
            hideRegisterDialog();
        }
    }

    private void hideRegisterDialog() {
        if (registerDialog != null && registerDialog.isShowing()) {
            registerDialog.dismiss();
        }
    }

    private class RegisterResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            hideRegisterDialog();
            try {
                UserBack user = JsonUtils.getInstance().fromJSON(UserBack.class, response);
                if (user.getStatus().equals("1")) {
                    popupWindowRegister2.dismiss();
                    SuiuuInfo.WriteVerification(LoginActivity.this, user.getMessage());
                    SuiuuInfo.WriteUserSign(LoginActivity.this, user.getData().getUserSign());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "注册失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "注册返回的数据解析异常信息:" + e.getMessage());
                AbnormalHandle(response, "注册失败，请稍候再试！");
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "注册网络请求失败的异常信息:" + e.getMessage());
            hideRegisterDialog();
            Toast.makeText(LoginActivity.this, "注册失败，请检查网络后再试！", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 登陆到服务器
     *
     * @param userName 用户名
     * @param password 密码
     */
    private void suiuuLogin(String userName, String password) {
        if (loginDialog != null && !loginDialog.isShowing()) {
            loginDialog.show();
        }

        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[2];
        paramsArray[0] = new OkHttpManager.Params(USER_NAME, userName);
        paramsArray[1] = new OkHttpManager.Params(PASSWORD, password);

        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.SelfLoginPath,
                    new LoginResultCallback(), paramsArray);
        } catch (IOException e) {
            e.printStackTrace();
            hideLoginDialog();
        }
    }

    private void hideLoginDialog() {
        if (loginDialog != null && loginDialog.isShowing()) {
            loginDialog.dismiss();
        }
    }

    private class LoginResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            hideLoginDialog();
            DeBugLog.i(TAG, "登陆返回的数据:" + response);
            try {
                UserBack user = JsonUtils.getInstance().fromJSON(UserBack.class, response);
                if (user.getStatus().equals("1")) {
                    UserBack.UserBackData data = user.getData();
                    SuiuuInfo.WriteVerification(LoginActivity.this, user.getMessage());
                    SuiuuInfo.WriteUserSign(LoginActivity.this, data.getUserSign());
                    SuiuuInfo.WriteUserData(LoginActivity.this, data);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "登陆返回的数据解析异常:" + e.getMessage());
                AbnormalHandle(response, "获取数据失败，请稍候再试！");
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "登录网络请求失败返回的异常信息:" + e.getMessage());
            hideLoginDialog();
            Toast.makeText(LoginActivity.this, "登录失败，请稍候再试", Toast.LENGTH_SHORT).show();
        }

    }

    //    private void loginFailure2Umeng(final long start, final int code, final String message) {
    //        runOnUiThread(new Runnable() {
    //            public void run() {
    //                long costTime = System.currentTimeMillis() - start;
    //                Map<String, String> params = new HashMap<>();
    //                params.put("status", "failure");
    //                params.put("error_code", code + "");
    //                params.put("error_description", message);
    //                MobclickAgent.onEventValue(LoginActivity.this, "login1", params, (int) costTime);
    //                MobclickAgent.onEventDuration(LoginActivity.this, "login1", (int) costTime);
    //            }
    //        });
    //    }

    /**
     * QQ的openId,昵称,性别,头像URL
     */
    private String qq_open_id, qq_nick_Name, qq_gender, qq_head_image_path;

    private void hideTencentDialog() {
        if (tencentLoginDialog != null && tencentLoginDialog.isShowing()) {
            tencentLoginDialog.dismiss();
        }
    }

    /**
     * 友盟forQQ第三方登陆授权回调接口
     */
    private class QQLogin4UMAuthListener implements SocializeListeners.UMAuthListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "QQ授权开始");
            tencentLoginDialog.show();
        }

        @Override
        public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "QQ授权完成");
            Toast.makeText(LoginActivity.this, QQAuthorizedComplete, Toast.LENGTH_SHORT).show();

            if (bundle != null) {
                qq_open_id = bundle.getString(OPEN_ID_);
            }
            mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, new QQ4UMDataListener());
        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "QQ授权错误");
            DeBugLog.e(TAG, "QQ登陆错误:" + e.getMessage() + ",错误代码:" + e.getErrorCode());
            hideTencentDialog();
            Toast.makeText(LoginActivity.this, QQAuthorizedError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "QQ授权取消");
            hideTencentDialog();
            Toast.makeText(LoginActivity.this, QQAuthorizedCancel, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 友盟forQQ第三方登陆数据回调接口
     */
    private class QQ4UMDataListener implements SocializeListeners.UMDataListener {

        @Override
        public void onStart() {
            DeBugLog.i(TAG, "开始获取QQ数据");
        }

        @Override
        public void onComplete(int status, Map<String, Object> info) {
            DeBugLog.i(TAG, "QQ数据获取完成");
            hideLoginDialog();
            if (status == 200 && info != null) {
                String sex = info.get("gender").toString();
                if (!TextUtils.isEmpty(sex)) {
                    switch (sex) {
                        case "男":
                            qq_gender = "1";
                            break;
                        case "女":
                            qq_gender = "0";
                            break;
                        default:
                            qq_gender = "2";
                            break;
                    }
                }
                qq_nick_Name = info.get("screen_name").toString();
                qq_head_image_path = info.get("profile_image_url").toString();

                sendQQInfo2Service();
            } else {
                DeBugLog.e(TAG, "QQ数据获取失败");
                Toast.makeText(LoginActivity.this, "数据获取失败,请重试！", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * 发送QQ相关信息到服务器
     */
    private void sendQQInfo2Service() {
        String sign = null;
        try {
            sign = MD5Utils.getMD5(qq_open_id + type + HttpNewServicePath.ConfusedCode);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[6];
        paramsArray[0] = new OkHttpManager.Params(OPEN_ID, qq_open_id);
        paramsArray[1] = new OkHttpManager.Params(NICK_NAME, qq_nick_Name);
        paramsArray[2] = new OkHttpManager.Params(SEX, qq_gender);
        paramsArray[3] = new OkHttpManager.Params(HEAD_IMG, qq_head_image_path);
        paramsArray[4] = new OkHttpManager.Params(TYPE, type);
        paramsArray[5] = new OkHttpManager.Params(SIGN, sign);

        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.ThirdPartyPath,
                    new TencentResultCallback(), paramsArray);
        } catch (IOException e) {
            e.printStackTrace();
            hideLoginDialog();
        }
    }

    private class TencentResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            hideLoginDialog();
            DeBugLog.i(TAG, "使用QQ登录返回的数据:" + response);
            try {
                UserBack userBack = JsonUtils.getInstance().fromJSON(UserBack.class, response);
                if (userBack.getStatus().equals("1")) {

                    String message = userBack.getMessage();
                    if (!TextUtils.isEmpty(message)) {
                        SuiuuInfo.WriteVerification(LoginActivity.this, message);
                    }

                    String userSign = userBack.getData().getUserSign();
                    if (!TextUtils.isEmpty(userSign)) {
                        SuiuuInfo.WriteUserSign(LoginActivity.this, userSign);
                    }

                    SuiuuInfo.WriteUserSign(LoginActivity.this, userBack.getData().getIntro());
                    SuiuuInfo.WriteUserData(LoginActivity.this, userBack.getData());
                } else {
                    DeBugLog.e(TAG, "TencentResultCallback:返回数据有误！");
                    Toast.makeText(LoginActivity.this, DataError, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "QQ登陆数据解析错误:" + e.getMessage());
                Toast.makeText(LoginActivity.this, DataError, Toast.LENGTH_SHORT).show();
                AbnormalHandle(response, DataError);
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "QQ登陆数据解析错误:" + e.getMessage());
            Toast.makeText(LoginActivity.this, DataError, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 微信token，微信用户昵称，微信用户性别，微信用户openID，微信用户uID，微信用户头像URL
     */
    private String wechat_nick_name, wechat_gender, wechat_union_id, wechat_head_image_path;

    private void hideWeChatDialog() {
        if (weChatLoadDialog != null && weChatLoadDialog.isShowing()) {
            weChatLoadDialog.dismiss();
        }
    }

    /**
     * 友盟for微信第三方登陆授权回调接口
     */
    private class WeChat4UMAuthListener implements SocializeListeners.UMAuthListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "微信授权开始");
            weChatLoadDialog.show();
        }

        @Override
        public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "微信授权完成");
            Toast.makeText(LoginActivity.this, WeChatAuthorizedComplete, Toast.LENGTH_SHORT).show();
            mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, new WeChat4UMDataListener());
        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "微信授权错误:" + e.getMessage() + ",微信授权错误码:" + e.getErrorCode());
            hideWeChatDialog();
            Toast.makeText(LoginActivity.this, WeChatAuthorizedError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "微信授权取消");
            Toast.makeText(LoginActivity.this, WeChatAuthorizedCancel, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 友盟for微信第三方登陆数据回调接口
     */
    private class WeChat4UMDataListener implements SocializeListeners.UMDataListener {

        @Override
        public void onStart() {
            DeBugLog.i(TAG, "正在获取微信数据");
        }

        @Override
        public void onComplete(int status, Map<String, Object> info) {
            DeBugLog.i(TAG, "微信数据获取完成");
            hideWeChatDialog();
            if (status == 200 && info != null) {
                wechat_union_id = info.get("unionid").toString();
                wechat_nick_name = info.get("nickname").toString();
                String sex = info.get("sex").toString();
                switch (sex) {
                    case "1":
                        wechat_gender = "1";
                        break;

                    case "2":
                        wechat_gender = "0";
                        break;

                    default:
                        wechat_gender = "2";
                        break;
                }
                wechat_head_image_path = info.get("headimgurl").toString();

                sendWeChatInfo2Service();
            } else {
                DeBugLog.i(TAG, "微信数据获取失败");
            }
        }

    }

    /**
     * 发送微信相关信息到服务器
     */
    private void sendWeChatInfo2Service() {
        String sign = null;
        try {
            sign = MD5Utils.getMD5(wechat_union_id + type + HttpServicePath.ConfusedCode);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[6];
        paramsArray[0] = new OkHttpManager.Params(OPEN_ID, wechat_union_id);
        paramsArray[1] = new OkHttpManager.Params(NICK_NAME, wechat_nick_name);
        paramsArray[2] = new OkHttpManager.Params(SEX, wechat_gender);
        paramsArray[3] = new OkHttpManager.Params(HEAD_IMG, wechat_head_image_path);
        paramsArray[4] = new OkHttpManager.Params(TYPE, type);
        paramsArray[5] = new OkHttpManager.Params(SIGN, sign);

        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.ThirdPartyPath,
                    new WeChatResultCallback(), paramsArray);
        } catch (IOException e) {
            e.printStackTrace();
            hideLoginDialog();
        }
    }

    private class WeChatResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "服务器返回的数据:" + response);
            try {
                UserBack userBack = JsonUtils.getInstance().fromJSON(UserBack.class, response);
                if (userBack.getStatus().equals("1")) {

                    String message = userBack.getMessage();
                    if (!TextUtils.isEmpty(message)) {
                        SuiuuInfo.WriteVerification(LoginActivity.this, message);
                    }

                    String userSign = userBack.getData().getUserSign();
                    if (!TextUtils.isEmpty(userSign)) {
                        SuiuuInfo.WriteUserSign(LoginActivity.this, userSign);
                    }

                    SuiuuInfo.WriteUserSign(LoginActivity.this, userBack.getData().getIntro());
                    SuiuuInfo.WriteUserData(LoginActivity.this, userBack.getData());
                } else {
                    hideWeChatDialog();
                    Toast.makeText(LoginActivity.this, "登录失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "微信数据发送到服务器返回数据解析错误:" + e.getMessage());
                hideWeChatDialog();
                AbnormalHandle(response, DataError);
            }

        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "发送微信信息到服务器发生错误:" + e.getMessage());
            hideWeChatDialog();
            Toast.makeText(LoginActivity.this, NetworkAnomaly, Toast.LENGTH_SHORT).show();
        }

    }

    private String weibo_open_id, weibo_name, weibo_gender, weibo_head_img;

    /**
     * 友盟for微博第三方登陆授权回调接口
     */
    private class MicroBlog4UMAuthListener implements SocializeListeners.UMAuthListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "新浪微博授权开始！");
            Toast.makeText(LoginActivity.this, WeiboAuthorizedStart, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "新浪微博授权完成！");
            if (bundle != null && !TextUtils.isEmpty(bundle.getString("uid"))) {
                DeBugLog.i(TAG, "新浪微博授权成功！");
                DeBugLog.i(TAG, "新浪微博授权信息:" + bundle.toString());
                Toast.makeText(LoginActivity.this, WeiboAuthorizedComplete, Toast.LENGTH_SHORT).show();
                mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, new MicroBlog4UMDataListener());
            } else {
                DeBugLog.i(TAG, "新浪微博授权失败！");
                Toast.makeText(LoginActivity.this, WeiboAuthorizedFailure, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {
            DeBugLog.e(TAG, "新浪微博授权错误！" + e.getMessage());
            DeBugLog.e(TAG, "新浪微博错误代码1:" + e.getErrorCode());
            DeBugLog.e(TAG, "新浪微博错误代码2::" + String.valueOf(share_media.getReqCode()));
            Toast.makeText(LoginActivity.this, WeiboAuthorizedError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "新浪微博授权取消！");
            Toast.makeText(LoginActivity.this, WeiboAuthorizedCancel, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 友盟for微博第三方登陆数据回调接口
     */
    private class MicroBlog4UMDataListener implements SocializeListeners.UMDataListener {

        @Override
        public void onStart() {
            DeBugLog.i(TAG, "正在获取微博数据");
        }

        @Override
        public void onComplete(int status, Map<String, Object> info) {
            DeBugLog.i(TAG, "status:" + status);
            if (status == 200 && info != null) {
                DeBugLog.i(TAG, "微博返回数据为:" + info.toString());

                try {
                    weibo_open_id = info.get(OPEN_ID).toString();
                    DeBugLog.i(TAG, "openid:" + weibo_open_id);
                } catch (Exception e) {
                    DeBugLog.e(TAG, "获取openid失败:" + e.getMessage());
                }

                try {
                    weibo_name = info.get("screen_name").toString();
                    DeBugLog.i(TAG, "获取screenName失败:" + weibo_name);
                } catch (Exception e) {
                    DeBugLog.e(TAG, "获取screenName失败:" + e.getMessage());
                }

                try {
                    weibo_gender = info.get("gender").toString();
                    DeBugLog.i(TAG, "gender:" + weibo_gender);
                } catch (Exception e) {
                    DeBugLog.e(TAG, "获取gender失败:" + e.getMessage());
                }

                try {
                    weibo_head_img = info.get("profile_image_url").toString();
                    DeBugLog.i(TAG, "image_url:" + weibo_head_img);
                } catch (Exception e) {
                    DeBugLog.e(TAG, "获取image_url失败:" + e.getMessage());
                }

                sendWeiBoInfo2Service();
            } else {
                DeBugLog.e(TAG, "发生错误，未接收到数据！");
            }
        }

    }

    /**
     * 发送微博相关数据到服务器
     */
    private void sendWeiBoInfo2Service() {
        if (loginDialog != null && !loginDialog.isShowing()) {
            loginDialog.show();
        }

        String code;
        switch (weibo_gender) {
            case "男":
                code = "1";
                break;
            case "女":
                code = "0";
                break;
            default:
                code = "2";
                break;
        }

        String sign = null;
        try {
            sign = MD5Utils.getMD5(weibo_open_id + type + HttpNewServicePath.ConfusedCode);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        SuiuuInfo.WriteInformation(LoginActivity.this, new RequestData(weibo_open_id, weibo_name,
                code, weibo_head_img, type));

        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[6];
        paramsArray[0] = new OkHttpManager.Params(OPEN_ID, weibo_open_id);
        paramsArray[1] = new OkHttpManager.Params(NICK_NAME, weibo_name);
        paramsArray[2] = new OkHttpManager.Params(SEX, code);
        paramsArray[3] = new OkHttpManager.Params(HEAD_IMG, weibo_head_img);
        paramsArray[4] = new OkHttpManager.Params(TYPE, type);
        paramsArray[5] = new OkHttpManager.Params(SIGN, sign);

        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.ThirdPartyPath,
                    new WeiBoResultCallback(), paramsArray);
        } catch (IOException e) {
            e.printStackTrace();
            hideLoginDialog();
        }
    }

    private class WeiBoResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(LoginActivity.this, NoData, Toast.LENGTH_SHORT).show();
            } else {
                try {
                    if (!TextUtils.isEmpty(response)) {
                        UserBack userBack = JsonUtils.getInstance().fromJSON(UserBack.class, response);
                        if (userBack != null) {
                            if (userBack.getStatus().equals("1")) {
                                SuiuuInfo.WriteVerification(LoginActivity.this, userBack.getMessage());
                                SuiuuInfo.WriteUserSign(LoginActivity.this, userBack.getData().getUserSign());
                            } else {
                                Toast.makeText(LoginActivity.this, "数据获取失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, NetworkAnomaly, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, NoData, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    DeBugLog.e(TAG, "微博登录错误:" + e.getMessage());
                    AbnormalHandle(response, "数据获取失败，请稍候再试！");
                }
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.i(TAG, "网络异常:" + e.getMessage());
            hideLoginDialog();
            Toast.makeText(LoginActivity.this, NetworkAnomaly, Toast.LENGTH_SHORT).show();
        }

    }

    private void AbnormalHandle(String AbnormalField, String showInfo) {
        try {
            JSONObject object = new JSONObject(AbnormalField);
            String status = object.getString(STATUS);
            if (status.equals("-1")) {
                Toast.makeText(this, SystemException, Toast.LENGTH_SHORT).show();
            } else if (status.equals("-2")) {
                Toast.makeText(this, object.getString(DATA), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, showInfo, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if (popupWindowLogin.isShowing()) {
            popupWindowLogin.dismiss();
        } else if (popupWindowRegister1.isShowing()) {
            popupWindowRegister1.dismiss();
        } else if (popupWindowRegister2.isShowing()) {
            popupWindowRegister2.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindowLogin != null) {
            if (popupWindowLogin.isShowing()) {
                popupWindowLogin.dismiss();
            }
        }
    }

}