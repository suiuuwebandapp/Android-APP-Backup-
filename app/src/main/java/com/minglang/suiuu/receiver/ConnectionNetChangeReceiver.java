package com.minglang.suiuu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/8/21 15:47
 * 修改人：Administrator
 * 修改时间：2015/8/21 15:47
 * 修改备注：
 */
public class ConnectionNetChangeReceiver extends BroadcastReceiver{
    private ConnectionChangeListener listener;
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            Log.i("suiuu", "网络不可用了");
            listener.conectionBreakOff(context);
            //改变背景或者 处理网络的全局变量
        }else {
            //改变背景或者 处理网络的全局变量
            listener.conectionResume(context);
        }
    }
    public interface ConnectionChangeListener {
        public void conectionBreakOff(Context b);
        public void conectionResume(Context b);

    }

    public void setConnectionChangeListener(ConnectionChangeListener listener) {
        this.listener = listener;
    }
}
