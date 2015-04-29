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
     */
    private static String RootPath = "http://192.168.11.202";

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
    public static String LoopArticlePath = RootPath + "/circle/get-article-by-circle-id";

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
}
