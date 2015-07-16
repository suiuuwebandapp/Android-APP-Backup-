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
import com.minglang.suiuu.activity.LoopArticleActivity;
import com.minglang.suiuu.adapter.CollectionLoopAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.entity.CollectionLoop;
import com.minglang.suiuu.entity.CollectionLoopData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏的圈子
 * <p/>
 * Use the {@link CollectionLoopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionLoopFragment extends Fragment {

    private static final String TAG = CollectionLoopFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    private PullToRefreshGridView pullToRefreshGridView;

    private int page = 1;

    private List<CollectionLoopData> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    private CollectionLoopAdapter collectionLoopAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CollectionLoopFragment.
     */
    public static CollectionLoopFragment newInstance(String param1, String param2) {
        CollectionLoopFragment fragment = new CollectionLoopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CollectionLoopFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_collection_loop, container, false);

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
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_bar);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        pullToRefreshGridView = (PullToRefreshGridView) rootView.findViewById(R.id.collection_loop_grid_view);
        pullToRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        GridView gridView = pullToRefreshGridView.getRefreshableView();
        gridView.setNumColumns(2);

        ScreenUtils utils = new ScreenUtils(getActivity());
        int screenWidth = utils.getScreenWidth();

        collectionLoopAdapter = new CollectionLoopAdapter(getActivity());
        collectionLoopAdapter.setScreenParams(screenWidth);
        gridView.setAdapter(collectionLoopAdapter);

        DeBugLog.i(TAG, "userSign:" + userSign);
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
                getCollectionLoop4Service(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                String label = DateUtils.formatDateTime(SuiuuApplication.applicationContext, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                getCollectionLoop4Service(page);
            }
        });

        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleId = listAll.get(position).getArticleId();
                Intent intent = new Intent(getActivity(), LoopArticleActivity.class);
                intent.putExtra("articleId", articleId);
                intent.putExtra("TAG", TAG);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        getCollectionLoop4Service(1);
    }

    /**
     * 从网络获取收藏的圈子数据
     */
    private void getCollectionLoop4Service(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("page", String.valueOf(page));
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.CollectionLoopPath, new CollectionLoopRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 将数据绑定到View
     *
     * @param str 网络请求返回的Json字符串
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(SuiuuApplication.applicationContext, "圈子收藏" +
                    getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
        } else {
            try {
                CollectionLoop collectionLoop = JsonUtils.getInstance().fromJSON(CollectionLoop.class, str);
                if (collectionLoop.getStatus().equals("1")) {
                    List<CollectionLoopData> list = collectionLoop.getData().getData();
                    if (list != null && list.size() > 0) {
                        clearDataList();
                        listAll.addAll(list);
                        collectionLoopAdapter.setListData(listAll);
                    } else {
                        failureComputePage();
                        Toast.makeText(SuiuuApplication.applicationContext, "圈子收藏" +
                                getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    failureComputePage();
                    Toast.makeText(SuiuuApplication.applicationContext,
                            getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                failureComputePage();
                DeBugLog.e(TAG, "收藏的圈子的数据解析异常:" + e.getMessage());
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
     * 获取收藏的圈子网络请求回调接口
     */
    private class CollectionLoopRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            hideDialogAndRefreshView();
            bindData2View(stringResponseInfo.result);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "收藏的圈子数据请求失败:" + s);

            failureComputePage();
            hideDialogAndRefreshView();

            Toast.makeText(SuiuuApplication.applicationContext,
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }

    }

}
