package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.minglang.suiuu.utils.HttpNewServicePath;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.OkHttpManager;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.squareup.okhttp.Request;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    private static final String NAME = "name";

    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String ADDR = "addr";
    private static final String TAG_S = "tags";

    private static final String STATUS = "status";
    private static final String DATA = "data";

    private static final String OTHER_TAG = "OtherTag";

    @BindString(R.string.load_wait)
    String LoadMsg;

    @BindString(R.string.question_wait)
    String QuestionMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.AddTag)
    String AddTag;

    @BindString(R.string.TagNameNotNull)
    String TagNameNotNull;

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

    @Bind(R.id.answer_user_list_view)
    NoScrollBarListView invitationAnswerUserListView;

    @Bind(R.id.question_location)
    TextView addTagView;

    private String countryCNname;

    private String cityName;

    private String countryId;

    private String cityId;

    private String title;

    private String content;

    private ProgressDialog loadDialog;

    private ProgressDialog questionDialog;

    private List<InviteUserEntity> listAll = new ArrayList<>();

    private InvitationAnswerAdapter adapter;

    private List<String> tagList = new ArrayList<>();

    private Context context;

    private String tagName;

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
            promptSelectCountry();
        } else {
            getInvitation4Service();
        }
    }

    /**
     * 初始化方法
     */
    private void initView() {
        loadDialog = new ProgressDialog(this);
        loadDialog.setMessage(LoadMsg);
        loadDialog.setCanceledOnTouchOutside(false);

        questionDialog = new ProgressDialog(this);
        questionDialog.setMessage(QuestionMsg);
        questionDialog.setCanceledOnTouchOutside(false);

        toolbar.setTitleTextColor(titleTextColor);
        setSupportActionBar(toolbar);

        scrollView.smoothScrollTo(0, 0);

        context = this;

        adapter = new InvitationAnswerAdapter(this, listAll, R.layout.item_invitation_answer);
        invitationAnswerUserListView.setAdapter(adapter);

        try {
            token = URLEncoder.encode(SuiuuInfo.ReadAppTimeSign(this), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 控件动作
     */
    private void viewAction() {

        addTagView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(PutQuestionsActivity.this);
                new AlertDialog.Builder(PutQuestionsActivity.this)
                        .setTitle(AddTag)
                        .setView(editText)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tagName = editText.getText().toString().trim();
                                if (!TextUtils.isEmpty(tagName)) {
                                    tagList.add(tagName);
                                    sendTag2Service(tagName);
                                } else {
                                    Toast.makeText(PutQuestionsActivity.this, TagNameNotNull, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .create().show();
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
                intent.putExtra(OTHER_TAG, TAG);
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

    }

    /**
     * 提示选择国家
     */
    private void promptSelectCountry() {
        new AlertDialog.Builder(this)
                .setMessage(PleaseSelectCountry)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PutQuestionsActivity.this, SelectCountryActivity.class);
                        intent.putExtra(OTHER_TAG, TAG);
                        startActivityForResult(intent, AppConstant.SELECT_COUNTRY_OK);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create().show();
    }

    /**
     * 构造请求邀请URL
     *
     * @return 带参URL
     */
    private String buildInvitationUrl() {
        String[] keyArray = new String[]{COUNTRY_ID, CITY_ID, TOKEN};
        String[] valueArray = new String[]{countryId, cityId, token};
        return addUrlAndParams(HttpNewServicePath.getInvitationAnswerUserPath, keyArray, valueArray);
    }

    /**
     * 获取可被邀请的用户列表
     */
    private void getInvitation4Service() {
        if (loadDialog != null && !loadDialog.isShowing()) {
            loadDialog.show();
        }

        String url = buildInvitationUrl();
        DeBugLog.i(TAG, "Invitation URL:" + url);

        try {
            OkHttpManager.onGetAsynRequest(url, new InvitationResultCallBack());
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(PutQuestionsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 发送标签到服务器
     *
     * @param tagName 标签名
     */
    private void sendTag2Service(String tagName) {
        OkHttpManager.Params params = new OkHttpManager.Params(NAME, tagName);
        OkHttpManager.Params params2 = new OkHttpManager.Params(TOKEN, token);

        String tagUrl = HttpNewServicePath.addTagInterFacePath + "?" + TOKEN + "=" + token;
        DeBugLog.i(TAG, "tagUrl:" + tagUrl);

        try {
            OkHttpManager.onPostAsynRequest(tagUrl, new TagResultCallBack(), params, params2);
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(PutQuestionsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * TAG的list集合转为请求参数值
     *
     * @return Params value
     */
    private String buildTagParams() {
        String tags = null;
        for (int i = 0; i < tagList.size(); i++) {
            if (tagList.size() > i + 1) {
                tags = tags + tagList.get(i) + ",";
            } else {
                tags = tags + tagList.get(i);
            }
        }
        return tags;
    }

    /**
     * 构造提问参数
     *
     * @return 提问参数数组
     */
    private OkHttpManager.Params[] buildNewProblemParams() {
        OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[6];
        paramsArray[0] = new OkHttpManager.Params(TITLE, title);
        paramsArray[1] = new OkHttpManager.Params(CONTENT, content);
        paramsArray[2] = new OkHttpManager.Params(ADDR, countryCNname + "," + cityName);
        paramsArray[3] = new OkHttpManager.Params(COUNTRY_ID, countryId);
        paramsArray[4] = new OkHttpManager.Params(CITY_ID, cityId);
        paramsArray[5] = new OkHttpManager.Params(TAG_S, buildTagParams());
        return paramsArray;
    }

    /**
     * 发起提问网络请求
     */
    private void sendNewProblem4Service() {
        if (questionDialog != null && !questionDialog.isShowing()) {
            questionDialog.show();
        }

        String problemUrl = HttpNewServicePath.addNewProblemInterFacePath + "?" + TOKEN + "=" + token;
        DeBugLog.i(TAG, "problemUrl:" + problemUrl);

        try {
            OkHttpManager.onPostAsynRequest(problemUrl, new AddProblemResultCallBack(), buildNewProblemParams());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(PutQuestionsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    private void hideDialog() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }

        if (questionDialog != null && questionDialog.isShowing()) {
            questionDialog.dismiss();
        }

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
                countryCNname = data.getStringExtra(COUNTRY_CN_NAME);
                cityName = data.getStringExtra(CITY_NAME);

                countryId = data.getStringExtra(COUNTRY_ID);
                cityId = data.getStringExtra(CITY_ID);

                DeBugLog.i(TAG, "countryCNname:" + countryCNname + ",cityName:" + cityName);
                DeBugLog.i(TAG, "countryId:" + countryId + ",cityId:" + cityId);

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
                title = inputQuestionTitle.getText().toString().trim();
                content = inputProblemContent.getText().toString().trim();
                DeBugLog.i(TAG, "title:" + title + ",content:" + content);

                sendNewProblem4Service();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取关注用户的回调接口
     */
    private class InvitationResultCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "获取邀请的用户列表异常:" + e);
            hideDialog();
            Toast.makeText(PutQuestionsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "返回的数据:" + response);
            hideDialog();

            try {
                InvitationAnswer invitationAnswer
                        = JsonUtils.getInstance().fromJSON(InvitationAnswer.class, response);
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
    }

    /**
     * 添加标签的回调接口
     */
    private class TagResultCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "添加标签异常:" + e.getMessage());
            Toast.makeText(PutQuestionsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "添加标签返回的数据:" + response);
            try {
                JSONObject object = new JSONObject(response);
                String status = object.getString(STATUS);
                switch (status) {
                    case "1":
                        TextView tagView = (TextView) LayoutInflater.from(context).inflate(R.layout.layout_text_tag,
                                questionFlowLayout, false);
                        tagView.setText(tagName);
                        questionFlowLayout.addView(tagView);
                        break;

                    case "-1":
                        Toast.makeText(context, SystemException, Toast.LENGTH_SHORT).show();
                        break;

                    case "-2":
                        Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                        break;

                }
            } catch (Exception e) {
                DeBugLog.e(TAG, "Exception:" + e.getMessage());
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * 提问的回调接口
     */
    private class AddProblemResultCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onError(Request request, Exception e) {
            DeBugLog.e(TAG, "添加新问题异常:" + e.getMessage());
            Toast.makeText(PutQuestionsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response) {
            DeBugLog.i(TAG, "添加新问题数据:" + response);
        }

        @Override
        public void onFinish() {
            hideDialog();
        }
    }

}