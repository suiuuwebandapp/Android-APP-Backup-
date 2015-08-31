package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.AccountManageAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.TransferAccounts;
import com.minglang.suiuu.entity.TransferAccounts.TransferAccountsData.TransferAccountsItemData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

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
public class AccountManageActivity extends BaseAppCompatActivity {

    private static final String TAG = AccountManageActivity.class.getSimpleName();

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.DataException)
    String DataException;

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.account_balance_toolbar)
    Toolbar toolbar;

    @Bind(R.id.account_balance_number)
    TextView accountBalanceNumber;

    @Bind(R.id.account_balance_list_view)
    PullToRefreshListView pullToRefreshListView;

    private List<TransferAccountsItemData> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    private AccountManageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        ButterKnife.bind(this);
        initView();
        viewAction();
        sendAccountInfoRequest();
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

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        adapter = new AccountManageAdapter(this, listAll, R.layout.item_account_manage);

        try {
            token = URLEncoder.encode(SuiuuInfo.ReadAppTimeSign(this), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            token = SuiuuInfo.ReadAppTimeSign(this);
        }

        String strBalance = SuiuuInfo.ReadBalance(this);
        accountBalanceNumber.setText("￥" + strBalance);

    }

    /**
     * 控件动作
     */
    private void viewAction() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(AccountManageActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                pullToRefreshListView.onRefreshComplete();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(AccountManageActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                pullToRefreshListView.onRefreshComplete();

            }

        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
        }
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        pullToRefreshListView.onRefreshComplete();
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
                    List<TransferAccountsItemData> list = accounts.getData().getList();
                    if (list != null && list.size() > 0) {
                        listAll.addAll(list);
                        adapter.setList(listAll);
                    } else {
                        DeBugLog.e(TAG, "集合数据为空");
                        Toast.makeText(this, NoData, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    DeBugLog.e(TAG, "返回数据为空");
                    Toast.makeText(this, NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                hideDialog();
                DeBugLog.e(TAG, "数据解析异常:" + e.getMessage());
                Toast.makeText(this, DataException, Toast.LENGTH_SHORT).show();
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
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "错误信息:" + e.getMessage());
            hideDialog();
            Toast.makeText(AccountManageActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "账户信息:" + response);
            hideDialog();
            bindData2View(response);
        }

    }

}