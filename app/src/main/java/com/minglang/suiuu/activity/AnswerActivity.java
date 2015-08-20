package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 回答问题页面
 */
public class AnswerActivity extends BaseAppCompatActivity {

    private static final String TAG = AnswerActivity.class.getSimpleName();

    private static final String Q_ID = "qId";

    private static final String CONTENT = "content";

    private static final String STATUS = "status";
    private static final String SUC_VALUE = "1";

    private String qID;

    @BindString(R.string.Waiting)
    String wait;

    @BindString(R.string.AnswerSuccess)
    String success;

    @BindString(R.string.NetworkAnomaly)
    String netWorkError;

    @BindString(R.string.AnswerNoNull)
    String AnswerNoNull;

    @Bind(R.id.answer_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.answer_edit_text)
    EditText editText;

    @BindColor(R.color.white)
    int titleTextColor;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        qID = getIntent().getStringExtra(Q_ID);
        DeBugLog.i(TAG, "问题ID:" + qID);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        toolbar.setTitleTextColor(titleTextColor);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(wait);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private RequestParams buildRequestParams(String id, String content) {
        RequestParams params = new RequestParams();
        params.addBodyParameter(HttpServicePath.key, SuiuuInfo.ReadVerification(this));
        params.addBodyParameter(Q_ID, id);
        params.addBodyParameter(CONTENT, content);
        return params;
    }

    private void setAnswerContent2Service(RequestParams params) {
        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.setAnswerToQuestionPath, new AnswerRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_answer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.answer_confirm:
                String str = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(str)) {
                    setAnswerContent2Service(buildRequestParams(qID, str));
                } else {
                    Toast.makeText(this, AnswerNoNull, Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class AnswerRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String str = responseInfo.result;
            DeBugLog.i(TAG, "回答问题返回结果:" + str);
            hideDialog();
            try {
                JSONObject object = new JSONObject(str);
                String status = object.getString(STATUS);
                if (status.equals(SUC_VALUE)) {
                    Toast.makeText(AnswerActivity.this, success, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AnswerActivity.this, netWorkError, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析数据异常:" + e.getMessage());
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",Error:" + s);
            hideDialog();
            Toast.makeText(AnswerActivity.this, netWorkError, Toast.LENGTH_SHORT).show();
        }

    }

}