package com.minglang.suiuu.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.utils.AppConstant;
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

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDrawable;
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

    @BindString(R.string.SelectTag)
    String SelectTag;

    @BindDrawable(R.drawable.shape_trip_image_publish_tag)
    Drawable tripImagePublishTag;

    @BindDrawable(R.drawable.shape_trip_image_publish_press_tag)
    Drawable tripImagePublishPressTag;

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

    @Bind(R.id.question_location_view)
    TextView questionLocation;

    @Bind(R.id.question_tag_flow_layout)
    FlowLayout questionTagFlowLayout;

    @Bind(R.id.question_tag)
    TextView showSelectTagView;

    private String countryId;

    private String cityId;

    private ProgressDialog loadDialog;

    private String address;

    private Context context;

    /**
     * 自定义添加的标签
     */
    private String customTagName = "";

    /**
     * 所有显示Tag的TextView的集合
     */
    private List<TextView> tagViewList = new ArrayList<>();
    private List<TextView> clickTagViewList = new ArrayList<>();

    private String tagText = "";

    private String[] tagArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_questions);

        countryId = getIntent().getStringExtra(COUNTRY_ID);
        cityId = getIntent().getStringExtra(CITY_ID);
        L.i(TAG, "countryId:" + countryId + ",cityId:" + cityId);

        ButterKnife.bind(this);
        initView();
        viewAction();
        showDefaultTag2View();
    }

    /**
     * 初始化方法
     */
    private void initView() {

        context = this;

        try {
            token = URLEncoder.encode(SuiuuInfo.ReadAppTimeSign(this), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        loadDialog = new ProgressDialog(this);
        loadDialog.setMessage(LoadMsg);
        loadDialog.setCanceledOnTouchOutside(false);

        toolbar.setTitleTextColor(titleTextColor);
        setSupportActionBar(toolbar);

        scrollView.smoothScrollTo(0, 0);

        tagArray = getResources().getStringArray(R.array.tripImageTagName);
    }

    /**
     * 控件动作
     */
    private void viewAction() {

        questionLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PutQuestionsActivity.this, SelectCountryActivity.class);
                intent.putExtra(OTHER_TAG, TAG);
                startActivityForResult(intent, AppConstant.SELECT_COUNTRY_OK);
            }
        });

    }

    /**
     * 弹出添加Tag的Dialog
     */
    private void showTagDialog() {
        final EditText editText = new EditText(PutQuestionsActivity.this);
        new AlertDialog.Builder(PutQuestionsActivity.this)
                .setTitle(AddTag)
                .setView(editText)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        customTagName = editText.getText().toString().trim();
                        if (!TextUtils.isEmpty(customTagName)) {
                            sendTag2Service(customTagName);
                        } else {
                            Toast.makeText(PutQuestionsActivity.this, TagNameNotNull, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create().show();
    }

    /**
     * 把默认Tag加载到View上
     */
    @SuppressWarnings("deprecation")
    private void showDefaultTag2View() {
        questionTagFlowLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < tagArray.length + 1; i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.tv, questionTagFlowLayout, false);
            if (tagArray.length == i) {
                textView.setBackgroundResource(R.drawable.icon_plus);
            } else {
                textView.setBackground(tripImagePublishTag);
                textView.setText(tagArray[i]);
            }

            textView.setTag(i);
            tagViewList.add(textView);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tagText = "";
                    int tagNumber = (int) v.getTag();
                    if (tagNumber == tagArray.length) {
                        showTagDialog();
                    } else {
                        if (clickTagViewList.contains(tagViewList.get(tagNumber))) {
                            tagViewList.get(tagNumber).setBackground(tripImagePublishTag);
                            tagViewList.get(tagNumber).setTextColor(getResources().getColor(R.color.gray));
                            clickTagViewList.remove(tagViewList.get(tagNumber));
                        } else {
                            tagViewList.get(tagNumber).setBackground(tripImagePublishPressTag);
                            tagViewList.get(tagNumber).setTextColor(getResources().getColor(R.color.white));
                            clickTagViewList.add(tagViewList.get(tagNumber));
                        }
                        showSelectTag2View();
                    }
                }
            });
            questionTagFlowLayout.addView(textView);
        }
    }

    /**
     * 把选择的Tag加载到View上
     */
    private void showSelectTag2View() {
        for (TextView tv : clickTagViewList) {
            tagText += tv.getText() + " ";
        }

        tagText += customTagName;
        if (TextUtils.isEmpty(tagText)) {
            if (TextUtils.isEmpty(customTagName)) {
                showSelectTagView.setText(SelectTag);
            } else {
                showSelectTagView.setText(customTagName);
            }
        } else {
            showSelectTagView.setText(tagText);
        }
    }

    /**
     * 发送Tag到服务器
     *
     * @param tagName 标签名
     */
    private void sendTag2Service(String tagName) {
        OkHttpManager.Params params = new OkHttpManager.Params(NAME, tagName);
        OkHttpManager.Params params2 = new OkHttpManager.Params(TOKEN, token);

        String tagUrl = HttpNewServicePath.addTagInterFacePath + "?" + TOKEN + "=" + token;
        try {
            OkHttpManager.onPostAsynRequest(tagUrl, new TagResultCallBack(), params, params2);
        } catch (IOException e) {
            e.printStackTrace();
            hideDialog();
            Toast.makeText(PutQuestionsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

    private void hideDialog() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            L.e(TAG, "select cancel");
            return;
        }

        if (data == null) {
            L.e(TAG, "back data is null!");
            return;
        }

        switch (requestCode) {
            case AppConstant.SELECT_COUNTRY_OK:
                String countryCNname = data.getStringExtra(COUNTRY_CN_NAME);
                String cityName = data.getStringExtra(CITY_NAME);

                countryId = data.getStringExtra(COUNTRY_ID);
                cityId = data.getStringExtra(CITY_ID);

                address = String.format("%s%s%s", countryCNname, ",", cityName);

                L.i(TAG, "countryCNname:" + countryCNname + ",cityName:" + cityName);
                L.i(TAG, "countryId:" + countryId + ",cityId:" + cityId);
                L.i(TAG, "Address:" + address);

                if (!TextUtils.isEmpty(countryCNname)) {
                    if (!TextUtils.isEmpty(cityName)) {
                        questionLocation.setText(address);
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

                if (TextUtils.isEmpty(title)) {
                    Toast.makeText(context, "标题不能为空！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(content)) {
                    Toast.makeText(context, "内容不能为空！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(countryId) && TextUtils.isEmpty(cityId)) {
                    Toast.makeText(context, "请选择国家城市！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(tagText)) {
                    Toast.makeText(context, "标签不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, SelectBeInvitedActivity.class);
                    intent.putExtra(TITLE, title);
                    intent.putExtra(CONTENT, content);
                    intent.putExtra(ADDR, address);
                    intent.putExtra(COUNTRY_ID, countryId);
                    intent.putExtra(CITY_ID, cityId);
                    intent.putExtra(TAG_S, tagText.replace(" ", ","));
                    startActivity(intent);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 添加标签的回调接口
     */
    private class TagResultCallBack extends OkHttpManager.ResultCallback<String> {

        @Override
        public void onResponse(String response) {
            L.i(TAG, "添加标签返回的数据:" + response);
            try {
                JSONObject object = new JSONObject(response);
                String status = object.getString(STATUS);
                switch (status) {
                    case "1":
                        customTagName = customTagName + " ";
                        showSelectTag2View();
                        break;

                    case "-1":
                        Toast.makeText(context, SystemException, Toast.LENGTH_SHORT).show();
                        break;

                    case "-2":
                        Toast.makeText(context, object.getString(DATA), Toast.LENGTH_SHORT).show();
                        break;

                }
            } catch (Exception e) {
                L.e(TAG, "Exception:" + e.getMessage());
                Toast.makeText(context, DataError, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Request request, Exception e) {
            L.e(TAG, "添加标签异常:" + e.getMessage());
            Toast.makeText(PutQuestionsActivity.this, NetworkError, Toast.LENGTH_SHORT).show();
        }

    }

}