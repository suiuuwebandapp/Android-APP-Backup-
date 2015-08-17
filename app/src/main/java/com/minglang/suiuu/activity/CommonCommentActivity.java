package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.SuHttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

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
public class CommonCommentActivity extends BaseActivity {

    private static final String TAG = CommonCommentActivity.class.getSimpleName();

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
    String wait;

    @BindString(R.string.comment)
    String strComment;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @Bind(R.id.tv_top_right_more)
    ImageView iv_top_right_more;

    @Bind(R.id.iv_top_back)
    ImageView iv_top_back;

    @Bind(R.id.tv_top_right)
    TextView tv_top_right;

    @Bind(R.id.tv_top_center)
    TextView tv_top_center;

    @Bind(R.id.et_comment_content)
    EditText et_input_comment;

    private ProgressDialog progressDialog;

    /**
     * 评论内容
     */
    private String commentContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_commnet);
        ButterKnife.bind(this);
        initView();
        viewAction();
    }

    private void initView() {
        articleId = this.getIntent().getStringExtra(ARTICLE_ID);
        tripId = this.getIntent().getStringExtra(TRIP_ID);
        rId = this.getIntent().getStringExtra(R_ID);
        rTitle = this.getIntent().getStringExtra(NICK_NAME);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(wait);
        progressDialog.setCanceledOnTouchOutside(false);

        iv_top_right_more.setVisibility(View.GONE);
        tv_top_right.setVisibility(View.VISIBLE);

        tv_top_center.setText(strComment);
    }

    private void viewAction() {
        iv_top_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_top_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentContent = et_input_comment.getText().toString().trim();
                if (TextUtils.isEmpty(commentContent)) {
                    Toast.makeText(CommonCommentActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    if (!TextUtils.isEmpty(articleId)) {
                        requestArticleCommentSend(commentContent, rId, rTitle);
                    } else {
                        requestSuiuuCommentSend(commentContent, rId, rTitle);
                    }
                }
            }
        });
    }

    //文章详情发送评论
    private void requestArticleCommentSend(String commentContent, String rId, String rTitle) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(TP_ID, articleId);
        params.addBodyParameter(COMMENT, commentContent);

        if (!TextUtils.isEmpty(rId)) {
            params.addBodyParameter(R_ID, rId);
            params.addBodyParameter(R_TITLE, rTitle);
        }

        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.articleCreateComment, new requestCommentSendCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();

    }

    //随游详情发送评论
    private void requestSuiuuCommentSend(String commentContent, String rId, String rTitle) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(TRIP_ID, tripId);
        params.addBodyParameter(CONTENT, commentContent);

        if (!TextUtils.isEmpty(rId)) {
            params.addBodyParameter(R_ID, rId);
            params.addBodyParameter(R_TITLE, rTitle);
        }
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.suiuuCreateComment, new requestCommentSendCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();

    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    class requestCommentSendCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            hideDialog();
            String str = responseInfo.result;
            try {
                JSONObject json = new JSONObject(str);
                int status = json.getInt(STATUS);
                if (status == 1) {
                    Intent intent = CommonCommentActivity.this.getIntent();
                    if (!TextUtils.isEmpty(rId)) {
                        intent.putExtra(CONTENT, "@" + rTitle + " " + commentContent);
                    } else {
                        intent.putExtra(CONTENT, commentContent);
                    }

                    CommonCommentActivity.this.setResult(COMMENT_SUCCESS, intent);
                    Toast.makeText(CommonCommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (JSONException e) {
                DeBugLog.e(TAG, "解析失败:" + e.getMessage());
                Toast.makeText(CommonCommentActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            hideDialog();
            DeBugLog.e(TAG, "HttpException:" + error.getMessage() + ",Error:" + msg);
            Toast.makeText(CommonCommentActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

}