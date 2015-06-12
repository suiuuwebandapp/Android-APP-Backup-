package com.minglang.suiuu.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenUtils {

    private int screenWidth, screenHeight;

    public ScreenUtils(Activity activity) {
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
