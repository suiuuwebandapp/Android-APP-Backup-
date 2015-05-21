package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.TestLoopDetailsAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.PullToRefreshStaggeredView;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.entity.LoopDetails;
import com.minglang.suiuu.entity.LoopDetailsDataList;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.List;

public class TestLoopDetailsActivity extends BaseActivity {

    private static final String TAG = TestLoopDetailsActivity.class.getSimpleName();

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

    private int page = 1;

    private PullToRefreshStaggeredView staggeredGridView;

    private List<LoopDetailsDataList> listAll = new ArrayList<>();

    private TestLoopDetailsAdapter loopDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_loop_details);

        type = getIntent().getStringExtra(TYPE);
        circleId = getIntent().getStringExtra(CIRCLEID);
        Verification = SuiuuInfo.ReadVerification(this);

        initView();
        ViewAction();
        getInternetServiceData(page);
    }

    private void getInternetServiceData(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, Verification);
        params.addBodyParameter(CIRCLEID, circleId);
        params.addBodyParameter(TYPE, type);
        params.addBodyParameter("number", String.valueOf(10));
        params.addBodyParameter("page", String.valueOf(page));

        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.LoopDetailsPath, new TestLoopDetailsRequestCallBack());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    private void ViewAction() {
        staggeredGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "position:" + position);
            }
        });

        staggeredGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<StaggeredGridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
                page = 1;
                getInternetServiceData(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
                page = page + 1;
                getInternetServiceData(page);
            }
        });

    }

    private void initView() {
        staggeredGridView = (PullToRefreshStaggeredView) findViewById(R.id.test_loop_details_grid_view);
        loopDetailsAdapter = new TestLoopDetailsAdapter(this);
        staggeredGridView.setAdapter(loopDetailsAdapter);
        staggeredGridView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private class TestLoopDetailsRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            if (page == 1) {
                if (listAll != null && listAll.size() > 0) {
                    listAll.clear();
                }
            }

            String str = stringResponseInfo.result;
            try {
                LoopDetails loopDetails = JsonUtils.getInstance().fromJSON(LoopDetails.class, str);
                List<LoopDetailsDataList> list = loopDetails.getData().getData();
                if (list != null && list.size() > 0) {
                    listAll.addAll(list);
                    loopDetailsAdapter.setDataList(listAll);
                } else {
                    if (page > 1) {
                        page = page - 1;
                    }
                    Toast.makeText(TestLoopDetailsActivity.this,
                            getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                if (page > 1) {
                    page = page - 1;
                }
                Log.e(TAG, "请求失败:" + e.getMessage());
                Toast.makeText(TestLoopDetailsActivity.this,
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
            staggeredGridView.onRefreshComplete();
        }

        @Override
        public void onFailure(HttpException e, String s) {

            Log.e(TAG, "圈子详细列表请求失败:" + s);

            staggeredGridView.onRefreshComplete();

            if (page > 1) {
                page = page - 1;
            }

            Toast.makeText(TestLoopDetailsActivity.this,
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }

}
