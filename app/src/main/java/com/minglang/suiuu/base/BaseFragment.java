package com.minglang.suiuu.base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class BaseFragment extends Fragment {

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
        imageLoader.clearMemoryCache();
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public void setStatusBarHeight(int statusBarHeight) {
        this.statusBarHeight = statusBarHeight;
    }

    public void setNavigationBarHeight(int navigationBarHeight) {
        this.navigationBarHeight = navigationBarHeight;
    }

    public void setNavigationBarWidth(int navigationBarWidth) {
        this.navigationBarWidth = navigationBarWidth;
    }

    public void setNavigationBar(boolean isNavigationBar) {
        this.isNavigationBar = isNavigationBar;
    }

    public void setKITKAT(boolean isKITKAT) {
        this.isKITKAT = isKITKAT;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

}
