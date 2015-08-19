package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.AreaCodeAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.entity.AreaCode;
import com.minglang.suiuu.entity.AreaCodeData;
import com.minglang.suiuu.entity.RequestData;
import com.minglang.suiuu.entity.UserBack;
import com.minglang.suiuu.entity.UserBackData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.MD5Utils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.qq.TencentConstant;
import com.minglang.suiuu.utils.wechat.WeChatConstant;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
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

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登录页面
 */
@SuppressWarnings("deprecation")
public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final String HUANXINPASSWORD = "suIuu9Q5E2T7";

    @Bind(R.id.loginBtn)
    Button loginBtn;

    @Bind(R.id.registerBtn)
    Button registerBtn;

    /**
     * ***************************分割线***************************
     */

    //登陆PopupWindow

    private View popupLoginRootView;

    private PopupWindow popupWindowLogin;

    private EditText popupLoginUserName;

    private EditText popupLoginPassword;

    private Button popupLoginBtn;

    /**
     * ***************************分割线***************************
     */

    //注册PopupWindow1

    private View popupRegisterView1;

    private PopupWindow popupWindowRegister1;

    private Spinner popupRegister1Spinner;

    private EditText popupRegister1PhoneNumber;

    private Button popupRegister1ObtainCaptcha;

    private AreaCodeAdapter areaCodeAdapter;

    /**
     * **************************分割线***************************
     */

    //注册PopupWindow2

    private View popupRegisterView2;

    private PopupWindow popupWindowRegister2;

    private EditText editCaptcha;

    private EditText editPassWord;

    private EditText editConfirmPassWord;

    private Button registerButton;

    /**
     * **************************分割线***************************
     */

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
    private String internationalAreaCode;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * ***************************分割线***************************
     */

    /**
     * 登陆用户名
     */
    private String suiuuLoginUserName;

    /**
     * 登陆密码
     */
    private String suiuuLoginPassword;

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

    //微信相关
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
        getInternationalAreaCode();
        ViewAction();
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
        loginDialog.setMessage(getResources().getString(R.string.login_wait));

        registerDialog = new ProgressDialog(this);
        registerDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        registerDialog.setCanceledOnTouchOutside(false);
        registerDialog.setCancelable(true);
        registerDialog.setMessage(getResources().getString(R.string.register_wait));

        tencentLoginDialog = new ProgressDialog(this);
        tencentLoginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        tencentLoginDialog.setCanceledOnTouchOutside(false);
        tencentLoginDialog.setMessage(getResources().getString(R.string.login_wait));

        weChatLoadDialog = new ProgressDialog(this);
        weChatLoadDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        weChatLoadDialog.setCanceledOnTouchOutside(false);
        weChatLoadDialog.setCancelable(true);
        weChatLoadDialog.setMessage(getResources().getString(R.string.login_wait));
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
        popupLoginRootView = LayoutInflater.from(this).inflate(R.layout.popup_login, null);

        popupLoginUserName = (EditText) popupLoginRootView.findViewById(R.id.userName);
        popupLoginPassword = (EditText) popupLoginRootView.findViewById(R.id.userPassword);
        popupLoginBtn = (Button) popupLoginRootView.findViewById(R.id.popupLoginBtn);

        ViewGroup.LayoutParams loginParams = popupLoginBtn.getLayoutParams();
        loginParams.width = screenWidth / 3;
        popupLoginBtn.setLayoutParams(loginParams);

        popupWindowLogin = new PopupWindow(popupLoginRootView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindowLogin.setBackgroundDrawable(new BitmapDrawable());

        //****************************分割线***************************\\

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

        //* ***************************分割线***************************\\

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
    private void ViewAction() {

        loginMainLogo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (areaCodeDataList == null || areaCodeDataList.size() <= 0) {
                    getInternationalAreaCode();
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowLogin.showAtLocation(popupLoginRootView,
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

                suiuuLoginUserName = popupLoginUserName.getText().toString().trim();
                suiuuLoginPassword = popupLoginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(suiuuLoginUserName)) {
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(suiuuLoginPassword)) {
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
                } else {
                    suiuuLogin(suiuuLoginUserName, suiuuLoginPassword);
                }
            }
        });

        popupRegister1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (areaCodeDataList != null && areaCodeDataList.size() > 0) {
                    AreaCodeData data = areaCodeDataList.get(position);
                    areaName = data.getCname();
                    internationalAreaCode = data.getAreaCode();
                    String usAreaName = data.getEname();
                    if (isZhCnLanguage) {
                        Toast.makeText(LoginActivity.this,
                                "您已选择:" + areaName + "   " + internationalAreaCode, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "You have chosen:" + usAreaName + "   " + internationalAreaCode, Toast.LENGTH_SHORT).show();
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
    private void getInternationalAreaCode() {
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.GET,
                HttpServicePath.GetInternationalAreaCode, new AreaCodeRequestCallBack());
        httpRequest.executive();
    }

    /**
     * 获取国际电话区号网络请求回调接口
     */
    private class AreaCodeRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            DeBugLog.i(TAG, "返回的国际电话区号数据:" + str);
            try {
                AreaCode areaCode = JsonUtils.getInstance().fromJSON(AreaCode.class, str);
                areaCodeDataList = areaCode.getData();
                if (areaCodeDataList != null && areaCodeDataList.size() > 0) {
                    areaCodeAdapter.setList(areaCodeDataList);
                } else {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.InternationalCodeFailure),
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "国际电话区号数据解析异常:" + e.getMessage());
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.InternationalCodeFailure),
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException httpException, String s) {
            DeBugLog.e(TAG, "国际电话区号数据请求失败:" + s);
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.InternationalCodeFailure),
                    Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 把国际电话区号和手机号发送到服务器
     */
    private void setPhoneNumber4Service() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("areaCode", internationalAreaCode);
        params.addBodyParameter("phone", phoneNumber);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.SendInternationalAreaCodeAndPhoneNumber, new PhoneNumberRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 发送手机号的网络请求回调接口
     */
    private class PhoneNumberRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            try {
                JSONObject object = new JSONObject(str);
                String status = object.getString("status");
                if ("1".equals(status.trim())) {
                    popupWindowRegister1.dismiss();
                    popupWindowRegister2.showAtLocation(popupRegisterView2, Gravity.CENTER_HORIZONTAL, 0, 0);
                    Toast.makeText(LoginActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "发送失败，请检查手机号码是否填写正确！", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "发送失败，请重试！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "发送区号手机号到服务器的请求失败1:" + s);
            DeBugLog.e(TAG, "发送区号手机号到服务器的请求失败2:" + e.getMessage());
            DeBugLog.e(TAG, "发送区号手机号到服务器的请求失败3:" + e.getExceptionCode());
            DeBugLog.e(TAG, "发送区号手机号到服务器的请求失败4:" + e.toString());
            Toast.makeText(LoginActivity.this, "发送失败，请重试！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 注册方法
     */
    private void register4Suiuu() {

        if (registerDialog != null) {
            registerDialog.show();
        }

        RequestParams params = new RequestParams();
        params.addBodyParameter("phone", phoneNumber);
        params.addBodyParameter("password", registerPassword1);
        params.addBodyParameter("cPassword", registerPassword2);
        params.addBodyParameter("nick", phoneNumber);
        params.addBodyParameter("validateCode", VerificationCode);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.Register4SuiuuPath, new RegisterRequestCallback());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 注册回调接口
     */
    private class RegisterRequestCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            if (registerDialog.isShowing()) {
                registerDialog.dismiss();
            }

            String str = stringResponseInfo.result;
            try {
                UserBack user = JsonUtils.getInstance().fromJSON(UserBack.class, str);
                if (user.getStatus().equals("1")) {
                    popupWindowRegister2.dismiss();
                    SuiuuInfo.WriteVerification(LoginActivity.this, user.getMessage());
                    SuiuuInfo.WriteUserSign(LoginActivity.this, user.getData().getUserSign());
                } else {
                    Toast.makeText(LoginActivity.this, "注册失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "注册返回的数据解析异常信息:" + e.getMessage());
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            if (registerDialog.isShowing()) {
                registerDialog.dismiss();
            }
            DeBugLog.e(TAG, "注册网络请求失败的异常信息:" + s);
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

        if (loginDialog != null) {
            loginDialog.show();
        }

        RequestParams params = new RequestParams();
        params.addBodyParameter("username", userName);
        params.addBodyParameter("password", password);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.SelfLoginPath, new LoginRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 登陆回调接口
     */
    private class LoginRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {

            if (loginDialog.isShowing()) {
                loginDialog.dismiss();
            }

            String str = responseInfo.result;
            try {
                UserBack user = JsonUtils.getInstance().fromJSON(UserBack.class, str);
                if (user.getStatus().equals("1")) {
                    UserBackData data = user.getData();
                    SuiuuInfo.WriteVerification(LoginActivity.this, user.getMessage());
                    SuiuuInfo.WriteUserSign(LoginActivity.this, data.getUserSign());
                    SuiuuInfo.WriteUserData(LoginActivity.this, data);
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "登陆返回的数据:" + str);
                DeBugLog.e(TAG, "登陆返回的数据解析异常:" + e.getMessage());
                Toast.makeText(LoginActivity.this, "获取数据失败，请稍候再试！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            DeBugLog.e(TAG, "登录网络请求失败返回的异常信息:" + msg);

            if (loginDialog.isShowing()) {
                loginDialog.dismiss();
            }

            Toast.makeText(LoginActivity.this, "登录失败，请稍候再试", Toast.LENGTH_SHORT).show();
        }
    }




    private void loginFailure2Umeng(final long start, final int code, final String message) {
        runOnUiThread(new Runnable() {
            public void run() {
                long costTime = System.currentTimeMillis() - start;
                Map<String, String> params = new HashMap<>();
                params.put("status", "failure");
                params.put("error_code", code + "");
                params.put("error_description", message);
                MobclickAgent.onEventValue(LoginActivity.this, "login1", params, (int) costTime);
                MobclickAgent.onEventDuration(LoginActivity.this, "login1", (int) costTime);
            }
        });
    }

    /**
     * QQ的openId,昵称,性别,头像URL
     */
    private String qq_open_id, qq_nick_Name, qq_gender, qq_head_image_path;

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

            Toast.makeText(LoginActivity.this, getResources().getString(R.string.QQAuthorizedComplete),
                    Toast.LENGTH_SHORT).show();

            if (bundle != null) {
                qq_open_id = bundle.getString("openid");
            }
            mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, new QQ4UMDataListener());
        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "QQ授权错误");
            DeBugLog.e(TAG, "QQ登陆错误:" + e.getMessage());
            DeBugLog.e(TAG, "错误代码:" + e.getErrorCode());

            if (tencentLoginDialog.isShowing()) {
                tencentLoginDialog.dismiss();
            }

            Toast.makeText(LoginActivity.this, getResources().getString(R.string.QQAuthorizedError),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "QQ授权取消");

            if (tencentLoginDialog.isShowing()) {
                tencentLoginDialog.dismiss();
            }

            Toast.makeText(LoginActivity.this, getResources().getString(R.string.QQAuthorizedCancel),
                    Toast.LENGTH_SHORT).show();
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

            if (tencentLoginDialog.isShowing()) {
                tencentLoginDialog.dismiss();
            }

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

                setQQData2Service();
            } else {
                DeBugLog.e(TAG, "QQ数据获取失败");
                Toast.makeText(LoginActivity.this, "数据获取失败,请重试！", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * 发送QQ相关信息到服务器
     */
    private void setQQData2Service() {

        SuHttpRequest http = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.ThirdPartyPath, new QQRequestCallBack());

        RequestParams params = new RequestParams();
        params.addBodyParameter("openId", qq_open_id);
        params.addBodyParameter("nickname", qq_nick_Name);
        params.addBodyParameter("sex", qq_gender);
        params.addBodyParameter("headImg", qq_head_image_path);
        params.addBodyParameter("type", type);

        String sign = null;
        try {
            sign = MD5Utils.getMD5(qq_open_id + type + HttpServicePath.ConfusedCode);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        params.addBodyParameter("sign", sign);

        http.setParams(params);
        http.executive();
    }

    /**
     * 发送QQ相关信息到服务器的回调接口
     */
    class QQRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {

            if (loginDialog != null && loginDialog.isShowing()) {
                loginDialog.dismiss();
            }

            String str = responseInfo.result;
            try {
                UserBack userBack = JsonUtils.getInstance().fromJSON(UserBack.class, str);
                if (userBack.status.equals("1")) {

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
                    DeBugLog.e(TAG, "QQRequestCallBack:返回数据有误！");
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "QQ登陆数据解析错误:" + e.getMessage());
                Toast.makeText(LoginActivity.this,
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(HttpException error, String msg) {
            DeBugLog.e(TAG, msg);

            if (loginDialog != null && loginDialog.isShowing()) {
                loginDialog.dismiss();
            }

            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }

    }

    //*********************************************************************************\\

    /**
     * 微信token，微信用户昵称，微信用户性别，微信用户openID，微信用户uID，微信用户头像URL
     */
    private String weChatNickName, weChatGender, weChatUnionId, weChatHeadImagePath;

    private String sign;

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
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.WeChatAuthorizedComplete),
                    Toast.LENGTH_SHORT).show();
            mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, new WeChat4UMDataListener());
        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "微信授权错误:" + e.getMessage());
            DeBugLog.i(TAG, "微信授权错误码:" + e.getErrorCode());

            if (weChatLoadDialog.isShowing()) {
                weChatLoadDialog.dismiss();
            }

            Toast.makeText(LoginActivity.this, getResources().getString(R.string.WeChatAuthorizedError),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "微信授权取消");
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.WeChatAuthorizedCancel),
                    Toast.LENGTH_SHORT).show();
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

            if (weChatLoadDialog.isShowing()) {
                weChatLoadDialog.dismiss();
            }

            if (status == 200 && info != null) {
                weChatUnionId = info.get("unionid").toString();
                weChatNickName = info.get("nickname").toString();
                String sex = info.get("sex").toString();
                switch (sex) {
                    case "1":
                        weChatGender = "1";
                        break;

                    case "2":
                        weChatGender = "0";
                        break;

                    default:
                        weChatGender = "2";
                        break;
                }
                weChatHeadImagePath = info.get("headimgurl").toString();

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
        RequestParams params = new RequestParams();
        params.addBodyParameter("openId", weChatUnionId);
        params.addBodyParameter("nickname", weChatNickName);
        params.addBodyParameter("sex", weChatGender);
        params.addBodyParameter("headImg", weChatHeadImagePath);
        params.addBodyParameter("type", type);
        try {
            sign = MD5Utils.getMD5(weChatUnionId + type + HttpServicePath.ConfusedCode);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        params.addBodyParameter("sign", sign);

        SuHttpRequest http = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.ThirdPartyPath, new WXRequestCallBack());
        http.setParams(params);
        http.executive();
    }

    /**
     * 发送微信数据数据到服务器的网络请求回调接口
     */
    private class WXRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            String str = stringResponseInfo.result;
            DeBugLog.i(TAG, "本地服务器返回的数据:" + str);

            try {
                UserBack userBack = JsonUtils.getInstance().fromJSON(UserBack.class, str);
                if (userBack.status.equals("1")) {

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

                    if (weChatLoadDialog.isShowing()) {
                        weChatLoadDialog.dismiss();
                    }

                    Toast.makeText(LoginActivity.this, "登录失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "微信数据发送到服务器返回数据解析错误:" + e.getMessage());

                if (weChatLoadDialog.isShowing()) {
                    weChatLoadDialog.dismiss();
                }

                Toast.makeText(LoginActivity.this,
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onFailure(HttpException e, String s) {

            DeBugLog.e(TAG, "发送微信信息到服务器发生错误:" + s);

            if (weChatLoadDialog.isShowing()) {
                weChatLoadDialog.dismiss();
            }

            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();

        }
    }

    //*********************************************************************************\\

    /**
     * 友盟for微博第三方登陆授权回调接口
     */
    private class MicroBlog4UMAuthListener implements SocializeListeners.UMAuthListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "新浪微博授权开始！");
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.WeiboAuthorizedStart),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "新浪微博授权完成！");
            if (bundle != null && !TextUtils.isEmpty(bundle.getString("uid"))) {
                DeBugLog.i(TAG, "新浪微博授权成功！");
                DeBugLog.i(TAG, "新浪微博授权信息:" + bundle.toString());
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.WeiboAuthorizedComplete),
                        Toast.LENGTH_SHORT).show();
                mController.getPlatformInfo(LoginActivity.this,
                        SHARE_MEDIA.SINA, new MicroBlog4UMDataListener());
            } else {
                DeBugLog.i(TAG, "新浪微博授权失败！");
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.WeiboAuthorizedFailure),
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {
            DeBugLog.e(TAG, "新浪微博授权错误！" + e.getMessage());
            DeBugLog.e(TAG, "新浪微博错误代码1:" + e.getErrorCode());
            DeBugLog.e(TAG, "新浪微博错误代码2::" + String.valueOf(share_media.getReqCode()));
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.WeiboAuthorizedError),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "新浪微博授权取消！");
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.WeiboAuthorizedCancel),
                    Toast.LENGTH_SHORT).show();
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

                String openid = null;
                try {
                    openid = info.get("openid").toString();
                    Log.i(TAG, "openid:" + openid);
                } catch (Exception e) {
                    Log.e(TAG, "获取openid失败:" + e.getMessage());
                }

                String screenName = null;
                try {
                    screenName = info.get("screen_name").toString();
                    Log.i(TAG, "获取screenName失败:" + screenName);
                } catch (Exception e) {
                    Log.e(TAG, "获取screenName失败:" + e.getMessage());
                }

                String gender = null;
                try {
                    gender = info.get("gender").toString();
                    Log.i(TAG, "gender:" + gender);
                } catch (Exception e) {
                    Log.e(TAG, "获取gender失败:" + e.getMessage());
                }

                String image_url = null;
                try {
                    image_url = info.get("profile_image_url").toString();
                    Log.i(TAG, "image_url:" + image_url);
                } catch (Exception e) {
                    Log.e(TAG, "获取image_url失败:" + e.getMessage());
                }

                setWeiBoData2Service(openid, screenName, gender, image_url);
            } else {
                DeBugLog.e(TAG, "发生错误，未接收到数据！");
            }
        }

    }

    /**
     * 发送微博相关数据到服务器
     */
    private void setWeiBoData2Service(String openid, String screenName, String gender, String headImagePath) {
        if (loginDialog != null) {
            loginDialog.show();
        }

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.ThirdPartyPath, new WeiBoRequestCallBack());

        String code;

        switch (gender) {
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

        RequestParams params = new RequestParams();
        params.addBodyParameter("openId", openid);
        params.addBodyParameter("nickname", screenName);
        params.addBodyParameter("sex", code);
        params.addBodyParameter("headImg", headImagePath);
        params.addBodyParameter("type", type);

        String sign = null;
        try {
            sign = MD5Utils.getMD5(openid + type + HttpServicePath.ConfusedCode);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        params.addBodyParameter("sign", sign);

        SuiuuInfo.WriteInformation(LoginActivity.this, new RequestData(openid, screenName,
                code, headImagePath, type));

        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 发送微博相关信息到服务器的回调接口
     */
    class WeiBoRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {

            if (loginDialog != null && loginDialog.isShowing()) {
                loginDialog.dismiss();
            }

            String str = responseInfo.result;
            UserBack userBack = JsonUtils.getInstance().fromJSON(UserBack.class, str);

            if (userBack != null) {
                if (userBack.status.equals("1")) {
                    SuiuuInfo.WriteVerification(LoginActivity.this, userBack.getMessage());
                    SuiuuInfo.WriteUserSign(LoginActivity.this, userBack.getData().getUserSign());
                } else {
                    Toast.makeText(LoginActivity.this, "数据获取失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "网络错误，请稍候再试！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            DeBugLog.i(TAG, msg);

            if (loginDialog != null && loginDialog.isShowing()) {
                loginDialog.dismiss();
            }

            Toast.makeText(LoginActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
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
    protected void onResume() {
        super.onResume();
//        if (autoLogin) {
//            return;
//        }
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