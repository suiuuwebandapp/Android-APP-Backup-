package com.minglang.suiuu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.minglang.suiuu.crash.GlobalCrashHandler;
import com.minglang.suiuu.entity.UserBack;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 读取保存用户基本信息
 * <p/>
 * Created by Administrator on 2015/4/25.
 */
public class SuiuuInfo implements Serializable {

    private static final String TAG = SuiuuInfo.class.getSimpleName();

    private static final String PREFERENCE_NAME = "SuiuuInfo";

    private static final String PREFERENCE_WE_CHAT_NAME = "SuiuuForWeChat";

    private static final String PREFERENCE_ALI_PAY_NAME = "SuiuuForAliPay";

    /**
     * 微信唯一ID
     */
    public static final String W_ONLY_ID = "ThirdOnlyID";

    /**
     * 微信用户昵称
     */
    public static final String W_NICK_NAME = "NickName";

    public static final String A_ACCOUNT_NAME = "AccountName";

    public static final String A_USER_NAME = "UserName";

    public static final String A_ACCOUNT_ID = "AlipayAccountId";

    public static final String W_ACCOUNT_ID = "WeChatAccountId";

    private static final String USER_ID = "userId";

    private static final String NICKNAME_ = "nickname";

    private static final String SUR_NAME = "surname";

    private static final String NAME = "name";

    private static final String PASSWORD = "password";

    private static final String EMAIL = "email";

    private static final String PHONE = "phone";

    private static final String AREA_CODE = "areaCode";

    private static final String SEX = "sex";

    private static final String BIRTHDAY = "birthday";

    private static final String HEAD_IMG = "headImg";

    private static final String HOBBY = "hobby";

    private static final String PROFESSION = "profession";

    private static final String SCHOOL = "school";

    private static final String QQ = "qq";

    private static final String WE_CHAT = "wechat";

    private static final String INTRO = "intro";

    private static final String INFO = "info";

    private static final String TRAVEL_COUNT = "travelCount";

    private static final String REGISTER_IP = "registerIp";

    private static final String REGISTER_TIME = "registerTime";

    private static final String LAST_LOGIN_IP = "lastLoginIp";

    private static final String LAST_LOGIN_TIME = "lastLoginTime";

    private static final String USER_SIGN = "userSign";

    private static final String STATUS = "status";

    private static final String IS_PUBLISHER = "isPublisher";

    private static final String COUNTRY_ID = "countryId";

    private static final String CITY_ID = "cityId";

    private static final String LON = "lon";

    private static final String LAT = "lat";

    private static final String BALANCE = "balance";

    private static final String VERSION = "version";

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

    private static final String APP_TIME_SIGN = "AppTimeSign";

