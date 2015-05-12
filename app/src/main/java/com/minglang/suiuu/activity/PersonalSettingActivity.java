package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.callback.SaveCallback;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.alibaba.sdk.android.oss.storage.OSSBucket;
import com.alibaba.sdk.android.oss.storage.OSSFile;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.UserBackData;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.SystemBarTintManager;
import com.minglang.suiuu.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;

@SuppressWarnings("deprecation")
public class PersonalSettingActivity extends Activity {

    private static final String TAG = PersonalSettingActivity.class.getSimpleName();

    private static final int UP_LOAD_SUCCESS = 200;

    private static final int UP_LOAD_FAIL = 400;

    /**
     * 返回按钮
     */
    private ImageView personalSettingBack;

    /**
     * 保存按钮
     */
    private TextView save;

    private TextView sex_man, sex_woman;

    /**
     * 头像ImageView
     */
    private CircleImageView headImageView;

    /**
     * 昵称编辑框
     */
    private EditText editNickName;

    private TextView countryDetails, cityDetails;

    /**
     * 职业编辑框
     */
    private EditText editTrade;

    /**
     * 签名编辑框
     */
    private EditText editSign;

    private String strGender;

    private String str_NickName;

    private String str_Trade;

    private String str_Sign;

    private String nativeImagePath;

    private String networkImagePath;

    private boolean isSelectHeadImage;

    /**
     * 上传进度框
     */
    private ProgressDialog upLoadDialog;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    private UserBackData data;

    private ProgressDialog progressDialog;

    private static OSSService ossService = OSSServiceProvider.getService();
    private static OSSBucket bucket = ossService.getOssBucket("suiuu");

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {

                case UP_LOAD_SUCCESS:
                    String s = msg.obj.toString();

                    Log.i(TAG, "图片上传成功:" + s);
                    networkImagePath = AppConstant.IMG_FROM_SUIUU_CONTENT + s;
                    Log.i(TAG, "NetworkImagePath:" + networkImagePath);

                    SuiuuInfo.WriteNativeHeadImagePath(PersonalSettingActivity.this, nativeImagePath);
                    SuiuuInfo.WriteUserHeadImagePath(PersonalSettingActivity.this, networkImagePath);

                    if (upLoadDialog.isShowing()) {
                        upLoadDialog.dismiss();
                    }

                    Toast.makeText(PersonalSettingActivity.this, "头像上传成功！", Toast.LENGTH_SHORT).show();
                    setData();
                    break;

                case UP_LOAD_FAIL:
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(PersonalSettingActivity.this,
                            getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_setting);

        initView();

        initLoadDefaultData();

        ViewAction();

    }

