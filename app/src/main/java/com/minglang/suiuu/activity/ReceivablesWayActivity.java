package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.ReceivablesWayAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.AccountInfo;
import com.minglang.suiuu.entity.AccountInfo.AccountInfoData;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.wechat.WeChatConstant;
import com.squareup.okhttp.Request;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 管理收款方式页面
 */
public class ReceivablesWayActivity extends BaseAppCompatActivity {

    private static final String TAG = ReceivablesWayActivity.class.getSimpleName();

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private static final String ACCOUNT_ID = "accountId";

    private static final String UNION_ID = "unionid";
    private static final String NICK_NAME = "nickname";

    private static final String OPEN_ID = "openId";
    private static final String NAME = "name";

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NetworkError)
    String NetworkError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkException;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.ConfirmDelete)
    String ConfirmDelete;

    @BindString(R.string.DeleteFailure)
    String DeleteFailure;

    @BindString(R.string.NoInstallWeChat)
    String NoInstallWeChat;

    @BindString(R.string.WeChatAuthorizedComplete)
    String WeChatAuthorizedComplete;

    @BindString(R.string.WeChatAuthorizedError)
    String WeChatAuthorizedError;

    @BindString(R.string.WeChatAuthorizedCancel)
    String WeChatAuthorizedCancel;

    @BindString(R.string.ObtainWeChatAuthorization)
    String ObtainWeChatAuthorization;

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.receivables_way_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.receivables_way_list_view)
    ListView receivablesWayListView;

    @Bind(R.id.receivables_way_hint_layout)
    RelativeLayout relativeLayout;

    private PopupWindow addPopupWindow;

    private View addRootView;

    private Button alipayButton;

    private Button weChatButton;

    private ProgressDialog progressDialog;

    private List<AccountInfoData> listAll = new ArrayList<>();

    private ReceivablesWayAdapter adapter;

    private long index = 0;

    private IWXAPI weChatApi;

    private UMSocialService mController;

    private Context context;

    private ProgressDialog weChatLoadDialog;

    private String openId, nickName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivables_way);
        ButterKnife.bind(this);
        initView();
        viewAction();
        sendRequest();
    }

    private void initView() {
        context = ReceivablesWayActivity.this;

        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        weChatLoadDialog = new ProgressDialog(this);
        weChatLoadDialog.setCanceledOnTouchOutside(false);
        weChatLoadDialog.setCancelable(true);
        weChatLoadDialog.setMessage(ObtainWeChatAuthorization);

        initPopupWindow();

        adapter = new ReceivablesWayAdapter(this, listAll, R.layout.item_receivables_way);
        receivablesWayListView.setAdapter(adapter);

        token = SuiuuInfo.ReadAppTimeSign(this);

        weChatApi = WXAPIFactory.createWXAPI(this, WeChatConstant.APP_ID, false);
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
    }

    @SuppressLint("InflateParams")
    @SuppressWarnings("deprecation")
    private void initPopupWindow() {
        addRootView = LayoutInflater.from(this).inflate(R.layout.popup_add_receivables, null);
        alipayButton = (Button) addRootView.findViewById(R.id.popup_add_receivables_alipay);
        weChatButton = (Button) addRootView.findViewById(R.id.popup_add_receivables_wechat);

        addPopupWindow = new PopupWindow(addRootView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        addPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        addPopupWindow.setAnimationStyle(R.style.time_popup_window_anim_style);
        addPopupWindow.setOutsideTouchable(false);
    }

    /**
     * 控件动作
     */
    private void viewAction() {

        alipayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPopupWindow.dismiss();
                startActivityForResult(new Intent(context, AddReceivablesWayActivity.class), AppConstant.ADD_ALIPAY_WAY);
            }
        });

        weChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPopupWindow.dismiss();

                Map<String, String> map = SuiuuInfo.ReadWeChatInfo(context);
                if (map != null && map.size() > 0) {
                    String openId = map.get(SuiuuInfo.W_ONLY_ID);
                    String nickName = map.get(SuiuuInfo.W_NICK_NAME);
                    if (TextUtils.isEmpty(openId)) {
                        if (!weChatApi.isWXAppInstalled()) {
                            Toast.makeText(context, NoInstallWeChat, Toast.LENGTH_SHORT).show();
                        } else {
                            UMWXHandler wxHandler = new UMWXHandler(context, WeChatConstant.APP_ID, WeChatConstant.APPSECRET);
                            wxHandler.addToSocialSDK();

                            mController.doOauthVerify(context, SHARE_MEDIA.WEIXIN, new ObtainWeChat4UmAuthListener());
                        }
                    } else {
                        sendWeChatDataRequest(openId, nickName);
                    }
                }
            }
        });

        weChatButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SuiuuInfo.ClearWeChatInfo(context);
                return false;
            }
        });

        adapter.setOnDeleteReceivablesItemListener(new ReceivablesWayAdapter.OnDeleteReceivablesItemListener() {
            @Override
            public void onDeleteReceivablesItem(long position) {
                index = position;
                new AlertDialog.Builder(context)
                        .setMessage(ConfirmDelete)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String accountId = listAll.get(Long.bitCount(index)).getAccountId();
                                sendDeleteRequest(accountId);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .create().show();
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

    }

    /**
     * 发送获取收款列表数据的网络请求
     */
    private void sendRequest() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        relativeLayout.setVisibility(View.GONE);

        try {
            String url = HttpNewServicePath.getUserBindAccountListData + "?" + TOKEN + "=" + URLEncoder.encode(token, "UTF-8");
            L.i(TAG, "Request URL:" + url);
            OkHttpManager.onGetAsynRequest(url, new ReceivablesWayResultCallback());
        } catch (Exception e) {
            e.printStackTrace();
            hideDialog();
            relativeLayout.setVisibility(View.VISIBLE);
            Toast.makeText(context, NetworkException, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 隐藏请求Dialog
     */
    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    /**
     * 绑定收款方式数据到View
     *
     * @param str JSON数据
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            relativeLayout.setVisibility(View.VISIBLE);
            Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
        } else {
            try {
                AccountInfo accountInfo = JsonUtils.getInstance().fromJSON(AccountInfo.class, str);
                List<AccountInfoData> list = accountInfo.getData();
                if (list != null && list.size() > 0) {
                    listAll.addAll(list);
                    adapter.setList(listAll);

                    for (AccountInfoData data : listAll) {
                        if (data.getType().equals("1")) {
                            weChatButton.setEnabled(false);
                            SuiuuInfo.WriteWeChatInfo(context, data.getAccount(), data.getUsername());
                            SuiuuInfo.WriteWeChatAccountId(context, data.getAccountId());
                        } else if (data.getType().equals("2")) {
                            alipayButton.setEnabled(false);
                            SuiuuInfo.WriteAliPayInfo(context, data.getAccount(), data.getUsername());
                            SuiuuInfo.WriteAliPayAccountId(context, data.getAccountId());
                        }
                    }

                } else {
                    relativeLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                L.e(TAG, "数据解析错误:" + e.getMessage());
                relativeLayout.setVisibility(View.VISIBLE);
                try {
                    JSONObject object = new JSONObject(str);
                    String status = object.getString(STATUS);
                    if (status.equals("-1")) {
                        Toast.makeText(context, SystemException, Toast.LENGTH_SHORT).show();
                    } else if (status.equals("-2")) {
                        Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    /**
     * 发送删除单项收款方式的网络请求
     *
     * @param accountId 相关ID
     */
    private void sendDeleteRequest(String accountId) {
        try {
            String url = HttpNewServicePath.deleteUserBindAccountItemData + "?" + TOKEN + "=" + URLEncoder.encode(token, "UTF-8");
            OkHttpManager.onPostAsynRequest(url, new DeleteWayResultCallback(), new OkHttpManager.Params(ACCOUNT_ID, accountId));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 发送微信相关数据到服务器
     *
     * @param openId   微信唯一ID
     * @param nickName 用户昵称
     */
    private void sendWeChatDataRequest(String openId, String nickName) {
        try {
            OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[2];
            paramsArray[0] = new OkHttpManager.Params(OPEN_ID, openId);
            paramsArray[1] = new OkHttpManager.Params(NAME, nickName);

            String url = HttpNewServicePath.addWeChatAUserInfo + "?" + TOKEN + "=" + URLEncoder.encode(token, "UTF-8");
            L.i(TAG, "微信绑定接口:" + url);

            OkHttpManager.onPostAsynRequest(url, new BindWeChat2SuiuuListener(), paramsArray);
        } catch (Exception e) {
            L.e(TAG, "网络请求过程发生异常:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 隐藏微信授权进度Dialog
     */
    private void hideWeChatDialog() {
        if (weChatLoadDialog != null && weChatLoadDialog.isShowing()) {
            weChatLoadDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppConstant.ADD_ALIPAY_WAY:
                    sendRequest();
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_receivables_way, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;

            case R.id.add_receivables:
                addPopupWindow.showAtLocation(addRootView, Gravity.BOTTOM, 0, 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取收款方式列表数据-回调接口
     */
    private class ReceivablesWayResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "返回的数据:" + response);
            hideDialog();
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "网络请求失败:" + e.getMessage());
            hideDialog();
            relativeLayout.setVisibility(View.VISIBLE);
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 删除单项收款方式回调接口
     */
    private class DeleteWayResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "删除返回信息:" + response);
            try {
                JSONObject object = new JSONObject(response);
                String status = object.getString(STATUS);
                if (!TextUtils.isEmpty(status))
                    switch (status) {
                        case "1":
                            listAll.remove(Long.bitCount(index));
                            adapter.notifyDataSetChanged();
                            sendRequest();
                            break;
                        case "-1":
                            Toast.makeText(context, SystemException, Toast.LENGTH_SHORT).show();
                            break;
                        case "-2":
                            Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                            break;
                    }
            } catch (Exception e) {
                L.e(TAG, "解析失败:" + e.getMessage());
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "删除失败:" + e.getMessage());
            Toast.makeText(context, DeleteFailure, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 获取微信授权回调接口
     */
    private class ObtainWeChat4UmAuthListener implements SocializeListeners.UMAuthListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            L.i(TAG, "微信授权开始");
            if (weChatLoadDialog != null && !weChatLoadDialog.isShowing()) {
                weChatLoadDialog.show();
            }
        }

        @Override
        public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {
            L.i(TAG, "微信授权完成");
            //hideWeChatDialog();
            Toast.makeText(context, WeChatAuthorizedComplete, Toast.LENGTH_SHORT).show();
            mController.getPlatformInfo(context, SHARE_MEDIA.WEIXIN, new ObtainWeChat4UmDataListener());
        }

        @Override
        public void onError(SocializeException e, SHARE_MEDIA share_media) {
            L.i(TAG, "微信授权错误:" + e.getMessage() + ",微信授权错误码:" + e.getErrorCode());
            hideWeChatDialog();
            Toast.makeText(context, WeChatAuthorizedError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            L.i(TAG, "微信授权取消");
            hideWeChatDialog();
            Toast.makeText(context, WeChatAuthorizedCancel, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 获取微信数据回调接口
     */
    private class ObtainWeChat4UmDataListener implements SocializeListeners.UMDataListener {

        @Override
        public void onStart() {
            L.i(TAG, "正在获取微信数据");
        }

        @Override
        public void onComplete(int status, Map<String, Object> info) {
            L.i(TAG, "微信数据获取完成");
            hideWeChatDialog();
            if (status == 200 && info != null) {
                L.i(TAG, "微信数据:" + info.toString());

                openId = info.get(UNION_ID).toString();
                nickName = info.get(NICK_NAME).toString();

                sendWeChatDataRequest(openId, nickName);
            }
        }

    }

    /**
     * 微信绑定的回调接口
     */
    private class BindWeChat2SuiuuListener extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "微信绑定返回的数据信息:" + response);
            try {
                JSONObject object = new JSONObject(response);
                String status = object.getString(STATUS);
                if (!TextUtils.isEmpty(status)) {
                    switch (status) {
                        case "1":
                            SuiuuInfo.WriteWeChatInfo(context, openId, nickName);
                            SuiuuInfo.WriteWeChatAccountId(context, object.getString(DATA));
                            break;
                        case "-1":
                            Toast.makeText(context, SystemException, Toast.LENGTH_SHORT).show();
                            break;
                        case "-2":
                            Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(context, NetworkException, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            } catch (Exception e) {
                L.e(TAG, "数据解析失败:" + e.getMessage());
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "网络请求失败:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

}