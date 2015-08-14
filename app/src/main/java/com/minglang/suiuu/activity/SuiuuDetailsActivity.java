package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.CommonCommentAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.dbhelper.UserDbHelper;
import com.minglang.suiuu.entity.SuiuuDetailsData;
import com.minglang.suiuu.entity.SuiuuDetailsData.DataEntity.CommentEntity.CommentDataEntity;
import com.minglang.suiuu.entity.UserBackData;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 项目名称：Suiuu
 * 类描述：suiuu的详细列表
 * 创建人：Administrator
 * 创建时间：2015/5/4 19:13
 * 修改人：Administrator
 * 修改时间：2015/5/4 19:13
 * 修改备注：
 */
public class SuiuuDetailsActivity extends BaseAppCompatActivity {

    private static final int COMMENT_SUCCESS = 20;

    private static final String TRIP_ID = "tripId";
    private static final String R_ID = "rId";
    private static final String NICK_NAME = "nickName";

    private static final String STATUS = "status";

    private static final String CONTENT = "content";
    private static final String HEAD_IMG = "headImg";
    private static final String NICK_NAME_ = "nickname";

    @BindString(R.string.LoginInvalid)
    String LoginInvalid;

    @BindString(R.string.DataReFai)
    String DataReFai;

    private String tripId;

    @Bind(R.id.suiuu_detail_web_view)
    WebView mWebView;

    private ProgressDialog progressDialog = null;

    @Bind(R.id.suiuu_details_back)
    ImageView suiuu_details_back;

    //显示评论总数
    @Bind(R.id.tv_suiuu_detail_comment_number)
    TextView tv_suiuu_detail_comment_number;

    //评论头像
    @Bind(R.id.sdv_comment_head_img)
    SimpleDraweeView sdv_comment_head_img;

    //评论输入框
    @Bind(R.id.et_suiuu_details_comment)
    EditText et_suiuu_detail_comment;

    //评论显示列表
    @Bind(R.id.lv_suiuu_detail_comment)
    NoScrollBarListView lv_suiuu_detail_comment;

    //咨询按钮
    @Bind(R.id.bb_consult)
    BootstrapButton bb_consult;

    //预定按钮
    @Bind(R.id.bb_schedule)
    BootstrapButton bb_schedule;

    private SuiuuDetailsData detailsData;

    @Bind(R.id.tv_to_comment_activity)
    TextView tv_to_comment_activity;

    @Bind(R.id.ll_suiuu_details_no_comment)
    LinearLayout ll_suiuu_detail_no_comment;

    @Bind(R.id.ll_suiuu_detail_input_comment)
    LinearLayout ll_suiuu_detail_input_comment;

    private CommonCommentAdapter adapter;

    WebSettings webSettings;

