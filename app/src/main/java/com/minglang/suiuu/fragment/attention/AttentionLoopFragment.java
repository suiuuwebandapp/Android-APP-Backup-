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
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.LoopDetailsActivity;
import com.minglang.suiuu.adapter.AttentionLoopAdapter;
import com.minglang.suiuu.entity.AttentionLoop;
import com.minglang.suiuu.entity.AttentionLoopData;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreGridViewContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 关注主题
 */
public class AttentionLoopFragment extends Fragment {

    private static final String TAG = AttentionLoopFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    private PtrClassicFrameLayout mPtrFrame;
    private LoadMoreGridViewContainer ptrLoadMore;
    private GridViewWithHeaderAndFooter attentionThemeGridView;

    private int page = 1;

    private List<AttentionLoopData> listAll = new ArrayList<>();

    private ProgressDialog progressDialog;

    private boolean clearFlag;

    private AttentionLoopAdapter attentionLoopAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttentionThemeFragment.
     */
    public static AttentionLoopFragment newInstance(String param1, String param2) {
        AttentionLoopFragment fragment = new AttentionLoopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AttentionLoopFragment() {
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

        View rootView = inflater.inflate(R.layout.fragment_attention_loop, container, false);
        initView(rootView);
        getData();
        ViewAction();

        return rootView;
    }

    private void ViewAction() {

        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, attentionThemeGridView, header);
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

        attentionThemeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cID = listAll.get(position).getcId();
                String type = listAll.get(position).getcType();
                String loopName = listAll.get(position).getcName();

                Intent intent = new Intent(getActivity(), LoopDetailsActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("circleId", cID);
                intent.putExtra("name", loopName);
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
     * 从服务器获取数据
     */
    private void getAttentionData4Service(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter("page", String.valueOf(page));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.AttentionLoopPath, new AttentionLoopRequestCallback());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment根View
     */
    private void initView(View rootView) {
        ScreenUtils utils = new ScreenUtils(getActivity());
        int screenWidth = utils.getScreenWidth();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.load_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.attention_loop_grid_view_frame);
        ptrLoadMore = (LoadMoreGridViewContainer) rootView.findViewById(R.id.attention_loop_load_more_container);
        attentionThemeGridView = (GridViewWithHeaderAndFooter) rootView.findViewById(R.id.attentionThemeGridView);

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

        attentionLoopAdapter = new AttentionLoopAdapter(getActivity());
        attentionLoopAdapter.setScreenParams(screenWidth);
        attentionThemeGridView.setAdapter(attentionLoopAdapter);

        Log.i(TAG, "userSign:" + userSign);
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
     * 关注的圈子网络请求回调接口
     */
    private class AttentionLoopRequestCallback extends RequestCallBack<String> {

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
            Log.i(TAG, "关注的圈子的数据:" + str);
            try {
                AttentionLoop attentionLoop = JsonUtil.getInstance().fromJSON(AttentionLoop.class, str);
                if (attentionLoop.getStatus().equals("1")) {
                    List<AttentionLoopData> list = attentionLoop.getData().getData();
                    if (list != null && list.size() > 0) {
                        listAll.addAll(list);
                        attentionLoopAdapter.setList(listAll);
                    } else {
                        Toast.makeText(getActivity(), "关注圈子" +
                                getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "关注的圈子数据解析异常:" + e.getMessage());
                Toast.makeText(getActivity(),
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, "关注的圈子数据请求失败:" + s);

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