package com.minglang.suiuu.fragment.collection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.minglang.suiuu.activity.SuiuuDetailActivity;
import com.minglang.suiuu.adapter.CollectionSuiuuAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.entity.CollectionSuiuu;
import com.minglang.suiuu.entity.CollectionSuiuuData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏的路线
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectionSuiuuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionSuiuuFragment extends Fragment {

    private static final String TAG = CollectionSuiuuFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    private PullToRefreshGridView pullToRefreshGridView;

    private int page = 1;

    private List<CollectionSuiuuData> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    private CollectionSuiuuAdapter collectionSuiuuAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CollectionRouteFragment.
     */
    public static CollectionSuiuuFragment newInstance(String param1, String param2) {
        CollectionSuiuuFragment fragment = new CollectionSuiuuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CollectionSuiuuFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_collection_suiuu, container, false);

        initView(rootView);
        ViewAction();
        getData();
        DeBugLog.i(TAG, "userSign:" + userSign);
        return rootView;
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment根View
     */
    private void initView(View rootView) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_bar);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        pullToRefreshGridView = (PullToRefreshGridView) rootView.findViewById(R.id.collection_suiuu_grid_view);
        GridView gridView = pullToRefreshGridView.getRefreshableView();
        gridView.setNumColumns(2);

        ScreenUtils utils = new ScreenUtils(getActivity());
        int screenWidth = utils.getScreenWidth();

        collectionSuiuuAdapter = new CollectionSuiuuAdapter(getActivity());
        collectionSuiuuAdapter.setScreenParams(screenWidth);
        gridView.setAdapter(collectionSuiuuAdapter);
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
                getCollectionSuiuu4Service(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                String label = DateUtils.formatDateTime(SuiuuApplication.applicationContext, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                getCollectionSuiuu4Service(page);
            }

        });

        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tripId = listAll.get(position).getTripId();
                Intent intent = new Intent(getActivity(), SuiuuDetailActivity.class);
                intent.putExtra("tripId", tripId);
                startActivity(intent);
            }
        });
    }

    private void getData() {

        if (progressDialog != null) {
            progressDialog.show();
        }

        getCollectionSuiuu4Service(1);
    }

    /**
     * 网络请求
     */
    private void getCollectionSuiuu4Service(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("page", String.valueOf(page));
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.GetCollectionSuiuuPath, new CollectionSuiuuRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    private void bingData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            failureComputePage();
            Toast.makeText(SuiuuApplication.applicationContext, "随游收藏" +
                    getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
        } else {
            try {
                CollectionSuiuu collectionSuiuu = JsonUtils.getInstance().fromJSON(CollectionSuiuu.class, str);
                if (collectionSuiuu.getStatus().equals("1")) {
                    List<CollectionSuiuuData> list = collectionSuiuu.getData().getData();
                    if (list != null && list.size() > 0) {
                        clearDataList();
                        listAll.addAll(list);
                        collectionSuiuuAdapter.setListData(listAll);
                    } else {
                        failureComputePage();
                        Toast.makeText(SuiuuApplication.applicationContext, "随游收藏" +
                                getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    failureComputePage();
                    Toast.makeText(SuiuuApplication.applicationContext,
                            getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                failureComputePage();
                DeBugLog.e(TAG, "收藏的随游数据解析失败" + e.getMessage());
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
     * 获取收藏的随游网络请求回调接口
     */
    private class CollectionSuiuuRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            hideDialogAndRefreshView();
            bingData2View(stringResponseInfo.result);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "收藏的随游数据请求失败:" + s);

            hideDialogAndRefreshView();
            failureComputePage();

            Toast.makeText(SuiuuApplication.applicationContext, getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }

    }

}
