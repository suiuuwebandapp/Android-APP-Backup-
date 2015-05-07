package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.activity.LoopDetailsActivity;
import com.minglang.suiuu.adapter.LoopFragmentPagerAdapter;
import com.minglang.suiuu.adapter.LoopScrollPagerAdapter;
import com.minglang.suiuu.customview.AutoScrollViewPager;
import com.minglang.suiuu.entity.RecommendLoop;
import com.minglang.suiuu.entity.RecommendLoopData;
import com.minglang.suiuu.fragment.loop.AreaFragment;
import com.minglang.suiuu.fragment.loop.ThemeFragment;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子页面
 */
public class LoopFragment extends Fragment {

    private static final String TAG = LoopFragment.class.getSimpleName();

    private static final int IMAGEID1 = 100001;
    private static final int IMAGEID2 = 100002;
    private static final int IMAGEID3 = 100003;
    private static final int IMAGEID4 = 100004;
    private static final int IMAGEID5 = 100005;

    private static final String TYPE = "type";
    private static final String CIRCLEID = "circleId";


    /**
     * 轮播ViewPager
     */
    private AutoScrollViewPager loopScrollViewPager;
    /**
     * 轮播图片View列表
     */
    private List<ImageView> imageList = new ArrayList<>();

    private ImageLoader imageLoader;

    private DisplayImageOptions displayImageOptions;

    /**
     * tab头对象
     */
    private TextView title0, title1;

    /**
     * 滑块
     */
    private ImageView sliderView;

    private ViewPager loopViewPager;

    /**
     * 设备宽度
     */
    private int screenWidth;

    /**
     * 设备高度
     */
    private int screenHeight;

    private int currIndex = 1;// 当前页卡编号

    private int tabWidth;// 每个tab头的宽度

    private int offsetX;//偏移量

    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5;

    /**
     * 推荐的圈子的数据
     */
    private List<RecommendLoopData> recommendLoopImagePathList;

