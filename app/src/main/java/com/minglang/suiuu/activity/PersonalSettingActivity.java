package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.callback.SaveCallback;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.alibaba.sdk.android.oss.storage.OSSBucket;
import com.alibaba.sdk.android.oss.storage.OSSFile;
import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.pickerview.OptionsPopupWindow;
import com.minglang.suiuu.entity.UserBack;
import com.minglang.suiuu.entity.UserBack.UserBackData;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.StatusBarCompat;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

@SuppressWarnings("deprecation")
public class PersonalSettingActivity extends BaseAppCompatActivity {

    private static final String TAG = PersonalSettingActivity.class.getSimpleName();

    private static final int UP_LOAD_SUCCESS = 200;
    private static final int UP_LOADING = 300;
    private static final int UP_LOAD_FAIL = 400;

    private static final String SUIUU = "suiuu";

    private static final String HEAD_IMG = "headImg";
    private static final String SEX = "sex";
    private static final String NICK_NAME = "nickname";
    private static final String BIRTHDAY = "birthday";
    private static final String INTRO = "intro";
    private static final String INFO = "info";
    private static final String COUNTRY_ID = "countryId";
    private static final String CITY_ID = "cityId";
    private static final String LON = "lon";
    private static final String LAT = "lat";
    private static final String PROFESSION = "profession";

    private static final String OTHER_TAG = "OtherTag";

    @BindString(R.string.uploading)
    String uploading;

    @BindString(R.string.uploading_data)
    String uploadingData;

    @BindString(R.string.NoData)
    String noData;

