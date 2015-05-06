package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.CommentAdapter;
import com.minglang.suiuu.adapter.LoopArticleImageAdapter;
import com.minglang.suiuu.adapter.showPicDescriptionAdapter;
import com.minglang.suiuu.chat.activity.ShowBigImage;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.customview.NoScrollBarGridView;
import com.minglang.suiuu.entity.DeleteArticle;
import com.minglang.suiuu.entity.LoopArticle;
import com.minglang.suiuu.entity.LoopArticleCommentList;
import com.minglang.suiuu.entity.LoopArticleData;
import com.minglang.suiuu.entity.SuiuuReturnDate;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInformation;
import com.minglang.suiuu.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体某个地区/主题下的某个帖子
 * <p/>
 * 网络部分未完成
 */
public class LoopArticleActivity extends Activity {

    private static final String TAG = LoopArticleActivity.class.getSimpleName();

    private static final String ARTICLEID = "articleId";

    private String OtherTAG;

    /**
     * 封面图片
     */
    private ImageView coverImage;

    /**
     * 点赞
     */
    private TextView praise;

    /**
     * 收藏
     */
    private TextView collection;

    /**
     * 位置信息
     */
    private TextView locationName;

    /**
     * 用户头像
     */
    private CircleImageView headImage;

    /**
     * 用户名
     */
    private TextView userName;

    /**
     * 用户位置
     */
    private TextView userLocation;

    /**
     * 修改
     */
    private TextView editor;

    /**
     * 删除
     */
    private TextView delete;

    /**
     * 文章内容
     */
    private TextView articleContent;

    /**
     * 展示图片的GridView
     */
    private NoScrollBarGridView noScrollBarGridView;

    /**
     * 展示评论
     */
    private TextView showComments;

    /**
     * 分享
     */
    private TextView share;

    /**
     * 评论
     */
    private TextView comments;

    /**
     * 图片适配器
     */
    private LoopArticleImageAdapter imageAdapter;

    private String articleId;

    /**
     * 验证信息
     */
    private String Verification;

    /**
     * 文章主题内容数据集合
     */
    private LoopArticleData loopArticleData;

    /**
     * 评论列表数据集合
     */
    private List<LoopArticleCommentList> list;

    /**
     * 图片加载
     */
    private ImageLoader imageLoader;

    private DisplayImageOptions options;
    private DisplayImageOptions options1;

    private ProgressDialog progressDialog;

