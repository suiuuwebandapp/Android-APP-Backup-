package com.minglang.suiuu.fragment.center;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.PersonalSuiuuAdapter;
import com.minglang.suiuu.entity.UserSuiuu;
import com.minglang.suiuu.entity.UserSuiuu.UserSuiuuData;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemClickListener;
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
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalSuiuuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalSuiuuFragment extends Fragment {

    private static final String TAG = PersonalSuiuuFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String PAGE = "page";
    private static final String NUMBER = "number";
    private static final String USERSIGN = "userSign";

    private String userSign;
    private String verification;

    @BindString(R.string.load_wait)
    String dialogMsg;

    @BindString(R.string.NoData)
    String noData;

    @BindString(R.string.DataError)
    String dataError;

    @BindString(R.string.NetworkAnomaly)
    String netWorkError;

    private int page = 1;

    @Bind(R.id.personal_suiuu_recycler_view)
    RecyclerView recyclerView;

    private GridLayoutManager gridLayoutManager;

    private ProgressDialog progressDialog;

    private List<UserSuiuuData> listAll = new ArrayList<>();

    private PersonalSuiuuAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalSuiuuFragment.
     */
    public static PersonalSuiuuFragment newInstance(String param1, String param2) {
        PersonalSuiuuFragment fragment = new PersonalSuiuuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PersonalSuiuuFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_personal_suiuu, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        ViewAction();
        getPersonalSuiuuData(buildRequestParams(page));
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
        progressDialog.setMessage(dialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        adapter = new PersonalSuiuuAdapter();

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void ViewAction() {
        //        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        //            @Override
        //            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        //                super.onScrollStateChanged(recyclerView, newState);
        //            }
        //
        //            @Override
        //            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        //                super.onScrolled(recyclerView, dx, dy);
        //                int lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
        //                int totalItemCount = adapter.getItemCount();
        //                if (lastVisibleItem + 1 >= totalItemCount && dy > 0) {
        //                    page = page + 1;
        //                    getPersonalSuiuuData(buildRequestParams(page));
        //                }
        //            }
        //
        //        });

        adapter.setOnItemClickListener(new RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

    }

    private RequestParams buildRequestParams(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(PAGE, String.valueOf(page));
        params.addBodyParameter(NUMBER, String.valueOf(10));
        params.addBodyParameter(USERSIGN, userSign);
        params.addBodyParameter(HttpServicePath.key, verification);
        return params;
    }

    private void getPersonalSuiuuData(RequestParams params) {
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getPersonalSuiuuDataPath, new PersonalSuiuuRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void failureLessPage() {
        if (page > 1) {
            page = page - 1;
        }
    }

    private void clearDataList() {
        if (page == 1) {
            if (listAll != null && listAll.size() > 0) {
                listAll.clear();
            }
        }
    }

    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            failureLessPage();
            Toast.makeText(getActivity(), noData, Toast.LENGTH_SHORT).show();
        } else {
            try {
                UserSuiuu userSuiuu = JsonUtils.getInstance().fromJSON(UserSuiuu.class, str);
                if (userSuiuu != null) {
                    List<UserSuiuuData> list = userSuiuu.getData();
                    if (list != null && list.size() > 0) {
                        clearDataList();
                        listAll.addAll(list);
                        adapter.setList(listAll);
                        DeBugLog.i(TAG, "当前页码:" + page + ",当前请求数据数量:" + list.size()
                                + ",总数据数量:" + listAll.size());
                    } else {
                        failureLessPage();
                        Toast.makeText(getActivity(), noData, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    failureLessPage();
                    Toast.makeText(getActivity(), noData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "数据绑定Error:" + e.getMessage());
                failureLessPage();
                Toast.makeText(getActivity(), dataError, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class PersonalSuiuuRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String str = responseInfo.result;
            hideDialog();
            bindData2View(str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",Error:" + s);
            hideDialog();
            failureLessPage();
            Toast.makeText(getActivity(), netWorkError, Toast.LENGTH_SHORT).show();
        }

    }

}