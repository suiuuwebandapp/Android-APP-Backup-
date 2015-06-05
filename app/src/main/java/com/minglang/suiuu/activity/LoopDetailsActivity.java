package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.LoopDetailsAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.PullToRefreshStaggeredView;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.entity.LoopDetails;
import com.minglang.suiuu.entity.LoopDetailsData;
import com.minglang.suiuu.entity.LoopDetailsDataList;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.DrawableUtils;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.squareup.leakcanary.RefWatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子-详情页页面
 */
public class LoopDetailsActivity extends BaseActivity {

    private static final String TAG = LoopDetailsActivity.class.getSimpleName();

    private static final String TYPE = "type";
    private static final String CIRCLEID = "circleId";
    private static final String CID = "cId";
    private static final String ARTICLEID = "articleId";

    private String type;

    /**
     * 圈子ID
     */
    private String circleId;

    /**
     * 返回键
     */
    private ImageView back;

    /**
     * 标题
     */
    private TextView title;

    /**
     * 添加关注
     */
    private TextView addAttention;

    /**
     * 数据显示控件
     */
    private PullToRefreshStaggeredView loopDetailsGridView;

    private List<LoopDetailsDataList> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    private LoopDetailsAdapter loopDetailsAdapter;

    private String loopName;

    private String attentionId;

    private int page = 1;

    private Context context = LoopDetailsActivity.this;

    private TextView reload;

