package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.ShowGVPictureAdapter;
import com.minglang.suiuu.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 反馈页面
 */

public class FeedbackActivity extends BaseActivity {

    @Bind(R.id.iv_top_back)
    ImageView back;

    @Bind(R.id.tv_top_right)
    TextView sendText;

    @Bind(R.id.et_question_description)
    EditText feedbackText;

    @Bind(R.id.gv_show_picture)
    GridView gv_show_picture;

    private ArrayList<String> listPicture = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ButterKnife.bind(this);
        ViewAction();
    }

    /**
     * 控件动作
     */
    private void ViewAction() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        gv_show_picture.setAdapter(new ShowGVPictureAdapter(this, listPicture, "0"));

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == 9) {
            listPicture = data.getStringArrayListExtra("pictureMessage");
            gv_show_picture.setAdapter(new ShowGVPictureAdapter(this, listPicture, "0"));
        }
    }
}
