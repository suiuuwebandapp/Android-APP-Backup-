package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.ProblemItemAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.entity.ProblemDetails;
import com.minglang.suiuu.entity.ProblemDetails.CommunityItemData.AnswerEntity;
import com.minglang.suiuu.entity.ProblemDetails.CommunityItemData.AttentionEntity;
import com.minglang.suiuu.entity.ProblemDetails.CommunityItemData.QuestionEntity;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.StatusBarCompat;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.phillipcalvin.iconbutton.IconButton;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 问题详情页
 */
public class ProblemDetailsActivity extends BaseAppCompatActivity {

    private static final String TAG = ProblemDetailsActivity.class.getSimpleName();

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String TAGS = "tag";

    private static final String Q_ID = "qId";

    private static final String STATUS = "status";

    private static final String ATTENTION_ID = "attentionId";

    private String qID;

    private String strID;

    private String title;

    private String tags;

    @BindString(R.string.Problem)
    String Problem;

    @BindColor(R.color.white)
    int titleTextColor;

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.AttentionQuestionSuccess)
    String AttentionSuccess;

    @BindString(R.string.AttentionQuestionFailure)
    String AttentionFailure;

    @BindString(R.string.AttentionQuestionException)
    String AttentionException;

    @BindString(R.string.CancelAttentionSuccess)
    String CancelAttentionSuccess;

    @BindString(R.string.CancelAttentionFailure)
    String CancelAttentionFailure;

    @BindString(R.string.CancelAttentionException)
    String CancelAttentionException;

    /**
     * 未关注的Selector
     */
    @BindDrawable(R.drawable.icon_no_attention_selector)
    Drawable noAttentionSelector;

    /**
     * 已关注的Selector
     */
    @BindDrawable(R.drawable.icon_is_attention_selector)
    Drawable isAttentionSelector;

    @Bind(R.id.problem_details_toolbar)
    Toolbar toolbar;

    @Bind(R.id.item_problem_layout_1_head_view)
    SimpleDraweeView headImageView;

    @Bind(R.id.item_problem_layout_1_problem)
    TextView problemTitle;

    @Bind(R.id.item_problem_layout_1_flow_layout)
    FlowLayout tagLayout;

    @Bind(R.id.item_problem_layout_2_problem)
    TextView problemContent;

    @Bind(R.id.item_problem_layout_3_attention)
    IconButton attentionBtn;

    @Bind(R.id.item_problem_layout_3_answer)
    IconButton answerBtn;

    @Bind(R.id.community_details_no_scroll_list_view)
    NoScrollBarListView noScrollBarListView;

    private ProgressDialog progressDialog;

    private ProblemItemAdapter adapter;

    private ProblemDetails problemDetails;

    private Context context;

    private boolean isAttention = false;

    private String attentionId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_details);

        strID = getIntent().getStringExtra(ID);
        title = getIntent().getStringExtra(TITLE);
        tags = getIntent().getStringExtra(TAGS);

        StatusBarCompat.compat(this);
        ButterKnife.bind(this);

        initView();
        viewAction();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        getProblemData(buildUrl(HttpNewServicePath.getProblemDetailsPath, strID));

    }

    /**
     * 初始化方法
     */
    private void initView() {
        context = this;

        verification = SuiuuInfo.ReadVerification(this);

        try {
            token = URLEncoder.encode(SuiuuInfo.ReadAppTimeSign(this), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        if (!TextUtils.isEmpty(title)) {
            toolbar.setTitle(title);
        } else {
            toolbar.setTitle(Problem);
        }

        toolbar.setTitleTextColor(titleTextColor);
        setSupportActionBar(toolbar);

        ImageView tag = (ImageView) LayoutInflater.from(this).inflate(R.layout.layout_image_tag, tagLayout, false);
        tagLayout.addView(tag);

        if (!TextUtils.isEmpty(tags)) {
            String[] tagArray = tags.split(",");
            for (String tagPosition : tagArray) {
                TextView tagView = (TextView) LayoutInflater.from(this).inflate(R.layout.layout_text_tag, tagLayout, false);
                tagView.setText(tagPosition);
                tagLayout.addView(tagView);
            }
        }

        adapter = new ProblemItemAdapter(this);
        noScrollBarListView.setAdapter(adapter);
    }

    /**
     * 控件相关事件
     */
    private void viewAction() {

        headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProblemDetailsActivity.this, PersonalMainPagerActivity.class);
                intent.putExtra(USER_SIGN, problemDetails.getData().getQuestion().get(0).getQUserSign());
                startActivity(intent);
            }
        });

        attentionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAttention) {
                    getCancelAttentionProblem(attentionId);
                } else {
                    getAttentionProblem(qID);
                }
            }
        });

        answerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(qID)) {
                    Intent intent = new Intent(ProblemDetailsActivity.this, AnswerActivity.class);
                    intent.putExtra(Q_ID, qID);
                    startActivity(intent);
                }
            }
        });

    }

    /**
     * 构造问题详情请求URL
     *
     * @param baseUrl 基础URL
     * @param id      问题ID
     * @return 带参数的URL
     */
    private String buildUrl(String baseUrl, String id) {
        String[] keyArray = new String[]{HttpNewServicePath.key, ID, TOKEN};
        String[] valueArray = new String[]{verification, id, token};
        return addUrlAndParams(baseUrl, keyArray, valueArray);
    }

    /**
     * 发起问题详情网络请求
     *
     * @param url 问题详情请求URL
     */
    private void getProblemData(String url) {
        L.i(TAG, "问题详情URL:" + url);
        try {
            OkHttpManager.onGetAsynRequest(url, new ProblemDetailsResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 关注问题
     *
     * @param id 问题ID
     */
    private void getAttentionProblem(String id) {
        OkHttpManager.Params params = new OkHttpManager.Params(ID, id);
        String url = HttpNewServicePath.getAttentionQuestionPath + "?" + TOKEN + "=" + token;

        try {
            OkHttpManager.onPostAsynRequest(url, new OkHttpManager.ResultCallback<String>() {

                @Override
                public void onResponse(String response) {
                    L.i(TAG, "关注问题返回的数据:" + response);
                    if (TextUtils.isEmpty(response)) {
                        Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                    } else try {
                        JSONObject object = new JSONObject(response);
                        String stats = object.getString(STATUS);
                        switch (stats) {
                            case "1":
                                isAttention = true;
                                attentionId = object.getString(DATA);
                                attentionBtn.setCompoundDrawablesWithIntrinsicBounds(isAttentionSelector, null, null, null);
                                Toast.makeText(context, AttentionSuccess, Toast.LENGTH_SHORT).show();
                                break;

                            case "-1":
                                isAttention = false;
                                attentionBtn.setCompoundDrawablesWithIntrinsicBounds(noAttentionSelector, null, null, null);
                                Toast.makeText(context, AttentionException, Toast.LENGTH_SHORT).show();
                                break;

                            case "-2":
                                isAttention = false;
                                attentionBtn.setCompoundDrawablesWithIntrinsicBounds(noAttentionSelector, null, null, null);
                                Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                                break;

                            default:
                                isAttention = false;
                                attentionBtn.setCompoundDrawablesWithIntrinsicBounds(noAttentionSelector, null, null, null);
                                Toast.makeText(context, AttentionFailure, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (Exception e) {
                        L.e(TAG, "解析失败:" + e.getMessage());
                        isAttention = false;
                        attentionBtn.setCompoundDrawablesWithIntrinsicBounds(noAttentionSelector, null, null, null);
                        Toast.makeText(context, AttentionException, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Request request, Exception e) {
                    L.e(TAG, "关注问题数据异常:" + e.getMessage());
                    isAttention = false;
                    attentionBtn.setCompoundDrawablesWithIntrinsicBounds(noAttentionSelector, null, null, null);
                    Toast.makeText(context, AttentionFailure, Toast.LENGTH_SHORT).show();
                }

            }, params);
        } catch (IOException e) {
            L.e(TAG, "关注问题失败:" + e.getMessage());
            isAttention = false;
            attentionBtn.setCompoundDrawablesWithIntrinsicBounds(noAttentionSelector, null, null, null);
            Toast.makeText(context, AttentionFailure, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消关注
     *
     * @param id 问题ID
     */
    private void getCancelAttentionProblem(String id) {
        OkHttpManager.Params params = new OkHttpManager.Params(ATTENTION_ID, id);
        String url = HttpNewServicePath.getCancelAttentionPath + "?" + TOKEN + "=" + token;
        try {
            OkHttpManager.onPostAsynRequest(url, new OkHttpManager.ResultCallback<String>() {

                @Override
                public void onResponse(String response) {
                    L.i(TAG, "取消关注返回的数据:" + response);
                    if (TextUtils.isEmpty(response)) {
                        Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                    } else try {
                        JSONObject object = new JSONObject(response);
                        String status = object.getString(STATUS);
                        switch (status) {
                            case "1":
                                isAttention = false;
                                attentionBtn.setCompoundDrawablesWithIntrinsicBounds(noAttentionSelector, null, null, null);
                                Toast.makeText(context, CancelAttentionSuccess, Toast.LENGTH_SHORT).show();
                                break;

                            case "-1":
                                isAttention = true;
                                attentionBtn.setCompoundDrawablesWithIntrinsicBounds(isAttentionSelector, null, null, null);
                                Toast.makeText(context, CancelAttentionException, Toast.LENGTH_SHORT).show();
                                break;

                            case "-2":
                                isAttention = true;
                                attentionBtn.setCompoundDrawablesWithIntrinsicBounds(isAttentionSelector, null, null, null);
                                Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                                break;

                            default:
                                isAttention = true;
                                attentionBtn.setCompoundDrawablesWithIntrinsicBounds(isAttentionSelector, null, null, null);
                                Toast.makeText(context, CancelAttentionException, Toast.LENGTH_SHORT).show();
                                break;

                        }
                    } catch (Exception e) {
                        L.e(TAG, "取消关注数据解析失败:" + e.getMessage());
                        isAttention = true;
                        attentionBtn.setCompoundDrawablesWithIntrinsicBounds(isAttentionSelector, null, null, null);
                        Toast.makeText(context, CancelAttentionException, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Request request, Exception e) {
                    isAttention = true;
                    attentionBtn.setCompoundDrawablesWithIntrinsicBounds(isAttentionSelector, null, null, null);
                    Toast.makeText(context, CancelAttentionFailure, Toast.LENGTH_SHORT).show();
                }

            }, params);
        } catch (Exception e) {
            L.e(TAG, "取消关注错误:" + e.getMessage());
            isAttention = true;
            attentionBtn.setCompoundDrawablesWithIntrinsicBounds(isAttentionSelector, null, null, null);
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 隐藏加载的进度框等
     */
    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 绑定数据到View
     *
     * @param str Json字符串
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(this, NoData, Toast.LENGTH_SHORT).show();
        } else try {
            problemDetails = JsonUtils.getInstance().fromJSON(ProblemDetails.class, str);
            if (problemDetails != null) {
                ProblemDetails.CommunityItemData itemData = problemDetails.getData();
                if (itemData != null) {

                    List<AttentionEntity> attentionEntityList = itemData.getAttention();
                    if (attentionEntityList != null && attentionEntityList.size() > 0) {
                        L.i(TAG, "已关注");
                        isAttention = true;
                        attentionId = attentionEntityList.get(0).getAttentionId();
                        attentionBtn.setCompoundDrawablesWithIntrinsicBounds(isAttentionSelector, null, null, null);
                    } else {
                        L.i(TAG, "未关注");
                        isAttention = false;
                        attentionBtn.setCompoundDrawablesWithIntrinsicBounds(noAttentionSelector, null, null, null);
                    }

                    List<QuestionEntity> questionList = itemData.getQuestion();
                    if (questionList != null && questionList.size() > 0) {
                        QuestionEntity questionEntity = questionList.get(0);

                        qID = questionEntity.getQId();
                        if (TextUtils.isEmpty(tags)) {
                            tags = questionEntity.getQTag();
                        }

                        String headImagePath = questionEntity.getHeadImg();
                        if (!TextUtils.isEmpty(headImagePath)) {
                            headImageView.setImageURI(Uri.parse(headImagePath));
                        }

                        String strTitle = questionEntity.getQTitle();
                        if (!TextUtils.isEmpty(strTitle)) {
                            problemTitle.setText(strTitle);
                        } else {
                            problemTitle.setText("");
                        }

                        String strContent = questionEntity.getQContent();
                        if (!TextUtils.isEmpty(strContent)) {
                            problemContent.setText(Html.fromHtml(strContent));
                        } else {
                            problemContent.setText("");
                        }
                    }

                    List<AnswerEntity> list = itemData.getAnswer();
                    if (list != null && list.size() > 0) {
                        adapter.setList(list);
                    }

                }
            }
        } catch (Exception e) {
            L.e(TAG, "解析错误:" + e.getMessage());
            Toast.makeText(this, DataError, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ProblemDetailsResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "问题详情数据:" + response);
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "问题详情数据异常:" + e.getMessage());
            Toast.makeText(ProblemDetailsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }

    }

}