package com.minglang.suiuu.fragment.loop;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.LoopDetailsActivity;
import com.minglang.suiuu.adapter.AreaAdapter;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshGridView;
import com.minglang.suiuu.entity.LoopBase;
import com.minglang.suiuu.entity.LoopBaseData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 地区页面
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link AreaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AreaFragment extends Fragment {

    private static final String TAG = AreaFragment.class.getSimpleName();

    private static final String TYPE = "type";
    private static final String CIRCLEID = "circleId";

    private PullToRefreshGridView areaGridView;

    /**
     * 数据集合
     */
    private List<LoopBaseData> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    /**
     * 屏幕宽度
     */
    private int screenWidth;

    private int page = 1;

    private TextView refreshDataView;

    private TextProgressDialog mProgressDialog;

    private AreaAdapter areaAdapter;

    private boolean refreshDataFlag;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AreaFragment.
     */
    public static AreaFragment newInstance(String param1, String param2) {
        AreaFragment fragment = new AreaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AreaFragment() {
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
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_area, null);

        ScreenUtils screenUtils = new ScreenUtils(getActivity());
        screenWidth = screenUtils.getScreenWidth();

        initView(rootView);
        ViewAction();
        getData();
        DeBugLog.i(TAG, "userSign:" + userSign);
        return rootView;
    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        refreshDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                refreshDataFlag = true;

                refreshDataView.setVisibility(View.INVISIBLE);

                if (mProgressDialog != null) {
                    mProgressDialog.showDialog();
                }

                page = 1;
                getInternetServiceData(page);
            }
        });

        areaGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                areaGridView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {

                refreshDataFlag = false;

                page = page + 1;
                getInternetServiceData(page);
            }
        });

        areaGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String circleId = listAll.get(position).getcId();
                String loopName = listAll.get(position).getcName();
                Intent intent = new Intent(getActivity(), LoopDetailsActivity.class);
                intent.putExtra(CIRCLEID, circleId);
                intent.putExtra(TYPE, "2");
                intent.putExtra("name", loopName);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

    }

    private void getData() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        getInternetServiceData(page);
    }

    /**
     * 从网络获取数据
     */
    private void getInternetServiceData(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter(TYPE, "2");
        params.addBodyParameter("page", String.valueOf(page));
        params.addBodyParameter("number", String.valueOf(10));

        SuHttpRequest suHttpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.LoopDataPath, new AreaRequestCallback());
        suHttpRequest.setParams(params);
        suHttpRequest.requestNetworkData();
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment根View
     */
    private void initView(View rootView) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(getResources().getString(R.string.load_wait));

        mProgressDialog = new TextProgressDialog(getActivity());
        mProgressDialog.setMessage(getResources().getString(R.string.pull_to_refresh_footer_refreshing_label));

        areaGridView = (PullToRefreshGridView) rootView.findViewById(R.id.areaGridView);
        areaGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshDataView = (TextView) rootView.findViewById(R.id.areaNoDataView);

        areaAdapter = new AreaAdapter(getActivity());
        areaAdapter.setScreenParams(screenWidth);
        areaGridView.setAdapter(areaAdapter);
    }

    /**
     * 网络请求回调接口
     */
    class AreaRequestCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (mProgressDialog.isShow()) {
                mProgressDialog.dismissDialog();
            }

            areaGridView.onRefreshComplete();

            String str = responseInfo.result;
            try {
                LoopBase loopBase = JsonUtils.getInstance().fromJSON(LoopBase.class, str);
                if (Integer.parseInt(loopBase.getStatus()) == 1) {
                    List<LoopBaseData> list = loopBase.getData().getData();
                    if (list != null && list.size() > 0) {
                        refreshDataView.setVisibility(View.INVISIBLE);
                        listAll.addAll(list);
                        areaAdapter.setList(listAll);
                    } else {

                        if (page > 1) {
                            page = page - 1;
                        }

                        if (refreshDataFlag) {
                            refreshDataView.setVisibility(View.VISIBLE);
                        }

                        Toast.makeText(getActivity(), getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                    }
                } else {

                    if (page > 1) {
                        page = page - 1;
                    }

                    if (refreshDataFlag) {
                        refreshDataView.setVisibility(View.VISIBLE);
                    }

                    Toast.makeText(getActivity(), getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "地区数据解析错误:" + e.getMessage());

                if (page > 1) {
                    page = page - 1;
                }

                if (refreshDataFlag) {
                    refreshDataView.setVisibility(View.VISIBLE);
                }

                Toast.makeText(getActivity(), getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
            DeBugLog.i(TAG, "page:" + page);
        }

        @Override
        public void onFailure(HttpException error, String msg) {

            DeBugLog.e(TAG, "地区数据请求失败:" + msg);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (mProgressDialog.isShow()) {
                mProgressDialog.dismissDialog();
            }

            if (page > 1) {
                page = page - 1;
            }

            if (refreshDataFlag) {
                refreshDataView.setVisibility(View.VISIBLE);
            }

            areaGridView.onRefreshComplete();

            Toast.makeText(getActivity(), getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
            DeBugLog.i(TAG, "page:" + page);
        }
    }

}