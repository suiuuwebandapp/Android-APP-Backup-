package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.CommonCommentAdapter;
import com.minglang.suiuu.adapter.showPicDescriptionAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.entity.TripGalleryDetail;
import com.minglang.suiuu.entity.TripGalleryDetail.DataEntity.CommentEntity;
import com.minglang.suiuu.entity.TripGalleryDetail.DataEntity.InfoEntity;
import com.minglang.suiuu.entity.TripGalleryDetail.DataEntity.LikeEntity;
import com.minglang.suiuu.entity.UserBackData;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/8 14:30
 * 修改人：Administrator
 * 修改时间：2015/7/8 14:30
 * 修改备注：
 */
public class TripGalleryDetailsActivity extends BaseAppCompatActivity {

    private TextProgressDialog dialog;
    private static final int COMMENT_SUCCESS = 20;

    private static final String ID = "id";

    @Bind(R.id.mv_trip_gallery_map)
    MapView mapView;

    private AMap aMap;

    @Bind(R.id.sv_trip_gallery_detail)
    ScrollView sv_trip_gallery_detail;
    private JsonUtils jsonUtil = JsonUtils.getInstance();

    @Bind(R.id.tv_top_center)
    TextView tv_top_center;

    @Bind(R.id.tv_top_right_more)
    ImageView tv_top_right_more;

    @Bind(R.id.iv_top_back)
    ImageView iv_top_back;

    /**
     * 详情数据
     */
    private InfoEntity tripGalleryDetailInfo;

    /**
     * 评论列表
     */
    private List<CommentEntity> commentContentList;

    /**
     * 标题
     */
    @Bind(R.id.tv_trip_gallery_details_name)
    TextView trip_gallery_details_name;

    /**
     * 标签
     */
    @Bind(R.id.tv_trip_gallery_details_tag)
    TextView trip_gallery_details_tag;

    /**
     * 内容列表
     */
    @Bind(R.id.trip_gallery_details_content)
    NoScrollBarListView trip_gallery_details_content;

    /**
     * 头像
     */
    @Bind(R.id.sdv_trip_gallery_details_portrait)
    SimpleDraweeView trip_gallery_details_portrait;

    /**
     * 发布者的名字
     */
    @Bind(R.id.tv_trip_gallery_details_publisher_name)
    TextView trip_gallery_details_publisher_name;

    /**
     * 发布的地址
     */
    @Bind(R.id.tv_trip_gallery_details_publish_location)
    TextView trip_gallery_detail_publish_location;

    /**
     * 评论条数
     */
    @Bind(R.id.tv_suiuu_details_comment_number)
    TextView suiuu_details_comment_number;

    /**
     * 评论框
     */
    @Bind(R.id.et_suiuu_details_comment)
    EditText suiuu_details_comment;

    /**
     * 输入评论布局
     */
    @Bind(R.id.ll_suiuu_details_input_comment)
    LinearLayout suiuu_details_input_comment;

    /**
     * 评论列表
     */
    @Bind(R.id.lv_suiuu_details_comment)
    NoScrollBarListView suiuu_details_comment_view;
    /**
     * 是否关注
     */
    @Bind(R.id.iv_trip_gallery_detail_heart)
    ImageView trip_gallery_detail_heart;

    /**
     * 还没有评论布局
     */
    @Bind(R.id.ll_suiuu_details_no_comment)
    LinearLayout suiuu_details_no_comment;

    /**
     * 点击跳入评论按钮
     */
    @Bind(R.id.tv_to_comment_activity)
    TextView tv_to_comment_activity;

    /**
     * 当前旅图的id
     */
    private String id;
    private CommonCommentAdapter adapter;

    /**
     * 猜你喜欢的布局
     */
    @Bind(R.id.ll_trip_gallery_details_guess_your_love)
    LinearLayout ll_trip_gallery_detail_guess_your_love;

    /**
     * 猜你喜欢的数据集合
     */
    private List<LikeEntity> likeList;

