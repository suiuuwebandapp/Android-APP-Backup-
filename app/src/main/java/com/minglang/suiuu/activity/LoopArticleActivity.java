package com.minglang.suiuu.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.ArticleCommentAdapter;
import com.minglang.suiuu.adapter.LoopArticleImageAdapter;
import com.minglang.suiuu.adapter.showPicDescriptionAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.customview.LinearLayoutForListView;
import com.minglang.suiuu.customview.NoScrollBarGridView;
import com.minglang.suiuu.entity.DeleteArticle;
import com.minglang.suiuu.entity.LoopArticle;
import com.minglang.suiuu.entity.LoopArticleCommentList;
import com.minglang.suiuu.entity.LoopArticleData;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.DrawableUtils;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.Utils;
import com.minglang.suiuu.utils.qq.TencentConstant;
import com.minglang.suiuu.utils.wechat.WeChatConstant;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 具体某个地区/主题下的某个帖子
 * <p/>
 * 网络部分未完成
 */
@SuppressWarnings("deprecation")
public class LoopArticleActivity extends BaseActivity {

    private static final String TAG = LoopArticleActivity.class.getSimpleName();

    private static final String ARTICLEID = "articleId";

    private String OtherTAG;

    /**
     * 封面图片
     */
    @Bind(R.id.loop_article_cover_image)
    ImageView coverImage;

    /**
     * 点赞
     */
    @Bind(R.id.loop_article_praise)
    TextView praise;

    /**
     * 收藏
     */
    @Bind(R.id.loop_article_collection)
    TextView collection;

    /**
     * 位置信息
     */
    @Bind(R.id.loop_article_location)
    TextView locationName;

    /**
     * 用户头像
     */
    @Bind(R.id.loop_article_user_head_image)
    CircleImageView headImage;

    /**
     * 用户名
     */
    @Bind(R.id.loop_article_user_name)
    TextView userName;

    /**
     * 用户位置
     */
    @Bind(R.id.loop_article_user_location)
    TextView userLocation;

    /**
     * 修改
     */
    @Bind(R.id.loop_article_editor)
    TextView editor;

    /**
     * 删除
     */
    @Bind(R.id.loop_article_delete)
    TextView delete;

    /**
     * 文章内容
     */
    @Bind(R.id.loop_article_content)
    TextView articleContent;

    /**
     * 展示图片的GridView
     */
    @Bind(R.id.loop_article_grid)
    NoScrollBarGridView noScrollBarGridView;

    /**
     * 展示评论
     */
    @Bind(R.id.loop_article_show_comment)
    TextView showComments;

    /**
     * 分享
     */
    @Bind(R.id.loop_article_share)
    TextView share;

    /**
     * 评论
     */
    @Bind(R.id.loop_article_comments)
    TextView comments;

    private String articleId;

    /**
     * 文章主题内容数据集合
     */
    private LoopArticleData loopArticleData;

    private DisplayImageOptions options1;
    private DisplayImageOptions options2;

    private ProgressDialog progressDialog;

    private List<String> imageList;

    @Bind(R.id.loop_article_showForAsk)
    RelativeLayout rl_showForAsk;

    @Bind(R.id.loop_article_showForTakePhoto)
    RelativeLayout rl_showForTakePhoto;

    @Bind(R.id.loop_article_listview)
    ListView loop_article_listview;

    @Bind(R.id.loop_article_comment_list)
    LinearLayoutForListView commentListView;

    @Bind(R.id.loop_article_back)
    ImageView loop_article_back;

    private boolean isClickComment;

    private String attentionId;

    /**
     * 点赞Id
     */
    private String praiseId;

    @Bind(R.id.loop_article_scrollView)
    ScrollView loop_article_scrollView;

    //判断取消的是点赞还是收藏
    private boolean isPraise;

