package com.minglang.suiuu.base;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;

import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.SystemBarTintManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class BaseActivity extends FragmentActivity {

    private static final String TAG = "BaseActivity";

    public ImageLoader imageLoader = ImageLoader.getInstance();

    /**
     * 状态栏控制
     */
    public SystemBarTintManager systemBarTintManager;
    /**
     * 状态栏设置
     */
    public SystemBarTintManager.SystemBarConfig systemBarConfig;
    /**
     * 状态栏高度
     */
    public int statusBarHeight;
    /**
     * 虚拟按键高度
     */
    public int navigationBarHeight;
    /**
     * 虚拟按键宽度(?)
     */
    public int navigationBarWidth;
    /**
     * 是否有虚拟按键
     */
    public boolean isNavigationBar;
    /**
     * 检查系统版本(是否高于4.4)
     */
    public boolean isKITKAT;

    public DisplayMetrics dm;

    /**
     * 屏幕宽度
     */
    public int screenWidth;
    /**
     * 屏幕高度
     */
    public int screenHeight;

    public FragmentManager fm;

    public int sliderImageWidth;

    public String userSign;
    public String verification;

    private long maxMemorySize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        maxMemorySize = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        DeBugLog.i(TAG, "应用程序可用最大内存:" + maxMemorySize);

        initScreen();
        initImageLoad();
        initStatusBarControl();
        initSundry();

        BaseFragment baseFragment = new BaseFragment();
        baseFragment.setContext(this);

        baseFragment.setKITKAT(isKITKAT);
        baseFragment.setNavigationBar(isNavigationBar);
        baseFragment.setStatusBarHeight(statusBarHeight);
        baseFragment.setNavigationBarHeight(navigationBarHeight);
        baseFragment.setNavigationBarWidth(navigationBarWidth);

        baseFragment.setUserSign(userSign);
        baseFragment.setVerification(verification);

        baseFragment.setScreenHeight(screenHeight);
        baseFragment.setScreenWidth(screenWidth);
    }

    private void initImageLoad() {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(this);
        builder.memoryCacheSize((int) maxMemorySize / 4);
        ImageLoaderConfiguration configuration = builder.build();
        if (!imageLoader.isInited()) {
            imageLoader.init(configuration);
        }
    }

    private void initStatusBarControl() {
        systemBarTintManager = new SystemBarTintManager(this);
        systemBarConfig = systemBarTintManager.getConfig();

        statusBarHeight = systemBarConfig.getStatusBarHeight();
        navigationBarHeight = systemBarConfig.getNavigationBarHeight();
        navigationBarWidth = systemBarConfig.getNavigationBarWidth();

        isNavigationBar = systemBarConfig.hasNavigtionBar();

        systemBarTintManager.setStatusBarTintEnabled(true);
        systemBarTintManager.setNavigationBarTintEnabled(false);
        systemBarTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        DeBugLog.i(TAG, "虚拟按键高度:" + String.valueOf(navigationBarHeight));
        DeBugLog.i(TAG, "NavigationBarWidth:" + String.valueOf(navigationBarWidth));
        DeBugLog.i(TAG, "状态栏高度:" + String.valueOf(statusBarHeight));
    }

    private void initScreen() {
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        DeBugLog.i(TAG, "屏幕宽度:" + String.valueOf(screenWidth));
        DeBugLog.i(TAG, "屏幕高度:" + String.valueOf(screenHeight));
    }

    private void initSundry() {
        fm = getSupportFragmentManager();
        sliderImageWidth = BitmapFactory.decodeResource(getResources(), R.drawable.slider).getWidth();
        userSign = SuiuuInfo.ReadUserSign(this);
        verification = SuiuuInfo.ReadVerification(this);

        DeBugLog.i(TAG, "userSign:" + userSign);
        DeBugLog.i(TAG, "verification:" + verification);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
