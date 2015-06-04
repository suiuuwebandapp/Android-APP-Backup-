package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.ShowGVPictureAdapter;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.chat.activity.ShowBigImage;
import com.minglang.suiuu.utils.SystemBarTintManager;

import java.util.ArrayList;

/**
 * 反馈页面
 */

public class FeedbackActivity extends BaseActivity {

    private ImageView back;

    private TextView sendText;

    private EditText feedbackText;

    private GridView gv_show_picture;

    private ArrayList<String> listPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initView();
        ViewAction();
    }

    private void ViewAction() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = feedbackText.getText().toString();
                Toast.makeText(FeedbackActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });

        feedbackText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s)) {
                    sendText.setTextColor(getResources().getColor(R.color.remindColor));
                } else {
                    sendText.setTextColor(getResources().getColor(R.color.titleColor));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        gv_show_picture.setAdapter(new ShowGVPictureAdapter(this, listPicture,"0"));
        gv_show_picture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == listPicture.size()) {
                    Intent intent = new Intent(FeedbackActivity.this, SelectPictureActivity.class);
                    startActivityForResult(intent, 1);
                } else {
                    Intent showPicture = new Intent(FeedbackActivity.this, ShowBigImage.class);
                    showPicture.putExtra("path", listPicture.get(position));
                    startActivity(showPicture);
                }
            }
        });
    }

    private void initView() {
        listPicture = new ArrayList<>();
        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(false);
        mTintManager.setTintColor(getResources().getColor(R.color.tr_black));

        int statusHeight = mTintManager.getConfig().getStatusBarHeight();

        boolean isKITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKITKAT) {
            RelativeLayout feedbackRootLayout = (RelativeLayout) findViewById(R.id.feedbackRootLayout);
            feedbackRootLayout.setPadding(0, statusHeight, 0, 0);
        }
        back = (ImageView) findViewById(R.id.iv_top_back);

        sendText = (TextView) findViewById(R.id.tv_top_right);

        feedbackText = (EditText) findViewById(R.id.et_question_description);

        gv_show_picture = (GridView) findViewById(R.id.gv_show_picture);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == 9) {
            listPicture = data.getStringArrayListExtra("pictureMessage");
            gv_show_picture.setAdapter(new ShowGVPictureAdapter(this, listPicture,"0"));
        }
    }
}
