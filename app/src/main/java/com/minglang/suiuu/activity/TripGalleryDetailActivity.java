package com.minglang.suiuu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.amap.api.maps.MapView;
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
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.entity.TripGalleryDetail;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/8 14:30
 * 修改人：Administrator
 * 修改时间：2015/7/8 14:30
 * 修改备注：
 */
public class TripGalleryDetailActivity extends BaseActivity {
    private static final int COMMENT_SUCCESS = 20;
    private MapView mapView;
    private AMap aMap;
    private ScrollView sv_trip_gallery_detail;
    private JsonUtils jsonUtil = JsonUtils.getInstance();
    /**
     * 详情数据
     */
    private TripGalleryDetail.DataEntity.InfoEntity tripGalleryDetailInfo;
    /**
     * 评论列表
     */
    private List<TripGalleryDetail.DataEntity.CommentEntity> commentContentList;
    /**
     * 标题
     */
    private TextView tv_trip_gallery_detail_name;
    /**
     * 标签
     */
    private TextView tv_trip_gallery_detail_tag;
    /**
     * 内容列表
     */
    private NoScrollBarListView nsbv_trip_gallery_detail_content;
    /**
     * 头像
     */
    private SimpleDraweeView sdv_trip_gallery_detail_portait;
    /**
     * 发布者的名字
     */
    private TextView tv_trip_gallery_detail_publisername;
    /**
     * 发布的地址
     */
    private TextView tv_trip_gallery_detail_publish_location;
    /**
     * 是否关注
     */
    private ImageView iv_trip_gallery_detail_heart;
    /**
     * 评论条数
     */
    private TextView tv_suiuu_detail_comment_number;
    /**
     * 发布者头像
     */
    private SimpleDraweeView sdv_comment_head_img;
    /**
     * 评论框
     */
    private EditText et_suiuu_detail_comment;
    /**
     * 输入评论布局
     */
    private LinearLayout ll_suiuu_detail_input_comment;
    /**
     * 评论列表
     */
    private NoScrollBarListView lv_suiuu_detail_comment;
    /**
     * 还没有评论布局
     */
    private LinearLayout ll_suiuu_detail_nocomment;
    /**
     * 点击跳入评论按钮
     */
    private TextView tv_to_commnet_activity;
    /**
     * 当前旅图的id
     */
    private String id;
    private CommonCommentAdapter adapter;
    /**
     * 猜你喜欢的布局
     */
    private LinearLayout ll_trip_gallery_detail_guessyourlove;
    /**
     * 猜你喜欢的数据集合
     */
    private List<TripGalleryDetail.DataEntity.LikeEntity> likeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_gallery_detail);
        id = this.getIntent().getStringExtra("id");
        mapView = (MapView) findViewById(R.id.mv_trip_gallery_map);
        mapView.onCreate(savedInstanceState);//必须要写
        init();
        loadTripGalleryDetailDate(id);
        viewAction();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        sv_trip_gallery_detail = (ScrollView) findViewById(R.id.sv_trip_gallery_detail);
        tv_trip_gallery_detail_name = (TextView) findViewById(R.id.tv_trip_gallery_detail_name);
        tv_trip_gallery_detail_tag = (TextView) findViewById(R.id.tv_trip_gallery_detail_tag);
        nsbv_trip_gallery_detail_content = (NoScrollBarListView) findViewById(R.id.nsbv_trip_gallery_detail_content);
        sdv_trip_gallery_detail_portait = (SimpleDraweeView) findViewById(R.id.sdv_trip_gallery_detail_portait);
        tv_trip_gallery_detail_publisername = (TextView) findViewById(R.id.tv_trip_gallery_detail_publisername);
        tv_trip_gallery_detail_publish_location = (TextView) findViewById(R.id.tv_trip_gallery_detail_publish_location);
        iv_trip_gallery_detail_heart = (ImageView) findViewById(R.id.iv_trip_gallery_detail_heart);
        tv_suiuu_detail_comment_number = (TextView) findViewById(R.id.tv_suiuu_detail_comment_number);
        sdv_comment_head_img = (SimpleDraweeView) findViewById(R.id.sdv_comment_head_img);
        et_suiuu_detail_comment = (EditText) findViewById(R.id.et_suiuu_detail_comment);
        ll_suiuu_detail_input_comment = (LinearLayout) findViewById(R.id.ll_suiuu_detail_input_comment);
        lv_suiuu_detail_comment = (NoScrollBarListView) findViewById(R.id.lv_suiuu_detail_comment);
        ll_suiuu_detail_nocomment = (LinearLayout) findViewById(R.id.ll_suiuu_detail_nocomment);
        tv_to_commnet_activity = (TextView) findViewById(R.id.tv_to_commnet_activity);
        ll_trip_gallery_detail_guessyourlove = (LinearLayout) findViewById(R.id.ll_trip_gallery_detail_guessyourlove);
    }

    private void viewAction() {
        et_suiuu_detail_comment.setOnClickListener(new MyOnClickListener());
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {

            @Override
            public void onTouch(MotionEvent arg0) {
                // TODO Auto-generated method stub
                if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
                    sv_trip_gallery_detail.requestDisallowInterceptTouchEvent(false);
                } else {
                    sv_trip_gallery_detail.requestDisallowInterceptTouchEvent(true);
                }
            }
        });
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    //根据id获得旅图详情
    private void loadTripGalleryDetailDate(String id) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getTripGalleryDetailById, new loadTripGalleryDetailDateCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 请求数据网络接口回调
     */
    class loadTripGalleryDetailDateCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Log.i("suiuu", responseInfo.result);
            String resultData = responseInfo.result;
            TripGalleryDetail tripGalleryDetail = JsonUtils.getInstance().fromJSON(TripGalleryDetail.class, resultData);
            if (tripGalleryDetail.getStatus() == 1) {
                tripGalleryDetailInfo = tripGalleryDetail.getData().getInfo();
                commentContentList = tripGalleryDetail.getData().getComment();
                likeList = tripGalleryDetail.getData().getLike();
                fullData();
            } else {
                Toast.makeText(TripGalleryDetailActivity.this, "返回结果为" + tripGalleryDetail.getStatus(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Toast.makeText(TripGalleryDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    private void fullData() {
        fullCommentList();
        tv_trip_gallery_detail_name.setText(tripGalleryDetailInfo.getTitle());
        tv_trip_gallery_detail_tag.setText(tripGalleryDetailInfo.getTags());
        Uri url = Uri.parse(tripGalleryDetailInfo.getHeadImg());
        sdv_trip_gallery_detail_portait.setImageURI(url);
        tv_trip_gallery_detail_publisername.setText(tripGalleryDetailInfo.getNickname());
        tv_trip_gallery_detail_publish_location.setText(tripGalleryDetailInfo.getAddress());
        tv_suiuu_detail_comment_number.setText(tripGalleryDetailInfo.getCommentCount());
        List<String> picList = jsonUtil.fromJSON(new TypeToken<ArrayList<String>>(){
        }.getType(), tripGalleryDetailInfo.getPicList());
        List<String> picDescription = jsonUtil.fromJSON(new TypeToken<ArrayList<String>>() {
        }.getType(), tripGalleryDetailInfo.getContents());
        nsbv_trip_gallery_detail_content.setAdapter(new showPicDescriptionAdapter(this, picList, picDescription));
        fullGuessYourLove();
    }

    private void fullGuessYourLove() {
        View itemView = null;
        ImageView imageView = null;
        TextView textView;
        for (int i = 0; i < likeList.size(); i++) {
            itemView = LayoutInflater.from(this).inflate(R.layout.item_trip_gallery_tag, null);
            itemView.setTag(i);
            itemView.measure(150,100);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_trip_gallery_tag);
            textView = (TextView) itemView.findViewById(R.id.tv_item_trip_gallery_tag);
//            imageView.setImageResource(mPhotosIntArray[i]);
            textView.setText(likeList.get(i).getTitle());
            itemView.setPadding(10, 0, 0, 0);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    Toast.makeText(TripGalleryDetailActivity.this, "点击了" + likeList.get(tag).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            ll_trip_gallery_detail_guessyourlove.addView(itemView);
        }
    }

    public void fullCommentList() {
        if (commentContentList.size() >= 1) {
            ll_suiuu_detail_input_comment.setVisibility(View.VISIBLE);
            ll_suiuu_detail_nocomment.setVisibility(View.GONE);
            showCommentList(commentContentList);
        } else {
            ll_suiuu_detail_nocomment.setVisibility(View.VISIBLE);
            ll_suiuu_detail_input_comment.setVisibility(View.GONE);
        }
    }

    private void showCommentList(List<TripGalleryDetail.DataEntity.CommentEntity> commentDataList) {
        if (adapter == null) {
            adapter = new CommonCommentAdapter(this, commentDataList, "1");
            lv_suiuu_detail_comment.setAdapter(adapter);
        } else {
//            adapter.onDateChange(commentDataList);
        }
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.sdv_comment_head_img:
                    //跳到个人中心
                    break;
                case R.id.et_suiuu_detail_comment:
                    //跳到评论页
                    Intent intent = new Intent(TripGalleryDetailActivity.this, CommonCommentActivity.class);
                    intent.putExtra("tripId", id);
                    startActivityForResult(intent, COMMENT_SUCCESS);
                    break;
                case R.id.tv_to_commnet_activity:
                    //跳到评论页
                    Intent commentintent = new Intent(TripGalleryDetailActivity.this, CommonCommentActivity.class);
                    commentintent.putExtra("tripId", id);
                    startActivityForResult(commentintent, COMMENT_SUCCESS);
                    break;


            }
        }

    }


}