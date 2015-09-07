package com.minglang.suiuu.fragment.myorder;

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
import com.minglang.suiuu.activity.GeneralOrderDetailsActivity;
import com.minglang.suiuu.adapter.CompletedAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.CompletedOrder;
import com.minglang.suiuu.entity.CompletedOrder.CompletedOrderData;
import com.minglang.suiuu.entity.TripJsonInfo;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompletedFragment#newInstance} factory method to
 * create an instance of this fragment.
 * <p/>
 * 普通用户->已完成的订单页面
 */
public class CompletedFragment extends BaseFragment {

    private static final String TAG = CompletedFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private static final String PAGE = "page";
    private static final String NUMBER = "number";

    private static final String ORDER_NUMBER = "orderNumber";
    private static final String TITLE_IMG = "titleImg";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    @Bind(R.id.completed_order_list_view)
    PullToRefreshListView pullToRefreshListView;

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

    private boolean isPullToRefresh = true;

    /**
     * 页码
     */
    private int page = 1;

    private String userSign;
    private String verification;

    private ProgressDialog progressDialog;

    private List<CompletedOrderData> listAll = new ArrayList<>();

    private CompletedAdapter adapter;

    private JsonUtils jsonUtils;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompletedFragment.
     */
    public static CompletedFragment newInstance(String param1, String param2, String param3) {
        CompletedFragment fragment = new CompletedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    public CompletedFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_completed, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        viewAction();
        sendRequest();
        DeBugLog.i(TAG, "userSign:" + userSign + ",verification:" + verification);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ListView listView = pullToRefreshListView.getRefreshableView();

        adapter = new CompletedAdapter(getActivity(), listAll, R.layout.item_my_order_layout);
        listView.setAdapter(adapter);

        jsonUtils = JsonUtils.getInstance();

        try {
            token = URLEncoder.encode(token, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void viewAction() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;
                isPullToRefresh = false;
                sendRequest();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                isPullToRefresh = false;
                sendRequest();
            }

        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int location = position - 1;

                String orderNumber = listAll.get(location).getOrderNumber();
                String titleImg = null;
                try {
                    titleImg = jsonUtils.fromJSON(TripJsonInfo.class, listAll.get(location)
                            .getTripJsonInfo()).getInfo().getTitleImg();
                } catch (Exception e) {
                    DeBugLog.e(TAG, "获取图片地址失败:" + e.getMessage());
                }

                Intent intent = new Intent(getActivity(), GeneralOrderDetailsActivity.class);
                intent.putExtra(ORDER_NUMBER, orderNumber);
                intent.putExtra(TITLE_IMG, titleImg);
                startActivity(intent);
            }
        });

    }

    private void sendRequest() {
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        String[] keyArray = new String[]{HttpServicePath.key, PAGE, NUMBER, TOKEN};
        String[] valueArray = new String[]{verification, String.valueOf(page), String.valueOf(15), token};
        String url = addUrlAndParams(HttpNewServicePath.getGeneralUserCompletedOrderPath, keyArray, valueArray);
        DeBugLog.i(TAG, "已完成数据请求URL:" + url);
        try {
            OkHttpManager.onGetAsynRequest(url, new CompletedResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }
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

    /**
     * 请求失败or无数据，请求页数减1
     */
    private void failureLessPage() {
        if (page > 1) {
            page = page - 1;
        }
    }

    /**
     * 如果页码为1，就清空数据
     */
    private void clearListData() {
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
                CompletedOrder completedOrder = jsonUtils.fromJSON(CompletedOrder.class, str);
                if (completedOrder != null) {
                    List<CompletedOrderData> list = completedOrder.getData();
                    if (list != null && list.size() > 0) {
                        clearListData();
                        listAll.addAll(list);
                        adapter.setList(listAll);
                    } else {
                        failureLessPage();
                        Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析失败:" + e.getMessage());
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

    private class CompletedResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "获取到的数据:" + response);
            hideDialog();
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "Exception:" + e.getMessage());
            hideDialog();
            failureLessPage();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

}