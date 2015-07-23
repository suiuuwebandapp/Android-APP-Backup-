package com.minglang.suiuu.fragment.attention;

import android.app.ProgressDialog;
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
import com.minglang.suiuu.adapter.AttentionGalleryAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.AttentionGallery;
import com.minglang.suiuu.entity.AttentionGallery.AttentionGalleryData;
import com.minglang.suiuu.entity.AttentionGallery.AttentionGalleryData.AttentionGalleryItemData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 关注的旅图页面
 */
public class AttentionGalleryFragment extends BaseFragment {

    private static final String TAG = AttentionGalleryFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String PAGE = "page";
    private static final String NUMBER = "number";

    private String userSign;
    private String verification;

    @BindString(R.string.load_wait)
    String progressString;

    @BindString(R.string.NoData)
    String noData;

    @BindString(R.string.DataError)
    String errorString;

    @Bind(R.id.attention_galler_gridView)
    PullToRefreshGridView pullToRefreshGridView;

    private int page = 1;

    private List<AttentionGalleryItemData> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    private AttentionGalleryAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttentionThemeFragment.
     */
    public static AttentionGalleryFragment newInstance(String param1, String param2) {
        AttentionGalleryFragment fragment = new AttentionGalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AttentionGalleryFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_attention_loop, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        ViewAction();
        getAttentionData4Service(page);
        return rootView;
    }

    /**
     * 初始化方法
     */
    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(progressString);
        progressDialog.setCanceledOnTouchOutside(false);

        pullToRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        GridView mGridView = pullToRefreshGridView.getRefreshableView();
        mGridView.setNumColumns(2);

        adapter = new AttentionGalleryAdapter(getActivity());
        adapter.setScreenParams(new ScreenUtils(getActivity()).getScreenWidth());
        mGridView.setAdapter(adapter);

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
                getAttentionData4Service(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                String label = DateUtils.formatDateTime(SuiuuApplication.applicationContext, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                getAttentionData4Service(page);
            }

        });

        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    /**
     * 从服务器获取数据
     */
    private void getAttentionData4Service(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter(PAGE, String.valueOf(page));
        params.addBodyParameter(NUMBER, String.valueOf(20));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getAttentionTripPath, new AttentionGalleryRequestCallback());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 隐藏Dialog与刷新View
     */
    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
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
            Toast.makeText(SuiuuApplication.applicationContext, noData, Toast.LENGTH_SHORT).show();
        } else {
            try {
                AttentionGallery attentionGallery = JsonUtils.getInstance().fromJSON(AttentionGallery.class, str);
                if (attentionGallery != null) {
                    AttentionGalleryData galleryData = attentionGallery.getData();
                    if (galleryData != null) {
                        List<AttentionGalleryItemData> list = galleryData.getData();
                        if (list != null && list.size() > 0) {
                            clearDataList();
                            listAll.addAll(list);
                            adapter.setList(listAll);
                        } else {
                            DeBugLog.e(TAG, "；列表为Null");
                            failureLessPage();
                            Toast.makeText(SuiuuApplication.applicationContext, noData, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        DeBugLog.e(TAG, "第二层为Null");
                        failureLessPage();
                        Toast.makeText(SuiuuApplication.applicationContext, noData, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    DeBugLog.e(TAG, "第一层为Null");
                    failureLessPage();
                    Toast.makeText(SuiuuApplication.applicationContext, noData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "数据解析失败:" + e.getMessage());
                failureLessPage();
                Toast.makeText(SuiuuApplication.applicationContext, errorString, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 关注的圈子网络请求回调接口
     */
    private class AttentionGalleryRequestCallback extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            hideDialog();
            bindData2View(stringResponseInfo.result);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "关注的圈子数据请求失败:" + s);

            hideDialog();
            failureLessPage();

            Toast.makeText(getActivity(),
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }

    }

}