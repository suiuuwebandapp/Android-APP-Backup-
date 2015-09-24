package com.minglang.suiuu.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.SuiuuDetailsCommentAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.dbhelper.DbHelper;
import com.minglang.suiuu.entity.SuiuuDetailsData;
import com.minglang.suiuu.entity.SuiuuDetailsData.DataEntity.CommentEntity.CommentDataEntity;
import com.minglang.suiuu.entity.UserBack.UserBackData;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

    private static final String TAG = SuiuuDetailsActivity.class.getSimpleName();

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

    @BindString(R.string.DataRequestFailure)
    String DataRequestFailure;

    @BindString(R.string.SystemException)
    String SystemException;

    private String tripId;

    @Bind(R.id.suiuu_detail_web_view)
    WebView mWebView;

    private ProgressDialog progressDialog = null;

    @Bind(R.id.rl_suiuu_detail)
    RelativeLayout rl_suiuu_detail;

    @Bind(R.id.suiuu_details_back)
    ImageView back;

    //显示评论总数
    @Bind(R.id.suiuu_details_comment_number)
    TextView suiuuDetailsCommentNumber;

    //评论头像
    @Bind(R.id.sdv_comment_head_img)
    SimpleDraweeView sdv_comment_head_img;

    //评论输入框
    @Bind(R.id.suiuu_details_comment)
    EditText et_suiuu_detail_comment;

    //评论显示列表
    @Bind(R.id.suiuu_details_comment_list_view)
    NoScrollBarListView suiuuDetailsComment;

    //咨询按钮
    @Bind(R.id.bb_consult)
    BootstrapButton consult;

    //预定按钮
    @Bind(R.id.bb_schedule)
    BootstrapButton schedule;

    private SuiuuDetailsData detailsData;

    @Bind(R.id.to_comment_activity)
    TextView to_comment_activity;

    @Bind(R.id.suiuu_details_no_comment)
    LinearLayout suiuu_detail_no_comment;

    @Bind(R.id.suiuu_details_input_comment)
    LinearLayout suiuu_detail_input_comment;

    private SuiuuDetailsCommentAdapter adapter;

    WebSettings webSettings;

    /**
     * 评论集合
     */
    private List<CommentDataEntity> listAll = new ArrayList<>();

    /**
     * 是否显示全部评论的状态
     */
    private boolean showAllComment = false;

    /**
     * 加载更多的view
     */
    private TextView textView = null;

    private AlertDialog setTagDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suiuu_details_activity);
        ButterKnife.bind(this);
        initView();
        getSuiuuDetailsData(tripId);
        showWebView();
        viewAction();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        tripId = getIntent().getStringExtra(TRIP_ID);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();

        webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        adapter = new SuiuuDetailsCommentAdapter(this, listAll);
        suiuuDetailsComment.setAdapter(adapter);
    }

    private void viewAction() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sdv_comment_head_img.setOnClickListener(new MyOnClickListener());

        et_suiuu_detail_comment.setOnClickListener(new MyOnClickListener());

        to_comment_activity.setOnClickListener(new MyOnClickListener());

        consult.setOnClickListener(new MyOnClickListener());

        schedule.setOnClickListener(new MyOnClickListener());

        suiuuDetailsComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if (!showAllComment && position == 5) {
                //showAllComment = true;
                //showList(listAll);
                //} else {
                Intent intent = new Intent(SuiuuDetailsActivity.this, CommonCommentActivity.class);
                intent.putExtra(TRIP_ID, tripId);
                intent.putExtra(R_ID, listAll.get(position).getCommentId());
                intent.putExtra(NICK_NAME, listAll.get(position).getNickname());
                startActivityForResult(intent, COMMENT_SUCCESS);
                //}
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
                    if (progress >= 85) {
                        rl_suiuu_detail.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                }
            });

            mWebView.setOnKeyListener(new View.OnKeyListener() {// webView can go back
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

            mWebView.loadUrl("http://www.suiuu.com/app-travel/get-travel-info?trId=" + tripId + "&token=" + token);

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
            public void showMask() {
                showSetTagDialog();
            }

            @JavascriptInterface
            public void userHomePage(String user) {
                Intent intent2UserActivity = new Intent(SuiuuDetailsActivity.this, PersonalMainPagerActivity.class);
                intent2UserActivity.putExtra("userSign", user);
                startActivity(intent2UserActivity);
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
        DbHelper helper = new DbHelper(this);
        //得到可读可写数据库
        SQLiteDatabase db = helper.getReadableDatabase();
        //执行sql语句
        db.execSQL("insert into user (userId,nikename,titleImg) values (?,?,?)",
                new Object[]{detailsData.getData().getPublisherList().get(0).getUserSign(),
                        detailsData.getData().getPublisherList().get(0).getNickname(),
                        detailsData.getData().getPublisherList().get(0).getHeadImg()});
        db.close();
    }

    public boolean isExistUser(String userId) {
        boolean isExist = false;
        DbHelper helper = new DbHelper(this);
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
    private void getSuiuuDetailsData(String tripId) {
        String[] keyArray1 = new String[]{TRIP_ID, "token"};
        String[] valueArray1 = new String[]{tripId, token};
        String url = addUrlAndParams(HttpNewServicePath.getSuiuuItemInfo, keyArray1, valueArray1);
        L.i(TAG, "请求URL:" + url);
        try {
            OkHttpManager.onGetAsynRequest(url, new SuiuuItemInfoCallBack());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求数据网络接口回调
     */
    class SuiuuItemInfoCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            Toast.makeText(SuiuuDetailsActivity.this, DataRequestFailure, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String str) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                String status = jsonObject.getString(STATUS);

                switch (status) {
                    case "1":
                        detailsData = JsonUtils.getInstance().fromJSON(SuiuuDetailsData.class, str);
                        listAll = detailsData.getData().getComment().getData();
                        fullCommentList();
                        break;

                    case "-1":
                        Toast.makeText(SuiuuDetailsActivity.this, SystemException, Toast.LENGTH_SHORT).show();
                        break;

                    case "-2":
                        Toast.makeText(SuiuuDetailsActivity.this, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                        break;

                    case "-3":
                        Toast.makeText(SuiuuDetailsActivity.this, LoginInvalid, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SuiuuDetailsActivity.this, FirstLoginActivity.class);
                        SuiuuDetailsActivity.this.startActivity(intent);
                        SuiuuDetailsActivity.this.finish();
                        break;

                    default:
                        Toast.makeText(SuiuuDetailsActivity.this, DataRequestFailure, Toast.LENGTH_SHORT).show();
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(SuiuuDetailsActivity.this, DataRequestFailure, Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void fullCommentList() {
        if (listAll != null && listAll.size() > 0) {
            suiuuDetailsCommentNumber.setText(String.format("%s%s%s", "全部评论 (共", listAll.size(), "条评论)"));
            suiuu_detail_input_comment.setVisibility(View.VISIBLE);
            suiuu_detail_no_comment.setVisibility(View.GONE);
            //showList(listAll);
            adapter.setList(listAll);
        } else {
            suiuu_detail_no_comment.setVisibility(View.VISIBLE);
            suiuu_detail_input_comment.setVisibility(View.GONE);
        }
    }

    //    private void showList(List<CommentDataEntity> commentDataList) {
    //        List<CommentDataEntity> newCommentDataList = new ArrayList<>();
    //        if (!showAllComment) {
    //            if (commentDataList.size() > 5) {
    //                if (textView == null) {
    //                    textView = new TextView(this);
    //                    textView.setText("点击加载更多");
    //                    textView.setPadding(0, 10, 0, 0);
    //                    textView.setGravity(Gravity.CENTER);
    //                    textView.setTextSize(18);
    //                }
    //                suiuuDetailsComment.addFooterView(textView);
    //                newCommentDataList.addAll(commentDataList);
    //                newCommentDataList.subList(5, commentDataList.size()).clear();
    //            }
    //        } else {
    //            suiuuDetailsComment.removeFooterView(textView);
    //        }
    //
    //        if (adapter == null) {
    //            adapter = new SuiuuDetailsCommentAdapter(this, commentDataList.size() > 5 && !showAllComment ? newCommentDataList : commentDataList);
    //            suiuuDetailsComment.setAdapter(adapter);
    //        } else {
    //            adapter.setList(commentDataList.size() > 5 && !showAllComment ? newCommentDataList : commentDataList);
    //        }
    //    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.sdv_comment_head_img:
                    //跳到个人中心
                    break;
                case R.id.suiuu_details_comment:
                    //跳到评论页
                    Intent intent = new Intent(SuiuuDetailsActivity.this, CommonCommentActivity.class);
                    intent.putExtra(TRIP_ID, tripId);
                    startActivityForResult(intent, COMMENT_SUCCESS);
                    break;
                case R.id.to_comment_activity:
                    //跳到评论页
                    Intent commentIntent = new Intent(SuiuuDetailsActivity.this, CommonCommentActivity.class);
                    commentIntent.putExtra(TRIP_ID, tripId);
                    startActivityForResult(commentIntent, COMMENT_SUCCESS);
                    break;
                case R.id.bb_consult:
                    //跳到会话页面
//                    if (!isExistUser(detailsData.getData().getPublisherList().get(0).getUserSign())) {
//                        addUser();
//                    }
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
                listAll.add(0, newCommentData);
                showAllComment = false;
                fullCommentList();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 设置自定义标签弹出框
     */
    protected void showSetTagDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.suiuu_dialog, null);
        ImageView iv_cancel = (ImageView) view.findViewById(R.id.iv_suiuu_dialog_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTagDialog.dismiss();
            }
        });
        setTagDialog = builder.create();
        setTagDialog.setView(view, 0, 0, 0, 0);
        setTagDialog.show();
    }

}