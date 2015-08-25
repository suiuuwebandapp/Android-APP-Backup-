package com.minglang.suiuu.utils;

/**
 * Created by Administrator on 2015/8/25.
 * <p/>
 * 新版接口
 */
public class HttpNewServicePath {

    /**
     * 混淆码
     */
    public static String ConfusedCode = "sui@uu~9527&*";

    public static String key = "app_suiuu_sign";

    /**
     * 服务器地址
     */
    public static String RootPath = "http://api.suiuu.com/v1";

    /**
     * 获取Token
     */
    public static String getToken = RootPath + "/app-main/get-token";

    /**
     * 得到问答社区页面上问题列表的数据
     */
    public static String getMainProblemListPath = RootPath + "/app-qa/get-qa-list";

    /**
     * 得到邀请的用户列表
     */
    public static String getInvitationAnswerUserPath = RootPath + "/app-qa/get-invite-user";

    /**
     * 得到问题详情
     */
    public static String getProblemDetailsPath = RootPath + "/app-qa/get-qa-info";

    /**
     * 得到指定用户的问答
     */
    public static String getPersonalProblemDataPath = RootPath + "/app-qa/get-user-qa";

}