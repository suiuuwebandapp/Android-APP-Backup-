package com.minglang.suiuu.fragment.attention;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.minglang.suiuu.adapter.AttentionProblemAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.AttentionProblem;
import com.minglang.suiuu.entity.AttentionProblem.AttentionProblemData;
import com.minglang.suiuu.entity.AttentionProblem.AttentionProblemData.AttentionProblemItemData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AttentionProblemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttentionProblemFragment extends BaseFragment {

    private static final String TAG = AttentionProblemFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String PAGE = "page";
    private static final String NUMBER = "number";

    private static final String ID = "id";
    private static final String TITLE = "title";

    private String userSign;
    private String verification;

    private int page = 1;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.load_wait)
    String loadingString;

    @Bind(R.id.attention_problem_list_view)
    PullToRefreshListView pullToRefreshListView;

    private List<AttentionProblemItemData> listAll = new ArrayList<>();

    private AttentionProblemAdapter adapter;

    private ProgressDialog progressDialog;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttentionProblemFragment.
     */
    public static AttentionProblemFragment newInstance(String param1, String param2) {
        AttentionProblemFragment fragment = new AttentionProblemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AttentionProblemFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_attention_problem, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        ViewAction();
        getProblemData4Service(buildRequestParams(page));
        DeBugLog.i(TAG, "userSign:" + userSign + ",verification:" + verification);
        return rootView;
    }

    /**
     * 初始化方法
     */
    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(loadingString);
        progressDialog.setCanceledOnTouchOutside(false);

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        ListView listView = pullToRefreshListView.getRefreshableView();

        adapter = new AttentionProblemAdapter(getActivity(), listAll, R.layout.item_attention_problem);
        listView.setAdapter(adapter);
    }

    /**
     * 控件相关事件
     */
    private void ViewAction() {
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;
                getProblemData4Service(buildRequestParams(page));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                getProblemData4Service(buildRequestParams(page));
            }

        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int location = position - 1;
                String strId = listAll.get(location).getQId();
                String strTitle = listAll.get(location).getQTitle();

                Intent intent = new Intent(getActivity(), CommunityDetailsActivity.class);
                intent.putExtra(ID, strId);
                intent.putExtra(TITLE, strTitle);
                startActivity(intent);

            }
        });
    }

    /**
     * 构造网络请求参数
     *
     * @param page 页码
     * @return 网络请求参数
     */
    private RequestParams buildRequestParams(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter(PAGE, String.valueOf(page));
        params.addBodyParameter(NUMBER, String.valueOf(20));
        return params;
    }

    /**
     * 网络请求方法
     *
     * @param params 网络请求参数
     */
    private void getProblemData4Service(RequestParams params) {
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getAttentionProblemInfoPath, new AttentionProblemRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        pullToRefreshListView.onRefreshComplete();
    }

    private void failureLessPage() {
        if (page > 1) {
            page = page - 1;
        }
    }

    private void clearDataList() {
        if (page == 1) {
            if (listAll != null && listAll.size() > 0) {
                listAll.clear();
            }
        }
    }

    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
        } else {
            try {
                AttentionProblem attentionProblem = JsonUtils.getInstance().fromJSON(AttentionProblem.class, str);
                if (attentionProblem != null) {
                    AttentionProblemData problemData = attentionProblem.getData();
                    if (problemData != null) {
                        List<AttentionProblemItemData> list = problemData.getData();
                        if (list != null && list.size() > 0) {
                            clearDataList();
                            listAll.addAll(list);
                            adapter.setList(listAll);
                        } else {
                            failureLessPage();
                            Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        failureLessPage();
                        Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    failureLessPage();
                    Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析错误:" + e.getMessage());
                failureLessPage();
                Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AttentionProblemRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            hideDialog();
            bindData2View(responseInfo.result);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException Error:" + e.getMessage());
            DeBugLog.e(TAG, "Error:" + s);
            hideDialog();
            failureLessPage();
        }

    }

}