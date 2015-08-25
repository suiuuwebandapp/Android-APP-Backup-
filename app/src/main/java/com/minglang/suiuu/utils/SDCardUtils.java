package com.minglang.suiuu.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * SD卡逻辑类
 * <p/>
 * Created by Administrator on 2015/5/26.
 */
public class SDCardUtils {

    private static final String TAG = SDCardUtils.class.getSimpleName();

    /**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
     *
     * @return
     */
    private static ArrayList<String> getDevMountList() {

        FileInputStream fileInputStream = null;
        try {
            File file = new File("/etc/vold.fstab");
            fileInputStream = new FileInputStream(file);

            int data;
            byte[] b = new byte[fileInputStream.available()];
            for (int i = 0; (data = fileInputStream.read()) != -1; i++) {
                b[i] = (byte) data;
            }

            String[] toSearch;
            toSearch = new String(b).split(" ");

            ArrayList<String> out = new ArrayList<>();

            if (toSearch.length > 0) {
                for (int i = 0; i < toSearch.length; i++) {
                    if (toSearch[i].contains("dev_mount")) {
                        if (new File(toSearch[i + 2]).exists()) {
                            out.add(toSearch[i + 2]);
                        }
                    }
                }
                return out;
            } else {
                return null;
            }

        } catch (Exception e) {
            DeBugLog.e(TAG, "error:" + e.getMessage());
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                DeBugLog.e(TAG, "I/O stream close error:" + e.getMessage());
            }
        }
    }

    /**
     * 获取扩展SD卡存储目录
     * <p/>
     * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
     * 否则：返回内置SD卡目录
     *
     * @return
     */
    public static String getExternalSdCardPath() {

        if (SDCardUtils.isMounted()) {
            return new File(Environment.getExternalStorageDirectory().getAbsolutePath()).getAbsolutePath();
        }

        String path = null;
        ArrayList<String> devMountList = getDevMountList();

        if (devMountList != null && devMountList.size() > 0) {
            for (String devMount : devMountList) {
                File file = new File(devMount);
                if (file.isDirectory() && file.canWrite()) {
                    path = file.getAbsolutePath();

                    String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault()).format(new Date());
                    File testWritable = new File(path, "test_" + timeStamp);

                    if (testWritable.mkdirs()) {
                        if (testWritable.delete()) {
                            DeBugLog.i(TAG, "Delete Success!");
                        }
                    } else {
                        path = null;
                    }
                }
            }
        }

        File sdCardFile;
        if (path != null) {
            sdCardFile = new File(path);
            return sdCardFile.getAbsolutePath();
        }

        return null;
    }

    public static boolean isMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

}