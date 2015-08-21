package com.minglang.suiuu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.DeBugLog;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

public class AddReceivablesWayActivity extends BaseAppCompatActivity {

    private static final String TAG = AddReceivablesWayActivity.class.getSimpleName();

    private static final String KEY = "key";

    private static final String ALIPAY = "alipay";
    private static final String WECHAT = "weChat";

    private String strKey;

    @BindColor(R.color.white)
    int titleColor;

    @BindString(R.string.AccountNotNull)
    String AccountNotNull;

    @BindString(R.string.YourNameNotNull)
    String YourNameNotNull;

    @Bind(R.id.add_receivables_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.add_receivables_account)
    EditText addReceivablesAccount;

    @Bind(R.id.add_receivables_user_name)
    EditText addReceivablesUserName;

    @Bind(R.id.add_receivables_ok)
    Button addReceivablesOk;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receivables_way);

        strKey = getIntent().getStringExtra(KEY);

        ButterKnife.bind(this);
        initView();
        viewAction();
    }

    private void initView() {
        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        context = AddReceivablesWayActivity.this;
    }

    private void viewAction() {
        addReceivablesOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAccount = addReceivablesAccount.getText().toString().trim();
                String strUserName = addReceivablesUserName.getText().toString().trim();
                if (TextUtils.isEmpty(strAccount)) {
                    Toast.makeText(context, AccountNotNull, Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(strUserName)) {
                    Toast.makeText(context, YourNameNotNull, Toast.LENGTH_SHORT).show();
                } else {
                    if (strKey.equals(ALIPAY)) {
                        DeBugLog.i(TAG, strKey + ",strAccount=" + strAccount + ",strUserName=" + strUserName);
                    } else if (strKey.equals(WECHAT)) {
                        DeBugLog.i(TAG, strKey + ",strAccount=" + strAccount + ",strUserName=" + strUserName);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_receivables_way, menu);
        return true;
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