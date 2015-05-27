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
package com.minglang.suiuu.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.AccessControlList;
import com.alibaba.sdk.android.oss.model.ClientConfiguration;
import com.alibaba.sdk.android.oss.model.TokenGenerator;
import com.alibaba.sdk.android.oss.util.OSSToolKit;
import com.easemob.EMCallBack;
import com.minglang.suiuu.chat.bean.User;
import com.minglang.suiuu.chat.chat.DemoHXSDKHelper;
import com.minglang.suiuu.crash.GlobalCrashHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.Map;


public class SuiuuApplication extends Application {

    private static final String TAG = SuiuuApplication.class.getSimpleName();

    public static Context applicationContext;
    private static SuiuuApplication instance;
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * @description 所有已经启动的activity集合
     */
    private static ArrayList<Activity> activityList = new ArrayList<>();

    /**
     * 当前用户nickname,为了苹果推送不是userid而是昵称
     */
    public static String currentUserNick = "";
    public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();
    public static OSSService ossService = OSSServiceProvider.getService();
    static final String accessKey = "LaKLZHyL2Dmy8Qqq"; // 测试代码没有考虑AK/SK的安全性
    static final String screctKey = "c7xPteQRqjV8nNB8xGFIZoFijzjDLX";

    private long maxMemorySize;

    public static SuiuuApplication getInstance() {
        if (instance == null) {
            instance = new SuiuuApplication();
        }
        return instance;
    }

    /**
     * 获取内存中好友user list
     *
     * @return 好友user list
     */
    public Map<String, User> getContactList() {
        return hxSDKHelper.getContactList();
    }

    /**
     * 设置好友user list到内存中
     *
     * @param contactList 好友user list
     */
    public void setContactList(Map<String, User> contactList) {
        hxSDKHelper.setContactList(contactList);
    }

    /**
     * 获取当前登陆用户名
     *
     * @return 当前登陆用户名
     */
    public String getUserName() {
        return hxSDKHelper.getHXId();
    }

    /**
     * 获取密码
     *
     * @return 密码
     */
    public String getPassword() {
        return hxSDKHelper.getPassword();
    }

    /**
     * 设置用户名
     */
    public void setUserName(String username) {
        hxSDKHelper.setHXId(username);
    }

    /**
     * 设置密码 下面的实例代码 只是demo，实际的应用中需要加password 加密后存入 preference 环信sdk
     * 内部的自动登录需要的密码，已经加密存储了
     *
     * @param pwd 密码
     */
    public void setPassword(String pwd) {
        hxSDKHelper.setPassword(pwd);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;

        maxMemorySize = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        // 环信初始化SDK帮助函数 返回true如果正确初始化，否则false，如果返回为false，请在后续的调用中不要调用任何和环信相关的代码
        hxSDKHelper.onInit(applicationContext);
        buildAboatOSS();

        GlobalCrashHandler globalCrashHandler = GlobalCrashHandler.getInstance();
        globalCrashHandler.init(getApplicationContext());

        initImageLoad();
    }

    /**
     * 初始化阿里OSS上传图片相关
     */
    public void buildAboatOSS() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // 初始化设置
        ossService.setApplicationContext(this.getApplicationContext());
        ossService.setGlobalDefaultTokenGenerator(new TokenGenerator() { // 设置全局默认加签器
            @Override
            public String generateToken(String httpMethod, String md5, String type, String date,
                                        String ossHeaders, String resource) {

                String content = httpMethod + "\n" + md5 + "\n" + type + "\n" + date + "\n" + ossHeaders
                        + resource;

                return OSSToolKit.generateToken(accessKey, screctKey, content);
            }
        });

        ossService.setGlobalDefaultHostId("oss-cn-hongkong.aliyuncs.com");
        ossService.setCustomStandardTimeWithEpochSec(System.currentTimeMillis() / 1000);
        ossService.setGlobalDefaultACL(AccessControlList.PRIVATE); // 默认为private

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectTimeout(15 * 1000); // 设置全局网络连接超时时间，默认30s
        conf.setSocketTimeout(15 * 1000); // 设置全局socket超时时间，默认30s
        conf.setMaxConnections(50); // 设置全局最大并发网络链接数, 默认50
        ossService.setClientConfiguration(conf);
    }

    private void initImageLoad() {

        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;


        ImageLoader imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(getApplicationContext());

        config.threadPoolSize(4);
        config.threadPriority(Thread.NORM_PRIORITY - 2);

        config.denyCacheImageMultipleSizesInMemory();

        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(Long.bitCount(maxMemorySize / 2));

        config.memoryCache(new WeakMemoryCache());
        config.memoryCacheSize(Long.bitCount(maxMemorySize / 2));

        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.memoryCacheExtraOptions(screenWidth / 2, screenHeight / 2);

        config.writeDebugLogs();

        imageLoader.init(config.build());
    }

    /**
     * 退出登录,清空数据
     */
    public void logout(final EMCallBack emCallBack) {
        // 先调用sdk logout，在清理app中自己的数据
        hxSDKHelper.logout(emCallBack);
    }

    public void exit() {
        for (Activity act : activityList) {
            act.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void addActivity(Activity act) {
        activityList.add(act);
    }
}
