package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.SuiuuDataDetail;
import com.minglang.suiuu.entity.SuiuuDetailForData;
import com.minglang.suiuu.entity.SuiuuDetailForInfo;
import com.minglang.suiuu.entity.SuiuuDetailForPublisherList;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称：Suiuu
 * 类描述：suiuu的详细列表
 * 创建人：Administrator
 * 创建时间：2015/5/4 19:13
 * 修改人：Administrator
 * 修改时间：2015/5/4 19:13
 * 修改备注：
 */
public class SuiuuDetailActivity extends Activity {
    private TextView tv_nikename;
    private TextView tv_selfsign;
    private TextView tv_title;
    private TextView tv_content;
    private JsonUtil jsonUtil = JsonUtil.getInstance();
    private SuiuuDetailForInfo detailInfo;
    private SuiuuDetailForPublisherList publisherInfo;
    private CircleImageView suiuu_details_user_head_image;
    private DisplayImageOptions options;
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

    /**
     * 验证信息
     */
    private String Verification;
    private Dialog dialog;
    //判断取消的是点赞还是收藏
    private boolean isPrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suiuu_details_activity);
        String tripId = this.getIntent().getStringExtra("tripId");
        Log.i("suiuu", "tripId=" + tripId);
        Verification = SuiuuInfo.ReadVerification(this);
        initView();
        loadDate(tripId);
        viewAction();

    }

    private void viewAction() {
        suiuu_details_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(attentionId)) {
                    //为空添加点赞
                    addPraseRequest();
                }else {
                    isPrase = true;
                    //取消点赞
                    collectionArticleCancle(attentionId);
                }
             }
        });
        suiuu_details_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(collectionId)) {
                    addCollection();
                }else {
                    isPrase = false;
                    //取消点赞
                    collectionArticleCancle(collectionId);
                }
            }
        });
    }

    private void initView() {
        tv_nikename = (TextView) findViewById(R.id.tv_nickname);
        tv_selfsign = (TextView) findViewById(R.id.tv_self_sign);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.progress_bar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
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
    }
    //填充数据
    public void fullOfData() {
        tv_title.setText(detailInfo.getTitle());
        tv_content.setText(detailInfo.getInfo());
        tv_nikename.setText(publisherInfo.getNickname());
        Log.i("suiuu", publisherInfo.getIntro() + "intro-----------");
        if (TextUtils.isEmpty(publisherInfo.getIntro())) {
            tv_selfsign.setText("该用户没有个性签名");
        } else {
            tv_selfsign.setText(publisherInfo.getIntro());
        }
        ImageLoader.getInstance().displayImage(publisherInfo.getHeadImg(), suiuu_details_user_head_image, options);
        Log.i("suiuu", "startTime=" + detailInfo.getStartTime() + ",endTime=" + detailInfo.getEndTime());
        tv_service_time.setText("服务时间:     " + detailInfo.getStartTime() + "-" + detailInfo.getEndTime());
        tv_suiuu_travel_time.setText("随游时长:     " + detailInfo.getTravelTime() + "个小时");
        tv_atmost_people.setText("最多人数:     " + detailInfo.getMakeUserCount() + "人");
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
        dialog.dismiss();
    }

    //访问网络
    private void loadDate(String tripId) {
        dialog.show();
        RequestParams params = new RequestParams();
        params.addBodyParameter("trId", tripId);
        params.addBodyParameter(HttpServicePath.key, Verification);
        Log.i("suiuu","userSing="+Verification);
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
            try {
                SuiuuDataDetail detail = jsonUtil.fromJSON(SuiuuDataDetail.class, responseInfo.result);
                if ("1".equals(detail.getStatus())) {
                    suiuuDetailData = detail.getData();
                    detailInfo = detail.getData().getInfo();
                    publisherInfo = detail.getData().getCreatePublisherInfo();
                    fullOfData();
                } else {
                    dialog.dismiss();
                    Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                dialog.dismiss();
                Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
            }
        }
        @Override
        public void onFailure(HttpException error, String msg) {
            dialog.dismiss();
            Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 点赞接口
     */
    private void addPraseRequest() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("travelId", detailInfo.getTripId());
        params.addBodyParameter(HttpServicePath.key, Verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.travelAddPrase, new addPraseRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }
    /**
     * 收藏随游接口
     */
    private void addCollection() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("travelId", detailInfo.getTripId());
        params.addBodyParameter(HttpServicePath.key, Verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.travelAddCollection, new addCollectionCallback());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 点赞,收藏取消接口
     * @param cancelId 取消Id
     */
    private void collectionArticleCancle(String cancelId) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("attentionId", cancelId);
        params.addBodyParameter(HttpServicePath.key, Verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.CollectionArticleCancelPath, new CollectionArticleCancelRequestCallback());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }
    /**
     * 点赞回调接口
     */
    class addPraseRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            try {
                JSONObject json = new JSONObject(stringResponseInfo.result);
                String status = json.getString("status");
                String data = json.getString("data");
                if("1".equals(status)) {
                    attentionId = data;
                    suiuu_details_praise.setTextColor(getResources().getColor(R.color.text_select_true));
                    suiuu_details_praise.setCompoundDrawables(setImgDrawTextPosition(R.drawable.icon_praise_red),null,null,null);
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
                    if(isPrase) {
                        attentionId = null;
                        suiuu_details_praise.setTextColor(getResources().getColor(R.color.white));
                        suiuu_details_praise.setCompoundDrawables(setImgDrawTextPosition(R.drawable.icon_praise_white), null, null, null);
                        Toast.makeText(SuiuuDetailActivity.this, "取消点赞成功", Toast.LENGTH_SHORT).show();
                    }else {
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
            Toast.makeText(SuiuuDetailActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    public Drawable setImgDrawTextPosition(int img) {
        Drawable drawable = this.getResources().getDrawable(img);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }
}
