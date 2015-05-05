package com.minglang.suiuu.fragment.main;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.LoopFragmentPagerAdapter;
import com.minglang.suiuu.adapter.LoopScrollPagerAdapter;
import com.minglang.suiuu.customview.AutoScrollViewPager;
import com.minglang.suiuu.fragment.loop.AreaFragment;
import com.minglang.suiuu.fragment.loop.ThemeFragment;
import com.minglang.suiuu.utils.SuiuuInformation;
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

    /**
     * 轮播ViewPager
     */
    private AutoScrollViewPager loopScrollViewPager;
    /**
     * 轮播图片View列表
     */
    private List<ImageView> imageList = new ArrayList<>();
    /**
     * 轮播图片地址列表
     */
    private List<String> imageIdList = new ArrayList<>();

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

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_loop, null);

        initScreenOrImageLoad();

        initView(rootView);

        ViewAction();

        return rootView;
    }


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
    }

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

        LayoutInflater inflater = LayoutInflater.from(getActivity());

        ImageView imageView1 = (ImageView) inflater.inflate(R.layout.image_view_match_parent, null);
        ImageView imageView2 = (ImageView) inflater.inflate(R.layout.image_view_match_parent, null);
        ImageView imageView3 = (ImageView) inflater.inflate(R.layout.image_view_match_parent, null);
        ImageView imageView4 = (ImageView) inflater.inflate(R.layout.image_view_match_parent, null);
        ImageView imageView5 = (ImageView) inflater.inflate(R.layout.image_view_match_parent, null);

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

        imageIdList.add(String.valueOf(R.drawable.scroll1));
        imageIdList.add(String.valueOf(R.drawable.scroll2));
        imageIdList.add(String.valueOf(R.drawable.scroll3));
        imageIdList.add(String.valueOf(R.drawable.scroll4));
        imageIdList.add(String.valueOf(R.drawable.scroll5));

        for (int i = 0; i < 5; i++) {
            loadImage(imageList.get(i), Integer.parseInt(imageIdList.get(i)));
        }

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

        String userSign = SuiuuInformation.ReadUserSign(getActivity());
        String verification = SuiuuInformation.ReadVerification(getActivity());

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

    private void loadImage(ImageView imageView, int imageId) {
        String uri = "drawable://" + imageId;
        imageLoader.displayImage(uri, imageView, displayImageOptions);
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

}
