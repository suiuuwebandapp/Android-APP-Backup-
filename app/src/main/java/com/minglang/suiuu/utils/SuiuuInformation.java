package com.minglang.suiuu.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.minglang.suiuu.entity.RequestData;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/4/25.
 */
public class SuiuuInformation implements Serializable {

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
    private static final String PREFERENCE_NAME2 = "Suiuu_Back";

    private static final String MESSAGE = "message";

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
        editor.commit();
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
        editor.commit();
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
