package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.L;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

public class UsingSuiuuActivity extends BaseAppCompatActivity {

    private static final String TAG = UsingSuiuuActivity.class.getSimpleName();

    @BindColor(R.color.white)
    int titleColor;

    @Bind(R.id.using_suiuu_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.using_suiuu_text)
    TextView UsingSuiuuText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_suiuu);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(titleColor);
        setSupportActionBar(toolbar);

        try {
            InputStream is = getAssets().open("UsingSuiuu.txt");
            InputStreamReader reader = new InputStreamReader(is);

            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer buffer = new StringBuffer("");

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
                buffer.append("\n");
            }

            UsingSuiuuText.setText(buffer);

        } catch (Exception e) {
            L.e(TAG, "文件读取失败:" + e.getMessage());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}