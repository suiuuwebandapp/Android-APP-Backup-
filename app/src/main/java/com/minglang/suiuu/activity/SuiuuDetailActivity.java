package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.chat.activity.ChatActivity;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.dbhelper.UserDbHelper;
import com.minglang.suiuu.entity.SuiuuDeatailData;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.List;

/**
 * 项目名称：Suiuu
 * 类描述：suiuu的详细列表
 * 创建人：Administrator
 * 创建时间：2015/5/4 19:13
 * 修改人：Administrator
 * 修改时间：2015/5/4 19:13
 * 修改备注：
 */
@SuppressWarnings("deprecation")
public class SuiuuDetailActivity extends BaseActivity {
    private String tripId;
    private WebView mWebView;
    private ProgressDialog pd = null;
    private ImageView suiuu_details_back;
    //显示评论总数
    private TextView tv_suiuu_detail_comment_number;
    //评论头像
    private SimpleDraweeView sdv_comment_head_img;
    //评论输入框
    private EditText et_suiuu_detail_comment;
    //评论显示列表
    private NoScrollBarListView lv_suiuu_detail_comment;
    //咨询按钮
    private BootstrapButton bb_consult;
    //预定按钮
    private BootstrapButton bb_schedule;
    private SuiuuDeatailData detail;
    private TextView tv_to_commnet_activity;
    private LinearLayout ll_suiuu_detail_nocomment;
    private LinearLayout ll_suiuu_detail_input_comment;
    private CommonCommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suiuu_details_activity);
        tripId = this.getIntent().getStringExtra("tripId");
        initView();
        showWebView();
        loadDate(tripId);
        viewAction();
    }

    private void initView() {
        pd = new ProgressDialog(this);
        pd.show();
        suiuu_details_back = (ImageView) findViewById(R.id.suiuu_details_back);
        mWebView = (WebView) findViewById(R.id.suiuu_detail_web_view);
        tv_suiuu_detail_comment_number = (TextView) findViewById(R.id.tv_suiuu_detail_comment_number);
        sdv_comment_head_img = (SimpleDraweeView) findViewById(R.id.sdv_comment_head_img);
        et_suiuu_detail_comment = (EditText) findViewById(R.id.et_suiuu_detail_comment);
        lv_suiuu_detail_comment = (NoScrollBarListView) findViewById(R.id.lv_suiuu_detail_comment);
        bb_consult = (BootstrapButton) findViewById(R.id.bb_consult);
        bb_schedule = (BootstrapButton) findViewById(R.id.bb_schedule);
        tv_to_commnet_activity = (TextView) findViewById(R.id.tv_to_commnet_activity);
        ll_suiuu_detail_nocomment = (LinearLayout)findViewById(R.id.ll_suiuu_detail_nocomment);
        ll_suiuu_detail_input_comment = (LinearLayout)findViewById(R.id.ll_suiuu_detail_nocomment);
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
        tv_to_commnet_activity.setOnClickListener(new MyOnClickListener());
        bb_consult.setOnClickListener(new MyOnClickListener());
        bb_schedule.setOnClickListener(new MyOnClickListener());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void showWebView() {        // webView与js交互代码
        try {
            mWebView.requestFocus();

            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int progress) {
                    pd.setMessage("已经加载" + progress + "%");
                    if (progress >= 80) {
                        pd.dismiss();
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
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDefaultTextEncodingName("utf-8");

            mWebView.addJavascriptInterface(getHtmlObject(), "jsObj");
            mWebView.loadUrl("http://apptest.suiuu.com/app-travel/get-travel-info?app_suiuu_sign=" + URLEncoder.encode(SuiuuInfo.ReadVerification(this)) + "&trId=" + tripId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getHtmlObject() {
        Object insertObj = new Object() {
            @JavascriptInterface
            public void jsAlert(String s) {
                Toast.makeText(SuiuuDetailActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @JavascriptInterface
            public void userHomePage(String user) {
                Intent intent = new Intent(SuiuuDetailActivity.this, OtherUserActivity.class);
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
        db.execSQL("insert into user (userid,nikename,titleimg) values (?,?,?)", new Object[]{detail.getData().getPublisherList().get(0).getUserSign(), detail.getData().getPublisherList().get(0).getNickname(), detail.getData().getPublisherList().get(0).getHeadImg()});
        db.close();
        Log.i("suiuu", "添加user成功");
    }

    public boolean isExistUser(String userId) {
        boolean isexist = false;
        UserDbHelper helper = new UserDbHelper(this);
        //得到可读数据库
        SQLiteDatabase db = helper.getReadableDatabase();
        //得到数据库查询的结果集的游标（指针）
        Cursor cursor = db.rawQuery("select * from user where userid = ? ", new String[]{userId});
        while (cursor.moveToNext()) {
            isexist = true;
        }
        cursor.close();
        db.close();
        return isexist;
    }

    //访问网络
    private void loadDate(String tripId) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("trId", tripId);
        params.addBodyParameter(HttpServicePath.key, verification);
        Log.i("suiuu", "vertification=" + verification);
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
            Log.i("suiuu", responseInfo.result);
            try {
                JSONObject json = new JSONObject(responseInfo.result);
                String status = json.getString("status");
                if ("1".equals(status)) {
                    detail = JsonUtils.getInstance().fromJSON(SuiuuDeatailData.class, responseInfo.result);
                    fullCommentList();
                } else if ("-3".equals(status)) {
                    Toast.makeText(getApplicationContext(), "登录信息过期,请重新登录", Toast.LENGTH_SHORT).show();
                    AppUtils.intentLogin(getApplicationContext());
                    SuiuuDetailActivity.this.finish();
                } else {
                    Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Toast.makeText(SuiuuDetailActivity.this, "数据请求失败，请稍候再试！", Toast.LENGTH_SHORT).show();
        }
    }

    public void fullCommentList() {
        List<SuiuuDeatailData.DataEntity.CommentEntity.CommentDataEntity> data = detail.getData().getComment().getData();
        if(data.size() >= 1) {
            ll_suiuu_detail_input_comment.setVisibility(View.VISIBLE);
            ll_suiuu_detail_nocomment.setVisibility(View.GONE);
            showList(data);
        }
    }
    private void showList(List<SuiuuDeatailData.DataEntity.CommentEntity.CommentDataEntity> tripGalleryList) {
        if (adapter == null) {
            adapter = new CommonCommentAdapter(this, tripGalleryList);
            lv_suiuu_detail_comment.setAdapter(adapter);
        } else {
            adapter.onDateChange(tripGalleryList);
        }
    }


    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.sdv_comment_head_img:
                    //跳到个人中心
                    break;
                case R.id.et_suiuu_detail_comment:
                    //跳到评论页
                    Intent intent = new Intent(SuiuuDetailActivity.this, CommonCommentActivity.class);
                    intent.putExtra("tripId", tripId);
                    startActivity(intent);
                    break;
                case R.id.tv_to_commnet_activity:
                    //跳到评论页
                    Intent commentintent = new Intent(SuiuuDetailActivity.this, CommonCommentActivity.class);
                    commentintent.putExtra("tripId", tripId);
                    startActivity(commentintent);
                    break;
                case R.id.bb_consult:
                    //跳到会话页面
                    if (isExistUser(detail.getData().getPublisherList().get(0).getUserSign())) {
                        //当前用户在数据库中已经存在;
                    } else {
                        addUser();
                        Log.i("suiuu", "不存在了");
                    }
                    Intent intentConsult = new Intent(SuiuuDetailActivity.this, ChatActivity.class);
                    intentConsult.putExtra("userId", detail.getData().getPublisherList().get(0).getUserSign());
                    startActivity(intentConsult);
                    break;
                case R.id.bb_schedule:
                    //跳到预定页面
                    Intent intentSchedule = new Intent(SuiuuDetailActivity.this, SuiuuOrderActivity.class);
                    intentSchedule.putExtra("titleInfo", detail.getData().getInfo().getTitle());
                    intentSchedule.putExtra("titleImg", detail.getData().getInfo().getTitleImg());
                    intentSchedule.putExtra("price", detail.getData().getInfo().getBasePrice());
                    intentSchedule.putExtra("tripId", detail.getData().getInfo().getTripId());
                    startActivity(intentSchedule);
                    break;

            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