    /**
     * 关注的集合
     */
    private List<TripGalleryDetail.DataEntity.AttentionEntity> attentionList;
    /**
     * 关注ID
     */
    private String attentionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_gallery_detail);

        ButterKnife.bind(this);

        init(savedInstanceState);
        loadTripGalleryDetailDate(id);
        viewAction();
    }

    /**
     * 初始化AMap对象
     */
    private void init(Bundle savedInstanceState) {
        id = this.getIntent().getStringExtra(ID);
        mapView.onCreate(savedInstanceState);//必须要写

        if (aMap == null) {
            aMap = mapView.getMap();
        }

        sv_trip_gallery_detail.smoothScrollTo(0, 0);

        tv_top_center.setText("旅图详情");
        tv_top_right_more.setBackgroundResource(R.drawable.btn_suiuu_share_selector);

        dialog = new TextProgressDialog(this);
    }

    private void viewAction() {
        iv_top_back.setOnClickListener(new MyOnClickListener());
        suiuu_details_comment.setOnClickListener(new MyOnClickListener());
        tv_to_comment_activity.setOnClickListener(new MyOnClickListener());
        trip_gallery_details_portrait.setOnClickListener(new MyOnClickListener());
        trip_gallery_detail_heart.setOnClickListener(new MyOnClickListener());
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {

            @Override
            public void onTouch(MotionEvent arg0) {
                if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
                    sv_trip_gallery_detail.requestDisallowInterceptTouchEvent(false);
                } else {
                    sv_trip_gallery_detail.requestDisallowInterceptTouchEvent(true);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    //根据id获得旅图详情
    private void loadTripGalleryDetailDate(String id) {
        dialog.showDialog();
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getTripGalleryDetailById, new loadTripGalleryDetailDateCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 收藏旅图
     */
    private void collectionTripGallery() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.CollectionTripGalleryPath, new CollectionTripGalleryRequestCallback());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 取消旅图取消
     */
    private void collectionTripGalleryCancel(String cancelId) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("attentionId", cancelId);
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.CollectionArticleCancelPath, new CollectionGalleryCancelRequestCallback());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 请求数据网络接口回调
     */
    class loadTripGalleryDetailDateCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            dialog.dismissDialog();
            String resultData = responseInfo.result;
            TripGalleryDetail tripGalleryDetail = JsonUtils.getInstance().fromJSON(TripGalleryDetail.class, resultData);
            if (tripGalleryDetail.getStatus() == 1) {
                tripGalleryDetailInfo = tripGalleryDetail.getData().getInfo();
                commentContentList = tripGalleryDetail.getData().getComment();
                attentionList = tripGalleryDetail.getData().getAttention();
                likeList = tripGalleryDetail.getData().getLike();
                fullData();
            } else {
                Toast.makeText(TripGalleryDetailsActivity.this, "返回结果为" + tripGalleryDetail.getStatus(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(HttpException error, String msg) {
            dialog.dismissDialog();
            Toast.makeText(TripGalleryDetailsActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 收藏文章回调接口
     */
    class CollectionTripGalleryRequestCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            try {
                JSONObject json = new JSONObject(stringResponseInfo.result);
                String status = json.getString("status");
                String data = json.getString("data");
                if ("1".equals(status)) {
                    attentionId = data;
                    trip_gallery_detail_heart.setBackgroundResource(R.drawable.attention_heart_press);
                    Toast.makeText(TripGalleryDetailsActivity.this, "收藏文章成功", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(TripGalleryDetailsActivity.this, "收藏失败，请稍候再试！", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(HttpException e, String s) {
            Toast.makeText(TripGalleryDetailsActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消收藏文章回调接口
     */
    class CollectionGalleryCancelRequestCallback extends RequestCallBack<String> {
        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            try {
                JSONObject json = new JSONObject(stringResponseInfo.result);
                String status = json.getString("status");
                String data = json.getString("data");
                if ("1".equals(status) && "success".equals(data)) {
                    attentionId = null;
                    trip_gallery_detail_heart.setBackgroundResource(R.drawable.attention_heart_normal);
                    Toast.makeText(TripGalleryDetailsActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TripGalleryDetailsActivity.this, "网络错误,请稍候再试", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(TripGalleryDetailsActivity.this, "网络错误,请稍候再试", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onFailure(HttpException e, String s) {
            Toast.makeText(TripGalleryDetailsActivity.this, "网络错误,请稍候再试", Toast.LENGTH_SHORT).show();
        }
    }


    private void fullData() {
        fullCommentList();
        trip_gallery_details_name.setText(tripGalleryDetailInfo.getTitle());
        trip_gallery_details_tag.setText(tripGalleryDetailInfo.getTags().replace(",", " "));
        Uri url = Uri.parse(tripGalleryDetailInfo.getHeadImg());
        trip_gallery_details_portrait.setImageURI(url);
        trip_gallery_details_publisher_name.setText(tripGalleryDetailInfo.getNickname());
        trip_gallery_detail_publish_location.setText(tripGalleryDetailInfo.getAddress());
        List<String> picList = jsonUtil.fromJSON(new TypeToken<ArrayList<String>>() {
        }.getType(), tripGalleryDetailInfo.getPicList());
        List<String> picDescription = jsonUtil.fromJSON(new TypeToken<ArrayList<String>>() {
        }.getType(), tripGalleryDetailInfo.getContents());
        //判断是否关注
        if (attentionList.size() < 1) {
            trip_gallery_detail_heart.setBackgroundResource(R.drawable.attention_heart_normal);
        } else {
            trip_gallery_detail_heart.setBackgroundResource(R.drawable.attention_heart_press);
            attentionId = attentionList.get(0).getAttentionId();
        }
        //初始化地图
        LatLng lngLat = new LatLng(Double.valueOf(tripGalleryDetailInfo.getLat()), Double.valueOf(tripGalleryDetailInfo.getLon()));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                lngLat, 16, 30, 0));
        aMap.moveCamera(cameraUpdate);
        aMap.addMarker(new MarkerOptions()
                .position(lngLat)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        trip_gallery_details_content.setAdapter(new showPicDescriptionAdapter(this, picList, picDescription));
        fullGuessYourLove();
        sv_trip_gallery_detail.smoothScrollTo(0, 0);
    }
    @SuppressLint("InflateParams")
    private void fullGuessYourLove() {
        View itemView;
        SimpleDraweeView sdv_item_trip_gallery_tag;
        TextView textView;
        for (int i = 0; i < likeList.size(); i++) {
            itemView = LayoutInflater.from(this).inflate(R.layout.item_trip_gallery_guess_your_love, null);
            itemView.setTag(i);
            sdv_item_trip_gallery_tag = (SimpleDraweeView) itemView.findViewById(R.id.sdv_trip_gallery_detail_like_titleimg);
            Uri uri = Uri.parse(likeList.get(i).getTitleImg());
            sdv_item_trip_gallery_tag.setImageURI(uri);
            textView = (TextView) itemView.findViewById(R.id.tv_item_trip_gallery_like_tag);
            textView.setText(likeList.get(i).getTitle());
            itemView.setPadding(10, 0, 0, 0);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    Intent intent = new Intent(TripGalleryDetailsActivity.this, TripGalleryDetailsActivity.class);
                    intent.putExtra("id", likeList.get(tag).getId());
                    startActivity(intent);
                }
            });
            ll_trip_gallery_detail_guess_your_love.addView(itemView);
        }
    }

    public void fullCommentList() {
        if (commentContentList.size() >= 1) {
            suiuu_details_comment_number.setText("全部评论 (共" + commentContentList.size() + "条评论)");
            suiuu_details_input_comment.setVisibility(View.VISIBLE);
            suiuu_details_no_comment.setVisibility(View.GONE);
            showCommentList(commentContentList);
        } else {
            suiuu_details_no_comment.setVisibility(View.VISIBLE);
            suiuu_details_input_comment.setVisibility(View.GONE);
        }
    }

    private void showCommentList(List<CommentEntity> commentDataList) {
        if (adapter == null) {
            adapter = new CommonCommentAdapter(this, commentDataList, "1");
            suiuu_details_comment_view.setAdapter(adapter);
        }
        //        else {
        //            adapter.onDateChange(commentDataList);
        //        }
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sdv_comment_head_img:
                    //跳到个人中心
                    break;
                case R.id.iv_top_back:
                    finish();
                    break;
                case R.id.et_suiuu_details_comment:
                    //跳到评论页
                    Intent intent = new Intent(TripGalleryDetailsActivity.this, CommonCommentActivity.class);
                    intent.putExtra("articleId", id);
                    startActivityForResult(intent, COMMENT_SUCCESS);
                    break;
                case R.id.tv_to_comment_activity:
                    //跳到评论页
                    Intent commentIntent = new Intent(TripGalleryDetailsActivity.this, CommonCommentActivity.class);
                    commentIntent.putExtra("articleId", id);
                    startActivityForResult(commentIntent, COMMENT_SUCCESS);
                    break;
                case R.id.sdv_trip_gallery_details_portrait:
                    Intent intent2UserActivity = new Intent(TripGalleryDetailsActivity.this, PersonalCenterActivity.class);
                    intent2UserActivity.putExtra("userSign", tripGalleryDetailInfo.getUserSign());
                    startActivity(intent2UserActivity);
                    break;
                case R.id.iv_trip_gallery_detail_heart:
                    //关注按钮
                    if (TextUtils.isEmpty(attentionId)) {
                        collectionTripGallery();
                    }else {
                        collectionTripGalleryCancel(attentionId);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == COMMENT_SUCCESS) {
            UserBackData userBackData = SuiuuInfo.ReadUserData(this);
            String content = data.getStringExtra("content");
            JSONObject json = new JSONObject();
            try {
                json.put("headImg", userBackData.getHeadImg());
                json.put("nickname", userBackData.getNickname());
                json.put("comment", content);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CommentEntity newCommentData =
                    JsonUtils.getInstance().fromJSON(CommentEntity.class, json.toString());
            commentContentList.add(0, newCommentData);
            fullCommentList();
        }
    }

}