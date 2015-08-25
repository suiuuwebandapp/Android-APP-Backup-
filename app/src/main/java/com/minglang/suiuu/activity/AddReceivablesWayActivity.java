package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.SuiuuHttp;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

public class AddReceivablesWayActivity extends BaseAppCompatActivity {

    private static final String TAG = AddReceivablesWayActivity.class.getSimpleName();

    private static final String KEY = "key";

    private static final String ALIPAY = "alipay";
    private static final String WECHAT = "weChat";

    private static final String ACCOUNT = "account";
    private static final String NAME = "name";

    private String strKey;

    @BindColor(R.color.white)
    int titleColor;

    @BindString(R.string.AccountNotNull)
    String AccountNotNull;

    @BindString(R.string.YourNameNotNull)
    String YourNameNotNull;

    @BindString(R.string.Loading)
    String loading;

    @Bind(R.id.add_receivables_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.add_receivables_account)
    EditText addReceivablesAccount;

    @Bind(R.id.add_receivables_user_name)
    EditText addReceivablesUserName;

    @Bind(R.id.add_receivables_ok)
    Button addReceivablesOk;

    private Context context;

    private boolean isAccount = true;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receivables_way);

        strKey = getIntent().getStringExtra(KEY);

        ButterKnife.bind(this);
        initView();
        viewAction();
    }

    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        context = AddReceivablesWayActivity.this;

        if (!TextUtils.isEmpty(strKey)) {
            if (strKey.equals(ALIPAY)) {
                isAccount = true;
            } else if (strKey.equals(WECHAT)) {
                isAccount = false;
            }
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(loading);
        progressDialog.setCanceledOnTouchOutside(false);

    }

    private void viewAction() {
        addReceivablesOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAccount = addReceivablesAccount.getText().toString().trim();
                String strUserName = addReceivablesUserName.getText().toString().trim();
                if (TextUtils.isEmpty(strAccount)) {
                    Toast.makeText(context, AccountNotNull, Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(strUserName)) {
                    Toast.makeText(context, YourNameNotNull, Toast.LENGTH_SHORT).show();
                } else {
                    setAccountInfo4Service(buildRequestParams(strAccount, strUserName));
                }
            }
        });
    }

    private RequestParams buildRequestParams(String account, String userName) {
        RequestParams params = new RequestParams();
        if (isAccount) {
            params.addBodyParameter(ACCOUNT, account);
        } else {
            params.addBodyParameter(ACCOUNT, account);
        }
        params.addBodyParameter(NAME, userName);
        return params;
    }

    private void setAccountInfo4Service(RequestParams params) {
        SuiuuHttp suiuuHttp = new SuiuuHttp();
        suiuuHttp.setHttpMethod(HttpRequest.HttpMethod.POST);
        if (isAccount) {
            suiuuHttp.setHttpPath(HttpServicePath.addAliPayUserInfo);
        } else {
            suiuuHttp.setHttpPath(HttpServicePath.addWeChatAUserInfo);
        }
        suiuuHttp.setParams(params);
        suiuuHttp.setRequestCallBack(new AddReceivablesWayCallBack());
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_receivables_way, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class AddReceivablesWayCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            hideDialog();
            String str = responseInfo.result;
            DeBugLog.i(TAG, "返回信息:" + str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",Error:" + s);
            hideDialog();
        }

    }

}