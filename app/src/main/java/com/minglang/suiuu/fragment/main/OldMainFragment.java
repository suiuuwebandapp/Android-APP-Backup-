package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.minglang.suiuu.application.SuiuuApplication;
import com.minglang.suiuu.base.BaseFragment;
import com.minglang.suiuu.customview.InnerListView;
import com.minglang.suiuu.customview.NoScrollBarGridView;
import com.minglang.suiuu.entity.MainDynamic;
import com.minglang.suiuu.entity.MainDynamicData;
import com.minglang.suiuu.entity.MainDynamicDataLoop;
import com.minglang.suiuu.entity.MainDynamicDataRecommendTravel;
import com.minglang.suiuu.entity.MainDynamicDataRecommendUser;
import com.minglang.suiuu.entity.MainDynamicDataUser;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * 主页面
 */
public class OldMainFragment extends BaseFragment {

    private static final String TAG = OldMainFragment.class.getSimpleName();

    private static final String USERSIGNKEY = "userSign";
    private static final String ARTICLEID = "articleId";

    private PtrClassicFrameLayout mPtrFrame;

    private ScrollView scrollView;

    /**
     * 关注动态数据集合
     */
    private List<MainDynamicDataUser> mainDynamicDataUserList;

    /**
     * 关注动态Layout
     */
    private InnerListView attentionDynamicListView;

    /**
     * 热门推荐数据集合
     */
    private List<MainDynamicDataRecommendTravel> recommendTravelList;

    /**
     * 热门推荐Layout
     */
    private InnerListView recommendDynamicListView;

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

    private RelativeLayout attentionDynamicTitleLayout, recommendDynamicTitleLayout, todayStarTitleLayout, loopDynamicTitleLayout;

