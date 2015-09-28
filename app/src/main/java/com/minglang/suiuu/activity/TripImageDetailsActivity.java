package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.minglang.suiuu.adapter.TripImageDetailsCommentAdapter;
import com.minglang.suiuu.adapter.TripImageCommentAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.customview.TextProgressDialog;
import com.minglang.suiuu.entity.TripImageDetails;
import com.minglang.suiuu.entity.TripImageDetails.TripImageDetailsData.AttentionEntity;
import com.minglang.suiuu.entity.TripImageDetails.TripImageDetailsData.CommentData;
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
import butterknife.BindColor;
import butterknife.BindDrawable;
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

    private static final String ATTENTION_ID = "attentionId";

    @BindColor(R.color.white)
    int titleColor;

    @BindDrawable(R.drawable.attention_heart_normal)
    Drawable AttentionHeartNormal;

    @BindDrawable(R.drawable.attention_heart_press)
    Drawable AttentionHeartPress;

    @BindString(R.string.NetworkError)
    String NetworkError;

    @BindString(R.string.DataError)
    String DataError;

    @Bind(R.id.trip_image_details)
    ScrollView tripImageDetails;

    private JsonUtils jsonUtil = JsonUtils.getInstance();

    /**
     * 详情数据
     */
    private TripImageDetailsInfo tripImageDetailsInfo;

    /**
     * 评论列表
     */
    private List<CommentData> commentContentList;

    @Bind(R.id.trip_image_details_tool_bar)
    Toolbar toolbar;

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
    NoScrollBarListView tripImageCommentListView;

    /**
     * 头像
     */
    @Bind(R.id.trip_image_details_portrait)
    SimpleDraweeView headImageView;

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
    ImageView tripImageDetailsHeart;

    /**
     * 关注总数
     */
    @Bind(R.id.headCount)
    TextView headCount;

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

    private TripImageDetailsCommentAdapter adapter;

    /**
     * 猜你喜欢的布局
     */
    @Bind(R.id.trip_image_details_guess_your_love)
    LinearLayout tripImageDetailsGuessYourLove;

    @Bind(R.id.trip_image_details_your_love_layout)
    LinearLayout tripImageDetailsGuessYourLoveLayout;

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
        setContentView(R.layout.activity_trip_image_details);
        ButterKnife.bind(this);
        init();
        loadTripGalleryDetailsData(id);
        viewAction();
        tripImageDetails.smoothScrollTo(0, 0);
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        id = this.getIntent().getStringExtra(ID);

        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(TripImageDetailsActivity.this);

        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        dialog = new TextProgressDialog(this);

        context = TripImageDetailsActivity.this;
    }

    private void viewAction() {

        suiuuDetailsComment.setOnClickListener(new MyOnClickListener());

        toCommentActivity.setOnClickListener(new MyOnClickListener());

        headImageView.setOnClickListener(new MyOnClickListener());

        tripImageDetailsHeart.setOnClickListener(new MyOnClickListener());

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

    //根据id获得旅图详情
    private void loadTripGalleryDetailsData(String id) {
        dialog.showDialog();
        String[] keyArray1 = new String[]{ID, TOKEN};
        String[] valueArray1 = new String[]{id, token};
        String url = addUrlAndParams(HttpNewServicePath.getTripGalleryDetailById, keyArray1, valueArray1);
        try {
            OkHttpManager.onGetAsynRequest(url, new TripImageDetailsDataCallBack());
        } catch (Exception e) {
            L.e(TAG, "网络请求失败:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 收藏旅图
     */
    private void collectionTripImage() {
        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[1];
        paramsArray[0] = new OkHttpManager.Params(ID, id);
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
    private void collectionTripImageCancel(String cancelId) {
        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[1];
        paramsArray[0] = new OkHttpManager.Params(ATTENTION_ID, cancelId);
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.getCancelAttentionPath + "?token=" + token,
                    new CollectionGalleryCancelRequestCallback(), paramsArray);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 请求数据网络接口回调
     */
    class TripImageDetailsDataCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String resultData) {
            try {
                TripImageDetails tripImageDetails = JsonUtils.getInstance().fromJSON(TripImageDetails.class, resultData);
                if (tripImageDetails.getStatus() == 1) {
                    tripImageDetailsInfo = tripImageDetails.getData().getInfo();
                    commentContentList = tripImageDetails.getData().getComment();
                    attentionList = tripImageDetails.getData().getAttention();
                    likeList = tripImageDetails.getData().getLike();
                    BindData2View();
                } else {
                    Toast.makeText(context, "返回结果为" + tripImageDetails.getStatus(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "request:" + request.toString() + ",Exception:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            dialog.dismissDialog();
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
                    tripImageDetailsHeart.setBackgroundResource(R.drawable.attention_heart_press);
                    String headCountStr = headCount.getText().toString().trim();
                    if (!TextUtils.isEmpty(headCountStr)) {
                        int headCountNumber = Integer.valueOf(headCountStr);
                        headCountNumber = headCountNumber + 1;
                        headCount.setText(String.valueOf(headCountNumber));
                    }
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
                String status = json.getString(STATUS);
                String data = json.getString(DATA);
                if ("1".equals(status) && "success".equals(data)) {
                    attentionId = null;
                    String headCountStr = headCount.getText().toString().trim();
                    if (!TextUtils.isEmpty(headCountStr)) {
                        int headCountNumber = Integer.valueOf(headCountStr);
                        headCountNumber = headCountNumber - 1;
                        if (headCountNumber < 0) {
                            headCountNumber = 0;
                        }
                        headCount.setText(String.valueOf(headCountNumber));
                    }
                    tripImageDetailsHeart.setBackground(AttentionHeartNormal);
                    Toast.makeText(context, "取消收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void BindData2View() {
        fullCommentList();

        String attentionCount = tripImageDetailsInfo.getAttentionCount();
        if (!TextUtils.isEmpty(attentionCount)) {
            headCount.setText(attentionCount);
        } else {
            headCount.setText("");
        }

        String title = tripImageDetailsInfo.getTitle();
        if (!TextUtils.isEmpty(title)) {
            tripImageDetailsName.setText(title);
        } else {
            tripImageDetailsName.setText("");
        }

        String tags = tripImageDetailsInfo.getTags();
        if (!TextUtils.isEmpty(tags)) {
            tripImageDetailsTag.setText(tags.replace(",", " "));
        } else {
            tripImageDetailsTag.setText("");
        }

        String headImagePath = tripImageDetailsInfo.getHeadImg();
        if (!TextUtils.isEmpty(headImagePath)) {
            headImageView.setImageURI(Uri.parse(headImagePath));
        } else {
            headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
        }

        String nickName = tripImageDetailsInfo.getNickname();
        if (!TextUtils.isEmpty(nickName)) {
            tripImageDetailsPublisherName.setText(nickName);
        } else {
            tripImageDetailsPublisherName.setText("");
        }

        String address = tripImageDetailsInfo.getAddress();
        if (!TextUtils.isEmpty(address)) {
            tripImageDetailsPublishLocation.setText(address);
        } else {
            tripImageDetailsPublishLocation.setText("");
        }

        List<String> picList = jsonUtil.fromJSON(new TypeToken<ArrayList<String>>() {
        }.getType(), tripImageDetailsInfo.getPicList());
        List<String> picDescription = jsonUtil.fromJSON(new TypeToken<ArrayList<String>>() {
        }.getType(), tripImageDetailsInfo.getContents());

        //判断是否关注
        if (attentionList.size() < 1) {
            tripImageDetailsHeart.setBackground(AttentionHeartNormal);
        } else {
            tripImageDetailsHeart.setBackground(AttentionHeartPress);
            attentionId = attentionList.get(0).getAttentionId();
        }

        tripImageCommentListView.setAdapter(new TripImageCommentAdapter(this, picList, picDescription));

        fullGuessYourLove();
    }

    @SuppressLint("InflateParams")
    private void fullGuessYourLove() {
        if (likeList != null && likeList.size() > 0) {
            View itemView;
            SimpleDraweeView your_love_main_image;
            TextView textView;
            for (int i = 0; i < likeList.size(); i++) {
                itemView = LayoutInflater.from(this).inflate(R.layout.item_trip_image_guess_your_love, null);
                itemView.setTag(i);

                your_love_main_image = (SimpleDraweeView) itemView.findViewById(R.id.trip_image_details_like_title_img);
                String titleImage = likeList.get(i).getTitleImg();
                if (!TextUtils.isEmpty(titleImage)) {
                    your_love_main_image.setImageURI(Uri.parse(titleImage));
                } else {
                    your_love_main_image.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.loading_error));
                }

                textView = (TextView) itemView.findViewById(R.id.item_trip_image_like_tag);
                String title = likeList.get(i).getTitle();
                if (!TextUtils.isEmpty(title)) {
                    textView.setText(title);
                } else {
                    textView.setText("");
                }

                itemView.setPadding(10, 0, 0, 0);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag = (int) v.getTag();
                        Intent intent = new Intent(context, TripImageDetailsActivity.class);
                        intent.putExtra(ID, likeList.get(tag).getId());
                        startActivity(intent);
                    }
                });

                tripImageDetailsGuessYourLove.addView(itemView);
            }
        } else {
            tripImageDetailsGuessYourLoveLayout.setVisibility(View.GONE);
        }
    }

    public void fullCommentList() {
        int commentContentListSize = commentContentList.size();
        if (commentContentListSize >= 1) {
            suiuuDetailsCommentNumber.setText(String.format("%s%s%s", "全部评论 (共", commentContentListSize, "条评论)"));
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
    private void showCommentList(List<CommentData> commentDataList) {
        List<CommentData> newCommentDataList = new ArrayList<>();
        if (!showAllComment) {
            int commentDataListSize = commentDataList.size();
            if (commentDataListSize > 5) {
                if (textView == null) {
                    textView = new TextView(this);
                    textView.setText("点击加载更多");
                    textView.setPadding(0, 10, 0, 0);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(18);
                }

                suiuuDetailsCommentListView.addFooterView(textView);

                newCommentDataList.addAll(commentDataList);
                newCommentDataList.subList(5, commentDataListSize).clear();
            }
        } else {
            suiuuDetailsCommentListView.removeFooterView(textView);
        }
        
        if (adapter == null) {
            adapter = new TripImageDetailsCommentAdapter(this, commentDataList.size() > 5 && !showAllComment ? newCommentDataList : commentDataList);
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
                    intent2UserActivity.putExtra("userSign", tripImageDetailsInfo.getUserSign());
                    startActivity(intent2UserActivity);
                    break;

                case R.id.trip_image_details_heart:
                    //关注按钮
                    if (TextUtils.isEmpty(attentionId)) {
                        collectionTripImage();
                    } else {
                        collectionTripImageCancel(attentionId);
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
            CommentData newCommentData = JsonUtils.getInstance().fromJSON(CommentData.class, json.toString());
            commentContentList.add(0, newCommentData);
            showAllComment = false;
            fullCommentList();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trip_image_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}