package com.minglang.suiuu.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.CommentAdapter;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SystemBarTintManager;

/**
 * 评论列表
 */
public class CommentsActivity extends Activity {

    /**
     * 返回键
     */
    private ImageView back;

    /**
     * 评论列表
     */
    private ListView mListView;

    private CommentAdapter adapter;
    private EditText et_input_comment;
    private Button bt_send_comment;
    private String articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        articleId = this.getIntent().getStringExtra("articleId");

        initView();

        ViewAction();

        mListView.setAdapter(adapter);

    }

    private void ViewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentContent = et_input_comment.getText().toString().trim();
                if(TextUtils.isEmpty(commentContent)) {
                    Toast.makeText(CommentsActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                }else {
                    requestCommentSend(commentContent);
                }
            }
        });
    }

    private void requestCommentSend(String commentContent) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("articleId", articleId);
        params.addBodyParameter("content", commentContent);
        params.addBodyParameter("rId", "0");
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.createComment, new requestComentSendCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }
    class requestComentSendCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Log.i("suiuu","success="+responseInfo.result);
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Log.e("suiuu", "网络请求失败:" + msg);
            Toast.makeText(CommentsActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化方法
     */
    private void initView() {

        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));
        int statusHeight = mTintManager.getConfig().getStatusBarHeight();

        /**
         系统版本是否高于4.4
         */
        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKITKAT) {
            RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.CommentRootLayout);
            rootLayout.setPadding(0, statusHeight, 0, 0);
        }

        back = (ImageView) findViewById(R.id.iv_top_back);
        mListView = (ListView) findViewById(R.id.lv_activity_commentlist);
        et_input_comment = (EditText) findViewById(R.id.et_input_comment);
        bt_send_comment = (Button) findViewById(R.id.bt_send_comment);
        adapter = new CommentAdapter(this);

    }

}