    /**
     * 控件行为
     */
    private void ViewAction() {

        personalSettingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_NickName = editNickName.getText().toString().trim();
                str_Trade = editTrade.getText().toString().trim();
                str_Sign = editSign.getText().toString().trim();

                if (TextUtils.isEmpty(str_NickName)) {
                    Toast.makeText(PersonalSettingActivity.this, "昵称不能为空！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_Trade)) {
                    Toast.makeText(PersonalSettingActivity.this, "职业不能为空！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_Sign)) {
                    Toast.makeText(PersonalSettingActivity.this, "签名不能为空！", Toast.LENGTH_SHORT).show();
                } else {
//                    if (isSelectHeadImage) {
                    if (TextUtils.isEmpty(nativeImagePath)) {
                        Toast.makeText(PersonalSettingActivity.this, "请选择头像！", Toast.LENGTH_SHORT).show();
                    } else {
                        isSelectHeadImage = false;

                        if (upLoadDialog != null) {
                            upLoadDialog.show();
                        }
                        upLoadImage();
                    }
//                    } else {
//                        Toast.makeText(PersonalSettingActivity.this, "您未修改头像！", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });

        headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.selectPicture(PersonalSettingActivity.this);
            }
        });

        sex_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex_man.setCompoundDrawablesWithIntrinsicBounds
                        (getResources().getDrawable(R.drawable.sex_man), null, null, null);
                sex_woman.setCompoundDrawablesWithIntrinsicBounds
                        (getResources().getDrawable(R.drawable.sex_none), null, null, null);
                strGender = "1";
            }
        });

        sex_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex_woman.setCompoundDrawablesWithIntrinsicBounds
                        (getResources().getDrawable(R.drawable.sex_woman), null, null, null);
                sex_man.setCompoundDrawablesWithIntrinsicBounds
                        (getResources().getDrawable(R.drawable.sex_none), null, null, null);
                strGender = "0";
            }
        });

        countryDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    private void upLoadImage() {
        String type = nativeImagePath.substring(nativeImagePath.lastIndexOf("/"));
        String name = type.substring(type.lastIndexOf(".") + 1);
        OSSFile bigFile = ossService.getOssFile(bucket, "suiuu_content" + type);
        try {
            bigFile.setUploadFilePath(nativeImagePath, name);
            bigFile.ResumableUploadInBackground(new SaveCallback() {
                @Override
                public void onSuccess(String s) {
                    Message message = new Message();
                    message.what = UP_LOAD_SUCCESS;
                    message.obj = s;
                    handler.sendMessage(message);
                }

                @Override
                public void onProgress(String objectKey, int byteCount, int totalSize) {
                    Log.i(TAG, "图片上传中:" + objectKey + ",byteCount:" + byteCount + ",totalSize:" + totalSize);
                    int percent = byteCount / totalSize;
                    Log.i(TAG, "百分比:" + percent + "%");
                }

                @Override
                public void onFailure(String s, OSSException e) {
                    Log.e(TAG, "上传失败:" + s);
                    Log.e(TAG, "数据更新异常:" + e.getMessage());

                    handler.sendEmptyMessage(UP_LOAD_FAIL);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "文件未找到:" + e.getMessage());
        }
    }

    private void setData() {

        if (progressDialog != null) {
            progressDialog.show();
        }

        setPersonalMessage4Service();
    }

    private void setPersonalMessage4Service() {
        String verification = SuiuuInfo.ReadVerification(this);

        RequestParams params = new RequestParams();
        params.addBodyParameter("headImg", networkImagePath);
        params.addBodyParameter("sex", strGender);
        params.addBodyParameter("nickname", str_NickName);
        params.addBodyParameter("birthday", data.getBirthday());
        params.addBodyParameter("intro", str_Sign);
        params.addBodyParameter("info", data.getInfo());
        params.addBodyParameter("countryId", "0");
        params.addBodyParameter("cityId", "0");
        params.addBodyParameter("lon", data.getLon());
        params.addBodyParameter("lat", data.getLat());
        params.addBodyParameter("profession", str_Trade);
        params.addBodyParameter(HttpServicePath.key, verification);

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.upDatePersonalStatus, new PersonalSettingRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    /**
     * 添加初始化数据
     */
    private void initLoadDefaultData() {
        String headImagePath = data.getHeadImg();
        String NativeHeadImagePath = SuiuuInfo.ReadNativeHeadImagePath(this);

        if (!TextUtils.isEmpty(headImagePath)) {
            imageLoader.displayImage(headImagePath, headImageView, options);
        } else {
            if (!TextUtils.isEmpty(NativeHeadImagePath)) {
                imageLoader.displayImage("file://" + NativeHeadImagePath, headImageView, options);
            }
        }

        String strNickName = data.getNickname();
        if (!TextUtils.isEmpty(strNickName)) {
            editNickName.setText(strNickName);
        }

        String strGender = data.getSex();
        if (!TextUtils.isEmpty(strGender)) {
            switch (strGender) {
                case "1":
                    sex_man.setCompoundDrawablesWithIntrinsicBounds
                            (getResources().getDrawable(R.drawable.sex_man), null, null, null);
                    break;
                case "0":
                    sex_woman.setCompoundDrawablesWithIntrinsicBounds
                            (getResources().getDrawable(R.drawable.sex_woman), null, null, null);
                    break;
            }
        }

        String signature = data.getIntro();
        if (!TextUtils.isEmpty(signature)) {
            editSign.setText(signature);
        }
    }

    /**
     * 初始化方法
     */
    private void initView() {
        /****************设置状态栏颜色*************/
        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        SystemBarTintManager.SystemBarConfig systemBarConfig = mTintManager.getConfig();

        int statusBarHeight = systemBarConfig.getStatusBarHeight();

        /**
         系统版本是否高于4.4
         */
        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKITKAT) {
            RelativeLayout personalRootLayout = (RelativeLayout) findViewById(R.id.personalRootLayout);
            personalRootLayout.setPadding(0, statusBarHeight, 0, 0);
        }

        personalSettingBack = (ImageView) findViewById(R.id.personalSettingBack);

        save = (TextView) findViewById(R.id.personal_save);

        headImageView = (CircleImageView) findViewById(R.id.headImageView);

        sex_man = (TextView) findViewById(R.id.personal_setting_man);
        sex_woman = (TextView) findViewById(R.id.personal_setting_woman);

        countryDetails = (TextView) findViewById(R.id.countryDetails);
        cityDetails = (TextView) findViewById(R.id.cityDetails);

        editNickName = (EditText) findViewById(R.id.editNickName);
        editTrade = (EditText) findViewById(R.id.editTrade);
        editSign = (EditText) findViewById(R.id.editSign);

        upLoadDialog = new ProgressDialog(this);
        upLoadDialog.setMessage("正在上传...");
        upLoadDialog.setCanceledOnTouchOutside(false);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在更新数据,请稍候...");
        progressDialog.setCanceledOnTouchOutside(false);

        imageLoader = ImageLoader.getInstance();
        if (imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        }
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image).showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

        data = SuiuuInfo.ReadUserData(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            Log.i(TAG, "select picture cancel");
            return;
        }

        if (data == null) {
            Log.i(TAG, "back data is null!");
            return;
        }

        switch (requestCode) {
            case AppConstant.KITKAT_LESS:
                Uri uri = data.getData();
                nativeImagePath = Utils.getPath(PersonalSettingActivity.this, uri);
                Log.i(TAG, "uri:" + uri.toString());
                Utils.cropPicture(PersonalSettingActivity.this, uri);
                break;

            case AppConstant.KITKAT_ABOVE:
                Uri uri2 = data.getData();
                nativeImagePath = Utils.getPath(PersonalSettingActivity.this, uri2);
                Log.i(TAG, "ImagePath:" + nativeImagePath);
                Utils.cropPicture(PersonalSettingActivity.this, Uri.fromFile(new File(nativeImagePath)));
                break;

            case AppConstant.INTENT_CROP:
                Log.i(TAG, "裁剪返回的数据:" + data.toString());
                Bitmap bitmap = data.getParcelableExtra("data");
                headImageView.setImageBitmap(bitmap);
                isSelectHeadImage = true;
                break;
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class PersonalSettingRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            String str = stringResponseInfo.result;
            Log.i(TAG, "个人数据保存成功:" + str);
        }

        @Override
        public void onFailure(HttpException e, String s) {

            Log.i(TAG, "数据更新异常:" + e.getMessage());

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Toast.makeText(PersonalSettingActivity.this,
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }
}
