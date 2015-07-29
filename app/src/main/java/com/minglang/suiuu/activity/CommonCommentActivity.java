package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.SuHttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

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
    private static final int COMMENT_SUCCESS = 20;
    private String articleId;
    private String tripId;
    private String rId;
    private String rTitle;
    private ImageView iv_top_back;
    private TextView tv_top_right;
    private ImageView iv_top_right_more;
    private TextView tv_top_center;
    private ProgressDialog dialog;
    private EditText et_input_comment;

    /**
     * 评论内容
     */
    private String commentContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_commnet);
        articleId = this.getIntent().getStringExtra("articleId");
        tripId = this.getIntent().getStringExtra("tripId");
        rId = this.getIntent().getStringExtra("rId");
        rTitle = this.getIntent().getStringExtra("nikeName");
        initView();
        viewAction();
    }

    private void initView() {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.load_wait));
        dialog.setCanceledOnTouchOutside(false);
        iv_top_right_more = (ImageView) findViewById(R.id.tv_top_right_more);
        iv_top_right_more.setVisibility(View.GONE);
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        tv_top_right.setVisibility(View.VISIBLE);
        iv_top_back = (ImageView) findViewById(R.id.iv_top_back);
        tv_top_center = (TextView) findViewById(R.id.tv_top_center);
        tv_top_center.setText(R.string.comment);
        et_input_comment = (EditText) findViewById(R.id.et_comment_content);
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
                    dialog.show();
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
        Log.i("suiuu","commentId= " + articleId);
        RequestParams params = new RequestParams();
        params.addBodyParameter("tpId", articleId);
        params.addBodyParameter("comment", commentContent);
        if (!TextUtils.isEmpty(rId)) {
            params.addBodyParameter("rId", rId);
            params.addBodyParameter("rTitle", rTitle);
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
        params.addBodyParameter("tripId", tripId);
        params.addBodyParameter("content", commentContent);
        if (!TextUtils.isEmpty(rId)) {
            params.addBodyParameter("rId", rId);
            params.addBodyParameter("rTitle", rTitle);
        }
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.suiuuCreateComment, new requestCommentSendCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();

    }

    class requestCommentSendCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Log.i("suiuu", "评论结果" + responseInfo.result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {
                JSONObject json = new JSONObject(responseInfo.result);
                int status = json.getInt("status");
                String data = json.getString("data");
                if (status == 1) {
                    Intent intent = CommonCommentActivity.this.getIntent();
                    if(!TextUtils.isEmpty(rId)) {
                        intent.putExtra("content", "@"+rTitle+" "+commentContent);
                    }else {
                        intent.putExtra("content", commentContent);
                    }
                    CommonCommentActivity.this.setResult(COMMENT_SUCCESS, intent);
                    Toast.makeText(CommonCommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(CommonCommentActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(CommonCommentActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }

    }
}
