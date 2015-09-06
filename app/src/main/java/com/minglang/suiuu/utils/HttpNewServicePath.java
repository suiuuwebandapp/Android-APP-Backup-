package com.minglang.suiuu.utils;

/**
 * Created by Administrator on 2015/8/25.
 * <p/>
 * 新版接口
 */
public class HttpNewServicePath {

    //***************************************************基本*****************************************************\\

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
     * 得到选择国家的数据
     */
    public static String getCountryData = RootPath + "/app-travel/get-country";

    /**
     * 得到城市列表
     */
    public static String getCityListPath = RootPath + "/app-travel/get-city";

    //***************************************************注册登录相关*****************************************************\\

    /**
     * 发送国际电话区号和手机号到服务器
     */
    public static String SendAreaCodeAndPhoneNumber = RootPath + "/app-login/get-phone-code";

    /**
     * 得到国际电话区号
     */
    public static String getAreaCodeDataPath = RootPath + "/app-login/get-country-list";

    /**
     * 应用用户注册地址
     */
    public static String Register4SuiuuPath = RootPath + "/app-login/app-register";

    /**
     * 应用自带登陆URL
     */
    public static String SelfLoginPath = RootPath + "/app-login/app-login";

    /**
     * 第三方登陆地址
     */
    public static String ThirdPartyPath = RootPath + "/app-login/access-login";

    //***************************************************消息提醒*******************************************************\\

    /**
     * 得到旅途消息
     */
    public static String getTripGalleryMsgDataPath = RootPath + "/app-user-message/get-tp-messages";

    /**
     * 得到问答消息
     */
    public static String getQuestionAndAnswerMsgDataPath = RootPath + "/app-user-message/get-qa-messages";

    /**
     * 得到订单消息
     */
    public static String getOrderMsgDataPath = RootPath + "/app-user-message/get-order-messages";

    /**
     * 得到系统消息
     */
    public static String getSystemMsgDataPath = RootPath + "/app-user-message/get-sys-messages";

    //****************************************************个人主页******************************************************\\

    public static String getPersonalMainPagePath = RootPath + "/app-user-info/get-info";

    /**
     * 得到指定用户的随游
     */
    public static String getPersonalSuiuuDataPath = RootPath + "/app-travel/my-trip-list";

    //***************************************************问答社区相关*****************************************************\\

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
    public static String getAttentionQuestionPath = RootPath + "/app-attention/attention-qa";

    /**
     * 关键字匹配Title
     */
    public static String serachProblemTitlePath = RootPath + "/app-qa/get-qa-title";

    /**
     * 回答问题
     */
    public static String setAnswerToQuestionPath = RootPath + "/app-qa/add-answer";

    //********************************************侧栏-随友-随游查看************************************************************\\

    /**
     * 获取我发布的随游
     * <p/>
     * 请求方式:GET
     */
    public static String getMyPublishedSuiuuPath = RootPath + "/app-travel/my-trip-list";

    /**
     * 获取我参加的随游
     * <p/>
     * 请求方式:GET
     */
    public static String MyParticipateSuiuuPath = RootPath + "/app-travel/my-join-trip-list";

    /**
     * 得到随游的详细信息
     */
    public static String getSuiuuItemInfo = RootPath + "/app-travel/get-travel-info-json";

    /**
     * 新申请数据接口
     */
    public static String getNewApplyForDataPath = RootPath + "/app-travel/get-apply-list";

    /**
     * 移除随友
     */
    public static String removeSuiuuUserPath = RootPath + "/app-travel/remove-publisher";

    public static String ignoreDataPath = RootPath + "/app-travel/oppose-apply";

    public static String agreeDataPath = RootPath + "/app-travel/agree-apply";

    //********************************************侧栏-随友-随游订单*************************************************************\\

    /**
     * 获取新订单
     */
    public static String getNewOrderDataPath = RootPath + "/app-travel/get-un-confirm-order";

    /**
     * 已接到的订单
     */
    public static String getConfirmOrderDataPath = RootPath + "/app-travel/get-publisher-order-list";

    /**
     * 忽略订单
     */
    public static String setIgnoreOrderDataPath = RootPath + "/app-travel/publisher-ignore-order";

    /**
     * 接受订单
     */
    public static String setConfirmOrderDataPath = RootPath + "/app-travel/publisher-confirm-order";

    /**
     * 取消订单
     */
    public static String setCancelOrderDataPath = RootPath + "/app-travel/publisher-cancel-order";

