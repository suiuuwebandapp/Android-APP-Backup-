package com.minglang.suiuu.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.RollViewPager;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.customview.mProgressDialog;
import com.minglang.suiuu.entity.SuiuuDataDetail;
import com.minglang.suiuu.entity.SuiuuDetailForData;
import com.minglang.suiuu.entity.SuiuuDetailForInfo;
import com.minglang.suiuu.entity.SuiuuDetailForPicList;
import com.minglang.suiuu.entity.SuiuuDetailForPublisherList;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：Suiuu
 * 类描述：suiuu的详细列表
 * 创建人：Administrator
 * 创建时间：2015/5/4 19:13
 * 修改人：Administrator
 * 修改时间：2015/5/4 19:13
 * 修改备注：
 */
public class SuiuuDetailActivity extends BaseActivity {
    private TextView tv_nikename;
    private TextView tv_selfsign;
    private TextView tv_title;
    private TextView tv_content;
    private JsonUtils jsonUtil = JsonUtils.getInstance();
    private SuiuuDetailForInfo detailInfo;
    private SuiuuDetailForPublisherList publisherInfo;
    private CircleImageView suiuu_details_user_head_image;
    private TextView tv_service_time;
    private TextView tv_suiuu_travel_time;
    private TextView tv_atmost_people;
    private RatingBar rb_user_star;
    private RatingBar rb_suiuu_star;
    private TextView tv_price;
    private TextView suiuu_details_praise;
    private SuiuuDetailForData suiuuDetailData;
    private TextView suiuu_details_collection;
    /**
     * 点赞Id
     */
    private String attentionId;
    /**
     * 收藏ID
     */
    private String collectionId;

