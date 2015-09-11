package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
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
import com.umeng.socialize.weixin.controller.UMWXHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：登录主界面
 * 创建人：Administrator
 * 创建时间：2015/9/10 14:08
 * 修改人：Administrator
 * 修改时间：2015/9/10 14:08
 * 修改备注：
 */
public class LoginsMainActivity extends BaseActivity {
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
    private static final String UNION_ID = "unionID";
    private static final String OPEN_ID_ = "openid";
    private static final String NICK_NAME = "nickname";
    private static final String SEX = "sex";
    private static final String HEAD_IMG = "headImg";
    private static final String TYPE = "type";
    private static final String SIGN = "sign";

    private static final String DATA = "data";


    @BindString(R.string.NoInstallWeChat)
    String NoInstallWeChat;
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
    @BindString(R.string.SystemException)
    String SystemException;
    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkAnomaly;
    @BindString(R.string.NoData)
    String NoData;
    /**
     * 退出
     */
    @Bind(R.id.iv_exit)
    ImageView iv_exit;
    /**
     * 登录按钮
     */
    @Bind(R.id.bt_login)
    TextView bt_login;
    /**
     * 注册按钮
     */
    @Bind(R.id.bt_register)
    TextView bt_register;
    /**
     * 新浪登录
     */
    @Bind(R.id.iv_sina)
    ImageView iv_sina;
    /**
     * 微信登陆
     */
    @Bind(R.id.iv_wechat)
    ImageView iv_wechat;
    /**
     * qq登录
     */
    @Bind(R.id.iv_qq)
    ImageView iv_qq;
    /**
     * 第三方登陆类型
     */
    private String type;

    private IWXAPI weChatApi;
    private UMSocialService mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logins);
        ButterKnife.bind(this);
        initView();
        viewAction();
    }

    private void initView() {
        initThirdParty();
    }

    private void viewAction() {
        iv_exit.setOnClickListener(new MyOnClickListener());
        bt_login.setOnClickListener(new MyOnClickListener());
        bt_register.setOnClickListener(new MyOnClickListener());
        iv_sina.setOnClickListener(new MyOnClickListener());
        iv_wechat.setOnClickListener(new MyOnClickListener());
        iv_qq.setOnClickListener(new MyOnClickListener());
    }

    /**
     * 初始化第三方相关实例
     */
    private void initThirdParty() {
        weChatApi = WXAPIFactory.createWXAPI(this, WeChatConstant.APP_ID, false);
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_exit:
                    finish();
                    break;
                case R.id.bt_login:
                    startActivity(new Intent(LoginsMainActivity.this, LoginsActivity.class));
                    break;
                case R.id.bt_register:
                    startActivity(new Intent(LoginsMainActivity.this, RegisterActivity.class));
                    break;
                case R.id.iv_sina:
                    type = "3";
                    mController.doOauthVerify(LoginsMainActivity.this, SHARE_MEDIA.SINA, new MicroBlog4UMAuthListener());
                    break;
                case R.id.iv_wechat:
                    type = "2";
                    if (!weChatApi.isWXAppInstalled()) {
                        Toast.makeText(LoginsMainActivity.this, NoInstallWeChat, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    UMWXHandler wxHandler = new UMWXHandler(LoginsMainActivity.this, WeChatConstant.APP_ID, WeChatConstant.APPSECRET);
                    wxHandler.addToSocialSDK();
                    mController.doOauthVerify(LoginsMainActivity.this, SHARE_MEDIA.WEIXIN, new WeChat4UMAuthListener());
                    break;
                case R.id.iv_qq:
                    type = "1";
                    UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginsMainActivity.this, TencentConstant.APP_ID, TencentConstant.APP_KEY);
                    qqSsoHandler.addToSocialSDK();
                    mController.doOauthVerify(LoginsMainActivity.this, SHARE_MEDIA.QQ, new QQLogin4UMAuthListener());
                    break;
                default:
                    break;
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
            DeBugLog.i(TAG, "QQ授权开始");
        }

        @Override
        public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "QQ授权完成");
            Toast.makeText(LoginsMainActivity.this, QQAuthorizedComplete, Toast.LENGTH_SHORT).show();

            if (bundle != null) {
                qq_open_id = bundle.getString(OPEN_ID_);
            }
            mController.getPlatformInfo(LoginsMainActivity.this, SHARE_MEDIA.QQ, new QQ4UMDataListener());
        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "QQ授权错误");
            DeBugLog.e(TAG, "QQ登陆错误:" + e.getMessage() + ",错误代码:" + e.getErrorCode());
            Toast.makeText(LoginsMainActivity.this, QQAuthorizedError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "QQ授权取消");
            Toast.makeText(LoginsMainActivity.this, QQAuthorizedCancel, Toast.LENGTH_SHORT).show();
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
                SuiuuInfo.WriteQuicklyLoginInfo(LoginsMainActivity.this, qq_open_id, qq_nick_Name, "1", qq_head_image_path);
