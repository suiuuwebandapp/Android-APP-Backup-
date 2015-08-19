package com.minglang.suiuu.fragment.order;

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

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.OrderDetailsActivity;
import com.minglang.suiuu.adapter.OrderListManageAdapter;
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.OrderManage;
import com.minglang.suiuu.entity.OrderManage.OrderManageData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 已接单页面
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmOrderFragment extends BaseFragment {

    private static final String TAG = ConfirmOrderFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String USER_SIGN = "userSign";
    private static final String PAGE = "page";
    private static final String NUMBER = "number";

    private static final String ID = "id";
    private static final String ORDER_STATUS = "orderStatus";

    private static final String CONFIRM = "Confirm";

    private String userSign;
    private String verification;

    @BindString(R.string.load_wait)
    String wait;

    @BindString(R.string.NoData)
    String noData;

    @BindString(R.string.DataError)
    String errorString;

    @Bind(R.id.confirm_order_list_view)
    PullToRefreshListView pullToRefreshListView;

    /**
     * 数据适配器
     */
    private OrderListManageAdapter orderListManageAdapter;

    /**
     * 进度框
     */
    private ProgressDialog progressDialog;

    /**
     * 页码
     */
    private int page = 1;

    private boolean isPullToRefresh = true;

    /**
     * 数据源
     */
    private List<OrderManageData> listAll = new ArrayList<>();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfirmOrderFragment.
     */
    public static ConfirmOrderFragment newInstance(String param1, String param2) {
        ConfirmOrderFragment fragment = new ConfirmOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ConfirmOrderFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_confirm_order, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        viewAction();
        getConfirmOrderData(page);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 初始化方法
     */
    private void initView() {
        DeBugLog.i(TAG, "userSign:" + userSign + ",verification:" + verification);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(wait);
        progressDialog.setCanceledOnTouchOutside(false);

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        orderListManageAdapter = new OrderListManageAdapter(getActivity());
        pullToRefreshListView.setAdapter(orderListManageAdapter);
    }

    /**
     * 控件动作
     */
    private void viewAction() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;
                isPullToRefresh = false;
                getConfirmOrderData(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                isPullToRefresh = false;
                getConfirmOrderData(page);
            }

        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int location = position - 1;
                String strID = listAll.get(location).getOrderNumber();
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                intent.putExtra(ID, strID);
                intent.putExtra(ORDER_STATUS, CONFIRM);
                startActivity(intent);
            }
        });

    }

    private void getConfirmOrderData(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(USER_SIGN, userSign);
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter(PAGE, String.valueOf(page));
        params.addBodyParameter(NUMBER, String.valueOf(15));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getConfirmOrderDataPath, new ConfirmOrderManageRequestCallBack());
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
            Toast.makeText(SuiuuApplication.applicationContext, noData, Toast.LENGTH_SHORT).show();
        } else {
            try {
                OrderManage orderManage = JsonUtils.getInstance().fromJSON(OrderManage.class, str);
                if (orderManage != null) {
                    List<OrderManageData> list = orderManage.getData();
                    if (list != null && list.size() > 0) {
                        clearListData();
                        listAll.addAll(list);
                        orderListManageAdapter.setList(listAll);
                    } else {
                        failureLessPage();
                        Toast.makeText(SuiuuApplication.applicationContext, noData, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    failureLessPage();
                    Toast.makeText(SuiuuApplication.applicationContext, noData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析失败:" + e.getMessage());
                failureLessPage();
                Toast.makeText(SuiuuApplication.applicationContext, errorString, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class ConfirmOrderManageRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (isPullToRefresh)
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String str = responseInfo.result;
            DeBugLog.i(TAG, "已接单数据:" + str);
            hideDialog();
            bindData2View(str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",Error Info:" + s);
            hideDialog();
            failureLessPage();
            Toast.makeText(SuiuuApplication.applicationContext, errorString, Toast.LENGTH_SHORT).show();
        }

    }

}