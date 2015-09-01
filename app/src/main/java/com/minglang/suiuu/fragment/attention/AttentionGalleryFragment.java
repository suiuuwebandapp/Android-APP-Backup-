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

import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshGridView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.TripGalleryDetailsActivity;
import com.minglang.suiuu.adapter.AttentionGalleryAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.AttentionGallery;
import com.minglang.suiuu.entity.AttentionGallery.AttentionGalleryData;
import com.minglang.suiuu.entity.AttentionGallery.AttentionGalleryData.AttentionGalleryItemData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.ScreenUtils;
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
 * 关注的旅图页面
 */
public class AttentionGalleryFragment extends BaseFragment {

    private static final String TAG = AttentionGalleryFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private static final String PAGE = "page";
    private static final String NUMBER = "number";

    private static final String ID = "id";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private String userSign;
    private String verification;

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

    @Bind(R.id.attention_galler_gridView)
    PullToRefreshGridView pullToRefreshGridView;

    private int page = 1;

    private boolean isPullToRefresh = true;

    private List<AttentionGalleryItemData> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    private AttentionGalleryAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @return A new instance of fragment AttentionThemeFragment.
     */
    public static AttentionGalleryFragment newInstance(String param1, String param2, String param3) {
        AttentionGalleryFragment fragment = new AttentionGalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
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
            token = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_attention_loop, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        viewAction();
        getData4Service(page);
        DeBugLog.i(TAG, "userSign:" + userSign + ",verification:" + verification + ",token:" + token);
        return rootView;
    }

    /**
     * 初始化方法
     */
    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        pullToRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        GridView mGridView = pullToRefreshGridView.getRefreshableView();
        mGridView.setNumColumns(2);

        adapter = new AttentionGalleryAdapter(getActivity());
        adapter.setScreenParams(new ScreenUtils(getActivity()).getScreenWidth());
        mGridView.setAdapter(adapter);
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
                isPullToRefresh = false;
                getData4Service(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                isPullToRefresh = false;
                getData4Service(page);
            }

        });

        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strId = listAll.get(position).getId();
                Intent intent = new Intent(getActivity(), TripGalleryDetailsActivity.class);
                intent.putExtra(ID, strId);
                startActivity(intent);
            }
        });
    }

    /**
     * 从服务器获取数据
     */
    private void getData4Service(int page) {
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        String[] keyArray = new String[]{HttpNewServicePath.key, PAGE, NUMBER, TOKEN};
        String[] valueArray = new String[]{verification, String.valueOf(page), String.valueOf(20), token};
        String url = addUrlAndParams(HttpNewServicePath.getAttentionTripPath, keyArray, valueArray);

        try {
            OkHttpManager.onGetAsynRequest(url, new AttentionGalleryResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            failureLessPage();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }
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
            Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
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
                            DeBugLog.e(TAG, "列表为Null");
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
                DeBugLog.e(TAG, "数据解析失败:" + e.getMessage());
                failureLessPage();
                try {
                    JSONObject object = new JSONObject(str);
                    String status = object.getString(STATUS);
                    if (status.equals("-1")) {
                        Toast.makeText(getActivity(), SystemException, Toast.LENGTH_SHORT).show();
                    } else if (status.equals("-2")) {
                        Toast.makeText(getActivity(), object.getString(DATA), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    Toast.makeText(getActivity(), DataError, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 关注的圈子网络请求回调接口
     */
    private class AttentionGalleryResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "关注的圈子数据请求失败:" + e.getMessage());
            hideDialog();
            failureLessPage();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "关注的旅图数据:" + response);
            hideDialog();
            bindData2View(response);
        }

    }

}