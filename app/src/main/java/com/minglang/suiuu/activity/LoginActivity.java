package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
import com.minglang.suiuu.entity.QQInfo;
import com.minglang.suiuu.entity.RequestData;
import com.minglang.suiuu.entity.UserBack;
import com.minglang.suiuu.thread.QQThread;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.MD5Utils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInformation;
import com.minglang.suiuu.utils.qq.TencentUtil;
import com.minglang.suiuu.utils.weibo.WeiboAccessTokenKeeper;
import com.minglang.suiuu.utils.weibo.WeiboConstants;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录页面
 */
public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();

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

    //注册PopupWindow

    private View popupRegisterView;

    private PopupWindow popupWindowRegister;

    private EditText popupRegisterUserName;

    private EditText popupRegisterPassword1;

    private Button popupRegisterBtn;

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
     * 注册用户名
     */
    private String suiuuRegisterUserName;

    /**
     * 注册密码
     */
    private String suiuuRegisterPassword;

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

    //微博相关类实例
    /**
     * 授权认证所需的信息
     */
    private AuthInfo authInfo;
    /**
     * 微博授权认证回调
     */
    private AuthListener authListener = new AuthListener();

    /**
     * SSO 授权认证实例
     */
    private SsoHandler ssoHandler;

    /**
     * 微博Token
     */
    private Oauth2AccessToken accessToken;

    /**
     * 用户信息接口
     */
    private UsersAPI usersAPI;

    /**
     * 微博 OpenAPI 回调接口。
     */
    private WeiBoRequestListener weiboRequestListener = new WeiBoRequestListener();


    //QQ相关类实例
    private static Tencent tencent;

    /**
     * QQ 用户信息类
     */
    private com.tencent.connect.UserInfo qqUserInfo;

    /**
     * 自定义QQ用户信息类
     */
    private QQInfo qqInfo;

    /**
     * 第三方登陆类型
     */
    private String type;

    private ProgressDialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tencent = Tencent.createInstance("1104557000", this.getApplicationContext());

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
        ViewAction();
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
                popupWindowRegister.showAtLocation(popupRegisterView,
                        Gravity.CENTER_HORIZONTAL, 0, 0);
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

        popupRegisterBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!CommonUtils.isNetWorkConnected(LoginActivity.this)) {
                    Toast.makeText(LoginActivity.this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
                    return;
                }

                suiuuRegisterUserName = popupRegisterUserName.getText().toString().trim();
                suiuuRegisterPassword = popupRegisterPassword1.getText().toString().trim();

                if (TextUtils.isEmpty(suiuuRegisterUserName)) {
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(suiuuRegisterPassword)) {
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                register4Suiuu();
            }
        });

        microBlog_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "3";
                ssoHandler.authorize(authListener);
            }
        });

        qq_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "1";
                if (!tencent.isSessionValid()) {//检测是否已登录
                    tencent.login(LoginActivity.this, "all", loginListener);
                }
            }
        });

        weChat_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "2";
            }
        });

    }

    //TODO 注册还未完成

    /**
     * 注册方法
     */
    private void register4Suiuu() {

        RequestParams params = new RequestParams();


        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST, "", new RegisterRequestCallback());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 注册回调接口
     */
    private class RegisterRequestCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

        }

        @Override
        public void onFailure(HttpException e, String s) {

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
            Log.i(TAG, str);
            try {
                UserBack userBack = JsonUtil.getInstance().fromJSON(UserBack.class, str);
                Log.i(TAG, userBack.toString());
                if (userBack != null) {
                    if (userBack.getStatus().equals("1")) {
                        SuiuuInformation.WriteVerification(LoginActivity.this, userBack.getMessage());
                        huanXinUsername = userBack.getData().getUserSign();
                        huanXinLogin();
                    } else {
                        Toast.makeText(LoginActivity.this, "获取数据失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                Toast.makeText(LoginActivity.this, "获取数据失败，请稍候再试！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Log.e(TAG, msg);

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
            boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(DemoApplication.currentUserNick.trim());
            if (!updatenick) {
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
        authInfo = new AuthInfo(this, WeiboConstants.APP_KEY, WeiboConstants.REDIRECT_URL, WeiboConstants.SCOPE);
        ssoHandler = new SsoHandler(this, authInfo);
        if (accessToken != null) {
            usersAPI = new UsersAPI(this, WeiboConstants.APP_KEY, accessToken);
        } else {
            usersAPI = new UsersAPI(this, WeiboConstants.APP_KEY, WeiboAccessTokenKeeper.readAccessToken(this));
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("InflateParams")
    private void initView() {

        DisplayMetrics dm = getResources().getDisplayMetrics();

        float density = dm.density;//屏幕密度（像素比例：0.75, 1.0, 1.5, 2.0）
        int densityDPI = dm.densityDpi;//屏幕密度（每寸像素：120, 160, 240, 320）

        int screenWidth = dm.widthPixels;//屏幕宽度

        Log.i(TAG, "像素比例:" + String.valueOf(density));
        Log.i(TAG, "每寸像素:" + String.valueOf(densityDPI));

        loginDialog = new ProgressDialog(this);
        loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loginDialog.setCanceledOnTouchOutside(false);
        loginDialog.setCancelable(true);
        loginDialog.setMessage(getResources().getString(R.string.login_wait));

        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);

        /**
         * ***************************分割线***************************
         */

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

        popupRegisterView = LayoutInflater.from(this).inflate(R.layout.popup_register, null);

        popupRegisterUserName = (EditText) popupRegisterView.findViewById(R.id.registerUserInfo);
        popupRegisterPassword1 = (EditText) popupRegisterView.findViewById(R.id.registerPassword1);

        popupRegisterBtn = (Button) popupRegisterView.findViewById(R.id.registerBtn);

        ViewGroup.LayoutParams registerParams = popupRegisterBtn.getLayoutParams();
        registerParams.width = screenWidth / 3;
        popupRegisterBtn.setLayoutParams(registerParams);

        popupWindowRegister = new PopupWindow(popupRegisterView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindowRegister.setBackgroundDrawable(new BitmapDrawable());

        microBlog_login = (ImageView) findViewById(R.id.microBlog_login);
        qq_login = (ImageView) findViewById(R.id.qq_login);
        weChat_login = (ImageView) findViewById(R.id.weChat_login);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        tencent.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {

        if (popupWindowLogin.isShowing()) {
            popupWindowLogin.dismiss();
        } else if (popupWindowRegister.isShowing()) {
            popupWindowRegister.dismiss();
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

    //↓↓↓微博登陆的一系列相关方法↓↓↓

    /**
     * 微博用户UID
     */
    private String WeiBoUserID;

    /**
     * 微博用户名
     */
    private String WeiBoUserName;

    /**
     * 微博头像地址
     */
    private String WeiBoImagePath;

    /**
     * 性别
     */
    private String WeiBoGender;

    /**
     * 授权结果回调
     */
    private class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle bundle) {
            accessToken = Oauth2AccessToken.parseAccessToken(bundle);
            if (accessToken != null && accessToken.isSessionValid()) {
                WeiboAccessTokenKeeper.writeAccessToken(LoginActivity.this, accessToken);

                long uid = Long.parseLong(accessToken.getUid());

                usersAPI.show(uid, weiboRequestListener);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "您已取消授权", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 微博 OpenAPI 回调接口。
     */
    private class WeiBoRequestListener implements com.sina.weibo.sdk.net.RequestListener {

        @Override
        public void onComplete(String s) {
            if (!TextUtils.isEmpty(s)) {
                com.sina.weibo.sdk.openapi.models.User user = com.sina.weibo.sdk.openapi.models.User.parse(s);
                setWeiBoData2Service(user);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            ErrorInfo info = ErrorInfo.parse(e.getMessage());
            Toast.makeText(LoginActivity.this, info.toString(), Toast.LENGTH_SHORT).show();
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

            WeiBoUserID = user.id;
            WeiBoUserName = user.screen_name;
            WeiBoImagePath = user.avatar_large;
            WeiBoGender = user.gender;

            SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                    HttpServicePath.ThirdPartyPath, new WeiBoRequestCallBack());

            String code;

            switch (WeiBoGender) {
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
            params.addBodyParameter("openId", WeiBoUserID);
            params.addBodyParameter("nickname", WeiBoUserName);
            params.addBodyParameter("sex", code);
            params.addBodyParameter("headImg", WeiBoImagePath);
            params.addBodyParameter("type", type);

            String sign = null;
            try {
                sign = MD5Utils.getMD5(WeiBoUserID + type + HttpServicePath.ConfusedCode);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            params.addBodyParameter("sign", sign);


            Log.i(TAG, "openID:" + WeiBoUserID + ",nickName:" + WeiBoUserName + ",sex:" + code +
                    ",headImage:" + WeiBoImagePath + ",type:" + type + ",sign:" + sign);

            SuiuuInformation.WriteInformation(LoginActivity.this, new RequestData(WeiBoUserID, WeiBoUserName,
                    code, WeiBoImagePath, type));

            httpRequest.setParams(params);
            httpRequest.requestNetworkData();
        } else {
            Toast.makeText(LoginActivity.this, "数据获取失败，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    //↑↑↑微博登陆的一系列相关方法↑↑↑


    //↓↓↓QQ登陆的一系列相关方法↓↓↓

    /**
     * 发送QQ相关信息到服务器
     */
    private void setQQData2Service() {
        if (qqInfo != null) {

            if (loginDialog != null) {
                loginDialog.show();
            }

            String qqNickName = qqInfo.getNickName();
            String qqImagePath = qqInfo.getImagePath();
            String gender = qqInfo.getGender();

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

            SuHttpRequest http = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                    HttpServicePath.ThirdPartyPath, new QQRequestCallBack());

            RequestParams params = new RequestParams();
            params.addBodyParameter("openId", qqOpenId);
            params.addBodyParameter("nickname", qqNickName);
            params.addBodyParameter("sex", code);
            params.addBodyParameter("headImg", qqImagePath);
            params.addBodyParameter("type", type);

            String sign = null;
            try {
                sign = MD5Utils.getMD5(qqOpenId + type + HttpServicePath.ConfusedCode);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            params.addBodyParameter("sign", sign);

            Log.i(TAG, "openID:" + qqOpenId + ",nickName:" + qqNickName + ",sex:" + code +
                    ",headImage:" + qqImagePath + ",type:" + type + ",sign:" + sign);

            SuiuuInformation.WriteInformation(LoginActivity.this, new RequestData(qqOpenId, qqNickName, code, qqImagePath, type));

            http.setParams(params);
            http.requestNetworkData();
        } else {
            Toast.makeText(LoginActivity.this, "获取信息失败，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * QQ
     * <p/>
     * 异步获取相关用户信息
     */
    private Handler qqHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    qqInfo = (QQInfo) msg.obj;
                    setQQData2Service();
                    break;
            }
            return false;
        }
    });

    /**
     * QQ
     * <p/>
     * 回调接口实例
     */
    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            initOpenidAndToken(values);
            getUserNickNameAndHeadImage();
        }
    };


    /**
     * QQ
     * <p/>
     * 获取昵称等信息
     */
    private void getUserNickNameAndHeadImage() {
        if (tencent != null && tencent.isSessionValid()) {
            IUiListener iUiListener = new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    QQThread qqThread = new QQThread(qqHandler, o);
                    qqThread.start();
                }

                @Override
                public void onError(UiError uiError) {
                    Toast.makeText(LoginActivity.this,
                            "获取信息失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {

                }
            };
            qqUserInfo = new com.tencent.connect.UserInfo(LoginActivity.this, tencent.getQQToken());
            qqUserInfo.getUserInfo(iUiListener);
        }
    }

    /**
     * QQ
     * <p/>
     * 相关凭据
     */
    private String qq_token;
    private String qq_expires;
    private String qqOpenId;

    /**
     * QQ
     * <p/>
     * 初始化token，openid等信息
     *
     * @param jsonObject 接口返回的Json数据
     */
    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            qq_token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            qq_expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            qqOpenId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(qq_token) && !TextUtils.isEmpty(qq_expires)
                    && !TextUtils.isEmpty(qqOpenId)) {
                tencent.setAccessToken(qq_token, qq_expires);
                tencent.setOpenId(qqOpenId);
            }
        } catch (Exception ignored) {
            Log.e(TAG, ignored.getMessage());
        }
    }

    /**
     * QQ
     * <p/>
     * 回调接口
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                TencentUtil.showResultDialog(LoginActivity.this, "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (jsonResponse != null && jsonResponse.length() == 0) {
                TencentUtil.showResultDialog(LoginActivity.this, "返回为空", "登录失败");
                return;
            }
            //TencentUtil.showResultDialog(LoginActivity.this, response.toString(), "登录成功");

            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {
            Log.d(TAG, values.toString());
        }

        @Override
        public void onError(UiError uiError) {
            TencentUtil.toastMessage(LoginActivity.this, "onError: " + uiError.errorDetail);
            TencentUtil.dismissDialog();
        }

        @Override
        public void onCancel() {
            TencentUtil.toastMessage(LoginActivity.this, "onCancel: ");
            TencentUtil.dismissDialog();
        }
    }

    //↑↑↑QQ登陆的一系列相关方法↑↑↑

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
            Log.i(TAG, str);
            UserBack userBack = JsonUtil.getInstance().fromJSON(UserBack.class, str);

            if (userBack != null) {
                if (userBack.status.equals("1")) {
                    SuiuuInformation.WriteVerification(LoginActivity.this, userBack.getMessage());

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
                    SuiuuInformation.WriteVerification(LoginActivity.this, userBack.getMessage());

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


}