    @BindString(R.string.DataError)
    String dataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.sex)
    String strSex;

    @BindString(R.string.man)
    String strMan;

    @BindString(R.string.woman)
    String strWoman;

    /**
     * 返回按钮
     */
    @Bind(R.id.personalSettingBack)
    ImageView personalSettingBack;

    /**
     * 保存按钮
     */
    @Bind(R.id.personal_save)
    TextView save;

    @Bind(R.id.personal_setting_scroll_view)
    ScrollView scrollView;

    @Bind(R.id.personal_sex)
    TextView personalSex;

    /**
     * 头像ImageView
     */
    @Bind(R.id.headImageView)
    SimpleDraweeView headImageView;

    /**
     * 昵称编辑框
     */
    @Bind(R.id.editNickName)
    EditText editNickName;

    @Bind(R.id.localDetails)
    TextView localDetails;

    /**
     * 职业编辑框
     */
    @Bind(R.id.editTrade)
    EditText editTrade;

    /**
     * 签名编辑框
     */
    @Bind(R.id.editSign)
    EditText editSign;

    private String strGender;

    private String str_NickName;

    private String str_Trade;

    private String str_Sign;

    private String nativeImagePath;

    private String netWorkImagePath;

    private ProgressDialog upLoadDialog;

    private UserBackData data;

    private ProgressDialog progressDialog;

    private static OSSService ossService = OSSServiceProvider.getService();
    private static OSSBucket bucket = ossService.getOssBucket(SUIUU);

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case UP_LOAD_SUCCESS:
                    String s = msg.obj.toString();

                    L.i(TAG, "图片上传成功:" + s);
                    netWorkImagePath = AppConstant.OSS_ROOT_PATH + s;
                    L.i(TAG, "NetworkImagePath:" + netWorkImagePath);

                    SuiuuInfo.WriteUserHeadImagePath(PersonalSettingActivity.this, netWorkImagePath);

                    if (upLoadDialog.isShowing()) {
                        upLoadDialog.dismiss();
                    }

                    Toast.makeText(PersonalSettingActivity.this, "操作已完成！", Toast.LENGTH_SHORT).show();
                    break;

                case UP_LOAD_FAIL:
                    hideDialog();
                    Toast.makeText(PersonalSettingActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
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

    private OptionsPopupWindow optionsPopupWindow;

    private ArrayList<String> optionsList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_setting);

        StatusBarCompat.compat(this);
        ButterKnife.bind(this);

        initView();
        initLoadDefaultData();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        upLoadDialog = new ProgressDialog(this);
        upLoadDialog.setMessage(uploading);
        upLoadDialog.setCanceledOnTouchOutside(false);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(uploadingData);
        progressDialog.setCanceledOnTouchOutside(false);

        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        data = SuiuuInfo.ReadUserData(this);

        optionsPopupWindow = new OptionsPopupWindow(this);

        optionsList.add(strMan);
        optionsList.add(strWoman);

        optionsPopupWindow.setPicker(optionsList);
    }

    /**
     * 添加初始化数据
     */
    private void initLoadDefaultData() {
        netWorkImagePath = data.getHeadImg();

        if (!TextUtils.isEmpty(netWorkImagePath)) {
            headImageView.setImageURI(Uri.parse(netWorkImagePath));
        } else {
            if (!TextUtils.isEmpty(nativeImagePath)) {
                headImageView.setImageURI(Uri.parse("file://com.minglang.suiuu/" + nativeImagePath));
            } else {
                headImageView.setImageURI(Uri.parse("res://com.minglang.suiuu/" + R.drawable.default_head_image_error));
            }
        }

        String strNickName = data.getNickname();
        if (!TextUtils.isEmpty(strNickName)) {
            editNickName.setText(strNickName);
        }

        String strGender = data.getSex();
        if (!TextUtils.isEmpty(strGender)) {
            if (strGender.equals("1")) {
                personalSex.setText(strSex + " 男");
            } else if (strGender.equals("0")) {
                personalSex.setText(strSex + " 女");
            }
        }

        String countryName = SuiuuInfo.ReadDomicileCountry(this);
        String cityName = SuiuuInfo.ReadDomicileCity(this);
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
                    setPersonalMessage4Service();
                }
            }
        });

        headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.selectPicture(PersonalSettingActivity.this);
            }
        });

        personalSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsPopupWindow.showAtLocation(scrollView, Gravity.BOTTOM, 0, 0);
            }
        });

        localDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalSettingActivity.this, SelectCountryActivity.class);
                intent.putExtra(OTHER_TAG, TAG);
                startActivityForResult(intent, AppConstant.SELECT_COUNTRY_OK);
            }
        });

        optionsPopupWindow.setOnOptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                if (strMan.equals(optionsList.get(options1))) {
                    strGender = "1";
                    personalSex.setText(strSex + " 男");
                } else if (strWoman.equals(optionsList.get(options1))) {
                    strGender = "0";
                    personalSex.setText(strSex + " 女");
                }
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
                    L.i(TAG, "图片上传中:" + objectKey + ",byteCount:" + byteCount + ",totalSize:" + totalSize);
                    handler.sendEmptyMessage(UP_LOADING);
                }

                @Override
                public void onFailure(String s, OSSException e) {
                    L.e(TAG, "上传失败:" + s);
                    L.e(TAG, "数据更新异常:" + e.getMessage());
                    handler.sendEmptyMessage(UP_LOAD_FAIL);
                }
            });
        } catch (Exception e) {
            L.e(TAG, "文件未找到:" + e.getMessage());
        }
    }

    private void setPersonalMessage4Service() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[12];
        paramsArray[0] = new OkHttpManager.Params(HEAD_IMG, netWorkImagePath);
        paramsArray[1] = new OkHttpManager.Params(SEX, strGender);
        paramsArray[2] = new OkHttpManager.Params(NICK_NAME, str_NickName);
        paramsArray[3] = new OkHttpManager.Params(BIRTHDAY, data.getBirthday());
        paramsArray[4] = new OkHttpManager.Params(INTRO, str_Sign);
        paramsArray[5] = new OkHttpManager.Params(INFO, data.getInfo());
        paramsArray[6] = new OkHttpManager.Params(COUNTRY_ID, countryId);
        paramsArray[7] = new OkHttpManager.Params(CITY_ID, cityId);
        paramsArray[8] = new OkHttpManager.Params(LON, data.getLon());
        paramsArray[9] = new OkHttpManager.Params(LAT, data.getLat());
        paramsArray[10] = new OkHttpManager.Params(PROFESSION, str_Trade);
        paramsArray[11] = new OkHttpManager.Params(HttpNewServicePath.key, verification);

        String url = HttpNewServicePath.upDatePersonalStatus + "?" + TOKEN + "=" + token;

        try {
            OkHttpManager.onPostAsynRequest(url, new PersonalSettingResultCallback(), paramsArray);
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(PersonalSettingActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(PersonalSettingActivity.this, noData, Toast.LENGTH_SHORT).show();
        } else {
            L.i(TAG, "更新成功:" + str);
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
                        Toast.makeText(PersonalSettingActivity.this, dataError, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                L.e(TAG, "更新个人资料的返回数据解析失败:" + e.getMessage());
                Toast.makeText(PersonalSettingActivity.this, dataError, Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            L.e(TAG, "select picture cancel");
            return;
        }

        if (data == null) {
            L.e(TAG, "back data is null!");
            return;
        }

        switch (requestCode) {
            case AppConstant.KITKAT_LESS:
                Uri uri = data.getData();
                nativeImagePath = AppUtils.getPath(PersonalSettingActivity.this, uri);
                L.i(TAG, "uri:" + uri.toString());
                AppUtils.cropPicture(PersonalSettingActivity.this, uri);
                break;

            case AppConstant.KITKAT_ABOVE:
                Uri uri2 = data.getData();
                nativeImagePath = AppUtils.getPath(PersonalSettingActivity.this, uri2);
                L.i(TAG, "ImagePath:" + nativeImagePath);
                AppUtils.cropPicture(PersonalSettingActivity.this, Uri.fromFile(new File(nativeImagePath)));
                break;

            case AppConstant.INTENT_CROP:
                headImageView.setImageURI(Uri.parse("file://" + nativeImagePath));
                break;

            case AppConstant.SELECT_COUNTRY_OK:
                countryId = data.getStringExtra("countryId");
                cityId = data.getStringExtra("cityId");

                String countryCNname = data.getStringExtra("countryCNname");
                String cityName = data.getStringExtra("cityName");

                SuiuuInfo.WriteDomicileInfo(this, countryCNname, cityName);

                localDetails.setText(countryCNname + "," + cityName);

                L.i(TAG, "countryId:" + countryId + ",countryCNname:" + countryCNname + ",cityId:"
                        + cityId + ",cityName:" + cityName);
                break;
        }
    }

    private class PersonalSettingResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "返回的数据:" + response);
            hideDialog();
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.i(TAG, "数据更新异常:" + e.getMessage());
            hideDialog();
            Toast.makeText(PersonalSettingActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

}