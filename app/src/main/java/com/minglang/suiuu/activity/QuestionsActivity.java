package com.minglang.suiuu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.customview.NoScrollBarListView;
import com.minglang.suiuu.utils.AppConstant;
import com.minglang.suiuu.utils.DeBugLog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 提问页面
 */
public class QuestionsActivity extends BaseAppCompatActivity {

    private static final String TAG = QuestionsActivity.class.getSimpleName();

    @Bind(R.id.questionsScrollView)
    ScrollView scrollView;

    @Bind(R.id.inputProblemTitle)
    EditText inputQuestionTitle;

    @Bind(R.id.inputProblemContent)
    EditText inputProblemContent;

    @Bind(R.id.LocationLayout)
    FrameLayout locationLayout;

    @Bind(R.id.questionLocation)
    TextView questionLocation;

    @Bind(R.id.questionFlowLayout)
    FlowLayout questionFlowLayout;

    @Bind(R.id.answerUserLayout)
    LinearLayout answerUserLayout;

    @Bind(R.id.answerUserListView)
    NoScrollBarListView answerUserListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        ButterKnife.bind(this);

        initView();
        ViewAction();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.questionToolbar);
        toolbar.setTitle("提问");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
    }

    private void ViewAction() {

        scrollView.smoothScrollTo(0, 0);

        answerUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        locationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionsActivity.this, SelectCountryActivity.class);
                startActivityForResult(intent, AppConstant.SELECT_COUNTRY_OK);
            }
        });

        DeBugLog.i(TAG, "questionFlowLayout child count:" + questionFlowLayout.getChildCount());

        DeBugLog.i(TAG, "answerUserLayout child count:" + answerUserLayout.getChildCount());
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
                String countryCNname = data.getStringExtra("countryCNname");
                String cityName = data.getStringExtra("cityName");
                DeBugLog.i(TAG, "countryCNname:" + countryCNname + ",cityName:" + cityName);

                if (!TextUtils.isEmpty(countryCNname)) {
                    if (!TextUtils.isEmpty(cityName)) {
                        questionLocation.setText(countryCNname + "," + countryCNname);
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

}