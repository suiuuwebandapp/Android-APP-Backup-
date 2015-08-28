package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
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
import com.minglang.suiuu.entity.Tag;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuHttp;
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

    private static final String Q_ID = "qId";

    private static final String STATUS = "status";
    private static final String SUC_VALUE = "1";

    private String qID;

    private String strID;

    private String title;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_item);

        strID = getIntent().getStringExtra(ID);
        title = getIntent().getStringExtra(TITLE);
        DeBugLog.i(TAG, "strID:" + strID);

        ButterKnife.bind(this);
        initView();
        viewAction();
        getTagList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCommunityItemDetails(buildRequestParams(strID));

        //        if (progressDialog != null && !progressDialog.isShowing()) {
        //            progressDialog.show();
        //        }
        //        getProblemData(buildUrl(HttpNewServicePath.getProblemDetailsPath, strID));

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

        attentionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                getAttentionRequest(buildAttentionParams(qID));
                getAttentionProblem(buildUrl(HttpNewServicePath.getAttentionQuestionPath, qID));
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

                getCommunityItemDetails(buildRequestParams(strID));
                //                getProblemData(buildUrl(HttpNewServicePath.getProblemDetailsPath, strID));
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
     * 获取系统标签
     */
    private void getTagList() {
        //        SuiuuHttp httpRequest = new SuiuuHttp(HttpRequest.HttpMethod.GET,
        //                HttpNewServicePath.getDefaultTagListPath, new TagRequestCallBack());
        //        httpRequest.executive();

        try {
            String url = HttpNewServicePath.getDefaultTagListPath + "?" + TOKEN + "=" + token;
            DeBugLog.i(TAG, "TAG URL:" + url);
            OkHttpManager.onGetAsynRequest(url, new TagResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            DeBugLog.e(TAG, "获取标签异常:" + e.getMessage());
        }

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

    private void getAttentionProblem(String url) {
        try {
            OkHttpManager.onGetAsynRequest(url, new AttentionProblemResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //******************************旧版网络请求************************************************************

    /**
     * 构造问题详情网络请求参数
     *
     * @param id 问题ID
     * @return 网络请求参数
     */
    private RequestParams buildRequestParams(String id) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter(ID, id);
        return params;
    }

    /**
     * 问题详情网络请求
     *
     * @param params 网络请求参数
     */
    private void getCommunityItemDetails(RequestParams params) {
        SuiuuHttp httpRequest = new SuiuuHttp(HttpRequest.HttpMethod.POST,
                HttpServicePath.getProblemDetailsPath, new CommunityItemRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    /**
     * 构造关注问题的网络请求参数
     *
     * @param id 问题ID
     * @return 请求参数
     */
    private RequestParams buildAttentionParams(String id) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, verification);
        params.addBodyParameter(ID, id);
        return params;
    }

    /**
     * 关注问题的网络请求
     *
     * @param params 请求参数
     */
    private void getAttentionRequest(RequestParams params) {
        SuiuuHttp httpRequest = new SuiuuHttp(HttpRequest.HttpMethod.POST,
                HttpServicePath.getAttentionQuestionPath, new AttentionQuestionRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
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
                CommunityItem communityItem = JsonUtils.getInstance().fromJSON(CommunityItem.class, str);
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
                            DeBugLog.i(TAG, "title:" + strTitle);

                            String strContent = questionEntity.getQContent();
                            if (!TextUtils.isEmpty(strContent)) {
                                problemContent.setText(strContent);
                            } else {
                                problemContent.setText("");
                            }
                            DeBugLog.i(TAG, "content:" + strContent);
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

    /**
     * 问题详情页网络请求回调接口
     */
    private class CommunityItemRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            DeBugLog.i(TAG, "返回的数据:" + responseInfo.result);
            hideDialog();
            bindData2View(responseInfo.result);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",Error:" + s);
            hideDialog();
            Toast.makeText(CommunityDetailsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 得到Tag的网络请求回调接口
     */
    private class TagRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String str = responseInfo.result;
            if (!TextUtils.isEmpty(str)) {
                Tag tagInfo = JsonUtils.getInstance().fromJSON(Tag.class, str);
                if (tagInfo != null) {
                    List<Tag.TagData> list = tagInfo.getData();
                    if (list != null && list.size() > 0) {
                        for (Tag.TagData data : list) {
                            TextView tagView = (TextView) LayoutInflater.from(CommunityDetailsActivity.this)
                                    .inflate(R.layout.layout_text_tag, tagLayout, false);
                            tagView.setText(data.getTName());
                            tagLayout.addView(tagView);
                        }
                    }
                }

            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "get Tag HttpException:" + e.getMessage() + ",get Tag Error:" + s);
        }

    }

    private class AttentionQuestionRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String str = responseInfo.result;
            try {
                JSONObject object = new JSONObject(str);
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

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "Attention HttpException:" + e.getMessage() + ",Attention Error:" + s);
        }

    }


    private class TagResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "get Tag HttpException:" + e.getMessage());
        }

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "返回的Tag数据:" + response);
            if (!TextUtils.isEmpty(response)) {
                try {
                    Tag tagInfo = JsonUtils.getInstance().fromJSON(Tag.class, response);
                    if (tagInfo != null) {
                        List<Tag.TagData> list = tagInfo.getData();
                        if (list != null && list.size() > 0) {
                            for (Tag.TagData data : list) {
                                TextView tagView = (TextView) LayoutInflater.from(CommunityDetailsActivity.this)
                                        .inflate(R.layout.layout_text_tag, tagLayout, false);
                                tagView.setText(data.getTName());
                                tagLayout.addView(tagView);
                            }
                        }
                    }
                } catch (Exception e) {
                    DeBugLog.e(TAG, "Tag数据解析失败:" + e.getMessage());
                }
            } else {
                DeBugLog.e(TAG, "无返回Tag数据");
            }
        }

    }

    private class ProblemDetailsResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "问题详情数据异常:" + e.getMessage());
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