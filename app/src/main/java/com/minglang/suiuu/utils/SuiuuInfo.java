package com.minglang.suiuu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.minglang.suiuu.entity.RequestData;
import com.minglang.suiuu.entity.UserBackData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 读取保存用户基本信息
 * <p/>
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
    private static final String HEAD_IMG = "headImg";

    /**
     * 本地头像地址
     */
    private static final String NATIVE_HEAD_IMG = "NativeHeadImage";

    private static final String TYPE = "Type";

    /**
     * 登陆成功后返回的验证信息
     */
    private static final String PREFERENCE_NAME2 = "SuiuuInfo";

    /**
     * 当前用户的位置信息
     */
    private static final String LocationInfo = "SuiuuLocationInfo";

    private static final String MESSAGE = "message";

    private static final String SIGNATURE = "Signature";

    /**
     * 居住地国家
     */
    private static final String DomicileCountry = "domicileCountry";

    /**
     * 居住地城市
     */
    private static final String DomicileCity = "domicileCity";

    public static void WriteAnyInfo(Context context, String key, String value) {
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND).edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 读取居住地城市信息
     *
     * @param context 上下文对象
     * @return 居住度城市信息
     */
    public static String ReadDomicileCity(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND).getString(DomicileCity, "");
    }

    /**
     * 读取居住地国家
     *
     * @param context 上下文对象
     * @return 居住地国家信息
     */
    public static String ReadDomicileCountry(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND).getString(DomicileCountry, "");
    }

    /**
     * 保存居住地信息
     *
     * @param context         上下文对象
     * @param domicileCountry 居住地国家
     * @param domicileCity    居住地城市
     */
    public static void WriteDomicileInfo(Context context, String domicileCountry, String domicileCity) {
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND).edit();
        editor.putString(DomicileCountry, domicileCountry);
        editor.putString(DomicileCity, domicileCity);
        editor.apply();

    }

    /**
     * 读取个性签名
     *
     * @param context 上下文对象
     * @return 个性签名内容
     */
    private static String ReadUserSignature(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND).getString(SIGNATURE, "");
    }

    /**
     * 保存个性签名
     *
     * @param context   上下文对象
     * @param Signature 个性签名
     */
    public static void WriteUserSignature(Context context, String Signature) {
        if (context == null) {
            return;
        }
        context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND).edit()
                .putString(SIGNATURE, Signature).apply();
    }

    /**
     * 读取本地的头像地址
     *
     * @param context 上下文对象
     * @return 本地的头像地址
     */
    public static String ReadNativeHeadImagePath(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND).getString(NATIVE_HEAD_IMG, "");
    }

    /**
     * 保存本地的头像地址
     *
     * @param context             上下文对象
     * @param nativeHeadImagePath 本地头像地址
     */
    public static void WriteNativeHeadImagePath(Context context, String nativeHeadImagePath) {
        if (context == null) {
            return;
        }
        context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND).edit()
                .putString(NATIVE_HEAD_IMG, nativeHeadImagePath).apply();
    }

    /**
     * 保存用户头像URL
     *
     * @param context       上下文对象
     * @param headImagePath 头像URL
     */
    public static void WriteUserHeadImagePath(Context context, String headImagePath) {
        if (context == null) {
            return;
        }
        context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND).edit()
                .putString(HEAD_IMG, headImagePath).apply();
    }

    /**
     * 读取用户数据
     *
     * @param context 上下文对象
     * @return 用户数据实体类
     */
    public static UserBackData ReadUserData(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND);
        UserBackData data = new UserBackData();
        data.setUserId(sp.getString("userId", ""));
        data.setNickname(sp.getString("nickname", ""));
        data.setPassword(sp.getString("password", ""));
        data.setEmail(sp.getString("email", ""));
        data.setEmail(sp.getString("email", ""));
        data.setPhone(sp.getString("phone", ""));
        data.setAreaCode(sp.getString("areaCode", ""));
        data.setSex(sp.getString("sex", ""));
        data.setBirthday(sp.getString("birthday", ""));
        data.setHeadImg(sp.getString("headImg", ""));
        data.setHobby(sp.getString("hobby", ""));
        data.setProfession(sp.getString("profession", ""));
        data.setSchool(sp.getString("school", ""));
        data.setIntro(sp.getString("intro", ""));
        data.setInfo(sp.getString("info", ""));
        data.setTravelCount(sp.getString("travelCount", ""));
        data.setRegisterIp(sp.getString("registerIp", ""));
        data.setRegisterTime(sp.getString("registerTime", ""));
        data.setLastLoginIp(sp.getString("lastLoginIp", ""));
        data.setLastLoginTime(sp.getString("lastLoginTime", ""));
        data.setUserSign(sp.getString("userSign", ""));
        data.setStatus(sp.getString("status", ""));
        data.setIsPublisher(sp.getString("isPublisher", ""));
        data.setCountryId(sp.getString("countryId", ""));
        data.setCityId(sp.getString("cityId", ""));
        data.setLon(sp.getString("lon", ""));
        data.setLat(sp.getString("lat", ""));
        return data;
    }

    /**
     * 保存用户数据
     *
     * @param context 上下文对象
     * @param data    用户数据
     */
    public static void WriteUserData(Context context, UserBackData data) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("userId", data.getUserId());
        editor.putString("nickname", data.getNickname());
        editor.putString("password", data.getPassword());
        editor.putString("email", data.getEmail());
        editor.putString("phone", data.getPhone());
        editor.putString("areaCode", data.getAreaCode());
        editor.putString("sex", data.getSex());
        editor.putString("birthday", data.getBirthday());
        editor.putString("headImg", data.getHeadImg());
        editor.putString("hobby", data.getHobby());
        editor.putString("profession", data.getProfession());
        editor.putString("school", data.getSchool());
        editor.putString("intro", data.getIntro());
        editor.putString("info", data.getInfo());
        editor.putString("travelCount", data.getTravelCount());
        editor.putString("registerIp", data.getRegisterIp());
        editor.putString("registerTime", data.getRegisterTime());
        editor.putString("lastLoginIp", data.getLastLoginIp());
        editor.putString("lastLoginTime", data.getLastLoginTime());
        editor.putString("userSign", data.getUserSign());
        editor.putString("status", data.getStatus());
        editor.putString("isPublisher", data.getIsPublisher());
        editor.putString("countryId", data.getCountryId());
        editor.putString("cityId", data.getCityId());
        editor.putString("lon", data.getLon());
        editor.putString("lat", data.getLat());
        editor.apply();
    }

    /**
     * 读取sign
     *
     * @param context 上下文对象
     * @return sign内容
     */
    public static String ReadUserSign(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND)
                .getString("userSign", "");
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
        context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND).edit()
                .putString("userSign", usersign).apply();
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
     * 从本地读取第三方相关信息
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
        context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND)
                .edit().putString(MESSAGE, str).apply();
    }

    /**
     * 读取验证信息
     *
     * @param context 上下文对象
     * @return 验证信息
     */
    public static String ReadVerification(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND)
                .getString(MESSAGE, "");
    }

    /**
     * 清除第三方相关信息
     *
     * @param context 上下文对象
     */
    public static void ClearSuiuuThird(Context context) {
        context.getSharedPreferences(PREFERENCE_NAME1, Context.MODE_APPEND)
                .edit().clear().apply();
    }

    /**
     * 清除用户身份信息
     *
     * @param context 上下文对象
     */
    public static void ClearSuiuuInfo(Context context) {
        context.getSharedPreferences(PREFERENCE_NAME2, Context.MODE_APPEND)
                .edit().clear().apply();
    }

    /**
     * 保存当前用户位置信息
     */
    public static void WriteUserLocation(Context context, double lat, double lng) {
        SharedPreferences sp = context.getSharedPreferences(LocationInfo, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("lat", String.valueOf(lat));
        editor.putString("lng", String.valueOf(lng));
        editor.apply();
    }

    /**
     * 读取当前用户的位置信息
     *
     * @param context
     * @return
     */
    public static Map ReadUserLocation(Context context) {
        SharedPreferences sp = context.getSharedPreferences(LocationInfo, Context.MODE_APPEND);
        Map<String, String> map = new HashMap<>();
        map.put("lat", sp.getString("lat", ""));
        map.put("lng", sp.getString("lng", ""));
        return map;
    }

}