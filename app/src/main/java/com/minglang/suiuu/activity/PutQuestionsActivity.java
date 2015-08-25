package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.InvitationAnswerAdapter;
import com.minglang.suiuu.adapter.InvitationAnswerAdapter.OnLoadInvitationAnswerUserListener;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.entity.InvitationAnswer;
import com.minglang.suiuu.entity.InvitationAnswer.InvitationAnswerData.InviteUserEntity;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.DeBugLog;
import com.minglang.suiuu.utils.HttpServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.SuiuuHttp;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 提问页面
 */
public class PutQuestionsActivity extends BaseAppCompatActivity {

    private static final String TAG = PutQuestionsActivity.class.getSimpleName();

    private static final String COUNTRY_ID = "countryId";
    private static final String CITY_ID = "cityId";

    private static final String COUNTRY_CN_NAME = "countryCNname";
    private static final String CITY_NAME = "cityName";

    @BindString(R.string.load_wait)
    String wait;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.PleaseSelectCountry)
    String PleaseSelectCountry;

    @BindColor(R.color.white)
    int titleTextColor;

    @Bind(R.id.question_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.questions_scroll_view)
    ScrollView scrollView;

    @Bind(R.id.input_problem_title)
    EditText inputQuestionTitle;

    @Bind(R.id.input_problem_content)
    EditText inputProblemContent;

    @Bind(R.id.location_layout)
    FrameLayout locationLayout;

    @Bind(R.id.question_location_view)
    TextView questionLocation;

    @Bind(R.id.question_flow_layout)
    FlowLayout questionFlowLayout;

    @Bind(R.id.answer_user_layout)
    LinearLayout answerUserLayout;

    @Bind(R.id.answer_user_list_view)
    NoScrollBarListView invitationAnswerUserListView;

    private ImageView addHeadView;

    private String countryId = null;

    private String cityId = null;

    private ProgressDialog progressDialog;

    private List<InviteUserEntity> listAll = new ArrayList<>();

    private InvitationAnswerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_questions);

        countryId = getIntent().getStringExtra(COUNTRY_ID);
        cityId = getIntent().getStringExtra(CITY_ID);
        DeBugLog.i(TAG, "countryId:" + countryId + ",cityId:" + cityId);

        ButterKnife.bind(this);
        initView();
        viewAction();

        if (TextUtils.isEmpty(countryId) && TextUtils.isEmpty(cityId)) {
            promptBuildRequestParams();
        } else {
            getInvitationAnswerData(buildRequestParams());
        }
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(wait);
        progressDialog.setCanceledOnTouchOutside(false);

        toolbar.setTitleTextColor(titleTextColor);
        setSupportActionBar(toolbar);

        scrollView.smoothScrollTo(0, 0);

        addHeadView = (ImageView) LayoutInflater.from(this)
                .inflate(R.layout.view_flow_layout_head, questionFlowLayout, false);
        questionFlowLayout.addView(addHeadView);

        adapter = new InvitationAnswerAdapter(this, listAll, R.layout.item_invitation_answer);
        invitationAnswerUserListView.setAdapter(adapter);
    }

    private void viewAction() {

        addHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        invitationAnswerUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        locationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PutQuestionsActivity.this, SelectCountryActivity.class);
                startActivityForResult(intent, AppConstant.SELECT_COUNTRY_OK);
            }
        });

        adapter.setOnLoadInvitationAnswerUserListener(new OnLoadInvitationAnswerUserListener() {
            @Override
            public void onLoadInvitation(InviteUserEntity inviteUserEntity, long position) {
                DeBugLog.i(TAG, "第" + position + "条的用户的用户名是" + inviteUserEntity.getNickname()
                        + ",UserSign是:" + inviteUserEntity.getUserSign());
            }
        });

        DeBugLog.i(TAG, "questionFlowLayout child count:" + questionFlowLayout.getChildCount());
        DeBugLog.i(TAG, "answerUserLayout child count:" + answerUserLayout.getChildCount());
    }

    private void promptBuildRequestParams() {
        new AlertDialog.Builder(this).setMessage(PleaseSelectCountry)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PutQuestionsActivity.this, SelectCountryActivity.class);
                        startActivityForResult(intent, AppConstant.SELECT_COUNTRY_OK);
                    }
                }).create().show();
    }

    /**
     * 构造请求参数
     *
     * @return
     */
    private RequestParams buildRequestParams() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(COUNTRY_ID, countryId);
        params.addBodyParameter(CITY_ID, cityId);
        return params;
    }

    /**
     * 网络请求方法
     *
     * @param params 参数
     */
    private void getInvitationAnswerData(RequestParams params) {
        SuiuuHttp httpRequest = new SuiuuHttp(HttpRequest.HttpMethod.POST,
                HttpServicePath.getInvitationAnswerUserPath, new InvitationRequestCallBack());
        httpRequest.setParams(params);
        httpRequest.executive();
    }

    private void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            DeBugLog.e(TAG, "select cancel");
            return;
        }

        if (data == null) {
            DeBugLog.e(TAG, "back data is null!");
            return;
        }

        switch (requestCode) {
            case AppConstant.SELECT_COUNTRY_OK:
                String countryCNname = data.getStringExtra(COUNTRY_CN_NAME);
                String cityName = data.getStringExtra(CITY_NAME);
                DeBugLog.i(TAG, "countryCNname:" + countryCNname + ",cityName:" + cityName);

                if (!TextUtils.isEmpty(countryCNname)) {
                    if (!TextUtils.isEmpty(cityName)) {
                        questionLocation.setText(countryCNname + "," + cityName);
                    } else {
                        questionLocation.setText(countryCNname);
                    }
                } else {
                    questionLocation.setText("");
                }
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_questions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.question_confirm:
                String title = inputQuestionTitle.getText().toString().trim();
                String content = inputProblemContent.getText().toString().trim();
                DeBugLog.i(TAG, "title:" + title + ",content:" + content);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class InvitationRequestCallBack extends RequestCallBack<String> {

        @Override
        public void onStart() {
            if (progressDialog != null && !progressDialog.isShowing())
                progressDialog.show();
        }

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            String str = responseInfo.result;
            DeBugLog.i(TAG, "返回的数据:" + str);
            hideDialog();
            try {
                InvitationAnswer invitationAnswer
                        = JsonUtils.getInstance().fromJSON(InvitationAnswer.class, str);
                List<InviteUserEntity> list = invitationAnswer.getData().getInviteUser();
                if (list != null && list.size() > 0) {
                    listAll.addAll(list);
                    adapter.setList(listAll);
                } else {
                    Toast.makeText(PutQuestionsActivity.this, NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "解析异常:" + e.getMessage());
                Toast.makeText(PutQuestionsActivity.this, DataError, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(HttpException e, String s) {
            DeBugLog.e(TAG, "HttpException:" + e + ",Error:" + s);
            hideDialog();
            Toast.makeText(PutQuestionsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

}