package com.minglang.suiuu.utils;

import android.util.Log;

/**
 * Log管理
 * <p/>
 * Created by Administrator on 2015/5/21.
 */
public class DeBugLog {

    public static boolean DeBug = true;

    public static void v(String TAG, String msg) {
        if (DeBug) {
            Log.v(TAG, msg);
        }
    }

    public static void v(String TAG, String msg, Throwable tr) {
        if (DeBug) {
            Log.v(TAG, msg, tr);
        }
    }

    public static void d(String TAG, String msg) {
        if (DeBug) {
            Log.d(TAG, msg);
        }
    }

    public static void d(String TAG, String msg, Throwable tr) {
        if (DeBug) {
            Log.d(TAG, msg, tr);
        }
    }

    public static void i(String TAG, String msg) {
        if (DeBug) {
            Log.i(TAG, msg);
        }
    }

    public static void i(String TAG, String msg, Throwable tr) {
        if (DeBug) {
            Log.i(TAG, msg, tr);
        }
    }

    public static void w(String TAG, String msg) {
        if (DeBug) {
            Log.w(TAG, msg);
        }
    }

    public static void w(String TAG, Throwable tr) {
        if (DeBug) {
            Log.w(TAG, tr);
        }
    }

    public static void w(String TAG, String msg, Throwable tr) {
        if (DeBug) {
            Log.d(TAG, msg, tr);
        }
    }

    public static void e(String TAG, String msg) {
        if (DeBug) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String TAG, String msg, Throwable tr) {
        if (DeBug) {
            Log.d(TAG, msg, tr);
        }
    }

    public static int println(int priority, String tag, String msg) {
        return Log.println(priority, tag, msg);
    }
}
