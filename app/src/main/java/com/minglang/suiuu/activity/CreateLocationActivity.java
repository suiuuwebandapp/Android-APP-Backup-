package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称：Android-APP-Backup-
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2015/9/18 17:12
 * 修改人：Administrator
 * 修改时间：2015/9/18 17:12
 * 修改备注：
 */
public class CreateLocationActivity extends BaseAppCompatActivity {

    @Bind(R.id.iv_create_location_callback)
    ImageView iv_create_location_callback;

    @Bind(R.id.iv_create_location_ok)
    ImageView iv_create_location_ok;

    @Bind(R.id.et_create_location)
    EditText et_create_location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_location);
        ButterKnife.bind(this);
        viewAction();
    }

    private void viewAction() {
        iv_create_location_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = et_create_location.getText().toString().trim();
                if (TextUtils.isEmpty(location)) {
                    Toast.makeText(CreateLocationActivity.this, "创建地点不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = CreateLocationActivity.this.getIntent();
                intent.putExtra("address", location);
                CreateLocationActivity.this.setResult(RESULT_OK, intent);
                finish();
            }
        });

        iv_create_location_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}