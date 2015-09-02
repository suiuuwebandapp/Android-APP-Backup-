package com.minglang.suiuu.utils;

/**
 *
 */
public class HttpServicePath {

    /**
     * 混淆码
     */
    public static String ConfusedCode = "sui@uu~9527&*";

    public static String key = "app_suiuu_sign";

    /**
     * 服务器地址
     * <p/>
     */
    public static String RootPath = "http://apptest.suiuu.com";

    public static String SharePath = "/circle/web-info?infoId=";

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
     * 圈子文章请求地址
     * 请求方式:POST
     * 请求参数: 文章id articleId、统一参数key{@link #key}
     */
    public static String LoopArticlePath = RootPath + "/circle/get-article-by-id";

    /**
     * 删除文章网络接口
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:文章ID articleId、统一参数key{@link #key}
     */
    public static String DeleteArticlePath = RootPath + "/circle/delete-article";

    /**
     * 获取关注的用户
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:页码 page(0 得到全部、1—— +∞ 对应页码数)
     */
    public static String AttentionUserPath = RootPath + "/attention/get-attention-user";

    /**
     * 发布旅图文章
     */
    public static String createTripGallery = RootPath + "/app-travel-picture/add-travel-picture";
    /**
     * 得到旅图列表
     */
    public static String getTripGalleryList = RootPath + "/app-travel-picture/get-list";
    /**
     * 根据id获得旅图详情
     */
    public static String getTripGalleryDetailById = RootPath + "/app-travel-picture/get-info";

    /**
     * 修改圈子文章
     */
    public static String updateLoop = RootPath + "/circle/up-date-article";

    /**
     * 收藏文章
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:需要收藏文章的id:articleId
     */
    @Deprecated
    public static String CollectionArticlePath = RootPath + "/attention/add-collection-article";
    /**
     * 收藏旅图
     */
    public static String CollectionTripGalleryPath = RootPath + "/attention/attention-tp";

    /**
     * 取消收藏文章
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:需要收藏文章的attentionId
     */
    public static String CollectionArticleCancelPath = RootPath + "/attention/delete-attention";

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
     * 得到随游列表
     */
    public static String getSuiuuList = RootPath + "/app-travel/get-travel-list";

    /**
     * 得到随游高级搜索中的标签
     */
    public static String getSuiuuSearchTag = RootPath + "/app-travel/get-tag-list";

    /**
     * 得到随游的详细信息
     */
    public static String getSuiuuItemInfo = RootPath + "/app-travel/get-travel-info-json";

    /**
     * 得到选择国家的数据
     */
    public static String getCountryData = RootPath + "/app-travel/get-country";

    /**
     * 创建一条评论
     */
    public static String articleCreateComment = RootPath + "/app-travel-picture/add-travel-picture-comment";

    /**
     * 创建一条评论
     */
    public static String suiuuCreateComment = RootPath + "/app-travel/add-comment";

    /**
     * 根据文章ID获得评论列表
     */
    public static String getCommentListByArticleId = RootPath + "/circle/get-comment-by-article-id";

    /**
     * 根据文章ID获得评论列表
     */
    public static String getCommentListByTripId = RootPath + "/app-travel/get-comment-list";

    /**
     * 圈子中添加点赞
     */
    public static String articleAddPraise = RootPath + "/attention/add-praise-circle-article";

    /**
     * 更新个人信息状态
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:sex 性别、nickname 昵称、birthday 生日、intro 短简介
     * info 详情介绍、countryId、国家id cityI、城市id、lon经度、lat 维度、profession 职业
     */
    public static String upDatePersonalStatus = RootPath + "/app-main/update-user-info";

    /**
     * 得到城市列表
     */
    public static String getCityListPath = RootPath + "/app-travel/get-city";

    /**
     * 获取我发布的随游
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:userSign
     */
    public static String MyPublishedSuiuuPath = RootPath + "/app-travel/my-trip-list";

    /**
     * 获取我参加的随游
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:userSign
     */
    public static String MyParticipateSuiuuPath = RootPath + "/app-travel/my-join-trip-list";

    /**
     * 生成订单号
     */
    public static String createOrderNumber = RootPath + "/app-travel/add-order";

    /**
     * 获得charge
     */
    public static String getCharge = RootPath + "/app-main/pay";

    /**
     * 获取新订单
     */
    public static String getNewOrderDataPath = RootPath + "/app-travel/get-un-confirm-order";

    /**
     * 已接到的订单
     */
    public static String getConfirmOrderDataPath = RootPath + "/app-travel/get-publisher-order-list";

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

    public static String getSuiuuUserInfoPath = RootPath + "/app-travel/get-publisher-info";

    /**
     * 得到问答社区页面上问题列表的数据
     */
    public static String getMainProblemListPath = RootPath + "/app-qa/get-qa-list";

    /**
     * 得到问题详情
     */
    public static String getProblemDetailsPath = RootPath + "/app-qa/get-qa-info";

    /**
     * 得到系统默认Tag
     */
    public static String getDefaultTagListPath = RootPath + "/app-qa/get-tag";

    /**
     * 得到关注的旅图
     */
    public static String getAttentionTripPath = RootPath + "/attention/get-attention-tp";

    /**
     * 得到关注的问答
     */
    public static String getAttentionProblemInfoPath = RootPath + "/attention/get-attention-qa";

    /**
     * 关注问题
     */
    public static String getAttentionQuestionPath = RootPath + "/attention/attention-qa";

    /**
     * 回答问题
     */
    public static String setAnswerToQuestionPath = RootPath + "/app-qa/add-answer";

    /**
     * 得到普通用户的已完成的订单
     */
    public static String getGeneralUserCompletedOrderPath = RootPath + "/app-travel/get-finish-order";

    /**
     * 得到普通用户未完成的订单
     */
    public static String getGeneralUserNotFinishOrderPath = RootPath + "/app-travel/get-un-finish-order";

    /**
     * 得到普通用户订单的详情
     */
    public static String getGeneralUserOrderDetailsPath = RootPath + "/app-travel/user-order-info";

    /**
     * 得到指定用户的随游
     */
    public static String getPersonalSuiuuDataPath = RootPath + "/app-travel/my-trip-list";

    /**
     * 得到指定用户的旅图
     */
    public static String getPersonalTripDataPath = RootPath + "/app-travel-picture/get-user-tp";

    /**
     * 得到指定用户的问答
     */
    public static String getPersonalProblemDataPath = RootPath + "/app-qa/get-user-qa";

    /**
     * 得到随游的订单详情
     */
    public static String getOrderDetailsDataPath = RootPath + "/app-travel/trip-order-info";

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

    /**
     * 得到邀请的用户列表
     */
    public static String getInvitationAnswerUserPath = RootPath + "/app-qa/get-invite-user";

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

}