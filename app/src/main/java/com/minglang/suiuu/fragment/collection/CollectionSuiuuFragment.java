package com.minglang.suiuu.fragment.collection;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.CollectionSuiuuAdapter;
import com.minglang.suiuu.entity.CollectionSuiuu;
import com.minglang.suiuu.entity.CollectionSuiuuData;
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
 * 收藏的路线
 * <p/>
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectionSuiuuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionSuiuuFragment extends Fragment {

    private static final String TAG = CollectionSuiuuFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;


    private PtrClassicFrameLayout mPtrFrame;

    private LoadMoreGridViewContainer ptrLoadMore;

    private GridViewWithHeaderAndFooter gridView;

    private int page = 1;

    CollectionSuiuuAdapter adapter;

    private List<CollectionSuiuuData> listAll = new ArrayList<>();

    private Dialog dialog;

    private boolean clearFlag;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CollectionRouteFragment.
     */
    public static CollectionSuiuuFragment newInstance(String param1, String param2) {
        CollectionSuiuuFragment fragment = new CollectionSuiuuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CollectionSuiuuFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_collection_suiuu, container, false);

        initView(rootView);

        ViewAction();

        getData();

        return rootView;
    }

    private void getData() {

        if (dialog != null) {
            dialog.show();
        }

        getCollectionSuiuu4Service(1);
    }

    /**
     * 网络请求
     */
    private void getCollectionSuiuu4Service(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter("page", String.valueOf(page));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.GetCollectionSuiuuPath, new CollectionSuiuuRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, gridView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                clearFlag = true;
                getCollectionSuiuu4Service(1);
            }
        });

        ptrLoadMore.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                clearFlag = false;
                page = page + 1;
                getCollectionSuiuu4Service(page);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment根View
     */
    @SuppressWarnings("deprecation")
    private void initView(View rootView) {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.collection_suiuu_grid_view_frame);
        ptrLoadMore = (LoadMoreGridViewContainer) rootView.findViewById(R.id.collection_suiuu_load_more_container);
        gridView = (GridViewWithHeaderAndFooter) rootView.findViewById(R.id.collection_suiuu_grid_view);

        ptrLoadMore.useDefaultHeader();

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

        ScreenUtils utils = new ScreenUtils(getActivity());
        int screenWidth = utils.getScreenWidth();
        int screenHeight = utils.getScreenHeight();

        adapter = new CollectionSuiuuAdapter(getActivity());
        adapter.setScreenParams(screenWidth, screenHeight);
        gridView.setAdapter(adapter);
    }

    /**
     * 获取收藏的随游网络请求回调接口
     */
    private class CollectionSuiuuRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            mPtrFrame.refreshComplete();
            ptrLoadMore.loadMoreFinish(true, true);

            if (clearFlag) {
                listAll.clear();
            }

            String str = stringResponseInfo.result;
            try {
                CollectionSuiuu suiuu = JsonUtil.getInstance().fromJSON(CollectionSuiuu.class, str);
                List<CollectionSuiuuData> list = suiuu.getData().getData();
                listAll.addAll(list);
                adapter.setListData(listAll);

            } catch (Exception e) {
                Log.e(TAG, "收藏的随游数据解析失败" + e.getMessage());
                Toast.makeText(getActivity(), getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(HttpException e, String s) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            mPtrFrame.refreshComplete();
            ptrLoadMore.loadMoreFinish(true, true);

            if (page > 1) {
                page = page - 1;
            }

            Log.e(TAG, "收藏的随游数据请求失败:" + s);
            Toast.makeText(getActivity(), getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }

}
