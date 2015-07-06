package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.customview.FlowLayout;
import com.minglang.suiuu.customview.InnerListView;
import com.minglang.suiuu.utils.ScreenUtils;

/**
 * 提问页面
 */
public class QuestionsActivity extends AppCompatActivity {

    private ScrollView scrollView;

    private EditText inputQuestionTitle;
    private EditText inputProblemContent;

    private TextView questionLocaltion;

    private LinearLayout tagLayout;

    private FlowLayout questionFlowLayout;

    private LinearLayout answerUserLayout;

    private InnerListView answerUserListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

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

        scrollView = (ScrollView) findViewById(R.id.questionsScrollView);

        inputQuestionTitle = (EditText) findViewById(R.id.inputProblemTitle);
        inputProblemContent = (EditText) findViewById(R.id.inputProblemContent);

        questionLocaltion = (TextView) findViewById(R.id.questionLocaltion);

        tagLayout = (LinearLayout) findViewById(R.id.tagLayout);

        questionFlowLayout = (FlowLayout) findViewById(R.id.questionFlowLayout);

        answerUserLayout = (LinearLayout) findViewById(R.id.answerUserLayout);

        answerUserListView = (InnerListView) findViewById(R.id.answerUserListView);
        answerUserListView.setParentScrollView(scrollView);
        answerUserListView.setMaxHeight(new ScreenUtils(this).getScreenHeight());
    }

    private void ViewAction() {

        answerUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

}