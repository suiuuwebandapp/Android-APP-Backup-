package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.OtherUserArticleAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.chat.activity.ChatActivity;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshBase.*;
import com.minglang.suiuu.customview.pulltorefresh.PullToRefreshGridView;
import com.minglang.suiuu.entity.OtherUser;
import com.minglang.suiuu.entity.OtherUserDataArticle;
import com.minglang.suiuu.entity.OtherUserDataInfo;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 其他用户的个人主页
 * <p/>
 * 数据结构已改变
 */
public class OtherUserActivity extends BaseActivity {

    private static final String TAG = OtherUserActivity.class.getSimpleName();

    private static final String USERSIGNKEY = "userSign";

    /**
     * 返回键
     */
    private ImageView otherUserBack;

    /**
     * 收藏
     */
    private TextView collection;

    /**
     * 头像
     */
    private ImageView headImage;

    /**
     * 关注
     */
    private TextView attention;

    /**
     * 会话
     */
    private TextView conversation;

    private PullToRefreshGridView otherUserLoopGridView;

    private OtherUserClick otherUserClick = new OtherUserClick();

    private String userSign;

    private OtherUser otherUser;

    private List<OtherUserDataArticle> articleListAll = new ArrayList<>();

    private ProgressDialog dialog;

    private TextView otherUserName, otherUserLocation, otherUserSignature;

    private String attentionId;

    private DisplayImageOptions options;

