package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshBase.Mode;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.SuiuuCommentAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.SuiuuComment;
import com.minglang.suiuu.entity.SuiuuComment.SuiuuCommentData.SuiuuCommentItemData;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

public class SuiuuCommentActivity extends BaseAppCompatActivity {

    private static final String TAG = SuiuuCommentActivity.class.getSimpleName();

    @BindColor(R.color.white)
    int titleColor;

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.DataException)
    String DataException;

    @BindString(R.string.SystemException)
    String SystemException;

    @Bind(R.id.suiuu_comment_tool_bar)
    Toolbar toolBar;

    @Bind(R.id.suiuu_comment_list_view)
    PullToRefreshListView pullToRefreshListView;

    private int page = 1;

    private boolean isPullToRefresh = true;

    private ProgressDialog progressDialog;

    private List<SuiuuCommentItemData> listAll = new ArrayList<>();

    private SuiuuCommentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_comment);
        ButterKnife.bind(this);
        initView();
        viewAction();
        sendRequest();
    }

    private void initView() {
        toolBar.setTitleTextColor(titleColor);
        setSupportActionBar(toolBar);

        userSign = getIntent().getStringExtra(USER_SIGN);
        token = SuiuuInfo.ReadAppTimeSign(this);

        pullToRefreshListView.setMode(Mode.BOTH);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        adapter = new SuiuuCommentAdapter(this, listAll, R.layout.item_suiuu_comment);
        pullToRefreshListView.setAdapter(adapter);
    }

    private void viewAction() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(SuiuuCommentActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;
                isPullToRefresh = false;
                sendRequest();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(SuiuuCommentActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page++;
                isPullToRefresh = false;
                sendRequest();
            }
        });

    }

    private void sendRequest() {
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        String[] keyArray = new String[]{USER_SIGN, TOKEN, PAGE, NUMBER};
        String[] valueArray = new String[]{userSign, token, String.valueOf(page), String.valueOf(10)};
        String url = addUrlAndParams(HttpNewServicePath.getPersonalSuiuuCommentPath, keyArray, valueArray);
        L.i(TAG, "随游评论请求URL:" + url);

        try {
            OkHttpManager.onGetAsynRequest(url, new SuiuuCommentResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            failureLessPage();
            Toast.makeText(SuiuuCommentActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 隐藏进度框和ListView加载进度View
     */
    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        pullToRefreshListView.onRefreshComplete();
    }

    /**
     * 请求失败,页码减1
     */
    private void failureLessPage() {
        if (page > 1) {
            page = page - 1;
        }
    }

    /**
     * 如果页码为1，就清空数据
     */
    private void clearListData() {
        if (page == 1) {
            if (listAll != null && listAll.size() > 0) {
                listAll.clear();
            }
        }
    }

    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            failureLessPage();
            Toast.makeText(this, NoData, Toast.LENGTH_SHORT).show();
        } else {
            try {
                SuiuuComment suiuuComment = JsonUtils.getInstance().fromJSON(SuiuuComment.class, str);
                List<SuiuuCommentItemData> list = suiuuComment.getData().getData();
                if (list != null && list.size() > 0) {
                    clearListData();
                    listAll.addAll(list);
                    adapter.setList(listAll);
                } else {
                    failureLessPage();
                    Toast.makeText(this, NoData, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                failureLessPage();
                L.e(TAG, "解析错误:" + e.getMessage());
                try {
                    JSONObject object = new JSONObject(str);
                    String status = object.getString(STATUS);
                    if (status.equals("-1")) {
                        Toast.makeText(this, SystemException, Toast.LENGTH_SHORT).show();
                    } else if (status.equals("-2")) {
                        Toast.makeText(this, object.getString(DATA), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    Toast.makeText(this, DataException, Toast.LENGTH_SHORT).show();
                }

            }

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

    private class SuiuuCommentResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "返回的评论数据:" + response);
            hideDialog();
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Exception:" + e.getMessage());
            hideDialog();
            failureLessPage();
            Toast.makeText(SuiuuCommentActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

}