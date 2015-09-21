package com.minglang.suiuu.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SDCardUtils;
import com.minglang.suiuu.utils.SuiuuInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 全局异常捕获处理类
 * <p/>
 * Created by Administrator on 2015/5/26.
 */
public class GlobalCrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = GlobalCrashHandler.class.getSimpleName();

    private static GlobalCrashHandler instance;

    private Context context;

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    //用来存储设备信息和异常信息
    private Map<String, String> infoMap = new HashMap<>();

    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());

    private GlobalCrashHandler() {

    }

    public static synchronized GlobalCrashHandler getInstance() {
        if (instance == null) {
            instance = new GlobalCrashHandler();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex 异常信息
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        //收集设备参数信息
        collectDeviceInfo(context);
        //保存日志文件
        String fileName = saveCrashInfo2File(ex);
        if (!TextUtils.isEmpty(fileName)) {
            SuiuuInfo.WriteErrorLogName(context, TAG, fileName);
        }
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx 应用程序上下文对象
     */
    private void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infoMap.put("versionName", versionName);
                infoMap.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            L.e(TAG, "an error occur when collect package info", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infoMap.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                L.e(TAG, "an error occur when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex 异常
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infoMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();

        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        sb.append(result);

        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";

            if (SDCardUtils.isMounted()) {

                String path = SDCardUtils.getExternalSdCardPath() + "/Suiuu";
                File dir = new File(path);

                if (!dir.exists()) {//目录不存在
                    boolean flag = dir.mkdirs();
                    if (flag) {//创建路径成功
                        FileOutputStream fos = new FileOutputStream(new File(path, fileName));
                        fos.write(sb.toString().getBytes());
                        fos.close();
                    } else {//创建路径失败
                        L.e(TAG, "Folder creation failed! Unable to write log!");
                    }
                } else {
                    FileOutputStream fos = new FileOutputStream(new File(path, fileName));
                    fos.write(sb.toString().getBytes());
                    fos.close();
                }
            }
            return fileName;
        } catch (Exception e) {
            L.e(TAG, "an error occur while writing file...", e);
        }
        return null;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            L.i(TAG, "应用发生异常，退出!");
            L.e(TAG, "Throwable1:" + ex.getMessage());
            L.e(TAG, "Throwable2:" + ex.toString());
            L.e(TAG, "Throwable3:" + ex.getLocalizedMessage());

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
}
