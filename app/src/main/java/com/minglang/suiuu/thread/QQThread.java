package com.minglang.suiuu.thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.minglang.suiuu.entity.QQInfo;

import org.json.JSONObject;

import java.lang.Object;


/**
 * Created by Administrator on 2015/4/17.
 * <p/>
 * 新建线程类，该类用于获取QQ登录成功后返回的信息
 */
public class QQThread extends Thread {

    private static final String TAG = QQThread.class.getSimpleName();

    private Handler handler;

    private Object object;

    private JSONObject jsonObject;

    public QQThread(Handler handler, Object object) {
        this.handler = handler;
        this.object = object;
        jsonObject = (JSONObject) this.object;
    }

    @Override
    public void run() {

        if (jsonObject != null && jsonObject.length() > 0) {
            QQInfo info = new QQInfo();

            String nickName;
            String imagePath;
            String gender;

            try {
                nickName = jsonObject.getString("nickname");
                imagePath = jsonObject.getString("figureurl_qq_2");
                gender = jsonObject.getString("gender");

                info.setNickName(nickName);
                info.setImagePath(imagePath);
                info.setGender(gender);

                Message msg = new Message();
                msg.what = 1;
                msg.obj = info;

                handler.sendMessage(msg);

            } catch (Exception e) {
                Log.i(TAG, e.getMessage());
            }
        }

    }
}