    private String myUserSign;
    private List<String> imageList;
    private RelativeLayout rl_showForAsk;
    private RelativeLayout rl_showForTakePhoto;
    private ListView loop_article_listview, lv_comment_list;
    private ImageView loop_article_back;
    private boolean isClickComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_article);

        articleId = getIntent().getStringExtra(ARTICLEID);
        OtherTAG = getIntent().getStringExtra("TAG");
        Verification = SuiuuInformation.ReadVerification(this);

        Log.i(TAG, "articleId:" + articleId);
        Log.i(TAG, "Verification:" + Verification);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.scroll1)
                .showImageForEmptyUri(R.drawable.scroll1).showImageOnFail(R.drawable.scroll1)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();
        options1 = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_suiuu_image)
                .showImageForEmptyUri(R.drawable.default_suiuu_image).showImageOnFail(R.drawable.default_suiuu_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();
        myUserSign = SuiuuInformation.ReadUserSign(this);

        initView();
        ViewAction();
        getInternetServiceData();
    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionArticle();
            }
        });

        editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(LoopArticleActivity.this).setTitle(getResources().
                        getString(R.string.sure_delete))
                        .setNegativeButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteArticle();
                            }
                        })
                        .setPositiveButton(getResources().getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(LoopArticleActivity.this, "已取消删除", Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();
            }
        });

        noScrollBarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showPic = new Intent(LoopArticleActivity.this, ShowBigImage.class);
                showPic.putExtra("remotepath", AppConstant.IMG_FROM_SUIUU_CONTENT + imageList.get(position));
                showPic.putExtra("isHuanXin", false);
                startActivity(showPic);
            }
        });
        //显示评论列表
        showComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClickComment) {
                    lv_comment_list.setVisibility(View.GONE);
                    isClickComment = false;
                } else {
                    List<LoopArticleCommentList> commentList = loopArticleData.getCommentList();
                    if (commentList.size() > 0) {
                        lv_comment_list.setVisibility(View.VISIBLE);
                        lv_comment_list.setAdapter(new CommentAdapter(LoopArticleActivity.this, commentList));
                    } else {
                        Toast.makeText(LoopArticleActivity.this, R.string.thisArticNoComment, Toast.LENGTH_SHORT).show();
                    }
                    isClickComment = true;
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                Intent intent = new Intent(LoopArticleActivity.this, OtherUserActivity.class);
                intent.putExtra("userSign", loopArticleData.getaCreateUserSign());
                startActivity(intent);
            }
        });
    }

    /**
     * 收藏文章
     */
    private void collectionArticle() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("articleId", articleId);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.CollectionArticlePath, new CollectionArticleRequestCallback());

        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 删除文章
     */
    private void deleteArticle() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("articleId", articleId);
        params.addBodyParameter(HttpServicePath.key, Verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.DeleteArticlePath, new DeleteArticleRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
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
        params.addBodyParameter(HttpServicePath.key, Verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.LoopArticlePath, new LoopArticleRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 将显示文章内容
     */
    private void setForArticleContent() {
        rl_showForTakePhoto.setVisibility(View.GONE);
        rl_showForAsk.setVisibility(View.VISIBLE);
        locationName.setText(loopArticleData.getaTitle());
        imageLoader.displayImage(loopArticleData.getHeadImg(), headImage, options1);
        userName.setText(loopArticleData.getNickname());
        String address = loopArticleData.getaAddr();
        if (!TextUtils.isEmpty(address)) {
            userLocation.setText(address);
        } else {
            userLocation.setText("");
        }

        String imageListPath = loopArticleData.getaImgList();
        if (!TextUtils.isEmpty(imageListPath)) {
            imageList = JsonUtil.getInstance().fromJSON(new TypeToken<ArrayList<String>>() {
            }.getType(), imageListPath);
            if (imageList != null) {
                if (imageList.size() >= 1) {
                    imageLoader.displayImage(AppConstant.IMG_FROM_SUIUU_CONTENT + imageList.get(0), coverImage, options);
                }
                String content = loopArticleData.getaContent();
                String type = loopArticleData.getaType();
                if (!TextUtils.isEmpty(type)) {
                    if ("1".equals(type)) {
                        rl_showForTakePhoto.setVisibility(View.VISIBLE);
                        rl_showForAsk.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(content)) {
                            List<String> contentList = JsonUtil.getInstance().fromJSON(new TypeToken<ArrayList<String>>() {
                            }.getType(), content);
                            if (contentList != null) {
                                loop_article_listview.setAdapter(new showPicDescriptionAdapter(this, imageList, contentList));
                            } else {
                                Log.e(TAG, "contentList==null!");
                            }
                            Utils.setListViewHeightBasedOnChildren(loop_article_listview);
                        } else {
                            Log.e(TAG, "content==null!***");
                        }
                    } else {
                        if (!TextUtils.isEmpty(content)) {
                            articleContent.setText(content);
                        } else {
                            articleContent.setText("");
                            Log.e(TAG, "***content==null!");
                        }
                        imageAdapter = new LoopArticleImageAdapter(this, imageList);
                        noScrollBarGridView.setAdapter(imageAdapter);
                    }
                } else {
                    Log.e(TAG, "type==null!");
                }
            } else {
                Log.e(TAG, "imageList==null!");
            }
        } else {
            Log.e(TAG, "imageListPath==null!");
        }
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

        coverImage = (ImageView) findViewById(R.id.loop_article_cover_image);

        praise = (TextView) findViewById(R.id.loop_article_praise);
        collection = (TextView) findViewById(R.id.loop_article_collection);

        locationName = (TextView) findViewById(R.id.loop_article_location);

        headImage = (CircleImageView) findViewById(R.id.loop_article_user_head_image);
        userName = (TextView) findViewById(R.id.loop_article_user_name);
        userLocation = (TextView) findViewById(R.id.loop_article_user_location);

        editor = (TextView) findViewById(R.id.loop_article_editor);
        delete = (TextView) findViewById(R.id.loop_article_delete);

        articleContent = (TextView) findViewById(R.id.loop_article_content);
        noScrollBarGridView = (NoScrollBarGridView) findViewById(R.id.loop_article_grid);
        showComments = (TextView) findViewById(R.id.loop_article_show_comment);

        share = (TextView) findViewById(R.id.loop_article_share);
        comments = (TextView) findViewById(R.id.loop_article_comments);
        rl_showForTakePhoto = (RelativeLayout) findViewById(R.id.rl_showForTakePhoto);
        rl_showForAsk = (RelativeLayout) findViewById(R.id.rl_showForAsk);
        loop_article_listview = (ListView) findViewById(R.id.loop_article_listview);
        loop_article_back = (ImageView) findViewById(R.id.loop_article_back);
        lv_comment_list = (ListView) findViewById(R.id.lv_comment_list);
    }

    /**
     * 请求数据网络接口回调
     */
    class LoopArticleRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String str = responseInfo.result;
            Log.i(TAG, "文章详情数据:" + str);
            try {
                //全部数据
                LoopArticle loopArticle = JsonUtil.getInstance().fromJSON(LoopArticle.class, str);
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
                                list = loopArticleData.getCommentList();
                                String otherUserSign = loopArticleData.getaCreateUserSign();
                                if (!TextUtils.isEmpty(otherUserSign)) {
                                    if (!myUserSign.equals(otherUserSign)) {
                                        editor.setVisibility(View.GONE);
                                        delete.setVisibility(View.GONE);
                                    }
                                } else {
                                    Log.e(TAG, "otherUserSign==null!");
                                }
                            } else {
                                Log.e(TAG, "loopArticleData==null!");
                                Toast.makeText(LoopArticleActivity.this,
                                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Log.e(TAG, "status==null!");
                    }
                } else {
                    Log.e(TAG, "loopArticle==null!");
                    Toast.makeText(LoopArticleActivity.this,
                            getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "数据解析失败1:" + e.toString());
                Log.e(TAG, "数据解析失败2:" + e.getMessage());
                Log.e(TAG, "数据解析失败3:" + e.getLocalizedMessage());
                Toast.makeText(LoopArticleActivity.this,
                        getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        }

        @Override
        public void onFailure(HttpException error, String msg) {

            Log.e(TAG, "网络请求失败:" + msg);

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
            DeleteArticle deleteArticle = JsonUtil.getInstance().fromJSON(DeleteArticle.class, str);
            if (deleteArticle != null) {
                if (deleteArticle.getStatus().equals("1")) {
                    Toast.makeText(LoopArticleActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoopArticleActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Log.i(TAG, msg);
            Toast.makeText(LoopArticleActivity.this, "删除失败，请稍候再试", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 收藏文章回调接口
     */
    class CollectionArticleRequestCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            try {
                SuiuuReturnDate baseCollection = JsonUtil.getInstance().fromJSON(SuiuuReturnDate.class, str);
                if (baseCollection.getStatus().equals("1")) {
                    Toast.makeText(LoopArticleActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(LoopArticleActivity.this, "收藏失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
            }

        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, s);
            Toast.makeText(LoopArticleActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void finish() {
        if (OtherTAG.equals(AskQuestionActivity.class.getSimpleName())) {
            startActivity(new Intent(LoopArticleActivity.this, MainActivity.class));
            super.finish();
        } else {
            super.finish();
        }
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
