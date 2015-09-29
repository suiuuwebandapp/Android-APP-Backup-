package com.minglang.suiuu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.minglang.pulltorefreshlibrary.PullToRefreshBase;
import com.minglang.pulltorefreshlibrary.PullToRefreshListView;
import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.InvitationAnswerAdapter;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.entity.InvitationAnswer;
import com.minglang.suiuu.entity.InvitationAnswer.InvitationAnswerData.InviteUserEntity;
import com.minglang.suiuu.utils.JsonUtils;
import com.minglang.suiuu.utils.L;
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
import java.util.Set;

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

    private String title;
    private String content;
    private String address;
    private ArrayList<String> tagList = new ArrayList<>();
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

    @BindString(R.string.DataError)
    String DataError;

    @BindString(R.string.NetworkAnomaly)
    String NetworkError;

    @BindString(R.string.SystemException)
    String SystemException;

    @Bind(R.id.select_be_invited_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.select_be_invited_list_view)
    PullToRefreshListView pullToRefreshListView;

    private ProgressDialog loadDialog;

    private ProgressDialog questionDialog;

    private InvitationAnswerAdapter adapter;

    private List<InviteUserEntity> listAll = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_be_invited);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        title = intent.getStringExtra(TITLE);
        content = intent.getStringExtra(CONTENT);
        address = intent.getStringExtra(ADDR);
        tagList = intent.getStringArrayListExtra(TAG_S);
        countryId = intent.getStringExtra(COUNTRY_ID);
        cityId = intent.getStringExtra(CITY_ID);
        L.i(TAG, "title:" + title + ",content:" + content + ",address:" + address
                + ",tagList:" + tagList.toString() + ",countryId:" + countryId + ",cityId:" + cityId);

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

        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        adapter = new InvitationAnswerAdapter(this, listAll, R.layout.item_invitation_answer);
        pullToRefreshListView.setAdapter(adapter);

        try {
            token = URLEncoder.encode(SuiuuInfo.ReadAppTimeSign(this), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void viewAction() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(context, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(context, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
            }

        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

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
     * TAG的list集合转为请求参数值
     *
     * @return Params value
     */
    private String buildTagParams() {
        String tags = "";
        if (tagList != null && tagList.size() > 0) {
            for (int i = 0; i < tagList.size(); i++) {
                if (tagList.size() > i + 1) {
                    tags = tags + tagList.get(i) + ",";
                } else {
                    tags = tags + tagList.get(i);
                }
            }
            return tags;
        } else {
            Toast.makeText(context, "标签不能为空！", Toast.LENGTH_SHORT).show();
            return "";
        }
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
        paramsArray[2] = new OkHttpManager.Params(ADDR, address);
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
        OkHttpManager.Params[] paramsArray = buildNewProblemParams();
        try {
            OkHttpManager.onPostAsynRequest(problemUrl, new AddProblemResultCallBack(), paramsArray);
        } catch (Exception e) {
            L.e(TAG, "提问请求异常:" + e.getMessage());
            hideDialog();
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }
    }

    private void hideDialog() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }

        if (questionDialog != null && questionDialog.isShowing()) {
            questionDialog.dismiss();
        }

        pullToRefreshListView.onRefreshComplete();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_questions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.question_confirm_commit:
                Set<Integer> set = adapter.getSet();
                SparseBooleanArray sparseBooleanArray = adapter.getSparseBooleanArray();
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
        public void onError(Request request, Exception e) {
            L.e(TAG, "添加新问题异常:" + e.getMessage());
            Toast.makeText(context, NetworkError, Toast.LENGTH_SHORT).show();
        }

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
        public void onFinish() {
            hideDialog();
        }

    }

}