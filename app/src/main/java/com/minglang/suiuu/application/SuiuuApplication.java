/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.minglang.suiuu.application;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.AccessControlList;
import com.alibaba.sdk.android.oss.model.ClientConfiguration;
import com.alibaba.sdk.android.oss.model.TokenGenerator;
import com.alibaba.sdk.android.oss.util.OSSToolKit;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.minglang.suiuu.chat.bean.User;
import com.minglang.suiuu.chat.chat.DemoHXSDKHelper;
import com.minglang.suiuu.chat.model.DefaultHXSDKModel;
import com.minglang.suiuu.utils.DeBugLog;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.Map;


public class SuiuuApplication extends Application {

    private static final String TAG = SuiuuApplication.class.getSimpleName();

    public static Context applicationContext;
    private static SuiuuApplication instance;
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * 当前用户nickname,为了苹果推送不是userid而是昵称
     */
    public static String currentUserNick = "";
    public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();
    public static OSSService ossService = OSSServiceProvider.getService();
    static final String accessKey = "LaKLZHyL2Dmy8Qqq"; // 测试代码没有考虑AK/SK的安全性
    static final String screctKey = "c7xPteQRqjV8nNB8xGFIZoFijzjDLX";

    private ImageLoader imageLoader;

    private static RefWatcher refWatcher;

    public static SuiuuApplication getInstance() {
        if (instance == null) {
            instance = new SuiuuApplication();
        }
        return instance;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;

        refWatcher = LeakCanary.install(this);

        initHuanXin();
        initAboatOSS();
        initImageLoad();
        //GlobalCrashHandler.getInstance().init(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        imageLoader.clearMemoryCache();
    }

    /**
     * 初始化环信
     */
    private void initHuanXin() {
        // 环信初始化SDK帮助函数 返回true如果正确初始化，否则false，如果返回为false，请在后续的调用中不要调用任何和环信相关的代码
        hxSDKHelper.onInit(this);
        DefaultHXSDKModel defaultHXSDKModel = new DefaultHXSDKModel(this);
        if (defaultHXSDKModel.isDebugMode()) {
            EMChat.getInstance().setDebugMode(false);
        }
    }

    /**
     * 初始化阿里OSS上传图片相关
     */
    public void initAboatOSS() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // 初始化设置
        ossService.setApplicationContext(this);
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
        long maxMemorySize = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        DeBugLog.i(TAG, "最大可用内存为:" + String.valueOf(maxMemorySize));
        imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);

        config.threadPoolSize(4);
        config.threadPriority(Thread.NORM_PRIORITY - 2);

        config.denyCacheImageMultipleSizesInMemory();

        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(Long.bitCount(maxMemorySize / 2));

        config.memoryCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);

        imageLoader.init(config.build());
    }

    public static RefWatcher getRefWatcher() {
        return refWatcher;
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

    /**
     * 退出登录,清空数据
     */
    public void logout(final EMCallBack emCallBack) {
        // 先调用sdk logout，在清理app中自己的数据
        hxSDKHelper.logout(emCallBack);
    }
}
