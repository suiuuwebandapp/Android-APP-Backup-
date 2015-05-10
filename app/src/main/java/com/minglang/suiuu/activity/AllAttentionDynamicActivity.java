package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.minglang.suiuu.entity.AllAttentionDynamic;
import com.minglang.suiuu.entity.AllAttentionDynamicData;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 查看更多关注页面
 */
public class AllAttentionDynamicActivity extends Activity {

    private static final String TAG = AllAttentionDynamicActivity.class.getSimpleName();

    private static final String PAGE = "page";

    /**
     * 下拉刷新组件
     */
    private PtrClassicFrameLayout mPtrFrame;

    /**
     * 上拉加载组件
     */
    private LoadMoreListViewContainer loadMoreContainer;

    private ListView listView;

    private int page = 1;

    private List<AllAttentionDynamicData> listAll = new ArrayList<>();

    private AllAttentionDynamicAdapter adapter;

    private ProgressDialog dialog;

    private ImageView back;

    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_attention_dynamic);

        initView();

        ViewAction();

        getDynamicData4Service();
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

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                isRefresh = true;

                RequestParams params = new RequestParams();
                params.addBodyParameter(PAGE, "1");
                params.addBodyParameter(HttpServicePath.key,
                        SuiuuInfo.ReadVerification(AllAttentionDynamicActivity.this));

                SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                        HttpServicePath.AllAttentionDynamicPath, new AllAttentionDynamicRequestCallBack());
                httpRequest.setParams(params);
                httpRequest.requestNetworkData();
            }
        });

        loadMoreContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {

                page = page + 1;

                isRefresh = false;

                RequestParams params = new RequestParams();
                params.addBodyParameter(PAGE, String.valueOf(page));

                SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                        HttpServicePath.AllAttentionDynamicPath, new AllAttentionDynamicRequestCallBack());
                httpRequest.setParams(params);
                httpRequest.requestNetworkData();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleId = listAll.get(position).getArticleId();
                Intent intent = new Intent(AllAttentionDynamicActivity.this, LoopArticleActivity.class);
                intent.putExtra("articleId", articleId);
                startActivity(intent);
            }
        });
    }

    /**
     * 绑定数据到View
     *
     * @param str Json字符串
     */
    private void setData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(this, getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
        } else {
            try {
                AllAttentionDynamic dynamic = JsonUtil.getInstance().fromJSON(AllAttentionDynamic.class, str);
                List<AllAttentionDynamicData> list = dynamic.getData().getData();
                if (list != null && list.size() > 0) {
                    listAll.addAll(list);
                    adapter.setList(listAll);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "关注详细信息解析错误:" + e.getMessage());
                Toast.makeText(this, getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 从网络获取数据
     */
    private void getDynamicData4Service() {

        if (dialog != null) {
            dialog.show();
        }

        RequestParams params = new RequestParams();
        params.addBodyParameter(PAGE, String.valueOf(page));
        //params.addBodyParameter("usersign", sign);
        params.addBodyParameter(HttpServicePath.key, SuiuuInfo.ReadVerification(this));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.AllAttentionDynamicPath, new AllAttentionDynamicRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 初始化方法
     */
    private void initView() {

        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.load_wait));
        dialog.setCanceledOnTouchOutside(false);

        back = (ImageView) findViewById(R.id.AllAttentionBack);
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.all_attention_dynamic_frame);
        listView = (ListView) findViewById(R.id.all_attention_dynamic_list_view);
        loadMoreContainer = (LoadMoreListViewContainer) findViewById(R.id.all_attention_dynamic_load_more_container);

        MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrame);

        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setPinContent(true);

        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);

        adapter = new AllAttentionDynamicAdapter(this);
        listView.setAdapter(adapter);

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
     * 网络请求回调接口
     */
    private class AllAttentionDynamicRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            mPtrFrame.refreshComplete();
            loadMoreContainer.loadMoreFinish(true, true);

            if (isRefresh) {
                listAll.clear();
            }

            String str = stringResponseInfo.result;
            Log.i(TAG, "关注详细信息数据:" + str);
            setData2View(str);
        }

        @Override
        public void onFailure(HttpException e, String s) {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            mPtrFrame.refreshComplete();

            if (page > 1) {
                page = page - 1;
            }

            int error = 0;
            String errorMessage = "加载失败，点击加载更多";
            loadMoreContainer.loadMoreError(error, errorMessage);

            Log.e(TAG, "关注动态数据加载失败:" + s);
            Toast.makeText(AllAttentionDynamicActivity.this,
                    getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
        }
    }
}
