package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.minglang.suiuu.chat.activity.ChatActivity;
import com.minglang.suiuu.entity.OtherUser;
import com.minglang.suiuu.entity.OtherUserDataArticle;
import com.minglang.suiuu.entity.OtherUserDataInfo;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.ScreenUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 其他用户的个人主页
 * <p/>
 * 尚未完成
 */
public class OtherUserActivity extends Activity {

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

    private GridView otherUserLoop;

    private OtherUserClick otherUserClick = new OtherUserClick();

    private String userSign;

    private OtherUser otherUser;

    private List<OtherUserDataArticle> articleList;

    private ProgressDialog dialog;

    private int screenWidth, screenHeight;

    private TextView otherUserName,otherUserLocation,otherUserSignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);

        userSign = getIntent().getStringExtra(USERSIGNKEY);
        Log.i("suiuu",userSign);

        initView();

        ViewAction();

        getUserInfo2Service();

    }

    private void getUserInfo2Service() {

        if (dialog != null) {
            dialog.show();
        }

        String msg = SuiuuInformation.ReadVerification(this);

        RequestParams params = new RequestParams();
        params.addBodyParameter(USERSIGNKEY, userSign);
        params.addBodyParameter(HttpServicePath.key, msg);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.OtherUserInformationPath, new GetOtherUserInformationRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();

    }

    /**
     * 添加关注
     */
    private void AddAttentionRequest4Service() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(USERSIGNKEY, userSign);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.AddAttentionUserPath, new AddAttentionRequestCallBack());
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

        otherUserLoop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleId = articleList.get(position).getArticleId();
                Intent intent = new Intent(OtherUserActivity.this, LoopArticleActivity.class);
                intent.putExtra("articleId", articleId);
                startActivity(intent);
            }
        });

    }

    /**
     * 初始化方法
     */
    private void initView() {

        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.load_wait));
        dialog.setCanceledOnTouchOutside(false);

        ScreenUtils screenUtils = new ScreenUtils(this);
        screenWidth = screenUtils.getScreenWidth();
        screenHeight = screenUtils.getScreenHeight();

        otherUserBack = (ImageView) findViewById(R.id.OtherUserBack);
        collection = (TextView) findViewById(R.id.otherUserCollection);
        headImage = (ImageView) findViewById(R.id.otherUserHeadImage);
        attention = (TextView) findViewById(R.id.otherUserAttention);
        conversation = (TextView) findViewById(R.id.otherUserConversation);
        otherUserLoop = (GridView) findViewById(R.id.otherUserLoop);
        otherUserName = (TextView) findViewById(R.id.otherUserName);
        otherUserLocation = (TextView) findViewById(R.id.otherUserLocation);
        otherUserSignature = (TextView) findViewById(R.id.otherUserSignature);
    }

    /**
     * 用户信息回调接口
     */
    private void fullData() {
        OtherUserDataInfo user = otherUser.getData().getUser();
        otherUserName.setText(user.getNickname());
        otherUserSignature.setText(user.getIntro());

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
                    AddAttentionRequest4Service();
                    break;

                case R.id.otherUserConversation:

                    String userId = otherUser.getData().getUser().getUserId();
                    Intent intent = new Intent(OtherUserActivity.this, ChatActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("nikeName",otherUser.getData().getUser().getNickname());
                    startActivity(intent);
                    break;

            }

        }

    }

    private class GetOtherUserInformationRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            String str = stringResponseInfo.result;
            Log.i("suiuu",str);
            try {
                otherUser = JsonUtil.getInstance().fromJSON(OtherUser.class, str);
                articleList = otherUser.getData().getArticleList();
                fullData();
                OtherUserArticleAdapter adapter = new OtherUserArticleAdapter(OtherUserActivity.this, articleList, screenWidth, screenHeight);
                otherUserLoop.setAdapter(adapter);
            } catch (Exception e) {
                Log.e(TAG, "用户数据解析失败异常信息:" + e.getMessage());
                Toast.makeText(OtherUserActivity.this, "网络异常，请稍候再试!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        @Override
        public void onFailure(HttpException e, String s) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            Log.e(TAG, "获取用户数据失败异常信息:" + s);

            Toast.makeText(OtherUserActivity.this, "网络异常，请稍候再试!", Toast.LENGTH_SHORT).show();
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
            Log.e(TAG, "添加关注网络异常信息:" + s);
            Toast.makeText(OtherUserActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

}
