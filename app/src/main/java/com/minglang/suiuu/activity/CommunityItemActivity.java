package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.entity.CommunityItem;
import com.minglang.suiuu.entity.Tag;
import com.minglang.suiuu.entity.CommunityItem.CommunityItemData.AnswerEntity;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 问题详情页
 */
public class CommunityItemActivity extends BaseAppCompatActivity {

    private static final String TAG = CommunityItemActivity.class.getSimpleName();

    private static final String ID = "id";

    @BindColor(R.color.white)
    int titleTextColor;

    @BindString(R.string.NoData)
    String NoData;

    @Bind(R.id.community_item_refresh_scroll_view)
    PullToRefreshScrollView pullToRefreshScrollView;

    @Bind(R.id.noScrollListView)
    NoScrollBarListView noScrollBarListView;

    @Bind(R.id.community_item_toolbar)
    Toolbar toolbar;

    @Bind(R.id.item_community_layout_1_head_view)
    CircleImageView headImageView;

    @Bind(R.id.item_community_layout_1_problem)
    TextView problemTitle;

    @Bind(R.id.item_community_layout_1_flow_layout)
    FlowLayout tagLayout;

    @Bind(R.id.item_community_layout_2_problem)
    TextView problemContent;

    @Bind(R.id.item_community_layout_3_attention)
    TextView attentionBtn;

    @Bind(R.id.item_community_layout_3_answer)
    TextView answerBtn;

    private ProgressDialog progressDialog;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    private CommunityItemAdapter communityItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_item);

        String strID = getIntent().getStringExtra(ID);
        DeBugLog.i(TAG, "strID:" + strID);

        ButterKnife.bind(this);
        initView();
        ViewAction();
        getTagList();
        getCommunityItemDetails(buildRequestParams(strID));
    }

    private void initView() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("问题");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        toolbar.setTitleTextColor(titleTextColor);

        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.load_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image).showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

        ImageView tag = (ImageView) LayoutInflater.from(this).inflate(R.layout.layout_image_tag, tagLayout, false);
        tagLayout.addView(tag);

        communityItemAdapter = new CommunityItemAdapter(this);
        noScrollBarListView.setAdapter(communityItemAdapter);
    }

    private void ViewAction() {

        attentionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        answerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        noScrollBarListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                DeBugLog.i(TAG, "firstVisibleItem:" + firstVisibleItem + ",visibleItemCount:" + visibleItemCount
                        + ",totalItemCount:" + totalItemCount);
            }
        });

        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                String label = DateUtils.formatDateTime(CommunityItemActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                pullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                String label = DateUtils.formatDateTime(CommunityItemActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                pullToRefreshScrollView.onRefreshComplete();
            }
        });

    }

    private void getTagList() {
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.GET,
                HttpServicePath.getDefaultTagListPath, new TagRequestCallBack());
        httpRequest.requestNetworkData();
    }

    private RequestParams buildRequestParams(String id) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, SuiuuInfo.ReadVerification(this));
        params.addBodyParameter(ID, id);
        return params;
    }

    private void getCommunityItemDetails(RequestParams params) {
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getProblemDetailsPath, new CommunityItemRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
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
                        CommunityItem.CommunityItemData.QuestionEntity questionEntity = itemData.getQuestion();

                        String headImagePath = questionEntity.getHeadImg();
                        if (!TextUtils.isEmpty(headImagePath)) {
                            imageLoader.displayImage(headImagePath, headImageView, options);
                        }

                        String strTitle = questionEntity.getQTitle();
                        if (!TextUtils.isEmpty(strTitle)) {
                            problemTitle.setText(strTitle);
                        } else {
                            problemTitle.setText("");
                        }

                        String strContent = questionEntity.getQContent();
                        if (!TextUtils.isEmpty(strContent)) {
                            problemContent.setText(strContent);
                        } else {
                            problemContent.setText("");
                        }

                        List<AnswerEntity> list = itemData.getAnswer();
                        if (list != null && list.size() > 0) {
                            communityItemAdapter.setList(list);
                        }
                    }
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析错误:" + e.getMessage());
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
            DeBugLog.e(TAG, "HttpException:" + e.getMessage());
            DeBugLog.e(TAG, "Error:" + s);
            hideDialog();
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
                            TextView tagView = (TextView) LayoutInflater.from(CommunityItemActivity.this)
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
            DeBugLog.e(TAG, "get Tag HttpException:" + e.getMessage());
            DeBugLog.e(TAG, "get Tag Error:" + s);
        }
    }

}