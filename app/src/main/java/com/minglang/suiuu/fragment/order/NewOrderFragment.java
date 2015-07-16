package com.minglang.suiuu.fragment.order;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.minglang.suiuu.adapter.OrderListManageAdapter;
import com.minglang.suiuu.entity.OrderManage;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 新订单页面
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link NewOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewOrderFragment extends Fragment {

    private static final String TAG = NewOrderFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    private static PullToRefreshListView pullToRefreshListView;

    private OrderListManageAdapter orderListManageAdapter;

    private ProgressDialog progressDialog;

    private int page = 1;

    private static final int COMPLETE = 1;

    private List<OrderManage.OrderManageData> listAll = new ArrayList<>();

    private static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {

                case COMPLETE:
                    pullToRefreshListView.onRefreshComplete();
                    break;
            }
            return false;
        }
    });

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewOrderFragment.
     */
    public static NewOrderFragment newInstance(String param1, String param2) {
        NewOrderFragment fragment = new NewOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NewOrderFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_new_order, container, false);

        initView(rootView);
        ViewAction();
        getNewOrderData(page);
        return rootView;
    }

    /**
     * 厨初始化方法
     *
     * @param rootView Fragment根View
     */
    private void initView(View rootView) {
        DeBugLog.i(TAG, "userSign:" + userSign + ",verification:" + verification);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.load_wait));

        pullToRefreshListView = (PullToRefreshListView) rootView.findViewById(R.id.new_order_list_view);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        ListView listView = pullToRefreshListView.getRefreshableView();

        orderListManageAdapter = new OrderListManageAdapter(getActivity());
        listView.setAdapter(orderListManageAdapter);
    }

    /**
     * 控件动作
     */
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

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    private void getNewOrderData(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("userSign", userSign);
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter("page", String.valueOf(page));
        params.addBodyParameter("number", String.valueOf(10));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getNewOrderDataPath, new NewOrderRequestCallback());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 隐藏进度框和ListView加载进度View
     */
    private void showOrHideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        handler.sendEmptyMessage(COMPLETE);

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
        if (!TextUtils.isEmpty(str)) {
            try {
                OrderManage orderManage = JsonUtils.getInstance().fromJSON(OrderManage.class, str);
                List<OrderManage.OrderManageData> list = orderManage.getData();
                if (list != null && list.size() > 0) {
                    clearListData();
                    listAll.addAll(list);
                    orderListManageAdapter.setList(listAll);
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "JSON Analytical:" + e.getMessage());
            }
        }
    }

    private class NewOrderRequestCallback extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            showOrHideDialog();

            String str = responseInfo.result;
            bindData2View(str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",Error Info:" + s);
            showOrHideDialog();
        }

    }

}
