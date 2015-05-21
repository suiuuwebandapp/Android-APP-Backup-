package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.UserBack;
import com.minglang.suiuu.entity.UserBackData;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.DrawableUtils;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;

@SuppressWarnings("deprecation")
public class PersonalSettingActivity extends BaseActivity {

    private static final String TAG = PersonalSettingActivity.class.getSimpleName();

    private static final int UP_LOAD_SUCCESS = 200;

    private static final int UP_LOAD_FAIL = 400;

    private static final int UP_LOADING = 300;

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

    private TextView localDetails;

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

    private String netWorkImagePath;

//    private boolean isSelectHeadImage;

    /**
     * 上传进度框
     */
    private ProgressDialog upLoadDialog;

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

                    DeBugLog.i(TAG, "图片上传成功:" + s);
                    netWorkImagePath = AppConstant.IMG_FROM_SUIUU + s;
                    DeBugLog.i(TAG, "NetworkImagePath:" + netWorkImagePath);

                    SuiuuInfo.WriteNativeHeadImagePath(PersonalSettingActivity.this, nativeImagePath);
                    SuiuuInfo.WriteUserHeadImagePath(PersonalSettingActivity.this, netWorkImagePath);

                    if (upLoadDialog.isShowing()) {
                        upLoadDialog.dismiss();
                    }
                    Toast.makeText(PersonalSettingActivity.this, "操作已完成！", Toast.LENGTH_SHORT).show();
                    break;

