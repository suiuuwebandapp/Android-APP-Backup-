package com.minglang.suiuu.fragment.center;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.ProblemDetailsActivity;
import com.minglang.suiuu.adapter.PersonalProblemAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.UserProblem;
import com.minglang.suiuu.entity.UserProblem.UserProblemData.UserProblemItemData;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemClickListener;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
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
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalProblemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalProblemFragment extends BaseFragment {

    private static final String TAG = PersonalProblemFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private static final String PAGE = "page";
    private static final String NUMBER = "number";
    private static final String USERSIGN = "userSign";

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

    private int page = 1;

    @Bind(R.id.personal_problem_head_frame)
    PtrClassicFrameLayout frameLayout;

    @Bind(R.id.personal_problem_recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;

    private ProgressDialog progressDialog;

    private List<UserProblemItemData> listAll = new ArrayList<>();

    private PersonalProblemAdapter adapter;

    private boolean isPullToRefresh = true;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @return A new instance of fragment PersonalProblemFragment.
     */
    public static PersonalProblemFragment newInstance(String param1, String param2, String param3) {
        PersonalProblemFragment fragment = new PersonalProblemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    public PersonalProblemFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_personal_problem, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        viewAction();
        getPersonalSuiuuData();
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
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        int paddingParams = AppUtils.newInstance().dip2px(15, getActivity());

        MaterialHeader header = new MaterialHeader(getActivity());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, paddingParams, 0, paddingParams);
        header.setPtrFrameLayout(frameLayout);

        frameLayout.setHeaderView(header);
        frameLayout.addPtrUIHandler(header);
        frameLayout.setPinContent(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());

        adapter = new PersonalProblemAdapter();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 控件动作
     */
    private void viewAction() {
        //        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        //            @Override
        //            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        //                super.onScrollStateChanged(recyclerView, newState);
        //            }
        //
        //            @Override
        //            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        //                super.onScrolled(recyclerView, dx, dy);
        //                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        //                int totalItemCount = adapter.getItemCount();
        //                if (lastVisibleItem >= totalItemCount && dy > 0) {
        //                    page = page + 1;
        //                    getPersonalSuiuuData(buildRequestParams(page));
        //                }
        //            }
        //
        //        });

        frameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, recyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                isPullToRefresh = false;
                getPersonalSuiuuData();
            }
        });

        adapter.setOnItemClickListener(new RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = listAll.get(position).getQId();
                Intent intent = new Intent(getActivity(), ProblemDetailsActivity.class);
                intent.putExtra(ID, id);
                startActivity(intent);
            }
        });

    }

    /**
     * 网络请求
     */
    private void getPersonalSuiuuData() {
        if (isPullToRefresh) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        String[] keyArray = new String[]{PAGE, NUMBER, USERSIGN, HttpNewServicePath.key, TOKEN};
        String[] valueArray = new String[]{String.valueOf(page), String.valueOf(10), userSign, verification, token};
        String url = addUrlAndParams(HttpNewServicePath.getPersonalProblemDataPath, keyArray, valueArray);

        try {
            OkHttpManager.onGetAsynRequest(url, new PersonalProblemResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            failureLessPage();
            Toast.makeText(getActivity(), NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 隐藏进度框
     */
    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        frameLayout.refreshComplete();
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
        L.i(TAG, "关注问题返回结果:" + str);
        if (TextUtils.isEmpty(str)) {
            failureLessPage();
            Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
        } else try {
            UserProblem userProblem = JsonUtils.getInstance().fromJSON(UserProblem.class, str);
            if (userProblem != null) {
                List<UserProblemItemData> list = userProblem.getData().getData();
                if (list != null && list.size() > 0) {
                    clearDataList();
                    listAll.addAll(list);
                    adapter.setList(listAll);
                } else {
                    failureLessPage();
                    Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
                }
            } else {
                failureLessPage();
                Toast.makeText(getActivity(), NoData, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            L.e(TAG, "数据绑定Error:" + e.getMessage());
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

    private class PersonalProblemResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "Problem:" + response);
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