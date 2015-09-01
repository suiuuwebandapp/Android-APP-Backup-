package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.PersonalSuiuuAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.UserSuiuu;
import com.minglang.suiuu.entity.UserSuiuu.UserSuiuuData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuiuuHttp;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 个人中心页面
 */
public class PersonalCenterActivity extends BaseAppCompatActivity {

    private static final String TAG = PersonalCenterActivity.class.getSimpleName();

    private static final String PAGE = "page";
    private static final String NUMBER = "number";
    private static final String USERSIGN = "userSign";

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetWorkError;

    @Bind(R.id.personal_center_toolbar)
    Toolbar toolbar;

    @Bind(R.id.personal_center_recycler_view)
    RecyclerView recyclerView;

    private ProgressDialog progressDialog;

    private List<UserSuiuuData> listAll = new ArrayList<>();

    private PersonalSuiuuAdapter adapter;

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        ButterKnife.bind(this);
        initView();
        getPersonalSuiuuData(buildRequestParams(page));
    }

    private void initView() {
        userSign = SuiuuInfo.ReadUserSign(this);
        verification = SuiuuInfo.ReadVerification(this);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        adapter = new PersonalSuiuuAdapter();

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private RequestParams buildRequestParams(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(PAGE, String.valueOf(page));
        params.addBodyParameter(NUMBER, String.valueOf(10));
        params.addBodyParameter(USERSIGN, userSign);
        params.addBodyParameter(HttpServicePath.key, verification);
        return params;
    }

    private void getPersonalSuiuuData(RequestParams params) {
        SuiuuHttp httpRequest = new SuiuuHttp(HttpRequest.HttpMethod.POST,
                HttpServicePath.getPersonalSuiuuDataPath, new PersonalSuiuuRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void failureLessPage() {
        if (page > 1) {
            page = page - 1;
        }
    }

    private void clearDataList() {
        if (page == 1) {
            if (listAll != null && listAll.size() > 0) {
                listAll.clear();
            }
        }
    }

    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            failureLessPage();
            Toast.makeText(PersonalCenterActivity.this, NoData, Toast.LENGTH_SHORT).show();
        } else {
            try {
                UserSuiuu userSuiuu = JsonUtils.getInstance().fromJSON(UserSuiuu.class, str);
                if (userSuiuu != null) {
                    List<UserSuiuuData> list = userSuiuu.getData();
                    if (list != null && list.size() > 0) {
                        clearDataList();
                        listAll.addAll(list);
                        adapter.setList(listAll);
                        DeBugLog.i(TAG, "当前页码:" + page + ",当前请求数据数量:" + list.size()
                                + ",总数据数量:" + listAll.size());
                    } else {
                        failureLessPage();
                        Toast.makeText(PersonalCenterActivity.this, NoData, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    failureLessPage();
                    Toast.makeText(PersonalCenterActivity.this, NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "数据绑定Error:" + e.getMessage());
                failureLessPage();
                Toast.makeText(PersonalCenterActivity.this, DataError, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class PersonalSuiuuRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String str = responseInfo.result;
            DeBugLog.i(TAG, "返回的数据:" + str);
            hideDialog();
            bindData2View(str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",Error:" + s);
            hideDialog();
            failureLessPage();
            Toast.makeText(PersonalCenterActivity.this, NetWorkError, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_personal_center, menu);
        return true;
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

}