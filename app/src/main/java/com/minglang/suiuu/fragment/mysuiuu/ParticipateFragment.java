package com.minglang.suiuu.fragment.mysuiuu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshListView;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.SuHttpRequest;

/**
 * 已参加的随游页面
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link ParticipateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParticipateFragment extends Fragment {

    private static final String TAG = ParticipateFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    private int page = 1;

    private PullToRefreshListView pullToRefreshListView;

    private ProgressDialog progressDialog;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParticipateFragment.
     */
    public static ParticipateFragment newInstance(String param1, String param2) {
        ParticipateFragment fragment = new ParticipateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ParticipateFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_participate, container, false);

        initView(rootView);
        ViewAction();
        getData();
        return rootView;
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment的根View
     */
    private void initView(View rootView) {
        Log.i(TAG, "userSign:" + userSign + ",verification:" + verification);

        pullToRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.my_suiuu_participate_list_view);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.load_wait));
    }

    private void ViewAction() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                pullToRefreshListView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                pullToRefreshListView.onRefreshComplete();
            }

        });

    }

    private void getData() {

        if (progressDialog != null) {
            progressDialog.show();
        }

        getParticipateSuiuuData(page);

    }

    /**
     * 网络请求
     *
     * @param page 页码
     */
    private void getParticipateSuiuuData(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("userSign", userSign);
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter("page", String.valueOf(page));
        params.addBodyParameter("number", String.valueOf(10));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.MyPublishedSuiuuPath, new MyParticipateRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    private void showOrHideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        pullToRefreshListView.onRefreshComplete();

    }

    private class MyParticipateRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {

            String str = responseInfo.result;
            Log.i(TAG, "我发布的随游:" + str);

            showOrHideDialog();

        }

        @Override
        public void onFailure(HttpException e, String s) {

            DeBugLog.e(TAG, "我发布的随游请求失败(1):" + e.getMessage());
            DeBugLog.e(TAG, "我发布的随游请求失败(2):" + e.getExceptionCode());
            DeBugLog.e(TAG, "我发布的随游请求失败(3):" + s);

            showOrHideDialog();

        }

    }

}