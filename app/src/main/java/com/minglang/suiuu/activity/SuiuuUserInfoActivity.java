package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.CircleImageView;
import com.minglang.suiuu.entity.SuiuuUser;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuHttpRequest;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class SuiuuUserInfoActivity extends AppCompatActivity {

    private static final String TAG = SuiuuUserInfoActivity.class.getSimpleName();

    /**
     * 返回按钮
     */
    private ImageView back;

    /**
     * 头像
     */
    private CircleImageView headImage;

    /**
     * 用户名
     */
    private TextView userName;

    /**
     * 用户所在位置
     */
    private TextView userLocation;

    /**
     * 用户个性签名
     */
    private TextView userIntro;

    /**
     * 个人简介
     */
    private TextView individualResume;

    /**
     * 电话号码
     */
    private TextView phoneNumber;

    /**
     * 拨打电话
     */
    private ImageView callButton;

    /**
     * 电子邮箱地址
     */
    private TextView emailAddress;

    private TextView tripCount;

    private RatingBar ratingBar;

    private TextView hisSuiuu;

    private TextView hisParticipationSuiuu;

    private String userSign;

    private ProgressDialog progressDialog;

    private ImageLoader imageLoader;

    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suiuu_user_info);

        userSign = getIntent().getStringExtra("userSign");

        initView();
        ViewAction();
        getSuiuuUserData();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        back = (ImageView) findViewById(R.id.suiuu_user_info_back);
        headImage = (CircleImageView) findViewById(R.id.suiuu_user_info_head_image);

        userName = (TextView) findViewById(R.id.suiuu_user_info_name);
        userLocation = (TextView) findViewById(R.id.suiuu_user_info_location);
        userIntro = (TextView) findViewById(R.id.suiuu_user_info_intro);

        individualResume = (TextView) findViewById(R.id.suiuu_user_info_individual_resume);
        phoneNumber = (TextView) findViewById(R.id.suiuu_user_info_phone_number);
        callButton = (ImageView) findViewById(R.id.suiuu_user_info_call);
        emailAddress = (TextView) findViewById(R.id.suiuu_user_info_email_address);
        tripCount = (TextView) findViewById(R.id.suiuu_user_info_trip_count);
        ratingBar = (RatingBar) findViewById(R.id.suiuu_user_info_indicator);
        hisSuiuu = (TextView) findViewById(R.id.suiuu_user_info_his_suiuu);
        hisParticipationSuiuu = (TextView) findViewById(R.id.suiuu_user_info_his_participation_suiuu);

        ratingBar.setIsIndicator(true);
        ratingBar.setNumStars(5);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.load_wait));
        progressDialog.setCanceledOnTouchOutside(false);

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.default_head_image)
                .showImageForEmptyUri(R.drawable.default_head_image).showImageOnFail(R.drawable.default_head_image)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    private void ViewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strPhoneNumber = phoneNumber.getText().toString().trim();
                if (!TextUtils.isEmpty(strPhoneNumber)) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(strPhoneNumber));
                    SuiuuUserInfoActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(SuiuuUserInfoActivity.this, "暂无电话号码！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        hisSuiuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        hisParticipationSuiuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void getSuiuuUserData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("userSign", userSign);
        params.addBodyParameter(HttpServicePath.key, SuiuuInfo.ReadVerification(this));

        SuHttpRequest httpRequest = new SuHttpRequest(HttpRequest.HttpMethod.POST,
                HttpServicePath.getSuiuuUserInfoPath, new SuiuuUserDataInfoRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.requestNetworkData();
    }

    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 绑定数据到View
     *
     * @param str JSON字符串
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(this, getResources().getString(R.string.NoData), Toast.LENGTH_SHORT).show();
        } else {
            DeBugLog.i(TAG, "str:" + str);

            try {
                SuiuuUser suiuuUser = JsonUtils.getInstance().fromJSON(SuiuuUser.class, str);
                SuiuuUser.SuiuuUserData.UserEntity userEntity = suiuuUser.getData().getUser();
                SuiuuUser.SuiuuUserData.PublisherEntity publisherEntity = suiuuUser.getData().getPublisher();

                String strHeadImagePath = userEntity.getHeadImg();
                if (!TextUtils.isEmpty(strHeadImagePath)) {
                    imageLoader.displayImage(strHeadImagePath, headImage, options);
                }

                String strUserName = userEntity.getNickname();
                if (!TextUtils.isEmpty(strUserName)) {
                    userName.setText(strUserName);
                } else {
                    userName.setText("");
                }

                String strUserCountry = userEntity.getCountryCname();
                String strUserCity = userEntity.getCityCname();

                if (!TextUtils.isEmpty(strUserCountry)) {
                    if (!TextUtils.isEmpty(strUserCity)) {
                        userLocation.setText(strUserCountry + " " + strUserCity);
                    } else {
                        userLocation.setText(strUserCountry);
                    }
                } else {
                    userLocation.setText("");
                }

                String strUserIntro = userEntity.getIntro();
                if (!TextUtils.isEmpty(strUserIntro)) {
                    userIntro.setText(strUserIntro);
                } else {
                    userIntro.setText("");
                }

                String strUserInfo = userEntity.getInfo();
                if (!TextUtils.isEmpty(strUserInfo)) {
                    individualResume.setText(strUserInfo);
                } else {
                    individualResume.setText("");
                }

                String strUserPhoneNumber = userEntity.getPhone();
                if (!TextUtils.isEmpty(strUserPhoneNumber)) {
                    phoneNumber.setText(strUserPhoneNumber);
                } else {
                    phoneNumber.setText("");
                }

                String strUserEmailAddress = userEntity.getEmail();
                if (!TextUtils.isEmpty(strUserEmailAddress)) {
                    emailAddress.setText(strUserEmailAddress);
                } else {
                    emailAddress.setText("");
                }

                String strTripCount = userEntity.getTravelCount();
                if (!TextUtils.isEmpty(strTripCount)) {
                    tripCount.setText(strTripCount);
                } else {
                    tripCount.setText("");
                }

                float defaultNumber = Float.parseFloat(TextUtils.isEmpty(publisherEntity.getScore())
                        ? "0" : publisherEntity.getScore());
                ratingBar.setRating(defaultNumber);
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析失败:" + e.getMessage());
            }

        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private class SuiuuUserDataInfoRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            hideDialog();
            String str = responseInfo.result;
            bindData2View(str);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e.getMessage() + ",error:" + s);
            hideDialog();
        }

    }

}