                case UP_LOAD_FAIL:
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(PersonalSettingActivity.this,
                            getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
                    break;

                case UP_LOADING:
                    if (!upLoadDialog.isShowing()) {
                        upLoadDialog.show();
                    }
                    break;
            }
            return false;
        }
    });

    private String countryId, cityId;

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
                    setData();
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
                sex_man.setCompoundDrawables(DrawableUtils.setBounds(getResources().getDrawable(R.drawable.sex_man)),
                        null, null, null);
                sex_woman.setCompoundDrawables(DrawableUtils.setBounds(getResources().getDrawable(R.drawable.sex_none)),
                        null, null, null);
                strGender = "1";
            }
        });

        sex_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex_woman.setCompoundDrawables(DrawableUtils.setBounds(getResources().getDrawable(R.drawable.sex_woman)),
                        null, null, null);
                sex_man.setCompoundDrawables(DrawableUtils.setBounds(getResources().getDrawable(R.drawable.sex_none)),
                        null, null, null);
                strGender = "0";
            }
        });

        localDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalSettingActivity.this, SelectCountryActivity.class);
                startActivityForResult(intent, AppConstant.SELECT_COUNTRY_OK);
            }
        });

    }

    /**
     * 上传头像到服务器
     */
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
                    DeBugLog.i(TAG, "图片上传中:" + objectKey + ",byteCount:" + byteCount + ",totalSize:" + totalSize);
                    handler.sendEmptyMessage(UP_LOADING);
                }

                @Override
                public void onFailure(String s, OSSException e) {
                    DeBugLog.e(TAG, "上传失败:" + s);
                    DeBugLog.e(TAG, "数据更新异常:" + e.getMessage());
                    handler.sendEmptyMessage(UP_LOAD_FAIL);
                }
            });
        } catch (Exception e) {
            DeBugLog.e(TAG, "文件未找到:" + e.getMessage());
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
        params.addBodyParameter("headImg", netWorkImagePath);
        params.addBodyParameter("sex", strGender);
        params.addBodyParameter("nickname", str_NickName);
        params.addBodyParameter("birthday", data.getBirthday());
        params.addBodyParameter("intro", str_Sign);
        params.addBodyParameter("info", data.getInfo());
        params.addBodyParameter("countryId", countryId);
        params.addBodyParameter("cityId", cityId);
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
        netWorkImagePath = data.getHeadImg();
        //nativeImagePath = SuiuuInfo.ReadNativeHeadImagePath(this);

        if (!TextUtils.isEmpty(netWorkImagePath)) {
            imageLoader.displayImage(netWorkImagePath, headImageView, options);
        } else {
            if (!TextUtils.isEmpty(nativeImagePath)) {
                imageLoader.displayImage("file://" + nativeImagePath, headImageView, options);
            }
        }

        String strNickName = data.getNickname();
        if (!TextUtils.isEmpty(strNickName)) {
            editNickName.setText(strNickName);
        }

        String strGender = data.getSex();
        if (!TextUtils.isEmpty(strGender)) {
            if (strGender.equals("1")) {
                sex_man.setCompoundDrawablesWithIntrinsicBounds
                        (getResources().getDrawable(R.drawable.sex_man), null, null, null);
                DeBugLog.i(TAG, "男");
            } else if (strGender.equals("0")) {
                sex_woman.setCompoundDrawablesWithIntrinsicBounds
                        (getResources().getDrawable(R.drawable.sex_woman), null, null, null);
                DeBugLog.i(TAG, "女");
            }
        }

        String countryName = SuiuuInfo.ReadDomicileCountry(this);
        String cityName = SuiuuInfo.ReadDomicilCity(this);
        if (!TextUtils.isEmpty(countryName) && !TextUtils.isEmpty(cityName)) {
            localDetails.setText(countryName + "," + cityName);
        } else if (TextUtils.isEmpty(countryName) && !TextUtils.isEmpty(cityName)) {
            localDetails.setText(cityName);
        } else if (!TextUtils.isEmpty(countryName) && TextUtils.isEmpty(cityName)) {
            localDetails.setText(countryName);
        } else {
            localDetails.setText(getResources().getString(R.string.local));
        }

        String profession = data.getProfession();
        if (!TextUtils.isEmpty(profession)) {
            editTrade.setText(profession);
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

        RelativeLayout personalRootLayout = (RelativeLayout) findViewById(R.id.personalRootLayout);
        if (isKITKAT) {
            if (navigationBarHeight <= 0) {
                personalRootLayout.setPadding(0, statusBarHeight, 0, 0);
            } else {
                personalRootLayout.setPadding(0, statusBarHeight, 0, navigationBarHeight);
            }
        }

        personalSettingBack = (ImageView) findViewById(R.id.personalSettingBack);
        save = (TextView) findViewById(R.id.personal_save);
        headImageView = (CircleImageView) findViewById(R.id.headImageView);
        sex_man = (TextView) findViewById(R.id.personal_setting_man);
        sex_woman = (TextView) findViewById(R.id.personal_setting_woman);
        localDetails = (TextView) findViewById(R.id.localDetails);
        editNickName = (EditText) findViewById(R.id.editNickName);
        editTrade = (EditText) findViewById(R.id.editTrade);
        editSign = (EditText) findViewById(R.id.editSign);

        upLoadDialog = new ProgressDialog(this);
        upLoadDialog.setMessage("正在上传...");
        upLoadDialog.setCanceledOnTouchOutside(false);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在更新数据,请稍候...");
        progressDialog.setCanceledOnTouchOutside(false);

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image).showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

        data = SuiuuInfo.ReadUserData(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            DeBugLog.i(TAG, "select picture cancel");
            return;
        }

        if (data == null) {
            DeBugLog.i(TAG, "back data is null!");
            return;
        }

        switch (requestCode) {
            case AppConstant.KITKAT_LESS:
                Uri uri = data.getData();
                nativeImagePath = Utils.getPath(PersonalSettingActivity.this, uri);
                DeBugLog.i(TAG, "uri:" + uri.toString());
                Utils.cropPicture(PersonalSettingActivity.this, uri);
                break;

            case AppConstant.KITKAT_ABOVE:
                Uri uri2 = data.getData();
                nativeImagePath = Utils.getPath(PersonalSettingActivity.this, uri2);
                DeBugLog.i(TAG, "ImagePath:" + nativeImagePath);
                Utils.cropPicture(PersonalSettingActivity.this, Uri.fromFile(new File(nativeImagePath)));
                break;

            case AppConstant.INTENT_CROP:
//                Bitmap bitmap = data.getParcelableExtra("data");
//                headImageView.setImageBitmap(bitmap);
                imageLoader.displayImage("file://" + nativeImagePath, headImageView);
//                isSelectHeadImage = true;
                break;

            case AppConstant.SELECT_COUNTRY_OK:
                countryId = data.getStringExtra("countryId");
                cityId = data.getStringExtra("cityId");
                String countryCNname = data.getStringExtra("countryCNname");
                String cityName = data.getStringExtra("cityName");
                DeBugLog.i(TAG, "countryId:" + countryId + ",countryCNname:" + countryCNname + ",cityId:"
                        + cityId + ",cityName:" + cityName);
                SuiuuInfo.WriteDomicileInfo(this, countryCNname, cityName);
                localDetails.setText(countryCNname + "," + cityName);
                break;
        }
    }

    /**
     * 发送个人资料到服务器的网络请求回调接口
     */
    private class PersonalSettingRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onSuccess(ResponseInfo<String> stringResponseInfo) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            String str = stringResponseInfo.result;
            if (TextUtils.isEmpty(str)) {
                Toast.makeText(PersonalSettingActivity.this,
                        getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
            } else {
                DeBugLog.i(TAG, "更新成功:" + str);
                try {
                    UserBack userBack = JsonUtils.getInstance().fromJSON(UserBack.class, str);
                    String status = userBack.getStatus();
                    if (!TextUtils.isEmpty(status)) {
                        if (status.equals("1")) {
                            UserBackData userBackData = userBack.getData();
                            SuiuuInfo.WriteUserData(PersonalSettingActivity.this, userBackData);

                            if (!TextUtils.isEmpty(nativeImagePath)) {
                                upLoadImage();
                            }

                        } else {
                            Toast.makeText(PersonalSettingActivity.this,
                                    getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    DeBugLog.e(TAG, "更新个人资料的返回数据解析失败:" + e.getMessage());
                    Toast.makeText(PersonalSettingActivity.this,
                            getResources().getString(R.string.DataError), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {

            DeBugLog.i(TAG, "数据更新异常:" + e.getMessage());

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Toast.makeText(PersonalSettingActivity.this,
                    getResources().getString(R.string.NetworkAnomaly), Toast.LENGTH_SHORT).show();
        }
    }
}
