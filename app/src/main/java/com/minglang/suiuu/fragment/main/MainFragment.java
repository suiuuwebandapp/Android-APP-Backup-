package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.AllAttentionDynamicActivity;
import com.minglang.suiuu.activity.LoopArticleActivity;
import com.minglang.suiuu.activity.OtherUserActivity;
import com.minglang.suiuu.activity.SuiuuDetailActivity;
import com.minglang.suiuu.adapter.AttentionDynamicAdapter;
import com.minglang.suiuu.adapter.LoopDynamicAdapter;
import com.minglang.suiuu.adapter.RecommendTravelAdapter;
import com.minglang.suiuu.adapter.TodayStarAdapter;
import com.minglang.suiuu.customview.LinearLayoutForListView;
import com.minglang.suiuu.customview.NoScrollBarGridView;
import com.minglang.suiuu.entity.MainDynamic;
import com.minglang.suiuu.entity.MainDynamicData;
import com.minglang.suiuu.entity.MainDynamicDataLoop;
import com.minglang.suiuu.entity.MainDynamicDataRecommendTravel;
import com.minglang.suiuu.entity.MainDynamicDataRecommendUser;
import com.minglang.suiuu.entity.MainDynamicDataUser;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;


/**
 * 主页面
 */
public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    private static final String USERSIGNKEY = "userSign";
    private static final String ARTICLEID = "articleId";

    private int screenWidth, screenHeight;

    private PtrClassicFrameLayout mPtrFrame;

    private ScrollView scrollView;

    /**
     * 关注动态数据集合
     */
    private List<MainDynamicDataUser> mainDynamicDataUserList;

    /**
     * 关注动态Layout
     */
    private LinearLayoutForListView attentionDynamicLayout;

    /**
     * 热门推荐数据集合
     */
    private List<MainDynamicDataRecommendTravel> recommendTravelList;

    /**
     * 热门推荐Layout
     */
    private LinearLayoutForListView recommendDynamicLayout;

    /**
     * 今日之星数据
     */
    private List<MainDynamicDataRecommendUser> recommendUserList;
    /**
     * 今日之星数据加载View
     */
    private NoScrollBarGridView todayStarGridView;

    /**
     * 圈子动态数据集合
     */
    private List<MainDynamicDataLoop> loopDynamicList = new ArrayList<>();

    /**
     * 圈子动态GridView
     */
    private NoScrollBarGridView loopDynamicGridView;

    /**
     * 点击查看更多动态
     */
    private TextView moreButton;

    private ProgressDialog progressDialog;

    private RelativeLayout AttentionDynamicTitleLayout, RecommendTravelTitleLayout, TodayStarTitleLayout, LoopDynamicTitleLayout;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, null);

        initView(rootView);

        ViewAction();

        getMainDynamic4Service();

        return rootView;
    }

    /**
     * 数据绑定到View
     *
     * @param str Json字符串
     */
    private void bindData2View(String str) {
        try {
            MainDynamic mainDynamic = JsonUtil.getInstance().fromJSON(MainDynamic.class, str);
            MainDynamicData data = mainDynamic.getData();

            //关注动态
            mainDynamicDataUserList = data.getUserDynamic().getData();
            if (recommendUserList != null && recommendUserList.size() > 0) {
                AttentionDynamicAdapter attentionDynamicAdapter = new AttentionDynamicAdapter(getActivity(),
                        mainDynamicDataUserList);
                attentionDynamicAdapter.setScreenHeight(screenHeight);
                attentionDynamicLayout.setAdapter(attentionDynamicAdapter);
            } else {
                AttentionDynamicTitleLayout.setVisibility(View.GONE);
            }

            //热门推荐
            recommendTravelList = data.getRecommendTravel().getData();
            if (recommendTravelList != null && recommendTravelList.size() > 0) {
                RecommendTravelAdapter recommendTravelAdapter = new RecommendTravelAdapter(getActivity(),
                        recommendTravelList);
                recommendTravelAdapter.setScreenHeight(screenHeight);
                recommendDynamicLayout.setAdapter(recommendTravelAdapter);
            } else {
                RecommendTravelTitleLayout.setVisibility(View.GONE);
            }

            //今日之星
            recommendUserList = data.getRecommendUser().getData();
            if (recommendUserList != null && recommendUserList.size() > 0) {
                TodayStarAdapter todayStarAdapter = new TodayStarAdapter(getActivity(), recommendUserList);
                todayStarAdapter.setScreenParams(screenWidth);
                todayStarGridView.setAdapter(todayStarAdapter);
            } else {
                TodayStarTitleLayout.setVisibility(View.GONE);
            }

            //圈子动态
            loopDynamicList = data.getCircleDynamic();
            if (loopDynamicList != null && loopDynamicList.size() > 0) {
                LoopDynamicAdapter loopDynamicAdapter = new LoopDynamicAdapter(getActivity(), loopDynamicList);
                loopDynamicAdapter.setScreenParams(screenWidth);
                loopDynamicGridView.setAdapter(loopDynamicAdapter);
            } else {
                LoopDynamicTitleLayout.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e(TAG, "首页数据解析失败:" + e.getMessage());
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 网络请求数据
     */
    private void getMainDynamic4Service() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("n", "6");
        params.addBodyParameter(HttpServicePath.key, SuiuuInfo.ReadVerification(getActivity()));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.MainDynamicPath, new MainDynamicRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();

        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    /**
     * 控件动作
     */
    private void ViewAction() {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, scrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                        HttpServicePath.MainDynamicPath, new MainDynamicRequestCallBack());
                httpRequest.requestNetworkData();
            }
        });

        attentionDynamicLayout.setOnItemClickListener(new LinearLayoutForListView.OnItemClickListener() {
            @Override
            public void onItemClicked(View v, Object obj, int position) {
                MainDynamicDataUser user = mainDynamicDataUserList.get(position);
                String articleId = user.getArticleId();
                Intent intent = new Intent(getActivity(), LoopArticleActivity.class);
                intent.putExtra(ARTICLEID, articleId);
                intent.putExtra("TAG", TAG);
                startActivity(intent);
            }
        });

        recommendDynamicLayout.setOnItemClickListener(new LinearLayoutForListView.OnItemClickListener() {
            @Override
            public void onItemClicked(View v, Object obj, int position) {
                Intent intent = new Intent(getActivity(), SuiuuDetailActivity.class);
                intent.putExtra("tripId", recommendTravelList.get(position).getTripId());
                startActivity(intent);
            }
        });

        todayStarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainDynamicDataRecommendUser user = recommendUserList.get(position);
                String userSign = user.getUserSign();
                Intent intent = new Intent(getActivity(), OtherUserActivity.class);
                intent.putExtra(USERSIGNKEY, userSign);
                startActivity(intent);
            }
        });

        loopDynamicGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainDynamicDataLoop loop = loopDynamicList.get(position);
                String articleId = loop.getArticleId();
                Intent intent = new Intent(getActivity(), LoopArticleActivity.class);
                intent.putExtra(ARTICLEID, articleId);
                intent.putExtra("TAG", TAG);
                startActivity(intent);
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllAttentionDynamicActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化方法
     */
    private void initView(View rootView) {

        ScreenUtils screenUtils = new ScreenUtils(getActivity());
        screenWidth = screenUtils.getScreenWidth();
        screenHeight = screenUtils.getScreenHeight();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.load_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.main_fragment_head_frame);

        MaterialHeader header = new MaterialHeader(getActivity());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrame);

        scrollView = (ScrollView) rootView.findViewById(R.id.main_fragment_scrollView);

        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKITKAT) {
            SystemBarTintManager.SystemBarConfig systemBarConfig = new SystemBarTintManager(getActivity()).getConfig();
            int statusHeight = systemBarConfig.getStatusBarHeight();
            scrollView.setPadding(0, statusHeight, 0, 0);
        }

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

        attentionDynamicLayout = (LinearLayoutForListView) rootView.findViewById(R.id.mainAttentionLayout);

        recommendDynamicLayout = (LinearLayoutForListView) rootView.findViewById(R.id.mainRecommendationLayout);

        todayStarGridView = (NoScrollBarGridView) rootView.findViewById(R.id.mainTodayStarGridView);

        loopDynamicGridView = (NoScrollBarGridView) rootView.findViewById(R.id.mainLoopDynamicGridView);

        moreButton = (TextView) rootView.findViewById(R.id.main_fragment_more);

        RelativeLayout footBlackView = (RelativeLayout) rootView.findViewById(R.id.main_fragment_foot_black_view);
        ViewGroup.LayoutParams params = footBlackView.getLayoutParams();
        params.height = BitmapFactory.decodeResource(getResources(), R.drawable.main_guide_line).getHeight();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        footBlackView.setLayoutParams(params);

        AttentionDynamicTitleLayout = (RelativeLayout) rootView.findViewById(R.id.AttentionDynamicTitleLayout);
        RecommendTravelTitleLayout = (RelativeLayout) rootView.findViewById(R.id.RecommendTravelTitleLayout);
        TodayStarTitleLayout = (RelativeLayout) rootView.findViewById(R.id.TodayStarTitleLayout);
        LoopDynamicTitleLayout = (RelativeLayout) rootView.findViewById(R.id.LoopDynamicTitleLayout);
    }

    /**
     * 网络请求回调接口
     */
    private class MainDynamicRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            String str = stringResponseInfo.result;

            attentionDynamicLayout.removeAllViews();
            recommendDynamicLayout.removeAllViews();
            bindData2View(str);
            scrollView.smoothScrollTo(0, 0);
            mPtrFrame.refreshComplete();
        }

        @Override
        public void onFailure(HttpException e, String s) {

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            mPtrFrame.refreshComplete();

            Log.e(TAG, "数据请求失败:" + s);
            Toast.makeText(getActivity(), "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }
}
