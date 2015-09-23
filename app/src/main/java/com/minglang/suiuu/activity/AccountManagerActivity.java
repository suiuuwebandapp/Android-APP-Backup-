package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.AccountManageAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.TransferAccounts;
import com.minglang.suiuu.entity.TransferAccounts.TransferAccountsData.TransferAccountsItemData;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 账户管理页面
 */
public class AccountManagerActivity extends BaseAppCompatActivity {

    private static final String TAG = AccountManagerActivity.class.getSimpleName();

    private static final String STATUS = "status";
    private static final String DATA = "data";

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.DataException)
    String DataException;

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.account_balance_toolbar)
    Toolbar toolbar;

    @Bind(R.id.account_balance_number)
    TextView accountBalanceNumber;

    @Bind(R.id.account_balance_layout)
    RelativeLayout relativeLayout;

    @Bind(R.id.account_balance_list_view)
    ListView pullToRefreshListView;

    private List<TransferAccountsItemData> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    private AccountManageAdapter adapter;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        ButterKnife.bind(this);
        initView();
        viewAction();
        sendAccountInfoRequest();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String strBalance = SuiuuInfo.ReadBalance(this);
        accountBalanceNumber.setText(String.format("%s%s", "￥", strBalance));
    }

    /**
     * 初始化方法
     */
    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        adapter = new AccountManageAdapter(this, listAll, R.layout.item_account_manage);
        pullToRefreshListView.setAdapter(adapter);

        try {
            token = URLEncoder.encode(SuiuuInfo.ReadAppTimeSign(this), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            token = SuiuuInfo.ReadAppTimeSign(this);
            e.printStackTrace();
        }

        context = AccountManagerActivity.this;
    }

    /**
     * 控件动作
     */
    private void viewAction() {

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, WithdrawalsActivity.class));
            }
        });

    }

    /**
     * 发起网络请求
     */
    private void sendAccountInfoRequest() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        String url = HttpNewServicePath.getUserAccountInfoPath + "?" + TOKEN + "=" + token;
        try {
            OkHttpManager.onGetAsynRequest(url, new AccountManagerResultCallback());
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

    /**
     * 绑定数据
     *
     * @param str Json字符串
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            hideDialog();
            Toast.makeText(this, NoData, Toast.LENGTH_SHORT).show();
        } else {
            try {
                TransferAccounts accounts = JsonUtils.getInstance().fromJSON(TransferAccounts.class, str);
                if (accounts != null) {
                    int status = accounts.getStatus();
                    if (status == 1) {
                        List<TransferAccountsItemData> list = accounts.getData().getList();
                        if (list != null && list.size() > 0) {
                            listAll.addAll(list);
                            adapter.setList(listAll);
                        } else {
                            L.e(TAG, "集合数据为空");
                            Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    L.e(TAG, "返回数据为空");
                    Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                hideDialog();
                L.e(TAG, "数据解析异常:" + e.getMessage());
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
                    Toast.makeText(context, DataException, Toast.LENGTH_SHORT).show();
                }

            }
        }
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

    private class AccountManagerResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            hideDialog();
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "错误信息:" + e.getMessage());
            hideDialog();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

}