    private UMSocialService mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_article);

        articleId = getIntent().getStringExtra(ARTICLEID);
        OtherTAG = getIntent().getStringExtra("TAG");

        initImageLoader();

        initView();
        initShareData();
        ViewAction();
        getInternetServiceData();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(getResources().getString(R.string.load_wait));
    }

    /**
     * 初始化图片加载器
     */
    private void initImageLoader() {
        options1 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.loading).showImageOnFail(R.drawable.loading_error)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

        options2 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image).showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(praiseId)) {
                    //为空添加点赞
                    addPraiseRequest();
                } else {
                    isPraise = true;
                    collectionArticleCancel(praiseId);
                }
            }
        });

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(attentionId)) {
                    collectionArticle();
                } else {
                    isPraise = false;
                    collectionArticleCancel(attentionId);
                }
            }
        });

        editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoopArticleActivity.this, EasyTackPhotoActivity.class);
                intent.putExtra("articleDetail", JsonUtils.getInstance().toJSON(loopArticleData));
                startActivity(intent);
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(LoopArticleActivity.this).setTitle(getResources().
                        getString(R.string.sure_delete))
                        .setNegativeButton(getResources().getString(android.R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteArticle();
                                    }
                                })
                        .setPositiveButton(getResources().getString(android.R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(LoopArticleActivity.this,
                                                "已取消删除", Toast.LENGTH_SHORT).show();
                                    }
                                }).create().show();
            }
        });

        noScrollBarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showPic = new Intent(LoopArticleActivity.this, ShowBigImage.class);
                showPic.putExtra("remotepath", imageList.get(position));
                showPic.putExtra("isHuanXin", false);
                startActivity(showPic);
            }
        });

        //显示评论列表
        showComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClickComment) {
                    commentListView.setVisibility(View.GONE);
                    loop_article_scrollView.smoothScrollTo(0, 0);
                    isClickComment = false;
                } else {
                    List<LoopArticleCommentList> commentList = loopArticleData.getCommentList();
                    if (commentList != null && commentList.size() > 0) {
                        commentListView.setVisibility(View.VISIBLE);
                        commentListView.setAdapter(new ArticleCommentAdapter(LoopArticleActivity.this, commentList));
                        loop_article_scrollView.smoothScrollBy(0, 100);
                    } else {
                        Toast.makeText(LoopArticleActivity.this, R.string.ThisArticNoComment, Toast.LENGTH_SHORT).show();
                    }
                    isClickComment = true;
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.openShare(LoopArticleActivity.this, new SocializeListeners.SnsPostListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, SocializeEntity socializeEntity) {

                    }
                });
            }
        });

        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoopArticleActivity.this, CommentsActivity.class);
                intent.putExtra("articleId", loopArticleData.getArticleId());
                startActivity(intent);
            }
        });

        loop_article_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoopArticleActivity.this, MainActivity.class));
                finish();
            }
        });
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 点赞接口
     */
    private void addPraiseRequest() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("articleId", articleId);
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.articleAddPraise, new addPraiseRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 收藏文章
     */
    private void collectionArticle() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("articleId", articleId);
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.CollectionArticlePath, new CollectionArticleRequestCallback());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 收藏文章取消
     */
    private void collectionArticleCancel(String cancelId) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("attentionId", cancelId);
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.CollectionArticleCancelPath, new CollectionArticleCancelRequestCallback());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 删除文章
     */
    private void deleteArticle() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        RequestParams params = new RequestParams();
        params.addBodyParameter("articleId", articleId);
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.DeleteArticlePath, new DeleteArticleRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 从网络获取数据
     */
    private void getInternetServiceData() {
        if (progressDialog != null) {
            progressDialog.show();
        }
        RequestParams params = new RequestParams();
        params.addBodyParameter("articleId", articleId);
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.LoopArticlePath, new LoopArticleRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();

    }

    /**
     * 将显示文章内容
     */
    private void setForArticleContent() {
        rl_showForTakePhoto.setVisibility(View.GONE);
        rl_showForAsk.setVisibility(View.VISIBLE);
        locationName.setText(loopArticleData.getaTitle());
        imageLoader.displayImage(loopArticleData.getHeadImg(), headImage, options2);
        userName.setText(loopArticleData.getNickname());

        String address = loopArticleData.getaAddr();
        if (!TextUtils.isEmpty(address)) {
            userLocation.setText(address);
        } else {
            userLocation.setText("");
        }

        attentionId = loopArticleData.getAttentionId();
        if (!TextUtils.isEmpty(attentionId)) {
            collection.setTextColor(getResources().getColor(R.color.text_select_true));
            collection.setCompoundDrawables(DrawableUtils.setBounds(getResources().
                    getDrawable(R.drawable.icon_collection_yellow)), null, null, null);

        }

        String imageListPath = loopArticleData.getaImgList();
        if (!TextUtils.isEmpty(imageListPath)) {
            imageList = JsonUtils.getInstance().fromJSON(new TypeToken<ArrayList<String>>() {
            }.getType(), imageListPath);
            if (imageList != null) {
                if (imageList.size() >= 1) {
                    imageLoader.displayImage(imageList.get(0), coverImage, options1);
                }

                String content = loopArticleData.getaContent();
                String type = loopArticleData.getaType();
                if (!TextUtils.isEmpty(type)) {
                    if ("1".equals(type)) {
                        rl_showForTakePhoto.setVisibility(View.VISIBLE);
                        rl_showForAsk.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(content)) {
                            List<String> contentList = JsonUtils.getInstance().fromJSON(new TypeToken<ArrayList<String>>() {
                            }.getType(), content);
                            if (contentList != null) {
                                loop_article_listview.setAdapter(new showPicDescriptionAdapter(this, imageList, contentList));
                            } else {
                                DeBugLog.e(TAG, "contentList==null!");
                            }
                            Utils.setListViewHeightBasedOnChildren(loop_article_listview);
                        } else {
                            DeBugLog.e(TAG, "content==null!***");
                        }
                    } else {
                        if (!TextUtils.isEmpty(content)) {
                            articleContent.setText(content);
                        } else {
                            articleContent.setText("");
                            DeBugLog.e(TAG, "***content==null!");
                        }
                        //图片适配器
                        LoopArticleImageAdapter imageAdapter = new LoopArticleImageAdapter(this, imageList);
                        noScrollBarGridView.setAdapter(imageAdapter);
                    }
                } else {
                    DeBugLog.e(TAG, "type==null!");
                }
            } else {
                DeBugLog.e(TAG, "imageList==null!");
            }
        } else {
            DeBugLog.e(TAG, "imageListPath==null!");
        }
    }

    /**
     * 初始化分享
     */
    private void initShareData() {
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        SocializeConfig config = mController.getConfig();
        config.removePlatform(SHARE_MEDIA.TENCENT);
        config.removePlatform(SHARE_MEDIA.QZONE);

        // 添加新浪和qq空间的SSO授权支持
        config.setSsoHandler(new SinaSsoHandler());
        //*******************************

        //添加微信
        UMWXHandler wxHandler = new UMWXHandler(LoopArticleActivity.this, WeChatConstant.APP_ID, WeChatConstant.APPSECRET);
        wxHandler.addToSocialSDK();
        //*******************************

        //微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(LoopArticleActivity.this, WeChatConstant.APP_ID, WeChatConstant.APPSECRET);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        // 设置微信朋友圈分享内容
//        CircleShareContent circleMedia = new CircleShareContent();
//        circleMedia.setShareContent("");
//        circleMedia.setTitle(""); //设置朋友圈title
//        circleMedia.setShareImage(mUMImgBitmap);
//        circleMedia.setTargetUrl("");//设置分享内容跳转URL
//        mController.setShareMedia(circleMedia);

        //*******************************

        //微信好友分享
//        WeiXinShareContent weiXinContent = new WeiXinShareContent();
//        weiXinContent.setShareContent("");//分享文字
//        weiXinContent.setTitle(""); //设置title
//        weiXinContent.setTargetUrl("");//设置分享内容跳转URL
//        weiXinContent.setShareImage(UMImgBitmap);//设置分享图片
//        mController.setShareMedia(weiXinContent);

        //*******************************

        //添加QQ支持
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoopArticleActivity.this,
                TencentConstant.APP_ID, TencentConstant.APP_KEY);
        qqSsoHandler.addToSocialSDK();

        //  QQ要分享的内容
        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent(HttpServicePath.RootPath + HttpServicePath.SharePath + articleId);//分享文字
