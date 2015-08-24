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

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.GeneralOrderDetailsActivity;
import com.minglang.suiuu.adapter.NotFinishedAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.NotFinishedOrder;
import com.minglang.suiuu.entity.TripJsonInfo;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuiuuHttp;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotFinishedFragment#newInstance} factory method to
 * create an instance of this fragment.
 * <p/>
 * 普通用户->未完成的订单页面
 */
public class NotFinishedFragment extends BaseFragment {
    private static final String TAG = NotFinishedFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String PAGE = "page";
    private static final String NUMBER = "number";

    private static final String ORDER_NUMBER = "orderNumber";
    private static final String TITLE_IMG = "titleImg";

    @Bind(R.id.not_finished_order_list_view)
    PullToRefreshListView pullToRefreshListView;

    @BindString(R.string.load_wait)
    String wait;

    @BindString(R.string.NoData)
    String dataNull;

    @BindString(R.string.DataError)
    String errorString;

    private boolean isPullToRefresh = true;

    /**
     * 页码
     */
    private int page = 1;

    private String userSign;
    private String verification;

    private ProgressDialog progressDialog;

    private List<NotFinishedOrder.NotFinishedOrderData> listAll = new ArrayList<>();

    private NotFinishedAdapter adapter;

    private JsonUtils jsonUtils;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotFinishedFragment.
     */
    public static NotFinishedFragment newInstance(String param1, String param2) {
        NotFinishedFragment fragment = new NotFinishedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NotFinishedFragment() {
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
        DeBugLog.i(TAG, "userSign:" + userSign + ",verification:" + verification);
        View rootView = inflater.inflate(R.layout.fragment_not_finished, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        ViewAction();
        getCompletedOrderData4Service(buildRequestParams(page));
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(wait);
        progressDialog.setCanceledOnTouchOutside(false);

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ListView listView = pullToRefreshListView.getRefreshableView();

        adapter = new NotFinishedAdapter(getActivity(), listAll, R.layout.item_my_order_layout);
        listView.setAdapter(adapter);

        jsonUtils = JsonUtils.getInstance();
    }

    private void ViewAction() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;
                isPullToRefresh = false;
                getCompletedOrderData4Service(buildRequestParams(page));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                isPullToRefresh = false;
                getCompletedOrderData4Service(buildRequestParams(page));
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
                    DeBugLog.e(TAG, "获取图片地址失败:");
                }

                Intent intent = new Intent(getActivity(), GeneralOrderDetailsActivity.class);
                intent.putExtra(ORDER_NUMBER, orderNumber);
                intent.putExtra(TITLE_IMG, titleImg);
                startActivity(intent);
            }
        });

    }

    private RequestParams buildRequestParams(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter(PAGE, String.valueOf(page));
        params.addBodyParameter(NUMBER, String.valueOf(15));
        return params;
    }

    private void getCompletedOrderData4Service(RequestParams params) {
        SuiuuHttp httpRequest = new SuiuuHttp(HttpRequest.HttpMethod.POST,
                HttpServicePath.getGeneralUserNotFinishOrderPath, new NotFinishedRequestCallBack());
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
            Toast.makeText(getActivity(), dataNull, Toast.LENGTH_SHORT).show();
        } else {
            try {
                NotFinishedOrder notFinishedOrder = jsonUtils.fromJSON(NotFinishedOrder.class, str);
                if (notFinishedOrder != null) {
                    List<NotFinishedOrder.NotFinishedOrderData> list = notFinishedOrder.getData();
                    if (list != null && list.size() > 0) {
                        clearListData();
                        listAll.addAll(list);
                        adapter.setList(listAll);
                    } else {
                        failureLessPage();
                        Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析失败:" + e.getMessage());
                failureLessPage();
                Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class NotFinishedRequestCallBack extends RequestCallBack<String> {

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
            DeBugLog.i(TAG, "获取到的数据:" + str);
            hideDialog();
            bindData2View(str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",Error Info:" + s);
            hideDialog();
            failureLessPage();
            Toast.makeText(getActivity(), errorString, Toast.LENGTH_SHORT).show();
        }

    }

}