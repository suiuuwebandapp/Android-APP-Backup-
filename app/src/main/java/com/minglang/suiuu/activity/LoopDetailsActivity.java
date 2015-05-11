package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.LoopDetailsAdapter;
import com.minglang.suiuu.entity.LoopDetails;
import com.minglang.suiuu.entity.LoopDetailsData;
import com.minglang.suiuu.entity.LoopDetailsDataList;
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
public class LoopDetailsActivity extends Activity {

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
    private GridView loopDetailsGridView;

    private List<LoopDetailsDataList> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    private LoopDetailsAdapter loopDetailsAdapter;

    private String loopName;

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

    private void getData() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        getInternetServiceData();
    }

    /**
     * 网络数据请求
     */
    private void getInternetServiceData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, Verification);
        params.addBodyParameter(CIRCLEID, circleId);
        params.addBodyParameter(TYPE, type);

        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.LoopDetailsPath, new LoopDetailsRequestCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();

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
                setAddAttention2Service();
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
     * 初始化方法
     */
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

        loopDetailsGridView = (GridView) findViewById(R.id.loopDetailsGridView);

        ScreenUtils screenUtils = new ScreenUtils(this);
        int screenWidth = screenUtils.getScreenWidth();

        loopDetailsAdapter = new LoopDetailsAdapter(this);
        loopDetailsAdapter.setScreenParams(screenWidth);
        loopDetailsGridView.setAdapter(loopDetailsAdapter);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * 得到圈子文章列表的网络请求回调接口
     */
    class LoopDetailsRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            String str = responseInfo.result;
            try {
                LoopDetails loopDetails = JsonUtil.getInstance().fromJSON(LoopDetails.class, str);
                LoopDetailsData loopDetailsData = loopDetails.getData();
                if (loopDetailsData != null) {
                    List<LoopDetailsDataList> list = loopDetailsData.getData();
                    if (list != null) {
                        listAll.addAll(list);
                        loopDetailsAdapter.setDataList(listAll);
                    } else {
                        Toast.makeText(LoopDetailsActivity.this,
                                getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                    }
                    Log.i(TAG, "相关数据:" + loopDetailsData.getMsg().toString());
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                Toast.makeText(LoopDetailsActivity.this,
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {

            Log.e(TAG, "圈子详细列表请求失败:" + msg);

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

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
            try {
                JSONObject object = new JSONObject(str);
                String status = object.getString("status");
                if (!TextUtils.isEmpty(status)) {
                    if (status.equals("1")) {
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

}
