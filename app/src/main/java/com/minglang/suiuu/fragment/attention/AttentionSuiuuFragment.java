package com.minglang.suiuu.fragment.attention;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshGridView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SuiuuDetailsActivity;
import com.minglang.suiuu.adapter.AttentionSuiuuAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.AttentionSuiuu;
import com.minglang.suiuu.entity.AttentionSuiuu.AttentionSuiuuData;
import com.minglang.suiuu.entity.AttentionSuiuu.AttentionSuiuuData.AttentionSuiuuItemData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuiuuHttp;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 关注的随游页面
 */
public class AttentionSuiuuFragment extends BaseFragment {

    private static final String TAG = AttentionSuiuuFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String PAGE = "page";
    private static final String NUMBER = "number";

    private static final String TRIP_ID = "tripId";

    private String userSign;
    private String verification;

    @BindString(R.string.load_wait)
    String dialogMessage;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @Bind(R.id.attention_user_GridView)
    PullToRefreshGridView pullToRefreshGridView;

    private int page = 1;

    private List<AttentionSuiuuItemData> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    private AttentionSuiuuAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttentionUserFragment.
     */
    public static AttentionSuiuuFragment newInstance(String param1, String param2) {
        AttentionSuiuuFragment fragment = new AttentionSuiuuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AttentionSuiuuFragment() {

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
        View rootView = inflater.inflate(R.layout.fragment_attention_user, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        viewAction();
        getData4Service(page);
        return rootView;
    }

    /**
     * 初始化方法
     */
    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(dialogMessage);
        progressDialog.setCanceledOnTouchOutside(false);

        pullToRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        GridView girdView = pullToRefreshGridView.getRefreshableView();
        girdView.setNumColumns(2);
        girdView.setHorizontalSpacing(4);
        girdView.setVerticalSpacing(4);

        ScreenUtils screenUtils = new ScreenUtils(getActivity());

        adapter = new AttentionSuiuuAdapter(getActivity());
        adapter.setScreenWidth(screenUtils.getScreenWidth());
        girdView.setAdapter(adapter);

        DeBugLog.i(TAG, "userSign:" + userSign);
    }

    /**
     * 控件动作
     */
    private void viewAction() {

        pullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;
                getData4Service(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                getData4Service(page);
            }
        });

        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tripId = listAll.get(position).getTripId();
                DeBugLog.i(TAG, "position:" + position + ",tripId:" + tripId);

                Intent intent = new Intent(getActivity(), SuiuuDetailsActivity.class);
                intent.putExtra(TRIP_ID, tripId);
                getActivity().startActivity(intent);
            }
        });

    }

    /**
     * 从服务获取关注用户的数据
     */
    private void getData4Service(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter(PAGE, String.valueOf(page));
        params.addBodyParameter(NUMBER, String.valueOf(20));

        SuiuuHttp httpRequest = new SuiuuHttp(HttpRequest.HttpMethod.POST,
                HttpServicePath.AttentionUserPath, new AttentionSuiuuRequestCallback());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 隐藏Dialog与刷新View
     */
    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        pullToRefreshGridView.onRefreshComplete();
    }

    /**
     * 请求失败or无数据，请求页数减1
     */
    private void failureLessPage() {
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
     * 将数据绑定到View
     *
     * @param str 网络请求返回的Json字符串
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            failureLessPage();
            Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
        } else {
            try {
                AttentionSuiuu attentionSuiuu = JsonUtils.getInstance().fromJSON(AttentionSuiuu.class, str);
                if (attentionSuiuu != null) {
                    AttentionSuiuuData suiuuData = attentionSuiuu.getData();
                    if (suiuuData != null) {
                        List<AttentionSuiuuItemData> list = suiuuData.getData();
                        if (list != null && list.size() > 0) {
                            clearDataList();
                            listAll.addAll(list);
                            adapter.setList(listAll);
                        } else {
                            DeBugLog.e(TAG, "列表数据为空！");
                            failureLessPage();
                            Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        DeBugLog.e(TAG, "第二层为Null");
                        failureLessPage();
                        Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    DeBugLog.e(TAG, "第一层为Null");
                    failureLessPage();
                    Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                failureLessPage();
                DeBugLog.e(TAG, "关注的用户的数据解析失败:" + e.getMessage());
                Toast.makeText(getActivity(), DataError, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 网络请求回调接口
     */
    private class AttentionSuiuuRequestCallback extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> response) {
            String str = response.result;
            DeBugLog.i(TAG, "关注的随游数据:" + str);
            hideDialog();
            bindData2View(str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "关注的用户数据请求失败:" + s);
            hideDialog();
            failureLessPage();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

}