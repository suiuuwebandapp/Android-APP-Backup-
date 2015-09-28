package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SDCardUtils;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;
import cn.edu.zafu.coreprogress.helper.ProgressHelper;
import cn.edu.zafu.coreprogress.listener.impl.UIProgressResponseListener;

/**
 * 关于随游页面
 */
public class AboutSuiuuActivity extends BaseAppCompatActivity {

    private static final String TAG = AboutSuiuuActivity.class.getSimpleName();

    private static final String APP_ID = "appId";
    private static final String CLIENT_TYPE = "clientType";
    private static final String VERSION_ID = "versionId";
    private static final String VERSION_MINI = "versionMini";

    @BindColor(R.color.white)
    int titleColor;

    @BindString(R.string.VersionName)
    String versionNameBefore;

    @BindString(R.string.VersionNameFailure)
    String versionNameFailure;

    @Bind(R.id.about_suiuu_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.about_suiuu_logo)
    ImageView logo;

    @Bind(R.id.about_suiuu_version)
    TextView aboutSuiuuVersion;

    @Bind(R.id.about_suiuu_evaluation)
    TextView aboutSuiuuEvaluation;

    @Bind(R.id.about_suiuu_help)
    TextView aboutSuiuuHelp;

    @Bind(R.id.about_suiuu_update)
    TextView aboutSuiuuUpdate;

    private int count = 1;

    private Context context;

    private String serialNumber;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_suiuu);
        ButterKnife.bind(this);
        initView();
        viewAction();
    }

    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);

        try {
            String name = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            aboutSuiuuVersion.setText(String.format("%s%s", versionNameBefore, name));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            aboutSuiuuVersion.setText(versionNameFailure);
        }

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        serialNumber = tm.getDeviceId();
        L.i(TAG, "手机串号:" + serialNumber);

        context = AboutSuiuuActivity.this;
    }

    private void viewAction() {

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count == 5) {
                    startActivity(new Intent(context, UpLoadLogFileActivity.class));
                }
            }
        });

        aboutSuiuuEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mAddress = "market://details?id=" + getPackageName();
                Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                marketIntent.setData(Uri.parse(mAddress));
                startActivity(marketIntent);
            }
        });

        aboutSuiuuHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, UsingSuiuuActivity.class));
            }
        });

        aboutSuiuuUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpManager.Params[] paramArray = new OkHttpManager.Params[4];
                paramArray[0] = new OkHttpManager.Params(APP_ID, serialNumber);
                paramArray[1] = new OkHttpManager.Params(CLIENT_TYPE, "androidPhone");
                paramArray[2] = new OkHttpManager.Params(VERSION_ID, "1");
                paramArray[3] = new OkHttpManager.Params(VERSION_MINI, "20");

                try {
                    OkHttpManager.onPostAsynRequest(HttpNewServicePath.getVersionInfoPath, new OkHttpManager.ResultCallback<String>() {

                        @Override
                        public void onResponse(String response) {
                            L.i(TAG, "更新信息:" + response);
                            if (TextUtils.isEmpty(response)) {
                                L.e(TAG, "无返回信息！");
                                Toast.makeText(context, "暂无版本信息！", Toast.LENGTH_SHORT).show();
                            } else try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString(STATUS);
                                if (status.equals("1")) {
                                    JSONObject data = object.getJSONObject(DATA);
                                    String versionId = data.getString("vId");
                                    L.i(TAG, "versionId:" + versionId);
                                    SuiuuInfo.WriteVersionId(context, versionId);

                                    String isUpdate = object.getString("isUpdate");
                                    switch (isUpdate) {
                                        case "0":
                                            Toast.makeText(context, "已是最新版本", Toast.LENGTH_SHORT).show();
                                            break;

                                        case "1":
                                        case "2":
                                            String url = object.getString("url");
                                            DownLoadFile(url);
                                            break;
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Request request, Exception e) {
                            L.e(TAG, "版本信息请求失败:" + e.getMessage());
                        }

                    }, paramArray);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "版本信息获取失败！", Toast.LENGTH_SHORT).show();
                }

                //DownLoadFile("http://suiuu.oss-cn-hongkong.aliyuncs.com/suiuu_app/Suiuu.apk");

            }
        });

    }

    private void DownLoadFile(final String url) {
        final String path = SDCardUtils.getExternalSdCardPath() + "/SuiuuDownLoad";

        UIProgressResponseListener uiProgressResponseListener = new UIProgressResponseListener() {

            @Override
            public void onUIResponseStart(long bytesRead, long contentLength, boolean done) {
                L.i(TAG, "onUIResponseStart() bytesRead:" + bytesRead + ",contentLength:" + contentLength + ",done:" + done);
                progressDialog.show();
            }

            @Override
            public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
                L.i(TAG, "onUIResponseProgress() bytesRead:" + bytesRead + ",contentLength:" + contentLength + ",done:" + done);
                progressDialog.setProgress((int) ((100 * bytesRead) / contentLength));
                progressDialog.setMessage((100 * bytesRead) / contentLength + "%");
            }

            @Override
            public void onUIResponseFinish(long bytesRead, long contentLength, boolean done) {
                L.i(TAG, "onUIResponseFinish() bytesRead:" + bytesRead + ",contentLength:" + contentLength + ",done:" + done);
                progressDialog.dismiss();
            }

        };

        final Request request = new Request.Builder().url(url).build();
        OkHttpClient okHttpClient = ProgressHelper.addProgressResponseListener(OkHttpManager.getInstance().getOkHttpClient(), uiProgressResponseListener);
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Response response) throws IOException {
                File folder = new File(path);
                if (!folder.exists()) {
                    boolean flag = folder.mkdirs();
                    if (flag) {
                        WriteFile(response, path, url);
                    }
                } else {
                    WriteFile(response, path, url);
                }
            }

            @Override
            public void onFailure(Request request, IOException e) {
                L.e(TAG, "request:" + request.body().toString() + ",IOException:" + e.getMessage());
            }

        });
    }

    private void InstallApk(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    /**
     * IO流写入到文件
     *
     * @param response IO流包装体
     * @param path     文件地址
     * @param url      文件网络URL
     */
    private void WriteFile(Response response, String path, String url) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            File file = new File(path, getFileName(url));
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();

            InstallApk(path);

        } catch (IOException e) {
            L.e(TAG, "文件写入失败:" + e.getMessage());
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                L.e(TAG, "InputStream关闭异常:" + e.getMessage());
            }

            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                L.e(TAG, "FileOutputStream关闭异常:" + e.getMessage());
            }
        }
    }

    /**
     * 截取文件名
     *
     * @param path 文件地址
     * @return 文件名
     */
    private String getFileName(String path) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        String time = sdf.format(new Date(System.currentTimeMillis()));
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length()) + time;
    }

    @Override
    protected void onResume() {
        super.onResume();
        count = 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about_us, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}