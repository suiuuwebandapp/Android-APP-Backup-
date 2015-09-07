package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
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
import com.minglang.suiuu.entity.PersonalCenter.PersonalCenterData.UserAptitudeEntity;
import com.minglang.suiuu.entity.PersonalCenter.PersonalCenterData.UserCardEntity;
import com.minglang.suiuu.entity.PersonalCenter.PersonalCenterData.UserInfoEntity;
import com.minglang.suiuu.interfaces.RecyclerViewOnItemClickListener;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.DrawableUtils;
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.Utils;
import com.squareup.okhttp.Request;

import org.json.JSONException;
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

    private static final String ID = "id";

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

    private ProgressDialog progressDialog;

    private List<TripListEntity> listAll = new ArrayList<>();

    private PersonalMainPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_main_pager);
        ButterKnife.bind(this);
        initView();
        viewAction();
        sendRequest();
    }

    private void initView() {
        userSign = getIntent().getStringExtra(USER_SIGN);
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

        DeBugLog.i(TAG, "userSign:" + userSign + ",verification:" + verification + ",token:" + token);
    }

    private void viewAction() {

        adapter.setOnItemClickListener(new RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                DeBugLog.i(TAG, "click position:" + position);
                Intent intent = new Intent(PersonalMainPagerActivity.this, TripGalleryDetailsActivity.class);
                intent.putExtra(ID, listAll.get(position).getTripId());
                startActivity(intent);
            }
        });

        tripImageTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(userSign)) {
                    Intent intent = new Intent(PersonalMainPagerActivity.this, PersonalTripGalleryActivity.class);
                    intent.putExtra(USER_SIGN, userSign);
                    startActivity(intent);
                }
            }
        });

        questionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(userSign)) {
                    Intent intent = new Intent(PersonalMainPagerActivity.this, PersonalProblemActivity.class);
                    intent.putExtra(USER_SIGN, userSign);
                    startActivity(intent);
                }
            }
        });

        attentionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(userSign)) {
                    Intent intent = new Intent(PersonalMainPagerActivity.this, AttentionActivity.class);
                    intent.putExtra(USER_SIGN, userSign);
                    startActivity(intent);
                }
            }
        });

        commentContentNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        DeBugLog.i(TAG, "Request Url:" + url);
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

    private void bindData2View(String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(PersonalMainPagerActivity.this, NoData, Toast.LENGTH_SHORT).show();
        } else {
            try {
                PersonalCenter personalCenter = JsonUtils.getInstance().fromJSON(PersonalCenter.class, str);
                if (personalCenter == null) {
                    Toast.makeText(PersonalMainPagerActivity.this, NoData, Toast.LENGTH_SHORT).show();
                } else {
                    PersonalCenterData data = personalCenter.getData();
                    if (data != null) {

                        String tripCount = data.getTpCount();
                        if (!TextUtils.isEmpty(tripCount)) {
                            tripImageTitle.setText(strTripImage + tripCount);
                        }

                        String questionsCount = data.getQaCount();
                        if (!TextUtils.isEmpty(questionsCount)) {
                            questionTitle.setText(strQuestions + questionsCount);
                        }

                        String attentionCount = data.getCollectCount();
                        if (!TextUtils.isEmpty(attentionCount)) {
                            attentionTitle.setText(strAttention + attentionCount);
                        }

                        String commentNumber = data.getCommentNumb();
                        if (!TextUtils.isEmpty(commentNumber)) {
                            commentContentNumber.setText(AllComment + " " + commentNumber);
                        }

                    }

                    //TODO 个人主页 用户信息
                    UserInfoEntity userInfo = personalCenter.getData().getUserInfo();
                    if (userInfo != null) {

                        String headImagePath = userInfo.getHeadImg();
                        if (!TextUtils.isEmpty(headImagePath)) {
                            headImageView.setImageURI(Uri.parse(headImagePath));
                        }

                        String userName = userInfo.getNickname();
                        if (!TextUtils.isEmpty(userName)) {
                            userNameView.setText(userName);
                        } else {
                            userNameView.setText("");
                        }

                        String countryName = userInfo.getCountryCname();
                        String cityName = userInfo.getCityCname();
                        if (!TextUtils.isEmpty(countryName)) {
                            if (!TextUtils.isEmpty(cityName)) {
                                userLocation.setText(countryName + "," + cityName);
                            } else {
                                userLocation.setText(countryName);
                            }
                        } else {
                            userLocation.setText("");
                        }

                        String birthday = userInfo.getBirthday();
                        if (!TextUtils.isEmpty(birthday)) {
                            String age = Utils.calculateAge(birthday);
                            if (!TextUtils.isEmpty(age)) {
                                userAge.setText(age);
                            } else {
                                userAge.setText("");
                            }
                        } else {
                            userAge.setText("");
                        }

                        String info = userInfo.getInfo();
                        if (!TextUtils.isEmpty(info)) {
                            infoView.setText(info);
                        } else {
                            infoView.setText("");
                        }

                        String email = userInfo.getEmail();
                        if (!TextUtils.isEmpty(email)) {
                            verEmail.setCompoundDrawables(null, null, DrawableUtils.setBounds(hook), null);
                        }

                        String phone = userInfo.getPhone();
                        if (!TextUtils.isEmpty(phone)) {
                            verPhone.setCompoundDrawables(null, null, DrawableUtils.setBounds(hook), null);
                        }

                        String profession = userInfo.getProfession();
                        if (!TextUtils.isEmpty(profession)) {
                            userProfession.setText(profession);
                        } else {
                            userProfession.setText("");
                        }

                    } else {
                        userNameView.setText("");
                        infoView.setText("");
                        userLocation.setText("");
                        userAge.setText("");
                    }

                    List<CommentInfoEntity> list = personalCenter.getData().getCommentInfo();
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

                    UserCardEntity userCard = personalCenter.getData().getUserCard();
                    if (userCard != null) {
                        verName.setCompoundDrawables(null, null, DrawableUtils.setBounds(hook), null);
                    }

                    UserAptitudeEntity userAptitude = personalCenter.getData().getUserAptitude();
                    if (userAptitude != null) {
                        verExperience.setCompoundDrawables(null, null, DrawableUtils.setBounds(hook), null);
                    }

                    List<TripListEntity> tripList = personalCenter.getData().getTripList();
                    if (tripList != null && tripList.size() > 0) {
                        listAll.addAll(tripList);
                        adapter.setList(listAll);
                    }

                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析异常:" + e.getMessage());
                try {
                    JSONObject object = new JSONObject(str);
                    String status = object.getString(STATUS);
                    if (status.equals("-1")) {
                        Toast.makeText(PersonalMainPagerActivity.this, SystemException, Toast.LENGTH_SHORT).show();
                    } else if (status.equals("-2")) {
                        Toast.makeText(PersonalMainPagerActivity.this, object.getString(DATA), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    Toast.makeText(PersonalMainPagerActivity.this, DataError, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class PersonalCenterResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "个人主页返回的数据:" + response);
            hideDialog();
            bindData2View(response);
        }

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "Exception:" + e.getMessage());
            DeBugLog.i(TAG, "request:" + request.body().toString());
            hideDialog();
            Toast.makeText(PersonalMainPagerActivity.this, NetWorkError, Toast.LENGTH_SHORT).show();
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