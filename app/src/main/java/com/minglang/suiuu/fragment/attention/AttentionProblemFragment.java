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

import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.ProblemDetailsActivity;
import com.minglang.suiuu.adapter.AttentionProblemAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.AttentionProblem;
import com.minglang.suiuu.entity.AttentionProblem.AttentionProblemData;
import com.minglang.suiuu.entity.AttentionProblem.AttentionProblemData.AttentionProblemItemData;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    private static final String ARG_PARAM3 = "param3";

    private static final String PAGE = "page";
    private static final String NUMBER = "number";

    private static final String ID = "id";
    private static final String TITLE = "title";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private String userSign;
    private String verification;

    private int page = 1;

    private boolean isPullToRefresh = true;

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
     * @param param3 Parameter 3.
     * @return A new instance of fragment AttentionProblemFragment.
     */
    public static AttentionProblemFragment newInstance(String param1, String param2, String param3) {
        AttentionProblemFragment fragment = new AttentionProblemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
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
            token = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_attention_problem, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        ViewAction();
        getProblemData4Service(page);
        return rootView;
    }

    /**
     * 初始化方法
     */
    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(DialogMsg);
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
                isPullToRefresh = false;
                getProblemData4Service(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                isPullToRefresh = false;
                getProblemData4Service(page);
            }

        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int location = position - 1;
                String strId = listAll.get(location).getQId();
                String strTitle = listAll.get(location).getQTitle();

                Intent intent = new Intent(getActivity(), ProblemDetailsActivity.class);
                intent.putExtra(ID, strId);
                intent.putExtra(TITLE, strTitle);
                startActivity(intent);

            }
        });
    }

    /**
     * 网络请求方法
     *
     * @param page 页码
     */
    private void getProblemData4Service(int page) {
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        String[] keyArray = new String[]{USER_SIGN, PAGE, NUMBER, TOKEN};
        String[] valueArray = new String[]{userSign, String.valueOf(page), String.valueOf(20), token};
        String url = addUrlAndParams(HttpNewServicePath.getAttentionProblemInfoPath, keyArray, valueArray);

        try {
            OkHttpManager.onGetAsynRequest(url, new AttentionProblemResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            failureLessPage();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }
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
            failureLessPage();
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
                            L.e(TAG, "列表为Null");
                            failureLessPage();
                            Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        L.e(TAG, "第二层为Null");
                        failureLessPage();
                        Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    L.e(TAG, "第一层为Null");
                    failureLessPage();
                    Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                L.e(TAG, "解析错误:" + e.getMessage());
                failureLessPage();
                try {
                    JSONObject object = new JSONObject(str);
                    String status = object.getString(STATUS);
                    switch (status) {
                        case "-1":
                            Toast.makeText(getActivity(), SystemException, Toast.LENGTH_SHORT).show();
                            break;
                        case "-2":
                            Toast.makeText(getActivity(), object.getString(DATA), Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    Toast.makeText(getActivity(), DataError, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class AttentionProblemResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            hideDialog();
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Exception:" + e.getMessage());
            hideDialog();
            failureLessPage();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

}