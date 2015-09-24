package com.minglang.suiuu.activity;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.base.BaseAppCompatActivity;
import com.minglang.suiuu.utils.L;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UsingSuiuuActivity extends BaseAppCompatActivity {

    private static final String TAG = UsingSuiuuActivity.class.getSimpleName();

    @Bind(R.id.using_suiuu_text)
    TextView UsingSuiuuText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_suiuu);
        ButterKnife.bind(this);

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
        }catch (Exception e){
            L.e(TAG,"文件读取失败:"+e.getMessage());
        }

    }

}