package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/7/21 14:06
 * 修改人：Administrator
 * 修改时间：2015/7/21 14:06
 * 修改备注：
 */
public class SendCommentActivity extends BaseAppCompatActivity {

    private static final String TAG = SendCommentActivity.class.getSimpleName();

    private static final int COMMENT_SUCCESS = 20;

    private static final String ARTICLE_ID = "articleId";
    private static final String TRIP_ID = "tripId";
    private static final String R_ID = "rId";
    private static final String NICK_NAME = "nickName";

    private static final String TP_ID = "tpId";
    private static final String COMMENT = "comment";
    private static final String R_TITLE = "rTitle";
    private static final String CONTENT = "content";

    private static final String STATUS = "status";

    private String articleId;
    private String tripId;
    private String rId;
    private String rTitle;

    @BindString(R.string.load_wait)
    String DialogMessage;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.DataException)
    String DataException;

    @Bind(R.id.send_comment_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.send_comment_edit_text)
    EditText inputComment;

    private ProgressDialog progressDialog;

    /**
     * 评论内容
     */
    private String commentContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_commnet);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        articleId = this.getIntent().getStringExtra(ARTICLE_ID);
        tripId = this.getIntent().getStringExtra(TRIP_ID);
        rId = this.getIntent().getStringExtra(R_ID);
        rTitle = this.getIntent().getStringExtra(NICK_NAME);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMessage);
        progressDialog.setCanceledOnTouchOutside(false);

        setSupportActionBar(toolbar);

        try {
            token = URLEncoder.encode(SuiuuInfo.ReadAppTimeSign(this), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            L.e(TAG, "解码TimeSign异常:" + e.getMessage());
        }
    }

    /**
     * 发送旅途评论
     *
     * @param commentContent 评论内容
     * @param rId            ID
     * @param rTitle         标题
     */
    private void sendTripImageComment(String commentContent, String rId, String rTitle) {
        Map<String, String> map = new HashMap<>();
        map.put(TP_ID, articleId);
        map.put(COMMENT, commentContent);

        if (!TextUtils.isEmpty(rId)) {
            map.put(R_ID, rId);
            map.put(R_TITLE, rTitle);
        }

        String url = HttpNewServicePath.articleCreateComment + "?" + TOKEN + "=" + token;
        L.i(TAG, "发送旅途评论URL:" + url);
        try {
            OkHttpManager.onPostAsynRequest(url, new sendCommentCallback(), map);
        } catch (IOException e) {
            L.e(TAG, "旅途评论发送失败:" + e.getMessage());
            Toast.makeText(SendCommentActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 发送随游评论
     *
     * @param commentContent 评论内容
     * @param rId            ID
     * @param rTitle         标题
     */
    private void sendSuiuuComment(String commentContent, String rId, String rTitle) {
        Map<String, String> map = new HashMap<>();
        map.put(TRIP_ID, tripId);
        map.put(CONTENT, commentContent);

        if (!TextUtils.isEmpty(rId)) {
            map.put(R_ID, rId);
            map.put(R_TITLE, rTitle);
        }

        String url = HttpNewServicePath.suiuuCreateComment + "?" + TOKEN + "=" + token;
        L.i(TAG, "发送随游评论URL:" + url);
        try {
            OkHttpManager.onPostAsynRequest(url, new sendCommentCallback(), map);
        } catch (IOException e) {
            L.e(TAG, "随游评论发送失败:" + e.getMessage());
            Toast.makeText(SendCommentActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_send_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.confirm_send_comment:
                commentContent = inputComment.getText().toString().trim();
                if (TextUtils.isEmpty(commentContent)) {
                    Toast.makeText(SendCommentActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    if (!TextUtils.isEmpty(articleId)) {
                        sendTripImageComment(commentContent, rId, rTitle);
                    } else {
                        sendSuiuuComment(commentContent, rId, rTitle);
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class sendCommentCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String str) {
            try {
                JSONObject json = new JSONObject(str);
                int status = json.getInt(STATUS);
                switch (status) {
                    case 1:
                        Intent intent = SendCommentActivity.this.getIntent();
                        if (!TextUtils.isEmpty(rId)) {
                            intent.putExtra(CONTENT, "@" + rTitle + " " + commentContent);
                        } else {
                            intent.putExtra(CONTENT, commentContent);
                        }
                        SendCommentActivity.this.setResult(COMMENT_SUCCESS, intent);
                        Toast.makeText(SendCommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
            } catch (JSONException e) {
                L.e(TAG, "解析失败:" + e.getMessage());
                Toast.makeText(SendCommentActivity.this, DataException, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "评论发送失败:" + e.getMessage());
            Toast.makeText(SendCommentActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }

    }

}