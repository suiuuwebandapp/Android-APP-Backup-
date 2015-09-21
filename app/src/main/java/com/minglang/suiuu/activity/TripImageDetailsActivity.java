package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.reflect.TypeToken;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.CommonCommentAdapter;
import com.minglang.suiuu.adapter.showPicDescriptionAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.entity.TripImageDetails;
import com.minglang.suiuu.entity.TripImageDetails.TripImageDetailsData.AttentionEntity;
import com.minglang.suiuu.entity.TripImageDetails.TripImageDetailsData.CommentEntity;
import com.minglang.suiuu.entity.TripImageDetails.TripImageDetailsData.LikeEntity;
import com.minglang.suiuu.entity.TripImageDetails.TripImageDetailsData.TripImageDetailsInfo;
import com.minglang.suiuu.entity.UserBack.UserBackData;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
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
public class TripImageDetailsActivity extends BaseAppCompatActivity {

    private static final String TAG = TripImageDetailsActivity.class.getSimpleName();

    private TextProgressDialog dialog;
    private static final int COMMENT_SUCCESS = 20;

    private static final String ID = "id";

    @BindString(R.string.NetworkError)
    String NetworkError;

    @BindString(R.string.DataError)
    String DataError;

    @Bind(R.id.trip_image_details)
    ScrollView tripImageDetails;

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
    private TripImageDetailsInfo tripGalleryDetailInfo;

    /**
     * 评论列表
     */
    private List<CommentEntity> commentContentList;

    /**
     * 标题
     */
    @Bind(R.id.trip_image_details_name)
    TextView tripImageDetailsName;

    /**
     * 标签
     */
    @Bind(R.id.trip_image_details_tag)
    TextView tripImageDetailsTag;

    /**
     * 内容列表
     */
    @Bind(R.id.trip_image_details_content)
    NoScrollBarListView tripImageDetailsContent;

    /**
     * 头像
     */
    @Bind(R.id.trip_image_details_portrait)
    SimpleDraweeView tripImageDetailsPortrait;

    /**
     * 发布者的名字
     */
    @Bind(R.id.trip_image_details_publisher_name)
    TextView tripImageDetailsPublisherName;

    /**
     * 发布的地址
     */
    @Bind(R.id.trip_image_details_publish_location)
    TextView tripImageDetailsPublishLocation;

    /**
     * 评论条数
     */
    @Bind(R.id.suiuu_details_comment_number)
    TextView suiuuDetailsCommentNumber;

    /**
     * 评论框
     */
    @Bind(R.id.suiuu_details_comment)
    EditText suiuuDetailsComment;

    /**
     * 输入评论布局
     */
    @Bind(R.id.suiuu_details_input_comment)
    LinearLayout suiuuDetailsInputComment;

    /**
     * 评论列表
     */
    @Bind(R.id.suiuu_details_comment_list_view)
    NoScrollBarListView suiuuDetailsCommentListView;

    /**
     * 是否关注
     */
    @Bind(R.id.trip_image_details_heart)
    ImageView tripGalleryDetailsHeart;

    /**
     * 关注总数
     */
    @Bind(R.id.headCount)
    TextView tv_head_count;

    /**
     * 还没有评论布局
     */
    @Bind(R.id.suiuu_details_no_comment)
    LinearLayout suiuuDetailsNoComment;

    /**
     * 点击跳入评论按钮
     */
    @Bind(R.id.to_comment_activity)
    TextView toCommentActivity;

    /**
     * 当前旅图的id
     */
    private String id;
    private CommonCommentAdapter adapter;

    /**
     * 猜你喜欢的布局
     */
    @Bind(R.id.trip_image_details_guess_your_love)
    LinearLayout tripImageDetailsGuessYourLove;

    /**
     * 猜你喜欢的数据集合
     */
    private List<LikeEntity> likeList;

    /**
     * 关注的集合
     */
    private List<AttentionEntity> attentionList;
    /**
     * 关注ID
     */
    private String attentionId;

    /**
     * 是否显示全部评论的状态
     */
    private boolean showAllComment = false;

