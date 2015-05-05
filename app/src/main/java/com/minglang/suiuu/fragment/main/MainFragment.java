package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.AllAttentionDynamicActivity;
import com.minglang.suiuu.activity.LoopArticleActivity;
import com.minglang.suiuu.activity.OtherUserActivity;
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

    private static final String USERSIGNKEY = "usersign";
    private static final String ARTICLEID = "articleId";

    private int screenWidth, screenHeight;

    /**
     * 下拉刷新组件
     */
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
    private List<MainDynamicDataRecommendTravel> mainDynamicDataRecommendTravelList;

    /**
     * 热门推荐Layout
     */
    private LinearLayoutForListView recommendDynamicLayout;

    /**
     * 今日之星数据
     */
    private List<MainDynamicDataRecommendUser> mainDynamicDataRecommendUserList;
    /**
     * 今日之星数据加载View
     */
    private NoScrollBarGridView todayStarGridView;

    /**
     * 圈子动态数据集合
     */
    private List<MainDynamicDataLoop> listLoopDynamic = new ArrayList<>();

    /**
     * 圈子动态GridView
     */
    private NoScrollBarGridView loopDynamicGridView;

    /**
     * 点击查看更多动态
     */
    private TextView moreButton;

    private Dialog dialog;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, null);

        initView(rootView);

        ViewAction();

        getData();

        return rootView;
    }

    /**
     * 数据绑定到View
     *
     * @param str Json字符串
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(getActivity(), getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
        } else {
            try {
                //数据总集
                MainDynamic mainDynamic = JsonUtil.getInstance().fromJSON(MainDynamic.class, str);
                MainDynamicData data = mainDynamic.getData();

                //关注动态
                mainDynamicDataUserList = data.getUserDynamic();
                AttentionDynamicAdapter attentionDynamicAdapter = new AttentionDynamicAdapter(getActivity(),
                        mainDynamicDataUserList);
                attentionDynamicLayout.setAdapter(attentionDynamicAdapter);

                //热门推荐
                mainDynamicDataRecommendTravelList = data.getRecommendTravel();
                RecommendTravelAdapter recommendTravelAdapter = new RecommendTravelAdapter(getActivity(),
                        mainDynamicDataRecommendTravelList);
                recommendDynamicLayout.setAdapter(recommendTravelAdapter);

                //今日之星
                mainDynamicDataRecommendUserList = data.getRecommendUser();
                TodayStarAdapter todayStarAdapter = new TodayStarAdapter(getActivity(), mainDynamicDataRecommendUserList);
                todayStarAdapter.setScreenParams(screenWidth, screenHeight);
                todayStarGridView.setAdapter(todayStarAdapter);

                //圈子动态
                listLoopDynamic = data.getCircleDynamic();
                LoopDynamicAdapter loopDynamicAdapter = new LoopDynamicAdapter(getActivity(), listLoopDynamic);
                loopDynamicAdapter.setScrrenParams(screenWidth, screenHeight);
                loopDynamicGridView.setAdapter(loopDynamicAdapter);

            } catch (Exception e) {
                Log.e(TAG, "首页动态数据解析错误:" + e.getMessage());
                Toast.makeText(getActivity(), getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getData() {

        if (dialog != null) {
            dialog.show();
        }
        getMainDynamic4Service();
    }

    /**
     * 网络请求数据
     */
    private void getMainDynamic4Service() {
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.MainDynamicPath, new MainDynamicRequestCallBack());
        httpRequest.requestNetworkData();
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
                getMainDynamic4Service();
            }
        });

        attentionDynamicLayout.setOnItemClickListener(new LinearLayoutForListView.OnItemClickListener() {
            @Override
            public void onItemClicked(View v, Object obj, int position) {
                MainDynamicDataUser user = mainDynamicDataUserList.get(position);
                String articleId = user.getArticleId();
                Intent intent = new Intent(getActivity(), LoopArticleActivity.class);
                intent.putExtra(ARTICLEID, articleId);
                startActivity(intent);
            }
        });

        recommendDynamicLayout.setOnItemClickListener(new LinearLayoutForListView.OnItemClickListener() {
            @Override
            public void onItemClicked(View v, Object obj, int position) {
                MainDynamicDataRecommendTravel travel = mainDynamicDataRecommendTravelList.get(position);
                Log.i(TAG, "热门推荐Item数据:" + travel.toString());
                Toast.makeText(getActivity(), "随游还未到来...请耐心等待", Toast.LENGTH_SHORT).show();
            }
        });

        todayStarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainDynamicDataRecommendUser user = mainDynamicDataRecommendUserList.get(position);
                String userSign = user.getUserSign();
                Intent intent = new Intent(getActivity(), OtherUserActivity.class);
                intent.putExtra(USERSIGNKEY, userSign);
                startActivity(intent);
            }
        });

        //圈子动态详情跳转
        loopDynamicGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainDynamicDataLoop loop = listLoopDynamic.get(position);
                String articleId = loop.getArticleId();
                Intent intent = new Intent(getActivity(), LoopArticleActivity.class);
                intent.putExtra(ARTICLEID, articleId);
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

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /**
     * 初始化方法
     */
    private void initView(View rootView) {

        ScreenUtils screenUtils = new ScreenUtils(getActivity());
        screenWidth = screenUtils.getScreenWidth();
        screenHeight = screenUtils.getScreenHeight();

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);

        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.main_fragment_head_frame);

        MaterialHeader header = new MaterialHeader(getActivity());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrame);

        scrollView = (ScrollView) rootView.findViewById(R.id.main_fragment_scrollView);

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
    }

    /**
     * 网络请求回调接口
     */
    private class MainDynamicRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            String str = stringResponseInfo.result;
            Log.i(TAG, "请求返回的数据:" + str);

            attentionDynamicLayout.removeAllViews();
            recommendDynamicLayout.removeAllViews();
            bindData2View(str);
            scrollView.smoothScrollTo(0, 0);
            mPtrFrame.refreshComplete();
        }

        @Override
        public void onFailure(HttpException e, String s) {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            mPtrFrame.refreshComplete();

            Log.e(TAG, "数据请求失败:" + s);
            Toast.makeText(getActivity(), getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }
}
