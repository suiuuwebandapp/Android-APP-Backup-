package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.exceptions.EaseMobException;
import com.easemob.util.EMLog;
import com.easemob.util.HanziToPinyin;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.chat.bean.User;
import com.minglang.suiuu.chat.chat.Constant;
import com.minglang.suiuu.chat.chat.DemoApplication;
import com.minglang.suiuu.chat.dao.UserDao;
import com.minglang.suiuu.chat.utils.CommonUtils;
import com.minglang.suiuu.entity.AreaCode;
import com.minglang.suiuu.entity.AreaCodeData;
import com.minglang.suiuu.entity.RequestData;
import com.minglang.suiuu.entity.UserBack;
import com.minglang.suiuu.entity.UserBackData;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录页面
 */
@SuppressWarnings("deprecation")
public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    //suIuu9Q5E2T7
    private static final String HUANXINPASSWORD = "suIuu9Q5E2T7";

    private Button loginBtn;

    private Button registerBtn;

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
     * ***************************分割线***************************
     */

    //判断是否登录
    private boolean autoLogin = false;
    /**
     * 环信用户名
     */
    private String huanXinUsername;
    private boolean progressShow;

    /**
     * 第三方登陆按钮
     */
    private ImageView microBlog_login, qq_login, weChat_login;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //		 如果用户名密码都有，直接进入主页面
