package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.LoopArticleImageAdapter;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.customview.NoScrollBarGridView;
import com.minglang.suiuu.entity.BaseCollection;
import com.minglang.suiuu.entity.DeleteArticle;
import com.minglang.suiuu.entity.LoopArticle;
import com.minglang.suiuu.entity.LoopArticleCommentList;
import com.minglang.suiuu.entity.LoopArticleData;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInformation;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.Arrays;
import java.util.List;

/**
 * 具体某个地区/主题下的某个帖子
 * <p/>
 * 网络部分未完成
 */
public class LoopArticleActivity extends Activity {

    private static final String TAG = LoopArticleActivity.class.getSimpleName();

    /**
     * 封面图片
     */
    private ImageView coverImage;

    /**
     * 返回键
     */
    private ImageView back;

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
     * 全部数据
     */
    private LoopArticle loopArticle;

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

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_article);

        articleId = getIntent().getStringExtra("articleId");
        Verification = SuiuuInformation.ReadVerification(this);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.scroll1)
                .showImageForEmptyUri(R.drawable.scroll1).showImageOnFail(R.drawable.scroll1)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();

        initView();
        ViewAction();
        getInternetServiceData();
    }

    /**
     * 控件动作
     */
    private void ViewAction() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

            }
        });

        showComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        locationName.setText(loopArticleData.getaTitle());

        imageLoader.displayImage(loopArticleData.getaImg(), coverImage, options);
//        Bitmap bitmap = imageLoader.loadImageSync(loopArticleData.getaImg(), options);
//        coverImage.setBackgroundDrawable(new BitmapDrawable(bitmap));

        imageLoader.displayImage(loopArticleData.getHeadImg(), headImage);

        userName.setText(loopArticleData.getNickname());
        userLocation.setText(loopArticleData.getaAddr());

        articleContent.setText(loopArticleData.getaContent());

        String images = loopArticleData.getaImgList();
        String[] imageArray = images.split("|");
        List<String> list = Arrays.asList(imageArray);
        imageAdapter = new LoopArticleImageAdapter(this, list);
        noScrollBarGridView.setAdapter(imageAdapter);
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

        back = (ImageView) findViewById(R.id.loop_article_back);
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
    }

    /**
     * 请求数据网络接口回调
     */
    class LoopArticleRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            loopArticle = JsonUtil.getInstance().fromJSON(LoopArticle.class, responseInfo.result);
            if (loopArticle != null) {
                if (!loopArticle.getStatus().equals("1")) {
                    Toast.makeText(LoopArticleActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                } else {
                    loopArticleData = loopArticle.getData();
                    if (loopArticleData != null) {
                        setForArticleContent();
                        list = loopArticleData.getCommentList();
                    }
                }
            } else {
                Toast.makeText(LoopArticleActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
            }

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        }

        @Override
        public void onFailure(HttpException error, String msg) {

            Log.i(TAG, msg);

            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            Toast.makeText(LoopArticleActivity.this, "获取数据失败！", Toast.LENGTH_SHORT).show();
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
                BaseCollection baseCollection = JsonUtil.getInstance().fromJSON(BaseCollection.class, str);
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

}
