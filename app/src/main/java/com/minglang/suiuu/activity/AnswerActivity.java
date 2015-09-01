package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;

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

        verification = SuiuuInfo.ReadVerification(this);
    }

    /**
     * 构建网络请求参数
     *
     * @param id      问题ID
     * @param content 答案
     * @return 参数
     */
    private OkHttpManager.Params[] buildNewParams(String id, String content) {
        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[3];
        paramsArray[0] = new OkHttpManager.Params(HttpNewServicePath.key, verification);
        paramsArray[1] = new OkHttpManager.Params(Q_ID, id);
        paramsArray[2] = new OkHttpManager.Params(CONTENT, content);
        return paramsArray;
    }

    /**
     * 发起网络请求
     *
     * @param content 答案内容
     */
    private void sendRequest(String content) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.setAnswerToQuestionPath,
                 new AnswerResultCallback(), buildNewParams(qID, content));
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
        }
    }

    /**
     * 隐藏Dialog
     */
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
                    //setAnswerContent2Service(buildRequestParams(qID, str));
                    sendRequest(str);
                } else {
                    Toast.makeText(this, AnswerNoNull, Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class AnswerResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            hideDialog();
            DeBugLog.e(TAG, "request:" + request.toString() + ",Exception:" + e.getMessage());
        }

        @Override
        public void onResponse(String response) {
            hideDialog();
            DeBugLog.i(TAG, "response:" + response);
            try {
                JSONObject object = new JSONObject(response);
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

    }

}