    private DisplayImageOptions displayImageOptions;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_old_main, null);

        initView(rootView);
        ViewAction();
        getData();

        return rootView;
    }

    /**
     * 初始化方法
     */
    private void initView(View rootView) {

        screenHeight = new ScreenUtils(getActivity()).getScreenHeight();
        screenWidth = new ScreenUtils(getActivity()).getScreenWidth();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.load_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.main_fragment_head_frame);

        scrollView = (ScrollView) rootView.findViewById(R.id.main_fragment_scrollView);

        if (isKITKAT) {
            if (navigationBarHeight <= 0) {
                scrollView.setPadding(0, statusBarHeight, 0, 0);
            } else {
                scrollView.setPadding(0, statusBarHeight, 0, navigationBarHeight);
            }
        }

        MaterialHeader header = new MaterialHeader(getActivity());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, Utils.newInstance().dip2px(15, getActivity()), 0, Utils.newInstance().dip2px(15, getActivity()));
        header.setPtrFrameLayout(mPtrFrame);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setPinContent(true);

        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.5f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);

        attentionDynamicListView = (InnerListView) rootView.findViewById(R.id.mainAttentionLayout);
        attentionDynamicListView.setMaxHeight(screenHeight);
        attentionDynamicListView.setParentScrollView(scrollView);

        recommendDynamicListView = (InnerListView) rootView.findViewById(R.id.mainRecommendationLayout);
        recommendDynamicListView.setMaxHeight(screenHeight);
        recommendDynamicListView.setParentScrollView(scrollView);

        todayStarGridView = (NoScrollBarGridView) rootView.findViewById(R.id.mainTodayStarGridView);
        loopDynamicGridView = (NoScrollBarGridView) rootView.findViewById(R.id.mainLoopDynamicGridView);

        moreButton = (TextView) rootView.findViewById(R.id.main_fragment_more);

        RelativeLayout footBlackView = (RelativeLayout) rootView.findViewById(R.id.main_fragment_foot_black_view);
        ViewGroup.LayoutParams params = footBlackView.getLayoutParams();
        params.height = BitmapFactory.decodeResource(getResources(), R.drawable.main_guide_line).getHeight();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        footBlackView.setLayoutParams(params);

        attentionDynamicTitleLayout = (RelativeLayout) rootView.findViewById(R.id.AttentionDynamicTitleLayout);
        recommendDynamicTitleLayout = (RelativeLayout) rootView.findViewById(R.id.RecommendTravelTitleLayout);
        todayStarTitleLayout = (RelativeLayout) rootView.findViewById(R.id.TodayStarTitleLayout);
        loopDynamicTitleLayout = (RelativeLayout) rootView.findViewById(R.id.LoopDynamicTitleLayout);

        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
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

        attentionDynamicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainDynamicDataUser user = mainDynamicDataUserList.get(position);
                String articleId = user.getArticleId();
                Intent intent = new Intent(getActivity(), LoopArticleActivity.class);
                intent.putExtra(ARTICLEID, articleId);
                intent.putExtra("TAG", TAG);
                startActivity(intent);
            }
        });

        recommendDynamicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

    private void getData() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        getMainDynamic4Service();
    }

    /**
     * 网络请求数据
     */
    private void getMainDynamic4Service() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("n", "6");
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.MainDynamicPath, new MainDynamicRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 数据绑定到View
     *
     * @param str Json字符串
     */
    private void bindData2View(String str) {
        MainImageLoadingListener mainImageLoadingListener = new MainImageLoadingListener();
        try {
            MainDynamic mainDynamic = JsonUtils.getInstance().fromJSON(MainDynamic.class, str);
            if (mainDynamic.getStatus().equals("1")) {
                MainDynamicData data = mainDynamic.getData();

                //关注动态
                mainDynamicDataUserList = data.getUserDynamic().getData();
                if (recommendUserList != null && recommendUserList.size() > 0) {
                    AttentionDynamicAdapter attentionDynamicAdapter = new AttentionDynamicAdapter(getActivity(),
                            mainDynamicDataUserList);
                    attentionDynamicAdapter.setScreenHeight(screenHeight);
                    attentionDynamicAdapter.setImageLoadingListener(mainImageLoadingListener);
                    attentionDynamicListView.setAdapter(attentionDynamicAdapter);
                } else {
                    attentionDynamicTitleLayout.setVisibility(View.GONE);
                    attentionDynamicListView.setVisibility(View.GONE);
                }

                //热门推荐
                recommendTravelList = data.getRecommendTravel().getData();
                if (recommendTravelList != null && recommendTravelList.size() > 0) {
                    RecommendTravelAdapter recommendTravelAdapter = new RecommendTravelAdapter(getActivity(),
                            recommendTravelList);
                    recommendTravelAdapter.setScreenHeight(screenHeight);
                    recommendTravelAdapter.setImageLoadingListener(mainImageLoadingListener);
                    recommendDynamicListView.setAdapter(recommendTravelAdapter);
                } else {
                    recommendDynamicTitleLayout.setVisibility(View.GONE);
                    recommendDynamicListView.setVisibility(View.GONE);
                }

                //今日之星
                recommendUserList = data.getRecommendUser().getData();
                if (recommendUserList != null && recommendUserList.size() > 0) {
                    TodayStarAdapter todayStarAdapter = new TodayStarAdapter(getActivity(), recommendUserList);
                    todayStarAdapter.setScreenParams(screenWidth);
                    todayStarAdapter.setImageLoadingListener(mainImageLoadingListener);
                    todayStarGridView.setAdapter(todayStarAdapter);
                } else {
                    todayStarTitleLayout.setVisibility(View.GONE);
                }

                //圈子动态
                loopDynamicList = data.getCircleDynamic();
                if (loopDynamicList != null && loopDynamicList.size() > 0) {
                    LoopDynamicAdapter loopDynamicAdapter = new LoopDynamicAdapter(getActivity(), loopDynamicList);
                    loopDynamicAdapter.setScreenParams(screenWidth);
                    loopDynamicAdapter.setImageLoadingListener(mainImageLoadingListener);
                    loopDynamicGridView.setAdapter(loopDynamicAdapter);
                } else {
                    loopDynamicTitleLayout.setVisibility(View.GONE);
                }
            } else if (mainDynamic.getStatus().equals("-3")) {
                Toast.makeText(getActivity().getApplicationContext(), "登录信息过期,请重新登录", Toast.LENGTH_SHORT).show();
                AppUtils.intentLogin(getActivity());
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(),
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            DeBugLog.e(TAG, "首页数据解析失败:" + e.getMessage());
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = SuiuuApplication.getRefWatcher();
        refWatcher.watch(this);
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

            DeBugLog.e(TAG, "数据请求失败:" + s);
            Toast.makeText(getActivity(), "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }

    }

    private class MainImageLoadingListener implements ImageLoadingListener {

        @Override
        public void onLoadingStarted(String imageUri, View view) {

        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            DeBugLog.e(TAG, "onLoadingFailed():" + imageUri);
            DeBugLog.e(TAG, "onLoadingFailed():view.getId():" + view.getId());

            switch (failReason.getType()) {
                case IO_ERROR:
                    DeBugLog.e(TAG, "ImageLoader is appear IO_ERROR!");

                case DECODING_ERROR:
                    DeBugLog.e(TAG, "ImageLoader is not DECODING_ERROR!");

                case NETWORK_DENIED:
                    DeBugLog.e(TAG, "ImageLoader is request failure!");
                    imageLoader.displayImage(imageUri, (ImageView) view, displayImageOptions, new MainImageLoadingListener());
                    break;

                case OUT_OF_MEMORY:
                    DeBugLog.e(TAG, "ImageLoader is appear OUT_OF_MEMORY!");
                    imageLoader.clearMemoryCache();
                    break;

                default:
                    DeBugLog.e(TAG, "ImageLoader is appear UNKNOWN ERROR!");
                    break;
            }
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {

        }

    }

}
