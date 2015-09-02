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
import com.minglang.suiuu.activity.CommunityDetailsActivity;
import com.minglang.suiuu.adapter.PersonalProblemAdapter;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.entity.UserProblem;
import com.minglang.suiuu.entity.UserProblem.UserProblemData.UserProblemItemData;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemClickListener;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonalProblemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalProblemFragment extends BaseFragment {

    private static final String TAG = PersonalProblemFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String PAGE = "page";
    private static final String NUMBER = "number";
    private static final String USERSIGN = "userSign";

    private static final String ID = "id";

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

    @Bind(R.id.personal_problem_recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;

    private ProgressDialog progressDialog;

    private List<UserProblemItemData> listAll = new ArrayList<>();

    private PersonalProblemAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalProblemFragment.
     */
    public static PersonalProblemFragment newInstance(String param1, String param2) {
        PersonalProblemFragment fragment = new PersonalProblemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DeBugLog.i(TAG, "userSign:" + userSign + ",verification:" + verification);
        View rootView = inflater.inflate(R.layout.fragment_personal_problem, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        viewAction();
        getPersonalSuiuuData();
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

        linearLayoutManager = new LinearLayoutManager(getActivity());

        adapter = new PersonalProblemAdapter();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

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
        //                    DeBugLog.i(TAG, "准备加载数据");
        //                    page = page + 1;
        //                    getPersonalSuiuuData(buildRequestParams(page));
        //                }
        //            }
        //
        //        });

        adapter.setOnItemClickListener(new RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = listAll.get(position).getQId();
                Intent intent = new Intent(getActivity(), CommunityDetailsActivity.class);
                intent.putExtra(ID, id);
                startActivity(intent);
            }
        });

    }

    private void getPersonalSuiuuData() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        String[] keyArray = new String[]{PAGE, NUMBER, USERSIGN, HttpServicePath.key, TOKEN};
        String[] valueArray = new String[]{String.valueOf(page), String.valueOf(10),
                userSign, verification, token};
        String url = addUrlAndParams(HttpNewServicePath.getPersonalProblemDataPath, keyArray, valueArray);

        try {
            OkHttpManager.onGetAsynRequest(url, new PersonalProblemResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            failureLessPage();
            Toast.makeText(getActivity(), netWorkError, Toast.LENGTH_SHORT).show();
        }
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
                UserProblem userProblem = JsonUtils.getInstance().fromJSON(UserProblem.class, str);
                if (userProblem != null) {
                    List<UserProblemItemData> list = userProblem.getData().getData();
                    if (list != null && list.size() > 0) {
                        clearDataList();
                        listAll.addAll(list);
                        adapter.setList(listAll);
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

    private class PersonalProblemResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "Problem:" + response);
            hideDialog();
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "Exception:" + e.getMessage());
            hideDialog();
            failureLessPage();
            Toast.makeText(getActivity(), netWorkError, Toast.LENGTH_SHORT).show();
        }

    }

}