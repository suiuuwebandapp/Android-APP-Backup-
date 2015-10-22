package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.InvitationAnswerAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.InvitationAnswer;
import com.minglang.suiuu.entity.InvitationAnswer.InvitationAnswerData.InviteUserEntity;
import com.minglang.suiuu.entity.InvitationAnswer.InvitationAnswerData.SysUserEntity;
import com.minglang.suiuu.entity.SearchInvited;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
import com.minglang.suiuu.utils.StatusBarCompat;
import com.minglang.suiuu.utils.SuiuuInfo;
import com.minglang.suiuu.utils.http.HttpNewServicePath;
import com.minglang.suiuu.utils.http.OkHttpManager;
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

public class SelectBeInvitedActivity extends BaseAppCompatActivity {

    private static final String TAG = SelectBeInvitedActivity.class.getSimpleName();

    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String ADDR = "addr";
    private static final String TAG_S = "tags";
    private static final String COUNTRY_ID = "countryId";
    private static final String CITY_ID = "cityId";
    private static final String USER_LIST = "userList";

    private static final String NAME = "name";

    private String title;
    private String content;
    private String address;
    private String countryId;
    private String cityId;

    private Context context;

    @BindColor(R.color.white)
    int titleColor;

    @BindString(R.string.load_wait)
    String LoadMsg;

    @BindString(R.string.question_wait)
    String QuestionMsg;

    @BindString(R.string.NoData)
    String NoData;

