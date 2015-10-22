package com.minglang.suiuu.fragment.mysuiuu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshBase.OnRefreshListener2;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SettingActivity;
import com.minglang.suiuu.activity.SuiuuDetailsActivity;
import com.minglang.suiuu.adapter.PublishedAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.Published;
import com.minglang.suiuu.entity.Published.PublishedData;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 已发布的随游页面
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link PublishedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublishedFragment extends BaseFragment {

    private static final String TAG = PublishedFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private static final String USER_SIGN = "userSign";
    private static final String PAGE = "page";
    private static final String NUMBER = "number";

    private static final String TRIP_ID = "tripId";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private static final String CLASS_NAME = "className";

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.DataException)
    String DataException;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.LoginInvalid)
    String LoginInvalid;

    private int page = 1;

    private boolean isPullToRefresh = true;

    @Bind(R.id.my_suiuu_published_list_view)
    PullToRefreshListView pullToRefreshListView;

    private ProgressDialog progressDialog;

    private List<PublishedData> listAll = new ArrayList<>();

    private PublishedAdapter publishedAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @return A new instance of fragment PublishedFragment.
     */
    public static PublishedFragment newInstance(String param1, String param2, String param3) {
        PublishedFragment fragment = new PublishedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
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
            token = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_published, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        viewAction();
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
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(DialogMsg);

        publishedAdapter = new PublishedAdapter(getActivity(), listAll, R.layout.item_published_layout);
        pullToRefreshListView.setAdapter(publishedAdapter);
    }

    private void viewAction() {
        pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;
                isPullToRefresh = false;
                getMyPublishedSuiuuData(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page++;
                isPullToRefresh = false;
                getMyPublishedSuiuuData(page);
            }

        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int location = position - 1;
                Intent intent = new Intent(getActivity(), SuiuuDetailsActivity.class);
                intent.putExtra(TRIP_ID, listAll.get(location).getTripId());
                intent.putExtra(CLASS_NAME, TAG);
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
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        String[] keyArray = new String[]{USER_SIGN, HttpNewServicePath.key, PAGE, NUMBER, TOKEN};
        String[] valueArray = new String[]{userSign, verification, String.valueOf(page), String.valueOf(10), token};
        String url = addUrlAndParams(HttpNewServicePath.getMyPublishedSuiuuPath, keyArray, valueArray);
        L.i(TAG, "请求URL:" + url);
        try {
            OkHttpManager.onGetAsynRequest(url, new MyPublishedResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            failureLessPage();
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
     * 请求失败,页码减1
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
        } else try {
            JSONObject object = new JSONObject(str);
            String status = object.getString(STATUS);
            switch (status) {
                case "1":
                    Published published = JsonUtils.getInstance().fromJSON(Published.class, str);
                    List<PublishedData> list = published.getData();
                    if (list != null && list.size() > 0) {
                        clearListData();
                        listAll.addAll(list);
                    } else {
                        failureLessPage();
                        Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                    }
                    publishedAdapter.setList(listAll);
                    break;

                case "-1":
                    Toast.makeText(getActivity(), SystemException, Toast.LENGTH_SHORT).show();
                    break;

                case "-2":
                    Toast.makeText(getActivity(), object.getString(DATA), Toast.LENGTH_SHORT).show();
                    break;

                case "-3":
                    Toast.makeText(getActivity(), LoginInvalid, Toast.LENGTH_SHORT).show();
                    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
                    localBroadcastManager.sendBroadcast(new Intent(SettingActivity.class.getSimpleName()));
                    ReturnLoginActivity(getActivity());
                    break;

                case "-4":
                    Toast.makeText(getActivity(), object.getString(DATA), Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (Exception e) {
            failureLessPage();
            L.e(TAG, "解析错误:" + e.getMessage());
            Toast.makeText(getActivity(), DataException, Toast.LENGTH_SHORT).show();
        }
    }

    private class MyPublishedResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "已发布的随游数据:" + response);
            hideDialog();
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Exception:" + e.getMessage());
            failureLessPage();
            hideDialog();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

}