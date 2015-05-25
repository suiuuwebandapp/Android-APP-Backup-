package com.minglang.suiuu.base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.minglang.suiuu.utils.DeBugLog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    public ImageLoader imageLoader = ImageLoader.getInstance();

    public String userSign;
    public String verification;

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

    /**
     * 屏幕宽度
     */
    public int screenWidth;
    /**
     * 屏幕高度
     */
    public int screenHeight;

    public void setContext(Context context) {
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context.getApplicationContext()));
        }
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
        DeBugLog.i(TAG, "screenWidth:" + screenWidth);
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
        DeBugLog.i(TAG, "screenHeight:" + screenHeight);
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
        DeBugLog.i(TAG, "userSign:" + userSign);
    }

    public void setVerification(String verification) {
        this.verification = verification;
        DeBugLog.i(TAG, "verification:" + verification);
    }

    public void setStatusBarHeight(int statusBarHeight) {
        this.statusBarHeight = statusBarHeight;
        DeBugLog.i(TAG, "statusBarHeight:" + statusBarHeight);
    }

    public void setNavigationBarHeight(int navigationBarHeight) {
        this.navigationBarHeight = navigationBarHeight;
        DeBugLog.i(TAG, "navigationBarHeight:" + navigationBarHeight);
    }

    public void setNavigationBarWidth(int navigationBarWidth) {
        this.navigationBarWidth = navigationBarWidth;
        DeBugLog.i(TAG, "navigationBarWidth:" + navigationBarWidth);
    }

    public void setNavigationBar(boolean isNavigationBar) {
        this.isNavigationBar = isNavigationBar;
        DeBugLog.i(TAG, "isNavigationBar:" + Boolean.toString(isNavigationBar));
    }

    public void setKITKAT(boolean isKITKAT) {
        this.isKITKAT = isKITKAT;
        DeBugLog.i(TAG, "isKITKAT:" + Boolean.toString(isKITKAT));
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

}
