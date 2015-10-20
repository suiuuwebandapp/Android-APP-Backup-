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
import butterknife.ButterKnife;

/**
 * 编辑随游价格
 */
public class EditSuiuuPriceActivity extends BaseAppCompatActivity {

    private static final String TAG = EditSuiuuPriceActivity.class.getSimpleName();

    private static final String OLD_PRICE = "oldPrice";

    @Bind(R.id.edit_suiuu_price_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.edit_suiuu_basic_price)
    EditText editBasicPrice;

    @Bind(R.id.edit_suiuu_additional_price)
    EditText editAdditionalPrice;

    @Bind(R.id.edit_suiuu_other_price)
    EditText editOtherPrice;

    private Intent oldIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_suiuu_price);
        oldIntent = getIntent();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String oldPrice = oldIntent.getStringExtra(OLD_PRICE);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_suiuu_price, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;

            case R.id.edit_suiuu_price_ok:
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
