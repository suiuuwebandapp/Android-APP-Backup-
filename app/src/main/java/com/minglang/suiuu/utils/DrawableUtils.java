package com.minglang.suiuu.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2015/5/20.
 */
public class DrawableUtils {

    public static Drawable setBounds(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

}
