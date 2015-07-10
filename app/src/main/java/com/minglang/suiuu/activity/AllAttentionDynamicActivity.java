package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.AllAttentionDynamicAdapter;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshListView;
import com.minglang.suiuu.entity.AllAttentionDynamic;
import com.minglang.suiuu.entity.AllAttentionDynamicData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 查看更多关注页面
 */
public class AllAttentionDynamicActivity extends Activity {

    private static final String TAG = AllAttentionDynamicActivity.class.getSimpleName();

    private static final String PAGE = "page";

    private static final String ARTICLEID = "articleId";

    private static final int COMPLETE = 1;

    private static PullToRefreshListView pullToRefreshListView;

    private int page = 1;

    private List<AllAttentionDynamicData> listAll = new ArrayList<>();

    private AllAttentionDynamicAdapter adapter;

    private ProgressDialog progressDialog;

    @Bind(R.id.AllAttentionBack)
    ImageView back;

    private static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case COMPLETE:
                    pullToRefreshListView.onRefreshComplete();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_attention_dynamic);

        ButterKnife.bind(this);

        initView();
        ViewAction();
        getDynamicData4Service(page);
    }

    /**
     * 初始化方法
     */
    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.load_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.all_attention_dynamic_list_view);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new AllAttentionDynamicAdapter(this);
        pullToRefreshListView.setAdapter(adapter);
    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleId = listAll.get(position).getArticleId();
                Intent intent = new Intent(AllAttentionDynamicActivity.this, LoopArticleActivity.class);
                intent.putExtra("TAG", TAG);
                intent.putExtra(ARTICLEID, articleId);
                startActivity(intent);
            }
        });

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;
                getDynamicData4Service(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                getDynamicData4Service(page);
            }
        });
    }

    /**
     * 从网络获取数据
     */
    private void getDynamicData4Service(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(PAGE, String.valueOf(page));
        params.addBodyParameter("number", String.valueOf(10));
        params.addBodyParameter(HttpServicePath.key, SuiuuInfo.ReadVerification(this));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.AllAttentionDynamicPath, new AllAttentionDynamicRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 隐藏进度框和ListView加载进度View
     */
    private void showOrHideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        handler.sendEmptyMessage(COMPLETE);

    }

    private void failureComputePage() {
        if (page > 1) {
            page = page - 1;
        }
    }

    /**
     * 绑定数据到View
     *
     * @param str Json字符串
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            failureComputePage();
            Toast.makeText(this, getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
        } else {
            try {
                AllAttentionDynamic dynamic = JsonUtils.getInstance().fromJSON(AllAttentionDynamic.class, str);
                List<AllAttentionDynamicData> list = dynamic.getData().getData();
                if (list != null && list.size() > 0) {
                    listAll.addAll(list);
                    adapter.setList(listAll);
                    adapter.notifyDataSetChanged();
                } else {
                    failureComputePage();
                    Toast.makeText(this, getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                failureComputePage();
                DeBugLog.e(TAG, "关注详细信息解析错误:" + e.getMessage());
                Toast.makeText(this, getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 网络请求回调接口
     */
    private class AllAttentionDynamicRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            showOrHideDialog();
            String str = stringResponseInfo.result;
            bindData2View(str);

        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "关注动态数据加载失败:" + s);

            showOrHideDialog();
            failureComputePage();

            Toast.makeText(AllAttentionDynamicActivity.this,
                    getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();

        }

    }

}
