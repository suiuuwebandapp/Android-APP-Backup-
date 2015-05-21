package com.minglang.suiuu.utils;

import android.content.Context;
import android.content.Intent;

import com.minglang.suiuu.activity.LoginActivity;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：公共操作方法
 * 创建人：Administrator
 * 创建时间：2015/5/21 16:10
 * 修改人：Administrator
 * 修改时间：2015/5/21 16:10
 * 修改备注：
 */
public class AppUtils {
    public static void intentLogin(Context context) {
        Intent intent = new Intent (context, LoginActivity.class);
        context.startActivity(intent);
    }
}
