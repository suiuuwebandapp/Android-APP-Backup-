package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.CommentAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.entity.CommentList;
import com.minglang.suiuu.entity.LoopArticleCommentList;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 评论列表
 */
public class CommentsActivity extends BaseActivity {

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
    private String tripId;
    private JsonUtils jsonUtil = JsonUtils.getInstance();
    private List<LoopArticleCommentList> commentLists;
    private ProgressDialog dialog;
    private String rId;
    private String rTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        articleId = this.getIntent().getStringExtra("articleId");
        tripId = this.getIntent().getStringExtra("tripId");

        initView();

        ViewAction();

        if (!TextUtils.isEmpty(articleId))
            requestDataArticle();
        if (!TextUtils.isEmpty(tripId))
            requestDataSuiuu(tripId, "1");
    }

    /**
     * 初始化方法
     */
    private void initView() {
        if (isKITKAT) {
            RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.CommentRootLayout);
            rootLayout.setPadding(0, statusBarHeight, 0, 0);
        }

        back = (ImageView) findViewById(R.id.iv_top_back);
        mListView = (ListView) findViewById(R.id.lv_activity_commentlist);
        et_input_comment = (EditText) findViewById(R.id.et_input_comment);
        bt_send_comment = (Button) findViewById(R.id.bt_send_comment);

        adapter = new CommentAdapter(this);
        mListView.setAdapter(adapter);

        TextView tv_top_center = (TextView) findViewById(R.id.tv_top_center);
        tv_top_center.setVisibility(View.VISIBLE);
        tv_top_center.setText("评论");

        TextView tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        tv_top_right.setVisibility(View.GONE);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.load_wait));
        dialog.setCanceledOnTouchOutside(false);

    }

    //根据文章Id请求评论列表
    private void requestDataArticle() {
        dialog.show();
        RequestParams params = new RequestParams();
        params.addBodyParameter("articleId", articleId);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getCommentListByArticleId, new getCommentListCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    //根据随UUId请求评论列表
    private void requestDataSuiuu(String tripId, String pageNumber) {
        dialog.show();
        RequestParams params = new RequestParams();
        params.addBodyParameter("tripId", tripId);
        params.addBodyParameter("numb", "20");
        params.addBodyParameter("cPage", pageNumber);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getCommentListByTripId, new getCommentListCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
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
                if (TextUtils.isEmpty(commentContent)) {
                    Toast.makeText(CommentsActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                    if (!TextUtils.isEmpty(articleId)) {
                        requestArticleCommentSend(commentContent, rId);
                    } else {
                        requestSuiuuCommentSend(commentContent, rId, rTitle);
                    }
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoopArticleCommentList itemComment = commentLists.get(position);
                et_input_comment.setHint("回复" + itemComment.getNickname());
                rId = itemComment.getCommentId();
                rTitle = "@" + itemComment.getNickname();

            }
        });
    }

    //文章详情发送评论
    private void requestArticleCommentSend(String commentContent, String rId) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("articleId", articleId);
        params.addBodyParameter("content", commentContent);
        params.addBodyParameter("rId", rId);
        params.addBodyParameter("rTitle", rTitle);
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
        params.addBodyParameter("rId", rId);
        params.addBodyParameter("rTitle", rTitle);
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.suiuuCreateComment, new requestCommentSendCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();

    }

    class requestCommentSendCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            try {
                JSONObject json = new JSONObject(responseInfo.result);
                String status = json.getString("status");
                String data = json.getString("data");

                if ("1".equals(status) && "success".equals(data)) {
                    et_input_comment.setText("");
                    if (TextUtils.isEmpty(tripId)) {
                        requestDataArticle();
                    } else {
                        requestDataSuiuu(tripId, "1");
                    }

                    rId = null;
                    rTitle = null;
                    et_input_comment.setText("");
                    et_input_comment.setHint("");
                    Toast.makeText(CommentsActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(CommentsActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
                rId = null;
                rTitle = null;
                et_input_comment.setText("");
            }
        }
        @Override
        public void onFailure(HttpException error, String msg) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            rId = null;
            rTitle = null;
            et_input_comment.setText("");
            Toast.makeText(CommentsActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }

    }

    class getCommentListCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            CommentList list = jsonUtil.fromJSON(CommentList.class, responseInfo.result);
            if ("1".equals(list.getStatus())) {
                commentLists = list.getData().getData();
                // mListView.setAdapter(new CommentAdapter(CommentsActivity.this, commentLists));
                adapter.setList(commentLists);
                dialog.dismiss();
            } else {
                Toast.makeText(CommentsActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onFailure(HttpException error, String msg) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(CommentsActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }

    }

}