    private mProgressDialog dialog;
    //判断取消的是点赞还是收藏
    private boolean isPraise;
    private TextView suiuu_details_comments;
    private String tripId;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private List<SuiuuDetailForPicList> picList;
    private LinearLayout dots_ll;
    private TextView top_news_title;
    private LinearLayout top_news_viewpager;
    private List<View> dotLists;
    private List<String> titleLists;
    private List<String> imageUrlLists;
    private TextView tv_choice;
    private ImageView suiuu_details_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suiuu_details_activity);
        tripId = this.getIntent().getStringExtra("tripId");
        Log.i("suiuu", "tripId=" + tripId);

        initView();
        loadDate(tripId);
        viewAction();
    }

    private void viewAction() {
        suiuu_details_praise.setOnClickListener(new MyOnclick());
        suiuu_details_collection.setOnClickListener(new MyOnclick());
        suiuu_details_comments.setOnClickListener(new MyOnclick());
        suiuu_details_user_head_image.setOnClickListener(new MyOnclick());
        suiuu_details_back.setOnClickListener(new MyOnclick());
    }

    private void initView() {
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.suiuu_detail_root);
        if (isKITKAT) {
            if (navigationBarHeight <= 0) {
                rootLayout.setPadding(0, statusBarHeight, 0, 0);
            } else {
                rootLayout.setPadding(0, statusBarHeight, 0, navigationBarHeight);
            }
        }

        dialog = new mProgressDialog(this);
        tv_nikename = (TextView) findViewById(R.id.tv_nickname);
        tv_selfsign = (TextView) findViewById(R.id.tv_self_sign);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);

        suiuu_details_user_head_image = (CircleImageView) findViewById(R.id.suiuu_details_user_head_image);

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_suiuu_image)
                .showImageForEmptyUri(R.drawable.default_suiuu_image).showImageOnFail(R.drawable.default_suiuu_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();

        tv_service_time = (TextView) findViewById(R.id.tv_service_time);
        tv_suiuu_travel_time = (TextView) findViewById(R.id.tv_suiuu_travel_time);
        tv_atmost_people = (TextView) findViewById(R.id.tv_atmost_people);
        rb_user_star = (RatingBar) findViewById(R.id.rb_user_star);
        rb_suiuu_star = (RatingBar) findViewById(R.id.rb_suiuu_star);
        tv_price = (TextView) findViewById(R.id.tv_price);
        suiuu_details_praise = (TextView) findViewById(R.id.suiuu_details_praise);
        suiuu_details_collection = (TextView) findViewById(R.id.suiuu_details_collection);
        suiuu_details_comments = (TextView) findViewById(R.id.suiuu_details_comments);
        dots_ll = (LinearLayout) findViewById(R.id.dots_ll);
        top_news_viewpager = (LinearLayout) findViewById(R.id.top_news_viewpager);
        tv_choice = (TextView) findViewById(R.id.tv_choiced);
        suiuu_details_back = (ImageView) findViewById(R.id.suiuu_details_back);
    }

    //填充数据
    public void fullOfData() {
        tv_choice.setText(detailInfo.getTravelTime() + "人选择过");
        tv_title.setText(detailInfo.getTitle());
        tv_content.setText(detailInfo.getInfo());
        tv_nikename.setText(suiuuDetailData.getUserInfo().getNickname());
        if (TextUtils.isEmpty(suiuuDetailData.getUserInfo().getIntro())) {
            tv_selfsign.setText("该用户没有个性签名");
        } else {
            tv_selfsign.setText(suiuuDetailData.getUserInfo().getIntro());
        }
        ImageLoader.getInstance().displayImage(suiuuDetailData.getUserInfo().getHeadImg(), suiuu_details_user_head_image, options);
        Log.i("suiuu", "startTime=" + detailInfo.getStartTime() + ",endTime=" + detailInfo.getEndTime());
        tv_service_time.setText("服务时间:     " + detailInfo.getStartTime() + "-" + detailInfo.getEndTime());
        tv_suiuu_travel_time.setText("随游时长:     " + detailInfo.getTravelTime() + "个小时");
        Log.i("suiuu", "detailInfo.getMakeUserCount()=" + detailInfo.getMakeUserCount());
        if (detailInfo.getMakeUserCount() == null) {
            tv_atmost_people.setText("最多人数:     0 人");
        } else {
            tv_atmost_people.setText("最多人数:     " + detailInfo.getMakeUserCount() + "人");
        }
        tv_price.setText("￥:  " + detailInfo.getBasePrice());
        //判断是否显示点赞
        if (suiuuDetailData.getPraise().size() >= 1) {
            attentionId = suiuuDetailData.getPraise().get(0).getAttentionId();
        }
        if (!TextUtils.isEmpty(attentionId)) {
            suiuu_details_praise.setTextColor(getResources().getColor(R.color.text_select_true));
            suiuu_details_praise.setCompoundDrawables(setImgDrawTextPosition(R.drawable.icon_praise_red), null, null, null);
        }
        if (suiuuDetailData.getAttention().size() >= 1) {
            collectionId = suiuuDetailData.getAttention().get(0).getAttentionId();
        }
        if (!TextUtils.isEmpty(collectionId)) {
            suiuu_details_collection.setTextColor(getResources().getColor(R.color.text_select_true));
            suiuu_details_collection.setCompoundDrawables(setImgDrawTextPosition(R.drawable.icon_collection_yellow), null, null, null);
        }
        picList = suiuuDetailData.getPicList();
        if (picList.size() >= 1) {
            setViewPager();
        }
        dialog.dismissDialog();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setViewPager() {
        initDot(picList.size());
        RollViewPager mViewPager = new RollViewPager(this, dotLists, new RollViewPager.PagerClickCallBack() {
            @Override
            public void pagerClick(int postion) {
//				Intent intent = new Intent(ct,DetaiAct.class);
//				ct.startActivity(intent);
                Toast.makeText(SuiuuDetailActivity.this, "xxxxxx", Toast.LENGTH_SHORT).show();
            }
        });
        //设置viewpager的宽高
        LinearLayout.LayoutParams layoutParams = new LayoutParams(
                new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
        titleLists = new ArrayList<>();
        imageUrlLists = new ArrayList<>();
        for (SuiuuDetailForPicList picDetail : picList) {
            imageUrlLists.add(picDetail.getUrl());
        }
        // 把图片的url地址传进去
        mViewPager.setimageUrlList(imageUrlLists);
        // 把title的url地址传进去
//        mViewPager.setTitleList(top_news_title, titleLists);
        // 定义一个viewpage可以跳动的方法
        mViewPager.startRoll();

        /**
         * 把viewpager加入到布局里面
         */
        top_news_viewpager.removeAllViews();
        top_news_viewpager.addView(mViewPager);
    }

    //访问网络
    private void loadDate(String tripId) {
        dialog.showDialog();
        RequestParams params = new RequestParams();
        params.addBodyParameter("trId", tripId);
        params.addBodyParameter(HttpServicePath.key, verification);
        Log.i("suiuu", "userSing=" + verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getSuiuuItemInfo, new SuiuuItemInfoCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 请求数据网络接口回调
     */
    class SuiuuItemInfoCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Log.i("suiuu", responseInfo.result);
            try {
                SuiuuDataDetail detail = jsonUtil.fromJSON(SuiuuDataDetail.class, responseInfo.result);
                if ("1".equals(detail.getStatus())) {
                    suiuuDetailData = detail.getData();
                    detailInfo = detail.getData().getInfo();
                    publisherInfo = detail.getData().getCreatePublisherInfo();
                    fullOfData();
                } else {
                    dialog.dismissDialog();
                    Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                dialog.dismissDialog();
                Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            dialog.dismissDialog();
            Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 点赞接口
     */
    private void addPraiseRequest() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("travelId", detailInfo.getTripId());
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.travelAddPraise, new addPraiseRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 收藏随游接口
     */
    private void addCollection() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("travelId", detailInfo.getTripId());
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.travelAddCollection, new addCollectionCallback());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 点赞,收藏取消接口
     *
     * @param cancelId 取消Id
     */
    private void collectionArticleCancel(String cancelId) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("attentionId", cancelId);
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.CollectionArticleCancelPath, new CollectionArticleCancelRequestCallback());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 点赞回调接口
     */
    class addPraiseRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            try {
                JSONObject json = new JSONObject(stringResponseInfo.result);
                String status = json.getString("status");
                String data = json.getString("data");
                if ("1".equals(status)) {
                    attentionId = data;
                    suiuu_details_praise.setTextColor(getResources().getColor(R.color.text_select_true));
                    suiuu_details_praise.setCompoundDrawables(setImgDrawTextPosition(R.drawable.icon_praise_red), null, null, null);
                    Toast.makeText(SuiuuDetailActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(SuiuuDetailActivity.this, "点赞失败，请稍候再试！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.i("i", s);
            Toast.makeText(SuiuuDetailActivity.this, "点赞失败，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消点赞收藏回调接口
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
                        attentionId = null;
                        suiuu_details_praise.setTextColor(getResources().getColor(R.color.white));
                        suiuu_details_praise.setCompoundDrawables(setImgDrawTextPosition(R.drawable.icon_praise_white), null, null, null);
                        Toast.makeText(SuiuuDetailActivity.this, "取消点赞成功", Toast.LENGTH_SHORT).show();
                    } else {
                        collectionId = null;
                        suiuu_details_collection.setTextColor(getResources().getColor(R.color.white));
                        suiuu_details_collection.setCompoundDrawables(setImgDrawTextPosition(R.drawable.icon_collection_white), null, null, null);
                        Toast.makeText(SuiuuDetailActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(SuiuuDetailActivity.this, "网络错误,请稍候再试", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(SuiuuDetailActivity.this, "网络错误,请稍候再试", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(HttpException e, String s) {
            Toast.makeText(SuiuuDetailActivity.this, "网络错误,请稍候再试", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 收藏文章回调接口
     */
    class addCollectionCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            Log.i("suiuu", stringResponseInfo.result);
            try {
                JSONObject json = new JSONObject(stringResponseInfo.result);
                String status = json.getString("status");
                String data = json.getString("data");
                if ("1".equals(status)) {
                    collectionId = data;
                    suiuu_details_collection.setTextColor(getResources().getColor(R.color.text_select_true));
                    suiuu_details_collection.setCompoundDrawables(setImgDrawTextPosition(R.drawable.icon_collection_yellow), null, null, null);
                    Toast.makeText(SuiuuDetailActivity.this, "收藏文章成功", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(SuiuuDetailActivity.this, "收藏失败，请稍候再试！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.i("suiuu", s);
            Toast.makeText(SuiuuDetailActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    public Drawable setImgDrawTextPosition(int img) {
        Drawable drawable = this.getResources().getDrawable(img);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    class MyOnclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int vId = v.getId();
            switch (vId) {
                case R.id.suiuu_details_praise:
                    if (TextUtils.isEmpty(attentionId)) {
                        //为空添加点赞
                        addPraiseRequest();
                    } else {
                        isPraise = true;
                        //取消点赞
                        collectionArticleCancel(attentionId);
                    }
                    break;
                case R.id.suiuu_details_collection:
                    if (TextUtils.isEmpty(collectionId)) {
                        addCollection();
                    } else {
                        isPraise = false;
                        //取消点赞
                        collectionArticleCancel(collectionId);
                    }
                    break;
                case R.id.suiuu_details_comments:
                    Intent intent = new Intent(SuiuuDetailActivity.this, CommentsActivity.class);
                    intent.putExtra("tripId", tripId);
                    startActivity(intent);
                    break;
                case R.id.suiuu_details_user_head_image:
                    Intent intentOtherUser = new Intent(SuiuuDetailActivity.this, OtherUserActivity.class);
                    intentOtherUser.putExtra("userSign", suiuuDetailData.getUserInfo().getUserSign());
                    startActivity(intentOtherUser);
                    break;
                case R.id.suiuu_details_back:
                    finish();
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 动态初始化点
     *
     * @param size
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initDot(int size) {
        Utils util = new Utils(this);
        dotLists = new ArrayList<>();
        dots_ll.removeAllViews();
        LayoutParams params;
        for (int i = 0; i < size; i++) {
            View pointView = new View(this);
            params = new LayoutParams(util.dip2px(15), util.dip2px(15));
            params.leftMargin = util.dip2px(10);
            pointView.setLayoutParams(params);
            pointView.setEnabled(false);
            pointView.setBackgroundResource(R.drawable.ic_launcher);
            if (i == 0) {
                pointView.setBackgroundResource(R.drawable.choiced_press);
            } else {
                pointView.setBackgroundResource(R.drawable.choiced_normal);
            }
            dotLists.add(pointView);
            dots_ll.addView(pointView);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
