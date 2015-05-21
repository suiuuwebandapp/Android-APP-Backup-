package com.minglang.suiuu.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;

import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.SystemBarTintManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

public class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    private Context context;

    private Activity activity;

    public ImageLoader imageLoader = ImageLoader.getInstance();

    public String userSign;
    public String verification;

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

    public MaterialHeader header;

    public BaseFragment(){
        header = new MaterialHeader(getActivity());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
    }

    public void setContext(Context context) {
        this.context = context;

        initImageLoad();
        initAppInfo();

    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        initStatusBarControl();
        initScreen();
    }

    private void initImageLoad() {
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }
    }

    private void initAppInfo() {
        userSign = SuiuuInfo.ReadUserSign(context);
        verification = SuiuuInfo.ReadVerification(context);
    }

    private void initStatusBarControl() {
        systemBarTintManager = new SystemBarTintManager(activity);
        systemBarConfig = systemBarTintManager.getConfig();

        statusBarHeight = systemBarConfig.getStatusBarHeight();
        navigationBarHeight = systemBarConfig.getNavigationBarHeight();
        navigationBarWidth = systemBarConfig.getNavigationBarWidth();

        isNavigationBar = systemBarConfig.hasNavigtionBar();

        systemBarTintManager.setStatusBarTintEnabled(true);
        systemBarTintManager.setNavigationBarTintEnabled(false);
        systemBarTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    private void initScreen() {
        dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

}
