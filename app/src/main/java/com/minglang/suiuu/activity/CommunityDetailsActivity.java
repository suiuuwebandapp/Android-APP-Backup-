package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshScrollView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.CommunityItemAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.entity.CommunityItem;
import com.minglang.suiuu.entity.CommunityItem.CommunityItemData.AnswerEntity;
import com.minglang.suiuu.entity.CommunityItem.CommunityItemData.QuestionEntity;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
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
public class CommunityDetailsActivity extends BaseAppCompatActivity {

    private static final String TAG = CommunityDetailsActivity.class.getSimpleName();

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String TAGS = "tag";

    private static final String Q_ID = "qId";

    private static final String STATUS = "status";
    private static final String SUC_VALUE = "1";

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

    @BindString(R.string.AttentionQuestion_SUC)
    String attention_suc;

    @BindString(R.string.AttentionQuestion_FAI)
    String attention_fai;

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

    @Bind(R.id.community_details_refresh_scroll_view)
    PullToRefreshScrollView pullToRefreshScrollView;

    @Bind(R.id.community_details_no_scroll_list_view)
    NoScrollBarListView noScrollBarListView;

    @Bind(R.id.community_details_toolbar)
    Toolbar toolbar;

    @Bind(R.id.item_community_layout_1_head_view)
    SimpleDraweeView headImageView;

    @Bind(R.id.item_community_layout_1_problem)
    TextView problemTitle;

    @Bind(R.id.item_community_layout_1_flow_layout)
    FlowLayout tagLayout;

    @Bind(R.id.item_community_layout_2_problem)
    TextView problemContent;

    @Bind(R.id.item_community_layout_3_attention)
    IconButton attentionBtn;

    @Bind(R.id.item_community_layout_3_answer)
    IconButton answerBtn;

    private ProgressDialog progressDialog;

    private CommunityItemAdapter communityItemAdapter;

    private CommunityItem communityItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_item);

        strID = getIntent().getStringExtra(ID);
        title = getIntent().getStringExtra(TITLE);
        tags = getIntent().getStringExtra(TAGS);
        DeBugLog.i(TAG, "Tag Array:" + tags);

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
        if (!TextUtils.isEmpty(title)) {
            toolbar.setTitle(title);
        } else {
            toolbar.setTitle(Problem);
        }

        toolbar.setTitleTextColor(titleTextColor);
        setSupportActionBar(toolbar);

        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        ImageView tag = (ImageView) LayoutInflater.from(this).inflate(R.layout.layout_image_tag, tagLayout, false);
        tagLayout.addView(tag);

        if (!TextUtils.isEmpty(tags)) {
            String[] tagArray = tags.split(",");
            for (String tagPosition : tagArray) {
                TextView tagView = (TextView) LayoutInflater.from(CommunityDetailsActivity.this)
                        .inflate(R.layout.layout_text_tag, tagLayout, false);
                tagView.setText(tagPosition);
                tagLayout.addView(tagView);
            }
        }

        communityItemAdapter = new CommunityItemAdapter(this);
        noScrollBarListView.setAdapter(communityItemAdapter);

        verification = SuiuuInfo.ReadVerification(this);

        try {
            token = URLEncoder.encode(SuiuuInfo.ReadAppTimeSign(this), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 控件相关事件
     */
    private void viewAction() {

        headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityDetailsActivity.this, PersonalMainPagerActivity.class);
                intent.putExtra(USER_SIGN, communityItem.getData().getQuestion().get(0).getQUserSign());
                startActivity(intent);
            }
        });

        attentionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAttentionProblem(qID);
            }
        });

        answerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(qID)) {
                    Intent intent = new Intent(CommunityDetailsActivity.this, AnswerActivity.class);
                    intent.putExtra(Q_ID, qID);
                    startActivity(intent);
                }
            }
        });

        noScrollBarListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }

        });

        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                String label = DateUtils.formatDateTime(CommunityDetailsActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                getProblemData(buildUrl(HttpNewServicePath.getProblemDetailsPath, strID));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                String label = DateUtils.formatDateTime(CommunityDetailsActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                pullToRefreshScrollView.onRefreshComplete();
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
        String[] keyArray = new String[]{HttpServicePath.key, ID, TOKEN};
        String[] valueArray = new String[]{verification, id, token};
        return addUrlAndParams(baseUrl, keyArray, valueArray);
    }

    /**
     * 发起问题详情网络请求
     *
     * @param url 问题详情请求URL
     */
    private void getProblemData(String url) {
        try {
            OkHttpManager.onGetAsynRequest(url, new ProblemDetailsResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getAttentionProblem(String id) {
        OkHttpManager.Params params = new OkHttpManager.Params(ID, id);

        String url = HttpNewServicePath.getAttentionQuestionPath + "?" + TOKEN + "=" + token;
        DeBugLog.i(TAG, "关注URL:" + url);

        try {
            OkHttpManager.onPostAsynRequest(url, new AttentionProblemResultCallback(), params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏加载的进度框等
     */
    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        pullToRefreshScrollView.onRefreshComplete();
    }

    /**
     * 绑定数据到View
     *
     * @param str Json字符串
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(this, NoData, Toast.LENGTH_SHORT).show();
        } else {
            try {
                communityItem = JsonUtils.getInstance().fromJSON(CommunityItem.class, str);
                if (communityItem != null) {
                    CommunityItem.CommunityItemData itemData = communityItem.getData();

                    if (itemData != null) {
                        List<CommunityItem.CommunityItemData.AttentionEntity> attentionEntityList
                                = itemData.getAttention();
                        if (attentionEntityList != null && attentionEntityList.size() > 0) {
                            DeBugLog.i(TAG, "已关注");
                            attentionBtn.setCompoundDrawablesWithIntrinsicBounds(isAttentionSelector, null, null, null);
                        } else {
                            DeBugLog.i(TAG, "未关注");
                            attentionBtn.setCompoundDrawablesWithIntrinsicBounds(noAttentionSelector, null, null, null);
                        }

                        List<QuestionEntity> questionList = itemData.getQuestion();

                        if (questionList != null && questionList.size() > 0) {
                            QuestionEntity questionEntity = questionList.get(0);

                            qID = questionEntity.getQId();

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
                            communityItemAdapter.setList(list);
                        }
                    }
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析错误:" + e.getMessage());
                Toast.makeText(this, DataError, Toast.LENGTH_SHORT).show();
            }
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
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "问题详情数据异常:" + e.getMessage());
            Toast.makeText(CommunityDetailsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "问题详情数据:" + response);
            hideDialog();
            bindData2View(response);
        }

    }

    private class AttentionProblemResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "关注问题数据异常:" + e.getMessage());
            Toast.makeText(CommunityDetailsActivity.this, attention_fai, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "关注问题返回的数据:" + response);
            try {
                JSONObject object = new JSONObject(response);
                String stats = object.getString(STATUS);
                if (stats.equals(SUC_VALUE)) {
                    Toast.makeText(CommunityDetailsActivity.this, attention_suc, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CommunityDetailsActivity.this, attention_fai, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析失败:" + e.getMessage());
            }
        }

    }

}