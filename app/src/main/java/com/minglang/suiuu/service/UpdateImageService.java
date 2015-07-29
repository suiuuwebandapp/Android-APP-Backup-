package com.minglang.suiuu.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.callback.SaveCallback;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.alibaba.sdk.android.oss.storage.OSSBucket;
import com.alibaba.sdk.android.oss.storage.OSSFile;
import com.google.gson.reflect.TypeToken;
import com.minglang.suiuu.utils.JsonUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：上传图片的服务
 * 创建人：Administrator
 * 创建时间：2015/7/29 15:22
 * 修改人：Administrator
 * 修改时间：2015/7/29 15:22
 * 修改备注：
 */
public class UpdateImageService extends Service {
    private List<String> imageList;
    private static OSSService ossService = OSSServiceProvider.getService();
    private static OSSBucket bucket = ossService.getOssBucket("suiuu");
    private int successNumber = 0;
    @Override
    public void onCreate() {
        Log.i("suiuu", "ExampleService-onCreate");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.i("suiuu", "ExampleService-onStart");
        imageList = new ArrayList<>();
        imageList = JsonUtils.getInstance().fromJSON(new TypeToken<ArrayList<String>>(){}.getType(),intent.getStringExtra("imageList"));
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        imageList = new ArrayList<>();
        imageList = JsonUtils.getInstance().fromJSON(new TypeToken<ArrayList<String>>(){}.getType(),intent.getStringExtra("imageList"));
        //执行文件的下载或者播放等操作
        Log.i("suiuu", "ExampleService-onStartCommand");
        /*
         * 这里返回状态有三个值，分别是:
         * 1、START_STICKY：当服务进程在运行时被杀死，系统将会把它置为started状态，但是不保存其传递的Intent对象，之后，系统会尝试重新创建服务;
         * 2、START_NOT_STICKY：当服务进程在运行时被杀死，并且没有新的Intent对象传递过来的话，系统将会把它置为started状态，
         *   但是系统不会重新创建服务，直到startService(Intent intent)方法再次被调用;
         * 3、START_REDELIVER_INTENT：当服务进程在运行时被杀死，它将会在隔一段时间后自动创建，并且最后一个传递的Intent对象将会再次传递过来。
         */
        Log.i("suiuu","图片集合大小"+imageList.size());
        for(String path : imageList) {
            updateImageDate(path);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("suiuu", "ExampleService-onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i("suiuu", "ExampleService-onDestroy");
        super.onDestroy();
    }
    private void updateImageDate(final String path) {
        Log.i("suiuu","上传的path="+path);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String type = path.substring(path.lastIndexOf("/"));
                String name = type.substring(type.lastIndexOf(".") + 1);
                String newPath = null;
//                try {
//                    newPath = CompressImageUtil.compressImage(path, name, 50);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                OSSFile bigFile = ossService.getOssFile(bucket, "suiuu_content" + type);
                try {
                    bigFile.setUploadFilePath(path,name);
                    bigFile.ResumableUploadInBackground(new SaveCallback() {
                        @Override
                        public void onSuccess(String objectKey) {
                            successNumber += 1;
                            if(successNumber == imageList.size()) {
                                stopSelf();
                            }
                        }
                        @Override
                        public void onProgress(String objectKey, int byteCount, int totalSize) {
                            Log.i("suiuu", "[onProgress] - current upload " + objectKey + " bytes: " + byteCount + " in total: " + totalSize);
                        }
                        @Override
                        public void onFailure(String objectKey, OSSException ossException) {
                            Log.i("suiuu", "[onFailure] - upload " + objectKey + " failed!\n" + ossException.toString());
                            ossException.printStackTrace();
//                    ossException.getException().printStackTrace();
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
