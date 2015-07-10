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
import com.minglang.suiuu.utils.AppConstant;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 编辑随游信息
 */
public class EditSuiuuInfoActivity extends BaseActivity {

    @Bind(R.id.edit_suiuu_info_back)
    ImageView back;

    @Bind(R.id.edit_suiuu_info_ok)
    TextView ok;

    @Bind(R.id.edit_suiuu_info_text)
    EditText editInfo;

    private Intent suiuuIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_suiuu_info);

        ButterKnife.bind(this);

        suiuuIntent = getIntent();

        ViewAction();
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
                    setResult(AppConstant.EDIT_SUIUU_INFO_TEXT_BACK, intent);
                    finish();
                }
            }
        });

    }

}