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
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.CommentAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.entity.CommentList;
import com.minglang.suiuu.entity.LoopArticleCommentList;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 评论列表
 */
public class CommentsActivity extends BaseActivity {

    private static final String ARTICLE_ID = "articleId";
    private static final String TRIP_ID = "tripId";

    private static final String NUMB = "numb";
    private static final String C_PAGE = "cPage";

    private String articleId;
    private String tripId;

    /**
     * 返回键
     */
    @Bind(R.id.iv_top_back)
    ImageView back;

    /**
     * 评论列表
     */
    @Bind(R.id.lv_activity_commentlist)
    ListView mListView;

    @Bind(R.id.et_input_comment)
    EditText et_input_comment;

    @Bind(R.id.bt_send_comment)
    Button bt_send_comment;

    @Bind(R.id.tv_top_center)
    TextView tv_top_center;

    @Bind(R.id.tv_top_right)
    TextView tv_top_right;

    private CommentAdapter adapter;

    private List<LoopArticleCommentList> commentLists;

    private ProgressDialog dialog;

    private String rId;
    private String rTitle;

    private JsonUtils jsonUtil = JsonUtils.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        articleId = this.getIntent().getStringExtra(ARTICLE_ID);
        tripId = this.getIntent().getStringExtra(TRIP_ID);

        ButterKnife.bind(this);
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
        adapter = new CommentAdapter(this);
        mListView.setAdapter(adapter);

        tv_top_center.setVisibility(View.VISIBLE);
        tv_top_center.setText("评论");

        tv_top_right.setVisibility(View.GONE);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.load_wait));
        dialog.setCanceledOnTouchOutside(false);

    }

    //根据文章Id请求评论列表
    private void requestDataArticle() {
        dialog.show();
        Map<String,String> map = new HashMap<>();
        map.put("articleId", articleId);
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.getCommentListByArticleId + "?token=" + SuiuuInfo.ReadAppTimeSign(CommentsActivity.this), new getCommentListCallBack(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //根据随UUId请求评论列表
    private void requestDataSuiuu(String tripId, String pageNumber) {
        dialog.show();
        Map<String,String> map = new HashMap<>();
        map.put(TRIP_ID,tripId);
        map.put(NUMB, "20");
        map.put(C_PAGE, pageNumber);
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.getCommentListByTripId + "?token=" + SuiuuInfo.ReadAppTimeSign(CommentsActivity.this), new getCommentListCallBack(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Map<String,String> map = new HashMap<>();
        map.put("articleId", articleId);
        map.put("content", commentContent);
        map.put("rId", rId);
        map.put("rTitle", rTitle);
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.articleCreateComment + "?token=" + SuiuuInfo.ReadAppTimeSign(CommentsActivity.this), new requestCommentSendCallBack(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //随游详情发送评论
    private void requestSuiuuCommentSend(String commentContent, String rId, String rTitle) {
        Map<String,String> map = new HashMap<>();
        map.put("tripId", tripId);
        map.put("content", commentContent);
        map.put("rId", rId);
        map.put("rTitle", rTitle);
        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.suiuuCreateComment + "?token=" + SuiuuInfo.ReadAppTimeSign(CommentsActivity.this), new requestCommentSendCallBack(), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class requestCommentSendCallBack extends OkHttpManager.ResultCallback<String>{
        @Override
        public void onError(Request request, Exception e) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            rId = null;
            rTitle = null;
            et_input_comment.setText("");
            Toast.makeText(CommentsActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            try {
                JSONObject json = new JSONObject(response);
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
    }

    class getCommentListCallBack extends OkHttpManager.ResultCallback<String> {
        @Override
        public void onError(Request request, Exception e) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(CommentsActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onResponse(String response) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            CommentList list = jsonUtil.fromJSON(CommentList.class, response);
            if ("1".equals(list.getStatus())) {
                commentLists = list.getData().getData();
                // mListView.setAdapter(new CommentAdapter(CommentsActivity.this, commentLists));
                adapter.setList(commentLists);
                dialog.dismiss();
            } else {
                Toast.makeText(CommentsActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
