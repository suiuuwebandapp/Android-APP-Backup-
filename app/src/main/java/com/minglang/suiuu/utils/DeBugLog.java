package com.minglang.suiuu.utils;

import android.util.Log;

/**
 * Log管理
 * <p/>
 * Created by Administrator on 2015/5/21.
 */
public class DeBugLog {

    public static boolean DeBug = false;

    public static void v(String TAG, String msg) {
        if (DeBug) {
            Log.v(TAG, msg);
        } else {
            Log.v(TAG, "No Log.v()");
        }
    }

    public static void v(String TAG, String msg, Throwable tr) {
        if (DeBug) {
            Log.v(TAG, msg, tr);
        } else {
            Log.v(TAG, "No Log.v()");
        }
    }


    public static void d(String TAG, String msg) {
        if (DeBug) {
            Log.d(TAG, msg);
        } else {
            Log.d(TAG, "No Log.d()");
        }
    }

    public static void d(String TAG, String msg, Throwable tr) {
        if (DeBug) {
            Log.d(TAG, msg, tr);
        } else {
            Log.d(TAG, "No Log.d()");
        }
    }

    public static void i(String TAG, String msg) {
        if (DeBug) {
            Log.i(TAG, msg);
        } else {
            Log.i(TAG, "No Log.i()");
        }
    }

    public static void i(String TAG, String msg, Throwable tr) {
        if (DeBug) {
            Log.i(TAG, msg, tr);
        } else {
            Log.i(TAG, "No Log.i()");
        }
    }

    public static void w(String TAG, String msg) {
        if (DeBug) {
            Log.w(TAG, msg);
        } else {
            Log.w(TAG, "No Log.w()");
        }
    }

    public static void w(String TAG, Throwable tr) {
        if (DeBug) {
            Log.w(TAG, tr);
        } else {
            Log.w(TAG, "No Log.w()");
        }
    }

    public static void w(String TAG, String msg, Throwable tr) {
        if (DeBug) {
            Log.d(TAG, msg, tr);
        } else {
            Log.d(TAG, "No Log.w()");
        }
    }

    public static void e(String TAG, String msg) {
        if (DeBug) {
            Log.e(TAG, msg);
        } else {
            Log.e(TAG, "No Log.w()");
        }
    }

    public static void e(String TAG, String msg, Throwable tr) {
        if (DeBug) {
            Log.d(TAG, msg, tr);
        } else {
            Log.d(TAG, "No Log.w()");
        }
    }

    public static String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }

    public static int println(int priority, String tag, String msg) {
        return Log.println(priority, tag, msg);
    }
}
