package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.PersonalTravelAdapter;
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
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 个人中心页面
 */
public class PersonalMainPagerActivity extends BaseAppCompatActivity {

    private static final String TAG = PersonalMainPagerActivity.class.getSimpleName();

    private static final String USERSIGN = "userSign";

    @BindString(R.string.load_wait)
    String DialogMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetWorkError;

    @BindString(R.string.MainTitle1)
    String strTripImage;

    @BindString(R.string.QuestionsAndAnswers)
    String strQuestions;

    @BindString(R.string.Attention)
    String strAttention;

    @BindDrawable(R.drawable.icon_hook)
    Drawable hook;

    @Bind(R.id.personal_center_app_bar_layout)
    AppBarLayout appBarLayout;

    @Bind(R.id.personal_center_toolbar)
    Toolbar toolbar;

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

    @Bind(R.id.personal_center_other_user_head_image_view)
    SimpleDraweeView commentUserHeadImageView;

    @Bind(R.id.personal_center_other_user_name)
    TextView commentUserNameView;

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

    private PersonalTravelAdapter adapter;

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
        userSign = SuiuuInfo.ReadUserSign(this);
        verification = SuiuuInfo.ReadVerification(this);
        token = SuiuuInfo.ReadAppTimeSign(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(DialogMsg);
        progressDialog.setCanceledOnTouchOutside(false);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        adapter = new PersonalTravelAdapter();

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void viewAction() {

        appBarLayout.setLayoutAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                DeBugLog.i(TAG, "AppLayout Start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                DeBugLog.i(TAG, "AppLayout End");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                DeBugLog.i(TAG, "AppLayout Repeat");
            }

        });

        adapter.setOnItemClickListener(new RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

    }

    private void sendRequest() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }

        String[] keyArray = new String[]{USERSIGN, TOKEN};
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
                    }

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

                    } else {
                        userNameView.setText("");
                        infoView.setText("");
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
                Toast.makeText(this, DataError, Toast.LENGTH_SHORT).show();
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