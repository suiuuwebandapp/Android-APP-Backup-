package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.AppConstant;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

/**
 * 编辑随游信息
 */
public class EditSuiuuInfoActivity extends BaseAppCompatActivity {

    private static final String OLD_INFO = "oldInfo";

    private Intent suiuuIntent;

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.edit_suiuu_info_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.edit_suiuu_info_text)
    EditText editInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_suiuu_info);
        suiuuIntent = getIntent();
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 控件动作
     */
    private void initView() {
        String str = suiuuIntent.getStringExtra(OLD_INFO);
        editInfo.setText(str);

        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_suiuu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.edit_suiuu_info_ok:
                String text = editInfo.getText().toString().trim();
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(EditSuiuuInfoActivity.this, "信息不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("newInfo", text);
                    setResult(AppConstant.EDIT_SUIUU_INFO_TEXT_BACK, intent);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}