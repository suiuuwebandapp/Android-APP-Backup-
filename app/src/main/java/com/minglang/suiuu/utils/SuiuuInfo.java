package com.minglang.suiuu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.minglang.suiuu.entity.RequestData;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/4/25.
 */
public class SuiuuInfo implements Serializable {

    /**
     * 文件名1
     */
    private static final String PREFERENCE_NAME1 = "Suiuu_Third";

    /**
     * 第三方唯一ID
     */
    private static final String THIRD_PARTY_ID = "ThirdPartyID";

    /**
     * 昵称
     */
    private static final String NICKNAME = "NickName";

    /**
     * 性别
     */
    private static final String GENDER = "Gender";

    /**
     * 头像URL
     */
    private static final String HEAD_IMG = "HeadImage";

    private static final String TYPE = "Type";

    /**
     * 登陆成功后返回的验证信息
     */
    private static final String PREFERENCE_NAME2 = "SuiuuInfo";

    private static final String MESSAGE = "message";

    public static String ReadWxCode(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME1, Context.MODE_APPEND);
        return sp.getString("code", "");
    }

    public static void WriteWxCode(Context context, String code) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME1, Context.MODE_APPEND);
        sp.edit().putString("code", code).apply();
    }

    /**
     * 读取用户头像URL
     *
     * @param context 上下文对象
     * @return 用户头像URL
     */
    public static String ReadUserHeadImagePath(Context context) {
        if (context == null) {
            return "";
        }
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND);
        return sp.getString(HEAD_IMG, "");
    }

    /**
     * 读取用户性别
     *
     * @param context 上下文对象
     * @return 用户性别
     */
    public static String ReadUserGender(Context context) {
        if (context == null) {
            return "";
        }
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND);
        return sp.getString(GENDER, "");
    }

    /**
     * 读取保存的用户名
     *
     * @param context 上下文对象
     * @return 用户名
     */
    public static String ReadUserName(Context context) {
        if (context == null) {
            return "";
        }
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND);
        return sp.getString(NICKNAME, "");
    }

    /**
     * 保存用户基本信息
     *
     * @param context           上下文对象
     * @param userName          用户名
     * @param userGender        用户性别
     * @param userHeadImagePath 用户头像地址
     */
    public static void WriteUserBasicInfo(Context context, String userName, String userGender, String userHeadImagePath) {
        if (context == null) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(NICKNAME, userName);
        editor.putString(GENDER, userGender);
        editor.putString(HEAD_IMG, userHeadImagePath);
        editor.apply();
    }

    /**
     * 读取sign
     *
     * @param context 上下文对象
     * @return sign内容
     */
    public static String ReadUserSign(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND);
        return sp.getString("userSign", "");
    }

    /**
     * 保存sign
     *
     * @param context  上下文对象
     * @param usersign sign内容
     */
    public static void WriteUserSign(Context context, String usersign) {
        if (context == null) {
            return;
        }

        if (TextUtils.isEmpty(usersign)) {
            return;
        }

        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("userSign", usersign);
        editor.apply();
    }

    /**
     * 第三方相关信息保存到本地
     *
     * @param context     上下文对象
     * @param requestData 相关数据实体
     */
    public static void WriteInformation(Context context, RequestData requestData) {

        if (context == null) {
            return;
        }

        if (requestData == null) {
            return;
        }

        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME1, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(THIRD_PARTY_ID, requestData.getID());
        editor.putString(NICKNAME, requestData.getNickName());
        editor.putString(GENDER, requestData.getGender());
        editor.putString(HEAD_IMG, requestData.getImagePath());
        editor.putString(TYPE, requestData.getType());
        editor.apply();
    }

    /**
     * 从本地读取
     *
     * @param context 上下文对象
     * @return 相关数据实体
     */
    public static RequestData ReadInformation(Context context) {
        RequestData requestData = new RequestData();
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME1, Context.MODE_APPEND);
        requestData.setID(sp.getString(THIRD_PARTY_ID, ""));
        requestData.setNickName(sp.getString(NICKNAME, ""));
        requestData.setGender(sp.getString(GENDER, ""));
        requestData.setImagePath(sp.getString(HEAD_IMG, ""));
        requestData.setType(sp.getString(TYPE, ""));
        return requestData;
    }

    /**
     * 保存验证信息
     *
     * @param context 上下文对象
     * @param str     验证信息
     */
    public static void WriteVerification(Context context, String str) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(MESSAGE, str);
        editor.apply();
    }

    /**
     * 读取验证信息
     *
     * @param context 上下文对象
     * @return 验证信息
     */
    public static String ReadVerification(Context context) {
        String str;
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND);
        str = sp.getString(MESSAGE, "");
        return str;
    }

}