    private OtherUserArticleAdapter adapter;

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);

        userSign = getIntent().getStringExtra(USERSIGNKEY);
        DeBugLog.i(TAG, "其他页面传递的userSign:" + userSign);

        initView();
        ViewAction();
        getData();
    }

    private void getData() {
        if (dialog != null) {
            dialog.show();
        }
        getUserInfo2Service(page);
    }

    private void getUserInfo2Service(int page) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(USERSIGNKEY, userSign);
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter("number", String.valueOf(10));
        params.addBodyParameter("page", String.valueOf(page));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.userInformationPath, new GetOtherUserInformationRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 添加关注
     */
    private void AddAttentionRequest4Service() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(USERSIGNKEY, userSign);
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.AddAttentionUserPath, new AddAttentionRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 关注用户取消取消
     */
    private void cancelAttentionRequest() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("attentionId", attentionId);
        params.addBodyParameter(HttpServicePath.key, verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.CollectionArticleCancelPath, new CollectionArticleCancelRequestCallback());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 控件动作
     */
    private void ViewAction() {
        otherUserBack.setOnClickListener(otherUserClick);
        collection.setOnClickListener(otherUserClick);
        headImage.setOnClickListener(otherUserClick);
        attention.setOnClickListener(otherUserClick);
        conversation.setOnClickListener(otherUserClick);

        otherUserLoopGridView.setOnRefreshListener(new OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {

                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                page = page + 1;
                getUserInfo2Service(page);
            }
        });

        otherUserLoopGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleId = articleListAll.get(position).getArticleId();
                Intent intent = new Intent(OtherUserActivity.this, LoopArticleActivity.class);
                intent.putExtra("articleId", articleId);
                intent.putExtra("TAG", TAG);
                startActivity(intent);
            }
        });

    }

    /**
     * 初始化方法
     */
    private void initView() {
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_suiuu_image)
                .showImageForEmptyUri(R.drawable.default_suiuu_image).showImageOnFail(R.drawable.default_suiuu_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE_SAFE).bitmapConfig(Bitmap.Config.RGB_565).build();

        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.load_wait));
        dialog.setCanceledOnTouchOutside(false);

        otherUserBack = (ImageView) findViewById(R.id.OtherUserBack);
        collection = (TextView) findViewById(R.id.otherUserCollection);
        headImage = (ImageView) findViewById(R.id.otherUserHeadImage);
        attention = (TextView) findViewById(R.id.otherUserAttention);
        conversation = (TextView) findViewById(R.id.otherUserConversation);

        otherUserLoopGridView = (PullToRefreshGridView) findViewById(R.id.otherUserLoop);
        otherUserLoopGridView.setMode(Mode.PULL_FROM_END);

        otherUserName = (TextView) findViewById(R.id.otherUserName);
        otherUserLocation = (TextView) findViewById(R.id.otherUserLocation);
        otherUserSignature = (TextView) findViewById(R.id.otherUserSignature);

        adapter = new OtherUserArticleAdapter(this, screenWidth);
        otherUserLoopGridView.setAdapter(adapter);
    }

    /**
     * 填充数据
     */
    private void fullData() {
        OtherUserDataInfo user = otherUser.getData().getUser();
        imageLoader.displayImage(user.getHeadImg(), headImage, options);
        otherUserName.setText(user.getNickname());
        otherUserSignature.setText(user.getIntro());
        collection.setText(otherUser.getData().getFansNumb());
        if (otherUser.getData().getAttentionRst().size() >= 1) {
            attentionId = otherUser.getData().getAttentionRst().get(0).getAttentionId();
        }
        if (!TextUtils.isEmpty(attentionId)) {
            attention.setTextColor(getResources().getColor(R.color.text_select_true));
            attention.setCompoundDrawables(setImgDrawTextPosition(R.drawable.icon_attention_light), null, null, null);
        }
    }

    /**
     * 控件点击事件
     */
    class OtherUserClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.OtherUserBack:
                    finish();
                    break;

                case R.id.otherUserCollection:
                    break;

                case R.id.otherUserHeadImage:
                    break;

                case R.id.otherUserAttention:
                    if (TextUtils.isEmpty(attentionId)) {
                        AddAttentionRequest4Service();
                    } else {
                        cancelAttentionRequest();
                    }
                    break;

                case R.id.otherUserConversation:
                    String userId = otherUser.getData().getUser().getUserId();
                    Intent intent = new Intent(OtherUserActivity.this, ChatActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("nikeName", otherUser.getData().getUser().getNickname());
                    startActivity(intent);
                    break;

            }

        }

    }

    //获取用户信息
    private class GetOtherUserInformationRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            otherUserLoopGridView.onRefreshComplete();

            String str = stringResponseInfo.result;
            DeBugLog.i(TAG, "用户信息:" + str);
            try {
                otherUser = JsonUtils.getInstance().fromJSON(OtherUser.class, str);
                fullData();
                List<OtherUserDataArticle> list = otherUser.getData().getArticleList();
                if (list != null && list.size() > 0) {
                    articleListAll.addAll(list);
                    adapter.setList(articleListAll);
                } else {
                    if (page > 1) {
                        page = page - 1;
                    }
                    Toast.makeText(OtherUserActivity.this, getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "用户数据解析失败异常信息:" + e.getMessage());

                if (page > 1) {
                    page = page - 1;
                }

                Toast.makeText(OtherUserActivity.this, getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
            }
            DeBugLog.i(TAG, "当前页码:" + page);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "获取用户数据失败异常信息:" + s);
            DeBugLog.i(TAG, "当前页码:" + page);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if (page > 1) {
                page = page - 1;
            }

            otherUserLoopGridView.onRefreshComplete();

            Toast.makeText(OtherUserActivity.this, getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 添加关注回调接口
     */
    private class AddAttentionRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            String str = stringResponseInfo.result;
            try {
                JSONObject object = new JSONObject(str);
                String status = object.getString("status");
                if (status.equals("1")) {
                    attentionId = object.getString("data");
                    attention.setTextColor(getResources().getColor(R.color.text_select_true));
                    attention.setCompoundDrawables(setImgDrawTextPosition(R.drawable.icon_attention_light), null, null, null);
                    Toast.makeText(OtherUserActivity.this, "关注成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OtherUserActivity.this, "关注失败！", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(OtherUserActivity.this, "关注失败！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "添加关注网络异常信息:" + s);
            Toast.makeText(OtherUserActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消关注用户回调接口
     */
    class CollectionArticleCancelRequestCallback extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            try {
                JSONObject json = new JSONObject(stringResponseInfo.result);
                String status = json.getString("status");
                String data = json.getString("data");
                if ("1".equals(status) && "success".equals(data)) {
                    attentionId = null;
                    attention.setTextColor(getResources().getColor(R.color.white));
                    attention.setCompoundDrawables(setImgDrawTextPosition(R.drawable.icon_attention_dark), null, null, null);
                    Toast.makeText(OtherUserActivity.this, "取消关注用户成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OtherUserActivity.this, "取消关注用户失败", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(OtherUserActivity.this, "取消关注用户失败", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, s);
            Toast.makeText(OtherUserActivity.this, "取消关注用户失败", Toast.LENGTH_SHORT).show();
        }
    }

    public Drawable setImgDrawTextPosition(int img) {
        Drawable drawable = this.getResources().getDrawable(img);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getUserInfo2Service(1);
//    }

}