    private List<String> imagePathList = new ArrayList<>();

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_loop, null);

        initScreenOrImageLoad();

        initView(rootView);

        getRecommendLoopData4Service();

        ViewAction();

        return rootView;
    }

    /**
     * 得到推荐的圈子的图片
     */
    private void getRecommendLoopData4Service() {
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.GetRecommendLoopPath, new RecommendLoopRequestCallBack());
        httpRequest.requestNetworkData();
    }

    /**
     * 控件动作
     */
    private void ViewAction() {
        title0.setOnClickListener(new TitleOnClick(0));
        title1.setOnClickListener(new TitleOnClick(1));

        loopViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                sliderView.setPadding(0, 0, 0, 0);

                switch (i) {
                    case 0:
                        title0.setTextColor(getResources().getColor(R.color.slider_line_color));
                        title1.setTextColor(getResources().getColor(R.color.textColor));
                        break;
                    case 1:
                        title0.setTextColor(getResources().getColor(R.color.textColor));
                        title1.setTextColor(getResources().getColor(R.color.slider_line_color));
                        break;
                }
                currIndex = i;
                Animation anim = new TranslateAnimation(currIndex == 1 ? offsetX : tabWidth + offsetX,
                        currIndex == 1 ? tabWidth + offsetX : offsetX, 0, 0);
                anim.setFillAfter(true);
                anim.setDuration(200);
                sliderView.startAnimation(anim);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        imageView1.setOnClickListener(new RecommendClick());
        imageView2.setOnClickListener(new RecommendClick());
        imageView3.setOnClickListener(new RecommendClick());
        imageView4.setOnClickListener(new RecommendClick());
        imageView5.setOnClickListener(new RecommendClick());

    }

    private void LoadRecommendLoopImage() {
        if (imagePathList != null && imagePathList.size() > 0) {
            imageLoader.displayImage(imagePathList.get(0), imageView1, displayImageOptions);
            imageLoader.displayImage(imagePathList.get(1), imageView2, displayImageOptions);
            imageLoader.displayImage(imagePathList.get(2), imageView3, displayImageOptions);
            imageLoader.displayImage(imagePathList.get(3), imageView4, displayImageOptions);
            imageLoader.displayImage(imagePathList.get(4), imageView5, displayImageOptions);
        }
    }

    /**
     * 初始化获得屏幕宽高等
     */
    private void initScreenOrImageLoad() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        screenWidth = dm.widthPixels;// 获取设备宽度
        tabWidth = screenWidth / 2;

        screenHeight = dm.heightPixels;

        imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        }
        displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.scroll1)
                .showImageForEmptyUri(R.drawable.scroll1).showImageOnFail(R.drawable.scroll1)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    /**
     * 初始化方法
     *
     * @param rootView Fragment根View
     */
    @SuppressLint("InflateParams")
    private void initView(View rootView) {

        RelativeLayout loopScrollLayout = (RelativeLayout) rootView.findViewById(R.id.LoopScrollLayout);
        ViewGroup.LayoutParams loopLayoutParams = loopScrollLayout.getLayoutParams();
        loopLayoutParams.height = screenHeight / 3;
        loopLayoutParams.width = screenWidth;
        loopScrollLayout.setLayoutParams(loopLayoutParams);

        imageView1 = new ImageView(getActivity());
        imageView1.setTag(IMAGEID1);
        imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView2 = new ImageView(getActivity());
        imageView2.setTag(IMAGEID2);
        imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView3 = new ImageView(getActivity());
        imageView3.setTag(IMAGEID3);
        imageView3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView4 = new ImageView(getActivity());
        imageView4.setTag(IMAGEID4);
        imageView4.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView5 = new ImageView(getActivity());
        imageView5.setTag(IMAGEID5);
        imageView5.setScaleType(ImageView.ScaleType.CENTER_CROP);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView1.setLayoutParams(params);
        imageView2.setLayoutParams(params);
        imageView3.setLayoutParams(params);
        imageView4.setLayoutParams(params);
        imageView5.setLayoutParams(params);

        imageList.add(imageView1);
        imageList.add(imageView2);
        imageList.add(imageView3);
        imageList.add(imageView4);
        imageList.add(imageView5);

        loopScrollViewPager = (AutoScrollViewPager) rootView.findViewById(R.id.LoopScrollViewPager);
        loopScrollViewPager.setInterval(2000);
        LoopScrollPagerAdapter loopScrollPagerAdapter = new LoopScrollPagerAdapter(getActivity(), imageList);
        loopScrollViewPager.setAdapter(loopScrollPagerAdapter);
        loopScrollViewPager.startAutoScroll();

        title0 = (TextView) rootView.findViewById(R.id.theme_title);
        title1 = (TextView) rootView.findViewById(R.id.area_title);

        sliderView = (ImageView) rootView.findViewById(R.id.sliderView);

        ViewGroup.LayoutParams sliderParams = sliderView.getLayoutParams();
        sliderParams.width = tabWidth;
        sliderView.setLayoutParams(sliderParams);

        loopViewPager = (ViewPager) rootView.findViewById(R.id.loopViewPager);
        loopViewPager.setOffscreenPageLimit(2);

        String userSign = SuiuuInfo.ReadUserSign(getActivity());
        String verification = SuiuuInfo.ReadVerification(getActivity());

        //主题页面
        ThemeFragment themeFragment = ThemeFragment.newInstance(userSign, verification);
        //地区页面
        AreaFragment areaFragment = AreaFragment.newInstance(userSign, verification);

        FragmentManager fm = getFragmentManager();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(themeFragment);
        fragments.add(areaFragment);

        LoopFragmentPagerAdapter lfpAdapter = new LoopFragmentPagerAdapter(fm, fragments);

        loopViewPager.setAdapter(lfpAdapter);

        initImageView();
    }

    private void initImageView() {

        int sliderViewWidth = BitmapFactory.decodeResource(getResources(), R.drawable.slider).getWidth();

        if (sliderViewWidth > tabWidth) {
            sliderView.getLayoutParams().width = tabWidth;
            sliderViewWidth = tabWidth;
        }

        offsetX = (tabWidth - sliderViewWidth) / 2;
        sliderView.setPadding(offsetX, 0, 0, 0);

    }

    @Override
    public void onResume() {
        super.onResume();
        loopScrollViewPager.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        loopScrollViewPager.stopAutoScroll();
    }

    class TitleOnClick implements View.OnClickListener {

        private int index;

        public TitleOnClick(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            loopViewPager.setCurrentItem(index);
        }
    }

    class RecommendLoopRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            try {
                RecommendLoop recommendLoop = JsonUtil.getInstance().fromJSON(RecommendLoop.class, str);
                recommendLoopImagePathList = recommendLoop.getData().getData();
                if (recommendLoopImagePathList != null && recommendLoopImagePathList.size() > 0) {
                    //RecommendImageList
                    for (RecommendLoopData data : recommendLoopImagePathList) {
                        String imagePath = data.getCpic();
                        imagePathList.add(imagePath);
                    }
                    LoadRecommendLoopImage();
                }
            } catch (Exception e) {
                Log.e(TAG, "推荐的圈子解析错误:" + e.getMessage());
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, "推荐的圈子请求错误:" + e.getMessage());
        }
    }

    class RecommendClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            boolean flag = recommendLoopImagePathList != null && recommendLoopImagePathList.size() > 0;
            Intent intent = new Intent(getActivity(), LoopDetailsActivity.class);

            switch ((int) v.getTag()) {

                case IMAGEID1:
                    if (flag) {
                        RecommendLoopData data = recommendLoopImagePathList.get(0);
                        String circleId = data.getcId();
                        String type = data.getcType();
                        String loopName = data.getcName();
                        intent.putExtra(TYPE, type);
                        intent.putExtra(CIRCLEID, circleId);
                        intent.putExtra("name",loopName);
                        startActivity(intent);
                    }
                    break;

                case IMAGEID2:
                    if (flag) {
                        RecommendLoopData data = recommendLoopImagePathList.get(1);
                        String circleId = data.getcId();
                        String type = data.getcType();
                        String loopName = data.getcName();
                        intent.putExtra(TYPE, type);
                        intent.putExtra(CIRCLEID, circleId);
                        intent.putExtra("name",loopName);
                        startActivity(intent);
                    }
                    break;

                case IMAGEID3:
                    if (flag) {
                        RecommendLoopData data = recommendLoopImagePathList.get(2);
                        String circleId = data.getcId();
                        String type = data.getcType();
                        String loopName = data.getcName();
                        intent.putExtra(TYPE, type);
                        intent.putExtra(CIRCLEID, circleId);
                        intent.putExtra("name",loopName);
                        startActivity(intent);
                    }
                    break;

                case IMAGEID4:
                    if (flag) {
                        RecommendLoopData data = recommendLoopImagePathList.get(3);
                        String circleId = data.getcId();
                        String type = data.getcType();
                        String loopName = data.getcName();
                        intent.putExtra(TYPE, type);
                        intent.putExtra(CIRCLEID, circleId);
                        intent.putExtra("name",loopName);
                        startActivity(intent);
                    }
                    break;

                case IMAGEID5:
                    if (flag) {
                        RecommendLoopData data = recommendLoopImagePathList.get(4);
                        String circleId = data.getcId();
                        String type = data.getcType();
                        String loopName = data.getcName();
                        intent.putExtra(TYPE, type);
                        intent.putExtra(CIRCLEID, circleId);
                        intent.putExtra("name",loopName);
                        startActivity(intent);
                    }
                    break;
            }

        }
    }

}
