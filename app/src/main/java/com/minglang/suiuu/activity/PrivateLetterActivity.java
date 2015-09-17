package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.PrivateLetterAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.PrivateLetter;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

public class PrivateLetterActivity extends BaseAppCompatActivity {

    private static final String TAG = PrivateLetterActivity.class.getSimpleName();

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private static final String RELATE_ID = "relateId";

    private static final String HEAD_IMAGE_PATH = "headImagePath";

    @BindColor(R.color.white)
    int titleColor;

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.SystemException)
    String SystemException;

    @Bind(R.id.private_letter_tool_bar)
    Toolbar toolBar;

    @Bind(R.id.private_letter_list_view)
    PullToRefreshListView pullToRefreshListView;

    private Context context;

    private PrivateLetterAdapter adapter;

    private ProgressDialog progressDialog;

    private List<PrivateLetter.PrivateLetterData> listAll = new ArrayList<>();

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_letter);
        ButterKnife.bind(this);
        initView();
        viewAction();
        sendPrivateLetterRequest();
    }

    private void initView() {
        toolBar.setTitleTextColor(titleColor);
        setSupportActionBar(toolBar);

        context = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        adapter = new PrivateLetterAdapter(this, listAll, R.layout.item_private_letter);

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setAdapter(adapter);

        token = SuiuuInfo.ReadAppTimeSign(this);
    }

    private void viewAction() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(context, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                handler.postAtTime(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshListView.onRefreshComplete();
                    }
                }, 3000);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(context, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                handler.postAtTime(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshListView.onRefreshComplete();
                    }
                }, 3000);

            }
        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int location = position - 1;
                String relateId = listAll.get(location).getRelateId();
                String headImagePath = listAll.get(location).getHeadImg();
                Intent intent = new Intent(PrivateLetterActivity.this, PrivateLetterChatActivity.class);
                intent.putExtra(RELATE_ID, relateId);
                intent.putExtra(HEAD_IMAGE_PATH, headImagePath);
                startActivity(intent);
            }
        });

    }

    private void sendPrivateLetterRequest() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        try {
            String url = HttpNewServicePath.getPrivateLetterListPath + "?" + TOKEN
                    + "=" + URLEncoder.encode(token, "UTF-8");
            DeBugLog.i(TAG, "请求URL:" + url);

            OkHttpManager.onGetAsynRequest(url, new OkHttpManager.ResultCallback<String>() {

                @Override
                public void onResponse(String response) {
                    DeBugLog.i(TAG, "私信列表数据:" + response);
                    if (TextUtils.isEmpty(response)) {
                        Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                    } else try {
                        PrivateLetter privateLetter = JsonUtils.getInstance().fromJSON(PrivateLetter.class, response);
                        List<PrivateLetter.PrivateLetterData> list = privateLetter.getData();
                        if (list != null && list.size() > 0) {
                            listAll.addAll(list);
                            adapter.setList(listAll);
                        } else {
                            Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        DeBugLog.e(TAG, "数据解析失败:" + e.getMessage());
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString(STATUS);
                            switch (status) {
                                case "-1":
                                    Toast.makeText(context, SystemException, Toast.LENGTH_SHORT).show();
                                    break;
                                case "-2":
                                    Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (Exception e1) {
                            Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onError(Request request, Exception e) {
                    DeBugLog.e(TAG, "网络请求失败:" + e.getMessage());
                    Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinish() {
                    hideDialog();
                }

            });
        } catch (Exception e) {
            DeBugLog.e(TAG, "网络请求异常:" + e.getMessage());
            hideDialog();
        }
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
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

}