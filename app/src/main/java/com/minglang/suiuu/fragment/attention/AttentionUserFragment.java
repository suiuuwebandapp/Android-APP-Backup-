package com.minglang.suiuu.fragment.attention;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.OtherUserActivity;
import com.minglang.suiuu.adapter.AttentionUserAdapter;
import com.minglang.suiuu.entity.AttentionUser;
import com.minglang.suiuu.entity.AttentionUserData;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 关注用户
 */
public class AttentionUserFragment extends Fragment {

    private static final String TAG = AttentionUserFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    private PtrClassicFrameLayout mPtrFrame;
    private LoadMoreListViewContainer ptrLoadMore;
    private ListView listView;

    private int page = 1;

    private List<AttentionUserData> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    private boolean clearFlag;

    private AttentionUserAdapter attentionUserAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttentionUserFragment.
     */
    public static AttentionUserFragment newInstance(String param1, String param2) {
        AttentionUserFragment fragment = new AttentionUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AttentionUserFragment() {

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

        View rootView = inflater.inflate(R.layout.fragment_attention_user, container, false);
        initView(rootView);
        getData();
        ViewAction();

        return rootView;
    }

    private void ViewAction() {

        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, listView, view2);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                clearFlag = true;
                getAttentionData4Service(1);
            }
        });

        ptrLoadMore.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                clearFlag = false;
                page = page + 1;
                getAttentionData4Service(page);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String otherUserSign = listAll.get(position).getUserSign();
                Intent intent = new Intent(getActivity(), OtherUserActivity.class);
                intent.putExtra("userSign", otherUserSign);
                startActivity(intent);
            }
        });

    }

    private void getData() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        getAttentionData4Service(page);
    }

    /**
     * 从服务获取关注用户的数据
     */
    private void getAttentionData4Service(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("page", String.valueOf(page));
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.AttentionUserPath, new AttentionUserRequestCallback());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
        Log.i(TAG, "verification:" + verification);
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment根View
     */
    private void initView(View rootView) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.load_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.attention_user_grid_view_frame);
        ptrLoadMore = (LoadMoreListViewContainer) rootView.findViewById(R.id.attention_user_load_more_container);
        listView = (ListView) rootView.findViewById(R.id.attention_user_ListView);

        MaterialHeader header = new MaterialHeader(getActivity());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrame);

        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setPinContent(true);

        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);

        attentionUserAdapter = new AttentionUserAdapter(getActivity());
        listView.setAdapter(attentionUserAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /**
     * 网络请求回调接口
     */
    private class AttentionUserRequestCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            mPtrFrame.refreshComplete();
            ptrLoadMore.loadMoreFinish(true, true);

            if (clearFlag) {
                if (listAll != null && listAll.size() > 0) {
                    listAll.clear();
                }
            }

            String str = stringResponseInfo.result;
            Log.i(TAG, "关注用户的数据:" + str);
            try {
                AttentionUser attentionUser = JsonUtil.getInstance().fromJSON(AttentionUser.class, str);
                if (attentionUser.getStatus().equals("1")) {
                    List<AttentionUserData> list = attentionUser.getData().getData();
                    if (list != null && list.size() > 0) {
                        listAll.addAll(list);
                        attentionUserAdapter.setList(listAll);
                    } else {
                        Toast.makeText(getActivity(), "关注用户" +
                                getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "关注的用户的数据解析失败:" + e.getMessage());
                Toast.makeText(getActivity(),
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, "关注的用户数据请求失败:" + s);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            mPtrFrame.refreshComplete();
            ptrLoadMore.loadMoreError(0, "加载失败，请重试");

            Toast.makeText(getActivity(),
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }

}