    @BindString(R.string.search_wait)
    String SearchData;

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.SystemException)
    String SystemException;

    @BindString(R.string.NoFindAboutUser)
    String NoFindAboutUser;

    @BindString(R.string.PleaseInputWantSearchContent)
    String PleaseInputWantSearchContent;

    @Bind(R.id.select_be_invited_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.input_your_invited_user)
    EditText inputYourInvitedUserView;

    @Bind(R.id.search_select_be_invited)
    ImageButton confirmSearch;

    @Bind(R.id.select_be_invited_list_view)
    ListView pullToRefreshListView;

    private ProgressDialog loadDialog;

    private ProgressDialog questionDialog;

    private ProgressDialog searchDialog;

    private InvitationAnswerAdapter adapter;

    private List<InviteUserEntity> listAll = new ArrayList<>();

    private List<String> userSignList;

    private String tags;

    private String inputString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_be_invited);

        StatusBarCompat.compat(this);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        title = intent.getStringExtra(TITLE);
        content = intent.getStringExtra(CONTENT);
        address = intent.getStringExtra(ADDR);
        tags = intent.getStringExtra(TAG_S);
        countryId = intent.getStringExtra(COUNTRY_ID);
        cityId = intent.getStringExtra(CITY_ID);
        L.i(TAG, "title:" + title + ",content:" + content + ",address:" + address +
                ",tags:" + tags + ",countryId:" + countryId + ",cityId:" + cityId);

        initView();
        viewAction();
        getInvitation4Service();
    }

    private void initView() {
        context = this;

        loadDialog = new ProgressDialog(this);
        loadDialog.setMessage(LoadMsg);
        loadDialog.setCanceledOnTouchOutside(false);

        questionDialog = new ProgressDialog(this);
        questionDialog.setMessage(QuestionMsg);
        questionDialog.setCanceledOnTouchOutside(false);

        searchDialog = new ProgressDialog(this);
        searchDialog.setMessage(SearchData);
        searchDialog.setCanceledOnTouchOutside(false);

        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        adapter = new InvitationAnswerAdapter(this, listAll, R.layout.item_invitation_answer);
        pullToRefreshListView.setAdapter(adapter);

        try {
            token = URLEncoder.encode(SuiuuInfo.ReadAppTimeSign(this), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void viewAction() {

        inputYourInvitedUserView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    String str = v.getText().toString();
                    L.i(TAG, "输入框内容:" + str);
                    if (!TextUtils.isEmpty(str)) {
                        searchAppointUser(str);
                    } else {
                        Toast.makeText(context, PleaseInputWantSearchContent, Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        inputYourInvitedUserView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputString = s.toString();
                if (TextUtils.isEmpty(s)) {
                    adapter.setList(listAll);
                }
            }

        });

        confirmSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FiltrationUserList(inputString);
                searchAppointUser(inputString);
            }
        });

        //        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //            @Override
        //            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //
        //            }
        //        });

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
        L.i(TAG, "Invitation URL:" + url);

        try {
            OkHttpManager.onGetAsynRequest(url, new InvitationResultCallBack());
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据已选择的用户构造UserSign参数
     *
     * @return UserSign参数
     */
    private String buildUserSignParams() {
        String userSigns = "";
        if (userSignList != null && userSignList.size() > 0) {
            for (int i = 0; i < userSignList.size(); i++) {
                if (userSignList.size() > i + 1) {
                    userSigns = userSigns + userSignList.get(i) + ",";
                } else {
                    userSigns = userSigns + userSignList.get(i);
                }
            }
        }
        L.i(TAG, "userSigns:" + userSigns);
        return userSigns;
    }

    /**
     * 构造提问参数
     *
     * @return 提问参数数组
     */
    private OkHttpManager.Params[] buildNewProblemParams() {
        String userSigns = buildUserSignParams();

        if (TextUtils.isEmpty(tags)) {
            Toast.makeText(context, "标签不能为空！", Toast.LENGTH_SHORT).show();
            return null;
        } else if (TextUtils.isEmpty(userSigns)) {
            Toast.makeText(context, "至少邀请一位用户！", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            OkHttpManager.Params[] paramsArray = new OkHttpManager.Params[7];
            paramsArray[0] = new OkHttpManager.Params(TITLE, title);
            paramsArray[1] = new OkHttpManager.Params(CONTENT, content);
            paramsArray[2] = new OkHttpManager.Params(ADDR, address);
            paramsArray[3] = new OkHttpManager.Params(COUNTRY_ID, countryId);
            paramsArray[4] = new OkHttpManager.Params(CITY_ID, cityId);
            paramsArray[5] = new OkHttpManager.Params(TAG_S, tags);
            paramsArray[6] = new OkHttpManager.Params(USER_LIST, userSigns);
            return paramsArray;
        }
    }

    /**
     * 发起提问网络请求
     */
    private void sendNewProblem4Service() {
        if (questionDialog != null && !questionDialog.isShowing()) {
            questionDialog.show();
        }

        String problemUrl = HttpNewServicePath.addNewProblemInterFacePath + "?" + TOKEN + "=" + token;
        OkHttpManager.Params[] paramsArray = buildNewProblemParams();
        if (paramsArray != null && paramsArray.length > 0) {
            try {
                OkHttpManager.onPostAsynRequest(problemUrl, new AddProblemResultCallBack(), paramsArray);
            } catch (Exception e) {
                L.e(TAG, "提问请求异常:" + e.getMessage());
                hideDialog();
                Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void hideDialog() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }

        if (questionDialog != null && questionDialog.isShowing()) {
            questionDialog.dismiss();
        }

        if (searchDialog != null && searchDialog.isShowing()) {
            searchDialog.dismiss();
        }

        //pullToRefreshListView.onRefreshComplete();
    }

    private void searchAppointUser(String userName) {
        if (searchDialog != null && !searchDialog.isShowing()) {
            searchDialog.show();
        }

        String[] keyArray = new String[]{NAME, TOKEN};
        String[] valueArray = new String[]{userName, token};

        String url = addUrlAndParams(HttpNewServicePath.getAppiontUserListPath, keyArray, valueArray);
        try {
            OkHttpManager.onGetAsynRequest(url, new SearchResultCallback());
        } catch (Exception e) {
            hideDialog();
            L.e(TAG, "搜索异常:" + e.getMessage());
            Toast.makeText(SelectBeInvitedActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_be_invited, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.question_confirm_commit:
                userSignList = adapter.getUserSignList();
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
        public void onResponse(String response) {
            L.i(TAG, "返回的数据:" + response);
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
            } else try {
                InvitationAnswer invitationAnswer = JsonUtils.getInstance().fromJSON(InvitationAnswer.class, response);
                SysUserEntity sysUserEntity = invitationAnswer.getData().getSysUser();

                if (sysUserEntity != null) {
                    InviteUserEntity inviteUserEntity = new InviteUserEntity();
                    inviteUserEntity.setHeadImg(sysUserEntity.getHeadImg());
                    inviteUserEntity.setNickname(sysUserEntity.getNickname());
                    inviteUserEntity.setUserSign(sysUserEntity.getUserSign());
                    listAll.add(inviteUserEntity);
                }

                List<InviteUserEntity> list = invitationAnswer.getData().getInviteUser();
                if (list != null && list.size() > 0) {
                    listAll.addAll(list);
                    adapter.setList(listAll);
                } else {
                    Toast.makeText(context, NoData, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                L.e(TAG, "解析异常:" + e.getMessage());
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString(STATUS);
                    switch (status) {
                        case "-1":
                            Toast.makeText(context, SystemException, Toast.LENGTH_SHORT).show();
                            break;

                        case "-2":
                            Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (Exception e1) {
                    L.e(TAG, "数据解析失败:" + e1.getMessage());
                    Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "获取邀请的用户列表异常:" + e);
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }

    }

    /**
     * 提问的回调接口
     */
    private class AddProblemResultCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "添加新问题数据:" + response);
            try {
                JSONObject object = new JSONObject(response);
                String status = object.getString(STATUS);
                switch (status) {
                    case "1":
                        finish();
                        break;

                    case "-1":
                        Toast.makeText(context, SystemException, Toast.LENGTH_SHORT).show();
                        break;

                    case "-2":
                        Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (Exception e) {
                L.e(TAG, "数据解析失败:" + e.getMessage());
                Toast.makeText(context, "添加问题失败！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "添加新问题异常:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }

    }

    private class SearchResultCallback extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            if (TextUtils.isEmpty(response)) {
                Toast.makeText(SelectBeInvitedActivity.this, NoData, Toast.LENGTH_SHORT).show();
            } else {
                SearchInvited searchInvited = JsonUtils.getInstance().fromJSON(SearchInvited.class, response);
                List<SearchInvited.SearchInvitedData> list = searchInvited.getData();
                List<InviteUserEntity> searchList = new ArrayList<>();
                if (list != null && list.size() > 0) {
                    for (SearchInvited.SearchInvitedData data : list) {
                        InviteUserEntity inviteUser = new InviteUserEntity();
                        inviteUser.setHeadImg(data.getHeadImg());
                        inviteUser.setNickname(data.getNickname());
                        inviteUser.setUserSign(data.getUserSign());
                        searchList.add(inviteUser);
                    }
                    adapter.setList(searchList);
                } else {
                    Toast.makeText(context, NoFindAboutUser, Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "搜索错误:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            hideDialog();
        }

    }

}