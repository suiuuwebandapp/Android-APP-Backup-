package com.minglang.suiuu.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2015/4/29.
 */
public class ScreenUtils {

    private Activity activity;

    private int screenWidth, screenHeight;

    public ScreenUtils(Activity activity) {
        this.activity = activity;
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

}
