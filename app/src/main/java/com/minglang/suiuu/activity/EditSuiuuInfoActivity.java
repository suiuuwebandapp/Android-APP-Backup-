package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;

public class EditSuiuuInfoActivity extends BaseActivity {

    private ImageView back;

    private TextView ok;

    private EditText editInfo;

    private Intent suiuuIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_suiuu_info);

        suiuuIntent = getIntent();

        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        back = (ImageView) findViewById(R.id.edit_suiuu_info_back);
        ok = (TextView) findViewById(R.id.edit_suiuu_info_ok);
        editInfo = (EditText) findViewById(R.id.edit_suiuu_info_text);
    }

    /**
     * 控件动作
     */
    private void ViewAction() {

        String str = suiuuIntent.getStringExtra("oldInfo");
        editInfo.setText(str);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editInfo.getText().toString().trim();
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(EditSuiuuInfoActivity.this, "信息不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("newInfo", text);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

}