//                enterMainBind(qq_head_image_path, qq_nick_Name,"1",qq_open_id);
                sendQQInfo2Service();
            } else {
                DeBugLog.e(TAG, "QQ数据获取失败");
                Toast.makeText(LoginsMainActivity.this, "数据获取失败,请重试！", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * 友盟for微信第三方登陆授权回调接口
     */
    private class WeChat4UMAuthListener implements SocializeListeners.UMAuthListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "微信授权开始");
        }

        @Override
        public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "微信授权完成");
            Toast.makeText(LoginsMainActivity.this, WeChatAuthorizedComplete, Toast.LENGTH_SHORT).show();
            mController.getPlatformInfo(LoginsMainActivity.this, SHARE_MEDIA.WEIXIN, new WeChat4UMDataListener());
        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "微信授权错误:" + e.getMessage() + ",微信授权错误码:" + e.getErrorCode());
            Toast.makeText(LoginsMainActivity.this, WeChatAuthorizedError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "微信授权取消");
            Toast.makeText(LoginsMainActivity.this, WeChatAuthorizedCancel, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 友盟for微博第三方登陆授权回调接口
     */
    private class MicroBlog4UMAuthListener implements SocializeListeners.UMAuthListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "新浪微博授权开始！");
            Toast.makeText(LoginsMainActivity.this, WeiboAuthorizedStart, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "新浪微博授权完成！");
            if (bundle != null && !TextUtils.isEmpty(bundle.getString("uid"))) {
                DeBugLog.i(TAG, "新浪微博授权成功！");
                DeBugLog.i(TAG, "新浪微博授权信息:" + bundle.toString());
                Toast.makeText(LoginsMainActivity.this, WeiboAuthorizedComplete, Toast.LENGTH_SHORT).show();
                mController.getPlatformInfo(LoginsMainActivity.this, SHARE_MEDIA.SINA, new MicroBlog4UMDataListener());
            } else {
                DeBugLog.i(TAG, "新浪微博授权失败！");
                Toast.makeText(LoginsMainActivity.this, WeiboAuthorizedFailure, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {
            DeBugLog.e(TAG, "新浪微博授权错误！" + e.getMessage());
            DeBugLog.e(TAG, "新浪微博错误代码1:" + e.getErrorCode());
            DeBugLog.e(TAG, "新浪微博错误代码2::" + String.valueOf(share_media.getReqCode()));
            Toast.makeText(LoginsMainActivity.this, WeiboAuthorizedError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            DeBugLog.i(TAG, "新浪微博授权取消！");
            Toast.makeText(LoginsMainActivity.this, WeiboAuthorizedCancel, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 微信token，微信用户昵称，微信用户性别，微信用户openID，微信用户uID，微信用户头像URL
     */
    private String wechat_nick_name, wechat_gender, wechat_union_id, wechat_head_image_path;

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
            if (status == 200 && info != null) {
                DeBugLog.i(TAG, "微信数据:" + info.toString());
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

                SuiuuInfo.WriteWeChatInfo(LoginsMainActivity.this, wechat_union_id, wechat_nick_name);
                SuiuuInfo.WriteQuicklyLoginInfo(LoginsMainActivity.this, wechat_union_id, wechat_nick_name, "2", wechat_head_image_path);
//                enterMainBind(wechat_head_image_path, wechat_nick_name, "2", wechat_union_id);
                sendWeChatInfo2Service();
            } else {
                DeBugLog.i(TAG, "微信数据获取失败");
            }
        }

    }

    private String weibo_open_id, weibo_name, weibo_gender, weibo_head_img;

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
                SuiuuInfo.WriteQuicklyLoginInfo(LoginsMainActivity.this, weibo_open_id, weibo_name, "3", weibo_head_img);
//                enterMainBind(weibo_head_img, weibo_name, "3", weibo_open_id);
                sendWeiBoInfo2Service();
            } else {
                DeBugLog.e(TAG, "发生错误，未接收到数据！");
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
        Log.i("suiuu","openId"+qq_open_id);
        paramsArray[0] = new OkHttpManager.Params(UNION_ID, qq_open_id);
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
        }
    }

    private class TencentResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            Log.i("suiuu", "使用QQ登录返回的数据:" + response);
            try {
                UserBack userBack = JsonUtils.getInstance().fromJSON(UserBack.class, response);
                if (userBack.getStatus().equals("1")) {

                    String message = userBack.getMessage();
                    if (!TextUtils.isEmpty(message)) {
                        SuiuuInfo.WriteVerification(LoginsMainActivity.this, message);
                    }

                    if (userBack.getData() != null) {
                        String userSign = userBack.getData().getUserSign();
                        Log.i("suiuu","userSign"+userSign);
                        if (!TextUtils.isEmpty(userBack.getData().getUserSign())) {
                            SuiuuInfo.WriteUserSign(LoginsMainActivity.this, userSign);
                        }

                        SuiuuInfo.WriteUserSign(LoginsMainActivity.this, userBack.getData().getIntro());
                        SuiuuInfo.WriteUserData(LoginsMainActivity.this, userBack.getData());
                        startActivity(new Intent(LoginsMainActivity.this, MainActivity.class));
                    }else {
                        enterMainBind(qq_head_image_path, qq_nick_Name,"1",qq_open_id);
                    }
                } else {
                    DeBugLog.e(TAG, "TencentResultCallback:返回数据有误！");
                    Toast.makeText(LoginsMainActivity.this, DataError, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "QQ登陆数据解析错误:" + e.getMessage());
                Toast.makeText(LoginsMainActivity.this, DataError, Toast.LENGTH_SHORT).show();
                AbnormalHandle(response, DataError);
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "QQ登陆数据解析错误:" + e.getMessage());
            Toast.makeText(LoginsMainActivity.this, DataError, Toast.LENGTH_SHORT).show();
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
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.ThirdPartyPath, new WeChatResultCallback(), paramsArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送微博相关数据到服务器
     */
    private void sendWeiBoInfo2Service() {
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

        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[6];
        paramsArray[0] = new OkHttpManager.Params(UNION_ID, weibo_open_id);
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
        }
    }

    private class WeiBoResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            Log.i("suiuu", response);
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(LoginsMainActivity.this, NoData, Toast.LENGTH_SHORT).show();
            } else {
                try {
                    if (!TextUtils.isEmpty(response)) {
                        UserBack userBack = JsonUtils.getInstance().fromJSON(UserBack.class, response);
                        if (userBack != null) {
                            if (userBack.getStatus().equals("1")) {
                                SuiuuInfo.WriteVerification(LoginsMainActivity.this, userBack.getMessage());
                                SuiuuInfo.WriteUserSign(LoginsMainActivity.this, userBack.getData().getUserSign());
                                if (userBack.getData() != null) {
                                    startActivity(new Intent(LoginsMainActivity.this,MainActivity.class));
                                }else {
                                    enterMainBind(weibo_head_img, weibo_name, "3", weibo_open_id);
                                }
                            } else {
                                Toast.makeText(LoginsMainActivity.this, "数据获取失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginsMainActivity.this, NetworkAnomaly, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginsMainActivity.this, NoData, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(LoginsMainActivity.this, NetworkAnomaly, Toast.LENGTH_SHORT).show();
        }

    }

    private class WeChatResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            Log.i("suiuu", "服务器返回的数据:" + response);
            try {
                UserBack userBack = JsonUtils.getInstance().fromJSON(UserBack.class, response);
                if (userBack.getStatus().equals("1")) {

                    String message = userBack.getMessage();
                    if (!TextUtils.isEmpty(message)) {
                        SuiuuInfo.WriteVerification(LoginsMainActivity.this, message);
                    }

                    String userSign = userBack.getData().getUserSign();
                    if (!TextUtils.isEmpty(userSign)) {
                        SuiuuInfo.WriteUserSign(LoginsMainActivity.this, userSign);
                    }

                    SuiuuInfo.WriteUserSign(LoginsMainActivity.this, userBack.getData().getIntro());
                    SuiuuInfo.WriteUserData(LoginsMainActivity.this, userBack.getData());
                    if (userBack.getData() != null) {
                        startActivity(new Intent(LoginsMainActivity.this,MainActivity.class));
                    }else {
                        enterMainBind(wechat_head_image_path, wechat_nick_name, "2", wechat_union_id);
                    }
                } else {
                    Toast.makeText(LoginsMainActivity.this, "登录失败，请重试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "微信数据发送到服务器返回数据解析错误:" + e.getMessage());
                AbnormalHandle(response, DataError);
            }

        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "发送微信信息到服务器发生错误:" + e.getMessage());
            Toast.makeText(LoginsMainActivity.this, NetworkAnomaly, Toast.LENGTH_SHORT).show();
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

    /**
     * 三方登录后跳入绑定的主界面
     *
     * @param headImage
     * @param nickName
     */
    public void enterMainBind(String headImage, String nickName, String type, String openId) {
        Intent intent = new Intent(this, ActivityMainBind.class);
        intent.putExtra("headImage", headImage);
        intent.putExtra("nickName", nickName);
        intent.putExtra("type", type);
        intent.putExtra("openId", openId);
        startActivity(intent);

    }
}
