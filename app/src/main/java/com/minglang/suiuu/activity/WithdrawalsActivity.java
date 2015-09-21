package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.AccountInfo;
import com.minglang.suiuu.entity.UserBack;
import com.minglang.suiuu.interfaces.DetachableListener;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 提现页面
 */
public class WithdrawalsActivity extends BaseAppCompatActivity {

    private static final String TAG = WithdrawalsActivity.class.getSimpleName();

    private static final String ACCOUNT_ID = "accountId";
    private static final String MONEY = "money";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private static final String ACCOUNT = "account";
    private static final String NAME = "name";

    @BindColor(R.color.white)
    int titleColor;

    @BindString(R.string.MoneyNotNull)
    String MoneyNotNull;

    @BindString(R.string.NoBindReceivablesWay)
    String NoBindReceivables;

    @BindString(R.string.NetworkError)
    String NetworkError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkException;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.DataException)
    String DataException;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.AccountNotNull)
    String AccountNotNull;

    @BindString(R.string.YourNameNotNull)
    String YourNameNotNull;

    @BindString(R.string.request_wait)
    String RequestMsg;

    @BindString(R.string.bind_alipay_wait)
    String BindALiPayMsg;

    @BindString(R.string.commit_wait)
    String CommitMsg;

    @Bind(R.id.withdrawals_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.withdrawals_input_money)
    EditText withdrawalsInputMoney;

    @Bind(R.id.withdrawals_alipay)
    TextView withdrawalsAlipay;

    @Bind(R.id.withdrawals_wechat)
    TextView withdrawalsWechat;

    private Context context;

    private String inputMoney;

    private View dialogView;

    private EditText dialogInputAccount;

    private EditText dialogInputUserName;

    private ProgressDialog requestDialog;

    private ProgressDialog bindAliPayDialog;

    private ProgressDialog commitDialog;

    private String alipayAccountId;

    private String weChatAccountId;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawals);
        ButterKnife.bind(this);
        initView();
        viewAction();
        judgeAccount();
    }

    @SuppressLint("InflateParams")
    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        context = WithdrawalsActivity.this;

        token = SuiuuInfo.ReadAppTimeSign(context);

        requestDialog = new ProgressDialog(context);
        requestDialog.setMessage(RequestMsg);
        requestDialog.setCanceledOnTouchOutside(false);

        bindAliPayDialog = new ProgressDialog(context);
        bindAliPayDialog.setMessage(BindALiPayMsg);
        bindAliPayDialog.setCanceledOnTouchOutside(false);

        commitDialog = new ProgressDialog(context);
        commitDialog.setMessage(CommitMsg);
        commitDialog.setCanceledOnTouchOutside(false);

        dialogView = LayoutInflater.from(context).inflate(R.layout.layout_input_withdrawals_info, null);
        dialogInputAccount = (EditText) dialogView.findViewById(R.id.layout_input_withdrawals_info_account);
        dialogInputUserName = (EditText) dialogView.findViewById(R.id.layout_input_withdrawals_info_user_name);

        initAlertDialog();
    }

    private void initAlertDialog() {

        DetachableListener detachableListener = DetachableListener.build(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String account = dialogInputAccount.getText().toString().trim();
                String userName = dialogInputUserName.getText().toString().trim();

                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(context, AccountNotNull, Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(context, YourNameNotNull, Toast.LENGTH_SHORT).show();
                } else {
                    sendBindAlipayWayRequest(account, userName);
                }

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.setView(dialogView)
                .setNegativeButton(android.R.string.ok, detachableListener)
                .setPositiveButton(android.R.string.cancel, null)
                .create();

        //detachableListener.clearOnDetach(dialog);

    }

    private void viewAction() {

        withdrawalsAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMoney = withdrawalsInputMoney.getText().toString().trim();
                if (TextUtils.isEmpty(inputMoney)) {
                    Toast.makeText(context, MoneyNotNull, Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, String> map = SuiuuInfo.ReadAliPayInfo(context);
                    String aliPayAccountName = map.get(SuiuuInfo.A_ACCOUNT_NAME);
                    String aliPayUserName = map.get(SuiuuInfo.A_USER_NAME);
                    alipayAccountId = SuiuuInfo.ReadAliPayAccountId(context);
                    L.i(TAG, "支付宝信息:" + map.toString() + ",id:" + alipayAccountId);
                    if (!TextUtils.isEmpty(aliPayAccountName) && !TextUtils.isEmpty(aliPayUserName)
                            && !TextUtils.isEmpty(alipayAccountId)) {
                        sendWithdrawalsRequest(alipayAccountId, inputMoney);
                    } else {
                        dialog.show();
                    }
                }
            }
        });

        withdrawalsWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMoney = withdrawalsInputMoney.getText().toString().trim();
                if (TextUtils.isEmpty(inputMoney)) {
                    Toast.makeText(context, MoneyNotNull, Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, String> map = SuiuuInfo.ReadWeChatInfo(context);
                    String weChatAccountName = map.get(SuiuuInfo.W_ONLY_ID);
                    String weChatUserName = map.get(SuiuuInfo.W_NICK_NAME);
                    weChatAccountId = SuiuuInfo.ReadWeChatAccountId(context);
                    L.i(TAG, "微信信息:" + map.toString() + ",id:" + weChatAccountId);
                    if (!TextUtils.isEmpty(weChatAccountName) && !TextUtils.isEmpty(weChatUserName)
                            && !TextUtils.isEmpty(weChatAccountId)) {
                        sendWithdrawalsRequest(weChatAccountId, inputMoney);
                    } else {
                        Toast.makeText(context, "请先添加微信收款方式！", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    /**
     * 判断是否已绑定账号
     */
    private void judgeAccount() {
        String aliPayAccountName = SuiuuInfo.ReadAliPayInfo(context).get(SuiuuInfo.A_ACCOUNT_NAME);
        String aliPayUserName = SuiuuInfo.ReadAliPayInfo(context).get(SuiuuInfo.A_USER_NAME);
        alipayAccountId = SuiuuInfo.ReadAliPayAccountId(context);

        String weChatAccountName = SuiuuInfo.ReadWeChatInfo(context).get(SuiuuInfo.W_ONLY_ID);
        String weChatUserName = SuiuuInfo.ReadWeChatInfo(context).get(SuiuuInfo.W_NICK_NAME);
        weChatAccountId = SuiuuInfo.ReadWeChatAccountId(context);

        if (!TextUtils.isEmpty(aliPayAccountName) && !TextUtils.isEmpty(aliPayUserName) && !TextUtils.isEmpty(alipayAccountId)) {
            L.i(TAG, "支付宝已绑定");
        } else if (!TextUtils.isEmpty(weChatAccountName) && !TextUtils.isEmpty(weChatUserName) && !TextUtils.isEmpty(weChatAccountId)) {
            L.i(TAG, "微信已绑定");
        } else {
            sendObtainReceivablesWayRequest();
        }
    }

    /**
     * 发起获取收款方式的网络请求
     */
    private void sendObtainReceivablesWayRequest() {
        if (requestDialog != null && !requestDialog.isShowing()) {
            requestDialog.show();
        }

        try {
            String url = HttpNewServicePath.getUserBindAccountListData + "?" + TOKEN + "=" + URLEncoder.encode(token, "UTF-8");
            L.i(TAG, "获取收款方式的URL:" + url);
            OkHttpManager.onGetAsynRequest(url, new ObtainReceivablesWayResultCallback());
        } catch (Exception e) {
            e.printStackTrace();
            hideCommitDialog();
            Toast.makeText(context, NetworkException, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 隐藏请求Dialog
     */
    private void hideRequestDialog() {
        if (requestDialog != null && requestDialog.isShowing()) {
            requestDialog.dismiss();
        }
    }

    /**
     * 获取收款方式失败
     */
    private void obtainReceivablesWayFailure() {
        withdrawalsAlipay.setEnabled(false);
        withdrawalsWechat.setEnabled(false);
    }

    /**
     * 绑定支付宝
     *
     * @param accountName 支付宝账户名
     * @param userName    用户真实姓名
     */
    private void sendBindAlipayWayRequest(final String accountName, final String userName) {
        if (bindAliPayDialog != null && !bindAliPayDialog.isShowing()) {
            bindAliPayDialog.show();
        }

        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[2];
        paramsArray[0] = new OkHttpManager.Params(ACCOUNT, accountName);
        paramsArray[1] = new OkHttpManager.Params(NAME, userName);

        String url = HttpNewServicePath.addAliPayUserInfo + "?" + TOKEN + "=" + token;
        L.i(TAG, "绑定支付宝:" + url);

        try {
            OkHttpManager.onPostAsynRequest(url, new OkHttpManager.ResultCallback<String>() {

                @Override
                public void onResponse(String response) {
                    if (TextUtils.isEmpty(response)) {
                        Toast.makeText(context, NoData, Toast.LENGTH_LONG).show();
                    } else try {
                        JSONObject object = new JSONObject(response);
                        String status = object.getString(STATUS);
                        if (!TextUtils.isEmpty(status)) {
                            switch (status) {
                                case "1":
                                    SuiuuInfo.WriteAliPayInfo(context, accountName, userName);
                                    alipayAccountId = object.getString(DATA);
                                    SuiuuInfo.WriteAliPayAccountId(context, alipayAccountId);
                                    sendWithdrawalsRequest(alipayAccountId, inputMoney);
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
                        L.e(TAG, "绑定支付宝数据解析异常:" + e.getMessage());
                        Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Request request, Exception e) {
                    L.e(TAG, "绑定支付宝失败:" + e.getMessage());
                    Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinish() {
                    hideBindDialog();
                }

            }, paramsArray);

        } catch (IOException e) {
            hideBindDialog();
            L.e(TAG, "绑定支付宝网络请求失败:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    private void hideBindDialog() {
        if (bindAliPayDialog != null && bindAliPayDialog.isShowing()) {
            bindAliPayDialog.dismiss();
        }
    }

    /**
     * 发起提现请求
     *
     * @param accountName 账户名
     * @param money       金额
     */
    private void sendWithdrawalsRequest(String accountName, String money) {
        if (commitDialog != null && !commitDialog.isShowing()) {
            commitDialog.show();
        }

        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[2];
        paramsArray[0] = new OkHttpManager.Params(ACCOUNT_ID, accountName);
        paramsArray[1] = new OkHttpManager.Params(MONEY, money);
        L.i(TAG, "提现请求账户:" + accountName + ",提现请求金额:" + money);

        try {
            String url = HttpNewServicePath.getDrawMoneyDataPath + "?" + TOKEN + "=" + token;
            L.i(TAG, "提现请求URL:" + url);
            OkHttpManager.onPostAsynRequest(url, new WithdrawalsResultCallback(), paramsArray);
        } catch (Exception e) {
            L.e(TAG, "提现请求网络错误:" + e.getMessage());
            Toast.makeText(context, NetworkException, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 隐藏提交Dialog
     */
    private void hideCommitDialog() {
        if (commitDialog != null && commitDialog.isShowing()) {
            commitDialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        L.i(TAG, "onDetachedFromWindow");
    }

    /**
     * 获取已绑定的数据的的回调接口
     */
    private class ObtainReceivablesWayResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "已绑定的收款方式数据:" + response);
            if (TextUtils.isEmpty(response)) {
                obtainReceivablesWayFailure();
                Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
            } else try {
                AccountInfo accountInfo = JsonUtils.getInstance().fromJSON(AccountInfo.class, response);
                if (accountInfo != null) {
                    List<AccountInfo.AccountInfoData> list = accountInfo.getData();
                    if (list != null && list.size() > 0) {
                        for (AccountInfo.AccountInfoData data : list) {
                            if (data.getType().equals("1")) {
                                String accountName = data.getAccount();
                                String userName = data.getUsername();
                                L.i(TAG, "WeChatAccountName:" + accountName + ",WeChatUserName:" + userName);
                                SuiuuInfo.WriteWeChatInfo(context, accountName, userName);
                                weChatAccountId = data.getAccountId();
                                SuiuuInfo.WriteWeChatAccountId(context, weChatAccountId);
                            } else if (data.getType().equals("2")) {
                                String accountName = data.getAccount();
                                String userName = data.getUsername();
                                L.i(TAG, "AliPayAccountName:" + accountName + ",AliPayUserName:" + userName);
                                SuiuuInfo.WriteAliPayInfo(context, accountName, userName);
                                alipayAccountId = data.getAccountId();
                                SuiuuInfo.WriteAliPayAccountId(context, alipayAccountId);
                            }
                        }
                    } else {
                        Toast.makeText(context, NoBindReceivables, Toast.LENGTH_LONG).show();
                    }
                } else {
                    obtainReceivablesWayFailure();
                    Toast.makeText(context, DataException, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                obtainReceivablesWayFailure();
                L.e(TAG, "已绑定的收款方式数据解析错误:" + e.getMessage());
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            obtainReceivablesWayFailure();
            L.e(TAG, "获取已绑定的收款方式网络请求失败:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideRequestDialog();
        }

    }

    /**
     * 提现回调接口
     */
    private class WithdrawalsResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "提现请求返回的数据:" + response);
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
            } else try {
                UserBack userBack = JsonUtils.getInstance().fromJSON(UserBack.class, response);
                JSONObject object = new JSONObject(response);
                String status = object.getString(STATUS);
                if (!TextUtils.isEmpty(status)) {
                    switch (status) {
                        case "1":
                            UserBack.UserBackData data = userBack.getData();
                            SuiuuInfo.WriteUserSign(context, data.getUserSign());
                            SuiuuInfo.WriteUserData(context, data);
                            Toast.makeText(context, "提现成功!", Toast.LENGTH_SHORT).show();
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
                L.e(TAG, "提现请求数据解析错误:" + e.getMessage());
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "提现请求网络请求失败:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideCommitDialog();
        }

    }

}