    /**
     * 加载更多的view
     */
    private TextView textView = null;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_gallery_detail);
        ButterKnife.bind(this);
        init();
        loadTripGalleryDetailDate(id);
        viewAction();
        tripImageDetails.smoothScrollTo(0, 0);
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        id = this.getIntent().getStringExtra(ID);
        verification = SuiuuInfo.ReadVerification(this);
        tv_top_center.setText("旅图详情");
        tv_top_right_more.setBackgroundResource(R.drawable.btn_suiuu_share_selector);
        dialog = new TextProgressDialog(this);

        token = SuiuuInfo.ReadAppTimeSign(TripImageDetailsActivity.this);

        context = context;
    }

    private void viewAction() {
        iv_top_back.setOnClickListener(new MyOnClickListener());
        suiuuDetailsComment.setOnClickListener(new MyOnClickListener());
        toCommentActivity.setOnClickListener(new MyOnClickListener());
        tripImageDetailsPortrait.setOnClickListener(new MyOnClickListener());
        tripGalleryDetailsHeart.setOnClickListener(new MyOnClickListener());

        //评论点击事件
        suiuuDetailsCommentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!showAllComment && position == 5) {
                    showAllComment = true;
                    showCommentList(commentContentList);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tripImageDetails.smoothScrollTo(0, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    //根据id获得旅图详情
    private void loadTripGalleryDetailDate(String id) {
        dialog.showDialog();
        String[] keyArray1 = new String[]{"id", "token"};
        String[] valueArray1 = new String[]{id, token};
        try {
            OkHttpManager.onGetAsynRequest(addUrlAndParams(HttpNewServicePath.getTripGalleryDetailById, keyArray1, valueArray1), new loadTripGalleryDetailDateCallBack());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 收藏旅图
     */
    private void collectionTripGallery() {
        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[1];
        paramsArray[0] = new OkHttpManager.Params("id", id);
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.CollectionTripGalleryPath + "?token=" + token,
                    new CollectionTripGalleryRequestCallback(), paramsArray);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消旅图收藏
     */
    private void collectionTripGalleryCancel(String cancelId) {
        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[1];
        paramsArray[0] = new OkHttpManager.Params("attentionId", cancelId);
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.CollectionArticleCancelPath + "?token=" + token,
                    new CollectionGalleryCancelRequestCallback(), paramsArray);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 请求数据网络接口回调
     */
    class loadTripGalleryDetailDateCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "request:" + request.toString() + ",Exception:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String resultData) {
            dialog.dismissDialog();
            L.i(TAG, resultData);
            try {
                TripImageDetails tripImageDetails = JsonUtils.getInstance().fromJSON(TripImageDetails.class, resultData);
                if (tripImageDetails.getStatus() == 1) {
                    tripGalleryDetailInfo = tripImageDetails.getData().getInfo();
                    commentContentList = tripImageDetails.getData().getComment();
                    attentionList = tripImageDetails.getData().getAttention();
                    likeList = tripImageDetails.getData().getLike();
                    fullData();
                } else {
                    Toast.makeText(context, "返回结果为" + tripImageDetails.getStatus(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * 收藏文章回调接口
     */
    class CollectionTripGalleryRequestCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "收藏文章 Exception" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            L.i(TAG, "收藏文章:" + response);
            try {
                JSONObject json = new JSONObject(response);
                String status = json.getString("status");
                String data = json.getString("data");
                if ("1".equals(status)) {
                    attentionId = data;
                    tripGalleryDetailsHeart.setBackgroundResource(R.drawable.attention_heart_press);
                    Toast.makeText(context, "收藏文章成功", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "收藏失败，请稍候再试！", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * 取消收藏文章回调接口
     */
    class CollectionGalleryCancelRequestCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "取消收藏文章Exception:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            try {
                JSONObject json = new JSONObject(response);
                String status = json.getString("status");
                String data = json.getString("data");
                if ("1".equals(status) && "success".equals(data)) {
                    attentionId = null;
                    tripGalleryDetailsHeart.setBackgroundResource(R.drawable.attention_heart_normal);
                    Toast.makeText(context, "取消收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void fullData() {
        fullCommentList();
        tv_head_count.setText(tripGalleryDetailInfo.getAttentionCount());
        tripImageDetailsName.setText(tripGalleryDetailInfo.getTitle());
        tripImageDetailsTag.setText(tripGalleryDetailInfo.getTags().replace(",", " "));

        Uri url = Uri.parse(tripGalleryDetailInfo.getHeadImg());

        tripImageDetailsPortrait.setImageURI(url);
        tripImageDetailsPublisherName.setText(tripGalleryDetailInfo.getNickname());
        tripImageDetailsPublishLocation.setText(tripGalleryDetailInfo.getAddress());

        List<String> picList = jsonUtil.fromJSON(new TypeToken<ArrayList<String>>() {
        }.getType(), tripGalleryDetailInfo.getPicList());

        List<String> picDescription = jsonUtil.fromJSON(new TypeToken<ArrayList<String>>() {
        }.getType(), tripGalleryDetailInfo.getContents());

        //判断是否关注
        if (attentionList.size() < 1) {
            tripGalleryDetailsHeart.setBackgroundResource(R.drawable.attention_heart_normal);
        } else {
            tripGalleryDetailsHeart.setBackgroundResource(R.drawable.attention_heart_press);
            attentionId = attentionList.get(0).getAttentionId();
        }
        tripImageDetailsContent.setAdapter(new showPicDescriptionAdapter(this, picList, picDescription));
        fullGuessYourLove();
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
                    Intent intent = new Intent(context, TripImageDetailsActivity.class);
                    intent.putExtra("id", likeList.get(tag).getId());
                    startActivity(intent);
                }
            });

            tripImageDetailsGuessYourLove.addView(itemView);
        }
    }

    public void fullCommentList() {
        if (commentContentList.size() >= 1) {
            int commentContentListSize = commentContentList.size();
            suiuuDetailsCommentNumber.setText("全部评论 (共" + String.valueOf(commentContentListSize) + "条评论)");
            suiuuDetailsInputComment.setVisibility(View.VISIBLE);
            suiuuDetailsNoComment.setVisibility(View.GONE);
            showCommentList(commentContentList);
        } else {
            suiuuDetailsNoComment.setVisibility(View.VISIBLE);
            suiuuDetailsInputComment.setVisibility(View.GONE);
        }
    }

    /**
     * @param commentDataList 评论列表
     */
    private void showCommentList(List<CommentEntity> commentDataList) {
        List<CommentEntity> newCommentDataList = new ArrayList<>();
        if (!showAllComment) {
            if (commentDataList.size() > 5) {
                if (textView == null) {
                    textView = new TextView(this);
                    textView.setText("点击加载更多");
                    textView.setPadding(0, 10, 0, 0);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(18);
                }
                suiuuDetailsCommentListView.addFooterView(textView);
                newCommentDataList.addAll(commentDataList);
                newCommentDataList.subList(5, commentDataList.size()).clear();
            }
        } else {
            suiuuDetailsCommentListView.removeFooterView(textView);
        }
        if (adapter == null) {
            adapter = new CommonCommentAdapter(this, commentDataList.size() > 5 && !showAllComment ? newCommentDataList : commentDataList, "1");
            suiuuDetailsCommentListView.setAdapter(adapter);
            AppUtils.setListViewHeightBasedOnChildren(suiuuDetailsCommentListView);
        } else {
            adapter.onDateChangeType(commentDataList.size() > 5 && !showAllComment ? newCommentDataList : commentDataList);
        }
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
                case R.id.suiuu_details_comment:
                    if (commentContentList.size() > 5 && !showAllComment) {
                        suiuuDetailsCommentListView.removeFooterView(textView);
                    }
                    //跳到评论页
                    Intent intent = new Intent(context, CommonCommentActivity.class);
                    intent.putExtra("articleId", id);
                    startActivityForResult(intent, COMMENT_SUCCESS);
                    break;
                case R.id.to_comment_activity:
                    //跳到评论页
                    Intent commentIntent = new Intent(context, CommonCommentActivity.class);
                    commentIntent.putExtra("articleId", id);
                    startActivityForResult(commentIntent, COMMENT_SUCCESS);
                    break;
                case R.id.trip_image_details_portrait:
                    Intent intent2UserActivity = new Intent(context, PersonalMainPagerActivity.class);
                    intent2UserActivity.putExtra("userSign", tripGalleryDetailInfo.getUserSign());
                    startActivity(intent2UserActivity);
                    break;
                case R.id.trip_image_details_heart:
                    //关注按钮
                    if (TextUtils.isEmpty(attentionId)) {
                        collectionTripGallery();
                    } else {
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
            showAllComment = false;
            fullCommentList();
        }
    }

}