    private TextProgressDialog textProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_details);

        type = getIntent().getStringExtra(TYPE);
        circleId = getIntent().getStringExtra(CIRCLEID);
        loopName = getIntent().getStringExtra("name");

        initView();
        ViewAction();
        getData();
    }

    /**
     * 初始化方法
     */
    @SuppressWarnings("deprecation")
    private void initView() {
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.loop_details_root_layout);
        if (isKITKAT) {
            if (navigationBarHeight <= 0) {
                rootLayout.setPadding(0, statusBarHeight, 0, 0);
            } else {
                rootLayout.setPadding(0, statusBarHeight, 0, navigationBarHeight);
            }
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(getResources().getString(R.string.load_wait));

        back = (ImageView) findViewById(R.id.loop_details_back);
        title = (TextView) findViewById(R.id.loop_details_title);
        addAttention = (TextView) findViewById(R.id.loop_details_add_attention);

        loopDetailsGridView = (PullToRefreshStaggeredView) findViewById(R.id.loopDetailsGridView);
        loopDetailsGridView.setMode(PullToRefreshBase.Mode.BOTH);

        loopDetailsAdapter = new LoopDetailsAdapter(this);
        loopDetailsAdapter.setScreenParams(screenWidth, screenHeight);
        loopDetailsGridView.setAdapter(loopDetailsAdapter);

        reload = (TextView) findViewById(R.id.loopDetailsReload);
        textProgressDialog = new TextProgressDialog(context);
        textProgressDialog.setMessage(getResources().getString(R.string.pull_to_refresh_footer_refreshing_label));
    }

    /**
     * 控件动作
     */
    private void ViewAction() {
        title.setText(loopName);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(attentionId)) {
                    setAddAttention2Service();
                } else {
                    setCancelAttention2Service();
                }
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textProgressDialog.isShow()) {
                    textProgressDialog.showDialog();
                }

                page = 1;
                getData();
            }
        });

        loopDetailsGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<StaggeredGridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {

                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;
                getInternetServiceData(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {

                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                getInternetServiceData(page);
            }
        });

        loopDetailsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleId = listAll.get(position).getArticleId();
                Intent intent = new Intent(LoopDetailsActivity.this, LoopArticleActivity.class);
                intent.putExtra(ARTICLEID, articleId);
                intent.putExtra("TAG", TAG);
                startActivity(intent);
            }
        });

    }

    private void getData() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        getInternetServiceData(page);
    }

    /**
     * 网络数据请求
     */
    private void getInternetServiceData(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter(CIRCLEID, circleId);
        params.addBodyParameter(TYPE, type);
        params.addBodyParameter("number", String.valueOf(10));
        params.addBodyParameter("page", String.valueOf(page));

        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.LoopDetailsPath, new LoopDetailsRequestCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    /**
     * 添加圈子关注网络请求
     */
    private void setAddAttention2Service() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(CID, circleId);
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.setAddAttentionPath, new AddAttentionRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 取消关注
     */
    private void setCancelAttention2Service() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("attentionId", attentionId);
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getCancelServicePath, new CancelRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 判断是否关注
     */
    @SuppressWarnings("deprecation")
    private void isAttention() {
        if (TextUtils.isEmpty(attentionId)) {
            addAttention.setCompoundDrawables(DrawableUtils.setBounds(getResources().getDrawable(R.drawable.attention)),
                    null, null, null);
        } else {
            addAttention.setCompoundDrawables(DrawableUtils.setBounds(getResources().getDrawable(R.drawable.attention_press)),
                    null, null, null);
        }
    }

    /**
     * 隐藏Dialog
     */
    private void showOrHideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (textProgressDialog != null && textProgressDialog.isShow()) {
            textProgressDialog.dismissDialog();
        }
        loopDetailsGridView.onRefreshComplete();
    }

    private void isClearListAll() {
        if (page == 1) {
            if (listAll != null && listAll.size() > 0) {
                listAll.clear();
            }
        }
    }

    private void failureComputePage() {
        if (page > 1) {
            page = page - 1;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = SuiuuApplication.getRefWatcher();
        refWatcher.watch(this);
    }

    /**
     * 得到圈子文章列表的网络请求回调接口
     */
    @SuppressWarnings("deprecation")
    class LoopDetailsRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {

            showOrHideDialog();
            isClearListAll();

            String str = responseInfo.result;
            try {
                LoopDetails loopDetails = JsonUtils.getInstance().fromJSON(LoopDetails.class, str);
                LoopDetailsData loopDetailsData = loopDetails.getData();
                List<LoopDetailsDataList> list = loopDetailsData.getData();
                if (list != null && list.size() > 0) {
                    reload.setVisibility(View.INVISIBLE);
                    listAll.addAll(list);
                    loopDetailsAdapter.setDataList(listAll);
                } else {
                    failureComputePage();
                    reload.setVisibility(View.VISIBLE);
                    Toast.makeText(context, getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                }
                attentionId = loopDetailsData.getAttentionId();
                isAttention();
            } catch (Exception e) {
                failureComputePage();
                reload.setVisibility(View.VISIBLE);
                Toast.makeText(LoopDetailsActivity.this,
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                DeBugLog.e(TAG, "数据请求失败:" + e.getMessage());
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            showOrHideDialog();
            failureComputePage();
            reload.setVisibility(View.VISIBLE);
            Toast.makeText(LoopDetailsActivity.this, getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
            DeBugLog.e(TAG, "圈子详细列表请求失败:" + msg);
        }
    }

    /**
     * 添加圈子关注网络请求
     */
    @SuppressWarnings("deprecation")
    class AddAttentionRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            DeBugLog.i(TAG, "关注圈子返回结果:" + str);
            try {
                JSONObject object = new JSONObject(str);
                String status = object.getString("status");
                if (!TextUtils.isEmpty(status)) {
                    if (status.equals("1")) {
                        addAttention.setCompoundDrawables(DrawableUtils.setBounds(
                                getResources().getDrawable(R.drawable.attention_press)), null, null, null);
                        attentionId = object.getString("data");
                        Toast.makeText(LoopDetailsActivity.this,
                                getResources().getString(R.string.addAttentionSuccess), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoopDetailsActivity.this,
                                getResources().getString(R.string.addAttentionFail), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoopDetailsActivity.this,
                            getResources().getString(R.string.addAttentionFail), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                DeBugLog.e(TAG, "添加关注数据解析异常:" + e.getMessage());
                Toast.makeText(LoopDetailsActivity.this,
                        getResources().getString(R.string.addAttentionFail), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "添加关注失败:" + s);
            Toast.makeText(LoopDetailsActivity.this,
                    getResources().getString(R.string.addAttentionFail), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消关注网络请求回调接口
     */
    @SuppressWarnings("deprecation")
    class CancelRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            try {
                JSONObject object = new JSONObject(str);
                String status = object.getString("status");
                if (status.equals("1")) {
                    addAttention.setCompoundDrawables(DrawableUtils.setBounds(
                            getResources().getDrawable(R.drawable.attention)), null, null, null);
                    attentionId = null;
                    Toast.makeText(context, "取消关注成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "取消关注失败！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "取消关注失败:" + e.getMessage());
                Toast.makeText(context, getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "取消关注失败:" + e.getMessage());
            Toast.makeText(context, getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }

}
