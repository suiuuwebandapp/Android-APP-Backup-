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
     * 获取服务器时间
     */
    public static String getTime = RootPath + "/app-main/get-time";

    /**
     * 获取Token
     */
    public static String getToken = RootPath + "/app-main/get-token";

    /**
     * 发送国际电话区号和手机号到服务器
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:国际电话区号 areaCode、手机号 phone
     */
    public static String SendAreaCodeAndPhoneNumber = RootPath + "/app-login/get-phone-code";

    /**
     * 得到国际电话区号
     */
    public static String getAreaCodeDataPath = RootPath + "/app-login/get-country-list";

    /**
     * 应用用户注册地址
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:手机号 phone、密码 password、确认密码 cPassword、昵称 nick、验证码 validateCode
     */
    public static String Register4SuiuuPath = RootPath + "/app-login/app-register";

    /**
     * 应用自带登陆URL
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:用户名 username 、密码 password
     */
    public static String SelfLoginPath = RootPath + "/app-login/app-login";

    /**
     * 第三方登陆地址
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:openId（第三方唯一标识符）、nickname（昵称）、sex （男：1、女：2、保密/不详：3）
     * headImg 头像URL、type（1、QQ 2、微信 3、微博）
     * sign 信息混淆（sign = md5 openId+type+混淆码）
     */
    public static String ThirdPartyPath = RootPath + "/app-login/access-login";

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
    /**
     * 获取用户账户信息
     */
    public static String getUserAccountInfoPath = RootPath + "/app-account/account-list";


/*-----------------------随游相关-----------------------------*/
    /**
     * 得到普通用户未完成的订单
     */
    public static String getSuiuuDetailInfo = RootPath + "/app-travel/get-travel-info";


/*-----------------------旅图相关------------------------------------*/



}