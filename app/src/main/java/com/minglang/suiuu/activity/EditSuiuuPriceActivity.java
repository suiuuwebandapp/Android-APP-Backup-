package com.minglang.suiuu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseActivity;
import com.minglang.suiuu.utils.AppConstant;

/**
 * 编辑随游价格
 */
public class EditSuiuuPriceActivity extends BaseActivity {

    private static final String TAG = EditSuiuuPriceActivity.class.getSimpleName();

    private ImageView back;

    private TextView ok;

    private EditText editBasicPrice;

    private EditText editAdditionalPrice;

    private EditText editOtherPrice;

    private Intent oldIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_suiuu_price);

        oldIntent = getIntent();

        initView();
        ViewAction();
    }

    /**
     * 初始化方法
     */
    private void initView() {
        back = (ImageView) findViewById(R.id.edit_suiuu_price_back);
        ok = (TextView) findViewById(R.id.edit_suiuu_price_ok);

        editBasicPrice = (EditText) findViewById(R.id.edit_suiuu_basic_price);
        editAdditionalPrice = (EditText) findViewById(R.id.edit_suiuu_additional_price);
        editOtherPrice = (EditText) findViewById(R.id.edit_suiuu_other_price);
    }

    private void ViewAction() {

        String oldPrice = oldIntent.getStringExtra("oldPrice");
        Log.i(TAG, "oldPrice:" + oldPrice);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textBasicPrice = editBasicPrice.getText().toString().trim();
                String textAdditionalPrice = editAdditionalPrice.getText().toString().trim();
                String textOtherPrice = editOtherPrice.getText().toString().trim();

                if (TextUtils.isEmpty(textBasicPrice)) {
                    Toast.makeText(EditSuiuuPriceActivity.this, "基本价格不能为空！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(textAdditionalPrice)) {
                    Toast.makeText(EditSuiuuPriceActivity.this, "附加价格不能为空！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(textOtherPrice)) {
                    Toast.makeText(EditSuiuuPriceActivity.this, "其他价格不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("newBasicPrice", textBasicPrice);
                    intent.putExtra("newAdditionalPrice", textAdditionalPrice);
                    intent.putExtra("newOtherPrice", textOtherPrice);
                    setResult(AppConstant.EDIT_SUIUU_PRICE_BACK, intent);
                    finish();
                }

            }
        });

    }

}
