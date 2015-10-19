package com.minglang.suiuu.fragment.mysuiuu;

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
import com.minglang.pulltorefreshlibrary.PullToRefreshBase.OnRefreshListener2;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.SuiuuDetailsActivity;
import com.minglang.suiuu.adapter.ParticipateAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.Participate;
import com.minglang.suiuu.entity.Participate.ParticipateData;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.http.OkHttpManager;
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
 * 已参加的随游页面
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link ParticipateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParticipateFragment extends BaseFragment {

    private static final String TAG = ParticipateFragment.class.getSimpleName();

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

    private String userSign;
    private String verification;

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

    private int page = 1;

    private boolean isPullToRefresh = true;

    @Bind(R.id.my_suiuu_participate_list_view)
    PullToRefreshListView pullToRefreshListView;

    private ProgressDialog progressDialog;

    private List<ParticipateData> listAll = new ArrayList<>();

    private ParticipateAdapter participateAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @return A new instance of fragment ParticipateFragment.
     */
    public static ParticipateFragment newInstance(String param1, String param2, String param3) {
        ParticipateFragment fragment = new ParticipateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    public ParticipateFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_participate, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        ViewAction();
        getMyParticipateSuiuuData(page);
        L.i(TAG, "userSign:" + userSign + ",verification:" + verification + ",token:" + token);
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

        participateAdapter = new ParticipateAdapter(getActivity(), listAll, R.layout.item_participate_layout);
        pullToRefreshListView.setAdapter(participateAdapter);

    }

    private void ViewAction() {
        pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = 1;
                isPullToRefresh = false;
                getMyParticipateSuiuuData(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page++;
                isPullToRefresh = false;
                getMyParticipateSuiuuData(page);
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
    private void getMyParticipateSuiuuData(int page) {
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        String[] keyArray = new String[]{USER_SIGN, HttpNewServicePath.key, PAGE, NUMBER, TOKEN};
        String[] valueArray = new String[]{userSign, verification, String.valueOf(page), String.valueOf(10), token};
        String url = addUrlAndParams(HttpNewServicePath.MyParticipateSuiuuPath, keyArray, valueArray);
        L.i(TAG, "请求URL:" + url);
        try {
            OkHttpManager.onGetAsynRequest(url, new MyParticipateResultCallback());
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
        } else {
            try {
                Participate participate = JsonUtils.getInstance().fromJSON(Participate.class, str);
                List<ParticipateData> list = participate.getData();
                if (list != null && list.size() > 0) {
                    clearListData();
                    listAll.addAll(list);
                    participateAdapter.setList(listAll);
                } else {
                    failureLessPage();
                    Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                failureLessPage();
                L.e(TAG, "解析错误:" + e.getMessage());
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
                    Toast.makeText(getActivity(), DataException, Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

    private class MyParticipateResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "已参加的随游数据:" + response);
            hideDialog();
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Exception:" + e.getMessage());
            hideDialog();
            failureLessPage();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

}