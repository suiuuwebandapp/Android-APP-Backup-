package com.minglang.suiuu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.SuHttpRequest;

/**
 * 其他用户的个人主页
 * <p/>
 * 尚未完成
 */
public class OtherUserActivity extends Activity {

    private static final String TAG = OtherUserActivity.class.getSimpleName();

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
     * 足迹
     */
    private TextView footprint;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);

        initView();

        ViewAction();

    }

    /**
     * 添加关注
     */
    private void AddAttentionRequest4Service() {
        RequestParams params = new RequestParams();

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
        footprint.setOnClickListener(otherUserClick);
        attention.setOnClickListener(otherUserClick);
        conversation.setOnClickListener(otherUserClick);

        otherUserLoop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    /**
     * 初始化方法
     */
    private void initView() {
        otherUserBack = (ImageView) findViewById(R.id.OtherUserBack);
        collection = (TextView) findViewById(R.id.otherUserCollection);
        headImage = (ImageView) findViewById(R.id.otherUserHeadImage);
        footprint = (TextView) findViewById(R.id.otherUserFootprint);
        attention = (TextView) findViewById(R.id.otherUserAttention);
        conversation = (TextView) findViewById(R.id.otherUserConversation);
        otherUserLoop = (GridView) findViewById(R.id.otherUserLoop);
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

                case R.id.otherUserFootprint:
                    break;

                case R.id.otherUserAttention:
                    //AddAttentionRequest4Service();
                    break;

                case R.id.otherUserConversation:
                    break;

            }

        }
    }

    /**
     * 添加关注回调接口
     */
    private class AddAttentionRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {

        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.e(TAG, s);
        }
    }

}
