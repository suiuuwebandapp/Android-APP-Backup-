package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.PersonalMainPagerAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.PersonalCenter;
import com.minglang.suiuu.entity.PersonalCenter.PersonalCenterData;
import com.minglang.suiuu.entity.PersonalCenter.PersonalCenterData.CommentInfoEntity;
import com.minglang.suiuu.entity.PersonalCenter.PersonalCenterData.TripListEntity;
import com.minglang.suiuu.entity.PersonalCenter.PersonalCenterData.UserInfoEntity;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemClickListener;
import com.minglang.suiuu.utils.AppUtils;
import com.minglang.suiuu.utils.DrawableUtils;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 个人中心页面
 */
public class PersonalMainPagerActivity extends BaseAppCompatActivity {

    private static final String TAG = PersonalMainPagerActivity.class.getSimpleName();

    private static final String USER_SIGN = "userSign";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private static final String TRIP_ID = "tripId";
    private static final String RELATE_ID = "relateId";
    private static final String HEAD_IMAGE_PATH = "headImagePath";

    private static final String CLASS_NAME = "className";

    @BindColor(R.color.transparent)
    int titleColor;

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetWorkError;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.LoginInvalid)
    String LoginInvalid;

    @BindString(R.string.MainTitle1)
    String strTripImage;

    @BindString(R.string.QuestionsAndAnswers)
    String strQuestions;

    @BindString(R.string.Attention)
    String strAttention;

    @BindString(R.string.AllComment)
    String AllComment;

    @BindDrawable(R.drawable.icon_hook)
    Drawable hook;

    @Bind(R.id.personal_center_toolbar)
    Toolbar toolbar;

    @Bind(R.id.personal_center_chat_view)
    ImageView chatView;

    @Bind(R.id.personal_center_recycler_header_view)
    RecyclerViewHeader recyclerViewHeader;

    @Bind(R.id.personal_center_head_image_view)
    SimpleDraweeView headImageView;

    @Bind(R.id.personal_center_user_name)
    TextView userNameView;

    @Bind(R.id.personal_center_trip_image)
    TextView tripImageTitle;

    @Bind(R.id.personal_center_question)
    TextView questionTitle;

    @Bind(R.id.personal_center_attention)
    TextView attentionTitle;

    @Bind(R.id.personal_center_info)
    TextView infoView;

    @Bind(R.id.personal_center_user_location)
    TextView userLocation;

    @Bind(R.id.personal_center_user_profession)
    TextView userProfession;

    @Bind(R.id.personal_center_user_age)
    TextView userAge;

    @Bind(R.id.personal_center_other_user_head_image_view)
    SimpleDraweeView commentUserHeadImageView;

    @Bind(R.id.personal_center_other_user_name)
    TextView commentUserNameView;

    @Bind(R.id.personal_center_other_user_comment_number)
    TextView commentContentNumber;

    @Bind(R.id.personal_center_comment_content)
    TextView commentContentView;

    @Bind(R.id.personal_center_email_ver)
    TextView verEmail;

    @Bind(R.id.personal_center_phone_ver)
    TextView verPhone;

    @Bind(R.id.personal_center_name_ver)
    TextView verName;

    @Bind(R.id.personal_center_experience_ver)
    TextView verExperience;

    @Bind(R.id.personal_center_recycler_view)
    RecyclerView recyclerView;

    private boolean isPublisher = false;

    private ProgressDialog progressDialog;

    private List<TripListEntity> listAll = new ArrayList<>();

    private PersonalMainPagerAdapter adapter;

    private String headImagePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_main_pager);
        ButterKnife.bind(this);
        initView();
        viewAction();
        sendRequest();
    }

    private void initView() {
        userSign = getIntent().getStringExtra(USER_SIGN);
        String oneselfUserSign = SuiuuInfo.ReadUserSign(this);
        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        adapter = new PersonalMainPagerAdapter();

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerViewHeader.attachTo(recyclerView, true);

        String publisher = SuiuuInfo.ReadUserData(this).getIsPublisher();
        isPublisher = !TextUtils.isEmpty(publisher) && publisher.equals("1");

        if (oneselfUserSign.equals(userSign)) {
            chatView.setVisibility(View.GONE);
        } else {
            chatView.setVisibility(View.VISIBLE);
        }
    }

    private void viewAction() {

        chatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.i(TAG, "userSign:" + userSign + ",headImagePath:" + headImagePath);
                Intent intent = new Intent(PersonalMainPagerActivity.this, PrivateLetterChatActivity.class);
                intent.putExtra(RELATE_ID, userSign);
                intent.putExtra(HEAD_IMAGE_PATH, headImagePath);
                startActivity(intent);
            }
        });

        adapter.setOnItemClickListener(new RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                L.i(TAG, "click position:" + position);
                Intent intent = new Intent(PersonalMainPagerActivity.this, SuiuuDetailsActivity.class);
                intent.putExtra(TRIP_ID, listAll.get(position).getTripId());
                intent.putExtra(CLASS_NAME, TAG);
                startActivity(intent);
            }
        });

        tripImageTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalMainPagerActivity.this, PersonalTripImageActivity.class);
                intent.putExtra(USER_SIGN, userSign);
                startActivity(intent);
            }
        });

        questionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalMainPagerActivity.this, PersonalProblemActivity.class);
                intent.putExtra(USER_SIGN, userSign);
                startActivity(intent);
            }
        });

        attentionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalMainPagerActivity.this, AttentionActivity.class);
                intent.putExtra(USER_SIGN, userSign);
                startActivity(intent);
            }
        });

        commentContentNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalMainPagerActivity.this, SuiuuCommentActivity.class);
                intent.putExtra(USER_SIGN, userSign);
                startActivity(intent);
            }
        });

    }

    private void sendRequest() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        String[] keyArray = new String[]{USER_SIGN, TOKEN};
        String[] valueArray = new String[]{userSign, token};
        String url = addUrlAndParams(HttpNewServicePath.getPersonalMainPagePath, keyArray, valueArray);
        try {
            OkHttpManager.onGetAsynRequest(url, new PersonalCenterResultCallback());
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(PersonalMainPagerActivity.this, NetWorkError, Toast.LENGTH_SHORT).show();
        }
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 将数据绑定到View上
     *
     * @param str Json字符串数据
     */
    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(PersonalMainPagerActivity.this, NoData, Toast.LENGTH_SHORT).show();
        } else try {
            JSONObject object = new JSONObject(str);
            String status = object.getString(STATUS);
            switch (status) {
                case "1":
                    PersonalCenter personalCenter = JsonUtils.getInstance().fromJSON(PersonalCenter.class, str);
                    if (personalCenter == null) {
                        Toast.makeText(PersonalMainPagerActivity.this, NoData, Toast.LENGTH_SHORT).show();
                    } else {
                        //TODO 个人主页 用户信息
                        UserInfoEntity userInfo = personalCenter.getData().getUserInfo();
                        bindUserInfo2View(userInfo);

                        PersonalCenterData data = personalCenter.getData();
                        bindUserReleaseInfo2View(data);

                        //TODO 绑定评论数据
                        List<CommentInfoEntity> list = personalCenter.getData().getCommentInfo();
                        bindCommentInfo2View(list);

                        //TODO 实名验证
                        PersonalCenterData.UserCardEntity userCard = personalCenter.getData().getUserCard();
                        if (userCard != null) {
                            verName.setCompoundDrawables(null, null, DrawableUtils.setBounds(hook), null);
                        }

                        //TODO 经历验证
                        PersonalCenterData.UserAptitudeEntity userAptitude = personalCenter.getData().getUserAptitude();
                        if (userAptitude != null) {
                            verExperience.setCompoundDrawables(null, null, DrawableUtils.setBounds(hook), null);
                        }

                        //TODO 相关随游数据绑定
                        if (isPublisher) {
                            List<TripListEntity> tripList = personalCenter.getData().getTripList();
                            if (tripList != null && tripList.size() > 0) {
                                listAll.addAll(tripList);
                                adapter.setList(listAll);
                            }
                        }

                    }
                    break;

                case "-1":
                    Toast.makeText(PersonalMainPagerActivity.this, SystemException, Toast.LENGTH_SHORT).show();
                    break;

                case "-2":
                    Toast.makeText(PersonalMainPagerActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
                    break;

                case "-3":
                    Toast.makeText(this, LoginInvalid, Toast.LENGTH_SHORT).show();
                    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    localBroadcastManager.sendBroadcast(new Intent(SettingActivity.class.getSimpleName()));
                    ReturnLoginActivity(this);
                    break;

                case "-4":
                    Toast.makeText(PersonalMainPagerActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
                    break;

            }
        } catch (Exception e) {
            L.e(TAG, "解析异常:" + e.getMessage());
            Toast.makeText(PersonalMainPagerActivity.this, DataError, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 将用户信息绑定到View上
     *
     * @param userInfo 用户信息数据实体类
     */
    private void bindUserInfo2View(UserInfoEntity userInfo) {
        if (userInfo != null) {
            headImagePath = userInfo.getHeadImg();//用户头像
            if (!TextUtils.isEmpty(headImagePath)) {
                headImageView.setImageURI(Uri.parse(headImagePath));
            }

            String userName = userInfo.getNickname();//用户昵称
            if (!TextUtils.isEmpty(userName)) {
                userNameView.setText(userName);
            } else {
                userNameView.setText("");
            }

            String countryName = userInfo.getCountryCname();//用户所在国家
            String cityName = userInfo.getCityCname();//用户所在城市
            if (!TextUtils.isEmpty(countryName)) {
                if (!TextUtils.isEmpty(cityName)) {
                    userLocation.setText(String.format("%s%s%s", countryName, ",", cityName));
                } else {
                    userLocation.setText(countryName);
                }
            } else {
                userLocation.setText("");
            }

            String profession = userInfo.getProfession();//用户职业
            if (!TextUtils.isEmpty(profession)) {
                userProfession.setText(profession);
            } else {
                userProfession.setText("");
            }

            String birthday = userInfo.getBirthday();//用户生日
            if (!TextUtils.isEmpty(birthday)) {
                String age = AppUtils.calculateAge(birthday);
                if (!TextUtils.isEmpty(age)) {
                    userAge.setText(age);
                } else {
                    userAge.setText("");
                }
            } else {
                userAge.setText("");
            }

            String info = userInfo.getInfo();//用户个人简介
            if (!TextUtils.isEmpty(info)) {
                infoView.setText(info);
            } else {
                infoView.setText("");
            }

            String email = userInfo.getEmail();//用户邮箱
            if (!TextUtils.isEmpty(email)) {
                verEmail.setCompoundDrawables(null, null, DrawableUtils.setBounds(hook), null);
            }

            String phone = userInfo.getPhone();//用户电话号码
            if (!TextUtils.isEmpty(phone)) {
                verPhone.setCompoundDrawables(null, null, DrawableUtils.setBounds(hook), null);
            }

        } else {
            userNameView.setText("");
            infoView.setText("");
            userLocation.setText("");
            userAge.setText("");
        }
    }

    /**
     * 将用户的相关数据绑定到View上
     *
     * @param data 用户相关数据
     */
    private void bindUserReleaseInfo2View(PersonalCenterData data) {
        if (data != null) {
            String tripCount = data.getTpCount();//旅图数量
            if (!TextUtils.isEmpty(tripCount)) {
                String str1 = strTripImage + tripCount;
                tripImageTitle.setText(str1);
            }

            String questionsCount = data.getQaCount();//问答数量
            if (!TextUtils.isEmpty(questionsCount)) {
                String str2 = strQuestions + questionsCount;
                questionTitle.setText(str2);
            }

            String attentionCount = data.getCollectCount();//关注数量
            if (!TextUtils.isEmpty(attentionCount)) {
                String str3 = strAttention + attentionCount;
                attentionTitle.setText(str3);
            }

            String commentNumber = data.getCommentNumb();//评论数量
            if (!TextUtils.isEmpty(commentNumber)) {
                String str4 = AllComment + commentNumber;
                commentContentNumber.setText(str4);
            }

        }
    }

    /**
     * 将评论数据绑定到View上
     *
     * @param list 评论数据集合
     */
    private void bindCommentInfo2View(List<CommentInfoEntity> list) {
        if (list != null && list.size() > 0) {
            CommentInfoEntity commentInfo = list.get(0);

            String otherUserHeadImagePath = commentInfo.getHeadImg();
            if (!TextUtils.isEmpty(otherUserHeadImagePath)) {
                commentUserHeadImageView.setImageURI(Uri.parse(otherUserHeadImagePath));
            }

            String otherUserName = commentInfo.getNickname();
            if (!TextUtils.isEmpty(otherUserHeadImagePath)) {
                commentUserNameView.setText(otherUserName);
            } else {
                commentUserNameView.setText("");
            }

            String otherUserContent = commentInfo.getContent();
            if (!TextUtils.isEmpty(otherUserContent)) {
                commentContentView.setText(otherUserContent);
            } else {
                commentContentView.setText("");
            }
        } else {
            commentUserNameView.setText("");
            commentContentView.setText("");
        }
    }

    private class PersonalCenterResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "个人主页返回的数据:" + response);
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "Exception:" + e.getMessage());
            Toast.makeText(PersonalMainPagerActivity.this, NetWorkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
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