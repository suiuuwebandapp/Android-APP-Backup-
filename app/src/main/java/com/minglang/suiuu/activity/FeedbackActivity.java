package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.L;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * 反馈页面
 * <p/>
 * 待添加网络方法
 */
public class FeedbackActivity extends BaseAppCompatActivity {
    private static final String TAG = FeedbackActivity.class.getSimpleName();

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.feed_back_tool_bar)
    Toolbar toolbar;

    @BindString(R.string.FeedbackInfoNotNull)
    String FeedbackInfoNotNull;

    @BindString(R.string.ContactWayNotNull)
    String ContactWayNotNull;

    @Bind(R.id.feed_back_information)
    EditText feedBackInformation;

    @Bind(R.id.feed_back_contact_way)
    EditText feedBackContactWay;

    @Bind(R.id.feed_back_commit)
    Button commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initView();
        viewAction();
    }

    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);
    }

    /**
     * 控件动作
     */
    private void viewAction() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedbackInfo = feedBackInformation.getText().toString();
                String contactWay = feedBackContactWay.getText().toString();
                if (TextUtils.isEmpty(feedbackInfo)) {
                    Toast.makeText(FeedbackActivity.this, FeedbackInfoNotNull, Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(contactWay)) {
                    Toast.makeText(FeedbackActivity.this, ContactWayNotNull, Toast.LENGTH_SHORT).show();
                } else {
                    L.i(TAG, "反馈信息:" + feedbackInfo + ",联系方式:" + contactWay);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}