    /**
     * 评论集合
     */
    private List<SuiuuDetailsData.DataEntity.CommentEntity.CommentDataEntity> commentDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suiuu_details_activity);

        ButterKnife.bind(this);
        initView();
        loadDate(tripId);
        showWebView();
        viewAction();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        tripId = getIntent().getStringExtra(TRIP_ID);

        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
    }

    private void viewAction() {
        suiuu_details_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sdv_comment_head_img.setOnClickListener(new MyOnClickListener());

        et_suiuu_detail_comment.setOnClickListener(new MyOnClickListener());

        tv_to_comment_activity.setOnClickListener(new MyOnClickListener());

        bb_consult.setOnClickListener(new MyOnClickListener());

        bb_schedule.setOnClickListener(new MyOnClickListener());

        lv_suiuu_detail_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SuiuuDetailsActivity.this, CommonCommentActivity.class);
                intent.putExtra(TRIP_ID, tripId);
                intent.putExtra(R_ID, commentDataList.get(position).getCommentId());
                intent.putExtra(NICK_NAME, commentDataList.get(position).getNickname());
                startActivityForResult(intent, COMMENT_SUCCESS);
            }
        });
    }

    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
    private void showWebView() {
        // webView与js交互代码
        try {
            mWebView.requestFocus();
            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int progress) {
                    progressDialog.setMessage("已经加载" + progress + "%");
                    if (progress >= 80) {
                        progressDialog.dismiss();
                    }
                }
            });

            mWebView.setOnKeyListener(new View.OnKeyListener() {        // webview can go back
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                    return false;
                }
            });

            mWebView.addJavascriptInterface(getHtmlObject(), "jsObj");
            mWebView.loadUrl("http://apptest.suiuu.com/app-travel/get-travel-info?app_suiuu_sign="
                    + URLEncoder.encode(SuiuuInfo.ReadVerification(this)) + "&trId=" + tripId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getHtmlObject() {
        Object insertObj = new Object() {
            @JavascriptInterface
            public void jsAlert(String s) {
                Toast.makeText(SuiuuDetailsActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @JavascriptInterface
            public void userHomePage(String user) {
                Intent intent = new Intent(SuiuuDetailsActivity.this, OtherUserActivity.class);
                intent.putExtra("userSign", user);
                startActivity(intent);
            }

            @JavascriptInterface
            public String HtmlcallJava2(final String param) {
                return "Html call Java : " + param;
            }

        };
        return insertObj;
    }

    /**
     * 添加一条记录
     */
    public void addUser() {
        UserDbHelper helper = new UserDbHelper(this);
        //得到可读可写数据库
        SQLiteDatabase db = helper.getReadableDatabase();
        //执行sql语句
        db.execSQL("insert into user (userId,nickName,titleImg) values (?,?,?)",
                new Object[]{detailsData.getData().getPublisherList().get(0).getUserSign(),
                        detailsData.getData().getPublisherList().get(0).getNickname(),
                        detailsData.getData().getPublisherList().get(0).getHeadImg()});
        db.close();
    }

    public boolean isExistUser(String userId) {
        boolean isExist = false;
        UserDbHelper helper = new UserDbHelper(this);
        //得到可读数据库
        SQLiteDatabase db = helper.getReadableDatabase();
        //得到数据库查询的结果集的游标（指针）
        Cursor cursor = db.rawQuery("select * from user where userid = ? ", new String[]{userId});
        while (cursor.moveToNext()) {
            isExist = true;
        }
        cursor.close();
        db.close();
        return isExist;
    }

    //访问网络
    private void loadDate(String tripId) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(TRIP_ID, tripId);
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getSuiuuItemInfo, new SuiuuItemInfoCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 请求数据网络接口回调
     */
    class SuiuuItemInfoCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            try {
                JSONObject json = new JSONObject(responseInfo.result);
                String status = json.getString(STATUS);

                if ("1".equals(status)) {
                    detailsData = JsonUtils.getInstance().fromJSON(SuiuuDetailsData.class, responseInfo.result);
                    commentDataList = detailsData.getData().getComment().getData();
                    fullCommentList();
                } else if ("-3".equals(status)) {
                    Toast.makeText(SuiuuDetailsActivity.this, LoginInvalid, Toast.LENGTH_SHORT).show();
                    AppUtils.intentLogin(SuiuuDetailsActivity.this);
                    SuiuuDetailsActivity.this.finish();
                } else {
                    Toast.makeText(SuiuuDetailsActivity.this, DataReFai, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Toast.makeText(SuiuuDetailsActivity.this, DataReFai, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Toast.makeText(SuiuuDetailsActivity.this, DataReFai, Toast.LENGTH_SHORT).show();
        }

    }

    public void fullCommentList() {
        if (commentDataList != null && commentDataList.size() > 0) {
            tv_suiuu_detail_comment_number.setText("全部评论 (共" + commentDataList.size() + "条评论)");
            ll_suiuu_detail_input_comment.setVisibility(View.VISIBLE);
            ll_suiuu_detail_no_comment.setVisibility(View.GONE);
            showList(commentDataList);
        } else {
            ll_suiuu_detail_no_comment.setVisibility(View.VISIBLE);
            ll_suiuu_detail_input_comment.setVisibility(View.GONE);
        }
    }

    private void showList(List<CommentDataEntity> commentDataList) {
        if (adapter == null) {
            adapter = new CommonCommentAdapter(this, commentDataList);
            lv_suiuu_detail_comment.setAdapter(adapter);
        } else {
            adapter.onDateChange(commentDataList);
        }
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.sdv_comment_head_img:
                    //跳到个人中心
                    break;
                case R.id.et_suiuu_details_comment:
                    //跳到评论页
                    Intent intent = new Intent(SuiuuDetailsActivity.this, CommonCommentActivity.class);
                    intent.putExtra(TRIP_ID, tripId);
                    startActivityForResult(intent, COMMENT_SUCCESS);
                    break;
                case R.id.tv_to_comment_activity:
                    //跳到评论页
                    Intent commentIntent = new Intent(SuiuuDetailsActivity.this, CommonCommentActivity.class);
                    commentIntent.putExtra(TRIP_ID, tripId);
                    startActivityForResult(commentIntent, COMMENT_SUCCESS);
                    break;
                case R.id.bb_consult:
                    //跳到会话页面
                    if (!isExistUser(detailsData.getData().getPublisherList().get(0).getUserSign())) {
                        addUser();
                    }

//                    Intent intentConsult = new Intent(SuiuuDetailsActivity.this, ChatActivity.class);
//                    intentConsult.putExtra("userId", detailsData.getData().getPublisherList().get(0).getUserSign());
//                    startActivity(intentConsult);
                    break;
                case R.id.bb_schedule:
                    //跳到预定页面
                    Intent intentSchedule = new Intent(SuiuuDetailsActivity.this, SuiuuOrderActivity.class);
                    intentSchedule.putExtra("titleInfo", detailsData.getData().getInfo().getTitle());
                    intentSchedule.putExtra("titleImg", detailsData.getData().getInfo().getTitleImg());
                    intentSchedule.putExtra("price", detailsData.getData().getInfo().getBasePrice());
                    intentSchedule.putExtra("tripId", detailsData.getData().getInfo().getTripId());
                    startActivity(intentSchedule);
                    break;

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == COMMENT_SUCCESS) {
            UserBackData userBackData = SuiuuInfo.ReadUserData(this);
            String content = data.getStringExtra(CONTENT);

            JSONObject json = new JSONObject();
            try {
                json.put(HEAD_IMG, userBackData.getHeadImg());
                json.put(NICK_NAME_, userBackData.getNickname());
                json.put(CONTENT, content);

                CommentDataEntity newCommentData =
                        JsonUtils.getInstance().fromJSON(CommentDataEntity.class, json.toString());
                commentDataList.add(0, newCommentData);
                fullCommentList();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}