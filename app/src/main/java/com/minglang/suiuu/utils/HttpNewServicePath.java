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

    /**
     * 添加标签
     */
    public static String addTagInterFacePath = RootPath + "/app-qa/add-tag";

    /**
     * 添加新问题
     */
    public static String addNewProblemInterFacePath = RootPath + "/app-qa/add-question";

    /**
     * 得到系统默认Tag
     */
    public static String getDefaultTagListPath = RootPath + "/app-qa/get-tag";

    /**
     * 关注问题
     */
    public static String getAttentionQuestionPath = RootPath + "/app-attention/attention-tp";

    /**
     * 关键字匹配Title
     */
    public static String serachProblemTitlePath = RootPath + "/app-qa/get-qa-title";

    /**
     * 得到普通用户的已完成的订单
     */
    public static String getGeneralUserCompletedOrderPath = RootPath + "/app-user-order/get-finish-order";

    /**
     * 得到普通用户未完成的订单
     */
    public static String getGeneralUserNotFinishOrderPath = RootPath + "/app-user-order/get-un-finish-order";
/*-----------------------随游相关-----------------------------*/
    /**
     * 得到普通用户未完成的订单
     */
    public static String getSuiuuDetailInfo = RootPath + "/app-travel/get-travel-info";

/*-----------------------旅图相关------------------------------------*/
}