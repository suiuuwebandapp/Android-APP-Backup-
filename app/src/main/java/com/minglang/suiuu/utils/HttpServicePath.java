package com.minglang.suiuu.utils;

/**
 * 存放接口URL
 * <p/>
 * Created by Administrator on 2015/4/24.
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
     * http://test.suiuu.com
     * http://192.168.11.220
     */
    private static String RootPath = "http://192.168.11.220";

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
     * 圈子数据
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:type(1为主题页面，2为地区页面) 统一参数key{@link #key}
     */
    public static String LoopDataPath = RootPath + "/circle/get-circle";

    /**
     * 圈子内容地址
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:圈子ID circleId、统一参数key{@link #key}
     */
    public static String LoopDetailsPath = RootPath + "/circle/get-article-by-circle-id";

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
     * 获取关注的圈子
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:页码 page(0 得到全部、1—— +∞ 对应页码数)
     */
    public static String AttentionLoopPath = RootPath + "/attention/get-attention-circle";

    /**
     * 获取关注的用户
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:页码 page(0 得到全部、1—— +∞ 对应页码数)
     */
    public static String AttentionUserPath = RootPath + "/attention/get-attention-user";

    /**
     * 发布圈子文章
     */
    public static String createLoop = RootPath + "/circle/create-article";


    /**
     * 获取收藏的圈子
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:页码 page(0 得到全部、1—— +∞ 对应页码数)
     */
    public static String CollectionLoopPath = RootPath + "/attention/get-collection-article";

    /**
     * 添加关注(用户)
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:需要关注用户标示 userSign
     */
    public static String AddAttentionUserPath = RootPath + "/attention/add-attention-user";

    /**
     * 收藏文章
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:需要收藏文章的id:articleId
     */
    public static String CollectionArticlePath = RootPath + "/attention/add-collection-article";

    /**
     * 首页动态请求地址
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数：无
     */
    public static String MainDynamicPath = RootPath + "/attention/get-index-list";

    /**
     * 得到详细关注动态数据
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:页码 page(0 得到全部、1—— +∞ 对应页码数)
     */
    public static String AllAttentionDynamicPath = RootPath + "/attention/get-user-dynamic";

    /**
     * 发送国际电话区号和手机号到服务器
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:国际电话区号 areaCode、手机号 phone
     */
    public static String SendInternationalAreaCodeAndPhoneNumber = RootPath + "/app-login/get-phone-code";

    /**
     * 得到国际电话区号
     */
    public static String GetInternationalAreaCode = RootPath + "/app-login/get-country-list";

    /**
     * 应用用户注册地址
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:手机号 phone、密码 password、确认密码 cPassword、昵称 nick、验证码 validateCode
     */
    public static String Register4SuiuuPath = RootPath + "/app-login/app-register";

    /**
     * 得到粉丝数据
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:统一参数key{@link #key}
     */
    public static String FansInformationPath = RootPath + "/attention/get-fans";

    /**
     * 得到其他用户个人主页数据
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:用户标识 userSign、统一参数key{@link #key}
     */
    public static String OtherUserInformationPath = RootPath + "/circle/get-homepage-info";

    /**
     * 获取消息列表
     * <p/>
     * 请求方式:POST
     * <p/>
     * 请求参数:请求的消息类型 type、统一参数key{@link #key}
     * <p/>
     * const TYPE_AT = 1;//类型为@我的
     * <p/>
     * const TYPE_COMMENT=2;//类型为评论的
     * <p/>
     * const  TYPE_REPLY=3;//类型为回复
     * <p/>
     * const  TYPE_ATTENTION=4;//类型为关注
     */
    public static String GetMessageListPath = RootPath + "/attention/get-messages-remind";

    /**
     * 获取收藏的随游
     * <p/>
     * 请求方式:Post
     *
     * 请求参数:
     */
    public static String GetCollectionSuiuuPath = RootPath + "/attention/get-collection-travel";

    /**
     * 得到随游列表
     */
    public static String getSuiuuList = RootPath+ "/app-travel/get-travel-list";

    /**
     * 得到随游的详细信息
     */
    public static String getSuiuuItemInfo = RootPath+ "/app-travel/get-travel-info";  /**
     * 得到选择国家的数据
     */
    public static String getCountryData = RootPath+ "/app-travel/get-country";
}
