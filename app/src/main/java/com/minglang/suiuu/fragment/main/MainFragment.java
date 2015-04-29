package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.TodayStarAdapter;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.customview.NoScrollBarGridView;
import com.minglang.suiuu.entity.MainDynamic;
import com.minglang.suiuu.entity.MainDynamicData;
import com.minglang.suiuu.entity.MainDynamicDataCircleDynamic;
import com.minglang.suiuu.entity.MainDynamicDataRecommendUser;
import com.minglang.suiuu.entity.MainDynamicDataUserDynamic;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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

    private int screenWidth, screenHeight;

    private PtrClassicFrameLayout mPtrFrame;

    private ScrollView scrollView;

    private MaterialHeader header;

    /**
     * 数据总集
     */
    private MainDynamic mainDynamic;

    private ImageLoader imageLoader;

    /**
     * 今日之星数据
     */
    private List<MainDynamicDataRecommendUser> mainDynamicDataRecommendUserList;
    /**
     * 今日之星数据加载View
     */
    private NoScrollBarGridView todayStarGridView;

    /**
     * 关注动态Layout
     */
    private LinearLayout attentionDynamicLayout;

    /**
     * GridView
     */
    private NoScrollBarGridView loopDynamicGridView;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, null);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));

        initView(rootView);

        ViewAction();

        getMainDynamic4Service();

        return rootView;
    }

    /**
     * 添加关注动态条目
     *
     * @param list 关注动态数据
     */
    private void addAttentionDynamicView(List<MainDynamicDataUserDynamic> list) {
        if (list == null) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            View view = AttentionDynamicData2View(list.get(i));
            view.setId(10 + i);
            attentionDynamicLayout.addView(view);
        }

    }

    /**
     * 加载关注数据到View
     *
     * @param mainDynamicDataUserDynamic 单条关注数据
     * @return 加载完数据后的View
     */
    private View AttentionDynamicData2View(MainDynamicDataUserDynamic mainDynamicDataUserDynamic) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_attention_dynamic, null);

        ImageView mainView = (ImageView) view.findViewById(R.id.item_attention_dynamic_image);
        String imagePath = mainDynamicDataUserDynamic.getaImg();
        if (!TextUtils.isEmpty(imagePath)) {
            imageLoader.displayImage(imagePath, mainView);
        }

        CircleImageView headImage = (CircleImageView) view.findViewById(R.id.item_attention_dynamic_head);
        String headImagePath = mainDynamicDataUserDynamic.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(headImagePath, headImage);
        }

        TextView title = (TextView) view.findViewById(R.id.item_attention_dynamic_title);
        String strTitle = mainDynamicDataUserDynamic.getaTitle();
        if (!TextUtils.isEmpty(strTitle)) {
            title.setText(strTitle);
        } else {
            title.setText("");
        }

        TextView content = (TextView) view.findViewById(R.id.item_attention_dynamic_content);
        String strContent = mainDynamicDataUserDynamic.getArticleId();
        if (!TextUtils.isEmpty(strContent)) {
            content.setText(strContent);
        } else {
            content.setText("");
        }

        return view;
    }

    /**
     * 添加今日之星条目
     *
     * @param list 今日之星数据
     */
    private void addTodayStarView(List<MainDynamicDataRecommendUser> list) {

    }

    /**
     * 添加圈子动态条目
     *
     * @param dynamic 圈子动态数据
     */
    private void addLoopDynamaicView(MainDynamicDataCircleDynamic dynamic) {

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

        todayStarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

        header = new MaterialHeader(getActivity());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrame);

        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.main_fragment_head_frame);
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

        attentionDynamicLayout = (LinearLayout) rootView.findViewById(R.id.mainAttentionLayout);

        todayStarGridView = (NoScrollBarGridView) rootView.findViewById(R.id.mainTodayStarGridView);

        loopDynamicGridView = (NoScrollBarGridView) rootView.findViewById(R.id.mainLoopDynamicGridView);
    }

    /**
     * 网络请求回调接口
     */
    private class MainDynamicRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            try {
                mainDynamic = JsonUtil.getInstance().fromJSON(MainDynamic.class, str);
                MainDynamicData data = mainDynamic.getData();

                //圈子动态
                MainDynamicDataCircleDynamic mainDynamicDataCircleDynamic = data.getCircleDynamic();
                //关注动态
                List<MainDynamicDataUserDynamic> mainDynamicDataUserDynamicList = data.getUserDynamic();
                addAttentionDynamicView(mainDynamicDataUserDynamicList);
                //今日之星
                mainDynamicDataRecommendUserList = data.getRecommendUser();

                TodayStarAdapter todayStarAdapter = new TodayStarAdapter(getActivity(), mainDynamicDataRecommendUserList);
                todayStarAdapter.setScreenParams(screenWidth, screenHeight);
                todayStarGridView.setAdapter(todayStarAdapter);

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, s);
        }
    }

}
