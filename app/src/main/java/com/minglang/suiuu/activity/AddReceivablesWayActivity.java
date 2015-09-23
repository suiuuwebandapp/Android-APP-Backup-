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

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 添加收款方式页面
 */
public class AddReceivablesWayActivity extends BaseAppCompatActivity {

    private static final String TAG = AddReceivablesWayActivity.class.getSimpleName();

    private static final String ACCOUNT = "account";
    private static final String NAME = "name";

    @BindColor(R.color.white)
    int titleColor;

    @BindString(R.string.AccountNotNull)
    String AccountNotNull;

    @BindString(R.string.YourNameNotNull)
    String YourNameNotNull;

    @BindString(R.string.Loading)
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

    @Bind(R.id.add_receivables_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.add_receivables_account)
    EditText addReceivablesAccount;

    @Bind(R.id.add_receivables_user_name)
    EditText addReceivablesUserName;

    @Bind(R.id.add_receivables_ok)
    Button addReceivablesOk;

    private Context context;

    private ProgressDialog progressDialog;

    private String account;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receivables_way);
        ButterKnife.bind(this);
        initView();
        viewAction();
    }

    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        context = AddReceivablesWayActivity.this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        token = SuiuuInfo.ReadAppTimeSign(this);
    }

    private void viewAction() {

        addReceivablesOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = addReceivablesAccount.getText().toString().trim();
                userName = addReceivablesUserName.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(context, AccountNotNull, Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(context, YourNameNotNull, Toast.LENGTH_SHORT).show();
                } else {
                    sendAccountInfo4Service(account, userName);
                }
            }
        });

    }

    private void sendAccountInfo4Service(String account, String userName) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[2];
        paramsArray[0] = new OkHttpManager.Params(ACCOUNT, account);
        paramsArray[1] = new OkHttpManager.Params(NAME, userName);

        String url = HttpNewServicePath.addAliPayUserInfo + "?" + TOKEN + "=" + token;
        try {
            OkHttpManager.onPostAsynRequest(url, new AddReceivablesWayResultCallback(), paramsArray);
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
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

    private class AddReceivablesWayResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            hideDialog();
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(context, NoData, Toast.LENGTH_LONG).show();
            } else try {
                JSONObject object = new JSONObject(response);
                String status = object.getString(STATUS);
                if (!TextUtils.isEmpty(status)) {
                    switch (status) {
                        case "1":
                            SuiuuInfo.WriteAliPayInfo(context, account, userName);
                            SuiuuInfo.WriteAliPayAccountId(context, object.getString(DATA));
                            setResult(RESULT_OK);
                            finish();
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
                L.e(TAG, "数据解析异常:" + e.getMessage());
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "添加支付宝账户异常:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

}