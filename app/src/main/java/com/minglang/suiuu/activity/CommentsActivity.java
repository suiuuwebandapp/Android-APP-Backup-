package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.CommentAdapter;
import com.minglang.suiuu.entity.CommentList;
import com.minglang.suiuu.entity.LoopArticleCommentList;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtil;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.SystemBarTintManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
    private String tripId;
    private JsonUtil jsonUtil = JsonUtil.getInstance();
    private List<LoopArticleCommentList> commentLists;
    private TextView tv_top_right, tv_top_center;
    private ProgressDialog dialog;
    private String Verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        articleId = this.getIntent().getStringExtra("articleId");
        tripId = this.getIntent().getStringExtra("tripId");

        Log.i("suiuu", "articleId = " + articleId);
        initView();

        ViewAction();
        if (!TextUtils.isEmpty(articleId))
            origainDataArtcle();
        if (!TextUtils.isEmpty(tripId))
            origainDataSuiuu(tripId,"1");
    }
    //根据文章Id请求评论列表
    private void origainDataArtcle() {
        dialog.show();
        RequestParams params = new RequestParams();
        params.addBodyParameter("articleId", articleId);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getCommentListByArticleId, new getCommentListCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }
    //根据随UUId请求评论列表
    private void origainDataSuiuu(String tripId,String pageNumber) {
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
                    requestCommentSend(commentContent, "0");
                }
            }
        });
    }

    private void requestCommentSend(String commentContent, String rId) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("articleId", articleId);
        params.addBodyParameter("content", commentContent);
        params.addBodyParameter("rId", rId);
        params.addBodyParameter(HttpServicePath.key, Verification);
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.artilceCreateComment, new requestComentSendCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    class requestComentSendCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            try {
                JSONObject json = new JSONObject(responseInfo.result);
                String status = json.getString("status");
                String data = json.getString("data");
                if ("1".equals(status) && "success".equals(data)) {
                    et_input_comment.setText("");
                    origainDataArtcle();
                    Toast.makeText(CommentsActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(CommentsActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
            }
            Log.i("suiuu", "success=" + responseInfo.result);
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Log.e("suiuu", "网络请求失败:" + msg);
            Toast.makeText(CommentsActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    class getCommentListCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Log.i("suiuu", "getList+" + responseInfo.result);
            CommentList list = jsonUtil.fromJSON(CommentList.class, responseInfo.result);
            if ("1".equals(list.getStatus())) {
                commentLists = list.getData().getData();
                mListView.setAdapter(new CommentAdapter(CommentsActivity.this, commentLists));
                dialog.dismiss();
            } else {
                Toast.makeText(CommentsActivity.this, "网络异常，请稍候再试！", Toast.LENGTH_SHORT).show();

            }
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
        Verification = SuiuuInfo.ReadVerification(this);
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
        tv_top_center = (TextView) findViewById(R.id.tv_top_center);
        tv_top_center.setText("评论");
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        tv_top_right.setVisibility(View.GONE);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.load_wait));
        dialog.setCanceledOnTouchOutside(false);

    }


}