    /**
     * 读取用户数据
     *
     * @param context 上下文对象
     * @return 用户数据实体类
     */
    public static UserBack.UserBackData ReadUserData(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND);
        UserBack back = new UserBack();
        UserBack.UserBackData data = back.new UserBackData();
        data.setUserId(sp.getString(USER_ID, ""));
        data.setNickname(sp.getString(NICKNAME_, ""));
        data.setSurname(sp.getString(SUR_NAME, ""));
        data.setName(sp.getString(NAME, ""));
        data.setPassword(sp.getString(PASSWORD, ""));
        data.setEmail(sp.getString(EMAIL, ""));
        data.setPhone(sp.getString(PHONE, ""));
        data.setAreaCode(sp.getString(AREA_CODE, ""));
        data.setSex(sp.getString(SEX, ""));
        data.setBirthday(sp.getString(BIRTHDAY, ""));
        data.setHeadImg(sp.getString(HEAD_IMG, ""));
        data.setHobby(sp.getString(HOBBY, ""));
        data.setProfession(sp.getString(PROFESSION, ""));
        data.setSchool(sp.getString(SCHOOL, ""));
        data.setQq(sp.getString(QQ, ""));
        data.setQq(sp.getString(WE_CHAT, ""));
        data.setIntro(sp.getString(INTRO, ""));
        data.setInfo(sp.getString(INFO, ""));
        data.setTravelCount(sp.getString(TRAVEL_COUNT, ""));
        data.setRegisterIp(sp.getString(REGISTER_IP, ""));
        data.setRegisterTime(sp.getString(REGISTER_TIME, ""));
        data.setLastLoginIp(sp.getString(LAST_LOGIN_IP, ""));
        data.setLastLoginTime(sp.getString(LAST_LOGIN_TIME, ""));
        data.setUserSign(sp.getString(USER_SIGN, ""));
        data.setStatus(sp.getString(STATUS, ""));
        data.setIsPublisher(sp.getString(IS_PUBLISHER, ""));
        data.setCountryId(sp.getString(COUNTRY_ID, ""));
        data.setCityId(sp.getString(CITY_ID, ""));
        data.setLon(sp.getString(LON, ""));
        data.setLat(sp.getString(LAT, ""));
        data.setBalance(sp.getString(BALANCE, ""));
        data.setVersion(sp.getString(VERSION, ""));
        return data;
    }

    /**
     * 保存用户数据
     *
     * @param context 上下文对象
     * @param data    用户数据
     */
    public static void WriteUserData(Context context, UserBack.UserBackData data) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_ID, data.getUserId());
        editor.putString(NICKNAME_, data.getNickname());
        editor.putString(SUR_NAME, data.getSurname());
        editor.putString(PASSWORD, data.getPassword());
        editor.putString(EMAIL, data.getEmail());
        editor.putString(PHONE, data.getPhone());
        editor.putString(AREA_CODE, data.getAreaCode());
        editor.putString(SEX, data.getSex());
        editor.putString(BIRTHDAY, data.getBirthday());
        editor.putString(HEAD_IMG, data.getHeadImg());
        editor.putString(HOBBY, data.getHobby());
        editor.putString(PROFESSION, data.getProfession());
        editor.putString(SCHOOL, data.getSchool());
        editor.putString(QQ, data.getQq());
        editor.putString(WE_CHAT, data.getWechat());
        editor.putString(INTRO, data.getIntro());
        editor.putString(INFO, data.getInfo());
        editor.putString(TRAVEL_COUNT, data.getTravelCount());
        editor.putString(REGISTER_IP, data.getRegisterIp());
        editor.putString(REGISTER_TIME, data.getRegisterTime());
        editor.putString(LAST_LOGIN_IP, data.getLastLoginIp());
        editor.putString(LAST_LOGIN_TIME, data.getLastLoginTime());
        editor.putString(USER_SIGN, data.getUserSign());
        editor.putString(STATUS, data.getStatus());
        editor.putString(IS_PUBLISHER, data.getIsPublisher());
        editor.putString(COUNTRY_ID, data.getCountryId());
        editor.putString(CITY_ID, data.getCityId());
        editor.putString(LON, data.getLon());
        editor.putString(LAT, data.getLat());
        editor.putString(BALANCE, data.getBalance());
        editor.putString(VERSION, data.getVersion());
        editor.apply();
    }

    //    /**
    //     * 写入余额
    //     *
    //     * @param context 上下文对象
    //     * @param value   余额值
    //     */

    //    private static void WriteBalance(Context context, String value) {
    //        if (context == null) {
    //            return;
    //        }
    //
    //        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).edit();
    //        editor.putString(BALANCE, value);
    //        editor.apply();
    //    }

    /**
     * @param context 上下文对象
     * @return 余额值
     */
    public static String ReadBalance(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).getString(BALANCE, "0");
    }

    /**
     * 写入token
     *
     * @param context 上下文对象
     * @param value   token
     */
    public static void WriteAppTimeSign(Context context, String value) {
        if (context == null) {
            return;
        }

        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).edit();
        editor.putString(APP_TIME_SIGN, value);
        editor.apply();
    }

    /**
     * 读取token
     *
     * @param context 上下文对象
     * @return token
     */
    public static String ReadAppTimeSign(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).getString(APP_TIME_SIGN, "");
    }

    /**
     * 写入错误日志名称
     *
     * @param context 上下文对象
     * @param key     异常捕获类类名
     * @param value   错误日志文件名
     */
    public static void WriteErrorLogName(Context context, String key, String value) {
        if (context == null) {
            return;
        }

        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 读取错误日志文件名
     *
     * @param context 上下文对象
     * @return
     */
    public static String ReadErrorLogName(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND)
                .getString(GlobalCrashHandler.class.getSimpleName(), "");
    }

    /**
     * 读取居住地城市信息
     *
     * @param context 上下文对象
     * @return 居住度城市信息
     */
    public static String ReadDomicileCity(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).getString(DomicileCity, "");
    }

    /**
     * 读取居住地国家
     *
     * @param context 上下文对象
     * @return 居住地国家信息
     */
    public static String ReadDomicileCountry(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).getString(DomicileCountry, "");
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

        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).edit();
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
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).getString(SIGNATURE, "");
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
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).edit()
                .putString(SIGNATURE, Signature).apply();
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
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).edit()
                .putString(HEAD_IMG, headImagePath).apply();
    }

    /**
     * 读取sign
     *
     * @param context 上下文对象
     * @return sign内容
     */
    public static String ReadUserSign(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND)
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
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).edit()
                .putString("userSign", usersign).apply();
    }

    /**
     * 保存验证信息
     *
     * @param context 上下文对象
     * @param str     验证信息
     */
    public static void WriteVerification(Context context, String str) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND)
                .edit().putString(MESSAGE, str).apply();
    }

    /**
     * 读取验证信息
     *
     * @param context 上下文对象
     * @return 验证信息
     */
    public static String ReadVerification(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND)
                .getString(MESSAGE, "");
    }

    /**
     * 清除用户身份信息
     *
     * @param context 上下文对象
     */
    public static void ClearSuiuuInfo(Context context) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_APPEND).edit().clear().apply();
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
     * @param context 上下文对象
     * @return 位置信息
     */
    public static Map ReadUserLocation(Context context) {
        SharedPreferences sp = context.getSharedPreferences(LocationInfo, Context.MODE_APPEND);
        Map<String, String> map = new HashMap<>();
        map.put("lat", sp.getString("lat", ""));
        map.put("lng", sp.getString("lng", ""));
        return map;
    }

    /**
     * 写入微信相关数据
     *
     * @param context  上下文对象
     * @param openId   唯一ID
     * @param nickName 昵称
     */
    public static void WriteWeChatInfo(Context context, String openId, String nickName) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_WE_CHAT_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(W_ONLY_ID, openId);
        editor.putString(W_NICK_NAME, nickName);
        editor.apply();
    }

    /**
     * 读取微信相关数据
     *
     * @param context 上下文对象
     * @return 相关数据集合
     */
    public static Map<String, String> ReadWeChatInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_WE_CHAT_NAME, Context.MODE_APPEND);
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put(W_ONLY_ID, sp.getString(W_ONLY_ID, ""));
        infoMap.put(W_NICK_NAME, sp.getString(W_NICK_NAME, ""));
        return infoMap;
    }

    /**
     * 保存支付宝的相关数据
     *
     * @param context     上下文对象
     * @param accountName 支付宝账户名
     * @param name        用户名
     */
    public static void WriteAliPayInfo(Context context, String accountName, String name) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_ALI_PAY_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(A_ACCOUNT_NAME, accountName);
        editor.putString(A_USER_NAME, name);
        editor.apply();
    }

    /**
     * 读取支付宝相关信息
     *
     * @param context 上下文对象
     * @return 数据集合
     */
    public static Map<String, String> ReadAliPayInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_ALI_PAY_NAME, Context.MODE_APPEND);
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put(A_ACCOUNT_NAME, sp.getString(A_ACCOUNT_NAME, ""));
        infoMap.put(A_USER_NAME, sp.getString(A_USER_NAME, ""));
        return infoMap;
    }

    /**
     * 写入支付宝的accountId
     *
     * @param context   上下文对象
     * @param accountId 支付宝的accountId
     */
    public static void WriteAliPayAccountId(Context context, String accountId) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_ALI_PAY_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(A_ACCOUNT_ID, accountId);
        editor.apply();
    }

    /**
     * 读取支付宝的accountId
     *
     * @param context 上下文对象
     * @return 支付宝的accountId
     */
    public static String ReadAliPayAccountId(Context context) {
        return context.getSharedPreferences(PREFERENCE_ALI_PAY_NAME, Context.MODE_APPEND).getString(A_ACCOUNT_ID, "");
    }

    /**
     * 写入微信的AccountId
     *
     * @param context   上下文对象
     * @param accountId 微信的accountId
     */
    public static void WriteWeChatAccountId(Context context, String accountId) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_ALI_PAY_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(W_ACCOUNT_ID, accountId);
        editor.apply();
    }

    /**
     * 读取微信的AccountId
     *
     * @param context 上下文对象
     * @return 微信的accountId
     */
    public static String ReadWeChatAccountId(Context context) {
        return context.getSharedPreferences(PREFERENCE_WE_CHAT_NAME, Context.MODE_APPEND).getString(W_ACCOUNT_ID, "");
    }

    /**
     * 清除微信相关数据
     *
     * @param context 上下文对象
     */
    public static void ClearAliPayInfo(Context context) {
        try {
            context.getSharedPreferences(PREFERENCE_ALI_PAY_NAME, Context.MODE_APPEND).edit().clear().apply();
        } catch (Exception e) {
            DeBugLog.e(TAG, PREFERENCE_ALI_PAY_NAME + "不存在！" + e.getMessage());
        }
    }

    /**
     * 清除微信相关数据
     *
     * @param context 上下文对象
     */
    public static void ClearWeChatInfo(Context context) {
        try {
            context.getSharedPreferences(PREFERENCE_WE_CHAT_NAME, Context.MODE_APPEND).edit().clear().apply();
        } catch (Exception e) {
            DeBugLog.e(TAG, PREFERENCE_WE_CHAT_NAME + "不存在！" + e.getMessage());
        }
    }

}