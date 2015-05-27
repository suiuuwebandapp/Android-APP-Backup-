/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.minglang.suiuu.chat.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.easemob.chat.EMChatConfig;
import com.easemob.cloud.CloudOperationCallback;
import com.easemob.cloud.HttpFileManager;
import com.easemob.util.ImageUtils;
import com.easemob.util.PathUtil;
import com.minglang.suiuu.R;
import com.minglang.suiuu.chat.photoview.PhotoView;
import com.minglang.suiuu.chat.photoview.PhotoViewAttacher;
import com.minglang.suiuu.chat.task.LoadLocalBigImgTask;
import com.minglang.suiuu.chat.utils.ImageCache;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 下载显示大图
 */
public class ShowBigImage extends BaseActivity {
    private ProgressDialog pd;
    private PhotoView image;
    private int default_res = R.drawable.default_image;
    private String localFilePath;
    private Bitmap bitmap;
    private boolean isDownloaded;
    private ProgressBar loadLocalPb;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_show_big_image);
        super.onCreate(savedInstanceState);
        bitmap = null;

        image = (PhotoView) findViewById(R.id.image);
        loadLocalPb = (ProgressBar) findViewById(R.id.pb_load_local);
        default_res = getIntent().getIntExtra("default_image", R.drawable.default_avatar);
        Uri uri = getIntent().getParcelableExtra("uri");
        String remotepath = getIntent().getExtras().getString("remotepath");
        boolean isHuanXin = getIntent().getExtras().getBoolean("isHuanXin");
        String secret = getIntent().getExtras().getString("secret");
        pd = new ProgressDialog(this,R.style.loading_dialog);
        pd.setCanceledOnTouchOutside(false);

        String path = getIntent().getExtras().getString("path");
        Log.i("suiuu", "show big image uri:" + uri + " remotepath:" + remotepath);
        //不是环信的 ,访问网络图片
        image.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                finish();
            }
        });
        if (!isHuanXin && !"".equals(remotepath)) {
            Log.i("suiuu", "来到这儿了吗");
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(ShowBigImage.this));
            options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_suiuu_image)
                    .showImageForEmptyUri(R.drawable.default_suiuu_image).showImageOnFail(R.drawable.default_suiuu_image)
                    .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                    .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();
            imageLoader.displayImage(remotepath, image, options, new SimpleImageLoadingListener(), new ImageLoadingProgressListener() {
                TextProgressDialog dialog = new TextProgressDialog(ShowBigImage.this,"");
                @Override
                public void onProgressUpdate(String s, View view, int i, int i2) {

                    dialog.showDialog();

                    if(i==i2) {
                        dialog.dismissDialog();
                    }


                }
            });
            return;
        }
        //传过路径显示图片
        if (path != null) {
            DisplayMetrics metrics1 = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics1);
            bitmap = ImageCache.getInstance().get(path);
            LoadLocalBigImgTask task = new LoadLocalBigImgTask(this, path, image, loadLocalPb, ImageUtils.SCALE_IMAGE_WIDTH,
                    ImageUtils.SCALE_IMAGE_HEIGHT);
            if (android.os.Build.VERSION.SDK_INT > 10) {
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                task.execute();
            }

        }
        //本地存在，直接显示本地的图片
        if (uri != null && new File(uri.getPath()).exists()) {
            System.err.println("showbigimage file exists. directly show it");
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            bitmap = ImageCache.getInstance().get(uri.getPath());
            if (bitmap == null) {
                LoadLocalBigImgTask task = new LoadLocalBigImgTask(this, uri.getPath(), image, loadLocalPb, ImageUtils.SCALE_IMAGE_WIDTH,
                        ImageUtils.SCALE_IMAGE_HEIGHT);
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    task.execute();
                }
            } else {
                image.setImageBitmap(bitmap);
            }
        } else if (remotepath != null) { //去服务器下载图片
            System.err.println("download remote image");
            Map<String, String> maps = new HashMap<String, String>();
            if (!TextUtils.isEmpty(secret)) {
                maps.put("share-secret", secret);
            }
            downloadImage(remotepath, maps);
        } else {
            image.setImageResource(default_res);
        }


    }


    /**
     * 通过远程URL，确定下本地下载后的localurl
     *
     * @param remoteUrl
     * @return
     */
    public String getLocalFilePath(String remoteUrl) {
        String localPath;
        if (remoteUrl.contains("/")) {
            localPath = PathUtil.getInstance().getImagePath().getAbsolutePath() + "/"
                    + remoteUrl.substring(remoteUrl.lastIndexOf("/") + 1);
        } else {
            localPath = PathUtil.getInstance().getImagePath().getAbsolutePath() + "/" + remoteUrl;
        }
        return localPath;
    }

    /**
     * 下载图片
     *
     * @param remoteFilePath
     */
    private void downloadImage(final String remoteFilePath, final Map<String, String> headers) {
        String str1 = getResources().getString(R.string.Download_the_pictures);
        pd.setMessage(str1);
        pd.show();
        localFilePath = getLocalFilePath(remoteFilePath);
        final HttpFileManager httpFileMgr = new HttpFileManager(this, EMChatConfig.getInstance().getStorageUrl());
        final CloudOperationCallback callback = new CloudOperationCallback() {
            public void onSuccess(String resultMsg) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DisplayMetrics metrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(metrics);
                        int screenWidth = metrics.widthPixels;
                        int screenHeight = metrics.heightPixels;

                        bitmap = ImageUtils.decodeScaleImage(localFilePath, screenWidth, screenHeight);
                        if (bitmap == null) {
                            image.setImageResource(default_res);
                        } else {
                            image.setImageBitmap(bitmap);
                            ImageCache.getInstance().put(localFilePath, bitmap);
                            isDownloaded = true;
                        }
                        if (pd != null) {
                            pd.dismiss();
                        }
                    }
                });
            }

            public void onError(String msg) {
                Log.e("###", "offline file transfer error:" + msg);
                File file = new File(localFilePath);
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        image.setImageResource(default_res);
                    }
                });
            }

            public void onProgress(final int progress) {
                Log.d("ease", "Progress: " + progress);
                final String str2 = getResources().getString(R.string.Download_the_pictures_new);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        pd.setMessage(str2 + progress + "%");
                    }
                });
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                httpFileMgr.downloadFile(remoteFilePath, localFilePath, headers, callback);
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        if (isDownloaded)
            setResult(RESULT_OK);
        finish();
    }
}
