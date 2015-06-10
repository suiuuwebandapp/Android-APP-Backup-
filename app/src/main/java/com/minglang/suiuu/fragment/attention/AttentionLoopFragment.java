package com.minglang.suiuu.fragment.attention;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.AttentionLoopAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshGridView;
import com.minglang.suiuu.entity.AttentionLoop;
import com.minglang.suiuu.entity.AttentionLoopData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注主题
 */
public class AttentionLoopFragment extends BaseFragment {

    private static final String TAG = AttentionLoopFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    private PullToRefreshGridView pullToRefreshGridView;

    private int page = 1;

    private List<AttentionLoopData> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    private AttentionLoopAdapter attentionLoopAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttentionThemeFragment.
     */
    public static AttentionLoopFragment newInstance(String param1, String param2) {
        AttentionLoopFragment fragment = new AttentionLoopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AttentionLoopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userSign = getArguments().getString(ARG_PARAM1);
            verification = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_attention_loop, container, false);
        initView(rootView);
        getData();
        ViewAction();

        return rootView;
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment根View
     */
    private void initView(View rootView) {
        ScreenUtils utils = new ScreenUtils(getActivity());
        int screenWidth = utils.getScreenWidth();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.load_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        pullToRefreshGridView = (PullToRefreshGridView) rootView.findViewById(R.id.attentionThemeGridView);
        pullToRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        GridView mGridView = pullToRefreshGridView.getRefreshableView();
        mGridView.setNumColumns(2);

        attentionLoopAdapter = new AttentionLoopAdapter(getActivity());
        attentionLoopAdapter.setScreenParams(screenWidth);
        mGridView.setAdapter(attentionLoopAdapter);

        DeBugLog.i(TAG, "userSign:" + userSign);
    }

    /**
     * 控件动作
     */
    private void ViewAction() {
        pullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                String label = DateUtils.formatDateTime(SuiuuApplication.applicationContext, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;
                getAttentionData4Service(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                String label = DateUtils.formatDateTime(SuiuuApplication.applicationContext, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                getAttentionData4Service(page);
            }

        });

        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void getData() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        getAttentionData4Service(page);
    }

    /**
     * 从服务器获取数据
     */
    private void getAttentionData4Service(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter("page", String.valueOf(page));
        params.addBodyParameter("number", String.valueOf(10));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.AttentionLoopPath, new AttentionLoopRequestCallback());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 将数据绑定到View
     *
     * @param str 网络请求返回的Json字符串
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            failureComputePage();
            Toast.makeText(SuiuuApplication.applicationContext, "关注圈子" +
                    getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
        } else {
            try {
                AttentionLoop attentionLoop = JsonUtils.getInstance().fromJSON(AttentionLoop.class, str);
                if (attentionLoop.getStatus().equals("1")) {
                    List<AttentionLoopData> list = attentionLoop.getData().getData();
                    if (list != null && list.size() > 0) {
                        clearDataList();
                        listAll.addAll(list);
                        attentionLoopAdapter.setList(listAll);
                    } else {
                        failureComputePage();
                        Toast.makeText(SuiuuApplication.applicationContext, "关注圈子" +
                                getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    failureComputePage();
                    Toast.makeText(SuiuuApplication.applicationContext,
                            getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                failureComputePage();
                DeBugLog.e(TAG, "关注的圈子数据解析异常:" + e.getMessage());
                Toast.makeText(SuiuuApplication.applicationContext,
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 隐藏Dialog与刷新View
     */
    private void hideDialogAndRefreshView() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        pullToRefreshGridView.onRefreshComplete();
    }

    /**
     * 请求失败or无数据，请求页数减1
     */
    private void failureComputePage() {
        if (page > 1) {
            page = page - 1;
        }
    }

    /**
     * 请求页码为第一页，且有数据缓存，清除后重新添加
     */
    private void clearDataList() {
        if (page == 1) {
            if (listAll != null && listAll.size() > 0) {
                listAll.clear();
            }
        }
    }

    /**
     * 关注的圈子网络请求回调接口
     */
    private class AttentionLoopRequestCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            hideDialogAndRefreshView();
            bindData2View(stringResponseInfo.result);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "关注的圈子数据请求失败:" + s);

            hideDialogAndRefreshView();
            failureComputePage();

            Toast.makeText(getActivity(),
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }

    }

}