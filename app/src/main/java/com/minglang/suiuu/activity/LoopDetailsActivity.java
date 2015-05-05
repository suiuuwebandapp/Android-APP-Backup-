package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
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
import com.minglang.suiuu.utils.SuiuuInformation;
import com.minglang.suiuu.utils.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子-详情页页面
 */
public class LoopDetailsActivity extends Activity {

    private static final String TAG = LoopDetailsActivity.class.getSimpleName();

    private static final String TYPE = "type";
    private static final String CIRCLEID = "circleId";

    private static final String ARTICLEID = "articleId";

    private SystemBarTintManager systemBarTintManager;

    /**
     * 虚拟按键高度
     */
    private int navigationBarHeight;

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

    /**
     * 屏幕宽度
     */
    private int screenWidth;

    /**
     * 屏幕高度
     */
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_details);

        type = getIntent().getStringExtra(TYPE);
        circleId = getIntent().getStringExtra(CIRCLEID);
        Verification = SuiuuInformation.ReadVerification(this);

        initView();
        ViewAction();
        getInternetServiceData();
    }

    /**
     * 网络数据请求
     */
    private void getInternetServiceData() {
        if (progressDialog != null) {
            progressDialog.show();
        }

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        loopDetailsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleId = listAll.get(position).getArticleId();
                Intent intent = new Intent(LoopDetailsActivity.this, LoopArticleActivity.class);
                intent.putExtra(ARTICLEID, articleId);
                startActivity(intent);
            }
        });

    }

    /**
     * 初始化方法
     */
    private void initView() {
        systemBarTintManager = new SystemBarTintManager(this);
        SystemBarTintManager.SystemBarConfig systemBarConfig = systemBarTintManager.getConfig();
        /**
         状态栏高度
         */
        int statusBarHeight = systemBarConfig.getStatusBarHeight();
        navigationBarHeight = systemBarConfig.getNavigationBarHeight();

        /****************设置状态栏颜色*************/
        systemBarTintManager.setStatusBarTintEnabled(true);
        systemBarTintManager.setNavigationBarTintEnabled(false);
        systemBarTintManager.setTintColor(getResources().getColor(R.color.tr_black));

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
        screenWidth = screenUtils.getScreenWidth();
        screenHeight = screenUtils.getScreenHeight();

        loopDetailsAdapter = new LoopDetailsAdapter(this);
        loopDetailsAdapter.setScreenParams(screenWidth, screenHeight);
        loopDetailsGridView.setAdapter(loopDetailsAdapter);
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

    class LoopDetailsRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
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

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
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

}