//        qqShareContent.setTitle("");//设置title
//        qqShareContent.setShareImage(mUMImgBitmap);//设置分享图片
        qqShareContent.setTargetUrl(HttpServicePath.RootPath + HttpServicePath.SharePath + articleId);//设置分享内容跳转URL
        mController.setShareMedia(qqShareContent);
    }

    /**
     * 点赞回调接口
     */
    class addPraiseRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            try {
                JSONObject json = new JSONObject(responseInfo.result);
                String status = json.getString("status");
                String data = json.getString("data");
                if ("1".equals(status)) {
                    praiseId = data;
                    praise.setTextColor(getResources().getColor(R.color.text_select_true));
                    praise.setCompoundDrawables(DrawableUtils.setBounds(getResources().
                            getDrawable(R.drawable.icon_praise_red)), null, null, null);
                    Toast.makeText(LoopArticleActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "点赞数据解析失败:" + e.getMessage());
                Toast.makeText(LoopArticleActivity.this, "点赞失败，请稍候再试！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            DeBugLog.e(TAG, "点赞失败:" + msg);
            Toast.makeText(LoopArticleActivity.this, "点赞失败，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 请求数据网络接口回调
     */
    class LoopArticleRequestCallBack extends RequestCallBack<String> {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String str = responseInfo.result;
            try {
                //全部数据
                LoopArticle loopArticle = JsonUtils.getInstance().fromJSON(LoopArticle.class, str);
                if (loopArticle != null) {
                    String status = loopArticle.getStatus();
                    if (!TextUtils.isEmpty(status)) {
                        if (!status.equals("1")) {
                            Toast.makeText(LoopArticleActivity.this,
                                    getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                        } else {
                            loopArticleData = loopArticle.getData();
                            if (loopArticleData != null) {
                                setForArticleContent();
                                String otherUserSign = loopArticleData.getaCreateUserSign();
                                if (!TextUtils.isEmpty(otherUserSign)) {
                                    if (!userSign.equals(otherUserSign)) {
                                        editor.setVisibility(View.GONE);
                                        delete.setVisibility(View.GONE);
                                    }
                                } else {
                                    DeBugLog.e(TAG, "otherUserSign==null!");
                                }
                            } else {
                                DeBugLog.e(TAG, "loopArticleData==null!");
                                Toast.makeText(LoopArticleActivity.this,
                                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        DeBugLog.e(TAG, "status==null!");
                    }
                } else {
                    DeBugLog.e(TAG, "loopArticle==null!");
                    Toast.makeText(LoopArticleActivity.this,
                            getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(LoopArticleActivity.this,
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        }

        @Override
        public void onFailure(HttpException error, String msg) {
            DeBugLog.e(TAG, "网络请求失败:" + msg);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Toast.makeText(LoopArticleActivity.this,
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 删除文章网络请求回调接口
     */
    class DeleteArticleRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String str = responseInfo.result;
            DeleteArticle deleteArticle = JsonUtils.getInstance().fromJSON(DeleteArticle.class, str);
            if (deleteArticle != null) {
                if (deleteArticle.getStatus().equals("1")) {
                    Toast.makeText(LoopArticleActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(LoopArticleActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            DeBugLog.i(TAG, msg);
            Toast.makeText(LoopArticleActivity.this, "删除失败，请稍候再试", Toast.LENGTH_SHORT).show();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    /**
     * 收藏文章回调接口
     */
    class CollectionArticleRequestCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            try {
                JSONObject json = new JSONObject(stringResponseInfo.result);
                String status = json.getString("status");
                String data = json.getString("data");
                if ("1".equals(status)) {
                    attentionId = data;
                    collection.setTextColor(getResources().getColor(R.color.text_select_true));
                    collection.setCompoundDrawables(DrawableUtils.setBounds(getResources().
                            getDrawable(R.drawable.icon_collection_yellow)), null, null, null);
                    Toast.makeText(LoopArticleActivity.this, "收藏文章成功", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(LoopArticleActivity.this, "收藏失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                DeBugLog.e(TAG, e.getMessage());
            }

        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "请求失败:" + s);
            Toast.makeText(LoopArticleActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消收藏文章回调接口
     */
    class CollectionArticleCancelRequestCallback extends RequestCallBack<String> {
        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            try {
                JSONObject json = new JSONObject(stringResponseInfo.result);
                String status = json.getString("status");
                String data = json.getString("data");
                if ("1".equals(status) && "success".equals(data)) {
                    if (isPraise) {
                        praiseId = null;
                        praise.setTextColor(getResources().getColor(R.color.white));
                        praise.setCompoundDrawables(DrawableUtils.setBounds(getResources().
                                getDrawable(R.drawable.icon_praise_white)), null, null, null);
                        Toast.makeText(LoopArticleActivity.this, "取消点赞成功", Toast.LENGTH_SHORT).show();
                    } else {
                        attentionId = null;
                        collection.setTextColor(getResources().getColor(R.color.white));
                        collection.setCompoundDrawables(DrawableUtils.setBounds(getResources().
                                getDrawable(R.drawable.icon_collection_white)), null, null, null);
                        Toast.makeText(LoopArticleActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoopArticleActivity.this, "网络错误,请稍候再试", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(LoopArticleActivity.this, "网络错误,请稍候再试", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, s);
            Toast.makeText(LoopArticleActivity.this, "网络错误,请稍候再试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void finish() {
        String EasyTackPhotoActivityTAG = EasyTackPhotoActivity.class.getSimpleName();
        if (OtherTAG.equals(EasyTackPhotoActivityTAG)) {
            DeBugLog.i(TAG, "EasyTackPhotoActivityTAG:" + EasyTackPhotoActivityTAG + ",Executive this judge");
            startActivity(new Intent(LoopArticleActivity.this, MainActivity.class));
            super.finish();
        } else {
            DeBugLog.i(TAG, "OtherTAG:" + OtherTAG + ",Executive this judge");
            super.finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}