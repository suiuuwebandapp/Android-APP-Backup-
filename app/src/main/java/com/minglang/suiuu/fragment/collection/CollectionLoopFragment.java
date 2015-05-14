package com.minglang.suiuu.fragment.collection;

import android.app.Dialog;
import android.content.Intent;
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
import com.minglang.suiuu.activity.LoopArticleActivity;
import com.minglang.suiuu.adapter.CollectionLoopAdapter;
import com.minglang.suiuu.entity.CollectionLoop;
import com.minglang.suiuu.entity.CollectionLoopData;
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
 * 收藏的圈子
 * <p/>
 * Use the {@link CollectionLoopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionLoopFragment extends Fragment {

    private static final String TAG = CollectionLoopFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userSign;
    private String verification;

    private PtrClassicFrameLayout mPtrFrame;

    private LoadMoreGridViewContainer ptrLoadMore;

    private GridViewWithHeaderAndFooter gridView;

    private int page = 1;

    private List<CollectionLoopData> listAll = new ArrayList<>();

    private Dialog dialog;

    private CollectionLoopAdapter collectionLoopAdapter;

    private boolean clearFlag;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CollectionLoopFragment.
     */
    public static CollectionLoopFragment newInstance(String param1, String param2) {
        CollectionLoopFragment fragment = new CollectionLoopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CollectionLoopFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_collection_loop, container, false);

        initView(rootView);

        ViewAction();

        getData();

        return rootView;
    }

    private void getData() {

        if (dialog != null) {
            dialog.show();
        }

        getCollectionLoop4Service(1);
    }

    /**
     * 从网络获取收藏的圈子数据
     */
    private void getCollectionLoop4Service(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("page", String.valueOf(page));
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.CollectionLoopPath, new CollectionLoopRequestCallBack());
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
                getCollectionLoop4Service(1);
            }
        });

        ptrLoadMore.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                clearFlag = false;
                page = page + 1;
                getCollectionLoop4Service(page);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleId = listAll.get(position).getArticleId();
                Intent intent = new Intent(getActivity(), LoopArticleActivity.class);
                intent.putExtra("articleId", articleId);
                intent.putExtra("TAG", TAG);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment的根View
     */
    private void initView(View rootView) {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.collection_loop_grid_view_frame);
        ptrLoadMore = (LoadMoreGridViewContainer) rootView.findViewById(R.id.collection_loop_load_more_container);

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

        gridView = (GridViewWithHeaderAndFooter) rootView.findViewById(R.id.collection_loop_grid_view);

        ScreenUtils utils = new ScreenUtils(getActivity());
        int screenWidth = utils.getScreenWidth();

        collectionLoopAdapter = new CollectionLoopAdapter(getActivity());
        collectionLoopAdapter.setScreenParams(screenWidth);
        gridView.setAdapter(collectionLoopAdapter);

        Log.i(TAG, "userSign:" + userSign);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /**
     * 获取收藏的圈子网络请求回调接口
     */
    private class CollectionLoopRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            mPtrFrame.refreshComplete();
            ptrLoadMore.loadMoreFinish(true, true);

            if (clearFlag) {
                if (listAll != null && listAll.size() > 0) {
                    listAll.clear();
                }
            }

            String str = stringResponseInfo.result;
            Log.i(TAG, "收藏的圈子数据:" + str);
            try {
                CollectionLoop collectionLoop = JsonUtil.getInstance().fromJSON(CollectionLoop.class, str);
                if (collectionLoop.getStatus().equals("1")) {
                    List<CollectionLoopData> list = collectionLoop.getData().getData();
                    if (list != null && list.size() > 0) {
                        listAll.addAll(list);
                        collectionLoopAdapter.setListData(listAll);
                    } else {
                        Toast.makeText(getActivity(), "圈子收藏" +
                                getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "收藏的圈子的数据解析异常:" + e.getMessage());
                Toast.makeText(getActivity(),
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {

            if (page > 1) {
                page = page - 1;
            }

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            mPtrFrame.refreshComplete();
            ptrLoadMore.loadMoreError(0, "加载失败，请重试");

            Log.e(TAG, "收藏的圈子数据请求失败:" + s);
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }

}
