package com.minglang.suiuu.fragment.mysuiuu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.MySuiuuReleaseActivity;
import com.minglang.suiuu.adapter.PublishedAdapter;
import com.minglang.suiuu.entity.Published;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 已发布的随游页面
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link PublishedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublishedFragment extends Fragment {

    private static final String TAG = PublishedFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    private int page = 1;

    @Bind(R.id.my_suiuu_published_list_view)
    PullToRefreshListView pullToRefreshListView;

    private ProgressDialog progressDialog;

    private List<Published.PublishedData> listAll = new ArrayList<>();

    private PublishedAdapter publishedAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublishedFragment.
     */
    public static PublishedFragment newInstance(String param1, String param2) {
        PublishedFragment fragment = new PublishedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PublishedFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_published, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        ViewAction();
        getMyPublishedSuiuuData(page);
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

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.load_wait));

        publishedAdapter = new PublishedAdapter(getActivity());
        publishedAdapter.setScreenHeight(new ScreenUtils(getActivity()).getScreenHeight());
        pullToRefreshListView.setAdapter(publishedAdapter);

    }

    private void ViewAction() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;
                getMyPublishedSuiuuData(page);
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
                Intent intent = new Intent(getActivity(), MySuiuuReleaseActivity.class);
                intent.putExtra("title", listAll.get(position).getTitle());
                intent.putExtra("info", listAll.get(position).getInfo());
                intent.putExtra("price", listAll.get(position).getBasePrice());
                intent.putExtra("tripId", listAll.get(position).getTripId());
                startActivity(intent);
            }
        });

    }

    /**
     * 网络请求
     *
     * @param page 页码
     */
    private void getMyPublishedSuiuuData(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("userSign", userSign);
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter("page", String.valueOf(page));
        params.addBodyParameter("number", String.valueOf(10));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpMethod.POST,
                HttpServicePath.MyPublishedSuiuuPath, new MyPublishedRequestCallBack());
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

        pullToRefreshListView.onRefreshComplete();
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
                Published published = JsonUtils.getInstance().fromJSON(Published.class, str);
                List<Published.PublishedData> list = published.getData();
                if (list != null && list.size() > 0) {
                    clearListData();
                    listAll.addAll(list);
                    publishedAdapter.setList(listAll);
                }

            } catch (Exception e) {
                Log.e(TAG, "Published Load Error:" + e.getMessage());
            }
        }
    }

    private class MyPublishedRequestCallBack extends RequestCallBack<String> {

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
            DeBugLog.e(TAG, "我发布的随游请求失败(1):" + e.getMessage());
            DeBugLog.e(TAG, "我发布的随游请求失败(2):" + e.getExceptionCode());
            DeBugLog.e(TAG, "我发布的随游请求失败(3):" + s);

            showOrHideDialog();
        }

    }

}