package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.callback.SaveCallback;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.alibaba.sdk.android.oss.storage.OSSBucket;
import com.alibaba.sdk.android.oss.storage.OSSFile;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.StatusBarCompat;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.minglang.suiuu.utils.SDCardUtils;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

public class UpLoadLogFileActivity extends BaseAppCompatActivity {

    private static final String TAG = UpLoadLogFileActivity.class.getSimpleName();

    private static final String SUIUU = "suiuu";

    private static final String V_ID = "vId";
    private static final String LOG = "log";
    private static final String USER_SIGN = "userSign";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    @BindColor(R.color.white)
    int titleColor;

    @BindString(R.string.commit_wait)
    String DialogMsg;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.SystemException)
    String SystemException;

    @Bind(R.id.up_load_lo_file_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.select_file_btn)
    Button selectFileBtn;

    private static OSSService ossService;
    private static OSSBucket bucket;

    private Context context;

    private boolean hasErrorLog = false;

    private String lastFilePath;

    private String versionId;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_log_file);

        StatusBarCompat.compat(this);
        ButterKnife.bind(this);

        initView();
        viewAction();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String path = SDCardUtils.getExternalSdCardPath() + "/Suiuu";
        File logFolder = new File(path);
        File[] logFileArray = logFolder.listFiles();
        if (logFileArray != null && logFileArray.length > 0) {
            hasErrorLog = true;
            File lastFile = logFileArray[logFileArray.length - 1];
            lastFilePath = lastFile.getAbsolutePath();
            L.i(TAG, "lastFilePath:" + lastFilePath);
        }
    }

    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        userSign = SuiuuInfo.ReadUserSign(this);
        versionId = SuiuuInfo.ReadVersionId(this);

        ossService = OSSServiceProvider.getService();
        bucket = ossService.getOssBucket(SUIUU);

        context = this;
    }

    private void viewAction() {
        selectFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasErrorLog) {
                    if (progressDialog != null && !progressDialog.isShowing()) {
                        progressDialog.show();
                    }

                    String lastFileName = lastFilePath.substring(lastFilePath.lastIndexOf("/"));
                    L.i(TAG, "lastFileName:" + lastFileName);

                    String lastFileType = lastFileName.substring(lastFileName.lastIndexOf(".") + 1);
                    L.i(TAG, "lastFileType:" + lastFileType);

                    OSSFile bigFile = ossService.getOssFile(bucket, "suiuu_log" + lastFileName);
                    try {
                        bigFile.setUploadFilePath(lastFilePath, lastFileName);
                        bigFile.ResumableUploadInBackground(new SaveCallback() {

                            @Override
                            public void onSuccess(String s) {
                                L.i(TAG, "上传成功后返回的地址:" + s);
                                String logFilePath = HttpNewServicePath.OssRootPath + s;
                                sendLogPathRequest(logFilePath);
                            }

                            @Override
                            public void onProgress(String objectKey, int byteCount, int totalSize) {
                                L.i(TAG, "byteCount:" + byteCount + ",totalSize:" + totalSize);
                            }

                            @Override
                            public void onFailure(String s, OSSException e) {
                                L.e(TAG, "上传失败:" + s + ",错误信息:" + e.getMessage());
                                hideDialog();
                            }

                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        hideDialog();
                    }
                } else {
                    Toast.makeText(context, "无错误日志信息！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendLogPathRequest(String logPath) {
        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[3];
        paramsArray[0] = new OkHttpManager.Params(V_ID, versionId);
        paramsArray[1] = new OkHttpManager.Params(LOG, logPath);
        paramsArray[2] = new OkHttpManager.Params(USER_SIGN, userSign);

        try {
            OkHttpManager.onPostAsynRequest(HttpNewServicePath.addErrorLogPath,
                    new OkHttpManager.ResultCallback<String>() {

                        @Override
                        public void onResponse(String response) {
                            L.i(TAG, "上传返回数据:" + response);
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString(STATUS);
                                switch (status) {
                                    case "1":
                                        Toast.makeText(context, "上传成功！", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "-1":
                                        Toast.makeText(context, SystemException, Toast.LENGTH_SHORT).show();
                                        break;
                                    case "-2":
                                        Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Request request, Exception e) {
                            L.e(TAG, "上传信息失败:" + e.getMessage());
                            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFinish() {
                            hideDialog();
                        }

                    }, paramsArray);
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}