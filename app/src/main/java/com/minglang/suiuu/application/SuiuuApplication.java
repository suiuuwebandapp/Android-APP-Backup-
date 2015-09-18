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

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.v4.content.LocalBroadcastManager;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.AccessControlList;
import com.alibaba.sdk.android.oss.model.ClientConfiguration;
import com.alibaba.sdk.android.oss.model.TokenGenerator;
import com.alibaba.sdk.android.oss.util.OSSToolKit;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.koushikdutta.WebSocketClient;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;

import org.apache.http.message.BasicNameValuePair;
import org.lasque.tusdk.core.TuSdkApplication;

import java.net.URI;
import java.util.Collections;
import java.util.List;

public class SuiuuApplication extends TuSdkApplication {

    private static final String TAG = SuiuuApplication.class.getSimpleName();

    private static final String TYPE = "type";
    private static final String USER_KEY = "user_key";
    private static final String LOGIN = "login";

    public static final String CONNECT = "connect";
    public static final String STRING_MESSAGE = "String_Message";
    public static final String BYTE_MESSAGE = "Byte_Message";
    public static final String DISCONNECT = "disconnect";
    public static final String DISCONNECT_CODE = "disconnect_code";
    public static final String ERROR = "error";

    private static SuiuuApplication instance;

    /**
     * 当前用户nickname,为了苹果推送不是userid而是昵称
     */
    public static OSSService ossService = OSSServiceProvider.getService();

    static final String accessKey = "LaKLZHyL2Dmy8Qqq"; // 测试代码没有考虑AK/SK的安全性
    static final String screctKey = "c7xPteQRqjV8nNB8xGFIZoFijzjDLX";

    private static WebSocketClient webSocketClient;

    private LocalBroadcastManager localBroadcastManager;

    public static SuiuuApplication getInstance() {
        if (instance == null) {
            instance = new SuiuuApplication();
        }
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);//开启多Dex文件支持
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "deprecation"})
    @Override
    public void onCreate() {
        super.onCreate();

        initApplication();
        initAboutOSS();

        //GlobalCrashHandler.getInstance().init(this);

        List<BasicNameValuePair> extraHeaders = Collections.singletonList(new BasicNameValuePair("", ""));

        try {
            webSocketClient = new WebSocketClient(URI.create(HttpNewServicePath.SocketPath), new InitListener(), extraHeaders);
        } catch (Exception e) {
            DeBugLog.e(TAG, "WebSocketClient初始化异常:" + e.getMessage());
        }

        try {
            webSocketClient.connect();
        } catch (Exception e) {
            DeBugLog.e(TAG, "WebSocketClient连接异常:" + e.getMessage());
        }

    }

    private void initApplication() {
        instance = this;
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        //fresco图片加载初始化
        Fresco.initialize(this);

        //初始化TuSDK
        this.initPreLoader(this, "745f61271fd7f7f7-00-04gxn1");

        // 设置输出状态
        this.setEnableLog(false);
    }

    /**
     * 初始化阿里OSS上传图片相关
     */
    public void initAboutOSS() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // 初始化设置
        ossService.setApplicationContext(this);
        ossService.setGlobalDefaultTokenGenerator(new TokenGenerator() { // 设置全局默认加签器
            @Override
            public String generateToken(String httpMethod, String md5, String type, String date,
                                        String ossHeaders, String resource) {
                String content = httpMethod + "\n" + md5 + "\n" + type + "\n" + date + "\n" + ossHeaders + resource;
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

    public static void setWebSocketClient(WebSocketClient webSocketClient) {
        SuiuuApplication.webSocketClient = webSocketClient;
    }

    public static WebSocketClient getWebSocketClient() {
        return webSocketClient;
    }

    private class InitListener implements WebSocketClient.Listener {

        @Override
        public void onConnect() {
            DeBugLog.i(TAG, "onConnect()");
            Intent intent = new Intent(CONNECT);
            intent.putExtra(CONNECT, "Socket连接已建立");
            localBroadcastManager.sendBroadcast(intent);
        }

        @Override
        public void onMessage(String message) {
            DeBugLog.i(TAG, "String Message:" + message);
            Intent intent = new Intent(STRING_MESSAGE);
            intent.putExtra(STRING_MESSAGE, message);
            localBroadcastManager.sendBroadcast(intent);
        }

        @Override
        public void onMessage(byte[] data) {
            String str = new String(data);
            DeBugLog.i(TAG, "byte[] Message:" + str);

            Intent intent = new Intent(BYTE_MESSAGE);
            intent.putExtra(BYTE_MESSAGE, str);
            localBroadcastManager.sendBroadcast(intent);
        }

        @Override
        public void onDisconnect(int code, String reason) {
            DeBugLog.i(TAG, "onDisconnect(),code:" + code + ",reason:" + reason);
            Intent intent = new Intent(DISCONNECT);
            intent.putExtra(DISCONNECT_CODE, code);
            intent.putExtra(DISCONNECT, reason);
            localBroadcastManager.sendBroadcast(intent);
        }

        @Override
        public void onError(Exception error) {
            DeBugLog.e(TAG, "onError(),error:" + error.getMessage());
            Intent intent = new Intent(ERROR);
            intent.putExtra(ERROR, error);
            localBroadcastManager.sendBroadcast(intent);
        }

    }

}