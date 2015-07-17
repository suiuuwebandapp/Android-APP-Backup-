package com.minglang.suiuu.fragment.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SelectCountryActivity;
import com.minglang.suiuu.adapter.CommunitySortAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.MainCommunity.MainCommunityData.MainCommunityItemData;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 问答社区页面
 */
public class CommunityFragment extends BaseFragment {
    private static final String TAG = CommunityFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String NUMBER = "number";
    private static final String PAGES = "page";
    private static final String SORT_NAME = "sortName";

    private String userSign;
    private String verification;

    private ProgressDialog progressDialog;

    private String[] stringArray;

    /**
     * 选择状态
     */
    private int selectedState = 0;

    /**
     * 页码
     */
    private int page = 1;

    @Bind(R.id.spinner)
    Spinner spinner;

    @Bind(R.id.selectLayout)
    FrameLayout selectLayout;

    @Bind(R.id.CommunityListView)
    PullToRefreshListView pullToRefreshListView;

    private List<MainCommunityItemData> list = new ArrayList<>();

    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CommunityFragment() {

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
        View rootView = inflater.inflate(R.layout.fragment_community, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        ViewAction();
        getProblemList(buildRequestParams(selectedState, page));
        DeBugLog.i(TAG, "userSign:" + userSign + ",verification:" + verification);
        return rootView;
    }

    private void initView() {
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.load_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        stringArray = getResources().getStringArray(R.array.communitySort);
        CommunitySortAdapter adapter = new CommunitySortAdapter(stringArray, getActivity());
        spinner.setAdapter(adapter);
    }

    private void ViewAction() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (stringArray[position]) {
                    case "默认":
                        selectedState = 0;
                        break;
                    case "热门":
                        selectedState = 1;
                        break;
                    case "时间":
                        selectedState = 2;
                        break;
                }
                page = 1;
                getProblemList(buildRequestParams(selectedState, page));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;
                getProblemList(buildRequestParams(selectedState, page));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                getProblemList(buildRequestParams(selectedState, page));
            }

        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        selectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectCountryActivity.class);
                startActivityForResult(intent, AppConstant.SELECT_COUNTRY_OK);
            }
        });

    }

    /**
     * 网络请求参数构造
     *
     * @param selected 排序类型
     * @param page     页码
     * @return 网络请求参数
     */
    private RequestParams buildRequestParams(int selected, int page) {
        RequestParams params = new RequestParams();
        switch (selected) {
            case 0:
                params.addBodyParameter(HttpServicePath.key, verification);
                params.addBodyParameter(NUMBER, String.valueOf(20));
                params.addBodyParameter(PAGES, String.valueOf(page));
                break;

            case 1:
                params.addBodyParameter(HttpServicePath.key, verification);
                params.addBodyParameter(NUMBER, String.valueOf(20));
                params.addBodyParameter(PAGES, String.valueOf(page));
                params.addBodyParameter(SORT_NAME, String.valueOf(0));
                break;

            case 2:
                params.addBodyParameter(HttpServicePath.key, verification);
                params.addBodyParameter(NUMBER, String.valueOf(20));
                params.addBodyParameter(PAGES, String.valueOf(page));
                params.addBodyParameter(SORT_NAME, String.valueOf(1));
                break;
        }
        return params;
    }

    /**
     * 网络请求方法
     *
     * @param params 请求参数
     */
    private void getProblemList(RequestParams params) {
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getMainProblemListPath, new CommunityRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        pullToRefreshListView.onRefreshComplete();
    }

    private void lessPageNumber() {
        if (page > 1) {
            page = page - 1;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DeBugLog.i(TAG, "requestCode:" + requestCode + ",resultCode:" + resultCode);
    }

    /**
     * 网络请求回调接口
     */
    private class CommunityRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onLoading(long total, long current, boolean isUploading) {
            DeBugLog.i(TAG, "total:" + total + ",current:" + current + "isUploading:" + Boolean.toString(isUploading));
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            hideDialog();
            String str = responseInfo.result;
            DeBugLog.i(TAG, "返回的数据:" + str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",error:" + s);
            lessPageNumber();
            hideDialog();
        }

    }

}