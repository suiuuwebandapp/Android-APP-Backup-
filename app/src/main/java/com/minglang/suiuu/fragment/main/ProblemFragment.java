package com.minglang.suiuu.fragment.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.CommunitySearchActivity;
import com.minglang.suiuu.activity.ProblemDetailsActivity;
import com.minglang.suiuu.activity.PutQuestionsActivity;
import com.minglang.suiuu.activity.SelectCountryActivity;
import com.minglang.suiuu.adapter.CommunitySortAdapter;
import com.minglang.suiuu.adapter.ProblemAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.MainCommunity;
import com.minglang.suiuu.entity.MainCommunity.MainCommunityData.MainCommunityItemData;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 问答社区页面
 */
public class ProblemFragment extends BaseFragment {

    private static final String TAG = ProblemFragment.class.getSimpleName();

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
    private static final String TAGS = "tag";

    private static final String TOKEN = "token";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private static final String OTHER_TAG = "OtherTag";

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

    private static final int number = 10;

    private String countryId = null;

    private String cityId = null;

    private boolean isPullToRefresh = true;

    private String searchString = null;

    public void setSearchString(String searchString) {
        this.searchString = searchString;
        page = 1;
        selectedState = 4;
        sendRequest();
    }

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.SystemException)
    String SystemException;

    @Bind(R.id.spinner)
    Spinner spinner;

    @Bind(R.id.select_layout)
    FrameLayout selectLayout;

    @Bind(R.id.problem_list_view)
    PullToRefreshListView pullToRefreshListView;

    private List<MainCommunityItemData> listAll = new ArrayList<>();

    private ProblemAdapter adapter;

    public static ProblemFragment newInstance(String param1, String param2) {
        ProblemFragment fragment = new ProblemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProblemFragment() {

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
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            userSign = getArguments().getString(ARG_PARAM1);
            verification = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_problem, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        viewAction();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 初始化方法
     */
    private void initView() {
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        ListView listView = pullToRefreshListView.getRefreshableView();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        stringArray = getResources().getStringArray(R.array.communitySort);
        CommunitySortAdapter adapter = new CommunitySortAdapter(stringArray, getActivity());
        spinner.setAdapter(adapter);

        this.adapter = new ProblemAdapter(getActivity());
        listView.setAdapter(this.adapter);

        token = SuiuuInfo.ReadAppTimeSign(getActivity());
    }

    /**
     * 各种控件的相关事件
     */
    private void viewAction() {

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
                sendRequest();
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
                sendRequest();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                isPullToRefresh = false;
                sendRequest();
            }

        });

        adapter.setOnCommunityClickListener(new ProblemAdapter.OnCommunityClickListener() {
            @Override
            public void onClickListener(View itemView, int position) {
                String qID = listAll.get(position).getQId();
                String qTitle = listAll.get(position).getQTitle();
                String tags = listAll.get(position).getQTag();

                Intent intent = new Intent(getActivity(), ProblemDetailsActivity.class);
                intent.putExtra(ID, qID);
                intent.putExtra(TITLE, qTitle);
                intent.putExtra(TAGS, tags);
                startActivity(intent);
            }
        });

        selectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectCountryActivity.class);
                intent.putExtra(OTHER_TAG, TAG);
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
    private String buildUrl(int selected, int page) {
        String url = "";
        switch (selected) {
            case 0:
                String[] keyArray0 = new String[]{HttpNewServicePath.key, NUMBER, PAGES, TOKEN};
                String[] valueArray0 = new String[]{verification, String.valueOf(number), String.valueOf(page), token};
                url = addUrlAndParams(HttpNewServicePath.getMainProblemListPath, keyArray0, valueArray0);
                break;

            case 1:
                String[] keyArray1 = new String[]{HttpNewServicePath.key, NUMBER, PAGES, SORT_NAME, TOKEN};
                String[] valueArray1 = new String[]{verification, String.valueOf(number), String.valueOf(page), String.valueOf(0), token};
                url = addUrlAndParams(HttpNewServicePath.getMainProblemListPath, keyArray1, valueArray1);
                break;

            case 2:
                String[] keyArray2 = new String[]{HttpNewServicePath.key, NUMBER, PAGES, SORT_NAME, TOKEN};
                String[] valueArray2 = new String[]{verification, String.valueOf(number), String.valueOf(page), String.valueOf(1), token};
                url = addUrlAndParams(HttpNewServicePath.getMainProblemListPath, keyArray2, valueArray2);
                break;

            case 3:
                String[] keyArray3 = new String[]{HttpNewServicePath.key, NUMBER, PAGES, SORT_NAME, COUNTRY_ID, CITY_ID, TOKEN};
                String[] valueArray3 = new String[]{verification, String.valueOf(number), String.valueOf(page), String.valueOf(1),
                        countryId, cityId, token};
                url = addUrlAndParams(HttpNewServicePath.getMainProblemListPath, keyArray3, valueArray3);
                break;

            case 4:
                String[] keyArray4 = new String[]{HttpNewServicePath.key, NUMBER, PAGES, SORT_NAME, COUNTRY_ID, CITY_ID, SEARCH, TOKEN};
                String[] valueArray4 = new String[]{verification, String.valueOf(number), String.valueOf(page), String.valueOf(1),
                        countryId, cityId, searchString, token};
                url = addUrlAndParams(HttpNewServicePath.getMainProblemListPath, keyArray4, valueArray4);
                break;

        }
        L.i(TAG, "问答社区数据请求URL:" + url);
        return url;
    }

    private void sendRequest() {
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        try {
            String _url = buildUrl(selectedState, page);
            OkHttpManager.onGetAsynRequest(_url, new CommunityResultCallBack());
        } catch (IOException e) {
            e.printStackTrace();
            lessPageNumber();
            hideDialog();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }
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
        Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
    }

    /**
     * 绑定数据到View上
     *
     * @param str Json字符串
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            lessPageNumber();
            Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
        } else try {
            JSONObject object = new JSONObject(str);
            String status = object.getString(STATUS);
            switch (status) {
                case "1":
                    MainCommunity mainCommunity = JsonUtils.getInstance().fromJSON(MainCommunity.class, str);
                    if (mainCommunity != null) {
                        MainCommunity.MainCommunityData communityData = mainCommunity.getData();
                        if (communityData != null) {
                            List<MainCommunityItemData> list = communityData.getData();
                            if (list != null && list.size() > 0) {
                                clearDataList();
                                listAll.addAll(list);
                                adapter.setList(listAll);
                            } else {
                                L.e(TAG, "返回列表数据为Null");
                                clearOldDataList();
                                lessPageNumber();
                            }
                        } else {
                            L.e(TAG, "返回二级数据为Null");
                            lessPageNumber();
                        }
                    } else {
                        L.e(TAG, "返回一级数据为Null");
                        lessPageNumber();
                    }
                    break;

                case "-1":
                    Toast.makeText(getActivity(), SystemException, Toast.LENGTH_SHORT).show();
                    break;

                case "-2":
                    Toast.makeText(getActivity(), object.getString(DATA), Toast.LENGTH_SHORT).show();
                    break;

                case "-3":
                    ReturnLoginActivity(getActivity());
                    break;

                case "-4":
                    Toast.makeText(getActivity(), object.getString(DATA), Toast.LENGTH_SHORT).show();
                    break;

                default:
                    Toast.makeText(getActivity(), DataError, Toast.LENGTH_SHORT).show();
                    break;

            }
        } catch (Exception e) {
            L.e(TAG, "解析异常:" + e.getMessage());
            lessPageNumber();
            Toast.makeText(getActivity(), DataError, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            L.e(TAG, "return information is null");
        } else if (data == null) {
            L.e(TAG, "back data is null!");
        } else {
            switch (requestCode) {
                case AppConstant.SELECT_COUNTRY_OK:
                    countryId = data.getStringExtra(COUNTRY_ID);
                    cityId = data.getStringExtra(CITY_ID);

                    page = 1;
                    selectedState = 3;
                    sendRequest();

                    L.i(TAG, "countryId:" + countryId);
                    L.i(TAG, "countryCNname:" + data.getStringExtra("countryCNname"));
                    L.i(TAG, "cityId:" + cityId);
                    L.i(TAG, "cityName:" + data.getStringExtra("cityName"));
                    break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_problem_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to_search_problem:
                Intent intent = new Intent(getActivity(), CommunitySearchActivity.class);
                startActivityForResult(intent, AppConstant.COMMUNITY_SEARCH_SKIP);
                break;

            case R.id.go_to_make_question:
                Intent intent2 = new Intent(getActivity(), PutQuestionsActivity.class);
                intent2.putExtra(COUNTRY_ID, getCountryId());
                intent2.putExtra(CITY_ID, getCityId());
                startActivity(intent2);
        }
        return true;
    }

    public String getUserSign() {
        return userSign;
    }

    private class CommunityResultCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "问答社区返回的数据:" + response);
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Exception:" + e.getMessage());
            lessPageNumber();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }

    }

}