//        if (DemoHXSDKHelper.getInstance().isLogined()) {
//            autoLogin = true;
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }
        DemoApplication.addActivity(this);
        setContentView(R.layout.activity_login);

        initView();
        initThirdParty();
        getInternationalAreaCode();
        ViewAction();
    }

    /**
     * 获取国际电话区号
     */
    private void getInternationalAreaCode() {
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.GetInternationalAreaCode, new AreaCodeRequestCallBack());
        httpRequest.requestNetworkData();
    }

    /**
     * 获取国际电话区号网络请求回调接口
     */
    private class AreaCodeRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            try {
                AreaCode areaCode = JsonUtil.getInstance().fromJSON(AreaCode.class, str);
                areaCodeDataList = areaCode.getData();
            } catch (Exception e) {
                Log.e(TAG, "国际电话区号数据解析异常:" + e.getMessage());
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, "国际电话区号数据请求失败:" + s);
        }
    }

    /**
     * 控件事件
     */
    private void ViewAction() {

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
                if (!CommonUtils.isNetWorkConnected(LoginActivity.this)) {
                    Toast.makeText(LoginActivity.this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
                    return;
                }
                suiuuLoginUserName = popupLoginUserName.getText().toString().trim();
                suiuuLoginPassword = popupLoginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(suiuuLoginUserName)) {
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(suiuuLoginPassword)) {
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
                    return;
                }

                suiuuLogin(suiuuLoginUserName, suiuuLoginPassword);
            }
        });

        popupRegister1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AreaCodeData data = areaCodeDataList.get(position);
                areaName = data.getCname();
                internationalAreaCode = data.getAreaCode();
                Toast.makeText(LoginActivity.this,
                        "您已选择" + areaName + ":" + internationalAreaCode, Toast.LENGTH_LONG).show();
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
                    register4Suiuu();
                }
            }
        });


        microBlog_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "3";

                mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.SINA, new MicroBlog4UMAuthListener());


            }
        });

        qq_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "1";

                UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginActivity.this,
                        TencentConstant.APP_ID, TencentConstant.APP_KEY);
                qqSsoHandler.addToSocialSDK();

                mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, new QQLogin4UMAuthListener());
            }
        });

        weChat_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "2";

                if (!weChatApi.isWXAppInstalled()) {
                    Toast.makeText(LoginActivity.this, "您尚未安装微信，请安装后再用！", Toast.LENGTH_SHORT).show();
                    return;
                }

                UMWXHandler wxHandler = new UMWXHandler(LoginActivity.this,
                        WeChatConstant.APP_ID, WeChatConstant.APPSECRET);
                wxHandler.addToSocialSDK();

                mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN, new WeChat4UMAuthListener());

            }
        });

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
        httpRequest.requestNetworkData();
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
                JSONObject data = object.getJSONObject("data");
                String dataInfo = data.getString("0");
                if (status.equals("1")) {
                    popupWindowRegister1.dismiss();
                    popupWindowRegister2.showAtLocation(popupRegisterView2, Gravity.CENTER_HORIZONTAL, 0, 0);
                    Toast.makeText(LoginActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "发送失败:" + dataInfo);
                    Toast.makeText(LoginActivity.this, "发送失败，请检查手机号码是否填写正确！", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "发送失败，请重试！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, "发送区号手机号到服务器的请求失败:" + s);
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
        httpRequest.requestNetworkData();
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
                UserBack user = JsonUtil.getInstance().fromJSON(UserBack.class, str);
                if (user.getStatus().equals("1")) {
                    popupWindowRegister2.dismiss();
                    SuiuuInfo.WriteVerification(LoginActivity.this, user.getMessage());
                    SuiuuInfo.WriteUserSign(LoginActivity.this, user.getData().getUserSign());
                    huanXinUsername = user.getData().getUserSign();
                    huanXinLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "注册失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "注册返回的数据解析异常信息:" + e.getMessage());
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            if (registerDialog.isShowing()) {
                registerDialog.dismiss();
            }
            Log.e(TAG, "注册网络请求失败的异常信息:" + s);
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
        httpRequest.requestNetworkData();
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
                UserBack user = JsonUtil.getInstance().fromJSON(UserBack.class, str);
                if (user.getStatus().equals("1")) {
                    UserBackData data = user.getData();
                    SuiuuInfo.WriteVerification(LoginActivity.this, user.getMessage());
                    SuiuuInfo.WriteUserSign(LoginActivity.this, data.getUserSign());
                    SuiuuInfo.WriteUserData(LoginActivity.this, data);
                    huanXinUsername = user.getData().getUserSign();
                    huanXinLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "登陆返回的数据:" + str);
                Log.e(TAG, "登陆返回的数据解析异常:" + e.getMessage());
                Toast.makeText(LoginActivity.this, "获取数据失败，请稍候再试！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Log.e(TAG, "登录网络请求失败返回的异常信息:" + msg);

            if (loginDialog.isShowing()) {
                loginDialog.dismiss();
            }

            Toast.makeText(LoginActivity.this, "登录失败，请稍候再试", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 环信登录方法
     */
    public void huanXinLogin() {
        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                progressShow = false;
            }
        });
        pd.setMessage(getResources().getString(R.string.Is_landing));
        pd.show();

        Log.i(TAG, "环信用户名:" + huanXinUsername);

        final long start = System.currentTimeMillis();
        // 调用sdk登陆方法登陆聊天服务器
        EMChatManager.getInstance().login(huanXinUsername, HUANXINPASSWORD, new suiuuEmCallback(pd, start));

    }

    /**
     * 重写环信回调接口
     */
    private class suiuuEmCallback implements EMCallBack {

        private ProgressDialog pd;
        private long start;

        private suiuuEmCallback(ProgressDialog pd, long start) {
            this.pd = pd;
            this.start = start;
        }

        @Override
        public void onSuccess() {
            if (!progressShow) {
                return;
            }
            // 登陆成功，保存用户名密码
            DemoApplication.getInstance().setUserName(huanXinUsername);
            DemoApplication.getInstance().setPassword(HUANXINPASSWORD);
            runOnUiThread(new Runnable() {
                public void run() {
                    pd.setMessage(getResources().getString(R.string.list_is_for));
                }
            });
            try {
                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                // ** manually load all local groups and
                // conversations in case we are auto login
//                    EMGroupManager.getInstance().loadAllGroups();
                EMChatManager.getInstance().loadAllConversations();
                //处理好友和群组
//					processContactsAndGroups();
                //处理好友和群组
                processContactsAndGroups();
            } catch (Exception e) {
                e.printStackTrace();
                //取好友或者群聊失败，不让进入主页面
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        DemoApplication.getInstance().logout(null);
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.login_failure_failed), Toast.LENGTH_LONG).show();
                    }
                });
                return;
            }
            //更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
            boolean upDateNick = EMChatManager.getInstance().updateCurrentUserNick(DemoApplication.currentUserNick.trim());
            if (!upDateNick) {
            }
            if (!LoginActivity.this.isFinishing()) {
                pd.dismiss();
            }
            // 进入主页面
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        @Override
        public void onError(int code, final String message) {
            loginFailure2Umeng(start, code, message);
            if (!progressShow) {
                return;
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.Login_failed) + message, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onProgress(int i, String s) {

        }
    }

    /**
     * 环信相关方法
     *
     * @throws EaseMobException
     */
    private void processContactsAndGroups() throws EaseMobException {
        // demo中简单的处理成每次登陆都去获取好友username，开发者自己根据情况而定
        List<String> usernames = EMContactManager.getInstance().getContactUserNames();
        EMLog.d("roster", "contacts size: " + usernames.size());
        Map<String, User> userlist = new HashMap<>();
        for (String username : usernames) {
            User user = new User();
            user.setUsername(username);
            setUserHeader(username, user);
            userlist.put(username, user);
        }
        // 添加user"申请与通知"
        User newFriends = new User();
        newFriends.setUsername(Constant.NEW_FRIENDS_USERNAME);
        String strChat = getResources().getString(R.string.Application_and_notify);
        newFriends.setNick(strChat);

        userlist.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
        // 添加"群聊"
        User groupUser = new User();
        String strGroup = getResources().getString(R.string.group_chat);
        groupUser.setUsername(Constant.GROUP_USERNAME);
        groupUser.setNick(strGroup);
        groupUser.setHeader("");
        userlist.put(Constant.GROUP_USERNAME, groupUser);

        // 存入内存
        DemoApplication.getInstance().setContactList(userlist);
        System.out.println("----------------" + userlist.values().toString());
        // 存入db
        UserDao dao = new UserDao(LoginActivity.this);
        List<User> users = new ArrayList<>(userlist.values());
        dao.saveContactList(users);

        //获取黑名单列表
        List<String> blackList = EMContactManager.getInstance().getBlackListUsernamesFromServer();
        //保存黑名单
        EMContactManager.getInstance().saveBlackList(blackList);

        // 获取群聊列表(群聊里只有groupid和groupname等简单信息，不包含members),sdk会把群组存入到内存和db中
        EMGroupManager.getInstance().getGroupsFromServer();
    }

    /**
     * 设置header属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
     *
     * @param username 用户名
     * @param user     用户
     */
    protected void setUserHeader(String username, User user) {
        String headerName;
        if (!TextUtils.isEmpty(user.getNick())) {
            headerName = user.getNick();
        } else {
            headerName = user.getUsername();
        }
        if (username.equals(Constant.NEW_FRIENDS_USERNAME)) {
            user.setHeader("");
        } else if (Character.isDigit(headerName.charAt(0))) {
            user.setHeader("#");
        } else {
            user.setHeader(HanziToPinyin.getInstance().get(headerName.substring(0, 1)).get(0).target.substring(0, 1).toUpperCase());
            char header = user.getHeader().toLowerCase().charAt(0);
            if (header < 'a' || header > 'z') {
                user.setHeader("#");
            }
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
     * 初始化第三方相关实例
     */
    private void initThirdParty() {

        weChatApi = WXAPIFactory.createWXAPI(this, WeChatConstant.APP_ID, false);

        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
    }

    /**
     * 初始化方法
     */
    private void initView() {

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

        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);

        initPopupWindow();

        microBlog_login = (ImageView) findViewById(R.id.microBlog_login);
        qq_login = (ImageView) findViewById(R.id.qq_login);
        weChat_login = (ImageView) findViewById(R.id.weChat_login);
    }

    /**
     * 初始化PopupWindow
     */
    @SuppressLint("InflateParams")
    private void initPopupWindow() {
        DisplayMetrics dm = getResources().getDisplayMetrics();

        float density = dm.density;//屏幕密度（像素比例：0.75, 1.0, 1.5, 2.0）
        int densityDPI = dm.densityDpi;//屏幕密度（每寸像素：120, 160, 240, 320）

        int screenWidth = dm.widthPixels;//屏幕宽度

        Log.i(TAG, "像素比例:" + String.valueOf(density));
        Log.i(TAG, "每寸像素:" + String.valueOf(densityDPI));

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

        /**
         * ***************************分割线***************************
         */

        popupRegisterView1 = LayoutInflater.from(this).inflate(R.layout.popup_register1, null);

        popupRegister1Spinner = (Spinner) popupRegisterView1.findViewById(R.id.popup_register1_international_area_code);
        popupRegister1PhoneNumber = (EditText) popupRegisterView1.findViewById(R.id.popup_register1_phone_number);
        popupRegister1ObtainCaptcha = (Button) popupRegisterView1.findViewById(R.id.popup_register1_button);

        ViewGroup.LayoutParams ObtainCaptchaParams = popupRegister1ObtainCaptcha.getLayoutParams();
        ObtainCaptchaParams.width = screenWidth / 3;
        popupRegister1ObtainCaptcha.setLayoutParams(ObtainCaptchaParams);

        popupWindowRegister1 = new PopupWindow(popupRegisterView1, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindowRegister1.setBackgroundDrawable(new BitmapDrawable());

        /**
         * ***************************分割线***************************
         */

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
     * 友盟for微博第三方登陆授权回调接口
     */
    private class MicroBlog4UMAuthListener implements SocializeListeners.UMAuthListener {


        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.i(TAG, "新浪微博授权开始！");
        }

        @Override
        public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
            Log.i(TAG, "新浪微博授权完成！");
            if (bundle != null && !TextUtils.isEmpty(bundle.getString("uid"))) {
                Log.i(TAG, "新浪微博授权成功！");
                Log.i(TAG, "新浪微博授权信息:" + bundle.toString());
                mController.getPlatformInfo(LoginActivity.this,
                        SHARE_MEDIA.SINA, new MicroBlog4UMDataListener());
            } else {
                Log.i(TAG, "新浪微博授权失败！");
            }
        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {
            Log.e(TAG, "新浪微博授权错误！" + e.getMessage());
            Log.e(TAG, "新浪微博错误代码:" + e.getErrorCode());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.i(TAG, "新浪微博授权取消！");
        }
    }

    /**
     * 友盟for微博第三方登陆数据回调接口
     */
    private class MicroBlog4UMDataListener implements SocializeListeners.UMDataListener {

        @Override
        public void onStart() {
            Log.i(TAG, "正在获取微博数据");
        }

        @Override
        public void onComplete(int status, Map<String, Object> info) {
            if (status == 200 && info != null) {
                Log.i(TAG, "微博返回数据为:" + info.toString());
            } else {
                Log.e(TAG, "发生错误，未接收到数据！");
            }
        }
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
            Log.i(TAG, "QQ授权开始");
            tencentLoginDialog.show();
        }

        @Override
        public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
            Log.i(TAG, "QQ授权完成");
            Log.i(TAG, "QQ授权信息:" + bundle.toString());
            if (bundle != null) {
                qq_open_id = bundle.getString("openid");
            }
            mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, new QQ4UMDataListener());
        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {
            Log.i(TAG, "QQ授权错误");
            Log.e(TAG, "QQ登陆错误:" + e.getMessage());
            Log.e(TAG, "错误代码:" + e.getErrorCode());

            if (tencentLoginDialog.isShowing()) {
                tencentLoginDialog.dismiss();
            }

            Toast.makeText(LoginActivity.this, "授权失败！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.i(TAG, "QQ授权取消");

            if (tencentLoginDialog.isShowing()) {
                tencentLoginDialog.dismiss();
            }

            Toast.makeText(LoginActivity.this, "您已取消授权！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 友盟forQQ第三方登陆数据回调接口
     */
    private class QQ4UMDataListener implements SocializeListeners.UMDataListener {

        @Override
        public void onStart() {
            Log.i(TAG, "开始获取QQ数据");
        }

        @Override
        public void onComplete(int status, Map<String, Object> info) {
            Log.i(TAG, "QQ数据获取完成");

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
                Log.e(TAG, "QQ数据获取失败");
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

        Log.i(TAG, "openID:" + qq_open_id + ",nickName:" + qq_nick_Name + ",sex:" + qq_gender +
                ",headImage:" + qq_head_image_path + ",type:" + type + ",sign:" + sign);

        http.setParams(params);
        http.requestNetworkData();
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
            Log.i(TAG, "QQ登陆返回的信息:" + str);
            try {
                UserBack userBack = JsonUtil.getInstance().fromJSON(UserBack.class, str);
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

                    huanXinUsername = userBack.getData().getUserSign();
                    huanXinLogin();
                } else {
                    Log.e(TAG, "QQRequestCallBack:返回数据有误！");
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "QQ登陆数据解析错误:" + e.getMessage());
                Toast.makeText(LoginActivity.this,
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Log.i(TAG, msg);

            if (loginDialog != null && loginDialog.isShowing()) {
                loginDialog.dismiss();
            }

            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }

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
            Log.i(TAG, "微信授权开始");
            weChatLoadDialog.show();
        }

        @Override
        public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
            Log.i(TAG, "微信授权完成");
            Log.i(TAG, "微信授权数据:" + bundle.toString());
            mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, new WeChat4UMDataListener());
        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {
            Log.i(TAG, "微信授权错误:" + e.getMessage());
            Log.i(TAG, "微信授权错误码:" + e.getErrorCode());

            if (weChatLoadDialog.isShowing()) {
                weChatLoadDialog.dismiss();
            }

            Toast.makeText(LoginActivity.this, "授权失败！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.i(TAG, "微信授权取消");
            Toast.makeText(LoginActivity.this, "您已取消授权！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 友盟for微信第三方登陆数据回调接口
     */
    private class WeChat4UMDataListener implements SocializeListeners.UMDataListener {

        @Override
        public void onStart() {
            Log.i(TAG, "正在获取微信数据");
        }

        @Override
        public void onComplete(int status, Map<String, Object> info) {
            Log.i(TAG, "微信数据获取完成");

            if (weChatLoadDialog.isShowing()) {
                weChatLoadDialog.dismiss();
            }

            if (status == 200 && info != null) {
                Log.i(TAG, "微信数据:" + info.toString());
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
                Log.i(TAG, "微信数据获取失败");
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

        Log.i(TAG, "发送微信数据:[openId:" + weChatUnionId + ",nickname:" + weChatNickName
                + ",sex:" + weChatGender + ",headImg:" + weChatHeadImagePath + ",type:" + type + ",sign:" + sign + "]");

        SuHttpRequest http = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.ThirdPartyPath, new WXRequestCallBack());
        http.setParams(params);
        http.requestNetworkData();
    }

    /**
     * 发送微信数据数据到服务器的网络请求回调接口
     */
    private class WXRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            String str = stringResponseInfo.result;
            Log.i(TAG, "本地服务器返回的数据:" + str);

            try {
                UserBack userBack = JsonUtil.getInstance().fromJSON(UserBack.class, str);
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

                    huanXinUsername = userBack.getData().getUserSign();
                    huanXinLogin();
                } else {

                    if (weChatLoadDialog.isShowing()) {
                        weChatLoadDialog.dismiss();
                    }

                    Toast.makeText(LoginActivity.this, "登录失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "微信数据发送到服务器返回数据解析错误:" + e.getMessage());

                if (weChatLoadDialog.isShowing()) {
                    weChatLoadDialog.dismiss();
                }

                Toast.makeText(LoginActivity.this,
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onFailure(HttpException e, String s) {

            Log.e(TAG, "发送微信信息到服务器发生错误:" + s);

            if (weChatLoadDialog.isShowing()) {
                weChatLoadDialog.dismiss();
            }

            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * 发送微博相关数据到服务器
     *
     * @param user 相关数据实体类
     */
    private void setWeiBoData2Service(com.sina.weibo.sdk.openapi.models.User user) {
        if (user != null) {

            if (loginDialog != null) {
                loginDialog.show();
            }

            //微博用户UID
            String weiBoUserID = user.id;
            //微博用户名
            String weiBoUserName = user.screen_name;
            //微博头像地址
            String weiBoImagePath = user.avatar_large;
            //性别
            String weiBoGender = user.gender;

            SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                    HttpServicePath.ThirdPartyPath, new WeiBoRequestCallBack());

            String code;

            switch (weiBoGender) {
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
            params.addBodyParameter("openId", weiBoUserID);
            params.addBodyParameter("nickname", weiBoUserName);
            params.addBodyParameter("sex", code);
            params.addBodyParameter("headImg", weiBoImagePath);
            params.addBodyParameter("type", type);

            String sign = null;
            try {
                sign = MD5Utils.getMD5(weiBoUserID + type + HttpServicePath.ConfusedCode);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            params.addBodyParameter("sign", sign);


            Log.i(TAG, "openID:" + weiBoUserID + ",nickName:" + weiBoUserName + ",sex:" + code +
                    ",headImage:" + weiBoImagePath + ",type:" + type + ",sign:" + sign);

            SuiuuInfo.WriteInformation(LoginActivity.this, new RequestData(weiBoUserID, weiBoUserName,
                    code, weiBoImagePath, type));

            httpRequest.setParams(params);
            httpRequest.requestNetworkData();
        } else {
            Toast.makeText(LoginActivity.this, "数据获取失败，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
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
            UserBack userBack = JsonUtil.getInstance().fromJSON(UserBack.class, str);

            if (userBack != null) {
                if (userBack.status.equals("1")) {
                    SuiuuInfo.WriteVerification(LoginActivity.this, userBack.getMessage());
                    SuiuuInfo.WriteUserSign(LoginActivity.this, userBack.getData().getUserSign());
                    huanXinUsername = userBack.getData().getUserSign();
                    huanXinLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "数据获取失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "网络错误，请稍候再试！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Log.i(TAG, msg);

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
        if (autoLogin) {
            return;
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