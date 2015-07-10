package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.DeBugLog;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

/**
 * 回答问题页面
 */
public class AnswerActivity extends BaseAppCompatActivity {

    private static final String TAG = AnswerActivity.class.getSimpleName();

    @Bind(R.id.answerToolbar)
    Toolbar toolbar;

    @Bind(R.id.answerEditText)
    EditText editText;

    @BindColor(R.color.white)
    int titleTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        toolbar.setTitle("回答");
        toolbar.setTitleTextColor(titleTextColor);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_answer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.answer_confirm:
                String str = editText.getText().toString().trim();
                DeBugLog.i(TAG, "str:" + str);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}