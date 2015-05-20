package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
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
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.PullToRefreshStaggeredView;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.entity.LoopDetails;
import com.minglang.suiuu.entity.LoopDetailsData;
import com.minglang.suiuu.entity.LoopDetailsDataList;
import com.minglang.suiuu.utils.DrawableUtils;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.SystemBarTintManager;

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
     * 验证信息
     */
    private String Verification;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_details);

        type = getIntent().getStringExtra(TYPE);
        circleId = getIntent().getStringExtra(CIRCLEID);
        loopName = getIntent().getStringExtra("name");

        Verification = SuiuuInfo.ReadVerification(this);

        initView();
        ViewAction();
        getData();
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
        params.addBodyParameter(HttpServicePath.key, Verification);
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
        params.addBodyParameter(HttpServicePath.key, Verification);
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
        params.addBodyParameter(HttpServicePath.key, Verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getCancelServicePath, new CancelRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 判断是否关注
     */
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
     * 初始化方法
     */
    @SuppressWarnings("deprecation")
    private void initView() {
        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
        SystemBarTintManager.SystemBarConfig systemBarConfig = systemBarTintManager.getConfig();
        //状态栏高度
        int statusBarHeight = systemBarConfig.getStatusBarHeight();
        //虚拟按键高度
        int navigationBarHeight = systemBarConfig.getNavigationBarHeight();

        /****************设置状态栏颜色*************/
        systemBarTintManager.setStatusBarTintEnabled(true);
        systemBarTintManager.setNavigationBarTintEnabled(false);
        systemBarTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.loop_details_root_layout);
        if (isKITKAT) {
            rootLayout.setPadding(0, statusBarHeight, 0, 0);
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

        ScreenUtils screenUtils = new ScreenUtils(this);
        int screenWidth = screenUtils.getScreenWidth();
        int screenHeight = screenUtils.getScreenHeight();

        loopDetailsAdapter = new LoopDetailsAdapter(this);
        loopDetailsAdapter.setScreenParams(screenWidth, screenHeight);
        loopDetailsGridView.setAdapter(loopDetailsAdapter);

    }

    /**
     * 得到圈子文章列表的网络请求回调接口
     */
    @SuppressWarnings("deprecation")
    class LoopDetailsRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (page == 1) {
                if (listAll != null && listAll.size() > 0) {
                    listAll.clear();
                }
            }

            String str = responseInfo.result;
            try {
                LoopDetails loopDetails = JsonUtil.getInstance().fromJSON(LoopDetails.class, str);
                LoopDetailsData loopDetailsData = loopDetails.getData();
                List<LoopDetailsDataList> list = loopDetailsData.getData();
                if (list != null && list.size() > 0) {
                    listAll.addAll(list);
                    loopDetailsAdapter.setDataList(listAll);
                } else {
                    if (page > 1) {
                        page = page - 1;
                    }
                    Toast.makeText(context, getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                }
                attentionId = loopDetailsData.getAttentionId();
                Log.i(TAG, "attentionId:" + attentionId);
                isAttention();
            } catch (Exception e) {
                Log.e(TAG, "数据请求失败:" + e.getMessage());

                if (page > 1) {
                    page = page - 1;
                }

                Toast.makeText(LoopDetailsActivity.this,
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
            loopDetailsGridView.onRefreshComplete();
        }

        @Override
        public void onFailure(HttpException error, String msg) {

            Log.e(TAG, "圈子详细列表请求失败:" + msg);

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (page > 1) {
                page = page - 1;
            }

            loopDetailsGridView.onRefreshComplete();

            Toast.makeText(LoopDetailsActivity.this,
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 添加圈子关注网络请求
     */
    class AddAttentionRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            Log.i(TAG, "关注圈子返回结果:" + str);
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
                Log.e(TAG, "添加关注数据解析异常:" + e.getMessage());
                Toast.makeText(LoopDetailsActivity.this,
                        getResources().getString(R.string.addAttentionFail), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, "添加关注失败:" + s);
            Toast.makeText(LoopDetailsActivity.this,
                    getResources().getString(R.string.addAttentionFail), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消关注网络请求回调接口
     */
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
                Log.e(TAG, "取消关注失败:" + e.getMessage());
                Toast.makeText(context, getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, "取消关注失败:" + e.getMessage());
            Toast.makeText(context, getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }

}
