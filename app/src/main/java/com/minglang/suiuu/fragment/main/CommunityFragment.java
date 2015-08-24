package com.minglang.suiuu.fragment.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.CommunityDetailsActivity;
import com.minglang.suiuu.activity.SelectCountryActivity;
import com.minglang.suiuu.adapter.CommunityAdapter;
import com.minglang.suiuu.adapter.CommunitySortAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.MainCommunity;
import com.minglang.suiuu.entity.MainCommunity.MainCommunityData;
import com.minglang.suiuu.entity.MainCommunity.MainCommunityData.MainCommunityItemData;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuiuuHttp;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
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

    private static final String COUNTRY_ID = "countryId";
    private static final String CITY_ID = "cityId";

    private static final String SEARCH = "search";

    private static final String ID = "id";
    private static final String TITLE = "title";

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

    private String countryId = null;

    private String cityId = null;

    private boolean isPullToRefresh = true;

    private String searchString = null;

    public void setSearchString(String searchString) {
        this.searchString = searchString;
        page = 1;
        selectedState = 4;
        getProblemList(buildRequestParams(selectedState, page));
    }

    @BindString(R.string.load_wait)
    String dialogMsg;

    @BindString(R.string.NoData)
    String NoDataHint;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @Bind(R.id.spinner)
    Spinner spinner;

    @Bind(R.id.selectLayout)
    FrameLayout selectLayout;

    @Bind(R.id.CommunityListView)
    PullToRefreshListView pullToRefreshListView;

    private List<MainCommunityItemData> listAll = new ArrayList<>();

    private CommunityAdapter adapter;

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

    public String getCountryId() {
        return countryId;
    }

    public String getCityId() {
        return cityId;
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
        DeBugLog.i(TAG, "userSign:" + userSign + ",verification:" + verification);
        return rootView;
    }

    /**
     * 初始化方法
     */
    private void initView() {
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        ListView listView = pullToRefreshListView.getRefreshableView();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(dialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        stringArray = getResources().getStringArray(R.array.communitySort);
        CommunitySortAdapter adapter = new CommunitySortAdapter(stringArray, getActivity());
        spinner.setAdapter(adapter);

        this.adapter = new CommunityAdapter(getActivity());
        listView.setAdapter(this.adapter);
    }

    /**
     * 各种控件的相关事件
     */
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
                isPullToRefresh = false;
                getProblemList(buildRequestParams(selectedState, page));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                isPullToRefresh = false;
                getProblemList(buildRequestParams(selectedState, page));
            }

        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int location = position - 1;
                String qID = listAll.get(location).getQId();
                String qTitle = listAll.get(location).getQTitle();
                DeBugLog.i(TAG, "location:" + location + ",qID:" + qID + ",qTitle:" + qTitle);

                Intent intent = new Intent(getActivity(), CommunityDetailsActivity.class);
                intent.putExtra(ID, qID);
                intent.putExtra(TITLE, qTitle);
                startActivity(intent);
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
        DeBugLog.i(TAG, "当前页码:" + page);
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

            case 3:
                params.addBodyParameter(HttpServicePath.key, verification);
                params.addBodyParameter(NUMBER, String.valueOf(20));
                params.addBodyParameter(PAGES, String.valueOf(page));
                params.addBodyParameter(SORT_NAME, String.valueOf(1));
                params.addBodyParameter(COUNTRY_ID, countryId);
                params.addBodyParameter(CITY_ID, cityId);
                break;
            case 4:
                params.addBodyParameter(HttpServicePath.key, verification);
                params.addBodyParameter(NUMBER, String.valueOf(20));
                params.addBodyParameter(PAGES, String.valueOf(page));
                params.addBodyParameter(SORT_NAME, String.valueOf(1));
                params.addBodyParameter(COUNTRY_ID, countryId);
                params.addBodyParameter(CITY_ID, cityId);
                params.addBodyParameter(SEARCH, searchString);
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
        SuiuuHttp httpRequest = new SuiuuHttp(HttpRequest.HttpMethod.POST,
                HttpServicePath.getMainProblemListPath, new CommunityRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 隐藏加载的进度框等
     */
    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        pullToRefreshListView.onRefreshComplete();
    }

    /**
     * 请求失败，页码减1
     */
    private void lessPageNumber() {
        if (page > 1) {
            page = page - 1;
        }
    }

    /**
     * 请求第一页时，清除其他数据
     */
    private void clearDataList() {
        if (page == 1) {
            if (listAll != null && listAll.size() > 0) {
                listAll.clear();
            }
        }
    }

    private void clearOldDataList() {
        if (page == 1) {
            if (listAll != null && listAll.size() > 0) {
                listAll.clear();
                adapter.setList(listAll);
            }
        }
        Toast.makeText(getActivity(), NoDataHint, Toast.LENGTH_SHORT).show();
    }

    /**
     * 绑定数据到View上
     *
     * @param str Json字符串
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            lessPageNumber();
            Toast.makeText(getActivity(), NoDataHint, Toast.LENGTH_SHORT).show();
        } else {
            try {
                MainCommunity mainCommunity = JsonUtils.getInstance().fromJSON(MainCommunity.class, str);
                if (mainCommunity != null) {
                    MainCommunityData communityData = mainCommunity.getData();
                    if (communityData != null) {
                        List<MainCommunityItemData> list = communityData.getData();
                        if (list != null && list.size() > 0) {
                            clearDataList();
                            listAll.addAll(list);
                            adapter.setList(listAll);
                            DeBugLog.i(TAG, "当前数据数量:" + listAll.size());
                        } else {
                            DeBugLog.e(TAG, "返回列表数据为Null");
                            clearOldDataList();
                            lessPageNumber();
                        }
                    } else {
                        DeBugLog.e(TAG, "返回二级数据为Null");
                        lessPageNumber();
                    }
                } else {
                    DeBugLog.e(TAG, "返回一级数据为Null");
                    lessPageNumber();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析异常:" + e.getMessage());
                lessPageNumber();
                Toast.makeText(getActivity(), DataError, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        DeBugLog.i(TAG, "requestCode:" + requestCode + ",resultCode:" + resultCode);

        if (resultCode != Activity.RESULT_OK) {
            DeBugLog.e(TAG, "return information is null");
        } else if (data == null) {
            DeBugLog.e(TAG, "back data is null!");
        } else {
            switch (requestCode) {
                case AppConstant.SELECT_COUNTRY_OK:
                    countryId = data.getStringExtra("countryId");
                    cityId = data.getStringExtra("cityId");

                    page = 1;
                    selectedState = 3;
                    getProblemList(buildRequestParams(selectedState, page));

                    DeBugLog.i(TAG, "countryId:" + countryId);
                    DeBugLog.i(TAG, "countryCNname:" + data.getStringExtra("countryCNname"));
                    DeBugLog.i(TAG, "cityId:" + cityId);
                    DeBugLog.i(TAG, "cityName:" + data.getStringExtra("cityName"));
                    break;
            }
        }
    }

    /**
     * 网络请求回调接口
     */
    private class CommunityRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (isPullToRefresh)
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String str = responseInfo.result;
            DeBugLog.i(TAG, "问答社区返回的数据:" + str);
            hideDialog();
            bindData2View(str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",error:" + s);
            lessPageNumber();
            hideDialog();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

}