    /**
     * 得到随游的订单详情
     */
    public static String getOrderDetailsDataPath = RootPath + "/app-travel/trip-order-info";

    //********************************************侧栏-随友-账户管理**************************************************************\\

    /**
     * 获取用户账户信息
     */
    public static String getUserAccountInfoPath = RootPath + "/app-account/account-list";

    //********************************************侧栏-普通用户-预定订单***********************************************************\\

    /**
     * 得到普通用户的已完成的订单
     */
    public static String getGeneralUserCompletedOrderPath = RootPath + "/app-user-order/get-finish-order";

    /**
     * 得到普通用户未完成的订单
     */
    public static String getGeneralUserNotFinishOrderPath = RootPath + "/app-user-order/get-un-finish-order";

    /**
     * 生成订单号
     */
    public static String createOrderNumber = RootPath + "/app-user-order/add-order";
    /**
     * 取消订单
     */
    public static String userCancelOrder = RootPath + "/app-user-order/cancel-order";
    /**
     * 删除订单
     */
    public static String userDeleteOrder = RootPath + "/app-user-order/delete-order";


    public static String getGeneralUserOrderDetailsPath = RootPath + "/app-travel/user-order-info";


    //***********************************************侧栏-普通用户-关注**************************************************************\\

    /**
     * 得到关注的旅图
     */
    public static String getAttentionTripPath = RootPath + "/app-attention/get-attention-tp";

    /**
     * 得到关注的问答
     */
    public static String getAttentionProblemInfoPath = RootPath + "/app-attention/get-attention-qa";

    /**
     * 得到关注的随游
     */
    public static String getAttentionSuiuuPath = RootPath + "/app-attention/get-collection-travel";

    //*************************************************账户相关**********************************************************************\\

    /**
     * 添加支付宝账户
     */
    public static String addAliPayUserInfo = RootPath + "/app-account/bind-alipay";

    /**
     * 添加微信账户
     */
    public static String addWeChatAUserInfo = RootPath + "/app-account/bind-wechat";

    /**
     * 得到绑定的账户列表
     */
    public static String getUserBindAccountListData = RootPath + "/app-account/get-user-account-list";

    //*************************************************temp*********************************************************\\

    /**
     * 圈子文章请求地址
     * 请求方式:POST
     * 请求参数: 文章id articleId、统一参数key{@link #key}
     */
    public static String LoopArticlePath = RootPath + "/circle/get-article-by-id";

    /**
     * 更新个人信息状态
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:sex 性别、nickname 昵称、birthday 生日、intro 短简介
     * info 详情介绍、countryId、国家id cityI、城市id、lon经度、lat 维度、profession 职业
     */
    public static String upDatePersonalStatus = RootPath + "/app-user-info/update-user-info";

    /*-----------------------随游相关-----------------------------*/

    /**
     * 得到随游的详细信息
     */
    public static String getSuiuuItemInfo = RootPath + "/app-travel/get-travel-info-json";

    /**
     * 得到普通用户未完成的订单
     */
    public static String getSuiuuDetailInfo = RootPath + "/app-travel/get-travel-info";
    /**
     * 得到随游列表
     */
    public static String getSuiuuList = RootPath + "/app-travel/get-travel-list";
    /**
     * 创建一条随游评论
     */
    public static String suiuuCreateComment = RootPath + "/app-travel/add-comment";
    /**
     * 得到随游高级搜索中的标签
     */
    public static String getSuiuuSearchTag = RootPath + "/app-travel/get-tag-list";

    /*-----------------------旅图相关------------------------------------*/

    /**
     * 发布旅图文章
     */
    public static String createTripGallery = RootPath + "/app-travel-picture/add-travel-picture";
    /**
     * 根据id获得旅图详情
     */
    public static String getTripGalleryDetailById = RootPath + "/app-travel-picture/get-info";

    /**
     * 得到旅图列表
     */
    public static String getTripGalleryList = RootPath + "/app-travel-picture/get-list";
    /**
     * 收藏旅图
     */
    public static String CollectionTripGalleryPath = RootPath + "/app-attention/attention-tp";
    /**
     * 取消收藏文章
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:需要收藏文章的attentionId
     */
    public static String CollectionArticleCancelPath = RootPath + "/app-attention/delete-attention";
    /**
     * 创建一条旅图评论
     */
    public static String articleCreateComment = RootPath + "/app-travel-picture/add-travel-picture-comment";

 /*-----------------------支付相关------------------------------------*/
    /**
     * 获得charge
     */
    public static String getCharge = RootPath + "/app-main/pay";

}