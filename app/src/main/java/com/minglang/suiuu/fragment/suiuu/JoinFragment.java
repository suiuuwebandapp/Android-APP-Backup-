package com.minglang.suiuu.fragment.suiuu;

import android.app.ProgressDialog;
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
import com.minglang.suiuu.adapter.JoinAdapter;
import com.minglang.suiuu.entity.ConfirmJoinSuiuu;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuiuuHttp;

import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 已参加的随游
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link JoinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinFragment extends Fragment {
    private static final String TAG = JoinFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private static final String USER_SIGN = "userSign";
    private static final String PAGE = "page";
    private static final String NUMBER = "number";
    private static final String TRIP_ID = "tripId";

    private String userSign;
    private String verification;
    private String tripId;

    @BindString(R.string.load_wait)
    String wait;

    @Bind(R.id.JoinListView)
    PullToRefreshListView pullToRefreshListView;

    private ProgressDialog progressDialog;

    private JoinAdapter joinAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JoinFragment.
     */
    public static JoinFragment newInstance(String param1, String param2, String param3) {
        JoinFragment fragment = new JoinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    public JoinFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userSign = getArguments().getString(ARG_PARAM1);
            verification = getArguments().getString(ARG_PARAM2);
            tripId = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_join, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        ViewAction();
        getJoinSuiuuData(1);
        DeBugLog.i(TAG, "userSign:" + userSign + ",verification:" + verification + ",tripId:" + tripId);
        return rootView;
    }

    /**
     * 初始化方法
     */
    private void initView() {
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ListView listView = pullToRefreshListView.getRefreshableView();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(wait);
        progressDialog.setCanceledOnTouchOutside(false);

        joinAdapter = new JoinAdapter(getActivity());
        listView.setAdapter(joinAdapter);
    }

    private void ViewAction() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                pullToRefreshListView.onRefreshComplete();
            }
        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = position - 1;
                Toast.makeText(getActivity(), "click " + index, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getJoinSuiuuData(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(USER_SIGN, userSign);
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter(PAGE, String.valueOf(page));
        params.addBodyParameter(NUMBER, String.valueOf(10));
        params.addBodyParameter(TRIP_ID, tripId);

        SuiuuHttp httpRequest = new SuiuuHttp(HttpRequest.HttpMethod.POST,
                HttpServicePath.getSuiuuItemInfo, new JoinSuiuuRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 隐藏进度框和ListView加载进度View
     */
    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        pullToRefreshListView.onRefreshComplete();
    }

    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(getActivity(), getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
        } else {
            try {
                ConfirmJoinSuiuu confirmJoinSuiuu = JsonUtils.getInstance().fromJSON(ConfirmJoinSuiuu.class, str);
                List<ConfirmJoinSuiuu.ConfirmJoinSuiuuData.PublisherListEntity> list = confirmJoinSuiuu.getData().getPublisherList();
                if (list != null && list.size() > 0) {
                    joinAdapter.setList(list);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析错误:" + e.getMessage());
            }
        }
    }

    private class JoinSuiuuRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            hideDialog();
            String str = responseInfo.result;
            bindData2View(str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",error:" + s);
            hideDialog();
            Toast.makeText(getActivity(), getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }

    }

}